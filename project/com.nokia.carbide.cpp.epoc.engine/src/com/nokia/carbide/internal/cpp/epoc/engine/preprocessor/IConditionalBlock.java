/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.preprocessor;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ISourceRegion;

import org.eclipse.jface.text.IDocument;

/**
 * This interface defines a region of nodes that share a 
 * common condition.
 *
 */
public interface IConditionalBlock {
	String toString();
	
	IASTPreprocessorStatement getCondition();
	IConditionalBlock getParent();
	IConditionalBlock[] getChildren();
	void setParent(IConditionalBlock block);
	void addChild(IConditionalBlock block);

	IASTNode getStartNode();
	IASTNode getEndNode();
	
	IASTNode[] getNodes();
	
	/** Get the span containing the non-ppstmts */
	ISourceRegion getSpan();
	/** Get the span including any pp-stmts */
	public ISourceRegion getFullSpan();

	void clear();

	/**
	 * @param node
	 */
	void addNode(IASTNode node);

	/**
	 * @param currentBlock
	 */
	void removeChild(IConditionalBlock currentBlock);

	/**
	 * @param visitor
	 * @return IViewRegionVisitor#VISIT_xxx code
	 */
	int accept(IConditionalBlockVisitor visitor);

	/**
	 * Reset the contained node list.
	 */
	void clearNodes();

	/**
	 * Tell whether the block spans the given region.
	 * @param region
	 * @return
	 */
	boolean containsRegion(ISourceRegion region);
	
	/**
	 * Tell whether the given region contains the node.
	 */
	boolean isContainedInRegion(ISourceRegion region);

	/**
	 * Tell if this block contains another (including itself).
	 * @param block
	 * @return true: block is equal to recipient or contains it (recursively)
	 */
	boolean contains(IConditionalBlock block);

	/**
	 * Get the first document the block is contained in
	 * @return
	 */
	IDocument getFirstDocument();

	/**
	 * Get the #if nesting depth of the block.  This ignores any nesting of blocks
	 * that #include files influence.
	 * @return depth, where 0 is top-level, 1 if inside parallel #if/#elif/#else blocks, etc.
	 */
	int getIfDepth();
}
