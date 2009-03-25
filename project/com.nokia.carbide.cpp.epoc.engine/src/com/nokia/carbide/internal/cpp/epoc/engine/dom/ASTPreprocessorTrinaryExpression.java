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


public class ASTPreprocessorTrinaryExpression extends ASTPreprocessorExpression
        implements IASTPreprocessorTrinaryExpression {

    private IASTPreprocessorExpression condition;
    private IASTPreprocessorExpression trueBranch;
    private IASTPreprocessorExpression falseBranch;

    /**
     * 
     */
    public ASTPreprocessorTrinaryExpression(IASTPreprocessorExpression condition, IASTPreprocessorExpression trueBranch, IASTPreprocessorExpression falseBranch) {
        super();
        setCondition(condition);
        setTrueBranch(trueBranch);
        setFalseBranch(falseBranch);
        dirty = false;
    }
    
    /**
	 * @param expression
	 */
	public ASTPreprocessorTrinaryExpression(ASTPreprocessorTrinaryExpression expression) {
		super(expression);
		setCondition((IASTPreprocessorExpression) expression.condition.copy());
		setTrueBranch((IASTPreprocessorExpression) expression.trueBranch.copy());
		setFalseBranch((IASTPreprocessorExpression) expression.falseBranch.copy());
		dirty = expression.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTPreprocessorTrinaryExpression))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTPreprocessorTrinaryExpression node = (ASTPreprocessorTrinaryExpression) obj;
		return node.condition.equalValue(condition)
			&& node.trueBranch.equalValue(trueBranch)
			&& node.falseBranch.equalValue(falseBranch);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ condition.hashCode() ^ trueBranch.hashCode() ^ falseBranch.hashCode() ^ 0xaaaa;
	}


	
	/* (non-Javadoc)
     * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
     */
    public IASTNode copy() {
    	return new ASTPreprocessorTrinaryExpression(this);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
     */
    public void rewrite(IRewriteHandler handler) {
    	handler.emitNode(condition);
    	handler.emitSpace();
        handler.emitText("?");  //$NON-NLS-1$
        handler.emitSpace();
        handler.emitNode(trueBranch);
        handler.emitSpace();
        handler.emitText(":");  //$NON-NLS-1$
        handler.emitSpace();
        handler.emitNode(falseBranch);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IASTPreprocessorTrinaryExpression#getCondition()
     */
    public IASTPreprocessorExpression getCondition() {
        return condition;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IASTPreprocessorTrinaryExpression#setCondition(com.nokia.sdt.sourcegen.doms.rss.dom.IASTExpression)
     */
    public void setCondition(IASTPreprocessorExpression cond) {
        Check.checkArg(cond);
        if (cond == this.condition)
        	return;
        unparent(this.condition);
        parent(cond);
        this.condition = cond;
        fireChanged();
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IASTPreprocessorTrinaryExpression#getTrueBranch()
     */
    public IASTPreprocessorExpression getTrueBranch() {
        return trueBranch;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IASTPreprocessorTrinaryExpression#setTrueBranch(com.nokia.sdt.sourcegen.doms.rss.dom.IASTExpression)
     */
    public void setTrueBranch(IASTPreprocessorExpression trueBranch) {
        Check.checkArg(trueBranch);
        if (trueBranch == this.trueBranch)
        	return;
        unparent(this.trueBranch);
        parent(trueBranch);
        this.trueBranch = trueBranch;
        fireChanged();
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IASTPreprocessorTrinaryExpression#getFalseBranch()
     */
    public IASTPreprocessorExpression getFalseBranch() {
        return falseBranch;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IASTPreprocessorTrinaryExpression#setFalseBranch(com.nokia.sdt.sourcegen.doms.rss.dom.IASTExpression)
     */
    public void setFalseBranch(IASTPreprocessorExpression falseBranch) {
        Check.checkArg(falseBranch);
        unparent(this.falseBranch);
        parent(falseBranch);
        this.falseBranch = falseBranch;
        fireChanged();
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IASTNode#getChildren()
     */
    public IASTNode[] getChildren() {
        return new IASTNode[] { condition, trueBranch, falseBranch };
    }
}
