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

import com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorBinaryExpression;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public class AstPreprocessorBinaryExpression extends AstBinaryExpression
        implements IAstPreprocessorBinaryExpression {

    /**
     * 
     */
    public AstPreprocessorBinaryExpression(int oper, IAstExpression left, IAstExpression right) {
        super(oper, left, right);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstBinaryExpression#getOperatorName()
     */
    protected String getOperatorName() {
        if (operator == K_LOG_AND)
            return "&&"; //$NON-NLS-1$
        else if (operator == K_LOG_OR)
            return "||"; //$NON-NLS-1$
        return super.getOperatorName();
    }

    /* (non-Javadoc)
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression#equalValue(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public boolean equalValue(IAstExpression expr) {
        if (expr instanceof IAstPreprocessorBinaryExpression) {
            IAstPreprocessorBinaryExpression bin = (IAstPreprocessorBinaryExpression) expr;
            return bin.getOperator() == getOperator()
            && bin.getLeftOperand().equalValue(getLeftOperand())
            && bin.getRightOperand().equalValue(getRightOperand());
        }
        return false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstBinaryExpression#setOperator(int)
     */
    public void setOperator(int operator) {
        Check.checkArg(operator <= IAstPreprocessorBinaryExpression.K_LAST);
        this.operator = operator;
        dirty = true;
    }

}
