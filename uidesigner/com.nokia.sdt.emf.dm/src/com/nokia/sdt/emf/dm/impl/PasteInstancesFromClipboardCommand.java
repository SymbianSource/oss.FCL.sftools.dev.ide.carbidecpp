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


package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NamePropertySupport;
import com.nokia.sdt.datamodel.util.NamePropertySupport.INameFilter;
import com.nokia.sdt.editor.IComponentEditor;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.emf.dm.impl.NodeCopier.IFilter;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.EMFEditPlugin;
import org.eclipse.emf.edit.domain.EditingDomain;

import java.util.*;

/**
 * 
 *
 */
public class PasteInstancesFromClipboardCommand extends AbstractCommand implements Command {

	  protected static final String LABEL = 
		  EMFEditPlugin.INSTANCE.getString("_UI_PasteFromClipboardCommand_label");
	  protected static final String DESCRIPTION = 
		  EMFEditPlugin.INSTANCE.getString("_UI_PasteFromClipboardCommand_description");

	
	private String ownerName;
	private IDesignerDataModel ownerModel;
	private EditingDomain editingDomain;
	private Collection clipboard;
	private List<INode> affectedObjects;
	private List<String> pastedNames;
	private List<NodeCopier> copiedNodesForRedo;
	
	class NameReplaceVisitor implements INodeVisitor {
		private INode root;
		private IDesignerDataModel model;
		
		/**
		 * When visiting each node, check it for clashes with the new model
		 * If clashes, assign the node a new name that does not clash with the new model
		 * or any of the children of the current node (which themselves may already have a name)
		 * @param root the root INode of the new model
		 */
		public NameReplaceVisitor(INode root) {
			this.root = root;
			model = root.getDesignerData().getDesignerDataModel();
		}
		
		private IComponent getComponent(INode node) {
			String componentId = node.getComponentId();
			return model.getComponentSet().lookupComponent(componentId);
		}
		
		private void assignNewName(final INode node, IComponent component) {
			// This filter is passed to the name generator to allow it to check
			// not only the target model, but also the children of the root of the current node
			final INode root = node.getRootContainer();
			INameFilter filter = new INameFilter() {
				public boolean test(final String name) {
					INodeVisitor visitor = new INodeVisitor() {
						public Object visit(INode node) {
							if (name.equals(node.getName()))
								return node;
							return null;
						}
					};
					return root.visitPreorder(visitor) == null;
				}
			};
			String generatedName = 
				NamePropertySupport.generateNameForModel(model, component, null, true, filter);
			StringValue sv = new StringValue(StringValue.LITERAL, generatedName);
			node.getProperties().set(INode.NAME_PROPERTY, sv, true);
		}

		public Object visit(INode node) {
			if (hasClashingName(root, node)) {
				assignNewName(node, getComponent(node));
			}
			return null;
		}
		
	}
	
	class NameClashDetector implements INodeVisitor {
		private INode root;
		
		public NameClashDetector(INode root) {
			this.root = root;
		}

		public Object visit(INode node) {
			if (hasClashingName(root, node)) {
				return node;
			}
			return null;
		}
		
	}
	
	public PasteInstancesFromClipboardCommand(EditingDomain editingDomain, EObject owner) {
		super(LABEL, DESCRIPTION);
		Check.checkArg(owner instanceof INode);
		Check.checkArg(editingDomain);
		this.ownerName = ((INode) owner).getName();
		this.ownerModel = ((INode) owner).getDesignerData().getDesignerDataModel();
		this.editingDomain = editingDomain;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
	 */
	protected boolean prepare() {
		affectedObjects = new ArrayList<INode>();
		pastedNames = new ArrayList<String>();
		copiedNodesForRedo = new ArrayList<NodeCopier>();
		clipboard = editingDomain.getClipboard();
		return (clipboard != null) && !clipboard.isEmpty();
	}
	
	private boolean hasNameClashes(INode root, INode node) {
		return node.visitPreorder(new NameClashDetector(root)) != null;
	}
	
	private void replaceClashingNames(INode root, INode node) {
		node.visitPreorder(new NameReplaceVisitor(root));
	}
	
	/**
	 * @param root the INode whose tree we check for clashing names
	 * @param node the INode whose name is checked for clashes
	 * @return true if INode.getName() exists in root's tree
	 */
	protected boolean hasClashingName(INode root, INode node) {
		String name = node.getName();
		return (name != null) && (root.findByNameProperty(name) != null);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#execute()
	 */
	public void execute() {
		if (!canExecute())
			return;
		
		affectedObjects.clear();
		pastedNames.clear();
		copiedNodesForRedo.clear();
		INode owner = getOwner();
		INode root = owner.getRootContainer();
		// For each node on the clipboard, 
		// if it, or any of its children's names clashes with the owner's model, 
		// make a copy of the node and replace any clashing names
		// Copy the resulting node (or the original, if no clashes) to the owner
		for (Iterator iter = clipboard.iterator(); iter.hasNext();) {
			INode node = (INode) iter.next();
			NodeCopier localCopier = new NodeCopier(node, new IFilter() {
				public boolean test(INode node) {
					IComponentEditor componentEditor = ModelUtils.getComponentEditor(node);
					return (componentEditor == null) || !componentEditor.isTemporaryObject();
				}
			});
			if (hasNameClashes(root, node)) {
				node = localCopier.getCopy();
				replaceClashingNames(root, node);
			}
			INode copy = NodeCopier.copyNode(node, owner, IDesignerDataModel.AT_END, false);
			if (copy != null) {
				ModelUtils.getPropertyInformation(copy).refresh();
				affectedObjects.add(copy);
				pastedNames.add(copy.getName());
				copiedNodesForRedo.add(localCopier);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
	 */
	public void undo() {
		INode owner = getOwner();
		// don't use removeAll, because notification is indices, instead of objects
		EList children = owner.getChildren();
		for (String name : pastedNames) {
			INode child = owner.findByNameProperty(name);
			if (child != null)
				children.remove(child);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#redo()
	 */
	public void redo() {
		affectedObjects.clear();
		pastedNames.clear();
		INode owner = getOwner();
		for (NodeCopier copier : copiedNodesForRedo) {
			INode copy = NodeCopier.copyNode(copier.getCopy(), owner, IDesignerDataModel.AT_END, false);
			if (copy != null) {
				ModelUtils.getPropertyInformation(copy).refresh();
				affectedObjects.add(copy);
				pastedNames.add(copy.getName());
			}
		}
	}
	
	private INode getOwner() {
		return (INode) ownerModel.findByNameProperty(ownerName);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#getAffectedObjects()
	 */
	public Collection getAffectedObjects() {
		return affectedObjects;
	}

}
