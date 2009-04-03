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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;

import java.util.*;

/**
 * This class updates provides the framework for updating a view's language
 * DOM by generating a list of modifications based on changes detected
 * to the view.  These modifications are consumed by the ViewDOMSynchronizer
 * to effect the changes.  
 * <p>
 * Modifications represent high-level operations like changing, adding, 
 * deleting, and reordering, and ViewDOMSynchronizer applies these appropriately
 * to honor macros and conditional statements.    
 * <p>
 * During the construction of these modifications, the implementing class is allowed to change
 * the language DOM in place (particularly for Change modifications, or for
 * promoting low-level nodes to higher-level nodes to enable modifications
 * to be performed more safely), so this is a destructive operation.  Note,
 * however, that any changes to the language DOM are otherwise ignored!
 *
 */
public abstract class BaseStatementUpdater {

	protected ViewASTBase view;
	protected Collection<IASTStatement> knownStmts;
	protected ListIterator<? extends IASTTopLevelNode> statementIterator;
	private List<IViewModification> modifications;
	private List<IMessage> messages;
	
	public BaseStatementUpdater(ViewASTBase view, List<IViewModification> modifications, List<IMessage> messages) {
		this.view = view;
		this.knownStmts = view.getKnownStatements();
		this.modifications = modifications;
		this.messages = messages;
	}
	

	protected void deleteStatement(final IASTStatement stmt) {
		modifications.add(new DeleteModification(stmt));
	}
	
	protected void changeStatement(final IASTStatement stmt) {
		if (stmt.getParent() != view.getFilteredTranslationUnit().getNodes())
			return;
		modifications.add(new ChangeModification(stmt));
		
	}
	
	protected void changeStatement(final IASTStatement stmt, final IASTStatement[] nodes) {
		if (stmt.getParent() != view.getFilteredTranslationUnit().getNodes())
			return;
		modifications.add(new ChangeMultiModification(stmt, nodes));
		
	}
	protected boolean changedStatement(IASTStatement stmt) {
		for (IViewModification modification : modifications) {
			IASTNode[] nodes = modification.getNodes();
			for (IASTNode node : nodes) {
				if (node == stmt)
					return true;
			}
		}
		return false;
	}
	
	protected void addStatement(final IASTStatement stmt) {
		modifications.add(new AddModification(view.getFilteredTranslationUnit(), stmt));
		
	}
	protected void addStatement(final IASTStatement stmt, final Pair<IASTNode, IASTNode> insertAnchors) {
		modifications.add(new AddModification(view.getFilteredTranslationUnit(), stmt, insertAnchors));
		
	}
	
	/** Make an ordered map of model objects -> existing statements.
	* The ordering in the map will be used to drive the reordering modification
	* later on.  So if the model refers to new objects, store a null entry for those
	* until we later fix them up.
	*/
	abstract protected void scanStatements();
	
	protected <ModelType, NodeType extends IASTStatement>
	Map<ModelType, NodeType>
	scanStructuredBlocks(Map<ModelType, NodeType> modelStmtMap, 
			Map<NodeType, ModelType> stmtModelMap,
			List<ModelType> modelList, 
			Class klazz, 
			StructuredItemListConverter<NodeType, ModelType> converter) {
		
		Map<ModelType, NodeType> orderedModelStmtMap = new LinkedHashMap<ModelType, NodeType>();
		
		// rescan known entries and fix ordering
		for (ModelType model : modelList) {
			// assume no changes in model identity
			NodeType node = modelStmtMap.get(model);
			if (node == null) {
				// client may have replaced model object with a new one that
				// has the same key; look for an equivalent entry
				for (Map.Entry<ModelType, NodeType> entry : modelStmtMap.entrySet()) {
					if (converter.elementMatches(model, entry.getKey())) {
						node = entry.getValue();
						break;
					}
				}
			}
			orderedModelStmtMap.put(model, node);
		}
		
		// and assign mapping for known statements
		for (Map.Entry<ModelType, NodeType> entry : orderedModelStmtMap.entrySet()) {
			stmtModelMap.put(entry.getValue(), entry.getKey());
		}
		
		return orderedModelStmtMap;
	}

	protected boolean equalLiteralNode(IASTLiteralTextNode node, String text) {
		if ((node == null) != (text == null))
			return false;
		if (node == null && text == null)
			return true;
		return node.getValue().equals(text);
	}

