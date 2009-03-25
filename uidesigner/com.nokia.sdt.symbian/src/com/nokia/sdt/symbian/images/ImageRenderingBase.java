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
package com.nokia.sdt.symbian.images;

import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.images.IImageRendering;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.utils.ImageUtils;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.*;

public abstract class ImageRenderingBase implements IImageRendering {

	protected static final RGB WHITE = new RGB(255, 255, 255);
	protected boolean flatten;
	protected boolean blend;
	protected IComponentInstance instance;
	protected String propertyPath;
	protected boolean isScaling;
	protected Point alignmentWeights;
	protected boolean isPreservingAspectRatio;
	protected Point viewableSize;
	protected boolean anyImageRenderingParametersSupplied;
	protected ILookAndFeel laf;
	protected String renderingModel;

	public ImageRenderingBase() {
		super();
	}

	public void reset() {
		anyImageRenderingParametersSupplied = false;
		flatten = false;
		blend = false;
		instance = null;
		propertyPath = null;
		isScaling = false;
		isPreservingAspectRatio = true;
		alignmentWeights = new Point(ImageUtils.ALIGN_LEFT, ImageUtils.ALIGN_TOP);
		viewableSize = null;
		laf = null;
		renderingModel = MODEL_DEFAULT;
	}

	public void setAlignmentWeights(Point weights) {
		this.alignmentWeights = weights;
		anyImageRenderingParametersSupplied = true;
	}

	public void setPreservingAspectRatio(boolean isPreserving) {
		this.isPreservingAspectRatio = isPreserving;
		anyImageRenderingParametersSupplied = true;
	}

	public void setScaling(boolean isScaling) {
		this.isScaling = isScaling;
		anyImageRenderingParametersSupplied = true;
	}

	public void setViewableSize(Point size) {
		this.viewableSize = size;
		anyImageRenderingParametersSupplied = true;
	}

	public void setTransparencyHandling(int transparencySetting) {
		switch (transparencySetting) {
		case TRANSPARENCY_KEEP:
			flatten = false;
			blend = false;
			break;
		case TRANSPARENCY_FLATTEN:
			flatten = true;
			blend = false;
			break;
		case TRANSPARENCY_FLATTEN_AND_BLEND:
			flatten = true;
			blend = true;
			break;
		default:
			throw new IllegalArgumentException("invalid transparency setting: " + transparencySetting); //$NON-NLS-1$
		}
		anyImageRenderingParametersSupplied = true;
	}

	public void setRenderingModel(String model) {
		this.renderingModel = model;
		anyImageRenderingParametersSupplied = true;
	}


	/**
	 * Get the image data for the model, properly aligned and scaled within its
	 * viewable area
	 * @param imageProvider
	 * @param model
	 * @return image data or <code>null</code> 
	 * @throws CoreException
	 */
	protected ImageData doGetImageData(GC gc, IImageModel model) throws CoreException {
		if (model == null)
			return null;

		RGB background = gc != null ? gc.getBackground().getRGB() : WHITE;

		// get the real image (unscaled) first, so we know how to scale or
		// preserve aspect ratio
        Point imgSize;
        
        ImageData realImageData = model.getImageDescriptor(null).getImageData();
        if (realImageData == null)
        	return null;
        
        // now fit that original data to the viewable size
        if (viewableSize == null) {
        	viewableSize = new Point(realImageData.width, realImageData.height);
        }
        
        if (isPreservingAspectRatio) {
            imgSize = new Point(realImageData.width, realImageData.height);
            imgSize = ImageUtils.scaleSizeToSize(imgSize, viewableSize);
        } else {
            imgSize = viewableSize;
        }

        // correct for broken image sizes
        if (imgSize.x <= 0)
        	imgSize.x = 1;
        if (imgSize.y <= 0)
        	imgSize.y = 1;
        
        ImageData imageData;
        if (isScaling) {
        	// scale here so we get the best SVG possible (if necessary)
            imageData = model.getImageDescriptor(imgSize).getImageData();
        } else {
        	// adjust alignments for cropped data
            if ((realImageData.width > viewableSize.x || realImageData.height > viewableSize.y)
            		&& alignmentWeights.x == ImageUtils.ALIGN_CENTER_OR_LEFT) {
                alignmentWeights = new Point(ImageUtils.ALIGN_LEFT, alignmentWeights.y);
            }
            imageData = realImageData;
        }
        
        if (imageData == null)
            return null;
     
        // optionally blend down 
        if (flatten) {
            imageData = ImageUtils.flattenAlphaMaskedImageData( 
                    imageData, background, blend, true /* transparent */);
        }
        
		return imageData;
	}

    /**
     * Render an image to the gc as the OS would do
     * @param gc
     * @param bitmapInfo
     * @param maskInfo
     */
    public void renderImage(Device device, GC gc, int x, int y,
    		ImageData imageData) {
    	if (imageData == null)
    		return;
        Image image = new Image(device, imageData);
        ImageUtils.renderImage(gc, image, x, y,
            viewableSize,
            alignmentWeights,
            false, // image has already been scaled
            isPreservingAspectRatio);
        image.dispose();
    }

}