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

import com.nokia.carbide.internal.cpp.epoc.engine.image.MultiImageSource;


public abstract class MultiImageSourceFactory {
	public static IMultiImageSource createMultiImageSource(boolean canSetHeaderPath, boolean allowBMPs, boolean allowSVGs) {
		return new MultiImageSource(canSetHeaderPath, allowBMPs, allowSVGs);
	}
}
