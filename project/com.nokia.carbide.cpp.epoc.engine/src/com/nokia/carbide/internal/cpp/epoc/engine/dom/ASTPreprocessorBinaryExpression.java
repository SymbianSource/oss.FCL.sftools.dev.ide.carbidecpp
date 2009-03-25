/*
* Copyright (c) 2005-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.dom;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTPreprocessorBinaryExpression extends ASTPreprocessorExpression
        implements IASTPreprocessorBinaryExpression {

    private int operator;
	private IASTPreprocessorExpression right;
	private IASTPreprocessorExpression left;

	/**
     * 
     */
    public ASTPreprocessorBinaryExpression(int oper, IASTPreprocessorExpression left, IASTPreprocessorExpression right) {
    	setOperator(oper);
    	setLeftOperand(left);
    	setRightOperand(right);
    	dirty = false;
    }

	public ASTPreprocessorBinaryExpression(ASTPreprocessorBinaryExpression expression) {
		super(expression);
		setOperator(expression.getOperator());
		setLeftOperand((IASTPreprocessorExpression) expression.getLeftOperand().copy());
		setRightOperand((IASTPreprocessorExpression) expression.getRightOperand().copy());
		dirty = expression.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTPreprocessorBinaryExpression))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTPreprocessorBinaryExpression node = (ASTPreprocessorBinaryExpression) obj;
		return node.operator == operator
			&& node.left.equalValue(left)
			&& node.right.equalValue(right);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ (operator << 16) ^ left.hashCode() ^ right.hashCode();
	}

	/* (non-Javadoc)
     * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.internal.ASTBinaryExpression#copy()
     */
    public IASTNode copy() {
    	return new ASTPreprocessorBinaryExpression(this);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getChildren()
     */
    public IASTNode[] getChildren() {
        return new IASTNode[] { left, right };
    }

    /* (non-Javadoc)
     * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
     */
    public void rewrite(IRewriteHandler handler) {
        
        // left op right:  7 * k
        // left[right]:  foo[3]
        
        if (operator != K_SUBSCRIPT) {
        	handler.emitNode(left);
        	handler.emitSpace();
        	handler.emitText(getOperatorName());
        	handler.emitSpace();
        	handler.emitNode(right);
        } else {
        	handler.emitNode(left);
        	handler.emitText("[");  //$NON-NLS-1$
        	handler.emitSpace();
        	handler.emitNode(right);
        	handler.emitSpace();
        	handler.emitText("]");  //$NON-NLS-1$
        }
    }

    protected String getOperatorName() {
        String opText = ""; //$NON-NLS-1$
        switch (operator) {
        case K_ADD:
            opText = "+"; //$NON-NLS-1$
            break;
         case K_AND:
            opText = "&"; //$NON-NLS-1$
            break;
        case K_DIV:
            opText = "/"; //$NON-NLS-1$
            break;
        case K_MOD:
            opText = "%"; //$NON-NLS-1$
            break;
        case K_MUL:
            opText = "*"; //$NON-NLS-1$
            break;
        case K_OR:
            opText = "|"; //$NON-NLS-1$
            break;
        case K_SUB:
            opText = "-"; //$NON-NLS-1$
            break;
        case K_SUBSCRIPT:
            opText = "["; //$NON-NLS-1$
            break;
        case K_XOR:
            opText = "^"; //$NON-NLS-1$
            break;
        case K_LOG_AND:
        	opText = "&&"; //$NON-NLS-1$
        	break;
        case K_LOG_OR:
        	opText = "||"; //$NON-NLS-1$
        	break;
        case K_EQUALS:
        	opText = "=="; //$NON-NLS-1$
        	break;
        case K_NOT_EQUALS:
        	opText = "!="; //$NON-NLS-1$
        	break;
        case K_GREATER_THAN:
        	opText = ">"; //$NON-NLS-1$
        	break;
        case K_LESS_THAN:
        	opText = "<"; //$NON-NLS-1$
        	break;
        case K_GREATER_THAN_OR_EQUALS:
        	opText = ">="; //$NON-NLS-1$
        	break;
        case K_LESS_THAN_OR_EQUALS:
        	opText = "<="; //$NON-NLS-1$
        	break;
        default:
            Check.checkState(false);
        }
        return opText;
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
        if (operator == this.operator)
        	return;
        this.operator = operator;
        fireChanged();
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstBinaryExpression#getLeftOperand()
     */
    public IASTPreprocessorExpression getLeftOperand() {
        return left;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstBinaryExpression#setLeftOperand(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public void setLeftOperand(IASTPreprocessorExpression expr) {
        Check.checkArg(expr);
        if (this.left == expr)
        	return;
        unparent(this.left);
        parent(expr);
        this.left = expr;
        fireChanged();
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstBinaryExpression#getRightOperand()
     */
    public IASTPreprocessorExpression getRightOperand() {
        return right;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstBinaryExpression#setRightOperand(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public void setRightOperand(IASTPreprocessorExpression expr) {
        Check.checkArg(expr);
        if (this.right == expr)
        	return;
        unparent(this.right);
        parent(expr);
        this.right = expr;
        fireChanged();
        dirty = true;
    }

}
