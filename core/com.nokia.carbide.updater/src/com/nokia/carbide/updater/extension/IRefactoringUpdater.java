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
import org.eclipse.ltk.core.refactoring.Refactoring;

/**
 * Interface implemented by extensions to the com.nokia.carbide.updater.refactoringUpdater
 * extension point.
 *
 */
public interface IRefactoringUpdater {

	/**
	 * Query if a project has upadtes.
	 * 
	 * @param project the project to be updated
	 * @param monitor a progress monitor
	 * @return boolean
	 */
	boolean hasRefactorings(IProject project, IProgressMonitor monitor);

	/**
	 * Get the refactoring information for a specific project.
	 * @param project the project to be updated
	 * @param monitor a progress monitor
	 * @return Refactoring or null
	 */
	Refactoring getRefactoring(IProject project, IProgressMonitor monitor);
	
	/**
	 * Notification that the updater is finished with refactoring updates
	 * for this project, whether they have been applied or not. Any
	 * cached resources may be freed at this point. The implementation
	 * should not assume hasRefactorings or getRefactorings was called for
	 * the project.
	 * @param project the project to be updated
	 */
	void refactoringComplete(IProject project);
	
}
