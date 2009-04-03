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
import com.nokia.cpp.internal.api.utils.core.IDisposable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.*;

/**
 * Interface providing details about a particular SDK's support
 * for images, to be used for validating image selection in
 * ImageEditorDialog.
 * 
 */
public interface IImageProvider extends IDisposable {
    /**
     * Get a cached image.  The caller does not own this
     * image and must copy it for any long-term use. 
     * @param device 
     * @param info
     * @param isMaskFile render as mask?  
     * @param size the size to scale to, or null
     * @return cached Image, owned by this
     */
    //public Image getImage(IPath projectPath, ImageInfo info, boolean isMaskFile, Point size);

    /**
     * Get a cached composed image from the current image and mask.
     * The caller does not own this image and must copy it 
     * for any long-term use. 
     * @param rootPath
     * @param imageInfo bitmap or null
     * @param maskInfo mask or null
     * @param size target size, or null for original size
     * @return cached image
     */
    //public Image getComposedImage(IPath rootPath, ImageInfo imageInfo, ImageInfo maskInfo, Point size);
    
    //public Image getImage(URIImageInfo info, Point size);

    /**
     * Validate the selection of image and mask against the
     * toolchain's support for the file, filename, filetype, etc. 
     * @param object the instance holding the property
     * @param propertyPath the specific image property affected
     * @param info the multi image info 
     * @param imageInfo the current image
     * @param maskInfo the current mask, or null
     * @return IStatus, either null or warning or error
     */
    //public IStatus validate(EObject object, String propertyPath, MultiImageInfo info, ImageInfo imageInfo, ImageInfo maskInfo);
    
    /**
     * Validate the selection of image and mask against the
     * toolchain's support for the file, filename, filetype, etc. 
     * @param object the instance holding the property
     * @param propertyPath the specific image property affected
     * @param info the URI info 
     * @return IStatus, either null or warning or error
     */
    //public IStatus validate(EObject object, String propertyPath, URIImageInfo info);
 
    /**
     * Render an image to the gc as the OS would do
     */
    /*public void renderImage(Device device, GC gc, int x, int y, 
            IPath rootPath, ImageInfo bitmapInfo, ImageInfo maskInfo, 
            Point viewableSize, Point alignmentWeights, 
            boolean isScaling, boolean isPreservingAspectRatio,
            boolean flatten, boolean doBlend);
*/
    /**
     * Render a monochrome image to the gc as the OS using the specified color
     */
    /*public void renderMonochromeImage(Device device, GC gc, int x, int y, 
    		IPath rootPath, ImageInfo bitmapInfo, ImageInfo maskInfo, 
    		Point viewableSize, Point alignmentWeights, 
    		boolean isScaling, boolean isPreservingAspectRatio,
    		boolean flatten, Color color);
    */
    /**
     * Render an image to the gc as the OS would do
     */
    /*public void renderImage(Device device, GC gc, int x, int y, 
            URIImageInfo uriInfo, 
            Point viewableSize, Point alignmentWeights, 
            boolean isScaling, boolean isPreservingAspectRatio,
            boolean flatten, boolean doBlend);
*/
	/**
	 * @return
	 */
	//public boolean supportsSVG();

	/**
	 * Render image data to the GC
	 * @param device
	 * @param gc
	 * @param x
	 * @param y
	 * @param data
	 * @param viewableSize
	 * @param alignmentWeights
	 * @param isScaling
	 * @param isPreservingAspectRatio
	 * @param flatten
	 * @param blend
	 */
	public void renderImage(Device device, GC gc, int x, int y, ImageData data,
			Point viewableSize, Point alignmentWeights, boolean isScaling,
			boolean isPreservingAspectRatio, boolean flatten, boolean blend);

	/**
	 * @param device
	 * @param gc
	 * @param x
	 * @param y
	 * @param model
	 * @param viewableSize
	 * @param alignmentWeights
	 * @param isScaling
	 * @param isPreservingAspectRatio
	 * @param flatten
	 * @param doBlend
	 * @throws CoreException
	 */
	void renderImage(Device device, GC gc, int x, int y, IImageModel model,
			Point viewableSize, Point alignmentWeights, boolean isScaling,
			boolean isPreservingAspectRatio, boolean flatten, boolean doBlend)
			throws CoreException;
  
}
