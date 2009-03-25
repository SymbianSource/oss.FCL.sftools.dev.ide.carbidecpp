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


package com.nokia.sdt.uidesigner.ui.actions;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;
import com.nokia.sdt.uidesigner.ui.utils.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.action.PasteAction;
import org.eclipse.jface.viewers.IStructuredSelection;

import java.util.*;

public class PasteActionCommandHandler extends PasteAction {

	private CommandHandlerHelper helper;

	/**
	 * @param editor the owner of the data model
	 */
	public PasteActionCommandHandler(IDesignerEditor editor) {
		super(editor.getDataModel().getEditingDomain());
		helper = new CommandHandlerHelper(editor);
		helper.addSelectionChangedListener(this);
	}
	
	public void run() {
		IStructuredSelection structuredSelection = getStructuredSelection();
		Collection selectedObjects = helper.makeEObjectList(structuredSelection.toList());
		EObject owner = null;
		
		if (selectedObjects.size() > 1) {
			owner = EditorUtils.getCommonDirectParent(selectedObjects);
		}
		else if (!selectedObjects.isEmpty()) { // size == 1
			owner = (EObject) selectedObjects.iterator().next();
			// special case where owner is not a container, use the parent of the owner
			if (Adapters.getContainer(owner) == null) {
				owner = Adapters.getComponentInstance(owner).getParent();
			}
		}
		
		// try the content container, first
		if (owner == null) {
			owner = helper.editor.getDisplayModel().getContentContainer();
		}
		
		// else try the root container
		if (owner == null) {
			owner = helper.getRootContainer();
		}
		
		// preflight, in case we need to try the parent
		Collection clipboard = helper.editor.getDataModel().getEditingDomain().getClipboard();
		List<IStatus> statuses = new ArrayList();
		if (!EditorUtils.canContainAtLeastOneComponent(owner, clipboard, statuses)) {
			// if can't paste even one object, try the owner's parent (just one level)
			IComponentInstance ownerInstance = Adapters.getComponentInstance(owner);
			EObject parent = null;
			if (ownerInstance != null)
				parent = ownerInstance.getParent();
			if (parent != null)
				owner = parent;
			if (!EditorUtils.canContainAtLeastOneComponent(owner, clipboard, statuses)) {
				StatusBuilder builder = new StatusBuilder(UIDesignerPlugin.getDefault());
				MultiStatus multiStatus = builder.createMultiStatus(
						Strings.getString("PasteActionCommandHandler.CouldNotAddComponentsError"), 
						new Object[0]); //$NON-NLS-1$
				for (Iterator<IStatus> iter = statuses.iterator(); iter.hasNext();)
					multiStatus.add(iter.next());
				Logging.showErrorDialog(null, 
						Strings.getString("PasteActionCommandHandler.PasteFailedTitle"), multiStatus); //$NON-NLS-1$
				owner = null;
			}
		}
		
		if (owner != null) {
			Command dataModelCommand = getDataModelCommand(owner);
			helper.doExecute(dataModelCommand);
			EditorUtils.setSelectionToAffectedObjects(helper.editor, dataModelCommand.getAffectedObjects());
		}
	}

	public Command createCommand(Collection selection) {
		// always pass the root container as the owner to enable the paste action
		// then preflight the actual selection in run() with the actual selection
		// to advise the user if there is a problem
		return getDataModelCommand(helper.getRootContainer());
	}
	
	private Command getDataModelCommand(EObject owner) {
		return helper.getDataModel().createPasteComponentInstancesCommand(owner);
	}
}
