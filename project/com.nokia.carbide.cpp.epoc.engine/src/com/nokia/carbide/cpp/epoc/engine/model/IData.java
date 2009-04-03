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

package com.nokia.carbide.cpp.epoc.engine.model;

import org.eclipse.core.runtime.IPath;

/**
 * This interface is the base for read-only data provided for an IView for use in caching.
 *
 */
public interface IData<T extends IView> {
	/** get the owning model's absolute path */
	IPath getModelPath();  

	/**
	 * get the owning model's absolute project path 
	 */
	IPath getProjectPath();


	/**
	 * Get the full path to all files (e.g. #includes) read for the view (including model file itself)
	 * @return array of absolute paths, never null
	 */
	IPath[] getReferencedFiles();


}
