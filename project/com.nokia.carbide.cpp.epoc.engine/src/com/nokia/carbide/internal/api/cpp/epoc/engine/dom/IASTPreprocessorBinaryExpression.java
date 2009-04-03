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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom;


/**
 * A binary expression used in the preprocessor
 *
 */
public interface IASTPreprocessorBinaryExpression extends IASTPreprocessorExpression {
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
    /** expr1&&expr2 */
    public static final int K_LOG_AND = 10;
    /** expr1||expr2 */
    public static final int K_LOG_OR = 11;

    /** expr1==expr2 */
    public static final int K_EQUALS = 12;
    /** expr1!=expr2 */
    public static final int K_NOT_EQUALS = 13;
    /** expr1>expr2 */
    public static final int K_GREATER_THAN = 14;
    /** expr1<expr2 */
    public static final int K_LESS_THAN = 15;
    /** expr1>=expr2 */
    public static final int K_GREATER_THAN_OR_EQUALS = 16;
    /** expr1<=expr2 */
    public static final int K_LESS_THAN_OR_EQUALS = 17;

    /** expr1>>expr2 */
    public static final int K_SHR = 18;
    /** expr1<<expr2 */
    public static final int K_SHL = 19;
    
    /** last value for subclass extensions */
    public static final int K_LAST = K_LESS_THAN_OR_EQUALS;
    
    /** Get the operator (K_xxx) */
    public int getOperator();

    /** Set the operator (K_xxx) */
    public void setOperator(int operator);

    /** Get the left-hand side of the expression */
    public IASTPreprocessorExpression getLeftOperand();

    /** Set the left-hand side of the expression */
    public void setLeftOperand(IASTPreprocessorExpression expr);

    /** Get the right-hand side of the expression */
    public IASTPreprocessorExpression getRightOperand();

    /** Set the right-hand side of the expression */
    public void setRightOperand(IASTPreprocessorExpression expr);
    
}
