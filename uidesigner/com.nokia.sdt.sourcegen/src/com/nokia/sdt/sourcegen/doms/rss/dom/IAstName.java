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
 * A name in the DOM.
 * <p>
 * A name is owned only by one node (i.e. has a single parent)
 * but may be referenced by multiple nodes.  The IAstNameHolder
 * interface exists on all nodes that hold IAstName to distinguish
 * the roles. 
 * 
 * @see IAstNameHolder
 * 
 * 
 *
 */
public interface IAstName extends IAstNode {
    /** Get the name
     * 
     * @return name (never null)
     */
    public String getName();

    /** Set the name
     * 
     * @param name (must not be null)
     */
    public void setName(String name);
    
    /** Get the scope of the name 
     * 
     * @return the scope (never null)
     */
    public IScope getScope();
    
    /** Set the scope of the name.
     * 
     * This doesn't do refactoring!
     * 
     * @param scope the scope (must not be null)
     */
    public void setScope(IScope scope);
}
