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
package com.nokia.sdt.datamodel.images;

import com.nokia.sdt.datamodel.adapter.IImagePropertyRenderingInfo;
import com.nokia.sdt.displaymodel.ILookAndFeel;

import org.eclipse.swt.graphics.*;

/**
 * This interface is used to render an image.  
 * The client obtains an instance of this
 * interface, invokes various setters, and then either renders
 * ({@link #render(GC, int, int)} or retrieves (@link #getImage(Device)}
 * the image.
 * <p>
 * This interface does not describe how to set what image is rendered.
 * See subinterfaces for that.
 * 
 * 
 * 
 */
public interface IImageRendering {
	/** Keep transparency/alpha channel when rendering. May be slow. */
	static final int TRANSPARENCY_KEEP = 0;

	/**
	 * Get rid of transparency before rending, by using the GC's background to
	 * fill in transparent regions. Alpha values < 64 are full background and
	 * otherwise full foreground.
	 */
	static final int TRANSPARENCY_FLATTEN = 1;

	/**
	 * Get rid of transparency before rending, by using the GC's background to
	 * fill in transparent regions. Blend each pixel with the background
	 * according to the alpha channel.
	 */
	static final int TRANSPARENCY_FLATTEN_AND_BLEND = 2;

	/** 
	 * Default rendering model
	 */
	static final String MODEL_DEFAULT = "default"; //$NON-NLS-1$
	
	/**
	 * Reset the rendering to a clean slate (no instance, property path,
	 * or rendering parameters), in case this instance is shared between renderings.
	 */
	void reset();

	/**
	 * Override the viewable size.  This may be, for example, the size into
	 * which the image is scaled or aligned (if not scaled).
	 * 
	 * @param size
	 *            the size to use, or null for the real image size
	 * @see IImagePropertyRenderingInfo#getViewableSize(String, ILookAndFeel)
	 */
	void setViewableSize(Point size);

	/**
	 * Override the alignment weights.  When the image is smaller than its
	 * viewable size and is not scaled or is scaled with aspect ratio preserved,
	 * then the image will not fully fit into the box.  The alignment weights
	 * tell how to position the image in the box.
	 * @param weights
	 *            Point describing weights in the x and y dimensions, where -1
	 *            indicates left/top, 0 indicates center, 1 indicates center or
	 *            left on overflow, 2 indicates right/bottom.
	 * @see IImagePropertyRenderingInfo#getAlignmentWeights(String, ILookAndFeel)
	 */
	void setAlignmentWeights(Point weights);

	/**
	 * Override whether the image is scaled to fit its viewable size.
	 * The aspect ratio preservation affects this.
	 * @param isScaling
	 *            true to scale or false to align or crop
	 * @see IImagePropertyRenderingInfo#isScaling(String, ILookAndFeel)
	 */
	void setScaling(boolean isScaling);

	/**
	 * Override whether the aspect ratio is preserved for a scaled bitmap or SVG
	 * image.  If it's preserved, the width and height maintain the same proportion
	 * when scaled.
	 * @param isPreserving
	 *            true to preserve width:height ratio, false to stretch to fit
	 * @see IImagePropertyRenderingInfo#isPreservingAspectRatio(String, ILookAndFeel)
	 */
	void setPreservingAspectRatio(boolean isPreserving);

	/**
	 * Configure the method used for handling transparency in the image (either
	 * transparent pixels or alpha channel). Note: blending is ignored in
	 * monochrome rendering.
	 * 
	 * @param transparencySetting
	 *            one of {@link #TRANSPARENCY_KEEP},
	 *            {@link #TRANSPARENCY_FLATTEN}, or
	 *            {@link #TRANSPARENCY_FLATTEN_AND_BLEND}
	 */
	void setTransparencyHandling(int transparencySetting);

	/**
	 * Set the rendering model for the image property, e.g., a different
	 * way to interpret the property or its contents for some reason.
	 * <p>  
	 * The implementation of IImageRendering will establish a suitable default 
	 * itself based on the property type and settings.  This is usually 
	 * used for special cases.  See extensions to this interface for other models.
	 * @see {@link #MODEL_DEFAULT}   
	 */
	void setRenderingModel(String model);
	
	/**
	 * Render the image to the GC.
	 * <p>
	 * Invoke after calling appropriate setters to render the image. 
	 * This uses the rendering settings and transparency handling flag 
	 * to get the image pixels, then renders these in the GC at the given offset
	 * inside a box of the viewable size.
	 * 
	 * @param graphics
	 *            the GC
	 * @param x
	 *            x offset of image (left)
	 * @param y
	 *            y offset of image (top)
	 */
	void render(GC gc, int x, int y);
	
	/**
	 * Get the image data to render.
	 * <p>
	 * Invoke after calling appropriate setters to render the image. 
	 * This uses the appropriate rendering flags and the
	 * transparency handling flag to get the image pixels, then provides
	 * the image data.
	 * @return an ImageData or null if insufficient information was set. 
	 */
	ImageData getImageData();
}
