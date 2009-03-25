/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
*
*/
package com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTExpression;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGUnaryConditionExpressionBase;
import com.nokia.cpp.internal.api.utils.core.*;
public abstract class ASTPKGUnaryCondtionExpressionBase extends ASTPKGStatement
		implements IASTPKGUnaryConditionExpressionBase {
	private boolean negation; // NOT()
	private IASTNode expression;

	public ASTPKGUnaryCondtionExpressionBase(boolean negation, IASTNode node) {
		super();
		setNegation(negation);
		setExpression(node);
		dirty = false;
	}

	public ASTPKGUnaryCondtionExpressionBase(
			ASTPKGUnaryCondtionExpressionBase expression) {
		super(expression);
		setNegation(expression.getNegation());
		setExpression((IASTNode) expression.getExpression().copy());
		dirty = expression.dirty;
	}

	public IASTNode getExpression() {
		return expression;
	}

	public boolean getNegation() {
		return negation;
	}

	public void setExpression(IASTNode node) {
		Check.checkArg(node);
		unparent(expression);
		parent(node);
		expression = node;
		fireChanged();
		dirty = true;
	}

	public void setNegation(boolean negation) {
		this.negation = negation;
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

	public IASTNode[] getChildren() {
		return new IASTNode[] { expression };
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((expression == null) ? 0 : expression.hashCode());
		result = prime * result + (negation ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ASTPKGUnaryCondtionExpressionBase other = (ASTPKGUnaryCondtionExpressionBase) obj;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		if (negation != other.negation)
			return false;
		return true;
	}
}
