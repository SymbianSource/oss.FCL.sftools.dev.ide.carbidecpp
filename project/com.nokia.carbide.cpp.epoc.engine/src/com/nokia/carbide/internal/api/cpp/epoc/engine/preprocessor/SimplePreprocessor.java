/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.carbide.cpp.epoc.engine.DocumentFactory;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.IDocumentParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.ASTPreprocessor;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.PreprocessedTranslationUnitTokenIterator;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.text.BadLocationException;

import java.util.NoSuchElementException;

/**
 * Simple preprocessor using EPOC engine preprocessor
 *
 */
public class SimplePreprocessor {
	/**
	 * Preprocess the file at the given path using the given view configuration, and return the
	 * results as a string.
	 */
	public String preprocess(IPath filesystemPath, IViewConfiguration viewConfiguration) throws CoreException {
		StringBuilder builder = new StringBuilder();
		IViewParserConfiguration parserConfig = viewConfiguration.getViewParserConfiguration();
		IPreprocessor preprocessor = new ASTPreprocessor(parserConfig.getTranslationUnitProvider(),
				parserConfig.getIncludeFileLocator(), parserConfig.getModelDocumentProvider());
		
		// read main file
		char[] chars = FileUtils.readFileContents(filesystemPath.toFile(), null);
		IDocumentParser preParser = ParserFactory.createPreParser();
		IASTTranslationUnit ppTu = preParser.parse(filesystemPath, DocumentFactory.createDocument(new String(chars)));
		
		// preprocess and get a token iterator
		IPreprocessorResults results = preprocessor.preprocess(ppTu,
				viewConfiguration.getViewFilter(),
				viewConfiguration.getMacros());
		PreprocessedTranslationUnitTokenIterator iter =
			new PreprocessedTranslationUnitTokenIterator(results.getFilteredTranslationUnit());
		
		// go through the tokens and append them, marking file changes
		IPath lastFile = null;
		while (true) {
			IToken next;
			try {
				next = iter.next();
			} catch (NoSuchElementException e) {
				break;
			}
			if (next.getLocation() instanceof IDocumentTokenLocation) {
				IDocumentTokenLocation documentTokenLocation = (IDocumentTokenLocation) next.getLocation();
				if (lastFile == null || !lastFile.equals(documentTokenLocation.getPath())) {
					int line;
					try {
						line = documentTokenLocation.getDocument().getLineOfOffset(next.getOffset()) + 1;
						builder.append("\n#line " + line + " " + documentTokenLocation.getPath().toOSString() + "\n");
					} catch (BadLocationException e) {
					}
					lastFile = documentTokenLocation.getPath();
				}
			}
			if (next.followsSpace())
				builder.append(' ');
			builder.append(next.getText());
		}
		
		return builder.toString();
	}
}
