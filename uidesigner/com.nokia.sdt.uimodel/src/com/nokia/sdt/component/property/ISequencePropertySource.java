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

import org.eclipse.ui.views.properties.IPropertySource;

import java.util.Iterator;
import java.util.List;

/**
 * Extension of IPropertySource to support sequence properties
 */
public interface ISequencePropertySource extends IPropertySource{

	public static final int AT_END = Integer.MAX_VALUE;
	
	/**
	 * @return the number of items in the sequence
	 */
	int size();
	
	/**
	 * Returns the given item in the sequence
	 */
	Object get(int index);
	
	/**
	 * Return a list containing all the current items.
	 * The list does not reflect subsequent additions or removals.
	 */
	List toList();
	
	/**
	 * Move the item at a given index to a new index
	 */
	void move(int index, int newIndex);
	
	/**
	 * Removes the given item
	 */
	void remove(int index);
	
	/**
	 * Removes all items
	 */
	void clear();
	
	/**
	 * Return true if elements are compound properties
	 */
	boolean isCompoundElement();

	/**
	 * Returns an non-modifying iterator over the sequence
	 */
	Iterator iterator();
	
	/**
	 * Adds a new simple property at the given index. The value
	 * cannot be an IPropertySource
	 * @param index location to insert the value
	 * @param the new value, or null to use the default value
	 * @throws IllegalArgumentException if value is an IPropertySource or
	 * the value does not match the element type, e.g. if the elements are
	 * compound properties.
	 */
	int addSimpleProperty(int index, Object value);
	
    /**
     * Adds a new compound property for a sequence and inserts it
     * at the given position.
     * @throws IllegalStateException if the element type is not
     * a compound property
     */
    IPropertySource addCompoundProperty(int index);
    
    /**
     * Returns a property path for the given element
     * @param index
     * @return
     */
    String getElementPath(int index);
    
}
