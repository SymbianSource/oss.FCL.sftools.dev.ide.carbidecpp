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
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorExpression;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorIfDirective;

/**
 * 
 *
 */
public class AstPreprocessorIfDirective extends
        AstPreprocessorTestDirective implements
        IAstPreprocessorIfDirective {
    
    /**
     * @param expression
     * @param taken
     */
    public AstPreprocessorIfDirective(IAstPreprocessorExpression expression, boolean taken) {
        super(expression, taken);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
        return new Object[] { "#if ", expression, ISourceFormatter.SEGMENT_NEWLINE }; //$NON-NLS-1$
    }
}
