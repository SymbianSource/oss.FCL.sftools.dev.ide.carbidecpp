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
package com.nokia.carbide.internal.api.updater;

import com.nokia.carbide.updater.extension.IUpdateTrigger;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Encapsulates all the settings and state for the update projects
 * A listener interface is provided to track updater status.
 * 
 * Updatings consists of two phases:
 * - project updating applies any necessary changes to Eclipse/CDT/Carbide
 *   project files, including .project, .cdtproject, and builder information
 * - file level updates applies changes to other files in the project. At the
 *   initial writing this includes UI Designer changes to .uidesign and
 *   C++ source files.
 */
public interface IProjectUpdateSession {
	
	/** Defined updater states
	 */
	enum State {
		NOT_SCHEDULED, 			// no work queued
		SCHEDULED,	   			// queued but not started
		SCANNING_PROJECTS,  	// examining projects
		UPDATING_PROJECTS,		// updating project formats
		GATHERING_REFACTORING, 	// gather file level update refactorings
		APPLYING_REFACTORING, 	// applying the factorings
		COMPLETE				// all done
	};
	
	/**
	 * Listener interface for state changes
	 */
	interface IStateListener {
		void stateChanged(IProjectUpdateSession session);
	}
	
	/**
	 * Indicate whether this session is something the user initiated and
	 * should have full progress feedback, or perhaps more subtle feedback.
	 * Controls whether {@link Job#setUser(boolean)} is used, which is a hint
	 * to Eclipse and doesn't specify exactly what changes. In practice it seems
	 * to control whether a modal progress dialog (with "Run in Background") button
	 * is shown, or whether the job runs in the background always.
	 * @param usedInitiated
	 * default is FALSE
	 */
	void setUserInitiated(boolean userInitiated);
	boolean getUserInitated();
	
	/**
	 * When true, no dialog is shown where no updates are needed
	 * default is TRUE
	 */
	void setSilentIfEmpty(boolean silentIfEmpty);
	boolean getSilentIfEmpty();
	
	/**
	 * When true, a dialog is used to prompt whether the user
	 * wants to proceed with file updates after project updates are complete.
	 * default is TRUE
	 */
	void setConfirmFileUpdates(boolean confirm);
	boolean getConfirmFileUpdates();
	
	/**
	 * When false the project updating step is omitted
	 * default is TRUE
	 */
	void setShowProjectUpdateDialog(boolean showProjectUpdateDialog);
	boolean getShowProjectUpdateDialog();
	
	/**
	 * When false the refactoring step is omitted
	 * default is TRUE
	 */
	void setShowRefactoringDialog(boolean showRefactoringDialog);
	boolean getShowRefactoringDialog();
	
	/**
	 * When true, the session will scan the workspace for any projects
	 * needed updating. When false the client must populate the list of
	 * projects to be examined 
	 * @see IProjectUpdateSession#addUpdatableProject(IProject)
	 * default is FALSE
	 */
	void setScanForProjects(boolean scanForProjects);
	boolean getScanForProjects();
	
	
	/**
	 * When true, postTrigger will be called on update triggers.
	 * @param shouldCallTriggers
	 * @see IUpdateTrigger#postTrigger(com.nokia.carbide.updater.extension.IUpdateTrigger.UpdateType, boolean)
	 * default is FALSE
	 */
	void setShouldCallTriggers(boolean shouldCallTriggers);
	boolean shouldCallTriggers();

	/**
	 * The projects that will be examined for updating. Note that
	 * a subset may actually need updating and a further subset may
	 * eventually be selected by the user.
	 * @return array of IProject
	 */
	IProject[] getUpdatableProjects();
	
	/**
	 * Add a project to the set of updatable projects. May only be 
	 * called when state is one of NOT_SCHEDULED, SCHEDULED, or SCANNING_PROJECTS.
	 * After that point an IllegalStateException is thrown.
	 * @param project
	 * @throws IllegalStateException
	 */
	void addUpdatableProject(IProject project);
	
	/**
	 * Removes a project from the set of updatable projects. May only be 
	 * called when state is one of NOT_SCHEDULED, SCHEDULED, or SCANNING_PROJECTS.
	 * After that point an IllegalStateException is thrown.
	 * @param project
	 * @throws IllegalStateException
	 */
	void removeUpdatableProject(IProject project);
	/**
	 * The projects that will be examined for file-level updating. Note that
	 * a subset may actually need updating and a further subset may
	 * eventually be selected by the user.
	 * @return array of IProject
	 */
	IProject[] getRefactoringProjects();
	
	/**
	 * Add a project to the set of potential file-level update projects. May only be 
	 * called when state is one of NOT_SCHEDULED, SCHEDULED, or SCANNING_PROJECTS.
	 * After that point an IllegalStateException is thrown.
	 * @param project
	 * @throws IllegalStateException
	 */
	void addRefactoringProject(IProject project);
	
	/**
	 * Adds all projects in the workspace to the set of candidate
	 * refactoring projects. Any projects already added as refactoring
	 * projects for other sessions will not be added to this session.
	 */
	void addAllWorkspaceRefactoringProjects();
	
	/**
	 * Removes a project from the set of file-level update projects. May only be 
	 * called when state is one of NOT_SCHEDULED, SCHEDULED, or SCANNING_PROJECTS.
	 * After that point an IllegalStateException is thrown.
	 * @param project
	 * @throws IllegalStateException
	 */
	void removeRefactoringProject(IProject project);
	
	/**
	 * Asynchronously schedules work according to the session settings.
	 */
	void updateProjects();
	
	void updateProjects(long delayInMillis);

	/**
	 * Returns the current state
	 */
	State getState();

	/**
	 * Once the session is in the COMPLETED state, returns the
	 * IStatus of project-level updates. Status of file-level
	 * updates is not available.
	 * @return
	 */
	IStatus getResult();
	
	/**
	 * Installs a listener to be notified of state changes.
	 * @param listener
	 */
	void addStateListener(IStateListener listener);
	
	/**
	 * Removes a previously added state change listener.
	 * @param listener
	 */
	void removeStateListener(IStateListener listener);
}
