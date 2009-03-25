/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGConditionExpression;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGIfElseifContainer;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTPKGIfElseifContainer extends ASTPKGStatement implements
		IASTPKGIfElseifContainer {
	IASTPKGConditionExpression condition;
	IASTListNode<IASTPKGStatement> statements;

	public ASTPKGIfElseifContainer(IASTPKGConditionExpression condition,
			IASTListNode<IASTPKGStatement> statements) {
		super();
		setCondition(condition);
		setStatements(statements);
		dirty = false;
	}

	public ASTPKGIfElseifContainer(ASTPKGIfElseifContainer statement) {
		super(statement);
		setCondition((IASTPKGConditionExpression) statement.getCondition()
				.copy());
		setStatements((IASTListNode<IASTPKGStatement>) statement
				.getStatements().copy());
		dirty = statement.dirty;
	}

	public IASTListNode<IASTPKGStatement> getStatements() {
		return statements;
	}

	public void setStatements(IASTListNode<IASTPKGStatement> statements) {
		Check.checkArg(statements);
		unparent(this.statements);
		parent(statements);
		this.statements = statements;
		fireChanged();
		dirty = true;
	}

	public IASTPKGConditionExpression getCondition() {
		return condition;
	}

	public void setCondition(IASTPKGConditionExpression condition) {
		Check.checkArg(condition);
		unparent(this.condition);
		parent(condition);
		this.condition = condition;
		fireChanged();
		dirty = true;
	}

	public IASTNode copy() {
		return new ASTPKGIfElseifContainer(this);
	}

	public IASTNode[] getChildren() {
		return makeChildListWith(condition, statements);
	}

	public void rewrite(IRewriteHandler handler) {
		handler.emitNode(statements);
		// statement is a EOL separated list, need new line at the end
		handler.emitNewline();
	}

	protected IASTNode[] makeChildListWith(IASTNode other, IASTNode other2) {
		IASTNode[] kids = new IASTNode[2];
		int idx = 0;
		if (other != null)
			kids[idx++] = other;
		if (other2 != null)
			kids[idx++] = other2;
		if (idx == 2)
			return kids;

		IASTNode[] smallerKids = new IASTNode[idx];
		System.arraycopy(kids, 0, smallerKids, 0, idx);
		return smallerKids;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((condition == null) ? 0 : condition.hashCode());
		result = prime * result
				+ ((statements == null) ? 0 : statements.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ASTPKGIfElseifContainer other = (ASTPKGIfElseifContainer) obj;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (statements == null) {
			if (other.statements != null)
				return false;
		} else if (!statements.equals(other.statements))
			return false;
		return true;
	}

}
