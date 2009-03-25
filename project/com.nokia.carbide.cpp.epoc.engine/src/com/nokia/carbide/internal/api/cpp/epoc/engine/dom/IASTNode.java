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

import com.nokia.cpp.internal.api.utils.core.*;

/**
 * This is the basis of any DOM.  Its intent is to represent
 * a portion of source (possibly spanning multiple documents)
 * that forms a unit of syntax in a parse tree.
 * 
 *
 */
public interface IASTNode {
	public static final IASTNode[] NO_CHILDREN = new IASTNode[0];
	
	/** Return raw representation of text. */
	String toString();

	/**
	 * Get the identifier that helps identify this node (when other 
	 * otherwise equal copies may exist).  
	 * @return id or null
	 */
	Object getId();

	/**
	 * Set an identifier that helps identify the node (when other
	 * otherwise equal copies may exist).
	 * @param id
	 */
	void setId(Object id);

	/**
	 * Test equality without reference to document, path, or region.
	 */
	boolean equalValue(IASTNode node);

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
	
	/** Get parent, or null. */
	IASTNode getParent();
	
	/** Set parent */
	void setParent(IASTNode parent);
	
	/** Get children, or empty. */
	IASTNode[] getChildren();
	
	/** Create a deep copy of the node.  Preferred over #clone() */
	IASTNode copy();
	
    /** Tell whether the node is dirty (changed with respect to original source)
     * @see #isDirtyTree()
     * @see #hasDirtySource()
     */
    public boolean isDirty();

    /** Tell whether the node or children are dirty
     * @see #hasDirtySourceTree()
     */
    public boolean isDirtyTree();

    /** Set the node's dirty flag 
     */
    public void setDirty(boolean dirty);

    /** Set the node and children's dirty flag 
     */
    public void setDirtyTree(boolean dirty);

	/**
	 * Tell whether the source is dirty, e.g., if the source info
	 * is missing, or the node is dirty.
	 */
	public boolean hasDirtySource();

	/**
	 * Tell whether the source or this node or any children 
	 * is dirty, e.g., if the source info is missing.
	 */
	public boolean hasDirtySourceTree();

    /**
     * Visit the tree.
     * @return the final IASTVisitor#VISIT_xxx code
     * (only IASTVisitor#VISIT_ABORT is useful to test for)
     */
    int accept(IASTVisitor visitor);
    
    /**
     * Get the translation unit owning this node.  May be null.
     */
    IASTTranslationUnit getTranslationUnit();
    
    /**
     * Rewrite the node's contents, taking into account whether the
     * contents are dirty, clean, or mixed (dirty children).
     */
    void rewrite(IRewriteHandler handler);
    
	/**
	 * Set the source info equal to another node's source.  If the other node
	 * is <code>null</code>, source info is cleared.
	 */
	void copySourceInfo(IASTNode node);
 
	/**
	 * Get new text representation (i.e. constructed from scratch)
	 */
	String getNewText();
	
	/**
	 * Get the original text from the document, or null if no document
	 */
	String getOriginalText();
	
	/**
	 * Get the source location for the node.  If a new node if completely
	 * new, its ISourceLocation will be null.  
	 */
	ISourceRegion getSourceRegion();

	/**
	 * Set the source location.
	 * @param region null or ISourceLocation
	 */
	void setSourceRegion(ISourceRegion region);

	/**
	 * Get human-readable source reference for the node.
	 * @return String, never null
	 */
	String getSourceReference();
	

	/**
	 * Get a message location for this node.  Looks up through parents
	 * until it finds one with source.  May return null.
	 * @return MessageLocation or null
	 */
	MessageLocation getMessageLocation();
	
	/**
	 * If this DOM was derived from another DOM, returns the array of 
	 * nodes that were the basis for this node.  This mapping is
	 * expected to exist at only one level of the DOM (e.g., statements),
	 * not necessarily at any higher or lower levels.
	 * @return non-empty array of sibling nodes or null
	 */
	IASTNode[] getSourceNodes();
	
	/**
	 * If this DOM was derived from another DOM, set the array of 
	 * sibling nodes that were the basis for this node.  This mapping is
	 * expected to exist at only one level of the DOM (e.g., statements),
	 * not necessarily at any higher or lower levels.
	 * @param nodes non-empty array of sibling nodes or null
	 */
	void setSourceNodes(IASTNode[] nodes);
	
}
