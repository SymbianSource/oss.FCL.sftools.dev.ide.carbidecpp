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

import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorLiteralExpression;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public class AstPreprocessorLiteralExpression extends AstLiteralExpression
        implements IAstPreprocessorLiteralExpression {

    /**
     * @param kind
     * @param value
     */
    public AstPreprocessorLiteralExpression(int kind, String value) {
        super(kind, value);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstLiteralExpression#setKind(int)
     */
    public void setKind(int kind) {
        if (kind == K_BOOLEAN)
            this.kind = kind;
        else
            super.setKind(kind);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#constructText()
     */
    public Object[] getTextSegments() {
        if (kind == K_BOOLEAN)
            return new Object[] { value };
        else
            return super.getTextSegments();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression#setValue(java.lang.String)
     */
    public void setValue(String value) {
        Check.checkArg(value);
        
        // validate the argument
        if (kind == K_BOOLEAN) {
            Check.checkArg(value.equals("false") || value.equals("true")); //$NON-NLS-1$ //$NON-NLS-2$
            this.value = value;
            dirty = true;
        }
        else
            super.setValue(value);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression#getCharValue()
     */
    public char getCharValue() {
        return super.getCharValue();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression#getFloatValue()
     */
    public double getFloatValue() {
        if (kind == K_BOOLEAN)
            return value.equals("false") ? 0.0 : 1.0; //$NON-NLS-1$
        else
            return super.getFloatValue();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression#getIntValue()
     */
    public int getIntValue() {
        if (kind == K_BOOLEAN)
            return value.equals("false") ? 0 : 1; //$NON-NLS-1$
        else
            return super.getIntValue();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression#getStringValue()
     */
    public String getStringValue() {
        if (kind == K_BOOLEAN)
            return value;
        else
            return super.getStringValue();
    }
}
