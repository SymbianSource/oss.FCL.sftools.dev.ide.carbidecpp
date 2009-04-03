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
public class AstRlsString extends AstNode implements IAstRlsString {

    private IAstName identifier;
    private IAstLiteralExpression string;

    /**
     * 
     */
    public AstRlsString(IAstName identifier, IAstLiteralExpression string) {
        setIdentifier(identifier);
        setString(string);
        dirty = false;
    }

    /**
     * 
     */
    public AstRlsString(String identifier, String string) {
        setIdentifier(new AstName(identifier, null));
        setString(new AstLiteralExpression(IAstLiteralExpression.K_STRING, string));
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
        return new Object[] {
                "rls_string ", // //$NON-NLS-1$
                identifier,
                " ", //$NON-NLS-1$
                string,
                ISourceFormatter.SEGMENT_NEWLINE
        };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRlsString#getIdentifier()
     */
    public IAstName getIdentifier() {
        return identifier;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRlsString#setIdentifier(com.nokia.sdt.sourcegen.doms.rss.dom.IAstName)
     */
    public void setIdentifier(IAstName name) {
        Check.checkArg(name);
        if (this.identifier != null)
            identifier.setParent(null);
        this.identifier = name;
        name.setParent(this);
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRlsString#getString()
     */
    public IAstLiteralExpression getString() {
        return string;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRlsString#setString(com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression)
     */
    public void setString(IAstLiteralExpression string) {
        Check.checkArg(string);
        Check.checkArg(string.getKind() == IAstLiteralExpression.K_STRING);
        if (this.string != null)
            string.setParent(null);
        this.string = string;
        string.setParent(this);
        dirty = true;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRlsString#setString(java.lang.String)
     */
    public void setString(String string) {
        setString(new AstLiteralExpression(IAstLiteralExpression.K_STRING, string));
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        return new IAstNode[] { identifier, string };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        return getChildren();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNameHolder#getRoleForName()
     */
    public int getRoleForName() {
        return IAstNameHolder.NAME_DEFINED;
    }

}
