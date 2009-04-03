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
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public class AstIdExpression extends AstExpression implements IAstIdExpression {

   /** name referenced in expression */
    private IAstName name;

    public AstIdExpression(IAstName name) {
        super();
        setName(name);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        // name is not owned
        return NO_CHILDREN;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        return new IAstNode[] { name };
    }

    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#constructText()
     */
    public Object[] getTextSegments() {
        // we're not defining the name, so provide its text
        return new Object[] { name.getName() }; 
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstIdExpression#getName()
     */
    public IAstName getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstIdExpression#setName(com.nokia.sdt.sourcegen.doms.rss.dom.IAstName)
     */
    public void setName(IAstName name) {
        Check.checkArg(name);
        if (this.name == null || !this.name.getName().equals(name.getName())) {
        	dirty = true;
        }
        this.name = name;
        // name not owned
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNameHolder#getRoleForName()
     */
    public int getRoleForName() {
        return NAME_REFERENCED;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression#simplify()
     */
    public IAstExpression simplify() {
        return this;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression#equalValue(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public boolean equalValue(IAstExpression expr) {
        return (expr instanceof IAstIdExpression)
        && ((IAstIdExpression) expr).getName().getName().equals(getName().getName());
    }
}
