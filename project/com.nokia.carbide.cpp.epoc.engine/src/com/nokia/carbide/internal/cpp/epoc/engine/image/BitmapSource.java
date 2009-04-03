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
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;


public class BitmapSource extends ImageSource implements IBitmapSource {

	private IPath maskPath;

	/**
	 * 
	 */
	public BitmapSource() {
		super();
	}
	
	/**
	 * Create bitmap entry with a mask.
	 */
	public BitmapSource(boolean isColor, int depth, int maskDepth, IPath path, IPath maskPath) {
		super(isColor, depth, maskDepth, path);
		setMaskPath(maskPath);
	}

	/**
	 * Create bitmap entry with a mask.
	 */
	public BitmapSource(boolean isColor, int depth, IPath path) {
		super(isColor, depth, path);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSource#copy()
	 */
	public IImageSource copy() {
		BitmapSource copy = new BitmapSource(isColor(), getDepth(), getMaskDepth(), getPath(), getMaskPath());
		return copy;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSourceReference#copyReference()
	 */
	public IImageSourceReference copyReference() {
		return copy();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BitmapSource color="+isColor()+" depth="+getDepth()+" maskDepth="+getMaskDepth() //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			+" path="+getPath() +" maskPath="+getMaskPath(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.image.ImageSource#isValid()
	 */
	@Override
	public boolean isValid() {
		return super.isValid() && ((getMaskDepth() != 0) || (maskPath == null));
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.image.ImageSource#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IImageSource))
			return false;
		if (!super.equals(obj))
			return false;
		
		IBitmapSource other = (IBitmapSource) obj;
		return ObjectUtils.equals(other.getMaskPath(), maskPath);

	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ (maskPath != null ? maskPath.hashCode() : 0) ^ -293842; 
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IBitmapSource#getMaskPath()
	 */
	public IPath getMaskPath() {
		return maskPath;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IBitmapSource#setMaskPath(org.eclipse.core.runtime.IPath)
	 */
	public void setMaskPath(IPath filepath) {
		this.maskPath = filepath;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.image.ImageSource#setFrom(com.nokia.carbide.cpp.epoc.engine.image.IImageSourceReference)
	 */
	@Override
	public void setFrom(IImageSourceReference ref) {
		super.setFrom(ref);
		if (ref instanceof IBitmapSourceReference)
			setMaskPath(((IBitmapSourceReference) ref).getMaskPath());
	}
}
