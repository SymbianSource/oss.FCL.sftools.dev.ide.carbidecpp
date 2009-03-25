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

import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.CarbideViewOperation;
import com.nokia.cpp.internal.api.utils.ui.editor.IEditingContext;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.*;

/**
 * Helper to wrap a third-party undoable operation in an MMP view operation.
 *
 */
public class WrappedUndoableOperation extends CarbideViewOperation {

	private IUndoableOperation operation;

	public WrappedUndoableOperation(IMMPView view,
			String label,
			IEditingContext editingContext,
			IUndoableOperation operation) {
		super(view, label != null ? label : operation.getLabel(), editingContext, null); //$NON-NLS-1$
		this.operation = operation;
	}

	@Override
	public String getLabel() {
		return operation.getLabel();
	}

	@Override
	protected IStatus doExecute(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		return operation.execute(monitor, info);
	}

	@Override
	protected IStatus doRedo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		return operation.redo(monitor, info);
	}

	@Override
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		return operation.undo(monitor, info);
	}

	@Override
	public IStatus doOperation(boolean updateControl) throws ExecutionException {
		// will not be called
		return Status.OK_STATUS;
	}
}
