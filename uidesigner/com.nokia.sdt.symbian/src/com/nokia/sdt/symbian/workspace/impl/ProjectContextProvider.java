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

package com.nokia.sdt.symbian.workspace.impl;

import com.nokia.sdt.symbian.SymbianPlugin;
import com.nokia.sdt.symbian.workspace.SymbianProjectUtils;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.workspace.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;

public class ProjectContextProvider implements IProjectContextProvider {
	
	private static final String PROJECT_CONTEXT_PROVIDER_ID = ".ProjectContextProvider";
	private static final String PROJECT_CONTEXT_PROVIDER_ID_2 = ".ProjectContextProvider2";
	private static QualifiedName PROJECT_BEING_CREATED_KEY = 
			new QualifiedName(SymbianPlugin.PI_NAME, PROJECT_CONTEXT_PROVIDER_ID);
	private static QualifiedName PROJECT_BEING_UPDATED_KEY = 
			new QualifiedName(SymbianPlugin.PI_NAME, PROJECT_CONTEXT_PROVIDER_ID_2);

	public ProjectContextProvider() {
		super();
	}
	
	public static void beginProjectCreation(IProject project) {
		try {
			project.setSessionProperty(PROJECT_BEING_CREATED_KEY, new Object());
		} catch (CoreException e) {
			Logging.log(SymbianPlugin.getDefault(), e.getStatus());
		}
	}
	
	public static void endProjectCreation(IProject project) {
		try {
			project.setSessionProperty(PROJECT_BEING_CREATED_KEY, null);
		} catch (CoreException e) {
			Logging.log(SymbianPlugin.getDefault(), e.getStatus());
		}
	}
	
	private static boolean isProjectBeingCreated(IProject project) {
		try {
			return project.getSessionProperty(PROJECT_BEING_CREATED_KEY) != null;
		} catch (CoreException e) {
			Logging.log(SymbianPlugin.getDefault(), e.getStatus());
		}
		
		return false;
	}
	
	public static void beginProjectUpdating(IProject project) {
		try {
			project.setSessionProperty(PROJECT_BEING_UPDATED_KEY, new Object());
		} catch (CoreException e) {
			Logging.log(SymbianPlugin.getDefault(), e.getStatus());
		}
	}
	
	public static void endProjectUpdating(IProject project) {
		try {
			project.setSessionProperty(PROJECT_BEING_UPDATED_KEY, null);
		} catch (CoreException e) {
			Logging.log(SymbianPlugin.getDefault(), e.getStatus());
		}
	}
	
	private static boolean isProjectBeingUpdated(IProject project) {
		try {
			return project.getSessionProperty(PROJECT_BEING_UPDATED_KEY) != null;
		} catch (CoreException e) {
			Logging.log(SymbianPlugin.getDefault(), e.getStatus());
		}
		
		return false;
	}
	
	public IProjectContext createContextForProject(
			WorkspaceContext workspaceContext, IProject project) {
		ProjectContext result = null;
		try {
			if (isProjectBeingCreated(project) || 
					SymbianProjectUtils.isWellConfiguredUIDesignerProject(project) ||
					(isProjectBeingUpdated(project) && 
							SymbianProjectUtils.hasUIDesignerRootDataModelFile(project))) {
				result = new ProjectContext(project);
			}
		} catch (CoreException x) {
			IStatus status = Logging.newSimpleStatus(SymbianPlugin.getDefault(),
					IStatus.ERROR, x.getMessage(), x);
			Logging.log(SymbianPlugin.getDefault(), status);
		}
		return result;
	}

}
