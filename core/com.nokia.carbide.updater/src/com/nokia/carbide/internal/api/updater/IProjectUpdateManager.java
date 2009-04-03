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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * IProjectUpdateManager tracks a set of updater sessions. All update work is 
 * encapsulated within a session.
 */
public interface IProjectUpdateManager {

	IProjectUpdateSession createSession();
		
	IProjectUpdateSession[] getSessions();
	
	/**
	 * Adds a project only if it's not already present in any existant session.
	 * This ensures that if multiple sessions exist at a time a project will
	 * not appear in more than one session.
	 * @param project the project to add
	 * @param session the desired session
	 * @return true if the project was added to the desired session
	 */
	boolean addExclusivelyToSessionForUpdate(IProject project, IProjectUpdateSession session);
	
	/**
	 * Adds a project only if it's not already present in any existant session.
	 * This ensures that if multiple sessions exist at a time a project will
	 * not appear in more than one session.
	 * @param project the project to add
	 * @param session the desired session
	 * @return true if the project was added to the desired session
	 */
	boolean addExclusivelyToSessionForRefactoring(IProject project, IProjectUpdateSession session);

	/**
	 * Query all installed project updater extensions to see if the
	 * project requires any updates
	 * @param project
	 * @param monitor
	 * @return true if updating needed
	 */
	boolean projectNeedsUpdate(IProject project, IProgressMonitor monitor);
}