	/**
	 * We update structured statements in place and rely on reordering
	 * modifications to maintain relative ordering.  
	 */
	protected<ModelType, NodeType extends IASTStatement>
	ModelType applyStructuredItemChange(Collection<ModelType> modelList,
			IASTStatement stmt,
			StructuredItemStatementListConverter<NodeType, ModelType> converter,
			Map<ModelType, NodeType> modelToStatementMap,
			Map<NodeType, ModelType> statementToModelMap) {
		ModelType model = statementToModelMap.get(stmt);
		
		if (model == null) {
			// deleted
			deleteStatement(stmt);
			return null;
		}
		
		ModelType existing = converter.fromNode((NodeType) stmt);
		if (existing != null) {
			
			if (model.equals(existing)) {
				// fine, leave it alone
				changeStatement(stmt);
			} else {
				// get any context needed based on the change
				IASTStatement context = null;
				if (converter.changeRequiresNewContext(existing, model)) {
					context = converter.createContextStatement(model);
				}
				// update node after context updated
				NodeType updatedNode = converter.toNode(model);
				if (updatedNode != null) {
					// update fields in existing statement from newly generated node
					converter.updateNode((NodeType)stmt, updatedNode);
					if (context != null) {
						converter.associateContextStatement(stmt, context);
						changeStatement(stmt, new IASTStatement[] { context, stmt });
					} else {
						changeStatement(stmt);
					}
				}
			}
		}
		// done with this
		modelList.remove(model);
		
		return model;
	}

	/**
	 * Update a single list argument statement. This handles removing obsolete
	 * elements, inserting in place if possible, and honoring the dictum to add
	 * new elements outside conditional blocks.
	 * <p>
	 * The AstListIterator implementation should handle changes with respect
	 * to intervening context statements (e.g. SOURCEPATH).
	 * @param stmt
	 *            the list statement
	 * @param converter
	 *            the converter to and from high level objects and AST nodes
	 * @param modelIterator
	 *            high-level object list
	 * @return true: statement was deleted, false: statement was changed
	 */
	protected <NodeType extends IASTLiteralTextNode, ModelType> boolean updateListStatement(
			IASTListHolder<NodeType> stmt,
			List<ModelType> modelList,
			ListIterator<ModelType> modelIter,
			ListStatementListConverter<NodeType, ModelType> converter) {

		if (modelIter == null) {
			// deleted
			deleteStatement((IASTStatement) stmt);
			return true;
		}
		
		//lastListStmts.put(EMMPStatement.valueOf(stmt.getKeywordName().toUpperCase()), stmt);
		
		ListIterator<IASTLiteralTextNode> nodeIter = (ListIterator) stmt.getList().listIterator(); 
		while (nodeIter.hasNext()) {
			NodeType node = (NodeType) nodeIter.next();

			ModelType origModel = converter.fromNode(node);
			if (origModel == null) {
				// unknown
				continue;
			}
			
			if (!modelIter.hasNext()) {
				// no match from model, delete
				nodeIter.remove();
				continue;
			}
			
			// match model against original
			ModelType model = modelIter.next();
			
			if (model.equals(origModel)) {
				// match
				modelIter.remove();
				continue;
			}
			
			NodeType modelElement = converter.toNode(model);
			
			
			// see if this element was deleted
			ListIterator<ModelType> otherModels = modelList.listIterator(modelIter.nextIndex()); 
			ModelType matchingModel = null;
			while (otherModels.hasNext()) {
				ModelType other = otherModels.next();
				NodeType otherElement = converter.toNode(other);
				if (otherElement != null && otherElement.equalValue(node)) {
					matchingModel = other;
					break;
				}
			}

			if (matchingModel == null) {
				// node no longer applies
				nodeIter.remove();
				modelIter.previous();
				continue;
			}
			
			// put model entry here
			if (modelElement != null) {
				nodeIter.previous();
				nodeIter.add(converter.toNode(model));
			}
			modelIter.remove();
		}
		
		// if statement is empty, delete it
		if (stmt.getList().isEmpty() && !converter.allowEmptyStatements()) {
			//lastListStmts.remove(stmtType);
			deleteStatement((IASTStatement) stmt);
			return true;
		} else {
			changeStatement((IASTStatement) stmt);
			return false;
		}
	}
	
	/**
	 * update a string list.
	 */
	/*
	protected void updateStringListStatement(IASTListHolder<IASTLiteralTextNode> statement,
			List<String> list,
			ListIterator<String> listIterator) { 
		updateListStatement(statement, list, listIterator,   
				new StatementListAdapter(new StringListConverter()) {

					public IASTListHolder createNewListStatement() {
						return ASTFactory.
					}
			
		});
	}*/

