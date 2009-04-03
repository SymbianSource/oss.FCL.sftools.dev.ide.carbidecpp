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
package com.nokia.sdt.symbian.ui.appeditor.context;

import org.eclipse.gef.commands.Command;


/**
 * A GEF command that can wrap an EMF command or GEF command, 
 * optionally associating an IEditingContext with the command.
 * The editing context is used to restore the UI location of the 
 * affected objects upon undo and redo. For example, a multi-page
 * editor should switch to the associated page and select the affected
 * objects.
 */
public class EditingContextCommand extends org.eclipse.gef.commands.Command {

	private org.eclipse.gef.commands.Command gefCommand;
	private org.eclipse.emf.common.command.Command emfCommand;
	private boolean alreadyExecuted;
	private IEditingContext editingContext;

	public EditingContextCommand(org.eclipse.emf.common.command.Command emfCommand, 
			boolean alreadyExecuted, IEditingContext editingContext) {
		this.emfCommand = emfCommand;
		this.alreadyExecuted = alreadyExecuted;
		this.editingContext = editingContext;
	}
	
	public EditingContextCommand(Command gefCommand, boolean alreadyExecuted, IEditingContext editingContext) {
		this.gefCommand = gefCommand;
		this.alreadyExecuted = alreadyExecuted;
		this.editingContext = editingContext;
	}
	
	public void setEMFCommand(org.eclipse.emf.common.command.Command emfCommand, boolean alreadyExecuted) {
		this.emfCommand = emfCommand;
		this.alreadyExecuted = alreadyExecuted;
	}
	
	public void setGEFCommand(org.eclipse.gef.commands.Command gefCommand, boolean alreadyExecuted) {
		this.gefCommand = gefCommand;
		this.alreadyExecuted = alreadyExecuted;
	}
	
	/**
	 * Subclasses that directly implement a (GEF) command, rather than wrapping
	 * another command, use this constructor
	 */
	protected EditingContextCommand(IEditingContext editingContext) {
		this.editingContext = editingContext;
	}

	public boolean canExecute() {
		if (alreadyExecuted)
			return true;
		
		boolean result = false;
		if (emfCommand != null) {
			result = emfCommand.canExecute();
		} else if (gefCommand != null) {
			result = gefCommand.canExecute();
		}
		return result;
	}

	public boolean canUndo() {
		boolean result = false;
		if (emfCommand != null) {
			result = emfCommand.canUndo();
		} else if (gefCommand != null) {
			result = gefCommand.canUndo();
		}
		return result;
	}

	public void dispose() {
		if (emfCommand != null) {
			emfCommand.dispose();
		}
		if (gefCommand != null) {
			gefCommand.dispose();
		}
	}

	public void execute() {
		if (alreadyExecuted)
			return;
		
		if (emfCommand != null) {
			emfCommand.execute();
		} else if (gefCommand != null) {
			gefCommand.execute();
		}
	}

	public String getLabel() {
		String result = null;
		if (emfCommand != null) {
			result = emfCommand.getLabel();
		} else if (gefCommand != null) {
			result = gefCommand.getLabel();
		}
		return result;
	}

	public void redo() {
		if (editingContext != null) {
			editingContext.show();
		}

		if (emfCommand != null) {
			emfCommand.redo();
		} else if (gefCommand != null) {
			gefCommand.redo();
		}
	}

	public void undo() {
		if (editingContext != null) {
			editingContext.show();
		}

		if (emfCommand != null) {
			emfCommand.undo();
		} else if (gefCommand != null) {
			gefCommand.undo();
		}
	}
}
