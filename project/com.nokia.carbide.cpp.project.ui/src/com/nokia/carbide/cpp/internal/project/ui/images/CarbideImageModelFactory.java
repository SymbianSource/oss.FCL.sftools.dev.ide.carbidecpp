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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cdt.builder.ImageMakefileViewPathHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.image.IImageSource;
import com.nokia.carbide.cpp.epoc.engine.image.IImageSourceReference;
import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.image.ImageFormat;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildInfo;
import com.nokia.carbide.cpp.internal.ui.images.CachingImageLoader;
import com.nokia.carbide.cpp.ui.images.IImageContainerEditorProvider;
import com.nokia.carbide.cpp.ui.images.IImageContainerModel;
import com.nokia.carbide.cpp.ui.images.IImageContainerModelListener;
import com.nokia.carbide.cpp.ui.images.IImageLoader;
import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.carbide.cpp.ui.images.ImageContainerModelBase;
import com.nokia.carbide.cpp.ui.images.ImageModelFactory;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;

/**
 * Factory for Carbide-specific image models and containers
 *
 */
public abstract class CarbideImageModelFactory extends ImageModelFactory {
	private static Map<String, IImageLoader> projectNameToImageLoaderMap =
		new HashMap<String, IImageLoader>();

	/**
	 * Get an image container for the images in the given multi-image source
	 * in a project
	 * @param modelFile
	 * @param multiImageSource
	 * @return new {@link ISymbianImageContainerModel}
	 */
	public static IMultiImageSourceImageContainerModel createMultiImageSourceModel(
			ICarbideProjectInfo projectInfo,
			IPath modelFile,
			IMultiImageSource multiImageSource) {
	
		IImageLoader imageLoader = getProjectImageLoader(projectInfo);
		IImageConverterFactory imageConverterFactory = getImageConverterFactory(projectInfo);
		boolean supportsSVG = doesProjectSupportSVG(projectInfo);
	        
		return createMultiImageSourceModel(
				getProjectLocation(projectInfo),
				imageLoader, imageConverterFactory,
				supportsSVG, modelFile, 
				multiImageSource);
	}

	private static IPath getProjectLocation(ICarbideProjectInfo projectInfo) {
		if (projectInfo == null)
			return new Path("."); //$NON-NLS-1$
		return ProjectUtils.getRealProjectLocation(projectInfo.getProject());
	}

	/**
	 * Get an image container for the images in the given multi-image source
	 * @param imageLoader 
	 * @param imageConverterFactory
	 * @param supportsSVG
	 * @param modelFile the full path to the model providing the multi-image source (or null)
	 * @param projectLocation the full path to the project
	 * @param multiImageSource
	 * @return new container model
	 */
	public static IMultiImageSourceImageContainerModel createMultiImageSourceModel(
			IPath projectLocation,
			IImageLoader imageLoader,
			IImageConverterFactory imageConverterFactory,
			boolean supportsSVG,
			IPath modelFile,
			IMultiImageSource multiImageSource) {
	
		return new MultiImageSourceImageContainerModel(imageLoader,
				imageConverterFactory,
				supportsSVG,
				modelFile,
				projectLocation,
				multiImageSource);
	}

	/**
	 * Get an image container for the images in the given AIF info
	 * in a project
	 * @param projectInfo
	 * @param modelFile
	 * @param aifInfo
	 * @return new {@link ISymbianImageContainerModel}
	 */
	public static IAIFImageContainerModel createAIFImageContainerModel(
			ICarbideProjectInfo projectInfo,
			IPath modelFile,
			IMMPAIFInfo aifInfo) {
		IImageLoader imageLoader = getProjectImageLoader(projectInfo);
		IImageConverterFactory imageConverterFactory = getImageConverterFactory(projectInfo);
	        
		return new AIFImageContainerModel(imageLoader, imageConverterFactory,
				getProjectLocation(projectInfo),
				modelFile, aifInfo);
	}

	/**
	 * Create a dummy image container model providing the given image
	 * loader and not creating any child models.
	 */
	public static ISymbianImageContainerModel createNullSymbianImageContainerModel(
			IPath baseLocation, IImageLoader imageLoader, final IImageConverterFactory converterFactory) {
		final ImageContainerModelBase base = new ImageContainerModelBase(baseLocation, imageLoader) {

			public IImageContainerEditorProvider createEditorProvider() {
				return null;
			}

			public IImageModel[] createImageModels() {
				return new IImageModel[0];
			}
			
		};
		return new ISymbianImageContainerModel() {

			/* (non-Javadoc)
			 * @see com.nokia.carbide.cpp.ui.images.IImageContainerModel#getBaseLocation()
			 */
			public IPath getBaseLocation() {
				return base.getBaseLocation();
			}
			public IImageConverterFactory getImageConverterFactory() {
				return converterFactory;
			}

			public void addListener(IImageContainerModelListener listener) {
				base.addListener(listener);
			}

			public IImageContainerEditorProvider createEditorProvider() {
				return base.createEditorProvider();
			}

			public IImageModel[] createImageModels() {
				return base.createImageModels();
			}

			public IImageLoader getImageLoader() {
				return base.getImageLoader();
			}

			public void removeListener(IImageContainerModelListener listener) {
				base.removeListener(listener);
			}
			
			/* (non-Javadoc)
			 * @see com.nokia.carbide.cpp.ui.images.IImageContainerModel#fireListeners()
			 */
			public void fireListeners() {
				base.fireListeners();
			}
		};
	}
	
