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
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstUnaryExpression;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorUnaryExpression;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public class AstPreprocessorUnaryExpression extends AstUnaryExpression
        implements IAstPreprocessorUnaryExpression {

    /**
     * @param operator
     * @param operand
     */
    public AstPreprocessorUnaryExpression(int operator, IAstExpression operand) {
        super(operator, operand);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstUnaryExpression#setOperator(int)
     */
    public void setOperator(int oper) {
        Check.checkArg(oper <= IAstPreprocessorUnaryExpression.K_LAST);
        this.operator = oper;
        dirty = true;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstUnaryExpression#getOperatorName()
     */
    protected String getOperatorName() {
        if (operator == K_DEFINED)
            return "defined"; //$NON-NLS-1$
        else if (operator == K_PLUS)
            return "+"; //$NON-NLS-1$
        return super.getOperatorName();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstUnaryExpression#constructText()
     */
    public Object[] getTextSegments() {
        String oper = null;
        switch (operator) {
        case K_DEFINED:
        	if (operand instanceof IAstUnaryExpression &&
        			(((IAstUnaryExpression) operand).getOperator() == IAstUnaryExpression.K_PARENTHESIS))
        		oper = "defined"; //$NON-NLS-1$
        	else
        		oper = "defined "; // keep space for "defined ID" //$NON-NLS-1$
            break;
        case K_PLUS:
            oper = "+"; //$NON-NLS-1$
            break;
        default:
            return super.getTextSegments();
        }
        return new Object[] { oper, operand };
    }
}
