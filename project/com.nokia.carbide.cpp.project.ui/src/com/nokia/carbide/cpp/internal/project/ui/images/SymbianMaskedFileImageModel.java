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
import com.nokia.carbide.cpp.internal.project.ui.Messages;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.ui.images.MaskedFileImageModel;
import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.images.IImageContainerModel;
import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.sdt.utils.ImageUtils;

import org.eclipse.core.runtime.*;
import org.eclipse.swt.graphics.*;

import java.text.MessageFormat;

/**
 * Base for Symbian image handlers
 *
 */
public class SymbianMaskedFileImageModel extends MaskedFileImageModel implements ISymbianMaskedFileImageModel {

	private final ImageFormat format;
	private final IImageConverterFactory converterFactory;
	private boolean isSVG;

	public SymbianMaskedFileImageModel(ISymbianImageContainerModel container,
			IPath projectLocation, IPath imagePath, IPath maskPath,
			ImageFormat format) {
		this(container, container.getImageConverterFactory(),
				projectLocation, imagePath, maskPath, format);
		
	}
	public SymbianMaskedFileImageModel(IImageContainerModel container,
			IImageConverterFactory converterFactory,
			IPath projectLocation, IPath imagePath, IPath maskPath,
			ImageFormat format) {
		super(container, projectLocation, imagePath, maskPath, MaskCompositionMethod.TILING);
		Check.checkArg(container);
		Check.checkArg(format);
		this.converterFactory = converterFactory;
		this.format = format;
		this.isSVG = imagePath != null && isSVGFile(imagePath.lastSegment());
		
	}
	
