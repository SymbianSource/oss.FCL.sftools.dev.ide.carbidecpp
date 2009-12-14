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

package com.nokia.carbide.cpp.epoc.engine.tests;

import com.nokia.carbide.cpp.epoc.engine.DocumentFactory;
import com.nokia.carbide.cpp.epoc.engine.model.IModelDocumentProvider;
import com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IIncludeFileLocator;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.ITranslationUnit;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.ITranslationUnitProvider;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.IDocumentParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;
import com.nokia.cpp.internal.api.utils.core.HostOS;
import com.nokia.cpp.internal.api.utils.core.PathUtils;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Testing impl that does not cache TUs
 *
 */
public abstract class ParserConfigurationBase implements IViewParserConfiguration {
	private Map<String, String> fs;
	private ITranslationUnitProvider tuProvider;
	public IIncludeFileLocator fileLocator;
	public IPath projectPath;
	public boolean failOnFileNotFound = true;
	public IModelDocumentProvider modelDocumentProvider;
	public ParserConfigurationBase(IPath projectPath) {
		this.projectPath = projectPath;
		fs = new HashMap<String, String>();
		
		// uncaching impl
		modelDocumentProvider = new IModelDocumentProvider() {
		
			public IDocument getDocument(final File file) {
				final String realpath[] = new String[1];
				realpath[0] = file.getName();
				String contents = fs.get(realpath[0]);
				if (contents == null) {
					realpath[0] = file.toString();
					contents = fs.get(realpath[0]);
				}
				if (contents == null) {
					realpath[0] = file.getAbsolutePath().toLowerCase();
					contents = fs.get(realpath[0]);
				}
				if (contents == null) {
					return null;
				}
				
				final IDocument document = DocumentFactory.createDocument(contents);
				document.addDocumentListener(new IDocumentListener() {

					public void documentAboutToBeChanged(DocumentEvent event) {
					}

					public void documentChanged(DocumentEvent event) {
						fs.put(realpath[0], document.get());
					}
					
				});
				return document;
			}
			
		};
		
		tuProvider = new ITranslationUnitProvider() {

			public ITranslationUnit getTranslationUnit(File file,
					IModelDocumentProvider modelDocumentProvider) {
				IDocumentParser preparser = ParserFactory.createPreParser();
				IDocument document = modelDocumentProvider.getDocument(file);
				if (document == null)
					return null;
				return preparser.parse(new Path(file.getAbsolutePath()), document);
			}
			
		};
		// this locator does not intend to replicate normal search semantics.
		// It will try extra hard to find files in other directories, as if
		// all the directories of registered files were on the search path.
		fileLocator = new IIncludeFileLocator() {
		
			public File findIncludeFile(String file, boolean isUser, File currentDir) {
				if (HostOS.IS_UNIX) {
					file = PathUtils.convertPathToUnix(file);
				}
				
				if (fs.containsKey(file))
					return new File(file);
				
				String fullPath = null;
				if (new Path(file).isAbsolute()) {
					fullPath = new File(file).getAbsolutePath();
				} else if (currentDir != null) {
					fullPath = new Path(currentDir.getAbsolutePath()).append(file).toOSString();
				}
				if (fullPath != null && fs.containsKey(fullPath))
					return new File(fullPath);
				
				// try to match filenames
				file = new Path(file).lastSegment();
				for (String path : fs.keySet()) {
					if (new Path(path).lastSegment().equalsIgnoreCase(file)) {
						return new File(path);
					}
				}
				
				return null;
			}
			
			/* (non-Javadoc)
			 * @see com.nokia.carbide.cpp.epoc.engine.preprocessor.IIncludeFileLocator#getSystemPaths()
			 */
			public File[] getSystemPaths() {
				return new File[0];
			}
			/* (non-Javadoc)
			 * @see com.nokia.carbide.cpp.epoc.engine.preprocessor.IIncludeFileLocator#getUserPaths()
			 */
			public File[] getUserPaths() {
				return new File[0];
			}
		};
	}
	
	public Map<String, String> getFilesystem() {
		return fs;
	}
	abstract protected IASTTranslationUnit parse(IPath path, IDocument document);
	
	public ITranslationUnitProvider getTranslationUnitProvider() {
		return tuProvider;
	}

	public IIncludeFileLocator getIncludeFileLocator() {
		return fileLocator;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration#getProjectLocation()
	 */
	public IPath getProjectLocation() {
		return projectPath;
	}

	public IModelDocumentProvider getModelDocumentProvider() {
		return modelDocumentProvider;
	}

}
