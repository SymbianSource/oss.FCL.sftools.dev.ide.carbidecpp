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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGConditionPrimitiveExpression;



/*
 * This is practically a unary node except the surrounding parenthesis and not
 * able to have negation
 */
public class ASTPKGConditionPrimitiveExpression extends
		ASTPKGUnaryCondtionExpressionBase implements
		IASTPKGConditionPrimitiveExpression {

	public ASTPKGConditionPrimitiveExpression(IASTNode node) {
		super(false, node);
	}

	public ASTPKGConditionPrimitiveExpression(
			ASTPKGConditionPrimitiveExpression expression) {
		super(expression);
	}

	public void rewrite(IRewriteHandler handler) {
		handler.emitNode(getExpression());
		handler.emitNewline();
	}

	// so this node is not a unary node
	public IASTNode copy() {
		return new ASTPKGConditionPrimitiveExpression(this);
	}

}
