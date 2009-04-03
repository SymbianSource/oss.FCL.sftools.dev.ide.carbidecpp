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
package com.nokia.cpp.internal.api.utils.ui.editor;

import java.text.MessageFormat;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.nokia.cpp.utils.ui.noexport.Messages;


/**
 * Implementation of AbstractOperation (still abstract) that
 * interacts with an IEditingContext.
 * Failure to restore an editing context does not abort execution, but
 * it does display a dialog.
 * 
 * The execute, undo, and redo commands are routed to new abstract methods
 * prefixed with "do", e.g. doExecute().
 */
public abstract class EditingContextOperation extends AbstractOperation implements IAdaptable {

	private IEditingContext editingContext;
	
	protected EditingContextOperation(String label, IEditingContext editingContext) {
		super(label);
		this.editingContext = editingContext;
	}
	
	public Object getAdapter(Class adapter) {
		Object result = null;
		if (IEditingContext.class == adapter) {
			result = editingContext;
		} else if (Shell.class == adapter) {
			result = getShell();
		} else if (adapter != null && adapter.isInstance(this)) {
			result = this;
		}
		return result;
	}
	
	public void setEditingContext(IEditingContext context) {
		this.editingContext = context;
	}
	
	public IEditingContext getEditingContext() {
		return editingContext;
	}

	/**
	 * Utility to retrieve the IEditingContext from an operation, if it has one
	 */
	public static IEditingContext getEditingContextForOperation(IUndoableOperation op) {
		IEditingContext result = null;
		if (op instanceof IAdaptable) {
			IAdaptable adaptable = (IAdaptable) op;
			result = (IEditingContext) adaptable.getAdapter(IEditingContext.class);
		}
		return result;
	}

	protected Shell getShell() {
		Shell result = null;
		if (editingContext != null) {
			result = editingContext.getShell();
		}
		if (result == null) {
			result = Display.getDefault().getActiveShell();
		}
		return result;
	}
	
	protected void restoreEditingContext(IAdaptable info) {
		if (editingContext != null) {
			IStatus status = editingContext.show();
			if (status != null && !status.isOK()) {
				String fmt = Messages.getString("EditingContextOperation.cantShowCommandLocation"); //$NON-NLS-1$
				Object params[] = {getLabel()};
				String msg = MessageFormat.format(fmt, params);
				ErrorDialog.openError(getShell(), Messages.getString("EditingContextOperation.errorDialogTitle"), msg, status); //$NON-NLS-1$
			}
		}
	}
	
	protected abstract IStatus doExecute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException;
	protected abstract IStatus doUndo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException;
	protected abstract IStatus doRedo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException;
	
	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		return doExecute(monitor, info);
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		restoreEditingContext(info);
		return doRedo(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		restoreEditingContext(info);
		return doUndo(monitor, info);
	}

}
