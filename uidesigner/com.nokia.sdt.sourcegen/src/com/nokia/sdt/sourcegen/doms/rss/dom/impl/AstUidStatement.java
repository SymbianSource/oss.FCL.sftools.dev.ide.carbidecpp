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
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public class AstUidStatement extends AstNode implements IAstUidStatement {

    private int which;
    private IAstLiteralExpression uid;

    /**
     * 
     */
    public AstUidStatement(int which, IAstLiteralExpression uid) {
        setWhich(which);
        setUid(uid);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
        return new Object[] {
                (which == 2 ? "UID2 " : "UID3 "), //$NON-NLS-1$ //$NON-NLS-2$
                uid,
                ISourceFormatter.SEGMENT_NEWLINE
        };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssUidStatement#getWhich()
     */
    public int getWhich() {
        return which;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssUidStatement#setWhich(int)
     */
    public void setWhich(int which) {
        Check.checkArg(which == 2 || which == 3);
        this.which = which;
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssUidStatement#getUid()
     */
    public IAstLiteralExpression getUid() {
        return uid;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssUidStatement#setUid(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public void setUid(IAstLiteralExpression uid) {
        Check.checkArg(uid);
        if (this.uid != null)
            uid.setParent(null);
        this.uid = uid;
        uid.setParent(this);
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        return new IAstNode[] { uid };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        return getChildren();
    }

}
