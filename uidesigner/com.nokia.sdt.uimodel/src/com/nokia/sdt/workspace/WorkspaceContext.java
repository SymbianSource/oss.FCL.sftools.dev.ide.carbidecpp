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
package com.nokia.sdt.workspace;

import com.nokia.sdt.workspace.impl.WorkspaceContextImpl;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Singleton object providing access to UI Designer projects and models
 * 
 *
 */
public abstract class WorkspaceContext {
	
	protected static WorkspaceContext instance;
	public static final int QUERY_SAVE_CHANGES_ON_DELETE_PRIORITY = Job.INTERACTIVE;
	public static final int QUERY_REMOVE_FROM_APP_ON_DELETE_PRIORITY = Job.SHORT;
	
	/**
	 * Return the singleton context object
	 */
	public static WorkspaceContext getContext() {
		if (instance == null) {
			instance = new WorkspaceContextImpl();
            instance.init();
		}
		return instance;
	}

	/**
     * Initialize the instance. 
     */
	protected abstract void init();

    /**
	 * Return the current context objects. There will be
	 * an IProjectContext object for any open project that
	 * contains IDesignerDataModels, whether loaded or unloaded
	 */
	public abstract IProjectContext[] getProjects();
	
	/**
	 * Returns an IProjectContext for the given project, if one exists
	 * or can be created.
	 */
	public abstract IProjectContext getContextForProject(IProject project);
	
	/**
	 * Returns an existing context, but does not create a new one.
	 * Generally this should not be used without a specific reason,
	 * use getContextForProject instead.
	 */
	public abstract IProjectContext getExistingContextForProject(IProject project);
	
	/**
	 * Utility to interrogate an object and discover its project context
	 */
	public abstract IProjectContext discoverProjectContext(Object object);
	
	/**
	 * Call this method if you've caused changes in a project and
	 * need the changes reflected synchronously. Normally changes
	 * are handled asynchronously from Eclipse resource change 
	 * notifications.
	 */
	public abstract void projectChanged(IProject project);
	
	/**
	 * Attempts to find an IDesignerDataModelSpecifier for the
	 * given Eclipse resource. 
	 */
	public abstract IDesignerDataModelSpecifier findSpecifierForResource(IResource resource);
	
	public abstract void addListener(IWorkspaceContextListener listener);
	
	public abstract void removeListener(IWorkspaceContextListener listener);

	public abstract void unloadCachedModels();
	
	/**
	 * Schedule a job to run which will attempt to
	 * unloaded cached models.
	 */
	public abstract void scheduleUnloadJob();
	
}
