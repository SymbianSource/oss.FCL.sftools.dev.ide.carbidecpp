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


public class SVGSource extends ImageSource implements ISVGSource {

	public SVGSource() {
		super();
	}
	
	public SVGSource(boolean isColor, int depth, int maskDepth, IPath path) {
		super(isColor, depth, maskDepth, path);
	}

	public SVGSource(IPath path) {
		super(true, 32, path);
		setMaskDepth(8);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSource#copy()
	 */
	public IImageSourceReference copy() {
		SVGSource copy = new SVGSource(isColor(), getDepth(), getMaskDepth(), getPath());
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
		return "SVGSource color="+isColor()+" depth="+getDepth()+" path="+getPath() //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		+" maskDepth="+getMaskDepth()+" maskPath="+getImpliedMaskPath(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.image.ImageSource#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ISVGSource))
			return false;
		return super.equals(obj);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ -12334; 
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.ISVGSource#getImpliedMaskPath()
	 */
	public IPath getImpliedMaskPath() {
		String fileName = getPath().lastSegment();
		fileName = TextUtils.stripExtension(fileName) + "_mask.bmp"; //$NON-NLS-1$
		return getPath().removeLastSegments(1).append(fileName);
	}

}
