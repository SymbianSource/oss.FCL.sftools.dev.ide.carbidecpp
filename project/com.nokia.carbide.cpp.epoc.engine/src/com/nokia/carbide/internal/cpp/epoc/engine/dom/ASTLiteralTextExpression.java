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

public class ASTLiteralTextExpression extends ASTLiteralTextNode implements
		IASTLiteralTextExpression {

	public ASTLiteralTextExpression(EStyle style) {
		super(style);
	}
	
	public ASTLiteralTextExpression(EStyle style, String value) {
		super(style, value);
	}
	
	/**
	 * @param expression
	 */
	public ASTLiteralTextExpression(ASTLiteralTextExpression expression) {
		super(expression);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTLiteralTextExpression(this);
	}

	public IASTExpression simplify() {
		return this;
	}

	public boolean equalValue(IASTExpression expr) {
		return expr instanceof IASTLiteralTextExpression
			&& ((IASTLiteralTextExpression) expr).getValue().equals(getValue());
	}
}
