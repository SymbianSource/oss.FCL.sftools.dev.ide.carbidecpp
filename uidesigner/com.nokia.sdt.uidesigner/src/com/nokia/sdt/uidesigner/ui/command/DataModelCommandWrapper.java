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


package com.nokia.sdt.uidesigner.ui.command;

import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;

import java.util.Collection;

/**
 * 
 *
 */
public class DataModelCommandWrapper extends Command {

	/** The wrapped data model command */
	private org.eclipse.emf.common.command.Command dataModelCommand;
	private IDesignerEditor editor;

	/**
	 * @param command the wrapped data model command
	 */
	public void setDataModelCommand(org.eclipse.emf.common.command.Command command) {
		this.dataModelCommand = command;
		if (dataModelCommand != null) {
			String label = dataModelCommand.getLabel();
			if (label != null) {
				setLabel(label);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		dataModelCommand.execute();
		if (editor != null)
			setSelectionToAffectedObjects(editor);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	public boolean canExecute() {
		if (dataModelCommand == null)
			return false;
		return dataModelCommand.canExecute();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		dataModelCommand.undo();
		if (editor != null)
			setSelectionToAffectedObjects(editor);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		dataModelCommand.redo();
		if (editor != null)
			setSelectionToAffectedObjects(editor);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	public boolean canUndo() {
		return true;
	}
	
	/**
	 * @return dataModelCommand as <code>org.eclipse.emf.common.command.Command</code>
	 */
	protected org.eclipse.emf.common.command.Command getCommand() {
		return dataModelCommand;
	}
	
	protected Collection getAffectedObjects() {
		return dataModelCommand.getAffectedObjects();
	}
	
	protected void setSelectionToAffectedObjects(IDesignerEditor editor) {
		Collection objects = getAffectedObjects();
		if (!objects.isEmpty())
			refreshTransients(objects.iterator().next());
		EditorUtils.setSelectionToAffectedObjects(editor, objects);
	}

	public void setEditor(IDesignerEditor editor) {
		this.editor = editor;
	}	
	
	private void refreshTransients(Object object) {
		if (editor.isTransientMode()) {
			editor.setLayoutMode();
			EObject transientObject = EditorUtils.findTransientObject(object);
			if (transientObject != null)
				editor.setTransientMode(transientObject);
		}
	}
}
