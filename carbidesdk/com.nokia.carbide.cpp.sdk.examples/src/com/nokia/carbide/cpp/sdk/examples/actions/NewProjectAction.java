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

import com.nokia.carbide.cpp.sdk.examples.jobs.NewProjectJob;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * This action is invoked when the user chooses our menu item.
 * The action is responsible for prompting the user for a
 * valid new project name.
 * The project creation is performed in {@link NewProjectJob}
 */
public class NewProjectAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public NewProjectAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		
		// Validates that the project name is legal and doesn't
		// refer to an existing project.
		IInputValidator validator = new IInputValidator() {
			public String isValid(String newText) {
				String result = null;
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IStatus status = workspace.validateName(newText, IResource.PROJECT);
				if (status.isOK()) {
					IResource existingProject = workspace.getRoot().findMember(newText);
					if (existingProject != null) {
						result = "A project with that name already exists.";
					}
				} else {
					result = status.getMessage();
				}
				return result;
			}
		};
		
		InputDialog dlg = new InputDialog(window.getShell(),
				"Create New Project", "Please enter a name for the new project",
				null, validator);
		if (dlg.open() == Dialog.OK) {
			// Do all the work in a WorkspaceJob
			NewProjectJob job = new NewProjectJob(dlg.getValue());
			job.schedule();
		}
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}