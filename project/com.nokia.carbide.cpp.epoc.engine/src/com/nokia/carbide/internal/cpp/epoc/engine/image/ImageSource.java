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

package com.nokia.carbide.internal.cpp.epoc.engine.image;

import com.nokia.carbide.cpp.epoc.engine.image.*;

import org.eclipse.core.runtime.IPath;


public class ImageSource extends ImageSourceReference implements IImageSource {

	private int depth;
	private boolean isColor;
	private int maskDepth;

	/**
	 * 
	 */
	public ImageSource(boolean isColor, int depth, IPath path) {
		super(path);
		setDepth(depth);
		setColor(isColor);
	}

	public ImageSource(boolean isColor, int depth, int maskDepth, IPath path) {
		this(isColor, depth, path);
		setMaskDepth(maskDepth);
	}

	public ImageSource() {
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IImageSource))
			return false;
		if (!super.equals(obj))
			return false;
		IImageSource other = (IImageSource) obj;
		return other.getDepth() == depth
		&& other.isColor() == isColor
		&& other.getMaskDepth() == maskDepth;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ (depth<<6) ^ (isColor ? 1 : 0) ^
			(maskDepth << 12) ^ 1983; 
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ImageSource color="+isColor+" depth="+depth+" path="+getPath(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSource#copy()
	 */
	public IImageSourceReference copy() {
		return new ImageSource(isColor, depth, getPath());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSource#isValid()
	 */
	public boolean isValid() {
		return super.isValid() && 
			depth > 0 &&
			isValidDepth(depth);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSource#getDepth()
	 */
	public int getDepth() {
		return depth;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSource#isColor()
	 */
	public boolean isColor() {
		return isColor;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSource#setColor(boolean)
	 */
	public void setColor(boolean color) {
		this.isColor = color;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSource#setDepth(int)
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.ISVGSource#getMaskDepth()
	 */
	public int getMaskDepth() {
		return maskDepth;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.ISVGSource#setMaskDepth(int)
	 */
	public void setMaskDepth(int depth) {
		this.maskDepth = depth;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSource#isValidDepth(int)
	 */
	public boolean isValidDepth(int depth) {
		return (!isColor ? depth == 1 || depth == 2 || depth == 4 || depth == 8
				: depth == 4 || depth == 8 || depth == 12 || depth == 16 || depth == 24 || depth == 32);	
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSource#getDefaultMaskPath(boolean)
	 */
	public IPath getDefaultMaskPath() {
		return getDefaultMaskPath(maskDepth);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSource#setFrom(com.nokia.carbide.cpp.epoc.engine.image.IImageSourceReference)
	 */
	public void setFrom(IImageSourceReference ref) {
		setPath(ref.getPath());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSource#getImageFormat()
	 */
	public ImageFormat getImageFormat() {
		return new ImageFormat(isColor, depth, maskDepth);
	}
}
