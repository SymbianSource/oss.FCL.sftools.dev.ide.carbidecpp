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

package com.nokia.sdt.symbian.images;

import com.nokia.carbide.cpp.epoc.engine.image.ImageFormat;
import com.nokia.carbide.cpp.internal.project.ui.images.*;
import com.nokia.carbide.cpp.ui.images.IImageModel;

import org.eclipse.core.runtime.Path;

/**
 * Factories for Symbian plugin-specific image models and containers
 *
 */
public abstract class SymbianImageModelFactory extends CarbideImageModelFactory {

	/**
	 * Create an IImageModel from image property information.
	 * @param imageContainerModel
	 * @param imagePropertyInfo
	 * @return {@link IImageModel} or null for missing or unknown image
	 */
	public static IImageModel createImagePropertyInfoImageModel(
			ImagePropertyInfo imagePropertyInfo) {
		IImageModel model = null;
		ImageFormat format = imagePropertyInfo.getImageFormat();
		
		if (imagePropertyInfo.uriInfo != null) {
			model = imagePropertyInfo.uriInfo.getImageModel();
		} else if (imagePropertyInfo.multiImageInfo != null) {
			model = CarbideImageModelFactory.createSymbianMaskedFileImageModel(
					imagePropertyInfo.multiImageInfo.getImageContainerModel(),
				imagePropertyInfo.bitmapInfo != null ?
						new Path(imagePropertyInfo.bitmapInfo.getFilePath()) :
							null,
				imagePropertyInfo.maskInfo != null ?
						new Path(imagePropertyInfo.maskInfo.getFilePath()) :
							null,
				format);
		}
		return model;
	}

	/**
	 * Create a model based on ImageInfos 
	 * @param imageContainerModel
	 * @param imageInfo
	 * @param maskInfo
	 * @return
	 */
	public static ISymbianMaskedFileImageModel createSymbianMaskedImageFileModel(
			MultiImageInfo multiImageInfo,
			ImageInfo imageInfo, ImageInfo maskInfo) {
		IMultiImageSourceImageContainerModel imageContainerModel = multiImageInfo.getImageContainerModel();
		return new ImageInfoImageModel(imageContainerModel, imageContainerModel.getBaseLocation(),
				multiImageInfo, imageInfo, maskInfo);
	}

}
