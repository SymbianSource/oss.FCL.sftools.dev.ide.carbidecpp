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

import com.nokia.carbide.cpp.epoc.engine.image.IImageSourceReference;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;


public class ImageSourceReference implements IImageSourceReference {

	private IPath path;

	/**
	 * 
	 */
	public ImageSourceReference(IPath path) {
		setPath(path);
	}

	public ImageSourceReference() {
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ImageSourceReference path="+getPath(); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IImageSourceReference))
			return false;
		IImageSourceReference other = (IImageSourceReference) obj;
		return ObjectUtils.equals(other.getPath(), path);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return path.hashCode() ^ 190999; 
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSourceReference#copyReference()
	 */
	public IImageSourceReference copy() {
		return new ImageSourceReference(path);
	}
	

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSource#isValid()
	 */
	public boolean isValid() {
		return path != null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSource#getPath()
	 */
	public IPath getPath() {
		return path;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSource#setPath(org.eclipse.core.runtime.IPath)
	 */
	public void setPath(IPath path) {
		Check.checkArg(path);
		this.path = path;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSource#getImpliedMaskPath()
	 */
	public IPath getDefaultMaskPath(int maskDepth) {
		if (path == null)
			return null;
		String fileName = getPath().lastSegment();
		fileName = TextUtils.stripExtension(fileName) 
			+ (maskDepth <= 1 ? "_mask.bmp" : "_mask_soft.bmp"); //$NON-NLS-1$ //$NON-NLS-2$
		return getPath().removeLastSegments(1).append(fileName);
	}
	
}
