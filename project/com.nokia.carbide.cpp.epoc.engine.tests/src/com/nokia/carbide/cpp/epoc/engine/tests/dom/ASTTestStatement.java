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

package com.nokia.carbide.cpp.epoc.engine.tests.dom;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTExpression;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTNode;


public class ASTTestStatement extends ASTNode implements IASTTestStatement {

	private IASTExpression expr;
	private IASTTopLevelNode next;

	public ASTTestStatement(IASTExpression expr) {
		setExpression(expr);
		dirty = false;
	}
	
	
	/**
	 * @param statement
	 */
	public ASTTestStatement(ASTTestStatement statement) {
		super(statement);
		setExpression((IASTExpression) statement.getExpression().copy());
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement#getKeywordName()
	 */
	public String getKeywordName() {
		return null;
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.IASTTestStatement#getExpression()
	 */
	public IASTExpression getExpression() {
		return expr;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.IASTTestStatement#setExpression(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTExpression)
	 */
	public void setExpression(IASTExpression expression) {
		unparent(this.expr);
		parent(expression);
		this.expr = expression;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return new IASTNode[] { expr };
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTTestStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitText("[[[");
		handler.emitNode(expr);
		handler.emitText("]]]");
		handler.emitNewline();
	}
	
	public IASTTopLevelNode getNext() {
		return next;
	}
	
	public void setNext(IASTTopLevelNode node) {
		this.next = node;
	}

}
