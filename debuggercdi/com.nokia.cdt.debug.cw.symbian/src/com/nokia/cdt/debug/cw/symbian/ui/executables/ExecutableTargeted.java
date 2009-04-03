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
package com.nokia.cdt.debug.cw.symbian.ui.executables;

import org.eclipse.cdt.debug.core.executables.ExecutablesManager;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.freescale.cdt.debug.cw.core.IExecutableTargeted;

public class ExecutableTargeted implements IExecutableTargeted {

	public void executableFileTargeted(String fullPath) {
		final String finalPath = fullPath;
		String jobName = "Checking " + new Path(fullPath).lastSegment();
		Job targetedJob = new Job(jobName){

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				ExecutablesManager.getExecutablesManager().importExecutables(new String[] {finalPath} , monitor);
				return Status.OK_STATUS;
			}};
			
			targetedJob.schedule();
	}

}
