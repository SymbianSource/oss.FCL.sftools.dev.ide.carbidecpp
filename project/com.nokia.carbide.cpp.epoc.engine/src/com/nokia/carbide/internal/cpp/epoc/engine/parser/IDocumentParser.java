/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.parser;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

/**
 * Interface to a simple parser that works directly on text and produces
 * a translation unit.  
 *
 */
public interface IDocumentParser {
	/**
	 * Parse the given document and return a translation unit
	 * in the given language
	 * @param path full path 
	 * @param document text for the given file
	 * @return new translation unit whose nodes reference
	 * source regions in the given document
	 */
	IASTTranslationUnit parse(IPath path, IDocument document);
	
	/**
	 * After a parse or reinterpret, tell if errors detected (problems
	 * indicated by IASTProblem nodes)
	 * @return
	 */
	boolean hadErrors();
}
