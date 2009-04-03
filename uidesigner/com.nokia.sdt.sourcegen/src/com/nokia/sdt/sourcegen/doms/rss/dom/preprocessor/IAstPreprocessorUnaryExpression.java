/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor;

import com.nokia.sdt.sourcegen.doms.rss.dom.IAstUnaryExpression;

/**
 * A unary expression
 * 
 * 
 *
 */
public interface IAstPreprocessorUnaryExpression extends IAstPreprocessorExpression, IAstUnaryExpression {
    /** defined expr or defined(expr)
     * (N.B.: may be IAstPreprocessorMacroIdentifierExpression 
     * or IAstPreprocessorUnaryExpression(K_PARENTHESIS) containing it 
     */
    public static final int K_DEFINED = IAstUnaryExpression.K_LAST+1;
    /** +expr */
    public static final int K_PLUS = IAstUnaryExpression.K_LAST+2;
    /** last value for subclass extensions */
    public static final int K_LAST = K_PLUS;
    
}
