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

package com.nokia.carbide.cpp.ui.images;


import org.eclipse.core.runtime.IPath;

/**
 * This interface represents an image which exists somewhere on the host
 * system (usually the project, but always the filesystem) and 
 * which may exist somewhere on the target as well.
 *
 */
public interface IFileImageModel extends IImageModel {
	/** Get the absolute host filesystem path to the image. 
	 * @return IPath or null
	 */
	IPath getSourceLocation();
	
	/** Get the relative path (to the parent's container) to the image. 
	 * @return IPath or null if not in the project
	 * @see IImageContainerModel#getBaseLocation()
	 */
	IPath getSourcePath();

	/** 
	 * Get the target location for file that contains the image.
	 * It will be null if the file is not part of the build.
	 * @return IPath or null
	 */
	IPath getTargetPath();
}
