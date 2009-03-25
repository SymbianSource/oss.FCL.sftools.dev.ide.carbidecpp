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
package com.nokia.cpp.internal.api.utils.ui;

import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

public interface IColorConverter {
	/** 
	 * Convert the given image RGB value to an RGB value
	 * that reflects the representation in another color space.
	 * @param palette a palette configured for R=0xff, G=0xff00, B=0xff0000 space
	 * @param outColors array of colors to convert to (not used)
	 * @param rgb RGB in inPalette space
	 * @return new pixel in outPalette space
	 */
	public int convertPixel(PaletteData palette, RGB[] outColors, RGB rgb);
}