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
/**
 * 
 */
package com.nokia.sdt.sourcegen.doms.rss.dom;

import java.util.*;


/**
 * A node that contains a homogenous list of items.
 * 
 *
 */
public interface IAstListNode extends IAstNode {
	/** 
	 * This interface allows an owner of AstListNode to
	 * have control over the way text segments are generated.
	 */
	interface ITextSegmentGenerator {
		/** Provide a list of text segments for the items in the list */
		public List getTextSegments(IAstListNode listNode);
	}

	/**
	 * Set the text segment generator
	 * @param generator
	 */
	public void setTextSegmentGenerator(ITextSegmentGenerator generator);

	/**
	 * Get the base type
	 */
	Class getBaseItemType();
	
    /** 
     * Add a node to the list
     * 
     * @param node
     * @throws IllegalArgumentException if node is already in list
     * or not instanceof base type
     */
 	void addItem(IAstNode node);
	/**
	 * Remove node from list
	 * @param node
     * @throws IllegalArgumentException if node not in list
     * or not instanceof base type
	 */
	void removeItem(IAstNode node);
	/**
	 * Insert one item after another
     * @param after the node after which to insert (null means start of list)
     * @param node the node
     * @throws IllegalArgumentException if after is not null and not in the list
     * or if node is invalid
     * or not instanceof base type
	 */
	void insertItem(IAstNode after, IAstNode node);
	
	/**
	 * Insert the given node before the other.
	 * @param node
	 * @param before node before which to insert (null means end of file)
	 */
	void insertBeforeFileNode(IAstNode node, IAstNode before);

	/**
	 * Tell if the node is in the list
	 * @param node
	 * @return
	 */
	boolean containsItem(IAstNode node);
	
	/**
	 * Get last item in list
	 * @return last item or null
	 */
	IAstNode getLastItem();
	
	/**
	 *	Clear all items from the list 
	 */
	void clearItems();
	
	/**
	 * Iterate children
	 */
	ListIterator listIterator();

	/**
	 * Copy the list of items. 
	 * @return shallow copy of items in list
	 */
	List copyItems();

	/**
	 * @return
	 */
	Iterator iterator();

	/**
	 * @param node
	 * @return
	 */
	public int indexOf(IAstNode node);
	
	public IAstNode get(int index);

	/**
	 * @return
	 */
	public int size();
	
}
