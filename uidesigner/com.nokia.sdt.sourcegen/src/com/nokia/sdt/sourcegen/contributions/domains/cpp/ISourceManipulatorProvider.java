/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen.contributions.domains.cpp;

import org.eclipse.core.runtime.IPath;

/**
 * 
 *
 */
public interface ISourceManipulatorProvider {
	/** Get a source manipulator for the given project-relative path */
	ISourceManipulator getSourceManipulator(IPath projectPath);

	/**
	 * Reset the provider to remove any cached information.
	 */
	void reset();
}
