/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description: 
*
*/
/**
 * 
 */
package com.nokia.sdt.sourcegen.doms.rss.impl;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.sourcegen.core.ISourceFile;
import com.nokia.sdt.sourcegen.doms.rss.IRssProjectFileManager;
import com.nokia.sdt.sourcegen.doms.rss.IRssModelStringHandler;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorLocIncludeNode;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorRlsIncludeNode;
import com.nokia.sdt.symbian.ISymbianNameGenerator;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Pair;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import java.util.*;

/**
 * 
 * 
 */
public class RssModelRlsStringHandler extends RssModelStringHandlerBase
		implements IRssModelStringHandler {

	/**
	 * @param manipulator
	 */
	public RssModelRlsStringHandler() {
		super();
	}

	public void exportStrings(IDesignerDataModel dataModel,
			IAstRssSourceFile mainFile, IAstRssSourceFile rssFile,
			IRssProjectFileManager fileManager) {

		DesignerDataModel modelImpl = (DesignerDataModel) dataModel;
		IDesignerData dd = modelImpl.getDesignerData();
		Check.checkArg(dd);
		
	    // remove any *.loc includes
	    IAstPreprocessorLocIncludeNode[] locIncludes = (IAstPreprocessorLocIncludeNode[]) rssFile.getFileNodes(IAstPreprocessorLocIncludeNode.class);
	    for (int i = 0; i < locIncludes.length; i++) {
	    	rssFile.removeFileNode(locIncludes[i]);
	    }

		// in non-primary file, reuse info from main file
		if (mainFile != rssFile) {
			reuseStringTablesFromMainFile(mainFile, rssFile);
			return;
		}

		// import macro table
		IMacroStringTable macroTable = dd.getMacroTable();
		if (macroTable != null) {
			EMap macros = macroTable.getStringMacros();
			defineStringMacros(rssFile, macros, macroTable.getKeyProvider());
		}

		// import localization tables
		ILocalizedStringBundle bundle = dd.getStringBundle();
		if (bundle == null)
			return;
		
		Set<Language> emittedLanguages = new HashSet<Language>();

		EList tables = bundle.getLocalizedStringTables();
		for (Iterator iter = tables.iterator(); iter.hasNext();) {
			ILocalizedStringTable table = (ILocalizedStringTable) iter.next();
			int langCode = table.getLanguage().getLanguageCode();

			// find or create an *.rls file
			ISourceFile rlsFile = null;
			IAstRlsSourceFile rlsSrcFile = rssFile.findRlsFile(langCode);
			
			if (rlsSrcFile != null) {
				rlsFile = rlsSrcFile.getSourceFile();
			} else {
				String suffix = "_" + Character.forDigit(langCode / 10, 10) //$NON-NLS-1$
						+ Character.forDigit(langCode % 10, 10);
	
				rlsSrcFile = (IAstRlsSourceFile) fileManager.findOrCreateDerivedSourceFile(dataModel,
								rssFile,
								ISymbianNameGenerator.RESOURCE_DIRECTORY_ID, suffix, ".rls", //$NON-NLS-1$
								AstRlsSourceFile.class, new Integer[] { langCode }, false);
				rlsFile = rlsSrcFile.getSourceFile();
			}
			
			// remove unreferenced strings
			removeUnreferencedStringMacros(rlsSrcFile, table.getStrings(), 
					bundle.getUserGeneratedStringKeys());
			
			// define the strings
			boolean any = defineRlsStrings(rlsSrcFile, table.getStrings(), macroTable
					.getKeyProvider());
			if (any) {
		    	if (rssFile.findInclude(rlsFile) == null) {
		    		IAstPreprocessorRlsIncludeNode rlsInc = new AstPreprocessorRlsIncludeNode(
		    				rlsFile.getFileName(), true, rlsSrcFile, langCode);
		    		rssFile.addFileNode(rlsInc);
		    	}
		    	fileManager.replaceRssFile(rlsSrcFile);
			}
			
			emittedLanguages.add(table.getLanguage());
		}
		
	    // remove any deleted languages
		IAstRlsSourceFile[] rlsSrcFiles = rssFile.getRlsSourceFiles();
		for (int i = 0; i < rlsSrcFiles.length; i++) {
			int langCode = rlsSrcFiles[i].getLanguageCode();
			Language language = new Language(langCode);
			if (!emittedLanguages.contains(language)) {
				rssFile.removeRlsSourceFile(rlsSrcFiles[i]);
			}
		}
		
	}
	
	/**
	 * @param mainFile
	 * @param rssFile
	 */
	private void reuseStringTablesFromMainFile(IAstRssSourceFile mainFile, IAstRssSourceFile rssFile) {
    	IAstPreprocessorRlsIncludeNode[] rlsIncls = (IAstPreprocessorRlsIncludeNode[]) mainFile.getFileNodes(IAstPreprocessorRlsIncludeNode.class);
    	for (int i = 0; i < rlsIncls.length; i++) {
    		ISourceFile sf = rlsIncls[i].getFile().getSourceFile();
    		if (rssFile.findInclude(sf) == null) {
    			AstPreprocessorRlsIncludeNode cloneIncl = new AstPreprocessorRlsIncludeNode(
    					rlsIncls[i].getFilename(), rlsIncls[i].isUserPath(), rlsIncls[i].getFile(),
    					rlsIncls[i].getLanguageCode());
				rssFile.addFileNode(cloneIncl);
    		}
    	}
	}

	/**
	 * Define or replace string macros for a given file.
	 * 
	 * @param file
	 * @param macros
	 * @param provider
	 * @return any changes
	 */
	private boolean defineRlsStrings(IAstRssSourceFile file, EMap macros,
			final IStringKeyProvider provider) {
		IAstRlsString[] oldStrings = file.getRlsStrings();
		List keys = new ArrayList(macros.entrySet());
		Collections.sort(keys, new Comparator() {

			public int compare(Object o1, Object o2) {
				Map.Entry e1 = (Map.Entry) o1;
				Map.Entry e2 = (Map.Entry) o2;
				return provider.compareKeys((String) e1.getKey(), (String) e2
						.getKey());
			}
		});

		boolean needUtf8 = false;
		boolean any = false;

		for (Iterator iter = keys.iterator(); iter.hasNext();) {
			any = true;
			Map.Entry entry = (Map.Entry) iter.next();
			IAstRlsString rlsString = null;

			String key = entry.getKey().toString();
	        Object value = entry.getValue();
	        String strValue = value != null ? value.toString() : "";  //$NON-NLS-1$

			for (int i = 0; i < oldStrings.length; i++) {
				if (oldStrings[i].getIdentifier().getName().equals(key)) {
					rlsString = oldStrings[i];
					break;
				}
			}

			if (rlsString == null) {
				if (strValue != null) {
					// make a new entry
					rlsString = new AstRlsString(key, strValue);
					file.appendFileNode(rlsString);
				} else {
					// leave undefined
				}
			} else {
				if (strValue != null) {
					// replace value of old rls_string
					if (!rlsString.getString().getValue().equals(strValue))
						rlsString.setString(strValue);
				} else {
					// delete string
					file.removeFileNode(rlsString);
				}
			}
			
			if (strValue != null)
				needUtf8 |= stringNeedsUtf8(strValue);
		}

		if (any && needUtf8) {
			forceCharSet(file, "UTF8", "UTF-8"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		return any;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelStringHandler#importStrings()
	 */
	public Pair<Command, Command> importStrings(IDesignerDataModel dataModel,
			IAstRssSourceFile rssFile,
			IRssProjectFileManager fileManager) {
	    DesignerDataModel modelImpl = (DesignerDataModel) dataModel;
	    IDesignerData dd = modelImpl.getDesignerData();
	    Check.checkArg(dd);

	    // Scan for all the designer-generated string entries and
	    // update them as needed.  Ignore deleted strings.
	    ILocalizedStringBundle bundle = dd.getStringBundle();
	    if (bundle == null)
	        return null;

	    CompoundCommand userCommands = new CompoundCommand();
	    CompoundCommand changeCommands = new CompoundCommand();
	    
	    // remove user-generated keys
	    userCommands.append(new ClearUserKeysCommand(dd));
	    
	    // import macros
	    IMacroStringTable macroTable = dd.getMacroTable();
	    if (macroTable != null) {
	    	importStringMacros(userCommands, changeCommands, dd, rssFile, null, macroTable.getStringMacros(), 
	        		macroTable.getUserGeneratedStringKeys()); 
	    }

	    // import strings from RLS
	    Set<Language> expectedLanguages = new HashSet<Language>();
	    for (Iterator iter = bundle.getLocalizedStringTables().iterator(); iter.hasNext();) {
			ILocalizedStringTable table = (ILocalizedStringTable) iter.next();
			expectedLanguages.add(table.getLanguage());
		}
	    
	    // find all the *.rls files and process their languages
	    IAstRlsSourceFile[] rlsFiles = rssFile.getRlsSourceFiles();
	    for (int i = 0; i < rlsFiles.length; i++) {
	    	IAstRlsSourceFile rlsSrcFile = rlsFiles[i];
			int langCode = rlsSrcFile.getLanguageCode();
			Language language = new Language(langCode);
			ILocalizedStringTable table = bundle.findLocalizedStringTable(language);
			if (table == null) {
				// do not automatically add new languages
				emitIgnoringAddedLanguageMessage(rssFile, language);
				continue;
			}
			importStrings(userCommands, changeCommands, dd, rlsSrcFile, language,
					table.getStrings(), bundle.getUserGeneratedStringKeys());
			expectedLanguages.remove(language);
	    }
	    
		// do not automatically remove languages
	    for (Iterator iter = expectedLanguages.iterator(); iter.hasNext();) {
			Language language = (Language) iter.next();
			emitIgnoringDeletedLanguageMessage(rssFile, language);
	    }
	    
	    return new Pair(userCommands.unwrap(), changeCommands.unwrap());
	}

	
}
