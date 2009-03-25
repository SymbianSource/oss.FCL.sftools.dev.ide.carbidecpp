/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.cpp.internal.project.ui.images;

import com.nokia.cpp.internal.api.utils.ui.IColorConverter;

/**
 * In UIQ, 1-bit masks appear to be ignored
 *
 */
public class UIQImageConverterFactory extends SymbianImageConverterFactory {

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.SymbianImageConverterFactory#getConverter(int, boolean, boolean)
	 */
	@Override
	public IColorConverter getConverter(int depth, boolean isColor,
			boolean isMask) {
		return super.getConverter(depth, isColor, isMask);
	}

}
