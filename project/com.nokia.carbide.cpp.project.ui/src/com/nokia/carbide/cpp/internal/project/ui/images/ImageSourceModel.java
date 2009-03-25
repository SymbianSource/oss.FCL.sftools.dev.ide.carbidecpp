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

import com.nokia.carbide.cdt.builder.ImageMakefileViewPathHelper;
import com.nokia.carbide.cpp.epoc.engine.image.IImageSource;
import com.nokia.carbide.cpp.internal.project.ui.Messages;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.*;

/**
 * A model for an IImageSource.  This may represent either a single
 * standalone image, a composed image, or the bitmap or mask from a MBM image.
 *
 */
public class ImageSourceModel extends ImageSourceReferenceModel implements IImageSourceModel {

	public ImageSourceModel(ISymbianImageContainerModel parent, 
			IPath projectLocation, 
			IImageSource imageSource) {
		super(parent, projectLocation, imageSource,
				imageSource.getImageFormat());
	}
	
	public ImageSourceModel(ISymbianImageContainerModel parent, 
			IPath projectLocation, 
			IImageSource imageSource,
			ImageMakefileViewPathHelper pathHelper) {
		super(parent, projectLocation, imageSource,
				imageSource.getImageFormat(),
				pathHelper);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.IImageSourceModel#getImageSource()
	 */
	public IImageSource getImageSource() {
		return (IImageSource) imageSourceReference;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.IImageModel#validate()
	 */
	public IStatus validate() {
		if (!imageSourceReference.isValid()) {
			return Logging.newStatus(ProjectUIPlugin.getDefault(), 
					new IllegalArgumentException(
							Messages.getString("ImageSourceModel.ImageSourceValidationError"))); //$NON-NLS-1$
		}
		return Status.OK_STATUS;
	}

}
