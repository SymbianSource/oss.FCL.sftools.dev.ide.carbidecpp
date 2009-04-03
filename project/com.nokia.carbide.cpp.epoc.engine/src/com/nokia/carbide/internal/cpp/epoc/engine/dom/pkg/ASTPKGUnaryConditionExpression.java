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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTExpression;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGConditionExpression;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGUnaryConditionExpression;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTPKGUnaryConditionExpression extends
		ASTPKGUnaryCondtionExpressionBase implements
		IASTPKGUnaryConditionExpression {

	public ASTPKGUnaryConditionExpression(boolean negation,
			IASTPKGConditionExpression node) {
		super(negation, node);
	}

	public ASTPKGUnaryConditionExpression(
			ASTPKGUnaryConditionExpression expression) {
		super(expression);
	}

	public IASTPKGConditionExpression getExpression() {
		return (IASTPKGConditionExpression) super.getExpression();
	}

	public void setExpression(IASTPKGConditionExpression expression) {
		Check.checkArg(expression);
		super.setExpression(expression);
	}

	public boolean equalValue(IASTExpression expr) {
		return false;
	}

	public IASTNode copy() {
		return new ASTPKGUnaryConditionExpression(this);
	}

	public void rewrite(IRewriteHandler handler) {
		if (getNegation()) {
			handler.emitText("NOT "); //$NON-NLS-1$
		}
		handler.emitText("("); //$NON-NLS-1$
		handler.emitNode(getExpression());
		handler.emitText(")"); //$NON-NLS-1$
		handler.emitNewline();
	}
}
