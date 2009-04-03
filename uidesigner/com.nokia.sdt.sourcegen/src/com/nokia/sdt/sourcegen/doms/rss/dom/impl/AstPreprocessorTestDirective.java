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

import com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorExpression;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorTestDirective;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public abstract class AstPreprocessorTestDirective extends AstPreprocessorNode implements
        IAstPreprocessorTestDirective {

    protected IAstPreprocessorExpression expression;
    private boolean taken;

    /**
     * 
     */
    public AstPreprocessorTestDirective(IAstPreprocessorExpression expression, boolean taken) {
        super();
        setExpression(expression);
        setTaken(taken);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorTestDirective#getExpression()
     */
    public IAstPreprocessorExpression getExpression() {
        return expression;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorTestDirective#setExpression(com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorExpression)
     */
    public void setExpression(IAstPreprocessorExpression expression) {
        Check.checkArg(expression);
        if (this.expression != null)
            expression.setParent(null);
        this.expression = expression;
        expression.setParent(this);
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorTestDirective#isTaken()
     */
    public boolean isTaken() {
        return taken;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorTestDirective#setTaken(boolean)
     */
    public void setTaken(boolean taken) {
        this.taken = taken;
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        return new IAstNode[] { expression };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        return getChildren();
    }

}
