/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom;

import java.util.List;

/**
 * A node representing a list of nodes.
 *
 */
public interface IASTListNode<T> extends IASTNode, List<T> {
	/** Get the string that separates elements of the list (may be null) */
	String getSeparator();
	/** Set the string that separates elements of the list (may be null) */
	void setSeparator(String separator);

	/**
	 * Find nodes contained in the given region.
	 * <p>
	 * Only nodes with regions are returned, of course.
	 * @param sourceRegion
	 */
	//IASTNode[] getNodesContainedIn(IDocument document, IRegion region);
	IASTNode[] getNodesContainedIn(ISourceRegion sourceRegion);

	/**
	 * Find nodes containing the given region.
	 * <p>
	 * Only nodes with regions are returned, of course.
	 * @param document TODO
	 */
	//IASTNode[] getNodesContaining(IDocument document, IRegion region);
	
	/**
	 * Remove a range of nodes in the list.  These are compared by reference
	 * equality so they must be in the list!
	 * @param nodes a set of entries present in the list (null entries allowed)
	 * @return index of first removed node, or list size for an empty node list
	 */
	int removeNodes(IASTNode[] nodes);
	/**
	 * Insert copies of a range of nodes at the given index.
	 * @param idx
	 * @param nodes a set of nodes which will be {@link IASTNode#copy() copied} into the list;
	 * may contain null entries
	 * @return array of copied nodes
	 */
	IASTNode[] insertNodeCopies(int idx, IASTNode[] nodes);
	/**
	 * Insert one node after another.  
	 * @param after node, must be in list (or null for insert at start)
	 * @param newNode
	 */
	void insertNode(IASTNode after, IASTNode newNode);
	/**
	 * Insert one node before another.  
	 * @param newNode
	 * @param before node, must be in list (or null for insert at end)
	 */
	void insertBeforeNode(IASTNode newNode, IASTNode before);
	
	/**
	 * Must implement to compare all variant fields (including
	 * document, path, region).
	 * @see Object#equals(java.lang.Object)
	 */
	boolean equals(Object obj);
	
	/**
	 * Must implement to satisfy requirement that if a.equals(b),
	 * then a.hashCode() == b.hashCode().
	 * @see Object#hashCode()
	 */
	int hashCode();

}
