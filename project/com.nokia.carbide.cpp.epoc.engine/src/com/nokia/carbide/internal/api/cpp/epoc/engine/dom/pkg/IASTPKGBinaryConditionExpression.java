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
*/

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;

public interface IASTPKGBinaryConditionExpression extends
		IASTPKGConditionExpression {
	final static int AND = 1;
	final static int OR = 2;

	boolean isAnd();

	boolean isOr();

	void setAnd();

	void setOr();

	IASTNode getLeftExpression();

	IASTNode getRightExpression();

	void setLeftExpression(IASTPKGConditionExpression expression);

	void setRightExpression(IASTPKGConditionExpression expression);
}
