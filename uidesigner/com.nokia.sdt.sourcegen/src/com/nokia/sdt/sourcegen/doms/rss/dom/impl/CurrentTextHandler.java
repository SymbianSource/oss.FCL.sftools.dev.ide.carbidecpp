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
package com.nokia.sdt.sourcegen.doms.rss.dom.impl;

import com.nokia.sdt.sourcegen.FormattingSegment;
import com.nokia.sdt.sourcegen.ISourceFormatter;
import com.nokia.sdt.sourcegen.core.ISourceRange;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode;
import com.nokia.sdt.utils.SubString;

/**
 * This handler emits the current text of the DOM.<p>
 * 1) For undirty node trees with source, the original parsed text 
 * is emitted.<p>
 * 2) For undirty nodes with source and dirty children, the 
 * segments of the node are interpreted so as to correlate existing
 * text with segments until the dirty children are found, whereupon
 * their text is newly generated.<p>
 * 3) For dirty nodes or nodes without source, newly generated text
 * is emitted.
 * 
 *
 */
class CurrentTextHandler extends NewTextHandler {
	
    CurrentTextHandler(ISourceFormatter formatter) {
    	super(formatter);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.NewTextHandler#handleAstNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    @Override
    public void handleAstNode(IAstNode node) {

        if (!node.hasDirtySourceTree()) {
        	// Entire node tree is clean
        	if (node.getExtendedRange().getLength() > 0)
        		handleText("");
        	produceCleanNode(node);
        } 
        else if (!node.hasDirtySource()) {
        	// Node is clean but not children
        	if (node.getExtendedRange().getLength() > 0)
        		handleText("");
        	updateNode(node);
        }
        else {
        	// Need to rewrite node (and children) entirely
    		handleText("");
        	rewriteNode(node);
        }
    }
    
	/* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.NewTextHandler#produceCleanNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    @Override
    public void produceCleanNode(IAstNode node) {
        ISourceRange sourceRange = node.getSourceRange();
		ISourceRange extRange = node.getExtendedRange();
		if (extRange == null)
			extRange = sourceRange;

		char[] extended = null;
		extended = extRange.getText().toCharArray();
    	SubString extendedHead = new SubString(extended, 0, sourceRange.getOffset() - extRange.getOffset());

		//if (extRange.getText().length() > 0)
		//	handleText(""); //$NON-NLS-1$

    	enterExtendedNode(node);

    	produceText(extendedHead);

    	// Prepare extended tail.
    	int tailOffs = extendedHead.length() + sourceRange.getLength();
    	int tailLength = extRange.getLength() - extendedHead.length() - sourceRange.getLength();
    	SubString extendedTail = new SubString(extended, tailOffs, tailLength);

		enterNode(node);
		
    	produceText(sourceRange.getText());
    	
    	leaveNode(node);

    	produceText(extendedTail);
    	
		leaveExtendedNode(node);
    }

	/**
	 * Update the source for a clean node with dirty children.
	 * @param node
	 */
    protected void updateNode(IAstNode node) {
        SubString extendedTail = null;

        //needsIndentPing = true;
//        handleText("");
        enterExtendedNode(node);

    	// Emit extended header.

        ISourceRange sourceRange = node.getSourceRange();
		ISourceRange outerRange = node.getExtendedRange();
		if (outerRange == null)
			outerRange = sourceRange;

		char[] extended = null;
		extended = outerRange.getText().toCharArray();
    	SubString extendedHead = new SubString(extended, 0, sourceRange.getOffset() - outerRange.getOffset());
    	produceText(extendedHead);

    	// Get the inner text
    	char[] inner = new SubString(extended, extendedHead.length(), sourceRange.getLength())
    		.toString().toCharArray();

    	// Prepare extended tail.
    	int tailOffs = extendedHead.length() + sourceRange.getLength();
    	int tailLength = outerRange.getLength() - extendedHead.length() - sourceRange.getLength();
    	extendedTail = new SubString(extended, tailOffs, tailLength);
        
        enterNode(node);

        // Update the text by keeping literal text and updating IAstNodes
        partialUpdateNode(node, sourceRange, inner);

        leaveNode(node);

        // Emit extended footer.
        
        if (extendedTail != null) {
        	produceText(extendedTail);
        }
        
        leaveExtendedNode(node);
    	
    	
	}

	/**
	 * Update a node whose structure has not changed by interleaving
	 * previously existing text with the updated contents of dirty
	 * child nodes.
	 * @param node
	 * @param sourceRange
	 * @param inner
	 */
	private void partialUpdateNode(IAstNode node, ISourceRange sourceRange, char[] inner) {
		// Iterate the segments, ignoring formatting and literal text segments.
		// Find the child IAstNode which is dirty and emit it specially.
		// Otherwise, emit the original text.

		// These indices track the range of text within node/sourceRange that
		// are unmodified and should be emitted raw.
		
		int innerIdx = 0;
		int innerIdxPrev = 0;
		
		Object[] segments = node.getTextSegments();
		for (int i = 0; i < segments.length; i++) {
			Object segment = segments[i];
			
			if (segment instanceof FormattingSegment) {
				
			} else if (!(segment instanceof IAstNode)) {
					
			} else {
				// Match embedded IAstNode
				IAstNode subNode = (IAstNode) segment;

				// Get the node's position in the parent
				ISourceRange subRange = subNode.getExtendedRange();
				if (subRange == null)
					subRange = subNode.getSourceRange();
				
				if (subRange != null) {
					innerIdx = subRange.getOffset() - sourceRange.getOffset();
				}

				// Dump any interim text
				if (innerIdx > innerIdxPrev) {
					SubString innerText = new SubString(inner, innerIdxPrev, innerIdx - innerIdxPrev); 
					produceText(innerText);
					innerIdxPrev = innerIdx;
				}

				// Skip the original node's length 
				if (subRange != null) {
					innerIdxPrev += subRange.getLength();
				}
				
				// Update or rewrite node
				handleAstNode(subNode);
			}
		}

		// Dump any interim text
		innerIdx = sourceRange.getLength();
		if (innerIdx > innerIdxPrev) {
			SubString tail = new SubString(inner, innerIdxPrev, innerIdx - innerIdxPrev);
			produceText(formatter.getReformattedText(tail));
		}
	}

	/**
	 * Rewrite the source for a dirty node.
	 * @param node
	 */
	protected void rewriteNode(IAstNode node) {
		if (node.getSourceRange() != null) {
			// emit extended range around rewritten guts
	        SubString extendedTail = null;
	    	// Emit extended header.
	        enterExtendedNode(node);

	        ISourceRange sourceRange = node.getSourceRange();
			ISourceRange outerRange = node.getExtendedRange();
			if (outerRange == null)
				outerRange = sourceRange;

			char[] extended = null;
			extended = outerRange.getText().toCharArray();
	    	SubString extendedHead = new SubString(extended, 0, sourceRange.getOffset() - outerRange.getOffset());
	    	produceText(extendedHead);

	    	// Prepare extended tail.
	    	int tailOffs = extendedHead.length() + sourceRange.getLength();
	    	int tailLength = outerRange.getLength() - extendedHead.length() - sourceRange.getLength();
	    	extendedTail = new SubString(extended, tailOffs, tailLength);
	        
	        enterNode(node);

	        // rewrite 
			node.handleSegments(this);

	        leaveNode(node);

	        // Emit extended footer.
	        
	        if (extendedTail != null) {
	        	produceText(extendedTail);
	        }
	        
	        leaveExtendedNode(node);
	  			
		} else {
//			maybePingFormatter();
//			handleText(""); //$NON-NLS-1$
			enterExtendedNode(node);
			enterNode(node);
			node.handleSegments(this);
			leaveNode(node);
			leaveExtendedNode(node);
		}
	}

	public void enterExtendedNode(IAstNode node) {
	}
    public void enterNode(IAstNode node) {
    }

    public void leaveNode(IAstNode node) {
    }

	public void leaveExtendedNode(IAstNode node) {
		
	}

}