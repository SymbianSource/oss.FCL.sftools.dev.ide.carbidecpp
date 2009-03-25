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

import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.editor.IComponentEditor;
import com.nokia.sdt.emf.dm.INode;
import com.nokia.sdt.emf.dm.impl.NodeCopier.IFilter;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.EMFEditPlugin;

import java.util.*;

/**
 * 
 */
public class RemoveChildInstancesCommand extends AbstractCommand {
	
	protected static final String LABEL = 
		EMFEditPlugin.INSTANCE.getString("_UI_RemoveCommand_label");
	protected static final String DESCRIPTION = 
		EMFEditPlugin.INSTANCE.getString("_UI_RemoveCommand_description");

	private List affectedObjects;

	private Map<String, List<NodeInfo>> ownerToInfoListMap;
	private Map<String, NodesCopier> ownerToNodesCopierMap;
	
	class NodeInfo {
		int savedIndex;
		String ownerName;
		String nodeName;
	}
	
	private final List<String> objectsToRemove;
	private List<NodeReferencesRestorer> nodeRestorers;
	private INode rootContainer;
	
	public RemoveChildInstancesCommand(List objectsToRemove) {
		super(LABEL, DESCRIPTION);
		Check.checkArg(objectsToRemove);
		Set noDuplicates = new HashSet(objectsToRemove);
		this.objectsToRemove = new ArrayList<String>();
		for (Iterator<INode> iter = noDuplicates.iterator(); iter.hasNext();) {
			INode node = iter.next();
			if (rootContainer == null)
				rootContainer = node.getRootContainer();
			String name = node.getName();
			Check.checkContract(name != null);
			this.objectsToRemove.add(name);
		}
	}
	
	private void sortInfoLists() {
		// sort info lists by savedIndex
		// so that reverse iteration will allow removal by index and 
		// restoration in undo preserves integrity of the saved index
		for (Iterator<List<NodeInfo>> iter = ownerToInfoListMap.values().iterator(); iter.hasNext();) {
			List<NodeInfo> infoList = iter.next();
			Collections.sort(infoList, new Comparator<NodeInfo>() {
				public int compare(NodeInfo o1, NodeInfo o2) {
					return o1.savedIndex - o2.savedIndex;
				}
			});
		}
	}
	
	private List createNodesList(List infoList) {
		List<INode> nodesList = new ArrayList();
		for (Iterator<NodeInfo> iter = infoList.iterator(); iter.hasNext();) {
			NodeInfo info = iter.next();
			INode node = rootContainer.findByNameProperty(info.nodeName);
			nodesList.add(node);
		}
		
		return nodesList;
	}
	
	private static boolean containsRecursive(INode rootContainer, List<String> objectsToRemove, String nodeName) {
		if (objectsToRemove.contains(nodeName))
			return true;
		
		INode node = rootContainer.findByNameProperty(nodeName);
		EObject owner = node.eContainer();
		if (owner instanceof INode) {
			String name = ((INode) owner).getName();
			Check.checkContract(name != null);
			return containsRecursive(rootContainer, objectsToRemove, name);
		}
		
		return false;
	}
	
