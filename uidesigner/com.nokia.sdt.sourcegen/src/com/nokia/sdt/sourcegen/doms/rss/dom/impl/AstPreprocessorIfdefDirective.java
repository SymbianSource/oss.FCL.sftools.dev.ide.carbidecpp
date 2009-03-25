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
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstIdExpression;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorExpression;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorIfdefDirective;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public class AstPreprocessorIfdefDirective extends
        AstPreprocessorTestDirective implements
        IAstPreprocessorIfdefDirective {
    /**
     * @param expression
     * @param taken
     */
    public AstPreprocessorIfdefDirective(IAstPreprocessorExpression expression, boolean taken) {
        super(expression, taken);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
        return new Object[] { "#ifdef ", expression, ISourceFormatter.SEGMENT_NEWLINE }; //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstPreprocessorTestDirective#setExpression(com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorExpression)
     */
    public void setExpression(IAstPreprocessorExpression expression) {
        Check.checkArg(expression instanceof IAstIdExpression);
        super.setExpression(expression);
    }
}
