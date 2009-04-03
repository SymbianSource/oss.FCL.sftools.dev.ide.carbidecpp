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

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.*;
import org.eclipse.core.runtime.IStatus;

class OperationHistoryListener implements IOperationHistoryListener {
	
	private final CarbideFormEditor editor;
	private final CarbideFormEditorContext editorContext;
	private boolean handlingUndoRedo;

	public OperationHistoryListener(CarbideFormEditor editor) {
		this.editor = editor;
		this.editorContext = editor.getBaseEditorContext();
	}

	public void historyNotification(OperationHistoryEvent event) {
		switch (event.getEventType()) {
		case OperationHistoryEvent.ABOUT_TO_EXECUTE:
			break;
			
		case OperationHistoryEvent.OPERATION_NOT_OK:
			break;
			
		case OperationHistoryEvent.ABOUT_TO_REDO:
			break;
		case OperationHistoryEvent.ABOUT_TO_UNDO:
			break;
			
		case OperationHistoryEvent.OPERATION_ADDED:
			if (editor.committingView) {
				editorContext.viewCommitTextOps.add(event.getOperation());
				if (event.getOperation() instanceof AbstractOperation) {
					AbstractOperation aop = (AbstractOperation) event.getOperation();
					aop.setLabel(aop.getLabel());
				}
			}
			break;
			
		case OperationHistoryEvent.UNDONE:
			if (!handlingUndoRedo) {
				try {
					handlingUndoRedo = true;
					handleUndo(event.getOperation());
					editor.respondToTextEditingUndoRedo(event.getOperation());
				} finally {
					handlingUndoRedo = false;
				}
			}
			break;
			
		case OperationHistoryEvent.REDONE:
			if (!handlingUndoRedo) {
				try {
					handlingUndoRedo = true;
					handleRedo(event.getOperation());
					editor.respondToTextEditingUndoRedo(event.getOperation());
				} finally {
					handlingUndoRedo = false;
				}
			}
			break;
			
		case OperationHistoryEvent.OPERATION_REMOVED:
			editorContext.viewCommitTextOps.remove(event.getOperation());
			break;
		}		
		editor.operationHistoryChanged();
	}

	private void handleRedo(final IUndoableOperation redoneOp) {
		// after a redo, check for subsequent redos that should be done as a unit
		final IOperationHistory opHistory = editorContext.operationHistory;
		final IUndoContext context = editorContext.allContext;
		boolean needRevert = editorContext.viewCommitTextOps.contains(redoneOp);
		while(opHistory.canRedo(context)) {
			IStatus status;
			IUndoableOperation op = opHistory.getRedoOperation(context);
			if (editorContext.viewCommitTextOps.contains(op)) {
				needRevert = true;
				try {
					status = opHistory.redo(context, null, null);
					editorContext.handleStatus(status);
				} catch (ExecutionException x) {
					editorContext.handleExecutionException(op, x);
				}
			} else {
				break;
			}
		}
		if (needRevert) {
			editor.revertView();
		}
	}

	private void handleUndo(final IUndoableOperation undoneOp) {
		// after an undo, check if the operation is an implicit one that
		// should trigger additional undos
		if (editorContext.viewCommitTextOps.contains(undoneOp)) {
			final IOperationHistory opHistory = editorContext.operationHistory;
			final IUndoContext context = editorContext.allContext;

			boolean repeat = true;
			CarbideViewOperation noopViewOperation = null;
			while(repeat && opHistory.canUndo(context)) {
				IStatus status;
				IUndoableOperation op = opHistory.getUndoOperation(context);
				repeat = editorContext.viewCommitTextOps.contains(op);
				if (op instanceof CarbideViewOperation) {
					noopViewOperation = (CarbideViewOperation) op;
					noopViewOperation.setNoopUndo();
				}
				try {
					status = opHistory.undo(context, null, null);
					editorContext.handleStatus(status);
				} catch (ExecutionException x) {
					editorContext.handleExecutionException(op, x);
				}
			} 
			editor.revertView();

			if (noopViewOperation != null) {
				noopViewOperation.refreshUI();
			}

			// reverting the view undos as many GUI operations as were previously
			// uncomitted. We now need to re-apply them to be in a consistent state
			// we're not modifying the undo stack here
			IUndoableOperation[] undoHistory = opHistory.getUndoHistory(editorContext.allContext);

			// re-apply all sequential CarbideViewOperations
			for (int i = undoHistory.length - 1; i >= 0; --i) {
				IUndoableOperation op = undoHistory[i];
				if (!doViewOperation(op)) {
					break;
				}
			}
		}
	}
	
	private boolean doViewOperation(IUndoableOperation op) {
		boolean isCarbideOp = false;
		if (op instanceof CarbideViewOperation) {
			CarbideViewOperation cvop = (CarbideViewOperation) op;
			isCarbideOp = true;
			try {
				IStatus status = cvop.doOperation(true);
				editorContext.handleStatus(status);
			} catch (ExecutionException x) {
				editorContext.handleExecutionException(op, x);
			}
		}
		return isCarbideOp;
	}
}