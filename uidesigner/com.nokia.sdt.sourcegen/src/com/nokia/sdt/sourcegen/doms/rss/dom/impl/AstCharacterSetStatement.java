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
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstCharacterSetStatement;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public class AstCharacterSetStatement extends AstNode implements
        IAstCharacterSetStatement {

    private String charSet; 

    public AstCharacterSetStatement(String charSet) {
        super();
        setCharacterSet(charSet);
        dirty = false;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
        return new Object[] {
                "CHARACTER_SET ", //$NON-NLS-1$
                charSet,
                ISourceFormatter.SEGMENT_NEWLINE
        };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstCharacterSetStatement#getCharacterSet()
     */
    public String getCharacterSet() {
        return charSet;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstCharacterSetStatement#setCharacterSet(java.lang.String)
     */
    public void setCharacterSet(String charSet) {
        Check.checkArg(charSet);
        this.charSet = charSet;
        dirty = true;
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

}
