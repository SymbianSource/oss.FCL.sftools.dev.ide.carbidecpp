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

import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPModel;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * This is an editor input which works on a MMP or image makefile view.
 * <p>
 * An editor creates this input and allows the multi-image editor to modify
 * the contents of the view.  The editor will queue up changes on a local copy
 * of affected view objects until it is saved, at which point such changes 
 * published as an aggregate IUndoableOperation through the IOperationHistory provided.
 * For the case where the editor persists past a save, it may also support a Revert
 * operation, which removes such operations from the history.
 *
 */
public class MultiImageSourceEditorInput implements IEditorInput {

	private IView view;
	private IMultiImageSource source;
	private IOperationHistory history;

	public MultiImageSourceEditorInput(IOperationHistory history, IView view, IMultiImageSource source) {
		this.history = history;
		this.view = view;
		this.source = source;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	public boolean exists() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return ImageDescriptor.getMissingImageDescriptor();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getName()
	 */
	public String getName() {
		return ((IMMPModel)view.getModel()).getPath().lastSegment();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	public IPersistableElement getPersistable() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	public String getToolTipText() {
		return ((IMMPModel)view.getModel()).getPath().toOSString();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		if (adapter.isAssignableFrom(IMultiImageSource.class))
			return source;
		if (adapter.isAssignableFrom(IView.class))
			return view;
		if (adapter.isAssignableFrom(IOperationHistory.class))
			return history;
		return null;
	}
}
