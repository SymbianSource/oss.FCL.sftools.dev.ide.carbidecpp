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


public class ASTPreprocessorUnknownStatement extends ASTPreprocessorStatement
		implements IASTPreprocessorUnknownStatement {

	private IASTPreprocessorTokenStream unknown;

	/**
	 * @param fileName
	 * @param isUser
	 */
	public ASTPreprocessorUnknownStatement(IASTPreprocessorTokenStream name) {
		setDirective(name);
		dirty = false;
	}

	/**
	 * @param statement
	 */
	public ASTPreprocessorUnknownStatement(ASTPreprocessorUnknownStatement statement) {
		super(statement);
		setDirective((IASTPreprocessorTokenStream) statement.getDirective().copy());
		dirty = statement.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTPreprocessorUnknownStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTPreprocessorUnknownStatement node = (ASTPreprocessorUnknownStatement) obj;
		return node.unknown.equalValue(unknown);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ unknown.hashCode() ^ 4;
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return new IASTNode[] { unknown };
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTPreprocessorUnknownStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitText("#"); //$NON-NLS-1$
		handler.emitNode(unknown);
		handler.emitNewline();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIncludeStatement#getName()
	 */
	public IASTPreprocessorTokenStream getDirective() {
		return unknown;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIncludeStatement#setName(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStream)
	 */
	public void setDirective(IASTPreprocessorTokenStream unknown) {
		Check.checkArg(unknown);
		unparent(this.unknown);
		parent(unknown);
		this.unknown = unknown;
		fireChanged();
		dirty = true;
	}

}
