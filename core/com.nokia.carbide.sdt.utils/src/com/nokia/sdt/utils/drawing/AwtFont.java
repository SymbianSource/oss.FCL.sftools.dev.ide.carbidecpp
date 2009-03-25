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

import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.utils.UtilsPlugin;
import com.nokia.sdt.utils.drawing.GC;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.graphics.*;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.font.*;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class loads a Truetype font at runtime via AWT and
 * uses it to render text.  (Unfortunately, SWT doesn't support
 * loading fonts dynamically.)
 *
 */
public class AwtFont implements IFont {

	Font awtFont;
    float size;
    private LineMetrics lineMetrics;
    private boolean calculatedAverage;
    private int averageCharWidth;
    private float height;

    private boolean isBitmapped;
    
    /** Instantiate an AWT font.  
     */
    public AwtFont(String filename, float size) {
        
        this.size = size;
        
        Pattern p = Pattern.compile(".*(\\d+)\\.(\\d+)\\.ttf"); //$NON-NLS-1$
        Matcher m = p.matcher(filename);
        if (m.matches()) {
            // This particular naming convention is unique to
            // our bitmapped TrueType fonts, and tells us the 
            // actual height of the font.
            this.height = Integer.parseInt(m.group(2));
            this.isBitmapped = true;
        } else {
            this.height = size;
            // convert points to pixels
            size = size * 100.f / 72;
            this.size = size;
        }
  
        try {
            File fontFile = new File(filename);
            awtFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            awtFont = awtFont.deriveFont(size);
        } catch (IOException e) {
            Logging.log(UtilsPlugin.getDefault(),
                    Logging.newStatus(UtilsPlugin.getDefault(), IStatus.ERROR, 
                            "Cannot load font file " + filename, e)); //$NON-NLS-1$
            awtFont = new Font("Serif", Font.PLAIN, (int)size); //$NON-NLS-1$
        } catch (FontFormatException e) {
            Logging.log(UtilsPlugin.getDefault(),
                    Logging.newStatus(UtilsPlugin.getDefault(), IStatus.ERROR, 
                            "Cannot read font file " + filename, e)); //$NON-NLS-1$
            awtFont = new Font("Serif", Font.PLAIN, (int)size); //$NON-NLS-1$
        }
        
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.drawing.IFont#dispose()
     */
    public void dispose() {
        // no real benefit to AWT
        awtFont = null;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.drawing.IFont#isBitmapped()
     */
    public boolean isBitmapped() {
    	return isBitmapped;
    }
    
    public Point formattedStringExtent(final String text, final Point bounds, int flags_, final int lineGap) {
    	return TextRendering.formattedStringExtent(this, text, bounds, flags_, lineGap);
    }

    /*
     *  (non-Javadoc)
     * @see com.nokia.sdt.utils.drawing.IFont#drawFormattedString(com.nokia.sdt.utils.drawing.GC, java.lang.String, org.eclipse.swt.graphics.Rectangle, int)
     */
    public void drawFormattedString(GC gc, final String text, final Rectangle bounds, int flags_, final int lineGap) {
    	TextRendering.drawFormattedString(this, gc, text, bounds, flags_, lineGap, null);
    }

    public Point stringExtent(String string) {
        return formattedStringExtent(string, new Point(0, 0), IFont.WRAPPING_NONE, 0);
    }

    public int getCharWidth(char ch) {
    	boolean antiAlias = !isBitmapped();
    	FontRenderContext frc = new FontRenderContext(null, antiAlias, false);
	    TextLayout tl = new TextLayout(""+ch, awtFont, frc);
	    return (int) tl.getVisibleAdvance();
    }

    public int getAdvanceWidth(char ch) {
    	boolean antiAlias = !isBitmapped();
    	FontRenderContext frc = new FontRenderContext(null, antiAlias, false);
	    TextLayout tl = new TextLayout(""+ch, awtFont, frc);
	    return (int) tl.getAdvance();
    }

    private LineMetrics getMetrics() {
        if (lineMetrics == null) {
            AffineTransform tr = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(tr, false, false);
            lineMetrics = awtFont.getLineMetrics("Aj", frc); //$NON-NLS-1$
        }
        return lineMetrics;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.drawing.IFont#getAscent()
     */
    public int getAscent() {
        return (int)getMetrics().getAscent();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.drawing.IFont#getDescent()
     */
    public int getDescent() {
        return (int)getMetrics().getDescent();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.drawing.IFont#getLeading()
     */
    public int getLeading() {
        return (int)getMetrics().getLeading();
    }

    public int getHeight() {
        return (int)getMetrics().getHeight();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.drawing.IFont#getAverageCharWidth()
     */
    public int getAverageCharWidth() {
        if (!calculatedAverage) {
            calculatedAverage = true;
            
            AffineTransform tr = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(tr, false, false);
            StringBuffer buf = new StringBuffer();
            for (char ch = 32; ch < 127; ch++)
                buf.append(ch);
            Rectangle2D r2d = awtFont.getStringBounds(buf.toString(), frc);
            averageCharWidth = (int)(r2d.getWidth() / buf.length());
        }
        return averageCharWidth;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.drawing.IFont#getSize()
     */
    public float getSize() {
        return height;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.drawing.IFont#getSWTFont()
     */
    public org.eclipse.swt.graphics.Font getSWTFont(Device device, int size) {
        // not really implemented: need for SWT to implement direct
        // support, or for OS (win32) to expose CreateScalableFontResource
        // and AddFontResource
        try {
            return new org.eclipse.swt.graphics.Font(device, "Arial Unicode MS", size, 0); //$NON-NLS-1$
        } catch (SWTError e) {
            return null;
        }
    }

	public void drawRotatedString(GC gc, String text, Rectangle bounds, int x, int y, float angle, boolean antiAlias) {
		IFont oldFont = gc.getFont();
		gc.setFont(this);
		TextRendering.drawRotatedString(gc, text, bounds, x, y, angle, antiAlias);
		gc.setFont(oldFont);
	}
}
