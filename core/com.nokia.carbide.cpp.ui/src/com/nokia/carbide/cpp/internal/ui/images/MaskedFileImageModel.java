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

package com.nokia.carbide.cpp.internal.ui.images;

import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.images.*;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.sdt.utils.ImageUtils;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

import java.io.File;
import java.io.FileNotFoundException;

public class MaskedFileImageModel extends FileImageModel implements
		IMaskedFileImageModel {

	private IPath maskPath;
	private IPath maskSourceLocation;
	private IImageDataProvider imageProvider;
	private IImageDataProvider maskProvider;
	private ImageDescriptor composedImageDescriptor;
	private ImageData imageData;
	private ImageData maskData;
	private final MaskCompositionMethod method;

	/**
	 * @param containerModel
	 * @param basePath
	 * @param imagePath
	 * @param composeByTiling if true, compose a non-equal-sized image and mask
	 * by tiling the mask, else, scale the mask
	 */
	public MaskedFileImageModel(IImageContainerModel containerModel,
			IPath basePath, IPath imagePath, IPath maskPath,
			MaskCompositionMethod method) {
		super(containerModel, basePath, imagePath);
		this.maskPath = maskPath;
		this.method = method;
		if (maskPath != null) {
			if (maskPath.isAbsolute() || maskPath.getDevice() != null)
				maskSourceLocation = maskPath;
			else
				maskSourceLocation = basePath.append(maskPath);
		}
	}

	/**
	 * @param containerModel
	 * @param project
	 * @param imagePath
	 */
	public MaskedFileImageModel(IImageContainerModel containerModel,
			IProject project, IPath imagePath, IPath maskPath,
			MaskCompositionMethod method) {
		super(containerModel, project, imagePath);
		this.maskPath = maskPath;
		this.method = method;
		if (maskPath != null) {
			if (maskPath.isAbsolute() || maskPath.getDevice() != null)
				maskSourceLocation = maskPath;
			else
				maskSourceLocation = basePath.append(maskPath);
		}
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((maskSourceLocation == null) ? 0 : maskSourceLocation
						.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
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
		final MaskedFileImageModel other = (MaskedFileImageModel) obj;
		if (maskSourceLocation == null) {
			if (other.maskSourceLocation != null)
				return false;
		} else if (!maskSourceLocation.equals(other.maskSourceLocation))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.IMaskedFileImageModel#getMaskCompositionMethod()
	 */
	public MaskCompositionMethod getMaskCompositionMethod() {
		return method;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.FileImageModel#getDisplayText()
	 */
	@Override
	public String getDisplayText() {
		if (maskPath != null) {
			return super.getDisplayText() + " + " + maskPath.toPortableString(); //$NON-NLS-1$
		}
		return super.getDisplayText();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.IMaskedFileImageModel#getMaskProjectPath()
	 */
	public IPath getMaskProjectPath() {
		return maskPath;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.IMaskedFileImageModel#getMaskSourceLocation()
	 */
	public IPath getMaskSourceLocation() {
		return maskSourceLocation;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.FileImageModel#validate()
	 */
	@Override
	public IStatus validate() {
		IStatus status = super.validate();
		if (!status.isOK())
			return status;

		if (maskSourceLocation != null) {
			File file = maskSourceLocation.toFile();
			if (!file.exists()) {
				return Logging.newStatus(CarbideUIPlugin.getDefault(),
						new FileNotFoundException(file.getAbsolutePath()));
			}
		}
		return Status.OK_STATUS;
	}

	/**
	 * Load the image data, cached for the default size
	 * @param size null for the original size or another size
	 * @return original ImageData
	 * @throws CoreException
	 */
	protected ImageData getImageData(Point size) throws CoreException {
		if (imageProvider == null) {
			imageProvider = getImageContainerModel().getImageLoader().createImageDataProvider(getSourceLocation());
			if (imageProvider != null) {
				imageProvider.addListener(this);
			}
		}
		if (size == null) {
			if (imageData == null && imageProvider != null) {
				imageData = imageProvider.getImageData(null);
			}
			return imageData;
		} else {
			return imageProvider.getImageData(size);
		}
	}
	
	/**
	 * Load the mask data, cached for the default size
	 * @param size null for the original size or another size
	 * @return original ImageData
	 * @throws CoreException
	 */
	protected ImageData getMaskData(Point size) throws CoreException {
		if (maskSourceLocation == null)
			return null;
			
		if (maskProvider == null) {
			maskProvider = getImageContainerModel().getImageLoader().createImageDataProvider(maskSourceLocation);
			if (maskProvider != null) {
				maskProvider.addListener(this);
			}
		}
		if (size == null) {
			if (maskData == null) {
				maskData = maskProvider.getImageData(null);
			}
			return maskData;
		}
		return maskProvider.getImageData(size);

	}
	
	public ImageDescriptor getImageDescriptor(Point size) throws CoreException {
		if (size == null) {
			if (composedImageDescriptor != null) {
				return composedImageDescriptor;
			}
		}
		
		ImageDescriptor descriptor = null;
		ImageData combined;
		ImageData imageData;
		ImageData maskData;

		if (maskSourceLocation != null) {
			if (method == MaskCompositionMethod.TILING) {
				// Get image and mask in the original size, then scale the whole thing, else
				// the tiling won't work
				imageData = getImageData(null);
				maskData = getMaskData(null);
				
				combined = combineImageAndMask(imageData, maskData);
				
				// scale the combined image
				if (size != null && (size.x != combined.width || size.y != combined.height)) {
					combined = ImageUtils.scaleImageData(combined, size, false, false);
				}
			} else {
				// Scale the image and mask to the same size, then combine
				imageData = getImageData(size);
				maskData = getMaskData(size);
				Check.checkState(imageData != null);
				combined = combineImageAndMask(imageData, maskData);
			}
			
		} else {
			// no mask, just get image at desired size
			imageData = getImageData(size);
			combined = combineImageAndMask(imageData, null);
		}
		
		descriptor = ImageDescriptor.createFromImageData(combined);
		if (size == null) {
			composedImageDescriptor = descriptor;
		}
		
		return descriptor;
	}

	/**
	 * Combine the image and mask data.  The default implementation supports
	 * 1 and 8-bit masks.
	 * @param imageData
	 * @param maskData
	 * @return ImageData
	 * @throws CoreException 
	 */
	protected ImageData combineImageAndMask(ImageData imageData,
			ImageData maskData) throws CoreException {
		if (maskData == null)
			return imageData;
		if (imageData == null)
			return null;
		if (maskData.depth > 1)
			return ImageUtils.createCombinedImageData(imageData, maskData, true);
		else
			return ImageUtils.createMaskedImageData(imageData, maskData);
	}

}
