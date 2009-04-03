/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.sdk.examples.actions;

import com.nokia.carbide.cpp.sdk.examples.jobs.ProjectReportJob;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * This action is invoked when the user chooses our menu item.
 * The action is responsible for prompting the user for a
 * a project.
 * The report creation is performed in {@link ProjectReportJob}
 */
public class CreateReportAction implements IWorkbenchWindowActionDelegate {
	
	private IWorkbenchWindow window;

	public CreateReportAction() {
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run(IAction action) {
		// Ask the user to choose a project
		ListDialog dialog = new ListDialog(window.getShell());
		dialog.setTitle("Project Report Example");
		dialog.setMessage("Please select a project");
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot().getProjects());
		dialog.setContentProvider(new ArrayContentProvider());
		dialog.setLabelProvider(new WorkbenchLabelProvider());
		if (dialog.open() == Dialog.OK) {
			Object[] selection = dialog.getResult();
			if (selection != null && selection.length > 0 &&
				selection[0] instanceof IProject) {
				IProject project = (IProject) selection[0];
				
				// Do all the work in a WorkspaceJob
				ProjectReportJob job = new ProjectReportJob(project);
				job.schedule();
			}
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}
}
