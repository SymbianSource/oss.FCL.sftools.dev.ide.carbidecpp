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

package com.nokia.sdt.sourcegen.doms.rss.dom;

/**
 * An expression with a single operator.
 * 
 * 
 *
 */
public interface IAstUnaryExpression extends IAstExpression {
    /** -expr (value 1) */
    public static final int K_NEGATE = 1;
    /** !expr (value 2) */
    public static final int K_NOT = 2;
    /** ~expr (value 3) */
    public static final int K_INVERT = 3;
    /** (expr) (value 4) */
    public static final int K_PARENTHESIS = 4;
    /** Last value for subclass extensions */
    public static final int K_LAST = K_PARENTHESIS;
    
    /** Get the expression operator (K_xxx) */
    public int getOperator();
    
    /** Set the expression operator (K_xxx) */
    public void setOperator(int oper);

    /** Get the operand
     * 
     * @return IAstExpression (never null)
     */
    public IAstExpression getOperand();
    
    /** Set the operand 
     * 
     * @param expr (must not be null)
     */
    public void setOperand(IAstExpression expr);
}
