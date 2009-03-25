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
 * A type which contains a list of other declarations
 * (usu. a struct)
 *  
 * 
 *
 */
public interface IAstCompositeTypeSpecifier extends IAstDeclaration {
	/** Get the member list */
	public IAstListNode getMemberList();
	
    /** Get the members of this type */
    public IAstMemberDeclaration[] getMembers();
    
    /** Add a new member */
    public void addMember(IAstMemberDeclaration decl);

    /** Find a member by name
     * <p>
     * This performs a shallow search and does not look into
     * enclosing composite types.  Use getScope() and
     * IScope#search(String) for lexical searches.  
     */
    public IAstMemberDeclaration findMember(String name);

    /** Get the scope.  This may not be used to modify the scope
     * without also calling this#setDirty(true). 
     */
    public IScope getScope();

}
