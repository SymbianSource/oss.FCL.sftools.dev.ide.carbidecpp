/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

/**
 * Interface implemented by extensions to the com.nokia.carbide.updater.projectUpdater
 * extension point.
 *
 */
public interface IProjectUpdater {
	
	/**
	 * Check the project for updating, return true if needs updating.
	 * @param project the project to be updated
	 * @param monitor a progress monitor
	 * @return boolean
	 */
	boolean needsUpdate(IProject project, IProgressMonitor monitor);
	
	/**
	 * Update a specific project.
	 * @param project the project to be updated
	 * @param monitor a progress monitor
	 * @return IStatus status of update
	 */
	IStatus update(IProject project, IProgressMonitor monitor);

	/**
	 * Return the label for the update
	 * This is appended to the project name if more than one updater is present.
	 * E.g., "Convert from Carbide 1.1 to 1.2"
	 * @return String
	 */
	String getUpdateLabel();
	
	/**
	 * Return the documentation for the update in html in a &lt;DIV&gt; tag
	 * to be inserted into a main html document.
	 * No &lt;HTML&gt;, &lt;HEAD&gt; or &lt;BODY&gt; tags should be included. 
	 * This goes in the documentation pane of the update dialog.
	 * @return String
	 */
	String getDocumentation();
	
}
