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

package com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTExpression;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGBinaryConditionExpression;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGConditionExpression;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTPKGBinaryConditionExpression extends ASTPKGStatement implements
		IASTPKGBinaryConditionExpression {
	private int condition; // AND/OR
	private IASTPKGConditionExpression leftExpression;
	private IASTPKGConditionExpression rightExpression;

	public ASTPKGBinaryConditionExpression(boolean isAnd,
			IASTPKGConditionExpression leftNode,
			IASTPKGConditionExpression rightNode) {
		super();
		if (isAnd) {
			setAnd();
		} else {
			setOr();
		}
		setLeftExpression(leftNode);
		setRightExpression(rightNode);
		dirty = false;
	}

	public ASTPKGBinaryConditionExpression(
			ASTPKGBinaryConditionExpression expression) {
		super(expression);
		if (expression.isAnd()) {
			setAnd();
		} else {
			setOr();
		}
		setLeftExpression((IASTPKGConditionExpression) expression
				.getLeftExpression().copy());
		setRightExpression((IASTPKGConditionExpression) expression
				.getRightExpression().copy());
		dirty = expression.dirty;
	}

	public IASTPKGConditionExpression getLeftExpression() {
		return leftExpression;
	}

	public IASTPKGConditionExpression getRightExpression() {
		return rightExpression;
	}

	public boolean isAnd() {
		return condition == AND;
	}

	public boolean isOr() {
		return condition == OR;
	}

	public void setAnd() {
		condition = AND;
		fireChanged();
		dirty = true;
	}

	public void setOr() {
		condition = OR;
		fireChanged();
		dirty = true;
	}

	public void setLeftExpression(IASTPKGConditionExpression node) {
		Check.checkArg(node);
		unparent(leftExpression);
		parent(node);
		leftExpression = node;
		fireChanged();
		dirty = true;
	}

	public void setRightExpression(IASTPKGConditionExpression node) {
		Check.checkArg(node);
		unparent(rightExpression);
		parent(node);
		rightExpression = node;
		fireChanged();
		dirty = true;
	}

	public boolean equalValue(IASTExpression expr) {
		return false;
	}

	public IASTExpression simplify() {
		Check.reportFailure("Not implemented",
				new UnsupportedOperationException());
		return null;
	}

	public IASTNode copy() {
		return new ASTPKGBinaryConditionExpression(this);
	}

	public IASTNode[] getChildren() {
		return new IASTNode[] { leftExpression, rightExpression };
	}

	public void rewrite(IRewriteHandler handler) {
		handler.emitNode(leftExpression);
		if (condition == AND) {
			handler.emitText(" AND"); //$NON-NLS-1$
		} else {
			handler.emitText(" OR"); //$NON-NLS-1$
		}
		handler.emitNewline();
		handler.emitNode(rightExpression);
		handler.emitNewline();
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
		result = prime * result + condition;
		result = prime * result
				+ ((leftExpression == null) ? 0 : leftExpression.hashCode());
		result = prime * result
				+ ((rightExpression == null) ? 0 : rightExpression.hashCode());
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
		final ASTPKGBinaryConditionExpression other = (ASTPKGBinaryConditionExpression) obj;
		if (condition != other.condition)
			return false;
		if (leftExpression == null) {
			if (other.leftExpression != null)
				return false;
		} else if (!leftExpression.equals(other.leftExpression))
			return false;
		if (rightExpression == null) {
			if (other.rightExpression != null)
				return false;
		} else if (!rightExpression.equals(other.rightExpression))
			return false;
		return true;
	}

}
