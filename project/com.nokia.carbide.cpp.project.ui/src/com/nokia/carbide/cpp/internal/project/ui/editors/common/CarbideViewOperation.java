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

import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.ui.editor.EditingContextOperation;
import com.nokia.cpp.internal.api.utils.ui.editor.IEditingContext;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.*;

/**
 * Base class for undoable operations on an IView
 */
public abstract class CarbideViewOperation extends EditingContextOperation {

	private final IView view;
	private final ControlHandler controlHandler;
	
	private boolean noopUndo; // one-time flag to no-op an undo
	
	
	protected CarbideViewOperation(IView view, String label, 
			IEditingContext editingContext,
			ControlHandler controlHandler) {
		super(label, editingContext);
		this.controlHandler = controlHandler;
		Check.checkArg(view);
		this.view = view;
	}
	
	public IView getView() {
		return view;
	}
	
	/**
	 * Apply the model change for execute/redo, optionally
	 * updating the GUI
	 * @param updateControl only update GUI if true
	 */
	public abstract IStatus doOperation(boolean updateControl) throws ExecutionException;
	
	@Override
	protected IStatus doExecute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		IStatus status = doOperation(false);
		return status;
	}

	@Override
	protected IStatus doRedo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		IStatus status = doOperation(true);
		return status;
	}
	
	/**
	 * Refresh UI related to this element. Does not switch UI context to the element
	 *
	 */
	public void refreshUI() {
		if (controlHandler != null) {
			controlHandler.refresh();
		}
	}

	public ControlHandler getControlHandler() {
		return controlHandler;
	}

	@Override
	public Object getAdapter(Class adapter) {
		Object result = null;
		if (ControlHandler.class.equals(adapter)) {
			result = controlHandler;
		}
		if (result == null) {
			result = super.getAdapter(adapter);
		}
		return result;
	}
	
	/**
	 * Set a temporary step to make the next undo
	 * a no-op. This is used when the operation needs
	 * to be "undone" for the propery functioning of the operation
	 * history, but where due to text document management the
	 * operation has already been undone in the model
	 */
	public void setNoopUndo() {
		noopUndo = true;
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		IStatus result;
		if (!noopUndo) {
			result = super.undo(monitor, info);
		} else {
			noopUndo = false;
			restoreEditingContext(info);
			if (controlHandler != null) {
				controlHandler.refresh();
			}
			result = Status.OK_STATUS;
		}
		return result;
	}
}
