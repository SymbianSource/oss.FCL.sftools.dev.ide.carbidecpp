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
package com.nokia.sdt.sourcegen.doms.rss.dom.impl;

import com.nokia.sdt.sourcegen.ISourceFormatter;
import com.nokia.sdt.sourcegen.core.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.util.Stack;

/**
 * Handle updating source text by storing clean nodes as
 * their original text and creating dirty nodes from scratch.
 * 
 *
 */
class CurrentTextUpdatingHandler extends CurrentTextHandler {
    
    ISourceFile file;
    int offset;
    Stack<Integer> offsetStack;
    
    CurrentTextUpdatingHandler(ISourceFormatter formatter, ISourceFile file, int offset, int line) {
    	super(formatter);
        this.file = file;
        this.offset = offset;
        offsetStack = new Stack<Integer>();
    }

    @Override
    public void produceCleanNode(IAstNode node) {

    	// prepare to bump all the offsets uniformly
    	ISourceRange extendedRange = node.getExtendedRange();
    	ISourceRange sourceRange = node.getSourceRange();
    	ISourceRange mainRange = extendedRange != null ? extendedRange : sourceRange;
    	Check.checkState(mainRange.getFile() == mainRange.getEndFile());
    	
    	// emit text...
    	super.produceCleanNode(node);

    	int endExtOffsetDelta = offsetStack.pop().intValue() - extendedRange.getEndOffset();
    	int endOffsetDelta = offsetStack.pop().intValue() - sourceRange.getEndOffset();
    	int offsetDelta = offsetStack.pop().intValue() - sourceRange.getOffset();
    	int extOffsetDelta = offsetStack.pop().intValue() - extendedRange.getOffset();

    	Check.checkState(offsetDelta == extOffsetDelta);
    	Check.checkState(endExtOffsetDelta == endOffsetDelta);

    	// and update ranges for this node and its children
    	bumpRangesRecursive(node, offsetDelta, endOffsetDelta);
    	
    	node.setDirty(false);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.CurrentTextHandler#updateNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    @Override
    protected void updateNode(IAstNode node) {
    	// prepare to bump the start offset...
    	ISourceRange extendedRange = node.getExtendedRange();
    	ISourceRange sourceRange = node.getSourceRange();
    	ISourceRange mainRange = extendedRange != null ? extendedRange : sourceRange;
    	Check.checkState(mainRange.getFile() == mainRange.getEndFile());
    	
    	// emit text...
    	super.updateNode(node);
    	
    	// and find the bump for the end offset...
    	int endExtOffsetDelta = offsetStack.pop().intValue() - extendedRange.getEndOffset();
    	int endOffsetDelta = offsetStack.pop().intValue() - sourceRange.getEndOffset();
    	int offsetDelta = offsetStack.pop().intValue() - sourceRange.getOffset();
    	int extOffsetDelta = offsetStack.pop().intValue() - extendedRange.getOffset();

    	Check.checkState(offsetDelta == extOffsetDelta);
    	Check.checkState(endExtOffsetDelta == endOffsetDelta);
    	
    	// and update ranges
    	bumpRanges(sourceRange, extendedRange, offsetDelta, endOffsetDelta);
    	
    	node.setDirty(false);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.CurrentTextHandler#rewriteNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    @Override
    protected void rewriteNode(IAstNode node) {
    	super.rewriteNode(node);
    	
    	int endExtOffsetVal = offsetStack.pop().intValue();
    	int endOffsetVal = offsetStack.pop().intValue();
    	int offsetVal = offsetStack.pop().intValue();
    	int extOffsetVal = offsetStack.pop().intValue();
    	
    	node.setSourceRange(new SourceRange(file, offsetVal, endOffsetVal - offsetVal)); 
    	node.setExtendedRange(new SourceRange(file, extOffsetVal, endExtOffsetVal - extOffsetVal));
    	node.setDirty(false);
    }
    
    @Override
    public void produceText(CharSequence text) {
        buffer.append(text);
        
        // update offset
        offset += text.length();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.CurrentTextHandler#enterExtendedNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    @Override
    public void enterExtendedNode(IAstNode node) {
    	super.enterExtendedNode(node);
    	offsetStack.push(new Integer(offset));
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.CurrentTextHandler#enterNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    @Override
    public void enterNode(IAstNode node) {
    	super.enterNode(node);
    	offsetStack.push(new Integer(offset));
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.CurrentTextHandler#leaveNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    @Override
    public void leaveNode(IAstNode node) {
    	super.leaveNode(node);
    	offsetStack.push(new Integer(offset));
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.CurrentTextHandler#leaveExtendedNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    @Override
    public void leaveExtendedNode(IAstNode node) {
    	super.leaveExtendedNode(node);
    	offsetStack.push(new Integer(offset));
    }

    /**
     * Bump ranges for a node and its children.
	 * @param node
	 * @param sourceRange
	 * @param extendedRange
	 * @param offsetDelta
     * @param endExtOffsetDelta 
     * @param extOffsetDelta 
	 * @param offsetDelta2
	 */
	private void bumpRangesRecursive(IAstNode node, int offsetDelta, int endOffsetDelta) {
    	ISourceRange extendedRange = node.getExtendedRange();
    	ISourceRange sourceRange = node.getSourceRange();
    	ISourceRange mainRange = extendedRange != null ? extendedRange : sourceRange;
    	if (mainRange != null) {
	    	Check.checkState(mainRange.getFile() == mainRange.getEndFile());
	    	bumpRanges(sourceRange, extendedRange, offsetDelta, endOffsetDelta);
    	
	    	IAstNode[] kids = node.getChildren();
	    	for (int i = 0; i < kids.length; i++) {
	    		bumpRangesRecursive(kids[i], offsetDelta, endOffsetDelta);
			}
    	}
	}

	/**
     * Bump source ranges by the given offsets.
     * @param sourceRange
     * @param extendedRange
     * @param offsetDelta
     * @param endOffsetDelta
     */
    private void bumpRanges(ISourceRange sourceRange, ISourceRange extendedRange, int offsetDelta, int endOffsetDelta) {
    	if (extendedRange != null) {
    		extendedRange.setOffsetRange(
    				extendedRange.getOffset() + offsetDelta, 
    				extendedRange.getEndOffset() + endOffsetDelta);
    	}
    	sourceRange.setOffsetRange(
					sourceRange.getOffset() + offsetDelta, 
					sourceRange.getEndOffset() + endOffsetDelta);
    }

}