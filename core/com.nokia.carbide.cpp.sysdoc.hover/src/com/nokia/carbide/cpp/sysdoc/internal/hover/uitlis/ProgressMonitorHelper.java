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
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverManager;

/**
 * This class used to show progress of an process on task bar. Indexing an
 * interchange file and extracting an SDL file takes some time. Progress of
 * these can be monitored and controlled by this class.
 */
public class ProgressMonitorHelper {

	private IProgressMonitor monitor;
	private ProgressJob runnableJob;
	private int taskSize = 100;
	private final String taskName;

	public ProgressMonitorHelper(ProgressJob runnableJob, String task) {
		this.runnableJob = runnableJob;
		this.taskName = task;
	}

	/**
	 * creates a {@link Job} which starts processing the task
	 * 
	 * @throws Exception
	 */
	public void run() throws Exception {

		if (HoverManager.isTestMode()) {
			runnableJob.start();
			runnableJob.setDone(true);
			return;
		}

		Job job = new Job(taskName) {
			@Override
			protected IStatus run(IProgressMonitor arg0) {
				monitor = arg0;
				monitor.beginTask(taskName, taskSize);
				try {
					runnableJob.start();
				} catch (Exception e) {
					HoverManager.getInstance().haltHoveringService(e);
				}
				monitor.done();
				// if (!runnableJob.isDone()){
				// throw new HoverInitializationException("");
				// }
				monitor.done();				
				runnableJob.setDone(true);
				return Status.OK_STATUS;
			}
		};
		job.setPriority(Job.SHORT); // high priority
		job.schedule();
	}

	public int getTaskSize() {
		return taskSize;
	}

	public void setTaskSize(int taskSize) {
		this.taskSize = taskSize;
	}

	public boolean isMonitorCanceled() {
		if (monitor == null) {
			return false;
		}
		return monitor.isCanceled();
	}

	public void subTask(String msg) {
		if (monitor == null) {
			return;
		}
		monitor.subTask(msg);
	}

	public void worked() {
		if (monitor == null) {
			return;
		}
		monitor.worked(1);
	}
}
