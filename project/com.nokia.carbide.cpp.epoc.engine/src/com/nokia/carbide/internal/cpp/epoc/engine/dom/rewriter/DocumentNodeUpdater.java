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
package com.nokia.carbide.internal.cpp.epoc.engine.dom.rewriter;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IDocumentSourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IMultiDocumentSourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ISourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

/**
 * This handler emits the current text of the DOM.<p>
 * 1) For undirty node trees with source, the original parsed text 
 * is emitted.<p>
 * 2) For undirty nodes with source and dirty children, the 
 * segments of the node are interpreted so as to correlate existing
 * text with text until the dirty children are found, whereupon
 * their text is newly generated.<p>
 * 3) For dirty nodes or nodes without source, newly generated text
 * is emitted.
 * <p>
 * If a node refers to a different document, that document is updated
 * separately with DocumentUpdater.
 * 
 * @see DocumentUpdater
 *
 */
public class DocumentNodeUpdater extends NodeRewriter {
	private IDocument document;
	private final IPreprocessorResults ppResults;
	
	public DocumentNodeUpdater(IPreprocessorResults ppResults, IDocument document) {
    	super();
    	Check.checkArg(document);
    	this.document = document;
    	this.ppResults = ppResults;
    	
    	// set the line delimiter from the document
    	try {
			String eol = document.getLineDelimiter(0);
			setEOL(eol);
		} catch (BadLocationException e) {
		}
    }
	
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.NewTextHandler#handleAstNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    @Override
    public void emitNode(IASTNode node) {
    	if (node instanceof IASTTranslationUnit &&
    			((IASTTranslationUnit)node).getMainDocument() != null 
    			&& ((IASTTranslationUnit)node).getMainDocument() != document) {
    		DocumentUpdater.updateDocuments(ppResults, node);
    		return;
    	}
    	
        if (!node.hasDirtySourceTree()) {
        	// Entire node tree is clean
        	needIndent = false;
        	produceCleanNode(node);
        } 
        else if (!node.hasDirtySource() && !(node instanceof IASTListNode)) {
        	// Node is clean but not children
        	needIndent = false;
        	updateNode(node);
        }
        else {
        	// Need to rewrite node (and children) entirely
        	rewriteNode(node);
        }
    }
    
    private void produceCleanNode(IASTNode node) {
		enterNode(node);
		
		emitText(node.getOriginalText(), false);
   	
    	leaveNode(node);
    }

	/**
	 * Update the source for a clean node with dirty children.
	 * @param node
	 */
    private void updateNode(IASTNode node) {

        enterNode(node);

        if (node.getSourceRegion() instanceof IMultiDocumentSourceRegion) {
        	// bail
        	rewriteNode(node);
        } else {
	        // Update the text by keeping literal text and updating IAstNodes
	        partialUpdateNode(node, node.getSourceRegion());
        }
        
        leaveNode(node);
	}

