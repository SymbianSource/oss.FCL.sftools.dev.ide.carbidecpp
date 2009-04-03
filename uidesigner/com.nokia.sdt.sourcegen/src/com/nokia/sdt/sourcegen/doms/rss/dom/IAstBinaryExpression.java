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
 * A binary expression
 * 
 * 
 *
 */
public interface IAstBinaryExpression extends IAstExpression {
    /** expr1+expr2 */
    public static final int K_ADD = 1;
    /** expr1-expr2 */
    public static final int K_SUB = 2;
    /** expr1*expr2 */
    public static final int K_MUL = 3;
    /** expr1/expr2 */
    public static final int K_DIV = 4;
    /** expr1%expr2 */
    public static final int K_MOD = 5;
    /** expr1&expr2 */
    public static final int K_AND = 6;
    /** expr1|expr2 */
    public static final int K_OR = 7;
    /** expr1^expr2 */
    public static final int K_XOR = 8;
    /** expr1[expr2] */
    public static final int K_SUBSCRIPT = 9;
    
    /** last entry for subclasses */
    public static final int K_LAST = 9;
    
    /** Get the operator (K_xxx) */
    public int getOperator();

    /** Set the operator (K_xxx) */
    public void setOperator(int operator);

    /** Get the left-hand side of the expression */
    public IAstExpression getLeftOperand();

    /** Set the left-hand side of the expression */
    public void setLeftOperand(IAstExpression expr);

    /** Get the right-hand side of the expression */
    public IAstExpression getRightOperand();

    /** Set the right-hand side of the expression */
    public void setRightOperand(IAstExpression expr);
}
