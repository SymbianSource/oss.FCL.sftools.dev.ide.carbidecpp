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

import com.nokia.carbide.cpp.internal.ui.images.*;
import com.nokia.carbide.cpp.ui.images.IMaskedFileImageModel.MaskCompositionMethod;

import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.graphics.Point;

import java.io.File;
import java.io.FilenameFilter;

/**
 * This class provides instances of image model interfaces.
 *
 */
public abstract class ImageModelFactory {

	/**
	 * Create an image loader, either caching or not.
	 * <p>
	 * Note: image models cache their image providers already.  The caching
	 * support caches multiple image sizes for all image models.  
	 * @param caching true: cache image providers and image data
	 * @return new ImageLoader
	 */
	public static IImageLoader createImageLoader(boolean caching) {
		if (caching)
			return new CachingImageLoader();
		else
			return new ImageLoader();
	}
	
	/**
	 * Create a dummy image container model providing the given image
	 * loader and not creating any child models.
	 * @param baseLocation 
	 */
	public static IImageContainerModel createNullImageContainerModel(
			IPath baseLocation, IImageLoader imageLoader) {
		return new ImageContainerModelBase(baseLocation, imageLoader) {

			public IImageContainerEditorProvider createEditorProvider() {
				return null;
			}

			public IImageModel[] createImageModels() {
				return new IImageModel[0];
			}
			
		};
	}
	
	/**
	 * Create an image container representing files in a directory.
	 * @param imageLoader the image loader, may not be null
	 * @param baseDir base directory to search, may not be null
	 * @param fileNameFilter if not null, a way to filter the files returned
	 * @param recursive true: recursive search
	 * @return new image container model
	 */
	public static IImageContainerModel createFileImageContainerModel(IImageLoader imageLoader, File baseDir,
			FilenameFilter fileNameFilter, boolean recursive) {
		return new FileImageContainerModel(baseDir, imageLoader, fileNameFilter, recursive);
	}
	
	/**
	 * Create a standalone image model.
	 * @param containerModel container, may not be null
	 * @param imageRelativePath path of image relative to baseDir, may not be null
	 */
	public static IFileImageModel createFileImageModel(
			IImageContainerModel containerModel,
			IPath imageRelativePath) {
		return new FileImageModel(containerModel, containerModel.getBaseLocation(), imageRelativePath);
	}

	/**
	 * Create a standalone masked image model.
	 * @param containerModel container, may not be null
	 * @param imageRelativePath path of image relative to baseDir, may not be null
	 * @param maskRelativePath path of mask relative to baseDir, may be null
	 * @param compositionMethod how to fit the mask to the image
	 */
	public static IMaskedFileImageModel createMaskedFileImageModel(
			IImageContainerModel containerModel,
			IPath imageRelativePath, IPath maskRelativePath, MaskCompositionMethod compositionMethod) {
		return new MaskedFileImageModel(containerModel, containerModel.getBaseLocation(), imageRelativePath, maskRelativePath,
				compositionMethod);
	}
	

	/**
	 * Create a null image model, which returns an ImageDescriptor returning <code>null</code> data
	 * @return 
	 */
	public static IImageModel createNullImageModel() {
		return new NullImageModel();
	}

	/**
	 * Wrap an image model in a variant that enforces a certain size.
	 * @param model existing model
	 * @param size fixed size
	 * @return IImageModel or <code>null</code> if original is null
	 */
	public static IImageModel createFixedSizeImageModelWrapper(IImageModel model, Point size) {
		if (model == null)
			return null;
		return new FixedSizeImageModelWrapper(model, size);
	}
}
