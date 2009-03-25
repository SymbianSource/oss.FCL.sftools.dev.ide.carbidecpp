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

package com.nokia.carbide.cpp.epoc.engine.model.mmp;

import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSource;
import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;

import java.util.List;

/**
 * The data in a START BITMAP ... END block.  This is merely
 * a wrapper for IImageBuildContainer that provides a
 * specific accessor for bitmaps.
 *
 */
public interface IMMPBitmap extends IMultiImageSource {

	/** Access/modify the list of bitmap sources.  
	 * @return the same list as #getSources().
	 */
	List<IBitmapSource> getBitmapSources();
}
