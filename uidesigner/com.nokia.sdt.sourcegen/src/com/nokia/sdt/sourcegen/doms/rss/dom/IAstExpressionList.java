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

import java.util.*;

/**
 * List of expressions (e.g. an array initializer).
 * The owner of this node should control the source formatting.
 * 
 * 
 *
 */
public interface IAstExpressionList extends IAstExpression {
    /** Return ordered list of expression nodes */
    public IAstExpression[] getList();
    
    /** Add a node to the end of the expression list */
    public void addExpression(IAstExpression expr);
    
    /**
     * Add a list of expressions
     */
    public void addAllExpressions(List<IAstExpression> elist);
    
	/**
	 * Get the numbered expression
	 * @throws IllegalArgumentException if index is too large
	 */
	public IAstExpression getExpression(int index);
	
	/**
	 * Get the length
	 */
	public int size();
	
	/**
	 * Overwrite the existing entry at the given index.
	 * @param index
	 * @param expr
	 * @throws IllegalArgumentException if index is too large
	 */
	public void setExpression(int index, IAstExpression expr);
	

	/**
	 * Remove an expression, unparenting it
	 */
	public void removeExpression(IAstExpression expression);
	
	/**
	 * Get an iterator.
	 */
	public Iterator<IAstExpression> iterator();
	
	/**
	 * Get a list iterator.
	 */
	public ListIterator<IAstExpression> listIterator();

	/**
	 * Clear the contents, unparenting all elements
	 */
	public void clear();

}
