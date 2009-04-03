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

public interface IImageConverterFactory {
    /**
     * Get the converter to images of this depth.
     * @param depth the target bit depth
     * @param isColor true: get a color->color converter, false: get a color->greyscale converter
     * @param isMask true: is the mask, false: is the image 
     * @return converter object
     */
    public IColorConverter getConverter(int depth, boolean isColor, boolean isMask);
    
    /**
     * Get the palette to map to 
     */
    public RGB[] getPalette(int depth, boolean isColor);
}
