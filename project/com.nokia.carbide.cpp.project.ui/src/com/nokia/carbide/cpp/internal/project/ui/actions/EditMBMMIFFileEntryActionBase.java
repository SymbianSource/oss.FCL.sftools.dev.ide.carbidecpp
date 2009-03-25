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
import com.nokia.carbide.cpp.internal.project.ui.views.IMBMMIFFileEntry;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

public abstract class EditMBMMIFFileEntryActionBase extends ModifyMultiImageSourceActionBase {

	protected IMBMMIFFileEntry mbmMifFileEntry;

	public EditMBMMIFFileEntryActionBase() {
	}

	/**
	 * @param view
	 */
	protected void editMbmMifFileInView(IView view) {
		IMultiImageSource source = findMultiImageSource(view, mbmMifFileEntry.getTargetFilePath());
		
		editMbmMifFileInView(view, source);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if (!(selection instanceof IStructuredSelection) || selection.isEmpty()
				|| ((IStructuredSelection) selection).size() != 1) {
			mbmMifFileEntry = null;
			modelFile = null;
			return;
		}
		
		Object element = ((IStructuredSelection)selection).getFirstElement();
		if (element instanceof IMBMMIFFileEntry) {
			action.setEnabled(true);
			mbmMifFileEntry = (IMBMMIFFileEntry) element;
			modelFile = mbmMifFileEntry.getModelFile();
		} else {
			action.setEnabled(false);
			mbmMifFileEntry = null;
			modelFile = null;
		}
			
	}

	public void setMBMMIFFileEntry(IMBMMIFFileEntry entry) {
		this.mbmMifFileEntry = entry;
		if (entry != null)
			this.modelFile = entry.getModelFile();
		else
			this.modelFile = null;
	}

}
