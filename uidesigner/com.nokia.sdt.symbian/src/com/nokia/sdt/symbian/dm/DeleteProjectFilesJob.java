/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.symbian.dm;

import com.nokia.cpp.internal.api.utils.ui.FilesListDialog;
import com.nokia.sdt.symbian.Messages;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.ui.progress.UIJob;

import java.util.ArrayList;
import java.util.List;

public class DeleteProjectFilesJob extends UIJob {

	private List<IFile> filesToDelete;
	private final boolean confirm;

	public DeleteProjectFilesJob(List<IFile> files, boolean confirm) {
		super(Messages.getString("DeleteProjectFilesJob.JobName")); //$NON-NLS-1$
		filesToDelete = new ArrayList<IFile>(files);
		this.confirm = confirm;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.progress.UIJob#runInUIThread(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		if (confirm) {
			final FilesListDialog confirmDlg = 
				new FilesListDialog(getDisplay().getActiveShell(), filesToDelete, 
						Messages.getString("DeleteProjectFilesJob.DialogTitle"), //$NON-NLS-1$
						Messages.getString("DeleteProjectFilesJob.DialogCaption"), false) { //$NON-NLS-1$
							@Override
							protected void okPressed() {
								super.okPressed();
								if (!filesToDelete.isEmpty()) {
									final List<IFile> filesToDelete_ = filesToDelete;
									WorkspaceJob job = new WorkspaceJob(
											Messages.getString("DeleteProjectFilesJob.JobTitle")) { //$NON-NLS-1$
										@Override
										public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
											for (IFile file : filesToDelete_) {
												file.delete(true, monitor);
											}
											
											return Status.OK_STATUS;
										}
									};
									
									job.schedule();
								}
							}
			};
			confirmDlg.setBlockOnOpen(false);
			confirmDlg.open();
		}
		
		return Status.OK_STATUS;
	}

}
