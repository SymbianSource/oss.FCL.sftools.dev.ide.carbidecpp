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

package com.nokia.carbide.internal.cpp.epoc.engine.dom.mmp;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPSingleArgumentStatement;


public class ASTMMPSingleArgumentStatement extends ASTMMPKeywordStatement implements
		IASTMMPSingleArgumentStatement {

	private IASTLiteralTextNode argument;


	/**
	 * @param keyword
	 * @param arguments
	 */
	public ASTMMPSingleArgumentStatement(IASTLiteralTextNode keyword, IASTLiteralTextNode argument) {
		super(keyword);
		setArgument(argument);
		dirty = false;
	}

	/**
	 * @param statement
	 */
	public ASTMMPSingleArgumentStatement(ASTMMPSingleArgumentStatement statement) {
		super(statement);
		setArgument((IASTLiteralTextNode) statement.getArgument().copy());
		dirty = statement.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTMMPSingleArgumentStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTMMPSingleArgumentStatement node = (ASTMMPSingleArgumentStatement) obj;
		return node.argument.equalValue(argument);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ argument.hashCode() ^ -309982;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPStatement#getArgumentText()
	 */
	public IASTLiteralTextNode getArgument() {
		return argument;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPStatement#setArgumentText(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode)
	 */
	public void setArgument(IASTLiteralTextNode argument) {
		unparent(this.argument);
		if (argument != null)
			parent(argument);
		this.argument = argument;
		fireChanged();
		dirty = true;
	}

	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.internal.ASTMMPStatement#copy()
	 */
	public IASTNode copy() {
		return new ASTMMPSingleArgumentStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitNode(getKeyword());
		handler.emitSpace();
		handler.emitNode(argument);
		handler.emitNewline();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.internal.ASTMMPKeywordStatement#getChildren()
	 */
	@Override
	public IASTNode[] getChildren() {
		return new IASTNode[] { getKeyword(), argument };
	}
}
