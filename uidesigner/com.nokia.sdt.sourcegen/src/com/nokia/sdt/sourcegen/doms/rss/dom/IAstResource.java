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
 * Base interface for a resource definition.  The key information
 * is the type of resource being instantiated and the list of
 * initializers for the resource.
 * @see IAstResourceDefinition for "RESOURCE FOO r_foo {...}"
 * @see IAstResourceExpression for "myfield = FOO {...} "
 * 
 *
 */
public interface IAstResource extends IAstNode, IAstListElement {
    /** Get resource type */
    public IAstStructDeclaration getStructType();

    /** Set resource type.  This clears the initializers list. */
    public void setStructType(IAstStructDeclaration type);

    /** Get the list of initializers */
    public IAstListNode getInitializerList();
    
    /** Get the initializer list */
    public IAstMemberInitializer[] getInitializers();
    
    /** Clear out the initializer list */
    public void clearInitializers();
    
    /** Find an initializer for the given member name */
    public IAstMemberInitializer findMemberInitializer(String memberName);
    
    /** Add an initializer to the list, replacing any
     * existing initializer
     * 
     * @param init
     * @throws IllegalArgumentException if init is already in list
     * or init is not a member of the resource's struct
     */
    public void addInitializer(IAstMemberInitializer init);

    /** Remove an initializer from the list
     * 
     * @param init
     * @throws IllegalArgumentException if init is not in list
     */
    public void removeInitializer(IAstMemberInitializer init);

    /** Insert an initializer after another one
     * 
     * @param after the initializer after which to insert (null means start of list)
     * @param init the initializer
     * @throws IllegalArgumentException if after is not null and not in the list, or if init is not a member of
     * the resource's struct
     */
    public void insertInitializer(IAstMemberInitializer after, IAstMemberInitializer init);

}
