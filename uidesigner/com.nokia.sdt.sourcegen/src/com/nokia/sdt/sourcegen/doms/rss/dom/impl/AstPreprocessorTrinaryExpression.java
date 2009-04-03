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
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorTrinaryExpression;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public class AstPreprocessorTrinaryExpression extends AstPreprocessorExpression
        implements IAstPreprocessorTrinaryExpression {

    private IAstExpression condition;
    private IAstExpression trueBranch;
    private IAstExpression falseBranch;

    /**
     * 
     */
    public AstPreprocessorTrinaryExpression(IAstExpression condition, IAstExpression trueBranch, IAstExpression falseBranch) {
        super();
        setCondition(condition);
        setTrueBranch(trueBranch);
        setFalseBranch(falseBranch);
        dirty = false;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#constructText()
     */
    public Object[] getTextSegments() {
        return new Object[] { condition,
                ISourceFormatter.SEGMENT_FORMATTING_SPACE,
                "?",  //$NON-NLS-1$
                ISourceFormatter.SEGMENT_FORMATTING_SPACE,
                trueBranch, 
                ISourceFormatter.SEGMENT_FORMATTING_SPACE,
                ":",  //$NON-NLS-1$
                ISourceFormatter.SEGMENT_FORMATTING_SPACE,
                falseBranch };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorTrinaryExpression#getCondition()
     */
    public IAstExpression getCondition() {
        return condition;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorTrinaryExpression#setCondition(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public void setCondition(IAstExpression cond) {
        Check.checkArg(cond);
        if (this.condition != null)
            this.condition.setParent(null);
        this.condition = cond;
        cond.setParent(this);
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorTrinaryExpression#getTrueBranch()
     */
    public IAstExpression getTrueBranch() {
        return trueBranch;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorTrinaryExpression#setTrueBranch(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public void setTrueBranch(IAstExpression trueBranch) {
        Check.checkArg(trueBranch);
        if (this.trueBranch != null)
            this.trueBranch.setParent(null);
        this.trueBranch = trueBranch;
        trueBranch.setParent(this);
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorTrinaryExpression#getFalseBranch()
     */
    public IAstExpression getFalseBranch() {
        return falseBranch;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorTrinaryExpression#setFalseBranch(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public void setFalseBranch(IAstExpression falseBranch) {
        Check.checkArg(falseBranch);
        if (this.falseBranch != null)
            this.falseBranch.setParent(null);
        this.falseBranch = falseBranch;
        falseBranch.setParent(this);
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression#simplify()
     */
    public IAstExpression simplify() {
        IAstExpression expr = condition.simplify();
        if (expr instanceof IAstLiteralExpression) {
            try {
                IAstLiteralExpression lit = (IAstLiteralExpression) expr;
                if (lit.getKind() == IAstLiteralExpression.K_INTEGER) {
                    if (lit.getIntValue() != 0)
                        return trueBranch.simplify();
                    else
                        return falseBranch.simplify();
                }
            } catch (NumberFormatException e) {
                // give up
            }
        }
        return this;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression#equalValue(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public boolean equalValue(IAstExpression expr) {
        if (!(expr instanceof IAstPreprocessorTrinaryExpression))
            return false;
        IAstPreprocessorTrinaryExpression tri = (IAstPreprocessorTrinaryExpression) expr;
        return condition.equalValue(tri.getCondition())
            && trueBranch.equalValue(tri.getTrueBranch())
            && falseBranch.equalValue(tri.getFalseBranch());
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        return new IAstNode[] { condition, trueBranch, falseBranch };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        return getChildren();
    }


}
