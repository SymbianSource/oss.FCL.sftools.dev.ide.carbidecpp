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

import com.nokia.sdt.sourcegen.doms.rss.dom.*;

/**
 * 
 *
 */
public class AstArrayDeclarator extends AstNode implements
        IAstArrayDeclarator {

    private IAstExpression size;

    public AstArrayDeclarator(IAstExpression size) {
        super();
        setArraySize(size);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstArrayDeclarator#getArraySize()
     */
    public IAstExpression getArraySize() {
        return size;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstArrayDeclarator#setArraySize(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public void setArraySize(IAstExpression expr) {
        if (this.size != null)
            this.size.setParent(null);
        this.size = expr;
        if (size != null)
            expr.setParent(this);
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#constructText()
     */
    public Object[] getTextSegments() {
        if (size != null)
            return new Object[] { "[", size, "]" }; //$NON-NLS-1$ //$NON-NLS-2$
        else
            return new Object[] { "[]" }; //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        if (size != null)
            return new IAstNode[] { size };
        else
            return IAstNode.NO_CHILDREN;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        return getChildren();
    }

}
