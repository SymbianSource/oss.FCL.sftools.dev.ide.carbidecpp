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
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorElseDirective;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorExpression;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public class AstPreprocessorElseDirective extends AstPreprocessorTestDirective
        implements IAstPreprocessorElseDirective {

    /**
     * 
     */
    public AstPreprocessorElseDirective(boolean taken) {
        super(null, taken);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
        return new Object[] { "#else", ISourceFormatter.SEGMENT_NEWLINE }; //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        return NO_CHILDREN;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        return NO_CHILDREN;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorTestDirective#getExpression()
     */
    public IAstPreprocessorExpression getExpression() {
        return null;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorTestDirective#setExpression(com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorExpression)
     */
    public void setExpression(IAstPreprocessorExpression expr) {
        Check.checkArg(expr == null);
    }

}
