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

import com.nokia.sdt.displaymodel.adapter.IDisplayObject;
import com.nokia.sdt.editor.IComponentEditor;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.editparts.ModelObjectEditPart;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.action.CutAction;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

import java.util.*;

/**
 * 
 *
 */
public class CutActionCommandHandler extends CutAction {

	private CommandHandlerHelper helper;

	/**
	 * @param editor the owner of the data model
	 */
	public CutActionCommandHandler(IDesignerEditor editor) {
		super(editor.getDataModel().getEditingDomain());
		helper = new CommandHandlerHelper(editor);
		helper.addSelectionChangedListener(this);
	}

	public void run() {
		helper.doExecute(command);
	}
	
	private boolean isDescendantOfSelectedPart(EditPart toTest, Collection selectedParts) {
		EditPart parent = toTest.getParent();
		while (parent != null) {
			if (selectedParts.contains(parent))
				return true;
			parent = parent.getParent();
		}
		
		return false;
	}
	
	public boolean updateSelection(IStructuredSelection selection) {
		List filteredSelection = new ArrayList();
		for (Iterator iter = selection.iterator(); iter.hasNext();) {
			EditPart part = (EditPart) iter.next();
			if (helper.isRemovablePart(part) && !isDescendantOfSelectedPart(part, selection.toList()))
				filteredSelection.add(part);
		}

		if (filteredSelection.isEmpty())
			return false; // can't execute
		
		return super.updateSelection(new StructuredSelection(filteredSelection));
	}
	
	public Command createCommand(Collection selection) {
		if (!isUserRemovable(selection))
			return UnexecutableCommand.INSTANCE;
		return helper.getDataModel().createCutComponentInstancesCommand((List) helper.makeEObjectList(selection));
	}

	private boolean isUserRemovable(Collection selection) {
		for (Object object : selection) {
			if (object instanceof EditPart) {
				Object model = ((EditPart) object).getModel();
				if (model instanceof EObject) {
					IDisplayObject displayObject = Adapters.getDisplayObject((EObject) model);
					if (displayObject != null && !displayObject.isRemovable())
						return false;
				}
			}
		}
		return true;
	}

}
