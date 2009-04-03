/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.image;

import org.eclipse.core.runtime.IPath;


/**
 * Representation of a source image file with color information.
 *
 */
public interface IImageSource extends IImageSourceReference {
	/** tell if the entry is valid (e.g., initialized): 
	 * path must be set and depth must be nonzero,
	 * and if in color, depth must be 4, 8, 16, or 24, else 1, 2, 4, or 8 */
	boolean isValid();

	/** tell whether the image is kept in color */
	boolean isColor();
	/** set the color conversion flag */
	void setColor(boolean color);
	
	/** get the bit depth to convert to
	 * 
	 * @return generally 4,8,12,16,24,32 for color
	 * or 1,2,4,8 for non-color
	 */
	int getDepth();
	/** set the bit depth to convert to
	 * 
	 * @param depth generally 4,8,12,16,24,32 for color
	 * or 1,2,4,8 for non-color
	 */
	void setDepth(int depth);

	/** get the mask depth (usually 1,2,4,8 or 0 for none) 
	 * <p>
	 * NOTE: for SVG, if this is nonzero, it doesn't mean a mask file is present,
	 * merely that an enum will be generated. 
	 */
	int getMaskDepth();
	/** set the mask depth (usually 1,2,4,8 or 0 for none) 
	 * 
	 * <p>
	 * NOTE: for SVG, if this is nonzero, it doesn't mean a mask file is present,
	 * merely that an enum will be generated. 
	 */
	void setMaskDepth(int depth);

	/** provide the default mask filepath */
	IPath getDefaultMaskPath();
	
	/** Tell if the depth is valid for the color/grayscale flag */
	boolean isValidDepth(int depth);

	/**
	 * Set shared fields from the reference.
	 * @param ref
	 */
	void setFrom(IImageSourceReference ref);

	/**
	 * Get a copy of the image format parameters
	 * @return
	 */
	ImageFormat getImageFormat();

}
