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

import com.nokia.sdt.editor.IDesignerEditor;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.ui.action.CopyAction;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

import java.util.*;

/**
 * 
 *
 */
public class CopyActionCommandHandler extends CopyAction {

	private CommandHandlerHelper helper;

	/**
	 * @param editor the owner of the data model
	 */
	public CopyActionCommandHandler(IDesignerEditor editor) {
		super(editor.getDataModel().getEditingDomain());
		helper = new CommandHandlerHelper(editor);
		helper.addSelectionChangedListener(this);
	}

	public void run() {
		if (command.canExecute()) {
			command.execute(); // do not execute on the stack! Should not dirty data model!
			helper.updateSelectionActions();
		}
	}

	public Command createCommand(Collection selection) {
		return helper.getDataModel().createCopyComponentInstancesCommand((List) helper.makeEObjectList(selection));
	}

	public boolean updateSelection(IStructuredSelection selection) {
		List filteredSelection = new ArrayList();
		for (Iterator iter = selection.iterator(); iter.hasNext();) {
			EditPart part = (EditPart) iter.next();
			filteredSelection.add(part);
		}

		if (filteredSelection.isEmpty())
			return false; // can't execute
		
		return super.updateSelection(new StructuredSelection(filteredSelection));
	}

}
