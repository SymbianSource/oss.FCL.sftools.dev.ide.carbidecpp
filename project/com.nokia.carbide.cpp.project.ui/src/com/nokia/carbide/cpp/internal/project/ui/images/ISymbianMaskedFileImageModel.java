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

import com.nokia.carbide.cpp.epoc.engine.image.ImageFormat;
import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.carbide.cpp.ui.images.IMaskedFileImageModel;

/**
 * Model for Symbian masked images, which have a color and mask depth. 
 *
 */
public interface ISymbianMaskedFileImageModel extends IMaskedFileImageModel {

	/**
	 * Get the image format describing the converted image
	 * @return ImageFormat, never null
	 */
	ImageFormat getImageFormat();

	/**
	 * Tell if this is an SVG image.
	 */
	boolean isSVG();
	
	/**
	 * Get the image model for the bitmap portion of the image
	 * @param preserveImageFormat if true, apply the relevant image format
	 * to the new image model, else if false, return a plain IFileImageModel
	 * @return IImageModel
	 */
	IImageModel getBitmapImageModel(boolean preserveImageFormat);

	/**
	 * Get the image model for the mask portion of the image.
	 * @param preserveImageFormat if true, apply the relevant image format
	 * to the new image model, else if false, return a plain IFileImageModel
	 * @return IImageModel, may be null
	 */
	IImageModel getMaskImageModel(boolean preserveImageFormat);
}
