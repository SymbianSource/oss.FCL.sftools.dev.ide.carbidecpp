/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.updater.extension;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import java.util.Collection;
import java.util.List;

/**
 * Interface implemented by extensions to the com.nokia.carbide.updater.updateProjectsScanner
 * extension point.
 *
 */
public interface IUpdateProjectsScanner {
	
	/**
	 * Scan the projects for those that are ineligible for updating
	 * 
	 * @param projectsToScan projects to scan
	 * @param monitor a progress monitor
	 * @return Collection<IProject> projects that are inelegible for updating
	 */
	Collection<IProject> scanProjects(List<IProject> projectsToScan, IProgressMonitor monitor);
	
	/**
	 * Assumes projects have already been scanned.
	 * Returns IStatus associated with any project.
	 * If project not in list from scan, returns OK status
	 * else, returns ERROR status with message describing inelegibility of project.
	 * 
	 * @param project
	 * @return IStatus status of project
	 */
	IStatus getProjectStatus(IProject project);

}
