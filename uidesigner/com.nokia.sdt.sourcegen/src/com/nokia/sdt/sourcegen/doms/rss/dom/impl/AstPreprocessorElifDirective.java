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
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorElifDirective;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorExpression;

/**
 * 
 *
 */
public class AstPreprocessorElifDirective extends AstPreprocessorTestDirective
        implements IAstPreprocessorElifDirective {

    /**
     * @param expression
     * @param taken
     */
    public AstPreprocessorElifDirective(IAstPreprocessorExpression expression,
            boolean taken) {
        super(expression, taken);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
        return new Object[] { "#elif ", expression, ISourceFormatter.SEGMENT_NEWLINE }; //$NON-NLS-1$
    }

}
