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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.DocumentTokenLocation;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.Token;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.TokenManager;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;


public abstract class BaseTokenManager extends BaseTokenizer implements TokenManager {

	protected IDocument document;
	protected IPath path;
	
	/**
	 * Create a token manager using a document which is guaranteed
	 * not to change over time.
	 * @param document
	 * @param path
	 */
	public BaseTokenManager(final IDocument document, final IPath path) {
		super(new DocumentTokenLocation(document, path),
				document.get().toCharArray());
		this.document = document;
		this.path = path;
	}
	
	protected Token convertToken(IToken token, int type) {
		return new ASTToken(token, type);
	}

}
