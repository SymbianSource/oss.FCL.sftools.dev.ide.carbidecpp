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
package com.nokia.carbide.cpp.internal.project.ui.editors.images;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.*;

public abstract class MultiImageEditorOperation extends AbstractOperation {

	private MultiImageEditorContextBase editorContext;
	
	/**
	 * @param label
	 */
	public MultiImageEditorOperation(String label, MultiImageEditorContextBase editorContext) {
		super(label);
		this.editorContext = editorContext;
	}

	public MultiImageEditorContextBase getEditorContext() {
		return editorContext;
	}
	
	/** Test whether the operation does any work or changes any state.  
	 * If not, the operation is neither stacked nor executed. */
	protected abstract boolean doesAnything();

	/** Undo the operation, throwing exceptions if failure. */
	protected abstract void undo();

	/** Do or redo the operation, throwing exceptions if failure. */
	protected abstract void redo();

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.operations.AbstractOperation#execute(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		try {
			redo();
			return Status.OK_STATUS;
		} catch (Throwable t) {
			throw new ExecutionException(t.getMessage(), t);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.operations.AbstractOperation#redo(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		try {
			redo();
			return Status.OK_STATUS;
		} catch (Throwable t) {
			throw new ExecutionException(t.getMessage(), t);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.operations.AbstractOperation#undo(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		try {
			undo();
			return Status.OK_STATUS;
		} catch (Throwable t) {
			throw new ExecutionException(t.getMessage(), t);
		}
	}
}
