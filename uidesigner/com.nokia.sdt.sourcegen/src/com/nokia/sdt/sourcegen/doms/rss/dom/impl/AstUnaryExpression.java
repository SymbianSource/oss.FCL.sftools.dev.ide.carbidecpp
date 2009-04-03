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

package com.nokia.sdt.sourcegen.doms.rss.dom.impl;

import com.nokia.sdt.sourcegen.ISourceFormatter;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public class AstUnaryExpression extends AstExpression implements
        IAstUnaryExpression {

    protected IAstExpression operand;
    protected int operator;

    /** Create a unary expression
     * 
     * @param operator one of K_xxx
     * @param operand must not be null
     */
    public AstUnaryExpression(int operator, IAstExpression operand) {
        super();
        setOperator(operator);
        setOperand(operand);
        dirty = false;
    }

     /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        return new IAstNode[] { operand };
    }

    /*
     * (non-Javadoc)
     *  
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
     public IAstNode[] getReferencedNodes() {
         return getChildren();
     }

     protected String getOperatorName() {
         String opString = "???"; //$NON-NLS-1$
         switch (operator) {
         case K_NEGATE:
             opString = "-"; //$NON-NLS-1$
             break;
         case K_NOT:
             opString = "!"; //$NON-NLS-1$
             break;
         case K_INVERT:
             opString = "~"; //$NON-NLS-1$
             break;
         case K_PARENTHESIS:
             opString = "("; //$NON-NLS-1$
             break;
         default:
             Check.checkState(false);
         }
         return opString;
     
     }
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#constructText()
     */
    public Object[] getTextSegments() {
        if (operator == K_PARENTHESIS)
            return new Object[] { 
        		"(", //$NON-NLS-1$
        		ISourceFormatter.SEGMENT_FORMATTING_SPACE,
        		operand, 
        		ISourceFormatter.SEGMENT_FORMATTING_SPACE,
        		")" //$NON-NLS-1$ 
        		};

        String opString = getOperatorName();
        return new Object[] { opString, operand };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstUnaryExpression#getOperator()
     */
    public int getOperator() {
        return operator;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstUnaryExpression#setOperator(int)
     */
    public void setOperator(int oper) {
        Check.checkArg(oper >= 0 && oper <= K_LAST);
        this.operator = oper;
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstUnaryExpression#getOperand()
     */
    public IAstExpression getOperand() {
        return operand;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstUnaryExpression#setOperand(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public void setOperand(IAstExpression expr) {
        Check.checkArg(expr);
        if (this.operand != null)
            this.operand.setParent(null);
        this.operand = expr;
        expr.setParent(this);
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression#simplify()
     */
    public IAstExpression simplify() {
        
        IAstExpression expr = operand.simplify();

        if (operator == K_PARENTHESIS)
            return expr;

        if ((expr instanceof IAstLiteralExpression)) {
            try {
                IAstLiteralExpression lit = (IAstLiteralExpression) expr;
                
                switch (lit.getKind()) {
                case IAstLiteralExpression.K_STRING:
                case IAstLiteralExpression.K_CHAR:
                    break;
                    
                case IAstLiteralExpression.K_FLOAT:
                    if (operator == K_NEGATE) {
                        double val = Double.valueOf(lit.getValue()).doubleValue();
                        val = -val;
                        IAstLiteralExpression lex = new AstLiteralExpression( 
                                IAstLiteralExpression.K_FLOAT, Double.toString(val));
                        lex.setParent(getParent());
                        return lex;
                    }
                    break;
            
                case IAstLiteralExpression.K_INTEGER:
                    int val = Integer.valueOf(lit.getValue()).intValue();
                    
                    switch (operator) {
                    case K_NEGATE:
                        val = -val;
                        break;
                    case K_NOT:
                        val = val != 0 ? 0 : 1;
                        break;
                    case K_INVERT:
                        val = ~val;
                        break;
                    }
    
                    IAstLiteralExpression lex = new AstLiteralExpression( 
                            lit.getKind(), Integer.toString(val));
                    lex.setParent(getParent());
                    return lex;
                }
            } catch (NumberFormatException e) {
                // guess that literal wasn't really valid
            }
        }
        
        if (!expr.equalValue(operand)) {
            expr.setParent(null);
            IAstUnaryExpression un = new AstUnaryExpression(operator, expr);
            un.setParent(getParent());
            return un;
        }
        else
            return this;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression#equalValue(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public boolean equalValue(IAstExpression expr) {
        return (expr instanceof IAstUnaryExpression)
        && ((IAstUnaryExpression) expr).getOperator() == getOperator()
        && ((IAstUnaryExpression) expr).getOperand().equalValue(getOperand());
    }
}
