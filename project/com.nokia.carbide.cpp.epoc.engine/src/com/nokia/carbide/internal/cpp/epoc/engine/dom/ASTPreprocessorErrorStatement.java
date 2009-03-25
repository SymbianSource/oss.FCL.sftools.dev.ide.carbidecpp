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

package com.nokia.carbide.internal.cpp.epoc.engine.dom;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTPreprocessorErrorStatement extends ASTPreprocessorStatement
		implements IASTPreprocessorErrorStatement {

	private IASTPreprocessorTokenStream error;

	/**
	 * @param fileName
	 * @param isUser
	 */
	public ASTPreprocessorErrorStatement(IASTPreprocessorTokenStream name) {
		setError(name);
		dirty = false;
	}

	/**
	 * @param statement
	 */
	public ASTPreprocessorErrorStatement(ASTPreprocessorErrorStatement statement) {
		super(statement);
		setError((IASTPreprocessorTokenStream) statement.getError().copy());
		dirty = statement.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTPreprocessorErrorStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTPreprocessorErrorStatement node = (ASTPreprocessorErrorStatement) obj;
		return node.error.equalValue(error);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ error.hashCode() ^ 4;
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return new IASTNode[] { error };
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTPreprocessorErrorStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitText("#error"); //$NON-NLS-1$
		if (error.getTokens().size() > 0) {
			handler.emitSpace();
			handler.emitNode(error);
		}
		handler.emitNewline();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIncludeStatement#getName()
	 */
	public IASTPreprocessorTokenStream getError() {
		return error;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIncludeStatement#setName(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStream)
	 */
	public void setError(IASTPreprocessorTokenStream error) {
		Check.checkArg(error);
		unparent(this.error);
		parent(error);
		this.error = error;
		fireChanged();
		dirty = true;
	}

}
