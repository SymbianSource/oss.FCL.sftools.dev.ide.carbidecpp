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

public class SymbianImageConverterFactory implements IImageConverterFactory {

    /* (non-Javadoc)
     * @see com.nokia.sdt.symbian.images.IImageConverterFactory#getConverter(int, boolean)
     */
    public IColorConverter getConverter(int depth, boolean isColor, boolean isMask) {
        IColorConverter converter = null;
        
        switch (depth) {
        case 1:
            converter = new BitmapUtils.GrayConverter2();
            break;
        case 2:
            converter = new BitmapUtils.GrayConverter4();
            break;
        case 4:
            if (isColor)
                converter = new BitmapUtils.ColorConverter16();
            else
                converter = new BitmapUtils.GrayConverter16();
            break;

        case 8:
            if (isColor)
                converter = new BitmapUtils.ColorConverter256();
            else
                converter = new BitmapUtils.GrayConverter256();
            break;
        }
        return converter;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.symbian.images.IImageConverterFactory#getPalette(int, boolean)
     */
    public RGB[] getPalette(int depth, boolean isColor) {
        return null;
    }
    

}
