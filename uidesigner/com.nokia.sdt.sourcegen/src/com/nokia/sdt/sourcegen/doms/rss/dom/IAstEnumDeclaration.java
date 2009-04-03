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
 * An "enum" declaration
 * 
 * <pre>
 * <b>
 * enum foo {
 *      aValue = 2,
 *      anotherValue = aValue+3 
 * }
 * </b>
 * </pre>
 * 
 * 
 *
 */
public interface IAstEnumDeclaration extends IAstDeclaration, IAstNameHolder, IAstTopLevelNode {
    /** Get the enum name (may be null) */
    public IAstName getName();
    
    /** Set the enum name (may be null) */
    public void setName(IAstName name);
    
    /** Get the enumerator list node */
    public IAstListNode getEnumeratorList();
    
    /** Get the list of enumerators */
    public IAstEnumerator[] getEnumerators();
    
    /** Add an enumerator */
    public void addEnumerator(IAstEnumerator enumerator);

    /** Find an enumerator by name */
    public IAstEnumerator findEnumerator(String name);

    /** Remove enumerator */
    public void removeEnumerator(IAstEnumerator enumerator);

   /** Get the scope.  This may not be used to modify the scope
     * without also calling this#setDirty(true). 
     */
    public IScope getScope();

	/**
	 * Get the effective value of the enumeration
	 * @return value, or null if uncalculable
	 */
	public IAstLiteralExpression getEffectiveValue(IAstEnumerator enm);

	/**
	 * Get the last enumerator.
	 * @return enum or null
	 */
	public IAstEnumerator getLastEnumerator();

	/**
	 * Remove an enumerator, ensuring that any start value is not lost.
	 * If the given enum has an initializer, it is moved down to the next
	 * enum, if it does not already have an initializer.
	 * @param enm the enumerator to delete
	 */
	public void removeEnumeratorPreservingStartValues(IAstEnumerator enm);
}
