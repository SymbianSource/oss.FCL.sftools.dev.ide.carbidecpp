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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPUnknownStatement;


public class ASTMMPUnknownStatement extends ASTMMPKeywordStatement implements
		IASTMMPUnknownStatement {

	private IASTListNode<IASTLiteralTextNode> arguments;

	/**
	 * @param keyword
	 */
	public ASTMMPUnknownStatement(IASTLiteralTextNode keyword, IASTListNode<IASTLiteralTextNode> arguments) {
		super(keyword);
		setArguments(arguments);
		dirty = false;
	}

	/**
	 * @param statement
	 */
	public ASTMMPUnknownStatement(ASTMMPUnknownStatement statement) {
		super(statement);
		setArguments((IASTListNode<IASTLiteralTextNode>) statement.getArguments().copy());
		dirty = statement.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTMMPUnknownStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTMMPUnknownStatement node = (ASTMMPUnknownStatement) obj;
		return node.arguments.equalValue(arguments);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ arguments.hashCode() ^ 0x55555;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTMMPUnknownStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitNode(getKeyword());
		if (arguments != null) {
			handler.emitSpace();
			handler.emitNode(arguments);
		}
		handler.emitNewline();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPUnknownStatement#getArguments()
	 */
	public IASTListNode<IASTLiteralTextNode> getArguments() {
		return arguments;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPUnknownStatement#setArguments(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode)
	 */
	public void setArguments(IASTListNode<IASTLiteralTextNode> arguments) {
		unparent(this.arguments);
		if (arguments != null)
			parent(arguments);
		this.arguments = arguments;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return new IASTNode[] { getKeyword(), arguments };
	}

}
