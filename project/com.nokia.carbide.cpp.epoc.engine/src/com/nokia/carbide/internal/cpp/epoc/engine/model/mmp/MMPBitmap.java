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

package com.nokia.carbide.internal.cpp.epoc.engine.model.mmp;

import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSource;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPBitmap;
import com.nokia.carbide.internal.cpp.epoc.engine.image.MultiImageSource;

import java.util.List;


public class MMPBitmap extends MultiImageSource implements IMMPBitmap {

	public MMPBitmap() {
		super(false, true, false);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource#copy()
	 */
	public IMMPBitmap copy() {
		MMPBitmap copy = new MMPBitmap();
		copy.setSources(copySources());
		copy.setTargetFile(getTargetFile());
		copy.setTargetPath(getTargetPath());
		copy.setHeaderFlags(getHeaderFlags());
		copy.generatedHeaderPath = getGeneratedHeaderFilePath();
		return copy;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IMMPBitmap))
			return false;
		return super.equals(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPBitmap#getBitmapSources()
	 */
	public List<IBitmapSource> getBitmapSources() {
		return (List<IBitmapSource>) (List) getSources();
	}
	
}
