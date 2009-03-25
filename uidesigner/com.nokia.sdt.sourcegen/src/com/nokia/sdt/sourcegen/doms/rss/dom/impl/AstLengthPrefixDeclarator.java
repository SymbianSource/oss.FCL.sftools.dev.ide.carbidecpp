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

import com.nokia.sdt.sourcegen.doms.rss.dom.IAstLengthPrefixDeclarator;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public class AstLengthPrefixDeclarator extends AstNode implements
        IAstLengthPrefixDeclarator {

    private int prefix;

    /**
     */
    public AstLengthPrefixDeclarator(int prefix) {
        super();
        setLengthPrefix(prefix);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
        switch (prefix) {
        case K_BYTE_PREFIXED:
            return new Object[] { "BYTE" }; //$NON-NLS-1$
        case K_WORD_PREFIXED:
            return new Object[] { "WORD" }; //$NON-NLS-1$
        }
        Check.checkState(false);
        return null;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLengthPrefixDeclarator#getLengthPrefix()
     */
    public int getLengthPrefix() {
        return prefix;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLengthPrefixDeclarator#setLengthPrefix(int)
     */
    public void setLengthPrefix(int prefix) {
        Check.checkArg(prefix == K_BYTE_PREFIXED || prefix == K_WORD_PREFIXED);
        this.prefix = prefix;
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        return IAstNode.NO_CHILDREN;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        return getChildren();
    }


}
