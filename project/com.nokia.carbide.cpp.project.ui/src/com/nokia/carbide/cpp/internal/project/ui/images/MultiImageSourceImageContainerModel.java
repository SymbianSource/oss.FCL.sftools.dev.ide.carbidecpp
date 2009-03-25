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

import com.nokia.carbide.cpp.epoc.engine.image.*;
import com.nokia.carbide.cpp.ui.images.IImageLoader;
import com.nokia.carbide.cpp.ui.images.IImageModel;

import org.eclipse.core.runtime.IPath;

import java.util.ArrayList;
import java.util.List;

public class MultiImageSourceImageContainerModel extends ModelFileBasedImageContainerModelBase implements
		IMultiImageSourceImageContainerModel {

	private final IMultiImageSource multiImageSource;
	private final IPath projectPath;
	private IImageConverterFactory imageConverterFactory;
	private final boolean supportSVG;

	/**
	 * Create model
	 * @param modelFile full path to model file
	 * @param projectPath project's full path
	 */
	public MultiImageSourceImageContainerModel(
			IImageLoader imageLoader,
			IImageConverterFactory imageConverterFactory,
			boolean supportSVG,
			IPath modelFile,
			IPath projectPath,
			IMultiImageSource multiImageSource) {
		super(imageLoader, projectPath, modelFile);
		this.imageConverterFactory = imageConverterFactory;
		this.projectPath = projectPath;
		this.multiImageSource = multiImageSource;
		this.supportSVG = supportSVG;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.IImageContainerModel#getTargetPath()
	 */
	public IPath getTargetPath() {
		return multiImageSource.getTargetFilePath();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.IImageContainerModel#createImageModels()
	 */
	public IImageModel[] createImageModels() {
		List<IImageModel> models = new ArrayList<IImageModel>();
		for (IImageSource imageSource : multiImageSource.getSources()) {
			if (!supportSVG && imageSource instanceof ISVGSourceReference)
				continue;
			models.add(new ImageSourceModel(
					this, projectPath, imageSource));
		}
		return (IImageModel[]) models.toArray(new IImageModel[models.size()]);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.IImageContainerModel#getImageConverterFactory()
	 */
	public IImageConverterFactory getImageConverterFactory() {
		return imageConverterFactory;
	}

}
