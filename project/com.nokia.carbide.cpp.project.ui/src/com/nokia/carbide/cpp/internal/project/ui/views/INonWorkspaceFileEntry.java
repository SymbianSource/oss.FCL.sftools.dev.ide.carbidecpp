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
package com.nokia.carbide.cpp.internal.project.ui.views;

import org.eclipse.core.runtime.IPath;

/**
 * Interface used to attach actions to non workspace files in the SPN view.
 *
 */
public interface INonWorkspaceFileEntry {
	/**
	 * Get the absolute path of the non-workspace file
	 * @return
	 */
	IPath getAbsolutePath();
}
