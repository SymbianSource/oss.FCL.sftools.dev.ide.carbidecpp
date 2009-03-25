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
public class AstBinaryExpression extends AstExpression implements
        IAstBinaryExpression {

    protected int operator;
    protected IAstExpression left, right;
    
    /**
     * @param operator one of K_xxx
     * @param left left-hand side, must not be null
     * @param right right-hand side, must not be null
     */
    public AstBinaryExpression(int operator, IAstExpression left, IAstExpression right) {
        super();
        setOperator(operator);
        setLeftOperand(left);
        setRightOperand(right);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        return new IAstNode[] { left, right };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        return getChildren();
    }

    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#constructText()
     */
    public Object[] getTextSegments() {
        
        // left op right:  7 * k
        // left[right]:  foo[3]
        
        if (operator != K_SUBSCRIPT) {
            return new Object[] { 
            		left, 
            		ISourceFormatter.SEGMENT_FORMATTING_SPACE, 
            		getOperatorName(), 
            		ISourceFormatter.SEGMENT_FORMATTING_SPACE, 
            		right 
            		}; 
        } else {
            return new Object[] { 
            		left, 
            		"[",  //$NON-NLS-1$
            		ISourceFormatter.SEGMENT_FORMATTING_SPACE, 
            		right, 
            		ISourceFormatter.SEGMENT_FORMATTING_SPACE, 
            		"]"  //$NON-NLS-1$
            		};  //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    protected String getOperatorName() {
        char opText = 0;
        switch (operator) {
        case K_ADD:
            opText = '+';
            break;
         case K_AND:
            opText = '&';
            break;
        case K_DIV:
            opText = '/';
            break;
        case K_MOD:
            opText = '%';
            break;
        case K_MUL:
            opText = '*';
            break;
        case K_OR:
            opText = '|';
            break;
        case K_SUB:
            opText = '-';
            break;
        case K_SUBSCRIPT:
            opText = '[';
            break;
        case K_XOR:
            opText = '^';
            break;
        default:
            Check.checkState(false);
        }
        return Character.toString(opText);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstBinaryExpression#getOperator()
     */
    public int getOperator() {
        return operator;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstBinaryExpression#setOperator(int)
     */
    public void setOperator(int operator) {
        Check.checkArg(operator >= 0 && operator <= K_LAST);
        this.operator = operator;
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstBinaryExpression#getLeftOperand()
     */
    public IAstExpression getLeftOperand() {
        return left;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstBinaryExpression#setLeftOperand(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public void setLeftOperand(IAstExpression expr) {
        Check.checkArg(expr);
        if (this.left != null)
            this.left.setParent(null);
        this.left = expr;
        expr.setParent(this);
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstBinaryExpression#getRightOperand()
     */
    public IAstExpression getRightOperand() {
        return right;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstBinaryExpression#setRightOperand(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public void setRightOperand(IAstExpression expr) {
        Check.checkArg(expr);
        if (this.right != null)
            this.right.setParent(null);
        this.right = expr;
        expr.setParent(this);
        dirty = true;
    }

    /** Return new IAstLiteralExpression#getKind() based on
     * whether the two kinds are convertible to another
     *  
     * @param leftKind IAstLiteralExpression#getKind()
     * @param rightKind IAstLiteralExpression#getKind()
     * @return new IAstLiteralExpression#getKind() or -1
     */
    protected int convertible(int leftKind, int rightKind) {
        if (leftKind == rightKind)
            return leftKind;
        if (leftKind == IAstLiteralExpression.K_INTEGER &&
                rightKind == IAstLiteralExpression.K_FLOAT)
            return IAstLiteralExpression.K_FLOAT;
        if (leftKind == IAstLiteralExpression.K_FLOAT &&
                rightKind == IAstLiteralExpression.K_INTEGER)
            return IAstLiteralExpression.K_FLOAT;
        return -1;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression#simplify()
     */
    public IAstExpression simplify() {
        IAstExpression newLeft = left.simplify();
        IAstExpression newRight = right.simplify();
        
        // it is simplifiable?
        if (operator != K_SUBSCRIPT
                && (newLeft instanceof IAstLiteralExpression)
                && (newRight instanceof IAstLiteralExpression)) {
        
            IAstLiteralExpression litLeft = (IAstLiteralExpression) newLeft;
            IAstLiteralExpression litRight = (IAstLiteralExpression) newRight;
            
            // try to collapse value if types are compatible
            int convertedType = convertible(litLeft.getKind(), litRight.getKind());
            if (convertedType != -1) {
                switch (operator) {
                case K_ADD:
                case K_SUB:
                case K_MUL:
                case K_DIV:
                    if (convertedType == IAstLiteralExpression.K_FLOAT) {
                        double dblLeft = litLeft.getFloatValue();
                        double dblRight = litRight.getFloatValue();
                        double dblResult = 0.0;
                        switch (operator) {
                        case K_ADD:
                            dblResult = dblLeft + dblRight; 
                            break;
                        case K_SUB:
                            dblResult = dblLeft - dblRight; 
                            break;
                        case K_MUL:
                            dblResult = dblLeft * dblRight; 
                            break;
                        case K_DIV:
                            dblResult = dblLeft / dblRight; 
                            break;
                        }
                        IAstLiteralExpression lit = new AstLiteralExpression( 
                                IAstLiteralExpression.K_FLOAT, Double.toString(dblResult));
                        lit.setParent(getParent());
                        return lit;
                    } else if (convertedType == IAstLiteralExpression.K_INTEGER) {
                        int intLeft = litLeft.getIntValue();
                        int intRight = litRight.getIntValue();
                        int intResult = 0;
                        switch (operator) {
                        case K_ADD:
                            intResult = intLeft + intRight; 
                            break;
                        case K_SUB:
                            intResult = intLeft - intRight; 
                            break;
                        case K_MUL:
                            intResult = intLeft * intRight; 
                            break;
                        case K_DIV:
                            intResult = intLeft / intRight; 
                            break;
                        }
                        IAstLiteralExpression lit = new AstLiteralExpression( 
                                IAstLiteralExpression.K_INTEGER, Integer.toString(intResult));
                        lit.setParent(getParent());
                        return lit;
                    }
                    break;
                
                case K_AND:
                case K_OR:
                case K_XOR:
                case K_MOD:
                    if (convertedType == IAstLiteralExpression.K_INTEGER) {
                        int intLeft = litLeft.getIntValue();
                        int intRight = litRight.getIntValue();
                        int intResult = 0;
                        switch (operator) {
                        case K_AND:
                            intResult = intLeft & intRight; 
                            break;
                        case K_OR:
                            intResult = intLeft | intRight; 
                            break;
                        case K_XOR:
                            intResult = intLeft ^ intRight; 
                            break;
                        case K_MOD:
                            intResult = intLeft % intRight; 
                            break;
                        }
                        IAstLiteralExpression lit = new AstLiteralExpression(
                                IAstLiteralExpression.K_INTEGER, Integer.toString(intResult));
                        lit.setParent(getParent());
                        return lit;
                    }
                    break;
                }
            }
        }
        
        // fallthrough: make new binary expression if children changed
        if (!newLeft.equalValue(left) || !newRight.equalValue(right)) {
            newLeft.setParent(null);
            newRight.setParent(null);
            IAstBinaryExpression bin = new AstBinaryExpression(operator, newLeft, newRight);
            bin.setParent(getParent());
            return bin;
        }
        else
            return this;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression#equalValue(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public boolean equalValue(IAstExpression expr) {
        if (!(expr instanceof IAstBinaryExpression)) 
            return false;
        if (((IAstBinaryExpression) expr).getOperator() != getOperator())
            return false;
        if (((IAstBinaryExpression) expr).getLeftOperand().equalValue(getLeftOperand())
        && ((IAstBinaryExpression) expr).getRightOperand().equalValue(getRightOperand()))
            return true;
        if (isCommutative() 
                && ((IAstBinaryExpression) expr).getLeftOperand().equalValue(getRightOperand())
                && ((IAstBinaryExpression) expr).getRightOperand().equalValue(getLeftOperand()))
            return true;
        return false;
    }

    /**
     * Tell whether the operator is commutative.
     */
    private boolean isCommutative() {
        return operator == K_ADD || operator == K_AND
            || operator == K_MUL || operator == K_OR 
            || operator == K_XOR;
    }

}
