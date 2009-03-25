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

package com.nokia.sdt.symbian.ui.appeditor;

import com.nokia.sdt.symbian.workspace.ISymbianProjectContext;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.workspace.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class EditApplicationAction implements IObjectActionDelegate {
	
	private ISymbianProjectContext selectedProject;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		selectedProject = null;
	}
	
	public void run(IAction action) {
		if (selectedProject != null) {
			IDesignerDataModelSpecifier rootModel = selectedProject.getRootModel();
			if (rootModel != null) {
				try {
					rootModel.openInEditor();
				} catch (CoreException x) {
					IStatus status = Logging.newSimpleStatus(0, IStatus.ERROR, x.getLocalizedMessage(), x);
					Logging.showErrorDialog(null, null, status);
				}
			}			
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		selectedProject = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			IProjectContext pc = WorkspaceContext.getContext().discoverProjectContext(ss.getFirstElement());
			if (pc != null) {
				selectedProject = (ISymbianProjectContext) pc.getAdapter(ISymbianProjectContext.class);
			}
		}
		action.setEnabled(selectedProject != null);
	}
}
