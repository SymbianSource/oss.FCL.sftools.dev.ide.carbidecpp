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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.*;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.StatusBuilder;
import com.nokia.cpp.internal.api.utils.ui.editor.EditingContextOperation;
import com.nokia.cpp.internal.api.utils.ui.editor.IEditingContext;

/**
 * Generic ICompositeOperation implementation, i.e. a compound command
 * 
 * Contains a set of operations that execute/undo/redo as a group.
 * If any contained operation fails the IStatus is returned and
 * remaining operations are not invoked.
 * 
 */
public class CompositeOperation extends EditingContextOperation implements
		ICompositeOperation {
	
	private final IOperationHistory opHistory;
	private final boolean adoptContexts;
	private List<IUndoableOperation> ops = new ArrayList<IUndoableOperation>();
	private boolean replaying;

	public CompositeOperation(String label, IEditingContext editingContext, 
			IOperationHistory opHistory, boolean adoptContexts) {
		super(label, editingContext);
		this.opHistory = opHistory;
		this.adoptContexts = adoptContexts;
	}
	
	public void dispose() {
		for (IUndoableOperation op : ops) {
			op.dispose();
		}
		ops.clear();
	}
	
	protected IOperationHistory operationHistory() {
		return opHistory;
	}
	
	/**
	 * Returns true if undo/redo should be done via special
	 * replay methods instead of standard IOperationHistory methods
	 */
	public boolean needsUndoRedoReplay() {
		return false;
	}
	
	/**
	 * Replay the contained operations separately. This can be
	 * necessary, e.g. with the text document's operations, because
	 * operation history listeners are listening for these operations
	 * and important side effects can occur if we don't execute the
	 * operations via the IOperationHistory interface.
	 * 
	 * If the contained commands replayed successfuly this command is also 
	 * moved from the undo list to the redo list.
	 * 
	 * @return an IStatus representing an error or informational
	 * messages from undoing the commands.
	 */
	public IStatus replayUndo(IProgressMonitor monitor, IAdaptable info) {

		// undo this command, as a no-op, so it gets transferred to the redo list
		replaying = true;
		try {
			opHistory.undoOperation(this, monitor, info);
		} catch (ExecutionException x) {
			ProjectUIPlugin.log(x);
		} finally {
			replaying = false;
		}
		
		StatusBuilder sb = new StatusBuilder(ProjectUIPlugin.getDefault());
		// undo the commands. if successful they're put on the redo list
		IUndoableOperation[] operations = ops.toArray(new IUndoableOperation[ops.size()]);
		for (int i = operations.length - 1; i >= 0; i--) {
			IUndoableOperation op = operations[i];
			try {
				IStatus status = opHistory.undoOperation(op, monitor, info);
				if (status != null && !status.isOK()) {
					// only record status with some kind of pertinent information
					sb.add(status);
				}
			} catch (ExecutionException x) {
				sb.add(new Status(IStatus.ERROR, ProjectUIPlugin.PLUGIN_ID, 
						0, x.getLocalizedMessage(), x));
			}
		}
		
		// remove operations from the redo list
		IUndoableOperation none[] = {};
		for (IUndoableOperation op : operations) {
			opHistory.replaceOperation(op, none);
		}
		
		String msg = sb.getErrorCount() > 0? Messages.getString("CompositeOperation.commandFailed") : Messages.getString("CompositeOperation.commandFailedWithMessages"); //$NON-NLS-1$ //$NON-NLS-2$
		return sb.createStatus(msg, null);
	}

	@Override
	protected IStatus doExecute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		for (IUndoableOperation op : ops) {
			IStatus status = op.execute(monitor, info);
			if (status != null && !status.isOK()) {
				return status;
			}
		}
		return Status.OK_STATUS;
	}

	@Override
	protected IStatus doRedo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (replaying) return Status.OK_STATUS;
		for (IUndoableOperation op : ops.toArray(new IUndoableOperation[ops.size()])) {
			IStatus status = opHistory.redoOperation(op, monitor, info);
			// IStatus status = op.redo(monitor, info);
			if (status != null && !status.isOK()) {
				return status;
			}
		}
		return Status.OK_STATUS;
	}

	@Override
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (replaying) return Status.OK_STATUS;
		IUndoableOperation[] operations = ops.toArray(new IUndoableOperation[ops.size()]);
		for (int i = operations.length - 1; i >= 0; i--) {
			IUndoableOperation op = operations[i];
			IStatus status = op.undo(monitor, info);
			if (status != null && !status.isOK()) {
				return status;
			}
		}
		return Status.OK_STATUS;
	}

	public void add(IUndoableOperation operation) {
		Check.checkArg(operation != this);
		if (!ops.contains(operation)) {
			ops.add(operation);
			if (adoptContexts) {
				for(IUndoContext context : operation.getContexts()) {
					addContext(context);
				}
			}
		}
		// see if we can adopt the editing context of a child operation
		if (getEditingContext() == null) {
			IEditingContext context = EditingContextOperation.getEditingContextForOperation(operation);
			if (context != null) {
				setEditingContext(context);
			}
		}
	}

	/**
	 * Note that IUndoContexts are not removed when an operation
	 * is removed.
	 */
	public void remove(IUndoableOperation operation) {
		ops.remove(operation);
	}
	
	public IUndoableOperation[] getOperations() {
		return ops.toArray(new IUndoableOperation[ops.size()]);
	}
}
