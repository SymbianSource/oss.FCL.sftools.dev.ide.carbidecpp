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
package com.nokia.carbide.cpp.internal.project.ui.editors.common;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.*;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IActionBars;

import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.cpp.internal.api.utils.core.IDisposable;
import com.nokia.cpp.internal.api.utils.core.Logging;

public class CarbideFormEditorContext implements IDisposable {

	public static final String TEXT_EDITOR_PAGE_ID = "texteditor"; //$NON-NLS-1$
	
	public CarbideFormEditor editor;
	public IDocument textDocument;
	public IOperationHistory operationHistory;
	public ObjectUndoContext modelOperationContext; // context used by our model/GUI operations
	public IUndoContext textEditorContext; // context used by the text editor
	public ObjectUndoContext allContext; // a context that matches both model & text operations
	public Set<IUndoableOperation> viewCommitTextOps = new HashSet<IUndoableOperation>();
	
	public CarbideFormEditorContext() {
		super();
	}

	/**
	 * Execute an operation and add it to the history. First 
	 * checks with the Team API that a modifying operation is allowed.
	 * @param operation
	 * @return true if operation executed successfully and was added to the history
	 */
	public boolean executeOperation(IUndoableOperation operation) {
		boolean result = false;
		// Check that the change is ok before executing it.
		// Edits that have initial UI, i.e. prompt via dialogs should be manually
		// preflighted first.
		if (editor.preflightEdit()) {
			operation.addContext(modelOperationContext);
			try {
				operationHistory.execute(operation, null, null);
				result = true;
			} catch (ExecutionException x) {
				handleExecutionException(operation, x);
			}
		} 
		else {
			// the model should not be changed since the operation did not execute
			// the UI state may be out of sync though. With a CarbideViewOperation
			// we can ask it to undo the UI change without touching the underlying model.
			if (operation instanceof CarbideViewOperation) {
				try {
					CarbideViewOperation vop = (CarbideViewOperation) operation;
					vop.setNoopUndo();
					vop.undo(new NullProgressMonitor(), null);
				} catch (ExecutionException x) {
					ProjectUIPlugin.log(x);
				}
			}
		}
		return result;
	}
	
	public void logAndDisplayError(String message, Throwable t) {
		ProjectUIPlugin.log(t, message);
		String statusMessage = t != null? t.getLocalizedMessage() : message;
		if (statusMessage == null) {
			statusMessage = ""; //$NON-NLS-1$
		}
		Logging.showErrorDialog(Messages.CarbideFormEditorContext_errorDialogTitle, message, 
				Logging.newSimpleStatus(1, IStatus.ERROR, statusMessage, t));
	}

	public void handleExecutionException(IUndoableOperation op, ExecutionException x) {
		String fmt = Messages.CarbideFormEditorContext_commandErrorFormatText;
		Object params[] = {op.getLabel()};
		String msg = MessageFormat.format(fmt, params);
		logAndDisplayError(msg, x);		
	}

	public void handleStatus(IStatus status) {
		if (status != null && !status.isOK()) {
			ProjectUIPlugin.log(status);
			ErrorDialog.openError(editor.getEditorSite().getShell(), 
					Messages.CarbideFormEditorContext_errorDialogTitle, status.getMessage(), status);
		}
	}

	public void dispose() {
		if (operationHistory != null) {
			operationHistory.dispose(allContext, true, true, true);
		}
		
		if (textDocument != null) {
			CarbideTextEditor.documentProvider().disconnect(editor.getEditorInput());
		}
	}

	public IStatusLineManager getStatusLineManager() {
		IStatusLineManager result = null;
		IActionBars actionBars = editor.getEditorSite().getActionBars();
		if (actionBars != null) {
			result = actionBars.getStatusLineManager();
		}
		return result;
	}

}