    private boolean isSVGFile(String file) {
        String lower = file.toLowerCase();
        return lower.endsWith(".svg") || lower.endsWith(".svgt"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /* (non-Javadoc)
     * @see com.nokia.carbide.cpp.internal.project.ui.images.ISymbianMaskedFileImageModel#isSVG()
     */
    public boolean isSVG() {
    	return isSVG;
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((format == null) ? 0 : format.hashCode());
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
		final SymbianMaskedFileImageModel other = (SymbianMaskedFileImageModel) obj;
		if (format == null) {
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.ISymbianMaskedFileImageModel#getImageFormat()
	 */
	public ImageFormat getImageFormat() {
		return format;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.internal.images.MaskedFileImageModel#getMaskData(org.eclipse.swt.graphics.Point)
	 */
	@Override
	protected ImageData getMaskData(Point size) throws CoreException {
		if (isSVG)
			return null;
		return super.getMaskData(size);
	}
	
	/**
     * Load the image and convert it using the given format.
     * @param path the full path to the image file
     * @param depth the color depth
     * @param isColor true: color, false: grey
     * @param isMask true: get image for mask (i.e. no palette), false: get image for bitmap
     * @return cached Image, owned by this
     */
    private ImageData convertImageData(ImageData rawImageData,
    		int depth, boolean isColor, boolean isMask) throws CoreException {
    	if (depth == 0)
    		return isMask ? null : rawImageData;
    	
    	if (isSVG) {
    		// bit depth ignored
    		if (!isMask)
    			depth = 32;
    		else if (depth != 0)
    			depth = 8;
    	}
    	
    	ImageData imageData = null;
    	
        if (converterFactory != null) {
        	imageData = ImageUtils.convertToDepth(rawImageData, depth, 
        			converterFactory.getConverter(depth, isColor, isMask),
        			converterFactory.getPalette(depth, isColor));
        	if (!isColor) {
        		imageData = ImageUtils.convertToGreyscale(imageData);
            }
        }
        else
        	imageData = rawImageData;
        return imageData;
    }

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.internal.images.MaskedFileImageModel#combineImageAndMask(org.eclipse.swt.graphics.ImageData, org.eclipse.swt.graphics.ImageData)
	 */
	@Override
	protected ImageData combineImageAndMask(ImageData imageData,
			ImageData maskData) throws CoreException {
		ImageData combined;
		
		imageData = convertImageData(imageData, format.depth, format.isColor, false);
		
		if (maskData != null) {
			maskData = convertImageData(maskData, format.maskDepth, format.maskDepth > 1, true);
			
			// Symbian is too smart
			if (format.depth == 1 && format.maskDepth > 1) {
				ImageData tmp = imageData;
				imageData = maskData;
				maskData = tmp;
			}
	
			// if tiling, note that the underlying routines perform the tiling
			if (format.maskDepth == 8)
				combined = ImageUtils.createCombinedImageData(imageData, maskData, true);
			else
				combined = ImageUtils.createMaskedImageData(imageData, maskData);
		} else {
			if (format.maskDepth == 0) {
				// flatten to white
				combined = ImageUtils.flattenAlphaMaskedImageData(imageData, 
						new RGB(255, 255, 255), true, false);
		 	} else {
		 		// just pass through
		 		combined = imageData;
		 	}
		}
		
		return combined;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.IImageSourceReferenceModel#getBitmapImageModel(com.nokia.carbide.cpp.epoc.engine.image.ImageFormat)
	 */
	public IImageModel getBitmapImageModel(boolean preserveImageFormat) {
		if (!preserveImageFormat)
			return CarbideImageModelFactory.createFileImageModel(
					getImageContainerModel(), getSourcePath());
		else
			return CarbideImageModelFactory.createSymbianFileImageModel(
				(ISymbianImageContainerModel) getImageContainerModel(), getSourcePath(), 
				format.isColor(), format.getDepth());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.IImageSourceReferenceModel#getMaskImageModel(com.nokia.carbide.cpp.epoc.engine.image.ImageFormat)
	 */
	public IImageModel getMaskImageModel(boolean preserveImageFormat) {
		if (getMaskSourceLocation() == null)
			return null;
		if (isSVG) {
			return CarbideImageModelFactory.createNullImageModel();
		}
		if (!preserveImageFormat)
			return CarbideImageModelFactory.createFileImageModel(
					getImageContainerModel(), getMaskProjectPath());
		else
			return CarbideImageModelFactory.createSymbianFileImageModel(
				(ISymbianImageContainerModel) getImageContainerModel(), getMaskProjectPath(), 
				false, format.getMaskDepth());
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.internal.images.MaskedFileImageModel#validate()
	 */
	@Override
	public IStatus validate() {
		IStatus status = super.validate();
		if (status.isOK()) {
			// check for a mask without an image
	        if (getMaskSourceLocation() != null && getSourceLocation() == null) {
	            return Logging.newStatus(ProjectUIPlugin.getDefault(),
	            		IStatus.ERROR,
	            		Messages.getString("SymbianMaskedFileImageModel.MaskAndNoImageInvisibleError"));  //$NON-NLS-1$
	        }
	        
	        // check the image
	        if (getSourceLocation() != null) {
	            status = validateImagePath(getSourceLocation().lastSegment());
	            if (status != null)
	                return status;
	        }
	        
	        // check the mask
	        if (getMaskSourceLocation() != null) {
	        	status = validateImagePath(getMaskSourceLocation().lastSegment());
	            if (status != null)
	                return status;
	        }

	        // indicate ignoring bitdepth/mask for SVG
	        if (isSVG) {
	        	if (format.getDepth() != 32 || 
	        			(format.getMaskDepth() != 0 && format.getMaskDepth() != 8)) {
	        		   return Logging.newStatus(ProjectUIPlugin.getDefault(),
	   	            		IStatus.WARNING,
	   	            		Messages.getString("SymbianMaskedFileImageModel.SVGColorDepthForcedMessage"));  //$NON-NLS-1$
	        	}
	        }
		}
		return Status.OK_STATUS;
	}
	
	/**
	 * @param filePath
	 * @return
	 */
	private IStatus validateImagePath(String filePath) {
		// check that the filename leads to a legal generated enum
        String base;
        int idx = filePath.lastIndexOf('/');
        if (idx >= 0)
            base = filePath.substring(idx+1);
        else {
            idx = filePath.lastIndexOf('\\');
            if (idx >= 0)
                base = filePath.substring(idx+1);
            else
                base = filePath;
        }
        idx = base.lastIndexOf('.');
        if (idx >= 0)
            base = base.substring(0, idx);
        
        if (!base.matches("^[A-Za-z0-9_]+$")) { //$NON-NLS-1$
            return createFormattedStatus(IStatus.ERROR, 
            		Messages.getString("SymbianMaskedFileImageModel.GeneratedEnumeratorIllegalError"), //$NON-NLS-1$
            		new Object[] { filePath });
        }
        
        return null;
	}

	/**
	 * @param warning
	 * @param string
	 * @param objects
	 * @return
	 */
	private IStatus createFormattedStatus(int severity, String string,
			Object[] objects) {
		return new Status(severity,
				CarbideUIPlugin.PLUGIN_ID,
				MessageFormat.format(string, objects));
	}	
}
