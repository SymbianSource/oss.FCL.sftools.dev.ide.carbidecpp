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
 * This is a model for a build file that packages images on the host.
 *
 */
public interface IImageContainerModel {
	/**
	 * Get the base location against which the container's models are relative.
	 * @return full filesystem path, never null
	 */
	IPath getBaseLocation();
	
	/**
	 * Get the full filesystem path to the build file (e.g. MMP, makefile, bld.inf, .pkg file, ...)
	 * @return IPath or null
	 */
	//IPath getModelFilePath();

	/** 
	 * Get the target location for the image file container. 
	 * @return IPath, never null
	 */
	//IPath getTargetPath();

	/**
	 * Create image models for the represented images.
	 * @return array, never null
	 */
	IImageModel[] createImageModels();

	/**
	 * Get the image loader for these images.
	 * @return IImageLoader, never null
	 */
	IImageLoader getImageLoader();
	
	/**
	 * Create a provider that will allow editing the image container.
	 * @return IImageContainerEditorProvider or <code>null</code> if the container
	 * has no editing support
	 */
	IImageContainerEditorProvider createEditorProvider();

	/**
	 * Add a listener to the container changing.
	 */
	void addListener(IImageContainerModelListener listener);

	/**
	 * Remove a listener to the container changing.
	 */
	void removeListener(IImageContainerModelListener listener);

	/**
	 * Fire listeners when change detected.
	 */
	void fireListeners();

}
