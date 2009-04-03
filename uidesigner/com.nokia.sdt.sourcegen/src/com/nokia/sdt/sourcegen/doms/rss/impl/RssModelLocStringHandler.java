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
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.*;
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
public class RssModelLocStringHandler extends RssModelStringHandlerBase {

	public RssModelLocStringHandler() {
		super();
	}

	public void exportStrings(IDesignerDataModel dataModel,
			IAstRssSourceFile mainFile, IAstRssSourceFile rssFile,
			IRssProjectFileManager fileManager) {
		
	    DesignerDataModel modelImpl = (DesignerDataModel) dataModel;
	    IDesignerData dd = modelImpl.getDesignerData();
	    Check.checkArg(dd);
	    
	    // remove any *.rls includes
	    IAstPreprocessorRlsIncludeNode[] rlsIncludes = (IAstPreprocessorRlsIncludeNode[]) rssFile.getFileNodes(IAstPreprocessorRlsIncludeNode.class);
	    for (int i = 0; i < rlsIncludes.length; i++) {
	    	rssFile.removeFileNode(rlsIncludes[i]);
	    }

	    // in non-primary file, just include files from other file
	    if (mainFile != rssFile) {
	    	reuseStringsFromMainFile(mainFile, rssFile);
	    	return;
	    }
	    
	    // export macro table
	    IMacroStringTable macroTable = dd.getMacroTable();
	    if (macroTable != null) {
	        EMap macros = macroTable.getStringMacros();
	        defineStringMacros(rssFile, macros, macroTable.getKeyProvider());
	    }
	    
	    // export localization tables
	    ILocalizedStringBundle bundle = dd.getStringBundle();
	    if (bundle == null)
	        return;
	    
	    
	    // find or create a *.loc file
	    IAstPreprocessorLocIncludeNode[] locIncludes = (IAstPreprocessorLocIncludeNode[]) rssFile.getFileNodes(IAstPreprocessorLocIncludeNode.class);
	    IAstLocSourceFile locSrcFile = null;
	    if (locIncludes.length > 0)
	    	locSrcFile = (IAstLocSourceFile) locIncludes[0].getFile();
	    
	    if (locSrcFile == null) {
	    	locSrcFile = (IAstLocSourceFile) fileManager.findOrCreateDerivedSourceFile(
		    		dataModel,
		            rssFile,
		            ISymbianNameGenerator.RESOURCE_DIRECTORY_ID, null,  //$NON-NLS-1$
		            ".loc", AstLocSourceFile.class, null, false);
	    }
	    
	    boolean any = false;
	    Set<Language> emittedLanguages = new HashSet<Language>();
	    EList tables = bundle.getLocalizedStringTables();
	    for (Iterator iter = tables.iterator(); iter.hasNext();) {
	        ILocalizedStringTable table = (ILocalizedStringTable) iter.next();
	        
	        // find or create an *.lxx file
	        int langCode = table.getLanguage().getLanguageCode();
	        String extension = ".l" + Character.forDigit(langCode / 10, 10) //$NON-NLS-1$
	            + Character.forDigit(langCode % 10, 10);
	        
	        IAstLxxSourceFile lxxSrcFile;
	        lxxSrcFile = locSrcFile.findLxxFile(langCode);
	        if (lxxSrcFile == null) {
		        lxxSrcFile = (IAstLxxSourceFile) fileManager.findOrCreateDerivedSourceFile(
		                dataModel,
		                locSrcFile,
		                ISymbianNameGenerator.RESOURCE_DIRECTORY_ID, null,
		                extension, AstLxxSourceFile.class,
		                new Object[] { new Integer(langCode) }, false);
	        }
	        
	        // remove unreferenced macros
	        removeUnreferencedStringMacros(lxxSrcFile, table.getStrings(), bundle.getUserGeneratedStringKeys());
	        
	        // define the macros
	        boolean anyMacros = defineStringMacros(lxxSrcFile, table.getStrings(), macroTable.getKeyProvider());
	        
	        if (anyMacros) {
	        	if (locSrcFile.findInclude(lxxSrcFile.getSourceFile()) == null)
	        		locSrcFile.addLxxSourceFile(lxxSrcFile);
	        	fileManager.replaceRssFile(lxxSrcFile);
	            any = true;
	        }
	        
	        emittedLanguages.add(table.getLanguage());
	    }
	    
	    // remove any deleted languages
		IAstLxxSourceFile[] lxxSrcFiles = locSrcFile.getLxxSourceFiles();
		for (int i = 0; i < lxxSrcFiles.length; i++) {
			int langCode = lxxSrcFiles[i].getLanguageCode();
			Language language = new Language(langCode);
			if (!emittedLanguages.contains(language)) {
				locSrcFile.removeLxxSourceFile(lxxSrcFiles[i]);
			}
		}
		
	    if (any) {
	        // only add the #include if we wrote anything
	    	if (rssFile.findInclude(locSrcFile.getSourceFile()) == null)
	    		rssFile.addFileNode(new AstPreprocessorLocIncludeNode(
	    				locSrcFile.getSourceFile().getFileName(), true, locSrcFile));
	    	
	    	fileManager.replaceRssFile(locSrcFile);
	    }
	}

