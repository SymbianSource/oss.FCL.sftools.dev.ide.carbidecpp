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


public class SVGSourceReference extends SVGSource implements ISVGSourceReference {

	public SVGSourceReference(IPath path) {
		super(path);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageSource#copy()
	 */
	public IImageSourceReference copy() {
		SVGSourceReference copy_ = new SVGSourceReference(getPath());
		return copy_;
	}


	public boolean equals(Object obj) {
		if (!(obj instanceof ISVGSourceReference))
			return false;
		return super.equals(obj);
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SVGSourceReference path="+getPath()+" implied mask="+getImpliedMaskPath(); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ 938549095; 
	}
}
