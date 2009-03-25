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
package com.nokia.carbide.cpp.internal.api.project.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import java.util.List;

/**
 * Determine if update to bld.inf/mmp should occur.
 *
 */
public interface IUpdateProjectFilesQuery {

	/**
	 * Given the list changes, determine if the bld.inf/mmp files should be
	 * updated to reflect the changes (delete/added/move/rename).
	 * @param project the owning project
	 * @param file the bld.inf/mmp file that would be changed
	 * @param changeDetails the list of descriptions of what would change
	 * @param isAdd true if the changes are additions, false if they are delete/move/rename
	 * @return true if the update should occur, false otherwise
	 */
	boolean shouldUpdate(final IProject project, final IFile file, final List<String> changeDetails, final boolean isAdd);
}
