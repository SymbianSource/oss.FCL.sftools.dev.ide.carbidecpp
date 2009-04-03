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

import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.nokia.carbide.internal.api.updater.IProjectUpdateManager;
import com.nokia.carbide.internal.api.updater.IProjectUpdateSession;
import com.nokia.carbide.internal.api.updater.IProjectUpdateSession.IStateListener;
import com.nokia.carbide.updater.extension.IProjectUpdater;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;

public class ProjectUpdateManager implements IProjectUpdateManager, IStateListener {
	
	private static ProjectUpdateManager instance = new ProjectUpdateManager();
	private List<ProjectUpdateSession> sessions = new ArrayList<ProjectUpdateSession>();
	
	public static ProjectUpdateManager instance() {
		return instance;
	}

	public IProjectUpdateSession createSession() {
		ProjectUpdateSession session = new ProjectUpdateSession();
		synchronized(sessions) {
			sessions.add(session);
		}
		session.addStateListener(this);
		return session;
	}

	public IProjectUpdateSession[] getSessions() {
		IProjectUpdateSession[] result;
		synchronized(sessions) {
			result = sessions.toArray(new IProjectUpdateSession[sessions.size()]);
		}
		return result;
	}

	public boolean addExclusivelyToSessionForUpdate(IProject project, IProjectUpdateSession session) {
		boolean result = true;
		synchronized(sessions) {
			for (IProjectUpdateSession currSession : sessions) {
				IProject[] updatableProjects = currSession.getUpdatableProjects();
				if (ObjectUtils.findEqualObject(updatableProjects, project) >= 0) {
					result = false;
					break;
				}
			}
			if (result) {
				session.addUpdatableProject(project);
			}
		}
		return result;
	}
	
	public boolean addExclusivelyToSessionForRefactoring(IProject project, IProjectUpdateSession session) {
		boolean result = true;
		synchronized(sessions) {
			for (IProjectUpdateSession currSession : sessions) {
				IProject[] refactoringProjects = currSession.getRefactoringProjects();
				if (ObjectUtils.findEqualObject(refactoringProjects, project) >= 0) {
					result = false;
					break;
				}
			}
			if (result) {
				session.addRefactoringProject(project);
			}
		}
		return result;
	}
	
	public void addAllRefactoringProjectsToSession(IProjectUpdateSession session) {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		synchronized(sessions) {
			for (IProject project : projects) {
				addExclusivelyToSessionForRefactoring(project, session);
			}
		}
	}
	
	public boolean projectNeedsUpdate(IProject project, IProgressMonitor monitor) {
		boolean result = false;
		for (IProjectUpdater projectUpdater : Startup.getUpdaters()) {
			if (projectUpdater.needsUpdate(project, monitor)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public String getProjectUpdaterDocumentation(List<IProject> projects) {
		StringBuffer documentation = new StringBuffer();
		IProgressMonitor monitor = new NullProgressMonitor();
		
		for (IProjectUpdater projectUpdater : Startup.getUpdaters()) {
			for (IProject project : projects) {
				if (projectUpdater.needsUpdate(project, monitor)) {
					documentation.append("<h2>"); //$NON-NLS-1$
					documentation.append(projectUpdater.getUpdateLabel());
					documentation.append("</h2>"); //$NON-NLS-1$
					documentation.append(projectUpdater.getDocumentation());
					documentation.append("<p>"); //$NON-NLS-1$
					break;
				}
			}
		}
		return documentation.toString();
	}

	public void stateChanged(IProjectUpdateSession session) {
		if (session.getState() == IProjectUpdateSession.State.COMPLETE) {
			removeSession(session);
		}
		
	}

	private void removeSession(IProjectUpdateSession session) {
		synchronized(sessions) {
			sessions.remove(session);
		}
		session.removeStateListener(this);
	}
}