	/**
	 * Create an image model for a file which has color and bitdepth attributes
	 * applied.
	 * @param imageContainerModel
	 * @param sourceLocation
	 * @param color
	 * @param depth
	 * @return
	 */
	public static IImageModel createSymbianFileImageModel(
			ISymbianImageContainerModel imageContainerModel, IPath sourceLocation,
			boolean color, int depth) {
		return createSymbianMaskedFileImageModel(imageContainerModel, sourceLocation, null, 
				new ImageFormat(color, depth));
	}
	
	/**
	 * Create a Symbian masked image file model
	 * @param container parent container model
	 * @param converterFactory image bitdepth converter factory
	 * @param baseLocation base location, never null
	 * @param imagePath image path relative to baseLocation, never null
	 * @param maskPath mask path relative to baseLocation, may be null
	 * @param format image format
	 */
	public static ISymbianMaskedFileImageModel createSymbianMaskedFileImageModel(
			IImageContainerModel container,
			IImageConverterFactory converterFactory,
			IPath imagePath, IPath maskPath,
			final ImageFormat format) {
		return new SymbianMaskedFileImageModel(container, converterFactory, 
				container.getBaseLocation(), imagePath, maskPath, format);
	}
	
	/**
	 * Create a Symbian masked image file model
	 * @param container parent container model
	 * @param baseLocation base location, never null
	 * @param imagePath image path relative to baseLocation, never null
	 * @param maskPath mask path relative to baseLocation, may be null
	 * @param format image format
	 */
	public static ISymbianMaskedFileImageModel createSymbianMaskedFileImageModel(
			ISymbianImageContainerModel container,
			IPath imagePath, IPath maskPath,
			final ImageFormat format) {
		return new SymbianMaskedFileImageModel(container, container.getBaseLocation(), imagePath, maskPath, format);
	}
	
	/**
	 * Create a image source reference model
	 * @param container the container of images, must not be null
	 * @param reference the source reference, must not be null
	 * @param imageFormat the color information for the image, must not be null
	 */
	public static IImageSourceReferenceModel createImageSourceReferenceModel(
			ISymbianImageContainerModel container,
			IImageSourceReference reference,
			ImageFormat imageFormat) {
		return new ImageSourceReferenceModel(container, container.getBaseLocation(), reference, imageFormat);
	}

	/**
	 * Create a image source reference model
	 * @param container the container of images, must not be null
	 * @param reference the source reference, must not be null
	 * @param imageFormat the color information for the image, must not be null
	 * @param pathHelper a path helper used to find images the same way mifconv does
	 */
	public static IImageSourceReferenceModel createImageSourceReferenceModel(
			ISymbianImageContainerModel container,
			IImageSourceReference reference,
			ImageFormat imageFormat,
			ImageMakefileViewPathHelper pathHelper) {
		return new ImageSourceReferenceModel(container, container.getBaseLocation(), reference, imageFormat, pathHelper);
	}

	/**
	 * Create a image source model
	 * @param container the container of images, must not be null
	 * @param source the source, must not be null
	 */
	public static IImageSourceModel createImageSourceModel(
			ISymbianImageContainerModel container,
			IImageSource source) {
		return new ImageSourceModel(container, container.getBaseLocation(), source);
	}
	
	/**
	 * Create a image source model for images from an image makefile
	 * @param container the container of images, must not be null
	 * @param source the source, must not be null
	 * @param pathHelper a path helper used to find images the same way mifconv does
	 */
	public static IImageSourceModel createImageSourceModel(
			ISymbianImageContainerModel container,
			IImageSource source,
			ImageMakefileViewPathHelper pathHelper) {
		return new ImageSourceModel(container, container.getBaseLocation(), source, pathHelper);
	}

	/**
	 * Tell if the project allows SVG in any build configuration
	 * @param projectInfo
	 * @return flag
	 */
	public static boolean doesProjectSupportSVG(ICarbideProjectInfo projectInfo) {
		// SVG supported after 2.8 on S60
		return true;
	}

	/**
	 * Get the image converter factory appropriate to the project
	 * @param projectInfo
	 * @return {@link IImageConverterFactory}, never null
	 */
	public static IImageConverterFactory getImageConverterFactory(
			ICarbideProjectInfo projectInfo) {
		
		return new SymbianImageConverterFactory();
	}

	/**
	 * Get a shared caching image loader for the project
	 * @param project
	 * @return IImageLoader, never null
	 */
	public static IImageLoader getProjectImageLoader(ICarbideProjectInfo projectInfo) {
		String projectName = ""; //$NON-NLS-1$
		if (projectInfo != null) {
			projectName = projectInfo.getProject().getName();
		}
		IImageLoader imageLoader = projectNameToImageLoaderMap.get(projectName);
		if (imageLoader == null) {
			imageLoader = new CachingImageLoader();
			projectNameToImageLoaderMap.put(projectName, imageLoader);
		}
		return imageLoader;
	}


}
