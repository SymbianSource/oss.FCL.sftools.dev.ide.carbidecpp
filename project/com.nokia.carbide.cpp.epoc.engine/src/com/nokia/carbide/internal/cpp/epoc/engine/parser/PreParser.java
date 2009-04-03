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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.PreParserCore;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

/**
 * A parser for preprocessor-level text.  This identifies directives
 * and parses them only as far as necessary (i.e., #if/#elif/#define/#include
 * have token streams, since these can be expanded by macros later).  
 * Anything not a directive is a token stream (or a text line for comments).
 *
 */
public class PreParser implements IDocumentParser {

	protected boolean hadErrors;
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.parser.IParser#parse(org.eclipse.core.runtime.IPath, org.eclipse.jface.text.IDocument)
	 */
	public IASTTranslationUnit parse(IPath path, IDocument document) {
		PreTokenManager tokenManager = new PreTokenManager(document, path);
		PreParserCore parser = new PreParserCore(tokenManager);
		IASTTranslationUnit tu = parser.parse();
		tu.setMainDocument(document);
		tu.setMainLocation(path);
	
		// give every node an id
		int idx = 0;
		IASTTopLevelNode prev = null;
		for (IASTTopLevelNode node : tu.getNodes()) {
			node.setId(new Integer(idx++));
			if (prev != null) {
				prev.setNext(node);
			}
			prev = node;
		}
		
		hadErrors = parser.hadErrors;
		
		tu.setDirtyTree(false);
		return tu;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.parser.IParser#hadErrors()
	 */
	public boolean hadErrors() {
		return hadErrors;
	}
}
