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
package com.nokia.carbide.cpp.internal.project.ui.actions;

import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.IView;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

public abstract class AddMBMMIFEntryActionBase extends ModifyMultiImageSourceActionBase {

	public AddMBMMIFEntryActionBase() {
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (!(selection instanceof IStructuredSelection) || selection.isEmpty()
				|| ((IStructuredSelection) selection).size() != 1) {
			modelFile = null;
			return;
		}
		
		Object element = ((IStructuredSelection) selection).getFirstElement();
		if (element instanceof IFile) {
			modelFile = (IFile) element;
			action.setEnabled(true);
		} else {
			modelFile = null;
			action.setEnabled(false);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.actions.ModifyMultiImageSourceActionBase#postDialogAcceptedActions(com.nokia.carbide.cpp.epoc.engine.model.IView, com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource)
	 */
	@Override
	protected void postDialogAcceptedActions(IView view, IMultiImageSource source) {
		getMultiImageSources(view).add(source);
	}
	
}
