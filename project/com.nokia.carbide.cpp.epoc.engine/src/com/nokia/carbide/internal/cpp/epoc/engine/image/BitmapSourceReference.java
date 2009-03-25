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


public class BitmapSourceReference extends ImageSourceReference implements IBitmapSourceReference {

	private IPath maskPath;

	/**
	 * 
	 */
	public BitmapSourceReference() {
		super();
	}
	
	/**
	 * Create bitmap entry with a mask.
	 */
	public BitmapSourceReference(IPath path, IPath maskPath) {
		super(path);
		setMaskPath(maskPath);
	}

	/**
	 * Create bitmap entry with a mask.
	 */
	public BitmapSourceReference(IPath path) {
		super(path);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSource#copy()
	 */
	public IImageSourceReference copy() {
		BitmapSourceReference copy = new BitmapSourceReference(getPath(), getMaskPath());
		return copy;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BitmapSourceReference path="+getPath()+" maskPath="+getMaskPath(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.image.ImageSource#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IBitmapSourceReference))
			return false;
		if (!super.equals(obj))
			return false;
		
		IBitmapSourceReference other = (IBitmapSourceReference) obj;
		return ObjectUtils.equals(other.getMaskPath(), maskPath);

	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ (maskPath != null ? maskPath.hashCode() : 0) ^ 282874382; 
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

}
