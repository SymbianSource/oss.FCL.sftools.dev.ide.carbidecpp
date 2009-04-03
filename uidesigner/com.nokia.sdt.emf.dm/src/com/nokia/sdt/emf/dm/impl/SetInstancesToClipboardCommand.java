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

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.emf.dm.INode;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.EMFEditPlugin;
import org.eclipse.emf.edit.domain.EditingDomain;

import java.util.*;

/**
 * This command gives the instances given it to the EditingDomain's clipbaord.
 * This assumes that the EditingDomain will make copies as needed.
 */
public class SetInstancesToClipboardCommand extends AbstractCommand implements Command {

	protected static final String LABEL = 
		EMFEditPlugin.INSTANCE.getString("_UI_CopyToClipboardCommand_label");
	protected static final String DESCRIPTION = 
		EMFEditPlugin.INSTANCE.getString("_UI_CopyToClipboardCommand_description");

	private EditingDomain editingDomain;
	private List<String> newObjects;
	private NodesCopier oldObjects;
	private INode rootContainer;

	public SetInstancesToClipboardCommand(EditingDomain editingDomain, List objectsToCopy) {
		super(LABEL, DESCRIPTION);
		this.editingDomain = editingDomain;
		this.newObjects = getNamesListFromNodesCollection(objectsToCopy);
		this.oldObjects = new NodesCopier(Collections.EMPTY_LIST);
		Check.checkContract(!objectsToCopy.isEmpty());
		rootContainer = ((INode) objectsToCopy.get(0)).getRootContainer();
	}
	
	private static List<String> getNamesListFromNodesCollection(Collection<INode> nodes) {
		List<String> names = new ArrayList();
		if (nodes != null) {
			for (Iterator<INode> iter = nodes.iterator(); iter.hasNext();) {
				String name = iter.next().getName();
				Check.checkContract(name != null);
				names.add(name);
			}
		}
		
		return names;
	}
	
	private static List<INode> createNodesListFromNamesList(INode root, List<String> names) {
		List<INode> nodes = new ArrayList();
		for (Iterator<String> iter = names.iterator(); iter.hasNext();) {
			String name = iter.next();
			INode node = root.findByNameProperty(name);
			Check.checkState(node != null); // we should be able to get all our nodes back from the names
			nodes.add(node);
		}
		
		return nodes;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
	 */
	protected boolean prepare() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#execute()
	 */
	public void execute() {
		Collection clipboard = editingDomain.getClipboard();
		if ((clipboard != null) && !clipboard.isEmpty())
			oldObjects = new NodesCopier(new ArrayList(clipboard));
		List<INode> nodes = createNodesListFromNamesList(rootContainer, newObjects);
		Collections.sort(nodes, new Comparator<EObject>() {
			public int compare(EObject o1, EObject o2) {
				return getIndex(o1) - getIndex(o2);
			}
		});
		editingDomain.setClipboard((Collection) nodes);
	}

	private int getIndex(EObject object) {
		IComponentInstance instance = ModelUtils.getComponentInstance(object);
		EObject parent = instance.getParent();
		IComponentInstance parentInstance = ModelUtils.getComponentInstance(parent);
		EObject[] children = parentInstance.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i].equals(object))
				return i;
		}
		Check.checkState(false); // something's terribly wrong if the parent isn't the parent!
		return -1;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
	 */
	public void undo() {
		editingDomain.setClipboard((Collection) oldObjects.getCopies());
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#redo()
	 */
	public void redo() {
		execute();
	}

}
