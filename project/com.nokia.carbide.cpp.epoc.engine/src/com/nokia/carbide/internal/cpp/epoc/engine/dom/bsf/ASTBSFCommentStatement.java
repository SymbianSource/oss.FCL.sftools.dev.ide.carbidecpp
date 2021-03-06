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

package com.nokia.carbide.internal.cpp.epoc.engine.dom.bsf;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStream;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bsf.IASTBSFCommentStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorTokenStreamStatement;


public class ASTBSFCommentStatement extends ASTPreprocessorTokenStreamStatement
		implements IASTBSFCommentStatement {

	/**
	 * @param tokenStream
	 */
	public ASTBSFCommentStatement(IASTPreprocessorTokenStream tokenStream) {
		super(tokenStream);
	}

	/**
	 * @param node
	 */
	public ASTBSFCommentStatement(ASTBSFCommentStatement node) {
		super(node);
	}

}
