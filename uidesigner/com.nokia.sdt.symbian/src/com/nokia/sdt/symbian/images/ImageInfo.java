/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.carbide.cpp.epoc.engine.image.*;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.core.runtime.IPath;

/**
 * Manage one image (i.e. a bitmap or a mask or an SVG file).
 * <p>
 * This either refers to an entry for an MBM/MIF file or an
 * entry derived from a URI.  The {@link #isURI()} call will tell the
 * difference, and {@link #getURI()} will provide that URI (the
 * pre-existing API will return derived/interpreted parts of that
 * URI). 
 * 
 *
 */
public class ImageInfo {
    
    /** The image, relative to the project */
    private String imageFile;
    /** The color depth */
    private int imageDepth;
    /** True: color, false: greyscale */
    private boolean isColor;
    private boolean isMask;
    private String imageBase;
    private boolean isSVG;
	private boolean isSoftMask;
	private String multiImageInfoBase;
    private int index;
    
    /**
     * Create image info from an epoc engine IImageSource
     */
	public ImageInfo(MultiImageInfo mii, IImageSource imageSource, boolean asMask, int index) {
		if (asMask)
			init(mii, imageSource, imageSource.isColor(), imageSource.getMaskDepth(), true, index);
		else
			init(mii, imageSource, imageSource.isColor(), imageSource.getDepth(), false, index);
    }

    /**
     * Create image info from an epoc engine IImageSource reference and color information
     */
	public ImageInfo(MultiImageInfo mii, IImageSourceReference imageSourceReference,
			boolean isColor, int depth,	boolean asMask, int index) {	
		init(mii, imageSourceReference, isColor, depth, asMask, index);
    }

	/**
	 * @param mii
	 * @param imageSourceReference
	 * @param isColor
	 * @param depth
	 * @param asMask
	 */
	private void init(MultiImageInfo mii, IImageSourceReference imageSourceReference, 
			boolean isColor, int depth, boolean asMask, int index) {
		IPath sourcePath;
		if (asMask) {
			isMask = true;
			if (imageSourceReference instanceof IBitmapSourceReference) {
				IBitmapSourceReference bitmapSourceReference = ((IBitmapSourceReference)imageSourceReference);
				sourcePath = bitmapSourceReference.getMaskPath();
				if (sourcePath == null)
					sourcePath = bitmapSourceReference.getDefaultMaskPath(depth);
			}
			else {
				sourcePath = ((ISVGSourceReference)imageSourceReference).getImpliedMaskPath();
				//sourcePath = imageSourceReference.getPath();
			}
			
		} else {
			sourcePath = imageSourceReference.getPath();
		}
		this.imageFile = sourcePath.toString();
		this.imageBase = TextUtils.titleCase(sourcePath.removeFileExtension().lastSegment().toLowerCase());
        this.multiImageInfoBase = mii != null ? mii.getBase() : null;

        if (asMask) {
	        isSoftMask = asMask && depth == 8;
	        int idx = imageBase.lastIndexOf("_mask_soft"); //$NON-NLS-1$
	        if (idx >= 0) {
	            // if mifconv, no enums end in "_soft"
	        	if (mii != null && mii.getFileType() == MultiImageInfo.MIF_FILE)
	        		imageBase = imageBase.substring(0, idx) + "_mask"; //$NON-NLS-1$
	        }
        }
        
        this.isColor = isColor;
        this.imageDepth = depth;
        if (imageSourceReference instanceof ISVGSourceReference) {
        	this.isSVG = true;
        }
        this.index = index;
	}

    /**
     * Get the base name for the image, in Titlecase format
     */
    public String getImageBase() {
        return imageBase;
    }

    /**
     * Get the enumeration for the image, or null
     */
    public String getImageEnumeration() {
    	if (multiImageInfoBase == null)
    		return null;
        String base = getImageBase();
        if (base == null)
            return null;
        String enumeration = "EMbm" + multiImageInfoBase + base; //$NON-NLS-1$
        return enumeration;
    }

    /**
     * Tell if the image is SVG
     */
    public boolean isSVG() {
        return isSVG;
    }

    /* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.images.IImageInfo#getFilePath()
	 */
    public String getFilePath() {
        return imageFile;
    }

    /**
     * Get the bit depth
     */
    public int getBitDepth() {
        return imageDepth;
    }

    /**
     * Get the "is-color" flag
     */
    public boolean isColor() {
        return isColor;
    }

	/**
	 * Tell whether this is a soft mask
	 */
	public boolean isSoftMask() {
    	return isSoftMask;
	}
	
	/**
	 * Get the index of the image in its source context.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @return
	 */
	public boolean isMask() {
		return isMask;
	}

}
