/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.sdt.component.property;

import java.util.Iterator;

	/**
	 * Interface onto a property value that provides
	 * read-only indexed access and the ability to
	 * insert items by index
	 *
	 */
public interface ISequencePropertyValue {

	public static final int AT_END = Integer.MAX_VALUE;
	
	/**
	 * Returns a view of the sequence as a property value source
	 * sequence elements appear as properties with ids as strings
	 * corresponding to zero-based index values
	 */
	IPropertyValueSource getValueSource();	
	
	/**
	 * Returns the property path for the given element
	 */
	String getElementPath(int index);

	/**
	 * @return the number of items in the sequence
	 */
	int size();
	
	/**
	 * Returns the given item in the sequence
	 */
	Object get(int index);
	
	/**
	 * Removes the item at the given index
	 */
	void remove(int index);
	
	/**
	 * Moves the item from its current index to the new index
	 */
	void move(int index, int newIndex);
	
	/**
	 * Removes all items
	 */
	void clear();

	/**
	 * Returns an non-modifying iterator over the sequence
	 */
	Iterator iterator();
	
	/**
	 * Add a new non-localized string value
	 * @return the item's index
	 */
	int addStringLiteral(int index, String value);
	
	/**
	 * Add a new localized string value
	 * @return the item's index
	 */
	int addLocalizedString(int index, String value);

	/**
	 * Add a new component reference string value
	 * @return the item's index
	 */
	int addComponentReferenceString(int index, String value);

	/**
     * Create a new child element for a sequence and insert it
     * at the given position.
     * @return new object of element type
     */
	IPropertyValueSource addChildValueSource(int index);
}
