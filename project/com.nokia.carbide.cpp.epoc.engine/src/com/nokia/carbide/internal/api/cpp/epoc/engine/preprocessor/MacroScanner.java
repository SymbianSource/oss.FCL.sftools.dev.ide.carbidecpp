/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.model.IModelDocumentProvider;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IIncludeFileLocator;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.ITranslationUnit;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.ITranslationUnitProvider;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.ASTPreprocessor;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.MacroDefinition;
import com.nokia.cpp.internal.api.utils.core.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This is a utility class that finds the #defines from a given source
 * file (and #includes) using the EPOC engine preprocessor parser.
 * 
 */
public class MacroScanner {
	public boolean DUMP = false;
	
	private Set<File> includes;
	private List<IASTPreprocessorDefineStatement> macros;
	private List<IDefine> macroDefs;
	private Map<String, IDefine> macroMap;
	private IModelDocumentProvider documentProvider;
	private IIncludeFileLocator locator;
	private ITranslationUnitProvider tuProvider;

	public MacroScanner(IIncludeFileLocator locator, IModelDocumentProvider provider, ITranslationUnitProvider tuProvider) {
		Check.checkArg(provider);
		this.macros = new ArrayList<IASTPreprocessorDefineStatement>();
		this.macroDefs = new ArrayList<IDefine>();
		this.macroMap = new HashMap<String, IDefine>();
		this.includes = new HashSet<File>();
		this.locator = locator;
		this.documentProvider = provider;
		this.tuProvider = tuProvider;
	}
	
	/**
	 * Reset the sets of macros and includes for the next file to scan.
	 *
	 */
	public void reset() {
		macros.clear();
		macroMap.clear();
		macroDefs.clear();
		includes.clear();
	}
	
	
	/**
	 * @return the list of preprocessor-level macro definitions;
	 * NOTE: contains macros later #undef'ed
	 */
	public Collection<IASTPreprocessorDefineStatement> getDefines() {
		return macros;
	}
	/**
	 * @return the list of high-level macro definitions
	 */
	public Collection<IDefine> getMacroDefinitions() {
		return macroDefs;
	}

	/**
	 * Scan the #defines starting at the given file
	 * @param file to scan
	 */
	public void scanFile(File file) {
		if (includes.contains(file))
			return;

		if (DUMP)
			System.out.println("Scanning #defines for " + file); //$NON-NLS-1$
		
		ITranslationUnit tu_ = tuProvider.getTranslationUnit(file, documentProvider);
		if (!(tu_ instanceof IASTTranslationUnit)) {
			EpocEnginePlugin.log(new FileNotFoundException("Failed to scan #defines for " + file)); //$NON-NLS-1$
			return;
		}

		// only add file if it exists, otherwise, its absence makes clients
		// think something has changed
		includes.add(file);

		IASTTranslationUnit tu = (IASTTranslationUnit) tu_;
		for (IASTTopLevelNode node : tu.getNodes()) {
			if (node instanceof IASTPreprocessorDefineStatement) {
				IASTPreprocessorDefineStatement define = (IASTPreprocessorDefineStatement) node;
				macros.add(define);
				IDefine macro = MacroDefinition.createMacroDefinition(define);
				IDefine existing = macroMap.get(macro.getName());
				if (existing != null) {
					macroDefs.remove(existing);
				}
				macroDefs.add(macro);
				macroMap.put(macro.getName(), macro);
			} else if (node instanceof IASTPreprocessorUndefStatement) {
				IASTPreprocessorUndefStatement undef = (IASTPreprocessorUndefStatement) node;
				
				IDefine existing = macroMap.get(undef.getMacroName().getValue());
				if (existing != null) {
					// keep the macros entry
					macroDefs.remove(existing);
					macroMap.remove(existing.getName());
				}
			} else if (node instanceof IASTPreprocessorIncludeStatement) {
				IASTPreprocessorIncludeStatement include = (IASTPreprocessorIncludeStatement) node;
				// be lazy, and don't expand macros
				Pair<Boolean, String> includeStmtInfo = 
					ASTPreprocessor.parseIncludeDirective(include, include.getName().getTokens());
				if (includeStmtInfo == null) {
					if (DUMP)
						EpocEnginePlugin.log(new IllegalArgumentException("Could not parse #include: " + include)); //$NON-NLS-1$
					continue;
				} 
				
				Boolean isUser = includeStmtInfo.first;
				String fileName = includeStmtInfo.second;
									
				handleInclude(file, isUser, fileName);
			}
		}
	}

	/**
	 * Look up the #include and recursively scan its includes.  Don't
	 * scan the same file more than once.
	 * @param info 
	 * @param include
	 */
	private void handleInclude(File current, boolean isUser, String fileName) {

		File incFile = locator.findIncludeFile(fileName, isUser, current.getParentFile());
		if (incFile != null) {
			scanFile(incFile);
		} else {
			// note: not enabled by default because client may explicitly exclude
			// some "noise" directories like epoc32\include
			if (DUMP)
				EpocEnginePlugin.log(new IllegalArgumentException("Could not locate #include: " + fileName)); //$NON-NLS-1$
		}
	}
	
	/**
	 * Get the list of all header files that were scanned (includes the
	 * top level file)
	 * @return non-<code>null</code> array of files
	 */
	public File[] getIncludedFiles() {
		return includes.toArray(new File[includes.size()]);
	}
}
