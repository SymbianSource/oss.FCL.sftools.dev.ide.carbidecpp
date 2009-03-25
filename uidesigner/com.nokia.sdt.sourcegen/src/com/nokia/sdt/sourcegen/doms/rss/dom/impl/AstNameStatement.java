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
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstNameStatement;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public class AstNameStatement extends AstNode implements IAstNameStatement {

    private String name;

    public AstNameStatement(String name) {
        super();
        setShortName(name);
        dirty = false;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
        return new Object[] { 
                "NAME ", //$NON-NLS-1$
                name,
                ISourceFormatter.SEGMENT_NEWLINE 
                };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNameStatement#getShortName()
     */
    public String getShortName() {
        return name;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNameStatement#setShortName(java.lang.String)
     */
    public void setShortName(String name) {
        Check.checkArg(name);
        Check.checkArg(name.length() == 4);
        this.name = name;
        this.dirty = true;
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
