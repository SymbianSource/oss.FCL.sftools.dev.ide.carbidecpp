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
public class AstName extends AstNode implements IAstName {

    String name;
    IScope scope;
    
    /**
     * Create a name
     * 
     * @param name
     * @param scope
     */
    public AstName(String name, IScope scope) {
        super();
        setName(name);
        setScope(scope);
        dirty = false;
    }
    
    /**
     * @param value
     */
    public AstName(String value) {
        super();
        setName(value);
        setScope(null);
        dirty = false;
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
        return getChildren();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#constructText()
     */
    public Object[] getTextSegments() {
        return new Object[] { name };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstName#getName()
     */
    public String getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstName#setName(java.lang.String)
     */
    public void setName(String name) {
        Check.checkArg(name);
        this.name = name;
        this.dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstName#getScope()
     */
    public IScope getScope() {
        return scope;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstName#setScope(com.nokia.sdt.sourcegen.doms.rss.dom.IScope)
     */
    public void setScope(IScope scope) {
        this.scope = scope;
    }

 }
