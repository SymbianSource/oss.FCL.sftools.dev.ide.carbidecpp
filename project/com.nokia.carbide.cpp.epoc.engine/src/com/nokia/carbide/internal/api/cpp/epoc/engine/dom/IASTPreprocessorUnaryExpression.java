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
 * A unary expression
 *
 */
public interface IASTPreprocessorUnaryExpression extends IASTPreprocessorExpression {
    /** -expr (value 1) */
    public static final int K_NEGATE = 1;
    /** !expr (value 2) */
    public static final int K_NOT = 2;
    /** ~expr (value 3) */
    public static final int K_INVERT = 3;
    /** (expr) (value 4) */
    public static final int K_PARENTHESIS = 4;
    /** defined expr or defined(expr)
     * (N.B.: may be IAstPreprocessorMacroIdentifierExpression 
     * or IAstPreprocessorUnaryExpression(K_PARENTHESIS) containing it 
     */
    public static final int K_DEFINED = 5;
    /** +expr */
    public static final int K_PLUS = 6;
    /** last value for subclass extensions */
    public static final int K_LAST = K_PLUS;
    
    /** Get the expression operator (K_xxx) */
    public int getOperator();
    
    /** Set the expression operator (K_xxx) */
    public void setOperator(int oper);

    /** Get the operand
     * 
     * @return IAstExpression (never null)
     */
    public IASTPreprocessorExpression getOperand();
    
    /** Set the operand 
     * 
     * @param expr (must not be null)
     */
    public void setOperand(IASTPreprocessorExpression expr);
    
    
}
