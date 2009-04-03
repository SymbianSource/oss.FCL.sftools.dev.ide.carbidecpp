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
package com.nokia.sdt.workspace.impl;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.progress.WorkbenchJob;

import com.nokia.sdt.editor.EditorServices;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.nokia.sdt.workspace.IProjectContext;
import com.nokia.sdt.workspace.WorkspaceContext;

public class UnloadCachedModelsJob extends WorkbenchJob {
	
	// ms delay for UI job scheduling for job that checks if we can unload cached models
    public static final int JOB_DELAY = 60 * 1000;
    private static String JOB_NAME = "Unload cached models";
    
    private boolean reschedule;

    static void initRecurringJob() {
		if (!WorkbenchUtils.isJUnitRunning()) {
			UnloadCachedModelsJob job = new UnloadCachedModelsJob(true);
			job.schedule(JOB_DELAY);
		}
	}

	public UnloadCachedModelsJob(boolean reschedule) {
		super(JOB_NAME);
		this.reschedule = reschedule;
	}

    @Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
    	if (reschedule) {
    		schedule(JOB_DELAY);
    	}
    	IProjectContext[] projects = WorkspaceContext.getContext().getProjects();
    	if (projects != null) {
    		for (IProjectContext pc : projects) {
    			IDesignerDataModelEditor[] editors = EditorServices.findEditorsForProject(pc.getProject());
    			if (editors == null || editors.length == 0) {
    				pc.unloadCachedModels();
    			}
    		}
    	}
		return Status.OK_STATUS;
	}
}
