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

package com.nokia.carbide.internal.cpp.epoc.engine.model;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListHolder;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTVisitor;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTUtils;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.ConditionalBlockUtils;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalBlock;
import com.nokia.cpp.internal.api.utils.core.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Update the preparser translation unit with changes from the view.
 * <p>
 * The view's commit phase provides a list of commands, each operating
 * on language DOM nodes, which will be applied to the preparser DOM, taking 
 * into account use of macros and placement of #ifs. 
 * <p>
 * The view can freely generate changes to what it sees as an expanded, unconditional
 * DOM, and this class interprets the changes with respect to the actual
 * macro-using and conditionalized structure to preserve the effective 
 * contents for the current view filter (though changes may affect other 
 * configurations as well, in this current model, which doesn't shield 
 * changes in #ifs).  
 * <p>
 * The general requirements are, we respect the existing (possibly-)
 * conditionalized structure of the source and avoid making any changes to such
 * conditionals or their contents.  Any "add" changes become global.
 * <p>
 * Also, we assume that the preparser DOM referenced by the source nodes of
 * language nodes are cached, so any changes to the preparser DOM are made in
 * shadow copies of those DOMs.  The results of {@link #sync()} contain an
 * updated mapping of translation units.
 */
public class ViewDOMSynchronizer<Model extends IOwnedModel<View>, View extends IView> {
	
	private IASTTranslationUnit mainPreparserTu;

	private IPreprocessorResults ppResults;
	private List<IViewModification> modifications;
	private Map<IASTNode, IASTNode> currentNodeMap;
	private List<IMessage> messages;
	
	public ViewDOMSynchronizer(IASTTranslationUnit preparserTu,
			IASTTranslationUnit langTu,
			IPreprocessorResults ppResults,
			List<IViewModification> modifications) {
		this.mainPreparserTu = preparserTu;
		this.ppResults = ppResults;
		this.modifications = modifications;
		this.currentNodeMap = new HashMap<IASTNode, IASTNode>();
		this.messages = new ArrayList<IMessage>();
		
		// Assign ids to all the nodes so we can track them as they are
		// modified.  Modifications refer to original nodes, not the
		// copied or transplanted nodes, so only the id will be available
		// to quickly find the current version.
		langTu.accept(new IASTVisitor() {
			public int visit(IASTNode node) {
				currentNodeMap.put(node, node);
				return VISIT_CHILDREN;
			}
			
		});
	}
	
	public ISynchronizerResults sync() {
		// Go through changes in order.  Changes must not depend on
		// language node trees that other modifications have deleted.
		for (IViewModification modification : modifications) {
			if (modification instanceof IViewAddModification) {
				IASTNode parent = ((IViewAddModification) modification).getParent();
				Check.checkState(parent != null);
				Pair<IASTNode, IASTNode> anchors = ((IViewAddModification) modification).getInsertAnchors();
				if (anchors == null)
					handleAddModification(parent, modification.getNodes(), null, null);
				else
					handleAddModification(parent, modification.getNodes(), anchors.first, anchors.second);
			} else if (modification instanceof IViewDeleteModification) {
				handleDeleteModification(modification.getNodes());
			} else if (modification instanceof IViewChangeModification) {
				IASTNode parent = ((IViewChangeModification) modification).getParent();
				IASTNode[] replacements = ((IViewChangeModification) modification).getReplacementNodes();
				if (replacements == null) {
					replacements = modification.getNodes();
				}
				handleChangeModification(parent, modification.getNodes(), replacements);
			} else if (modification instanceof IViewReorderModification) {
				handleReorderModification(modification.getNodes());
			} else {
				Check.checkState(false);
			}
		}

		final Map<File, IASTTranslationUnit> updatedFileMap = new HashMap<File, IASTTranslationUnit>();
		if (mainPreparserTu.isDirtyTree()) {
			updatedFileMap.put(mainPreparserTu.getMainLocation().toFile(), mainPreparserTu);
		}
		for (File file : ppResults.getReferencedFiles()) {
			IASTTranslationUnit tu = ppResults.getTranslationUnitFor(file);
			if (tu != null && tu.isDirtyTree()) {
				updatedFileMap.put(file, tu);
			}
		}
		
		return new ISynchronizerResults() {

			public Map<File, IASTTranslationUnit> getUpdatedFileMap() {
				return updatedFileMap;
			}
			
			/* (non-Javadoc)
			 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ISynchronizerResults#getMessages()
			 */
			public List<IMessage> getMessages() {
				return messages;
			}
			
		};
	}
	
	/**
	 * Get the preparser DOM parent for the API parent parameter.
	 * @param parent either a TU or a list nodes in a TU, and either
	 * a language DOM or a preparser DOM
	 * @return a preparser DOM node; if referencing a TU, its #getNodes() 
	 */
	private IASTNode getTargetPreparserDOMParent(IASTNode parent) {
		IASTNode[] origParents = parent.getSourceNodes();
		if (origParents != null) {
			if (origParents[origParents.length - 1] instanceof IASTTranslationUnit) {
				parent = ppResults.getOriginalTranslationUnit();
			}
		}
		
		if (parent instanceof IASTTranslationUnit) {
			parent = ((IASTTranslationUnit) parent).getNodes();
		}
		
		return parent;
	}

	/**
	 * Add new nodes to the end of the parent.  The parent may refer to the
	 * language DOM or the preparser DOM.  The latter is used to target
	 * content to a specific file.
	 * @param parent
	 * @param nodes
	 */
	
	private void handleAddModification(IASTNode parent, IASTNode[] nodes, IASTNode before, IASTNode after) {
		parent = getTargetPreparserDOMParent(parent);
		IASTListNode list = ((IASTListHolder) parent).getList();
		IConditionalBlock conditionalBlock = null;
		IASTNode insertNode = null;
		for (IASTNode node : nodes) {
			int index = -1;
			if (before != null || after != null) {
				if (after != null) {
					index = getCurrentIndex(list, after, true);
					insertNode = after;
					if (index == -1 && before != null) {
						index = getCurrentIndex(list, before, false);
						insertNode = before;
					}
				} else if (before != null) {
					index = getCurrentIndex(list, before, false);
					insertNode = before;
				}
				if (index == -1) {
					EpocEnginePlugin.log(new IllegalArgumentException("Cannot find reference node(s) for inserting (before = "  //$NON-NLS-1$
							+ before + ", after = " + after)); //$NON-NLS-1$
					index = list.size();
					conditionalBlock = null;
				} else if (insertNode != null) {
					// Find the conditional block, if any, containing the insert node.
					// We want to add near it but not inside it.
					//
					// The insert node may or may not have a source region;
					// if it does, fine; if not, it must be another added node, which
					// we assume was itself added outside a conditional block already.
					conditionalBlock = ConditionalBlockUtils.findBlockContaining(
							ppResults.getRootBlock(),
							insertNode.getSourceRegion());
				}
			} else {
				index = list.size();
				conditionalBlock = ppResults.getRootBlock(); 
			}
			
			// Warn if adding inside a conditional block.  We can't really
			// safely find the "right" place without knowledge of the language
			// (a conditional may encompass one statement or a lot of statements;
			// heck, it might even be in a whole-file #ifdef/#endif).
			
			if (conditionalBlock != null && conditionalBlock.getCondition() != null) {
				messages.add(ASTFactory.createMessage(
						IMessage.WARNING,
						"ViewDOMSynchronizer.AddingInsideConditional", //$NON-NLS-1$
						new Object[] { node.getNewText() },
						insertNode != null ? insertNode.getMessageLocation() :
							conditionalBlock.getEndNode().getMessageLocation()));
			}
			list.add(index, node);
			currentNodeMap.put(node, node);
		}
	}

	private int getCurrentIndex(IASTListNode list, IASTNode langNode, boolean after) {
		// optimistic search: assume lang node has been replaced into the DOM
		IASTNode currentLangNode = currentNodeMap.get(langNode);
		Check.checkState(currentLangNode != null);
		for (int index = 0; index < list.size(); index++) {
			if (list.get(index) == currentLangNode)
				return index + (after ? 1 : 0);
		}
		
		// search for ppstmts
		IASTNode[] ppStmts = langNode.getSourceNodes();
		if (ppStmts != null) {
			for (int index = 0; index < list.size(); index++) {
				if (list.get(index) == ppStmts[0])
					return index + (after ? ppStmts.length : 0);
			}
		}
		
		return -1;
	}

	/** 
	 * Find the node in the current (modified) preparser DOM closest to the
	 * given language node.  If the corresponding source node has been
	 * deleted, look either after or before its original position for another
	 * candidate node.
	 * @param langNode language DOM node
	 * @param after if original node deleted then: true: look beyond it, false: look before it
	 * @return a preparser DOM IASTNode or null for end-of-file
	 */
	/*
	private IASTNode locateClosestCurrentNodeAfter(IASTNode langNode) {
		IASTNode[] originalNodes = langNode.getSourceNodes();
		Check.checkState(originalNodes != null);
		
		// see if any of the original nodes are still in the preparser DOM,
		// looking at the last first since we place stuff after all the nodes
		for (int i = originalNodes.length; i-- >= 0; ) {
			if (originalNodes[i].getParent() != null) {
				return originalNodes[i];
			}
		}
		
		// The original node(s) are gone, so find something close by in source.
		// To do this we need to go back to the preparser parent of the language
		// node.
		Check.checkState(langNode.getParent() != null);
		IASTNode[] originalParentNodes = langNode.getParent().getSourceNodes();
		Check.checkState(originalParentNodes != null);
		IASTListNode<IASTNode> originalParent = null;
		
		// These may be split in files
		for (int i = 0; i < originalParentNodes.length; i++) {
			if (originalParentNodes[i].getParent() != null) {
				originalParent = (IASTListNode<IASTNode>) originalParentNodes[i];
				break;
			}
		}
		
		// A list or the like has been deleted?  If so, statement 
		// is stranded (e.g. a modified SOURCE statement inside a
		// START BITMAP that was deleted) -- likely a bug...?
		if (originalParent == null) {
			Check.checkState(false);
			return null;
		}
		
		// Iterate the parent and find the last statement after the original
		// target for a match
		IASTNode afterNode = null;
		for (ListIterator<IASTNode> iter = originalParent.listIterator(originalParent.size());
			iter.hasPrevious();) {
			IASTNode node = iter.previous();
			ISourceRegion region = node.getSourceRegion();
			if (region.getExclusiveHeadRegion().compareTo(
					
		}
		return null;
	}
	*/
	
	/**
	 * 
	 */
	private void handleDeleteModification(IASTNode[] nodes) {
		for (IASTNode node : nodes) {
			IASTNode[] ppStmts = node.getSourceNodes();
			Check.checkState(ppStmts != null);
			
			// see if these are all in the same parent
			IASTNode parent = null;
			for (IASTNode ppStmt : ppStmts) {
				if (parent == null) {
					parent = ppStmt.getParent();
				} else if (parent != ppStmt.getParent()) {
					parent = null;
					break;
				}
			}
			
			if (ASTUtils.isConditionalBlockInLangNode(ppResults.getRootBlock(), node)) {
				// comment out the block instead of deleting it
				commentOutBlock(parent.getTranslationUnit(), ppStmts);
			} else {
				// if all in same parent, use more efficient path
				if (parent != null) {
					((IASTListHolder)parent).getList().removeNodes(ppStmts);
				} else {
					// else slog through
					for (IASTNode ppStmt : ppStmts) {
						IASTListNode parentList = ((IASTListHolder)ppStmt.getParent()).getList();
						parentList.removeNodes(new IASTNode[] { ppStmt });
					}
				}
			}
		}
	}

	private void handleChangeModification(IASTNode parentNode, IASTNode[] fromNodes, IASTNode[] toNodes) {
		IASTListNode parent = null;
		if (parentNode != null) {
			parent = (IASTListNode) ((IASTListHolder)getTargetPreparserDOMParent(parentNode)).getList();
		}
		
		// See if all the nodes are together modifiable in place.  
		// If not, they will all be commented out and added anew
		List<IASTNode> ppStmtsList = new ArrayList<IASTNode>();
		boolean modified = false;
		for (IASTNode node : fromNodes) {
			Check.checkArg(node.getParent().getParent() instanceof IASTTranslationUnit);
			ppStmtsList.addAll(Arrays.asList(node.getSourceNodes()));
			modified |= node.isDirtyTree();
		}
		if (ppStmtsList.isEmpty())
			return;
		
		IASTNode[] ppStmts = (IASTNode[]) ppStmtsList.toArray(new IASTNode[ppStmtsList.size()]);

		// this implicitly checks that all the statements
		// are in the same TU
		boolean replaceInPlace = !modified || validateInPlaceReplacement(ppStmts);
		
		IASTListNode preparserParent = ((IASTListHolder) ppStmts[0].getParent()).getList();
		if (parent == null) {
			parent = preparserParent;
		}
		
		IASTTranslationUnit preparserTu = ppStmts[0].getTranslationUnit();
		if (replaceInPlace) {
			// Merrily replace the existing PP nodes with
			// their corresponding language nodes.
			
			int ppIdx = preparserParent.removeNodes(ppStmts);

			// now replace the nodes with the new ones
			IASTNode[] copies;
			if (parent == preparserTu.getNodes()) 
				copies = parent.insertNodeCopies(ppIdx, toNodes);
			else
				copies = parent.insertNodeCopies(parent.size(), toNodes);
			
			for (IASTNode copy : copies) {
				currentNodeMap.put(copy, copy);
			}
		} else {
			// The node spans a view region or has macro references.
			// Comment the original pp nodes out and insert the expanded node.

			IConditionalBlock conditionalBlock = ConditionalBlockUtils.findBlockContaining(
					ppResults.getRootBlock(),
					fromNodes[fromNodes.length - 1].getSourceRegion());
			Check.checkState(conditionalBlock != null);

			// comment out the original PP nodes
			IASTNode anchor = commentOutBlock(preparserTu, ppStmts);
			
			// forget the old pp-nodes; they're inactive now
			for (IASTNode oldPPStmt : ppStmts) {
				oldPPStmt.setId(null);
			}

			// and replace the new nodes in place
			preparserTu.getNodes().insertNodeCopies(preparserTu.getNodes().indexOf(anchor) + 1, toNodes);
		}
	}

	/**
	 * Tell whether it's safe to replace the nodes associated with a node
	 * with new contents.
	 * @param langNode
	 * @return true: safe, false: not safe
	 */
	private boolean validateInPlaceReplacement(IASTNode[] ppStmts) {
		// if the statement spans TUs, we can't handle it
		IASTTranslationUnit preparserTu = null;
		for (IASTNode ppStmt : ppStmts) {
			if (preparserTu == null) {
				preparserTu = ppStmt.getTranslationUnit();
			} else if (preparserTu != ppStmt.getTranslationUnit()) {
				return false;
			}
		}
		
		IConditionalBlock rootBlock = ppResults.getRootBlock();
		boolean langNodeUsedMacros = ppResults.nodesUseVariantMacros(ppStmts);
		boolean langNodeSpansRegions = ConditionalBlockUtils.nodesSpanRegions(rootBlock, ppStmts);
		// TODO: see if problems are modified...?
		boolean langNodeSpansProblems = //langNode instanceof IASTProblemNode || 
						ASTUtils.nodesSpanProblems(ppStmts);
		
		return (!langNodeSpansRegions && !langNodeUsedMacros && !langNodeSpansProblems);
	}

	/**
	 * @param ppStmts
	 */
	protected IASTNode commentOutBlock(IASTTranslationUnit preparserTu, IASTNode[] ppStmts) {
		IASTListNode listNode = ((IASTListHolder) ppStmts[0].getParent()).getList();
		Check.checkState(listNode == ((IASTListHolder) ppStmts[ppStmts.length - 1].getParent()).getList());
		listNode.insertBeforeNode(
				ASTFactory.createPreprocessorIfStatement(ASTFactory
						.createPreprocessorTokenStream("0")), //$NON-NLS-1$
				ppStmts[0]);
		IASTNode endifNode = ASTFactory.createPreprocessorEndifStatement(); 
		listNode.insertNode(ppStmts[ppStmts.length - 1], endifNode);
		return endifNode;
	}

	/**
	 * Ensure the model reflects the ordering of the given language nodes.  
	 * @param newNodeOrdering new ordering of language nodes
	 */
	private void handleReorderModification(IASTNode[] newNodeOrdering_) {
		// Expand the node list to reference the preparser nodes,
		// filtering out those we can't reorder (yet) if they're 
		// involved in conditional blocks.
		if (newNodeOrdering_.length < 2)
			return;
		
		IConditionalBlock rootBlock = ppResults.getRootBlock();
		List<IASTNode> preParserNodeOrdering = new ArrayList<IASTNode>();
		IConditionalBlock commonBlock = null;
		
		// Get the highest common conditional block.  Count nodes at the root of another document
		// as roots of the main document, since we'll move them here.
		//
		for (IASTNode node : newNodeOrdering_) {
			node = currentNodeMap.get(node);
			Check.checkState(node != null);

			IConditionalBlock theBlock = getTargetReorderedBlock(rootBlock, node);
			if (commonBlock == null) {
				commonBlock = theBlock;
			} else if (commonBlock == theBlock || commonBlock.contains(theBlock)) {
				// ok, same root common
			} else if (theBlock.contains(commonBlock)) {
				// widen
				commonBlock = theBlock;
			} else {
				// separate blocks, perhaps siblings
				// do nothing
			}
		}
		
		// Now eliminate nodes not present in the same block
		//
		for (IASTNode node : newNodeOrdering_) {
			IConditionalBlock theBlock = getTargetReorderedBlock(rootBlock, node);
			if (theBlock != commonBlock) {
				messages.add(ASTFactory.createMessage(
						IMessage.WARNING,
						"ViewDOMSynchronizer.CannotReorderAcrossConditionals", //$NON-NLS-1$
						new Object[] { node.getNewText() },
						node.getMessageLocation()));
				//EpocEnginePlugin.log(new Exception(""),  //$NON-NLS-1$
				//		"Ignoring reordering of node contained in different conditional region: " + node); //$NON-NLS-1$
				continue;
			}
			
			node = currentNodeMap.get(node);
			Check.checkState(node != null);
			
			IASTNode[] origNodes = node.getSourceNodes();
			
			if (origNodes != null) {
				// verify sanity
				for (IASTNode origNode : origNodes) {
					Check.checkState(origNode.getTranslationUnit() != null);
				}
				
				// still unmodified node
				preParserNodeOrdering.addAll(Arrays.asList(origNodes));
			} else {
				// new or modified/copied node: add self
				Check.checkState(node.getTranslationUnit() != null);
				preParserNodeOrdering.add(node);
			}
		}
		
		// The reordered nodes may be distributed among different TUs.  
		// Move them all to the same file.
		//
		// Also, all nodes must either be top-level nodes in TUs or in the same list node.
		//
		IASTTranslationUnit targetTu = null;
		IASTListNode<IASTNode> targetList = null;
		for (IASTNode node : preParserNodeOrdering) {
			if (targetList == null) {
				targetList = (IASTListNode<IASTNode>) ((IASTListHolder) node.getParent()).getList();
			}
			IASTTranslationUnit tu = node.getTranslationUnit();
			Check.checkState(tu != null && (node.getParent() == tu.getNodes() || node.getParent() == targetList));
			if (targetTu == null) {
				targetTu = tu;
			}
			// favor main TU if referenced
			if (tu == mainPreparserTu) {
				targetTu = tu;
				targetList = (IASTListNode) tu.getNodes();
			}
		}
		if (targetTu == null) {
			targetTu = mainPreparserTu;
		}
		if (targetList == null) {
			targetList = (IASTListNode) targetTu.getNodes();
		}

		// Move all items to the same TU
		//
		for (Iterator<IASTNode> iter = preParserNodeOrdering.iterator(); iter.hasNext(); ) {
			IASTNode node = iter.next();
			IASTTranslationUnit tu = node.getTranslationUnit();
			if (tu != targetTu) {
				((IASTListHolder)node.getParent()).getList().remove(node);
				targetTu.getNodes().add((IASTTopLevelNode) node);
				ASTUtils.copyAwaySourceRegionContents(node, targetTu.getMainLocation());
			}
		}
		
		// Now go through the list and reorder as needed
		//
		ListIterator<IASTNode> listIter = targetList.listIterator();
		while (listIter.hasNext() && preParserNodeOrdering.size() > 0) {
			int listIndex = listIter.nextIndex();
			IASTNode node = listIter.next();
			
			// note: use #contains() / #equals() here since node may have been copied
			int idx = preParserNodeOrdering.indexOf(node);
			if (idx == -1)
				continue;

			// swap this node with the one we want in this position
			if (idx != 0) {
				IASTNode swapNode = preParserNodeOrdering.remove(0);
				swapNode.setParent(null);
				int swapIndex = targetList.indexOf(swapNode);
				if (swapIndex == -1) {
					EpocEnginePlugin.log(new Exception(""),  //$NON-NLS-1$
							"Internal error when reordering: cannot locate node: " + swapNode); //$NON-NLS-1$
					continue;
				}
				node.setParent(null);
				targetList.set(listIndex, swapNode);
				targetList.set(swapIndex, node);
			} else {
				preParserNodeOrdering.remove(0);
			}
			/*
			if (idx != 0) {
				// move entry to end of current block
				IConditionalBlock block = ConditionalBlockUtils.findBlockForNode(ppResults.getRootBlock(), node);
				listIter.remove();
				
				if (block != null && block.getEndNode() != null) {
					int endIdx = targetList.indexOf(block.getEndNode());
					// might have been moved to this document, meaning not found in block
					if (endIdx >= 0) {
						targetList.add(endIdx, node);
					} else {
						targetList.add(node);
					}
					if (listIndex == targetList.indexOf(node)) {
						// no change: can't move outside block
						// TODO: warn about this
						EpocEnginePlugin.log(new Exception(""),  //$NON-NLS-1$
								"Ignoring reordering of node contained in conditional block: " + node); //$NON-NLS-1$
						preParserNodeOrdering.remove(idx);
					}
				} else {
					targetList.add(node);
				}
				
				listIter = targetList.listIterator(listIndex);
			} else {
				preParserNodeOrdering.remove(0);
			}
			*/
		}
	}

	private IConditionalBlock getTargetReorderedBlock(
			IConditionalBlock rootBlock, IASTNode node) {
		IConditionalBlock theBlock = null;

		if (node.getSourceRegion() == null) {
			theBlock = rootBlock; // XXX: assumption that we add outside conditional blocks
		} else {
			theBlock = ConditionalBlockUtils.findBlockContaining(rootBlock, node.getSourceRegion());
			if (theBlock.getParent() == null || theBlock.getParent() == rootBlock)
				theBlock = rootBlock;
		}
		if (theBlock.getCondition() == null && theBlock.getParent() != null)
			theBlock = theBlock.getParent();
		if (theBlock.getParent() == null || theBlock.getParent() == rootBlock)
			theBlock = rootBlock;

		return theBlock;
	} 
	/**
	 * Ensure the model reflects the ordering of the given language nodes.  
	 * @param nodes new ordering of language nodes
	 */
	void handleReorderModification_old(IASTNode[] nodes) {
		// Expand the node list to reference the preparser nodes,
		// filtering out those we can't reorder (yet) if they're 
		// involved in conditional blocks.
		if (nodes.length < 2)
			return;
		
		IConditionalBlock rootBlock = ppResults.getRootBlock();
		List<IASTNode> preParserNodeOrdering = new ArrayList<IASTNode>();
		for (IASTNode node : nodes) {
			node = currentNodeMap.get(node);
			Check.checkState(node != null);
			
			IASTNode[] origNodes = node.getSourceNodes();
			
			if (origNodes != null) {
				if (ConditionalBlockUtils.nodesSpanRegions(rootBlock, origNodes)) {
					// TODO: report warning
					continue;
				}
				
				// verify sanity
				for (IASTNode origNode : origNodes) {
					Check.checkState(origNode.getTranslationUnit() != null);
				}
				
				// still unmodified node
				preParserNodeOrdering.addAll(Arrays.asList(origNodes));
			} else {
				// new or modified/copied node: add self
				Check.checkState(node.getTranslationUnit() != null);
				preParserNodeOrdering.add(node);
			}
		}
		
		// The reordered nodes may be distributed among different TUs.  
		// Move them all to the same file.
		//
		// Also, all nodes must either be top-level nodes in TUs or in the same list node.
		//
		IASTTranslationUnit targetTu = null;
		IASTListNode<IASTNode> targetList = null;
		for (IASTNode node : preParserNodeOrdering) {
			if (targetList == null) {
				targetList = (IASTListNode<IASTNode>) ((IASTListHolder) node.getParent()).getList();
			}
			IASTTranslationUnit tu = node.getTranslationUnit();
			Check.checkState(tu != null && (node.getParent() == tu.getNodes() || node.getParent() == targetList));
			if (targetTu == null) {
				targetTu = tu;
			}
			// favor main TU if referenced
			if (tu == mainPreparserTu) {
				targetTu = tu;
				targetList = (IASTListNode) tu.getNodes();
			}
		}
		if (targetTu == null) {
			targetTu = mainPreparserTu;
		}
		if (targetList == null) {
			targetList = (IASTListNode) targetTu.getNodes();
		}

		// Move all items to the same TU
		//
		for (Iterator<IASTNode> iter = preParserNodeOrdering.iterator(); iter.hasNext(); ) {
			IASTNode node = iter.next();
			IASTTranslationUnit tu = node.getTranslationUnit();
			if (tu != targetTu) {
				if (ConditionalBlockUtils.findBlockForNode(rootBlock, node).getCondition() != null) {
					// don't move around conditional blocks
					// TODO: warn
					EpocEnginePlugin.log(new Exception(""),  //$NON-NLS-1$
							"Ignoring cross-file move of node contained in conditional block: " + node); //$NON-NLS-1$
					iter.remove();
				} else {
					((IASTListHolder)node.getParent()).getList().remove(node);
					targetTu.getNodes().add((IASTTopLevelNode) node);
					ASTUtils.copyAwaySourceRegionContents(node, targetTu.getMainLocation());
				}
			}
		}
		
		// Now go through the list and reorder as needed
		ListIterator<IASTNode> listIter = targetList.listIterator();
		while (listIter.hasNext()) {
			int listIndex = listIter.nextIndex();
			IASTNode node = listIter.next();
			// note: use #contains() / #equals() here since node may have been copied
			int idx = preParserNodeOrdering.indexOf(node);
			if (idx == -1)
				continue;

			if (idx != 0) {
				// move entry to end of current block
				IConditionalBlock block = ConditionalBlockUtils.findBlockForNode(ppResults.getRootBlock(), node);
				listIter.remove();
				
				if (block != null && block.getEndNode() != null) {
					int endIdx = targetList.indexOf(block.getEndNode());
					// might have been moved to this document, meaning not found in block
					if (endIdx >= 0) {
						targetList.add(endIdx, node);
					} else {
						targetList.add(node);
					}
					if (listIndex == targetList.indexOf(node)) {
						// no change: can't move outside block
						// TODO: warn about this
						EpocEnginePlugin.log(new Exception(""),  //$NON-NLS-1$
								"Ignoring reordering of node contained in conditional block: " + node); //$NON-NLS-1$
						preParserNodeOrdering.remove(idx);
					}
				} else {
					targetList.add(node);
				}
				listIter = targetList.listIterator(listIndex);
			} else {
				preParserNodeOrdering.remove(0);
			}
		}
	}

	/**
	 * Insert the node in the region nearest the given region that has a "true"
	 * condition
	 * 
	 * @param block
	 * @param node
	 */
	protected void insertInTrueBlockNear(IConditionalBlock block, IASTTranslationUnit preparserTu, IASTNode[] nodes) {
		while (block.getParent() != null
				&& block.getParent().getCondition() != null)
			block = block.getParent();
		IASTNode endNode = block.getParent() != null ? block.getParent().getEndNode() : null;
		int idx; 
		if (endNode != null) {
			idx = preparserTu.getNodes().indexOf(endNode);
		} else {
			idx = preparserTu.getNodes().size();
		}
		preparserTu.getNodes().insertNodeCopies(idx, nodes);
	}
}
