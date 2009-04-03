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

import com.nokia.sdt.sourcegen.ISourceFormatter;
import com.nokia.sdt.sourcegen.core.ISourceFile;
import com.nokia.sdt.sourcegen.core.ISourceRange;


/** 
 * Base interface for all nodes in the DOM.
 * <p>
 * This layout, naming convention, and general method pattern
 * is based in structure on the CDT DOM.  There is <i>some</i> 
 * creativity here, though.  :)
 * 
 * 
 *
 */
public interface IAstNode {
    public static final IAstNode[] NO_CHILDREN = new IAstNode[] { };
    
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

    /** Get parent of this node */
    public IAstNode getParent();

    /** Set parent of this node 
     * <p>
     * A node is parented whenever it is owned by the parent.
     * Usually a setter that passes an IAstNode to another
     * will also assign the parent.  
     */
    public void setParent(IAstNode node);

    /**Get the owned children of this node.  Each child's
     * #getParent() returns this.
     * @return array of children (never null) 
     */
    public IAstNode[] getChildren();
    
    /** Get references to other nodes from this node.
     * This set includes nodes which are not owned (i.e.
     * shared types and names).
     * This includes the same nodes as getChildren().
     */ 
    public IAstNode[] getReferencedNodes();
    
    /**
     * Get the precise range of source for the node, 
     * as defined by the syntactical tokens that comprise it.  
     * This range may be regenerated without disturbing 
     * surrounding whitespace.
     * <p>
     * This may return null when we either (1) do not care to
     * rewrite contents to disk (i.e. to save memory) or when
     * (2) a new node has been created, whose location will
     * be determined by position in relation to other similar nodes.
     * <p>
     * We can distinguish these two kinds of nodes by the
     * IAstSourceFile#isReadOnly() check.
     * 
     * @return ISourceRange, may be null
     */
    public ISourceRange getSourceRange();

    /**
     * Set the range for the tokens that
     * define a node.  This sets the extended range as
     * well.
     * @param range updated range; may be null
     */
    public void setSourceRange(ISourceRange range);

    /** 
     * Get the entire range of source the node covers,
     * including associated whitespace before or after it.
     * The tree of defined source ranges for a file
     * does NOT cover every character in the file -- some
     * ranges are unaccounted for.
     * <p>
     * This may return null when we either (1) do not care to
     * rewrite contents to disk (i.e. to save memory) or when
     * (2) a new node has been created, whose location will
     * be determined by position in relation to other similar nodes.
     * <p>
     * We can distinguish these two kinds of nodes by the
     * IAstSourceFile#isReadOnly() check.
     * 
     * @return ISourceRange, may be null
     */
    public ISourceRange getExtendedRange();

    /**
     * Set the source range for a node, including whitespace.
     * @param range updated range; may be null
     */
    public void setExtendedRange(ISourceRange range);

    /** Tell whether the source (range) for a node is dirty
     * because the node is dirty or 
     * source has never been written for it.
     * @return true if node has been modified 
     * or has no source range
     * @see #isDirty()
     * @see #getSourceRange()
     * @see #getExtendedRange()
     */
    public boolean hasDirtySource();

    /** Tell whether the source (range) for a node is dirty
     * because the node or its children are dirty or 
     * source has never been written for them.
     * @return true if node or children have been modified 
     * or have no source range
     * @see #isDirtyTree()
     * @see #getSourceRange()
     * @see #getExtendedRange()
     */
    public boolean hasDirtySourceTree();

    /** 
     * Construct the text for a node based on its content
     * by returning a list of text and/or IAstNode objects
     * which can be catenated to provide complete text for
     * the node.
     * <p>
     * Various SEGMENT_xxx constants may be used to provide
     * formatting hints; otherwise, text is literally emitted.
     * 
     * @return a list of String and IAstNode objects.  Each
     * string is taken literally and for IAstNode, the 
     * IAstNode#getCurrentText() method is called to provide
     * text.
     */
    public Object[] getTextSegments();

    /**
     * Run the segment handler over this node's segments.
     */
    public void handleSegments(ISegmentHandler handler);
    
    /** 
     * Get the original, unpreprocessed source text for this node
     * by reading the source stored in the source range
     * @return text constructed from entirely from original text,
     * or null for a new node (getSourceRange() == null)
     */
    public String getOriginalText();

    /**
     * Forcibly recreate the source from scratch
     * @param formatter the text formatter, may be null for default
     */
    public String getNewText(ISourceFormatter formatter);

    /**
     * Get the current source text for this node, either the
     * original file text or the newly generated text
     *
     * @param formatter the text formatter, may be null for default
     * @return text constructed from original text (for
     * unmodified nodes) and text generated anew (for
     * modified/new nodes)
     */
    public String getCurrentText(ISourceFormatter formatter);


    /**
     * Get the current text, while updating the source ranges
     * for each node (recursively) given that the current node
     * is emitted starting at the given file, offset, and line.
     * This undirties all the nodes.
     * 
     * @param formatter the text formatter, may be null for default
     * @param file start file
     * @param offset start offset
     * @param line start line
     * @return text constructed from original text (for
     * unmodified nodes) and text generated anew (for
     * modified/new nodes)
     */
    public String getCurrentTextUpdating(ISourceFormatter formatter, ISourceFile file, int offset, int line);

    /**
     * Find the translation unit owning this node
     */
    public ITranslationUnit getTranslationUnit();
    
    /**
     * Find the IAstSourceFile owning this node
     */
    public IAstSourceFile getAstSourceFile();
    
    /** Accept visitor
     * 
     * @param visitor impl
     */
    public void accept(AstVisitor visitor);

    /**
     * Accept a reference
     * @param visitor
     */
    public void acceptReference(AstVisitor visitor);
    

	/**
	 * Find a node recursively from the current node.
	 * @param node
	 * @return true: node found under self
	 */
	public boolean isNodeInTree(IAstNode node);
    
}