	/**
	 * @param mainFile
	 * @param rssFile
	 */
	private void reuseStringsFromMainFile(IAstRssSourceFile mainFile, IAstRssSourceFile rssFile) {
		IAstPreprocessorLocIncludeNode[] locIncls = (IAstPreprocessorLocIncludeNode[]) mainFile.getFileNodes(IAstPreprocessorLocIncludeNode.class);
		for (int i = 0; i < locIncls.length; i++) {
			ISourceFile sf = locIncls[i].getFile().getSourceFile();
			if (rssFile.findInclude(sf) == null) {
				AstPreprocessorLocIncludeNode cloneIncl = new AstPreprocessorLocIncludeNode(
						locIncls[i].getFilename(), locIncls[i].isUserPath(), locIncls[i].getFile());
				rssFile.addFileNode(cloneIncl);
			}
		}
	}

	/* (non-Javadoc)
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

	    // import strings
	    IAstPreprocessorIncludeDirective[] incls = rssFile.getIncludeFiles();
	    Set<Language> expectedLanguages = new HashSet<Language>();
	    for (Iterator iter = bundle.getLocalizedStringTables().iterator(); iter.hasNext();) {
			ILocalizedStringTable table = (ILocalizedStringTable) iter.next();
			expectedLanguages.add(table.getLanguage());
		}
	    
	    // find (all) the *.loc files and process their language tables
	    for (int i = 0; i < incls.length; i++) {
			if (incls[i].getFile() instanceof IAstLocSourceFile) {
				IAstLocSourceFile locSrcFile = (IAstLocSourceFile) incls[i].getFile();
				IAstLxxSourceFile[] lxxSrcFiles = locSrcFile.getLxxSourceFiles();
				for (int j = 0; j < lxxSrcFiles.length; j++) {
					int langCode = lxxSrcFiles[j].getLanguageCode();
					Language language = new Language(langCode);
					ILocalizedStringTable table = bundle.findLocalizedStringTable(language);
					if (table == null) {
						// do not automatically add new languages
						emitIgnoringAddedLanguageMessage(locSrcFile, language);
						continue;
					}
					importStringMacros(userCommands, changeCommands, dd, lxxSrcFiles[j], language,
							table.getStrings(), bundle.getUserGeneratedStringKeys());
					expectedLanguages.remove(language);
				}

				// do not automatically remove languages
			    for (Iterator iter = expectedLanguages.iterator(); iter.hasNext();) {
					Language language = (Language) iter.next();
					emitIgnoringDeletedLanguageMessage(locSrcFile, language);
				}
			}
		}
	    
	    return new Pair(userCommands.unwrap(), changeCommands.unwrap());
	}

}