	private void initialize() {
		saveNodesReferences();

		ownerToInfoListMap = new HashMap();
		for (Iterator<String> iter = objectsToRemove.iterator(); iter.hasNext();) {
			String nodeName = iter.next();
			INode node = rootContainer.findByNameProperty(nodeName);
			EObject owner = node.eContainer();
			// don't add any child node whose owner is also to be removed
			Check.checkState(owner instanceof INode);
			INode ownerNode = (INode) owner;
			String ownerName = ownerNode.getName();
			Check.checkContract(ownerName != null);
			if (!containsRecursive(rootContainer, objectsToRemove, ownerName)) {
				List infoList = ownerToInfoListMap.get(ownerName);
				if (infoList == null) {
					infoList = new ArrayList();
					ownerToInfoListMap.put(ownerName, infoList);
				}
				NodeInfo info = new NodeInfo();
				info.savedIndex = ownerNode.getChildren().indexOf(node);
				info.ownerName = ownerName;
				String name = node.getName();
				Check.checkContract(name != null);
				info.nodeName = name;
				infoList.add(info);
			}
		}
		
		sortInfoLists();
		
		ownerToNodesCopierMap = new HashMap();
		for (Iterator<String> iter = ownerToInfoListMap.keySet().iterator(); iter.hasNext();) {
			String ownerName = iter.next();
			List infoList = ownerToInfoListMap.get(ownerName);
			List<INode> nodesList = createNodesList(infoList);
			NodesCopier copier = new NodesCopier(nodesList, true, new IFilter() {
				public boolean test(INode node) {
					IComponentEditor componentEditor = ModelUtils.getComponentEditor(node);
					return (componentEditor == null) || !componentEditor.isTemporaryObject();
				}
			});
			ownerToNodesCopierMap.put(ownerName, copier);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
	 */
	protected boolean prepare() {
		return true;
	}

	private void saveNodesReferences() {
		// if a reference is found to a node that is being removed, 
		// the node is mapped to the reference owner
		nodeRestorers = new ArrayList();
		for (Iterator<String> iter = objectsToRemove.iterator(); iter.hasNext();) {
			String nodeName = iter.next();
			INode node = rootContainer.findByNameProperty(nodeName);
			NodeReferencesRestorer nodeReferences = NodeReferencesRestorer.getReferencesForNode(rootContainer, node);
			if (nodeReferences != null)
				nodeRestorers.add(nodeReferences);
		}
	}
	
	private void restoreReferences() {
		for (Iterator<NodeReferencesRestorer> iter = nodeRestorers.iterator(); iter.hasNext();) {
			NodeReferencesRestorer restorer = iter.next();
			restorer.restoreReferences(rootContainer);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#execute()
	 */
	public void execute() {
		if (!canExecute())
			return;
		
		if (ownerToInfoListMap == null)
			initialize();
		
		for (Iterator<String> iter = ownerToInfoListMap.keySet().iterator(); iter.hasNext();) {
			String ownerName = iter.next();
			INode owner = rootContainer.findByNameProperty(ownerName);
			EList children = owner.getChildren();
			List infoList = ownerToInfoListMap.get(ownerName);
			for (Iterator<NodeInfo> iterator = infoList.iterator(); iterator.hasNext();) {
				NodeInfo info = iterator.next();
				INode node = rootContainer.findByNameProperty(info.nodeName);
				if (node != null)
					children.remove(node);
			}
		}
		
		// only return affected objects for undo
		affectedObjects = Collections.EMPTY_LIST;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#redo()
	 */
	public void redo() {
		execute();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
	 */
	public void undo() {
		for (Iterator<String> iter = ownerToInfoListMap.keySet().iterator(); iter.hasNext();) {
			String ownerName = iter.next();
			List<NodeInfo> infoList = ownerToInfoListMap.get(ownerName);
			NodesCopier copier = ownerToNodesCopierMap.get(ownerName);
			INode owner = rootContainer.findByNameProperty(ownerName);
			affectedObjects = new ArrayList();
			for (int i = 0; i < infoList.size(); i++) {
				NodeInfo info = infoList.get(i);
				INode copy = copier.getCopy(i);
				if (!hasChildWithName(owner, copy.getName())) { // may have not been deleted due to containment error
					INode node = NodeCopier.copyNode(copy, owner, info.savedIndex, true, true);
					String nodeName = node.getName();
					Check.checkContract(nodeName != null);
					info.nodeName = nodeName; // save the new node in the node info, in case of redo!
					ModelUtils.getPropertyInformation(node).refresh();
					affectedObjects.add(node);
				}
			}
		}
		restoreReferences();
	}

	private boolean hasChildWithName(INode owner, String name) {
		for (Object child : owner.getChildren()) {
			if (child instanceof INode) {
				if (((INode) child).getName().equals(name))
					return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#getAffectedObjects()
	 */
	public Collection getAffectedObjects() {
		return affectedObjects;
	}

}
