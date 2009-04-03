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

import com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstSimpleDeclaration;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public class AstSimpleDeclaration extends AstNode implements
        IAstSimpleDeclaration {

    protected int kind;

    /**
     */
    public AstSimpleDeclaration(int kind) {
        super();
        setKind(kind);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
        String key = null;
        switch (kind) {
        case K_BUF:
            key = "BUF"; //$NON-NLS-1$
            break;
        case K_BUF8:
            key = "BUF8"; //$NON-NLS-1$
            break;
        case K_BYTE:
            key = "BYTE"; //$NON-NLS-1$
            break;
        case K_DOUBLE:
            key = "DOUBLE"; //$NON-NLS-1$
            break;
        case K_LINK:
            key = "LINK"; //$NON-NLS-1$
            break;
        case K_LLINK:
            key = "LLINK"; //$NON-NLS-1$
            break;
        case K_LONG:
            key = "LONG"; //$NON-NLS-1$
            break;
        case K_LTEXT:
            key = "LTEXT"; //$NON-NLS-1$
            break;
        case K_SRLINK:
            key = "SRLINK"; //$NON-NLS-1$
            break;
        case K_STRUCT:
            key = "STRUCT"; //$NON-NLS-1$
            break;
        case K_TEXT:
            key = "TEXT"; //$NON-NLS-1$
            break;
        case K_WORD:
            key = "WORD"; //$NON-NLS-1$
            break;
       case K_LTEXT8:
            key = "LTEXT8"; //$NON-NLS-1$
            break;
       case K_TEXT16:
    	   	key = "TEXT16"; //$NON-NLS-1$
    	   	break;
       case K_LTEXT16:
    	   key = "LTEXT16"; //$NON-NLS-1$
    	   break;
        default:
            Check.checkState(false);        
        }
        return new Object[] { key };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstSimpleDeclaration#getKind()
     */
    public int getKind() {
        return kind;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstSimpleDeclaration#setKind(int)
     */
    public void setKind(int kind) {
        Check.checkArg(kind >= 0 && kind <= K_LAST);
        this.kind = kind;
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