	protected void updateStatements() {
		statementIterator = view.getFilteredTranslationUnit().getNodes().listIterator();
		
		while (statementIterator.hasNext()) {
			IASTTopLevelNode tln = statementIterator.next();
			if (!(tln instanceof IASTStatement))
				continue;
			updateStatement((IASTStatement) tln);
		}
	}
	
	/**
	 * Update one statement, either by changing it in place and adding
	 * a change modification, or adding a delete modification.  A change
	 * modification may be added even if nothing changed, in orer to
	 * ensure homogeneity of the preparser DOM for post-update fixups.
	 * @param statement
	 */
	abstract protected void doUpdateStatement(IASTStatement statement);
	
	protected void updateStatement(IASTStatement statement) {
		if (!knownStmts.contains(statement)) {
			// either a problem or a repeated/ignored statement
			changeStatement(statement);
			return;
		}

		view.updateCurrentDirectory(statement);

		doUpdateStatement(statement);
	}

	protected <ModelType, NodeType extends IASTStatement> 
	void applyNewStructuredItemStatements(List<ModelType> modelList,
			StructuredItemStatementListConverter<NodeType, ModelType> converter,
			Map<ModelType, NodeType> orderedMap) {
		Iterator<ModelType> modelIter = modelList.iterator();
		while (modelIter.hasNext()) {
			ModelType model = modelIter.next();
			// find out where to add this
			Pair<IASTNode,IASTNode> insertAnchors = converter.getInsertAnchors();
			// get any context needed
			IASTStatement context = converter.createContextStatement(model);
			// create node after context updated
			NodeType statement = converter.toNode(model);
			if (statement != null) {
				if (context != null) {
					converter.associateContextStatement(statement, context);
					addStatement(context, insertAnchors);
					addStatement(statement);
				} else {
					addStatement(statement, insertAnchors);
				}
				orderedMap.put(model, statement);
			} else {
				orderedMap.remove(model);
			}
			modelIter.remove();
		}
		
	}
	
	protected <NodeType extends IASTLiteralTextNode, ModelType>
	void applyNewListArgumentStatement(ListIterator<ModelType> iter, 
			IASTListHolder<NodeType> lastStmt,
			ListStatementListConverter<NodeType, ModelType> converter) {
		if (!iter.hasNext())
			return;
		
		boolean changed = false;
		boolean isNew = false;
		while (iter.hasNext()) {
			ModelType model = iter.next();

			if (lastStmt == null
					|| view.isLangNodeInConditionalBlock(lastStmt)
					|| !converter.canAddToStatement(model)) {
				// add context
				IASTStatement context = converter.createContextStatement(model);
				if (context != null) {
					addStatement(context);
				}
				
				if (changed && !isNew && lastStmt != null) {
					// may have updated nodes already
					if (!changedStatement((IASTStatement) lastStmt))
						changeStatement((IASTStatement) lastStmt);
				}
				
				// make new statement
				lastStmt = converter.createNewListStatement();
				if (context != null) {
					converter.associateContextStatement((IASTStatement) lastStmt, context);
				}

				addStatement((IASTStatement) lastStmt);
				changed = false;
				isNew = true;
			}

			NodeType el = converter.toNode(model);
			if (el != null) {
				lastStmt.getList().add(el);
				changed = true;
			}
			iter.remove();
		}
		
		if (changed && !isNew) {
			// may have updated nodes already
			if (!changedStatement((IASTStatement) lastStmt))
				changeStatement((IASTStatement) lastStmt);
		}
	}
	
	/**
	 * Add new statements to the bld.inf.  The previous calls to
	 * #updateStatement() have already updated the view to delete
	 * view model elements corresponding to previously existing nodes in the TU,
	 * so anything left in the view is new.  Once finished, everything
	 * in the view should be reverted to empty. 
	 */
	abstract protected void applyNewStatements();

	/** Invoke applyReordering() for any re-orderable statements. */
	abstract protected void fixupOrdering();
	
