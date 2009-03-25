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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom;


/**
 * A preprocessor literal.  These have integral type and also include
 * the constants "false" and "true" which evaluate to 0 and 1 respectively.
 *
 */
public interface IASTPreprocessorLiteralExpression extends 
		IASTPreprocessorExpression, IASTLiteralTextNode {
    public boolean getBooleanValue();
    public void setValue(boolean value);
}
