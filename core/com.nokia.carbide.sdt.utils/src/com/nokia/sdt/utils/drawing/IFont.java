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
package com.nokia.sdt.utils.drawing;

import org.eclipse.swt.graphics.*;

/** 
 * This is a generic interface to a font rendering engine.
 * It is used here to allow us to load Series 60 TrueType fonts at runtime.
 *
 */
public interface IFont extends IFontConstants {
    /**
     * Dispose the font.
     */
    public void dispose();

    /**
	 * Get the extent of the string without regard to formatting.
	 */
    public Point stringExtent(String string);

    /**
     * @see org.eclipse.swt.graphics.GC#getCharWidth(char)
     */
    public int getCharWidth(char ch);

    /**
     * @see org.eclipse.swt.graphics.GC#getAdvanceWidth(char)
     */
    public int getAdvanceWidth(char ch);

    /**
     * @see org.eclipse.swt.graphics.FontMetrics#getAscent()
     */
    public int getAscent();

    /**
     * @see org.eclipse.swt.graphics.FontMetrics#getDescent()
     */
    public int getDescent();

    /**
     * @see org.eclipse.swt.graphics.FontMetrics#getHeight()
     */
    public int getHeight();

    /**
     * @see org.eclipse.swt.graphics.FontMetrics#getLeading()
     */
    public int getLeading();

    /**
     * @see org.eclipse.swt.graphics.FontMetrics#getAverageCharWidth()
     */
    public int getAverageCharWidth();

    /**
     * Get the font size
     * @return size in points
     */
    public float getSize();

    /**
     * Get an SWT font instance for the font
     * @param device 
     * @param size point size
     * @return a Font instance, or null -- you must dispose this font
     */
    public Font getSWTFont(Device device, int size);
    
    /**
     * Tell whether the font is bitmapped.
     */
    public boolean isBitmapped();
}
