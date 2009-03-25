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
package com.nokia.carbide.internal.updater;

import com.nokia.carbide.internal.api.updater.IProjectUpdateSession;
import com.nokia.carbide.updater.CarbideUpdaterPlugin;
import com.nokia.carbide.updater.extension.IRefactoringUpdater;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ListenerList;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.IPDOMManager;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

import java.util.*;

public class ProjectUpdateSession implements IProjectUpdateSession {
	
	// these are the projects to check for required updates. Projects in this set
	// may not need updates or the user may select only a subset of them
	private Set<IProject> updateProjects = Collections.synchronizedSet(new HashSet<IProject>());
	
	// these are the projects to check for refactoring updates
	private Set<IProject> refactoringProjects = Collections.synchronizedSet(new HashSet<IProject>());
	
	private final ListenerList<IStateListener> listeners = new ListenerList<IStateListener>();
	private State state = State.NOT_SCHEDULED;
	private ProjectUpdateJob job;
	private IStatus jobResult;
	private boolean userInitiated;
	private boolean confirmFileUpdates = true;
	private boolean scanForProjects;
	private boolean shouldCallTriggers;
	private boolean showProjectDialog = true;
	private boolean silentIfEmpty = true;
	private boolean showRefactoringDialog = true;

	
	
	ProjectUpdateSession() {
	}
	
	public IProject[] getUpdatableProjects() {
		return updateProjects.toArray(new IProject[updateProjects.size()]);
	}
	
	public void addUpdatableProject(IProject project) {
		if (state == State.NOT_SCHEDULED || state == State.SCHEDULED ||
			state == State.SCANNING_PROJECTS) {
			updateProjects.add(project);
		} else {
			throw new IllegalStateException();
		}
	}

	public void removeUpdatableProject(IProject project) {
		if (state == State.NOT_SCHEDULED || state == State.SCHEDULED ||
			state == State.SCANNING_PROJECTS) {
			updateProjects.remove(project);
		} else {
			throw new IllegalStateException();
		}
	}
	public IProject[] getRefactoringProjects() {
		return refactoringProjects.toArray(new IProject[refactoringProjects.size()]);
	}
	
	public synchronized void addRefactoringProject(IProject project) {
		if (state == State.NOT_SCHEDULED || state == State.SCHEDULED ||
			state == State.SCANNING_PROJECTS) {
			refactoringProjects.add(project);
		} else {
			throw new IllegalStateException();
		}	
	}
	
	public void addAllWorkspaceRefactoringProjects() {
		// adds only those not already part of another session
		ProjectUpdateManager.instance().addAllRefactoringProjectsToSession(this);
	}

	public synchronized void removeRefactoringProject(IProject project) {
		if (state == State.NOT_SCHEDULED || state == State.SCHEDULED ||
			state == State.SCANNING_PROJECTS) {
			refactoringProjects.remove(project);
		} else {
			throw new IllegalStateException();
		}	
	}
	
	public synchronized void updateProjects(long delayInMillis) {
		if (state != State.NOT_SCHEDULED) {
			throw new IllegalStateException();
		}
		job = new ProjectUpdateJob(this);
		setState(State.SCHEDULED);
		job.schedule(delayInMillis);
	}
	
	public void updateProjects() {
		updateProjects(0);
	}
	
	void projectUpdateJobCompleted(ProjectUpdateJob job) {
		Check.checkArg(job);
		Check.checkState(job == this.job);
		this.jobResult = job.getResult();
		this.job = null;
	}
	
	void jobScanningProjectsComplete(ProjectUpdateJob job) {
		Check.checkArg(job);
		Check.checkState(job == this.job);
		setState(State.UPDATING_PROJECTS);
	}

	public State getState() {
		return state;
	}
	
	public IStatus getResult() {
		return jobResult;
	}

	public boolean getUserInitated() {
		return userInitiated;
	}

	public void setUserInitiated(boolean userInitiated) {
		this.userInitiated = userInitiated;
	}

	public boolean getConfirmFileUpdates() {
		return confirmFileUpdates;
	}

	public boolean getScanForProjects() {
		return scanForProjects;
	}
	

	public boolean shouldCallTriggers() {
		return shouldCallTriggers;
	}

	public boolean getShowProjectUpdateDialog() {
		return showProjectDialog;
	}

	public boolean getSilentIfEmpty() {
		return silentIfEmpty;
	}

	public boolean getShowRefactoringDialog() {
		return showRefactoringDialog;
	}

	public void setConfirmFileUpdates(boolean confirm) {
		this.confirmFileUpdates = confirm;
	}

	public void setScanForProjects(boolean scanForProjects) {
		this.scanForProjects = scanForProjects;
	}

	public void setShouldCallTriggers(boolean shouldCallTriggers) {
		this.shouldCallTriggers = shouldCallTriggers;
	}

	public void setShowProjectUpdateDialog(boolean showProjectUpdateDialog) {
		this.showProjectDialog = showProjectUpdateDialog;
	}

	public void setShowRefactoringDialog(boolean showRefactoringDialog) {
		this.showRefactoringDialog = showRefactoringDialog;
	}

	public void setSilentIfEmpty(boolean silentIfEmpty) {
		this.silentIfEmpty = silentIfEmpty;
	}
	
	void setState(State newState) {
		boolean fire = false;
		synchronized(this) {
			if (newState != state) {
				state = newState;
				fire = true;
			}
		}
		if (fire) {
			for (IStateListener l : listeners) {
				l.stateChanged(this);
			}
		}
	}

	public void addStateListener(IStateListener listener) {
		listeners.add(listener);
	}

	public void removeStateListener(IStateListener listener) {
		listeners.remove(listener);
	}
	
	public void doneRefactorings() {
		IProject[] projects = getRefactoringProjects();
		for (IRefactoringUpdater updater : Startup.getRefactoringUpdaters()) {
			for (IProject project : projects) {
				try {
					updater.refactoringComplete(project);
				} catch (Exception x) {
					CarbideUpdaterPlugin.log(x);
				}
			}
		}
	}
	
	static String disableCProjectIndexer(IProject project) {
		String prevIndexerID = null;
		ICProject cproject = CoreModel.getDefault().getCModel().getCProject(project.getName());
		if (cproject != null) {
			try {
				IPDOMManager manager = CCorePlugin.getIndexManager();
				prevIndexerID = manager.getIndexerId(cproject);
				manager.setIndexerId(cproject, IPDOMManager.ID_NO_INDEXER);
			//	PDOMManager internalManager = (PDOMManager) manager;
			//	internalManager.removeProject(cproject);
			} catch (CoreException x) {
				CarbideUpdaterPlugin.log(x);
			}
		}
		return prevIndexerID;
	}
	
	static void enableCProjectIndexer(IProject project, String indexerID) {
		ICProject cproject = CoreModel.getDefault().getCModel().getCProject(project.getName());
		if (cproject != null) {
			try {
				IPDOMManager manager = CCorePlugin.getIndexManager();
			//	PDOMManager internalManager = (PDOMManager) manager;
			//	internalManager.addProject(cproject, null);
				manager.setIndexerId(cproject, indexerID);
			} catch (CoreException x) {
				CarbideUpdaterPlugin.log(x);
			}
		}
	}
}
