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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;


public interface IASTTestStatement extends IASTStatement {
	IASTExpression getExpression();
	void setExpression(IASTExpression expression);
}
