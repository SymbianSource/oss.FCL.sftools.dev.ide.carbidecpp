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

/**
 * The base interface of nodes which represent the entire contents
 * of a file.  
 * 
 * 
 *
 */
public interface IAstSourceFile extends IAstNode {
    /** Set the translation unit */
    public void setTranslationUnit(ITranslationUnit unit);
    
    /** Get the referenced source file (may be null) */
    public ISourceFile getSourceFile();
    /** Set the referenced source file (must not be null) */
    public void setSourceFile(ISourceFile file);

    /** Get read-only flag. */
    public boolean isReadOnly();
    
    /** 
     * Set read-only flag.  This tells whether we try
     * to rewrite the file's contents in rewrite().
     */
    public void setReadOnly(boolean readOnly);
    
    /** Rewrite the file from the contents of the node
     * 
     * @return true if any changes in text recorded (thus,
     * even if source is dirty and nodes changed, if these
     * have no effect on the file text, this returns false)
     */
    public boolean rewrite(ISourceFormatter formatter);

    /**
     * Remove all the contents contributed by this file
     * (e.g. this is a duplicate of another #include)
     */
    public void removeAllContents();
    
    /**
     * Move the toplevel nodes from the given source file and hold them
     * in this file.
     */
    public void moveContents(IAstSourceFile file);

    /**
     * Flatten out the file, bringing all existing #included content into this tree
     */
    public void flatten();

    /**
     * Get the file-level nodes defined by this file,
     * meaning top-level nodes and preprocessor nodes 
     * These define all of the real and preprocessor content of a file. 
     */
    public IAstNode[] getFileNodes();

    /**
     * Get the file-level nodes defined by this file which
     * match the given interface.
     */
    public IAstNode[] getFileNodes(Class klass);

    /** Add a file-level node to the file in the most
     * appropriate place (generally, nodes of the same type are
     * kept together -- use insertNode to place a node in an
     * exact location due to dependencies between nodes).
     * <p>
     * This detects whether the node has a source range and
     * inserts relative to other nodes in that case.  A new node
     * may be added only between the extended source ranges of existing
     * nodes.
     * <p>You must update any translation unit, if present, to notice
     * they changes. 
     * @param node must be descended from IAstTopLevelNode or IAstPreprocessorNode
     * @see ITranslationUnit#refresh()
     */
    public void addFileNode(IAstNode node);

    /** Add a file-level node to the end of the file
     * <p>You must update any translation unit, if present, to notice. 
     * @param node must be descended from IAstTopLevelNode or IAstPreprocessorNode
     * @see ITranslationUnit#refresh()
     */
    public void appendFileNode(IAstNode node);

    /** Insert a file-level node after another node 
     * <p>You must update any translation unit, if present, to notice. 
     * @param after node after which to insert (null means start of file); must be descended from IAstTopLevelNode or IAstPreprocessorNode
     * @param node must be descended from IAstTopLevelNode or IAstPreprocessorNode
     * @see ITranslationUnit#refresh()
     */
    public void insertFileNode(IAstNode after, IAstNode node);


    /** Insert a file-level node before another node 
     * <p>You must update any translation unit, if present, to notice. 
     * @param node must be descended from IAstTopLevelNode or IAstPreprocessorNode
     * @param before node before which to insert (null means end of file); must be descended from IAstTopLevelNode or IAstPreprocessorNode
     * @see ITranslationUnit#refresh()
     */
    public void insertBeforeFileNode(IAstNode node, IAstNode before);

    /**
     * Remove a file-level node from the file 
     * <p>You must update any translation unit, if present, to notice. 
     * @param node must be descended from IAstTopLevelNode or IAstPreprocessorNode
     * @see ITranslationUnit#refresh()
     */
    public void removeFileNode(IAstNode node);
    
    /**
     * Search for the existence of a node in the file
	 * @return
	 */
	public boolean findFileNode(IAstNode node);
    
    /**
     * Set the read-only flag for the given file and all its 
     * includes (recursively)
     * @param readOnly
     */
    public void setReadOnlyRecursive(boolean readOnly);

    /**
     * Get the file list node
     * @return
     */
    public IAstListNode getFileNodeList();
    

	/**
	 * Find nodes containing the given offset
	 * @param offset
	 * @return array of nodes from largest to smallest
	 */
	public IAstNode[] findNodesAt(int offset);
    
}
