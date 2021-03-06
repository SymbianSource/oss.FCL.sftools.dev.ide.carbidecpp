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

package com.nokia.sdt.sourcegen.doms.rss.dom;


/**
 * A scope defining names
 * 
 * In RSS, a scope is used for enums, STRUCTs, and the translation unit
 * 
 * 
 *
 */
public interface IScope {
    /** Get the owner of the scope (or null); this is, e.g.,
     * an IAstEnumDeclaration, IAstCompositeTypeSpecifier, etc. */
    public IAstNode getOwner();
    
    /** Set the owner of the scope (or null); this is, e.g.,
     * an IAstEnumDeclaration, IAstCompositeTypeSpecifier, etc. */
    public void setOwner(IAstNode owner);
    
    /** Get the name of the scope (or null) */
    public IAstName getScopeName();
    
    /** Set the name of the scope (or null) */
    public void setScopeName(IAstName name);
    
    /** Get the parent scope (or null) */
    public IScope getParent();
    
    /** Set the parent scope (or null) */
    public void setParent(IScope parent);
    
    /** Look up a name in this scope */
    public IAstName find(String name);

    /**
     * Look up a name in any scope visible from this scope,
     * starting from this one and going up the parent chain
     */
    public IAstName search(String name);

    /** Add a name to the scope.  Sets name's scope to this
     * and the name's parent to node.
     * @param name the name to add.  Current scope must be null.  */
    public void add(IAstName name);

    /** Remove a name to the scope.  Sets name's scope to null.
     * @param name the name to remove.  Current scope must be this.  */
    public void remove(IAstName name);
}
