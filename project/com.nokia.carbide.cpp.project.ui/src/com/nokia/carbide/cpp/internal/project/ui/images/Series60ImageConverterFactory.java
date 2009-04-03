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

import org.eclipse.swt.graphics.RGB;

/**
 * Color conversion for S60 hosts.  Here, 8-bit images are
 * mapped on the device to a different palette than bmconv
 * uses by default.  An option must be passed to bmconv to fix
 * this, but currently Carbide doesn't support it.
 * 
 *	@deprecated The underlying bug is fixed in Carbide 1.2, so the
 * palette doesn't have to be set, and images look like they should.
 */
public class Series60ImageConverterFactory extends SymbianImageConverterFactory {

    /* (non-Javadoc)
     * @see com.nokia.sdt.symbian.images.IImageConverterFactory#getConverter(int, boolean)
     */
    public IColorConverter getConverter(int depth, boolean isColor, boolean isMask) {
        if (depth == 8 && isColor && !isMask)
            return new BitmapUtils.ColorConverter256_S60();
        return super.getConverter(depth, isColor, isMask);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.symbian.images.IImageConverterFactory#getPalette(int, boolean)
     */
    public RGB[] getPalette(int depth, boolean isColor) {
        return null;
    }

}
