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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * A job that runs an action once the workbench is visible.
 */
public class RunRunnableWhenWorkbenchVisibleJob extends Job {

	private final Runnable runnable;
	private Shell shell;

	public RunRunnableWhenWorkbenchVisibleJob(Runnable runnable) {
		super(""); // never shown //$NON-NLS-1$ 
		this.runnable = runnable;
		this.shell = WorkbenchUtils.getActiveShell();
		
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
					if (shell != null && shell.isVisible()) {
						runnable.run();
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
	 * Create and schedule a job to run a Runnable once the shell is visible.
	 * @param runnable the Runnable to run
	 */
	public static void start(Runnable runnable) {
		Job job = new RunRunnableWhenWorkbenchVisibleJob(runnable);
		job.schedule();
	}

}
