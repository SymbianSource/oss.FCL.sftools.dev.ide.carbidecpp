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
package com.nokia.sdt.symbian.workspace.impl;

import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.nokia.sdt.symbian.Messages;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import java.text.MessageFormat;

public class DesignFileDeletedJob extends WorkspaceJob {

	private DesignerDataModelSpecifier specifier;

	public DesignFileDeletedJob(DesignerDataModelSpecifier specifier) {
		super(Messages.getString("DesignFileDeletedJob.jobTitle")); //$NON-NLS-1$
		this.specifier = specifier;
	}

	@Override
	public IStatus runInWorkspace(final IProgressMonitor monitor) {
		final IStatus[] result = new IStatus[1];
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				if (specifier.isRoot()) {
					result[0] = handleRootDesignDeleted(monitor);
				} else {
					result[0] = handleViewDesignDeleted(monitor);
				}
			}
		});
		return result[0];
	}

	IStatus handleRootDesignDeleted(IProgressMonitor monitor) {
		IFile designFile = specifier.getModelFile();
		if (!WorkbenchUtils.isJUnitRunning()) {
			String title = Messages
					.getString("DesignerDataModelSpecifier.dialogTitle"); //$NON-NLS-1$
			String fmt = Messages
					.getString("DesignerDataModelSpecifier.deleteRootModelWarning"); //$NON-NLS-1$
			Object params[] = { designFile.getFullPath().toString() };
			String msg = MessageFormat.format(fmt, params);
			MessageDialog.openWarning(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(), title, msg);
		}
		specifier.dispose();
		return Status.OK_STATUS;
	}

	IStatus handleViewDesignDeleted(IProgressMonitor monitor) {
		IStatus result;
		IFile designFile = specifier.getModelFile();
		String relativePath = designFile.getProjectRelativePath().toString();
		if (DesignReferenceJobUtilities.isViewReferencedInApplication(specifier, relativePath)) {
			String title = Messages
					.getString("DesignerDataModelSpecifier.dialogTitle"); //$NON-NLS-1$
			String fmt = Messages.getString("DesignerDataModelSpecifier.1"); //$NON-NLS-1$
			Object params[] = { designFile.getFullPath().toString() };
			String msg = MessageFormat.format(fmt, params);
			boolean proceed = true;
			if (!WorkbenchUtils.isJUnitRunning()) {
				proceed = MessageDialog.openQuestion(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(), title, msg);
			}
			if (proceed) {
				
				String path = designFile.getProjectRelativePath().toString();
				result = DesignReferenceJobUtilities.removeViewDesignReference(specifier.getProjectContext(), path, monitor);
			} else {
				result = Status.OK_STATUS;
			}
		} else {
			result = Status.OK_STATUS;
		}
		specifier.dispose();
		return result;
	}
}
