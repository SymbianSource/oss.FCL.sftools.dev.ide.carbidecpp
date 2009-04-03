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

import java.util.HashMap;
import java.util.Map;

/**
 * 
 *
 */
public class Scope implements IScope {

    private IAstNode owner;
    private IAstName name;
    private IScope parent;
    private Map entries;
    
    public Scope(IAstNode owner, IAstName name, IScope parent) {
        setOwner(owner);
        setScopeName(name);
        setParent(parent);
        entries = new HashMap();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IScope#getOwner()
     */
    public IAstNode getOwner() {
        return owner;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IScope#setOwner(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    public void setOwner(IAstNode owner) {
        this.owner = owner;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IScope#getScopeName()
     */
    public IAstName getScopeName() {
        return name;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IScope#setScopeName(com.nokia.sdt.sourcegen.doms.rss.dom.IAstName)
     */
    public void setScopeName(IAstName name) {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IScope#getParent()
     */
    public IScope getParent() {
        return parent;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IScope#setParent(com.nokia.sdt.sourcegen.doms.rss.dom.IScope)
     */
    public void setParent(IScope parent) {
        this.parent = parent;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IScope#find(java.lang.String)
     */
    public IAstName find(String name) {
        return (IAstName) entries.get(name);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IScope#search(java.lang.String)
     */
    public IAstName search(String name) {
        IScope scope = this;
        while (scope != null) {
            IAstName aName = scope.find(name);
            if (aName != null)
                return aName;
            scope = scope.getParent();
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IScope#add(com.nokia.sdt.sourcegen.doms.rss.dom.IAstName)
     */
    public void add(IAstName name) {
        Check.checkContract(name.getScope() == null);
        entries.put(name.getName(), name);
        name.setScope(this);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IScope#remove(com.nokia.sdt.sourcegen.doms.rss.dom.IAstName)
     */
    public void remove(IAstName name) {
        Check.checkContract(name.getScope() == this);
        entries.remove(name.getName());
        name.setScope(null);
    }

}