	protected <ModelType, NodeType extends IASTStatement>
	void applyReordering(Map<ModelType, NodeType> modelToStatementMap,
			OrderedObjectMap<ModelType, NodeType> origModelToStatementMap,
			StructuredItemListConverter<NodeType, ModelType> converter) {
		// ensure all the values were filled in
		for (Iterator<Map.Entry<ModelType, NodeType>> iter = modelToStatementMap.entrySet().iterator(); iter.hasNext(); ) {
			Check.checkState(iter.next().getValue() != null);
		}

		Iterator<ModelType> origIter = origModelToStatementMap.orderedKeyIterator();
		Iterator<Map.Entry<ModelType, NodeType>> newIter = modelToStatementMap.entrySet().iterator();
		
		while (origIter.hasNext() && newIter.hasNext()) {
			Map<IPath, List<IASTNode>> newOrdering = new HashMap<IPath, List<IASTNode>>();
			
			// Step through the original ordering and new ordering and see if they differ:
			// only then do we add entries to the reordering
			//
			List<IASTNode> previousList = null;
			boolean warnedNoReordering = false;
			
			// Step the lists, and keep track of a set of original objects
			// and new objects whose positions don't match.  
			// When a mismatch occurs, add the items to the sets,
			// and add the node, in the new order, to the reordering list.
			// If the sets become equal, then dump the changes.
			ConvertedModelSet<ModelType, NodeType> pendingOld = new ConvertedModelSet<ModelType, NodeType>(converter);
			ConvertedModelSet<ModelType, NodeType> pendingNew = new ConvertedModelSet<ModelType, NodeType>(converter);
			
			while (origIter.hasNext() && newIter.hasNext()) {
				ModelType origModel = origIter.next();
				Map.Entry<ModelType, NodeType> newModelEntry = newIter.next();
				
				// remember we need to reorder a node either if it's starting a new
				// stream of mismatching nodes or if we're inside a stream of mismatching nodes
				if (!converter.elementMatches(origModel, newModelEntry.getKey())
						|| pendingOld.size() > 0) {
					pendingOld.add(origModel);
					pendingNew.add(newModelEntry.getKey());
					
					List<IASTNode> list = getReorderingList(newOrdering, newModelEntry.getValue());
					list.add(newModelEntry.getValue());
					if (!warnedNoReordering && previousList != null && list != previousList) {
						warnNoReordering(newModelEntry.getValue());
						warnedNoReordering = true;
					}
					previousList = list;
				}
				if (pendingOld.size() > 1 && pendingOld.equals(pendingNew)) {
					dumpPendingReorderings(newOrdering);
					newOrdering.clear();
					pendingOld.clear();
					pendingNew.clear();
				}
			}
			
			// handle new entries
			while (newIter.hasNext()) {
				Map.Entry<ModelType, NodeType> newModelEntry = newIter.next();
				List<IASTNode> list = getReorderingList(newOrdering, newModelEntry.getValue());
				list.add(newModelEntry.getValue());
				if (!warnedNoReordering && previousList != null && list != previousList) {
					warnNoReordering(newModelEntry.getValue());
					warnedNoReordering = true;
				}
				previousList = list;
			}

			dumpPendingReorderings(newOrdering);
		}
	}


	private <ModelType, NodeType extends IASTStatement> void dumpPendingReorderings(
			Map<IPath, List<IASTNode>> newOrdering) {
		for (Map.Entry<IPath, List<IASTNode>> entry : newOrdering.entrySet()) {
			List<IASTNode> list = (List<IASTNode>) entry.getValue();
			if (list.size() > 1) {
				modifications.add(new ReorderModification((IASTNode[]) 
						list.toArray(new IASTNode[list.size()])));
			}
		}
	}

	private void warnNoReordering(IASTNode node) {
		messages.add(ASTFactory.createMessage(
				IMessage.WARNING,
				"BaseStatementUpdater.CannotReorderAcrossDocuments",
				new Object[] { node.getNewText() },
				node.getMessageLocation() != null ?
						node.getMessageLocation() :
							new MessageLocation(view.getModel().getPath())));
	}


	private <NodeType extends IASTNode> 
	List<IASTNode> getReorderingList(Map<IPath, List<IASTNode>> newOrdering, NodeType node) {
		IPath path = getNodeLocation(node);
		List<IASTNode> list = newOrdering.get(path);
		if (list == null) {
			list = new ArrayList<IASTNode>();
			newOrdering.put(path, list);
		}
		return list;
	}

	private IPath getNodeLocation(IASTNode node) {
		IPath thePath = null;
		if (node.getSourceRegion() != null)
			thePath = node.getSourceRegion().getFirstLocation();
		if (thePath == null) {
			thePath = view.getModel().getPath();
		}
		return thePath;
	}
	
	public void update() {
		scanStatements();
		updateStatements();
		applyNewStatements();
		fixupOrdering();
	}

	public List<IViewModification> getModifications() {
		return modifications;
	}
}
