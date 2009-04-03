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
import com.nokia.carbide.cpp.epoc.engine.image.*;
import com.nokia.carbide.cpp.internal.project.ui.Messages;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.*;

/**
 * A model for an IImageSourceReference.  This may represent either a single
 * standalone image, a composed image, or the bitmap or mask from a MBM image.
 *
 */
public class ImageSourceReferenceModel extends SymbianMaskedFileImageModel implements IImageSourceReferenceModel {

	protected IImageSourceReference imageSourceReference;

	public ImageSourceReferenceModel(ISymbianImageContainerModel parent, 
			IPath projectLocation, 
			IImageSourceReference imageSourceReference,
			ImageFormat imageFormat) {
		super(parent, projectLocation, imageSourceReference.getPath(), 
				imageSourceReference instanceof IBitmapSourceReference ?
						((IBitmapSourceReference) imageSourceReference).getMaskPath() : null,
				imageFormat);
		Check.checkArg(imageSourceReference);
		Check.checkArg(imageFormat);
		this.imageSourceReference = imageSourceReference;
	}
	
	protected ImageSourceReferenceModel(ISymbianImageContainerModel parent, 
			IPath projectLocation, 
			IImageSourceReference imageSourceReference,
			ImageFormat imageFormat,
			ImageMakefileViewPathHelper pathHelper) {
		super(parent, projectLocation, 
				pathHelper.findCandidateImagePath(imageSourceReference.getPath()), 
				imageSourceReference instanceof IBitmapSource ?
						pathHelper.findCandidateMaskPath(((IBitmapSource) imageSourceReference)) : null,
				imageFormat);
		Check.checkArg(imageSourceReference);
		Check.checkArg(imageFormat);
		this.imageSourceReference = imageSourceReference;
	}
	
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.providers.IImageModel#getTargetPath()
	 */
	public IPath getTargetPath() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.IImageModel#validate()
	 */
	public IStatus validate() {
		if (!imageSourceReference.isValid()) {
			return Logging.newStatus(ProjectUIPlugin.getDefault(), 
					new IllegalArgumentException(Messages.getString("ImageSourceReferenceModel.InvalidImageReferenceError"))); //$NON-NLS-1$
		}
		return Status.OK_STATUS;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((imageSourceReference == null) ? 0 : imageSourceReference
						.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ImageSourceReferenceModel other = (ImageSourceReferenceModel) obj;
		if (imageSourceReference == null) {
			if (other.imageSourceReference != null)
				return false;
		} else if (!imageSourceReference.equals(other.imageSourceReference))
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.IImageSourceReferenceModel#getImageSourceReference()
	 */
	public IImageSourceReference getImageSourceReference() {
		return imageSourceReference;
	}
	
}
