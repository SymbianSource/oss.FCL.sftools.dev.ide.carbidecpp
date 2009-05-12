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
package com.nokia.carbide.cpp.internal.project.ui.wizards;

import com.nokia.carbide.cpp.internal.api.project.core.ResourceChangeListener;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.*;
import org.eclipse.ui.ide.IDE;

import java.io.*;
import java.text.MessageFormat;

/**
 * This is a sample new wizard. Its role is to create a new file 
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace 
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file with the extension
 * "mmp". If a sample multi-page editor (also available
 * as a template) is registered for the same extension, it will
 * be able to open it.
 */

public class MMPWizard extends Wizard implements INewWizard {
	private MMPWizardPage page;
	private ISelection selection;
	private boolean isTest;

	/**
	 * Constructor for MMPWizard.
	 */
	public MMPWizard() {
		this(false);
	}
	
	public MMPWizard(boolean isTest) {
		super();
		this.isTest = isTest;
		setNeedsProgressMonitor(true);
		setWindowTitle(Messages.getString("MMPWizardPage.dialogTitle")); //$NON-NLS-1$
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new MMPWizardPage(selection, isTest);
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final String containerName = page.getContainerName();
		final String fileName = page.getFileName();
		final boolean isTestMMP = page.isTest();
		
		WorkspaceJob job = new WorkspaceJob("Creating " + fileName) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				try {
					doFinish(containerName, fileName, monitor, isTestMMP);
				} finally {
					monitor.done();
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();
		try {
			job.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return true;
	}
	
	/**
	 * The worker method. It will find the container, create the
	 * file if missing or just replace its contents, and open
	 * the editor on the newly created file.
	 */

	private void doFinish(
		String containerName,
		String fileName,
		IProgressMonitor monitor,
		boolean isTestMMP)
		throws CoreException {
		// create a sample file
		String taskName = MessageFormat.format(Messages.getString("MMPWizard.creatingTaskName"), new Object[] {fileName}); //$NON-NLS-1$
		monitor.beginTask(taskName, 2);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			String msg = MessageFormat.format(Messages.getString("MMPWizard.noFolderError"), new Object[]{containerName}); //$NON-NLS-1$
			throwCoreException(msg);
		}
		IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(fileName));
		try {
			String initialContents = ""; //$NON-NLS-1$
			InputStream stream = new ByteArrayInputStream(initialContents.getBytes());
			if (file.exists()) {
				file.setContents(stream, true, true, monitor);
			} else {
				file.create(stream, true, monitor);
				ResourceChangeListener.addMMPFileToProject(file.getProject(), file, isTestMMP);
			}
			stream.close();
		} catch (IOException e) {
		}

		monitor.worked(1);
		monitor.setTaskName(Messages.getString("MMPWizard.openFileTargetName")); //$NON-NLS-1$
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page =
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, file, true);
				} catch (PartInitException e) {
				}
			}
		});
		monitor.worked(1);
	}

	private void throwCoreException(String message) throws CoreException {
		IStatus status =
			new Status(IStatus.ERROR, ProjectUIPlugin.PLUGIN_ID, IStatus.OK, message, null); //$NON-NLS-1$
		throw new CoreException(status);
	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		setDefaultPageImageDescriptor(CarbideUIPlugin.getSharedImages().getImageDescriptor(ICarbideSharedImages.IMG_NEW_MMP_FILE_WIZARD_BANNER));
	}

	public WizardPage getMMPWizardPage() {
		return page;
	}

}