	/**
	 * Update a node whose structure has not changed by interleaving
	 * previously existing text with the updated contents of dirty
	 * child nodes.
	 * @param node
	 * @param sourceRegion
	 * @param inner
	 */
	private void partialUpdateNode(final IASTNode node, final ISourceRegion sourceRegion) {
		// Iterate the segments, ignoring formatting and literal text segments.
		// Find the child IAstNode which is dirty and emit it specially.
		// Otherwise, emit the original text.
		
		// These indices track the range of text within node/sourceRange that
		// are unmodified and should be emitted raw.
		
		class PartialUpdateRewriteHandler implements IRewriteHandler {
			int innerIdx = 0;
			int innerIdxPrev = 0;
			boolean needSpace = false;
			boolean needNewline = false;
			
			public void run(IASTNode node) {
				// don't try anything fancy yet with rewriting and macros...
				// too hard to track the various cases of spanning an entire node,
				// parts of a node, children only, etc...
				/*
				if (containsMacroExpansion(node)) {
		        	if (needSpace)
		        		emitSpaceIfMissing();
					DocumentNodeUpdater.this.rewriteNodeTree(node);
					return;
		        }*/
				
				
				node.rewrite(this);
				
				// Dump any interim text
				IDocumentSourceRegion sourceHead = sourceRegion.getInclusiveHeadRegion();
				int innerIdx = sourceHead.getRegion().getLength();
				if (innerIdx > innerIdxPrev) {
					String tail;
					try {
						tail = sourceHead.getDocument().get(
								sourceHead.getRegion().getOffset() + innerIdxPrev,
								innerIdx - innerIdxPrev);
						DocumentNodeUpdater.this.emitText(tail);
					} catch (BadLocationException e) {
						EpocEnginePlugin.log(e);
						Check.checkState(false);
					}
				}
				
				if (needNewline) {
					emitNewlineIfMissing();
				} else if (needSpace) {
					emitSpaceIfMissing();
				}
			}

			public void emitText(String text) {
				// ignore
			}

			public void emitSpace() {
				needSpace = true;
			}

			public void emitNewline() {
				needNewline = true;
			}

			public void emitWrappingHint() {
				// ignore
			}

			public void emitNode(IASTNode subNode) {
				IDocumentSourceRegion sourceHead = sourceRegion.getInclusiveHeadRegion();
				
				// Get the node's position in the parent
				IDocumentSourceRegion subHead = null;
				if (subNode.getSourceRegion() != null) {
					subHead = subNode.getSourceRegion().getInclusiveHeadRegion();
				}
				IRegion subRange = null;
				if (subHead != null) {
					subRange = subHead.getRegion();
				}
				
				if (subRange != null) {
					if (sourceHead.getDocument() != subHead.getDocument()) {
						// dump end of document
						String innerText;
						try {
							int offs = sourceHead.getRegion().getOffset() + innerIdxPrev;
							innerText = sourceHead.getDocument().get(
									offs,
									sourceHead.getDocument().getLength() - offs);
							DocumentNodeUpdater.this.emitText(innerText);
						} catch (BadLocationException e) {
							EpocEnginePlugin.log(e);
							Check.checkState(false);
						}
						
						innerIdx = subRange.getOffset();
						innerIdxPrev = innerIdx;
					} else {
						innerIdx = subRange.getOffset() - sourceHead.getRegion().getOffset();
						
					}
				}

				// Dump any interim text
				if (innerIdx > innerIdxPrev) {
					String innerText;
					try {
						innerText = sourceHead.getDocument().get(
								sourceHead.getRegion().getOffset() + innerIdxPrev,
								innerIdx - innerIdxPrev);
						DocumentNodeUpdater.this.emitText(innerText);
						innerIdxPrev = innerIdx;
					} catch (BadLocationException e) {
						EpocEnginePlugin.log(e);
						Check.checkState(false);
					}
				}

				// Skip the original node's length 
				if (subRange != null) {
					innerIdxPrev += subRange.getLength();
				}
				
		        if (containsNonSpanningMacroExpansion(subNode)) {
		        	if (needSpace)
		        		emitSpaceIfMissing();
					DocumentNodeUpdater.this.rewriteNodeTree(subNode);
					return;
		        }
				DocumentNodeUpdater.this.emitNode(subNode);
			}
			
			/* (non-Javadoc)
			 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler#indent(int)
			 */
			public void indent(int amount) {
				// ignore
			}
			
		}

		PartialUpdateRewriteHandler handler = new PartialUpdateRewriteHandler();
		handler.run(node);

	}

	/**
	 * Tell if the node contains a macro expansion which spans part of the
	 * node's source but is not encapsulated in one of its children.  
	 * @param subNode
	 * @return
	 */
	protected boolean containsNonSpanningMacroExpansion(IASTNode subNode) {
		return ppResults != null && ppResults.nodeContainsNonSpanningVariantMacros(subNode);
	}

	protected boolean containsMacroExpansion(IASTNode subNode) {
		return ppResults != null && ppResults.nodeReferencesVariantMacros(subNode);
	}

	/**
	 * Rewrite the source for a dirty node.
	 * @param node
	 */
	protected void rewriteNode(IASTNode node) {
		enterNode(node);
		// check for missing newlines
		if (node instanceof IASTTopLevelNode) {
			emitNewlineIfMissing();
		}
		node.rewrite(this);
		leaveNode(node);
	}

	/**
	 * Rewrite the source for a dirty node tree, without using any existing text.
	 * @param node
	 */
	protected void rewriteNodeTree(IASTNode node) {
		if (node instanceof IASTTopLevelNode) {
			emitNewlineIfMissing();
		}
		emitText(node.getNewText(), true);
	}
	
	private void enterNode(IASTNode node) {
    }

	private void leaveNode(IASTNode node) {
    }

}