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

package com.nokia.carbide.internal.cpp.epoc.engine.dom.bsf;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bsf.IASTBSFFlagStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTKeywordStatement;


public class ASTBSFFlagStatement extends ASTKeywordStatement implements
		IASTBSFFlagStatement {

	/**
	 * @param keyword
	 * @param arguments
	 */
	public ASTBSFFlagStatement(IASTLiteralTextNode keyword) {
		super(keyword);
	}

	/**
	 * @param statement
	 */
	public ASTBSFFlagStatement(ASTBSFFlagStatement statement) {
		super(statement);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTBSFFlagStatement))
			return false;
		return super.equalValue(obj);
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.internal.ASTMMPStatement#copy()
	 */
	public IASTNode copy() {
		return new ASTBSFFlagStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitNode(getKeyword());
		handler.emitNewline();
	}
	
}
