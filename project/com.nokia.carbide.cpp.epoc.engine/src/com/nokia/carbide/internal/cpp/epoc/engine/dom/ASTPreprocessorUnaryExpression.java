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



public class ASTPreprocessorUnaryExpression extends ASTPreprocessorExpression
        implements IASTPreprocessorUnaryExpression {

    private int operator;
	private IASTPreprocessorExpression operand;

	/**
     * @param operator
     * @param operand
     */
    public ASTPreprocessorUnaryExpression(int operator, IASTPreprocessorExpression operand) {
    	setOperator(operator);
    	setOperand(operand);
    	dirty = false;
    }

    /**
	 * @param expression
	 */
	public ASTPreprocessorUnaryExpression(ASTPreprocessorUnaryExpression expression) {
		super(expression);
		setOperator(expression.getOperator());
		setOperand((IASTPreprocessorExpression) expression.getOperand().copy());
		dirty = expression.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTPreprocessorUnaryExpression))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTPreprocessorUnaryExpression node = (ASTPreprocessorUnaryExpression) obj;
		return node.operator == operator
			&& node.operand.equalValue(operand);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ (operator << 16) ^ operand.hashCode() ^ 0x5555;
	}


	/* (non-Javadoc)
     * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.internal.ASTUnaryExpression#copy()
     */
    public IASTNode copy() {
    	return new ASTPreprocessorUnaryExpression(this);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorUnaryExpression#setOperand(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorExpression)
     */
    public void setOperand(IASTPreprocessorExpression expr) {
    	Check.checkArg(expr);
    	unparent(this.operand);
    	parent(expr);
    	this.operand = expr;
    	fireChanged();
    	dirty = true;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.ASTUnaryExpression#setOperator(int)
     */
    public void setOperator(int oper) {
        Check.checkArg(oper <= K_LAST);
        if (oper == this.operator)
        	return;
        this.operator = oper;
        fireChanged();
        dirty = true;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.ASTUnaryExpression#getOperatorName()
     */
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
        case K_DEFINED:
        	opString = "defined"; //$NON-NLS-1$
        	break;
        case K_PLUS:
        	opString = "+"; //$NON-NLS-1$
        	break;
        default:
            Check.checkState(false);
        }
        return opString;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.internal.ASTUnaryExpression#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
     */
    public void rewrite(IRewriteHandler handler) {
        String oper = null;
        switch (operator) {
        case K_PARENTHESIS:
        	handler.emitText("("); //$NON-NLS-1$
        	handler.emitSpace();
        	handler.emitNode(operand);
        	handler.emitSpace();
        	handler.emitText(")"); //$NON-NLS-1$
        	return;
        case K_DEFINED:
        	if (operand instanceof IASTPreprocessorUnaryExpression &&
        			(((IASTPreprocessorUnaryExpression) operand).getOperator() == IASTPreprocessorUnaryExpression.K_PARENTHESIS))
        		oper = "defined"; //$NON-NLS-1$
        	else
        		oper = "defined "; // keep space for "defined ID" //$NON-NLS-1$
            break;
        case K_PLUS:
            oper = "+"; //$NON-NLS-1$
            break;
        default:
        	oper = getOperatorName();
        	break;
        }
        
        handler.emitText(oper);
        handler.emitNode(operand);
    }

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorUnaryExpression#getOperator()
	 */
	public int getOperator() {
		return operator;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorUnaryExpression#getOperand()
	 */
	public IASTPreprocessorExpression getOperand() {
		return operand;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return new IASTNode[] { operand };
	}
}
