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

import org.eclipse.core.resources.IProject;

/**
 * IProjectContextProvider is implemention via extension point
 * implementors.
 * The main purpose is to determine which open projects should
 * have an IProjectContext implementation and to create instantiate
 * one as needed. 
 * The approach used is that the WorkspaceContext listens for project
 * open/close events and polls the providers to find one that can
 * provide the IProjectContext.
 * An alternative approach would be to have each implementor
 * listen for these events and proactively register and remove
 * IProjectContext objects. But the approach used is simpler for
 * the implementors and uses only a single listener.
 *
 */
public interface IProjectContextProvider {

	/**
	 * Creates an IProjectContext implementation appropriate to
	 * the IProject. The WorkspaceContext will poll all providers
	 * to attempt to get a project context. Implementations need
	 * to accept an project as a parameter, but just return null
	 * for project types they don't understand
	 */
	IProjectContext	createContextForProject(WorkspaceContext workspaceContext, IProject project);
}
