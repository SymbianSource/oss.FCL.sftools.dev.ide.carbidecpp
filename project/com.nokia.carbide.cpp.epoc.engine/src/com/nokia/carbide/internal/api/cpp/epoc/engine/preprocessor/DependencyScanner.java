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
import com.nokia.carbide.cpp.epoc.engine.preprocessor.ITranslationUnitProvider;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.ASTPreprocessor;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.CoreException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a utility class that quickly finds the #includes from a given source
 * file, without regard for macros or #ifs. It only records actual existing
 * files and ignores macro expansion (e.g. #define MYFILE ... / #include
 * MYFILE).  
 * <p>
 * The instance maintains a cache of #includes reachable from any file,
 * but provides per-file include lists.
 * 
 */
public class DependencyScanner {
	public boolean DUMP = false;
	
	private static final Pattern INCLUDE_REGEX = Pattern.compile("^\\s*#\\s*include\\s*(<|\")(.*)(?:>|\")", Pattern.MULTILINE); //$NON-NLS-1$
	
	private Map<File, Pair<Set<File>, Set<File>>> fileToIncludesMap;
	private Set<File> userIncludes;
	private Set<File> systemIncludes;
	private ITranslationUnitProvider tuProvider;
	private IIncludeFileLocator locator;

	private final IModelDocumentProvider documentProvider;

	public DependencyScanner(IIncludeFileLocator locator, ITranslationUnitProvider tuProvider, IModelDocumentProvider documentProvider) {
		Check.checkArg(tuProvider);
		Check.checkArg(documentProvider);
		this.fileToIncludesMap = new HashMap<File, Pair<Set<File>,Set<File>>>();
		this.userIncludes = new LinkedHashSet<File>();
		this.systemIncludes = new LinkedHashSet<File>();
		this.locator = locator;
		this.tuProvider = tuProvider; 
		this.documentProvider = documentProvider;
	}
	
	/**
	 * Reset the sets of user and system includes for the next file to scan.
	 *
	 */
	public void reset() {
		userIncludes.clear();
		systemIncludes.clear();
	}
	
	
	/**
	 * @return the list of system includes.  All entries exist on disk.
	 */
	public Collection<File> getSystemIncludes() {
		return systemIncludes;
	}

	/**
	 * @return the set of user includes.  All entries exist on disk.
	 */
	public Collection<File> getUserIncludes() {
		return userIncludes;
	}

	/**
	 * Scan the #includes starting at the given file, adding
	 * user and system entries to internal sets (see #getUserIncludes()
	 * and #getSystemIncludes()). 
	 * @param file start position
	 */
	public void scanFile(File file) {
		Pair<Set<File>, Set<File>> info = fileToIncludesMap.get(file);
		if (info == null) {
			if (DUMP)
				System.out.println("Scanning #includes for " + file); //$NON-NLS-1$
			info = scanFileViaRegex(file);
		}
		userIncludes.addAll(info.first);
		systemIncludes.addAll(info.second);
	}

	/**
	 * Get the #includes for a file using the DOM parser.
	 * @param file
	 */
	Pair<Set<File>, Set<File>> scanFileViaTu(File file) {
		Pair<Set<File>, Set<File>> info = new Pair(new LinkedHashSet<File>(), new LinkedHashSet<File>());
		// add early to avoid infinite recursion
		fileToIncludesMap.put(file, info);

		IASTTranslationUnit tu = (IASTTranslationUnit) tuProvider.getTranslationUnit(
				file, documentProvider);
		if (tu == null) {
			EpocEnginePlugin.log(new FileNotFoundException("Failed to scan #includes for " + file)); //$NON-NLS-1$
			return info;
		}
		
		
		for (IASTTopLevelNode node : tu.getNodes()) {
			if (node instanceof IASTPreprocessorIncludeStatement) {
				IASTPreprocessorIncludeStatement include = (IASTPreprocessorIncludeStatement) node;
				// be lazy, and don't expand macros
				Pair<Boolean, String> includeStmtInfo = 
					ASTPreprocessor.parseIncludeDirective(include, include.getName().getTokens());
				if (info == null) {
					if (DUMP)
						EpocEnginePlugin.log(new IllegalArgumentException("Could not parse #include: " + include)); //$NON-NLS-1$
					continue;
				} 
				
				Boolean isUser = includeStmtInfo.first;
				String fileName = includeStmtInfo.second;
									
				handleInclude(info, file, isUser, fileName);
			}
		}
		return info;
	}

	/**
	 * Get the #includes for a file using regular expressions.
	 * @param file
	 */
	Pair<Set<File>, Set<File>> scanFileViaRegex(File file) {
		Pair<Set<File>, Set<File>> info = new Pair(new LinkedHashSet<File>(), new LinkedHashSet<File>());
		// add early to avoid infinite recursion
		fileToIncludesMap.put(file, info);

		String fileText;
		try {
			fileText = new String(FileUtils.readFileContents(file, null));
		} catch (CoreException e) {
			if (DUMP)
				EpocEnginePlugin.log(e);
			return info;
		}
		
		Matcher matcher = INCLUDE_REGEX.matcher(fileText);
		while (matcher.find()) {
			// be lazy, and don't expand macros
			boolean isUser = matcher.group(1).equals("\""); //$NON-NLS-1$
			String fileName = matcher.group(2);
									
			handleInclude(info, file, isUser, fileName);
		}
		
		return info;
	}
	/**
	 * Look up the #include and recursively scan its includes.  Don't
	 * scan the same file more than once.
	 * @param info 
	 * @param include
	 */
	private void handleInclude(Pair<Set<File>, Set<File>> includeInfo, File current, boolean isUser, String fileName) {

		File incFile = locator.findIncludeFile(fileName, isUser, current.getParentFile());
		if (incFile != null) {
			if (!includeInfo.first.contains(incFile)
			&& !includeInfo.second.contains(incFile)) {
				Set<File> set = isUser ? includeInfo.first : includeInfo.second;
				set.add(incFile);
				scanFile(incFile);
			}
		} else {
			// note: not enabled by default because client may explicitly exclude
			// some "noise" directories like epoc32\include
			if (DUMP)
				EpocEnginePlugin.log(new IllegalArgumentException("Could not locate #include: " + fileName)); //$NON-NLS-1$
		}
	}
}
