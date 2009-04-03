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

import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * Wrapper for SWT FontMetrics
 *
 */
public class FontMetrics {

    org.eclipse.swt.graphics.FontMetrics swtMetrics;
    IFont iFont;
    
    /**
     * 
     */
    public FontMetrics(IFont iFont) {
        setFromIFont(iFont);
    }

    public FontMetrics(org.eclipse.swt.graphics.FontMetrics swtMetrics) {
        setFromFontMetrics(swtMetrics);
    }
    
    public void setFromFontMetrics(org.eclipse.swt.graphics.FontMetrics swtMetrics) {
        Check.checkArg(swtMetrics);
        this.swtMetrics = swtMetrics;
        this.iFont = null;
    }

    public void setFromIFont(IFont iFont) {
        Check.checkArg(iFont);
        this.iFont = iFont;
        this.swtMetrics = null;
    }

    public int getAscent() {
        if (iFont != null)
            return iFont.getAscent();
        else
            return swtMetrics.getAscent();
    }

    public int getDescent() {
        if (iFont != null)
            return iFont.getDescent();
        else
            return swtMetrics.getDescent();
    }

    public int getAverageCharWidth() {
        if (iFont != null)
            return iFont.getAverageCharWidth();
        else
            return swtMetrics.getAverageCharWidth();
    }
    
    /** Return nominal height of font (typical bounding box) */
    public int getHeight() {
        if (iFont != null)
            return iFont.getHeight();
        else
            return swtMetrics.getHeight();
    }
    
    public int getLeading() {
        if (iFont != null)
            return iFont.getLeading();
        else
            return swtMetrics.getLeading();
    }
    
    /** Return point size of font (maximal character height) */
    //public int getSize();


}
