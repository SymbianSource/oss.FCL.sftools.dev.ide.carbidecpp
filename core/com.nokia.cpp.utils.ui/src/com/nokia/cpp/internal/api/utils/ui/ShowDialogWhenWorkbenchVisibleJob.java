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
package com.nokia.cpp.internal.api.utils.ui;

import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * A job that shows a dialog only once the workbench is visible.
 *
 */
public class ShowDialogWhenWorkbenchVisibleJob extends Job {

	private Dialog dialog;
	private Shell shell;

	public ShowDialogWhenWorkbenchVisibleJob(Shell shell, Dialog dialog) {
		super("Dialog job"); // never shown //$NON-NLS-1$ 
		this.shell = shell;
		if (this.shell == null)
			this.shell = WorkbenchUtils.getActiveShell();
		this.dialog = dialog;
		
		// don't show up on the Progress view
		setUser(false);
		setSystem(true);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.progress.UIJob#runInUIThread(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public IStatus run(IProgressMonitor monitor) {
		while (true) {
			final boolean executed[] = { false };
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					if (shell.isVisible()) {
						dialog.open();
						executed[0] = true;
					}
				}
			});
			if (executed[0])
				break;
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				break;
			}
		}
		return Status.OK_STATUS;
	}

	/**
	 * Create and schedule a job to show a dialog once the shell is visible.
	 * No response will be reported from the dialog, so we mark it non-blocking.
	 * @param shell the shell, or null to select the active workbench window
	 * @param dialog the dialog to show
	 */
	public static void start(Shell shell, Dialog dialog) {
		dialog.setBlockOnOpen(false);
		Job job = new ShowDialogWhenWorkbenchVisibleJob(shell, dialog);
		job.schedule();
	}

}
