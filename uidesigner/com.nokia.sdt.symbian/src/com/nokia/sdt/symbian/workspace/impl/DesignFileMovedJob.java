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

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.nokia.sdt.symbian.Messages;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.workspace.IProjectContext;
import com.nokia.sdt.workspace.WorkspaceContext;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;

import java.text.MessageFormat;

public class DesignFileMovedJob extends UIJob {

	private DesignerDataModelSpecifier specifier;

	private IPath oldPath;

	public DesignFileMovedJob(DesignerDataModelSpecifier specifier,
			IPath oldPath) {
		super(Messages.getString("DesignFileMovedJob.jobName")); //$NON-NLS-1$
		Check.checkArg(specifier);
		Check.checkArg(oldPath);
		this.specifier = specifier;
		this.oldPath = oldPath;
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		IStatus result;
		if (specifier.isRoot()) {
			result = handleRootDesignMoved(monitor);
		} else {
			result = handleViewDesignMoved(monitor);
		}
		return result;
	}

	IStatus handleRootDesignMoved(IProgressMonitor monitor) {
		if (!WorkbenchUtils.isJUnitRunning()) {
			String title = Messages
					.getString("DesignerDataModelSpecifier.dialogTitle"); //$NON-NLS-1$
			String fmt = Messages
					.getString("DesignerDataModelSpecifier.moveRootModelWarning"); //$NON-NLS-1$
			IFile designFile = specifier.getModelFile();
			Object params[] = { designFile.getFullPath().toString() };
			String msg = MessageFormat.format(fmt, params);
			MessageDialog.openWarning(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(), title, msg);
		}
		specifier.dispose();
		return Status.OK_STATUS;
	}

	IStatus handleViewDesignMoved(IProgressMonitor monitor) {
		IStatus result = Status.OK_STATUS;
		IFile designFile = specifier.getModelFile();
		IFile oldFile = ResourcesPlugin.getWorkspace().getRoot().getFile(oldPath);
		IProject oldProject = oldFile.getProject();
		IProject newProject = designFile.getProject();
		if (newProject != null && newProject == oldProject) {
			boolean isDesignFile = SymbianModelUtils.isDesignName(designFile
					.getName());
			if (isDesignFile) {
				// Only ask the user about updating the app model if the design is currently referenced
				String oldRelativePath = oldPath.removeFirstSegments(1).toString();
				if (DesignReferenceJobUtilities.isViewReferencedInApplication(specifier, oldRelativePath)) {
					
					String title = Messages
					.getString("DesignerDataModelSpecifier.dialogTitle"); //$NON-NLS-1$
					String fmt = Messages.getString("DesignerDataModelSpecifier.0"); //$NON-NLS-1$
					Object params[] = { designFile.getFullPath().toString() };
					String msg = MessageFormat.format(fmt, params);
					boolean proceed = true;
					if (!WorkbenchUtils.isJUnitRunning()) {
						proceed = MessageDialog.openQuestion(PlatformUI
							.getWorkbench().getActiveWorkbenchWindow().getShell(),
							title, msg);
					}
					if (proceed) {
						String newRelativePath = designFile
						.getProjectRelativePath().toString();
						result = DesignReferenceJobUtilities.renameViewDesignReference(specifier,
								oldRelativePath, newRelativePath, monitor);
					}
				} 
			} else {
				// Changed the file extension, we will no longer treat it as a design
				String title = Messages
						.getString("DesignerDataModelSpecifier.dialogTitle"); //$NON-NLS-1$
				String fmt = Messages
						.getString("DesignerDataModelSpecifier.changeViewDesignExtension"); //$NON-NLS-1$
				Object params[] = { designFile.getFullPath().toString() };
				String msg = MessageFormat.format(fmt, params);
				boolean proceed = true;
				if (!WorkbenchUtils.isJUnitRunning()) {
					proceed = MessageDialog.openQuestion(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						title, msg);
				}
				if (proceed) {
					IProjectContext pc = WorkspaceContext.getContext().getContextForProject(oldProject);
					if (pc != null) {
						result = DesignReferenceJobUtilities.removeViewDesignReference(pc,
								oldFile.getProjectRelativePath().toString(), 
								monitor);
					}
				}
				specifier.dispose();
			}
		} else {
			// moved to a different project
			String title = Messages
					.getString("DesignerDataModelSpecifier.dialogTitle"); //$NON-NLS-1$
			String fmt = Messages
					.getString("DesignerDataModelSpecifier.moveViewDesignAcrossProjects"); //$NON-NLS-1$
			Object params[] = { designFile.getFullPath().toString() };
			String msg = MessageFormat.format(fmt, params);
			boolean proceed = true;
			if (!WorkbenchUtils.isJUnitRunning()) {
				proceed = MessageDialog.openQuestion(PlatformUI
					.getWorkbench().getActiveWorkbenchWindow().getShell(),
					title, msg);
			}
			if (proceed) {
				IProjectContext pc = WorkspaceContext.getContext().getContextForProject(oldProject);
				if (pc != null) {			
					result = DesignReferenceJobUtilities.removeViewDesignReference(pc,
							oldFile.getProjectRelativePath().toString(), 
							monitor);
				}
			}
			if (newProject == null) {
				specifier.dispose();
			}
		}
		return result;
	}
}
