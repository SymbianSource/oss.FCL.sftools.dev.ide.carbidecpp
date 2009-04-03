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
package com.nokia.carbide.cpp.internal.project.ui.images;

import com.nokia.carbide.cpp.ui.images.IImageModel;

import org.eclipse.core.runtime.CoreException;

/**
 * Model for a collection of images in AIF.
 *
 */
public interface IAIFImageContainerModel extends ISymbianImageContainerModel {

	/**
	 * Get the image model for the given indexed image.
	 * @param index the index for the pair of images (or image if no mask)
	 * @return null for missing or invalid image
	 */
	IImageModel getImageModel(int index) throws CoreException;
}
