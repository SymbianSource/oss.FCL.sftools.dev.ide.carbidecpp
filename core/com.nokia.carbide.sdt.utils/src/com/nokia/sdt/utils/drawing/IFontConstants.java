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

public interface IFontConstants {
    /** Ignore overflow (text is clipped) (default) */
    public static final int OVERFLOW_IGNORE = (0 << 0);
    /** Truncate text before overflow */
    //public static final int OVERFLOW_TRUNCATE = (1 << 0);
    /** Use an ellipsis to indicate truncation */ 
    public static final int OVERFLOW_ELLIPSIS = (2 << 0);
    public static final int OVERFLOW_MASK = (3 << 0);

    /** default */
    public static final int DIRECTION_LEFT_TO_RIGHT = (0 << 2); 
    public static final int DIRECTION_RIGHT_TO_LEFT = (1 << 2);
    public static final int DIRECTION_DEFAULT_LEFT_TO_RIGHT = (2 << 2);
    public static final int DIRECTION_DEFAULT_RIGHT_TO_LEFT = (3 << 2);
    public static final int DIRECTION_MASK = (3 << 2); 

    /** default */
    public static final int DRAW_TRANSPARENT = (0 << 4);
    public static final int DRAW_OPAQUE = (1 << 4);
    public static final int DRAW_MASK = (3 << 4);
    
    /** default */
    public static final int HORIZONTAL_ALIGN_LEFT = (0 << 6);
    public static final int HORIZONTAL_ALIGN_RIGHT = (1 << 6);
    public static final int HORIZONTAL_ALIGN_CENTER = (2 << 6);
    public static final int HORIZONTAL_ALIGN_MASK = (3 << 6);

    public static final int ALIGN_LEFT = HORIZONTAL_ALIGN_LEFT;
    public static final int ALIGN_RIGHT = HORIZONTAL_ALIGN_RIGHT;
    public static final int ALIGN_CENTER = HORIZONTAL_ALIGN_CENTER;
    public static final int ALIGN_MASK = HORIZONTAL_ALIGN_MASK;

    // <<< vertical alignment below >>>
    
    /** default */
    public static final int WRAPPING_NONE = (0 << 8);
    public static final int WRAPPING_ENABLED = (1 << 8);
    public static final int WRAPPING_MASK = (1 << 8);
    
    /** default */
    public static final int BIDI_NONE = (0 << 9);
    public static final int BIDI_ENABLED = (1 << 9);
    public static final int BIDI_MASK = (1 << 9);

    // note: not sure why we skipped one bit here. 1 << 10 can be used later.
    
    /** default */
    public static final int OPTIONS_NONE = (0 << 11);
    public static final int OPTIONS_UNDERLINE = (1 << 11);
    public static final int OPTIONS_STRIKETHROUGH = (2 << 11);
    public static final int OPTIONS_BOLD = (4 << 11);
    public static final int OPTIONS_EXTRABOLD = (12 << 11);
    public static final int OPTIONS_MASK = (15 << 11);
    
    // note: on by default
    /** default */
    public static final int ANTIALIAS_ON = (0 << 15);
    public static final int ANTIALIAS_OFF = (1 << 15);
    public static final int ANTIALIAS_MASK = (1 << 15);

    /** default */
    public static final int VERTICAL_ALIGN_TOP = (0 << 16);   
    public static final int VERTICAL_ALIGN_BOTTOM =(1 << 16);
    public static final int VERTICAL_ALIGN_CENTER = (2 << 16);
    public static final int VERTICAL_ALIGN_MASK = (3 << 16);

    // next: 18

}
