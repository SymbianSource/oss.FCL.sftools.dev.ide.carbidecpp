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

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.PathData;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.graphics.Transform;

/**
 * This class wraps the GC for use by JavaScript code.
 * 
 * @see org.eclipse.swt.graphics.GC
 */

public class GC {
    private org.eclipse.swt.graphics.GC gc;
    
    int offX, offY;
    boolean disposed;
    static int nextSerial = 1;
    int serial = nextSerial++;

    public final static RGB TRANSPARENT_RGB_VALUE = new RGB(0xff, 0x80, 0x00);
    public final static int TRANSPARENT_PIXEL = (TRANSPARENT_RGB_VALUE.red << 24)
			+ (TRANSPARENT_RGB_VALUE.green << 16)
			+ (TRANSPARENT_RGB_VALUE.blue << 8)
            + 0xff;
    public final static RGB SUBSTITUTE_RGB_VALUE = new RGB(0xfe, 0x7f, 0x01);
    public final static int SUBSTITUTE_PIXEL = (SUBSTITUTE_RGB_VALUE.red << 24)
			+ (SUBSTITUTE_RGB_VALUE.green << 16)
			+ (SUBSTITUTE_RGB_VALUE.blue << 8)
            + 0xff;
    private Color transparentColor;
    private Color substituteColor;
    private Device device;
   
    /* used in preference to gc.getFont()
     */
    IFont iFont;
    
    /** Create a wrapped GC for the given drawable. */
    public GC(Device device, Drawable drawable) {
        this.gc = new org.eclipse.swt.graphics.GC(drawable);
    
        this.device = device;
        this.transparentColor = new Color(device, TRANSPARENT_RGB_VALUE);
        this.substituteColor = new Color(device, SUBSTITUTE_RGB_VALUE);
    }

    public org.eclipse.swt.graphics.GC getWrappedGC() {
        return this.gc;
    }
    
    public void dispose() {
        transparentColor.dispose();
        substituteColor.dispose();
        gc.dispose();
        disposed = true;
        gc = null;
        transparentColor = null;
        substituteColor = null;
     }

    /** 
     * Draw a string which takes formatting flags, a line gap, and maximum
     * lengths for each line
     * 
     * @param string the string to draw
     * @param bounds the rectangle in which to draw
     * @param flags mask of IFont.xxx flags
     * @param lineGap extra pixel gap between lines
     * @param widths if not null, maximum width of each line (no effect on bounds); if shorter
     * than the number of lines rendered, the last width is used for remaining lines 
     */
    public void drawFormattedString(String string, Rectangle bounds, int flags, int lineGap, int[] widths) {
        if (iFont == null)
            throw new SWTException("font not set"); //$NON-NLS-1$
        TextRendering.drawFormattedString((AwtFont) iFont, this, string, bounds, flags, lineGap, widths);
    }

    /** 
     * Draw a string which takes formatting flags 
     * 
     * @param string the string to draw
     * @param bounds the rectangle in which to draw
     * @param flags mask of IFont.xxx flags
     * @param lineGap extra pixel gap between lines 
     */
    public void drawFormattedString(String string, Rectangle bounds, int flags, int lineGap) {
        if (iFont == null)
            throw new SWTException("font not set"); //$NON-NLS-1$
        TextRendering.drawFormattedString((AwtFont) iFont, this, string, bounds, flags, lineGap, null);
    }
    
   /** 
     * Draw a string which takes formatting flags 
     * 
     * @param string the string to draw
     * @param bounds the rectangle in which to draw
     * @param flags mask of IFont.xxx flags
     */
    public void drawFormattedString(String string, Rectangle bounds, int flags) {
        drawFormattedString(string, bounds, flags, 0);
    }

    /**
     * Draw a single line string within the given bounds, rotated to the given angle
     * @param text the text to render
     * @param bounds indicates the coordinates delimiting the text box.
     * Should be at least as high as the font. 
     * @param x the x location for the start of the string, must be within the bounds
     * @param y the y location for the start of the string, must be within the bounds
     * @param angle rotation in radians
     * @param antiAlias true to draw anti-aliased
     */
    public void drawRotatedString(String string, Rectangle bounds, int x, int y, float angle, boolean antiAlias) {
        if (iFont == null)
            throw new SWTException("font not set"); //$NON-NLS-1$
        TextRendering.drawRotatedString(this, string, bounds, x, y, angle, antiAlias);
    }
    
 
    /** Copy an array and offset alternating coordinates */
    private int[] adjustArray(int[] pointArray) {
        if (pointArray.length % 2 != 0)
            throw new IllegalArgumentException("array length is odd"); //$NON-NLS-1$

        int[] pointArrayCopy = new int[pointArray.length];
        for (int i = 0; i < pointArray.length; i += 2) {
            pointArrayCopy[i] = pointArray[i] + offX;
            pointArrayCopy[i+1] = pointArray[i+1] + offY;
        }
        return pointArrayCopy;
    }

    /**
     * Return a color that doesn't match the transparent color
     * (returning the substitute color if it does)
     * 
     * @param color
     * @return Color
     */
    private Color adjustColor(Color color) {
        if (color != null && color.getRGB().equals(transparentColor))
            return substituteColor;
        else
            return color;
    }


    /**
     * Adjust the path for the global offset
     * @param path
     * @return Path
     */
    private Path adjustPath(Path path) {
        PathData origData = path.getPathData();
        float[] newPoints = new float[origData.points.length];
        for (int i = 0; i < origData.points.length; i+=2) {
            newPoints[i] += offX;
            newPoints[i+1] += offY;
        }
        return createPath(origData.types, origData.points);
    }

    /**
     * SWT should really do this but currently doesn't.
     * <p>
     * TODO: periodically check if SWT >3.1 provides gc.setPath or Path#setPathData(PathData)
     * 
     * @param types
     * @param points
     * @return Path
     */
    private Path createPath(byte[] types, float[] points) {
        Path path = new Path(device);
        int pidx = 0, tidx = 0;
        while (tidx < types.length) {
            switch (types[tidx]) {
            case SWT.PATH_MOVE_TO:
                path.moveTo(points[pidx], points[pidx+1]);
                pidx += 2;
                break;
            case SWT.PATH_LINE_TO:
                path.lineTo(points[pidx], points[pidx+1]);
                pidx += 2;
                break;
            case SWT.PATH_QUAD_TO:
                path.quadTo(points[pidx], points[pidx+1], points[pidx+2], points[pidx+3]);
                pidx += 4;
                break;
            case SWT.PATH_CUBIC_TO:
                path.cubicTo(points[pidx], points[pidx+1], points[pidx+2], points[pidx+3], points[pidx+4], points[pidx+5]);
                pidx += 6;
                break;
            case SWT.PATH_CLOSE:
                break;
            }
            tidx++;
        }
        return path;
    }

    /**
     * Adjust a pattern using the transparent color to use
     * the substitute color
     * 
     * Note: no accessors, so no way to do this
     * @param pattern
     * @return Pattern
     */
    private Pattern adjustPattern(Pattern pattern) {
        return pattern;
    }

    /** Copy a rectangle and offset coordinates */
    private Rectangle adjustRectangle(Rectangle rect) {
        return new Rectangle(rect.x + offX, rect.y + offY, rect.width, rect.height);
    }

    /** Copy a rectangle and offset alternating coordinates */
    private Rectangle unadjustRectangle(Rectangle rect) {
        return new Rectangle(rect.x - offX, rect.y - offY, rect.width, rect.height);
    }


    /**
     * @see org.eclipse.swt.graphics.GC#copyArea(org.eclipse.swt.graphics.Image, int, int)
     */ 
    public void copyArea(Image image, int x, int y) {
        gc.copyArea(image, x + offX, y + offY);
    }

        
    /**
     * @see org.eclipse.swt.graphics.GC#copyArea(int, int, int, int, int, int)
     */ 
    public void copyArea(int srcX, int srcY, int width, int height, int destX,
            int destY) {
        gc.copyArea(srcX + offX, srcY + offY, width, height, destX + offX,
                destY + offY);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#copyArea(int, int, int, int, int, int, boolean)
     */ 
    public void copyArea(int srcX, int srcY, int width, int height, int destX, int destY, boolean paint) { 
        gc.copyArea(srcX + offX, srcY + offY, width, height, destX + offX, destY + offY, paint);
    }
    
    /**
     * @see org.eclipse.swt.graphics.GC#drawArc(int, int, int, int, int, int)
     */ 
    public void drawArc(int x, int y, int width, int height, int startAngle,
            int arcAngle) {
        gc.drawArc(x + offX, y + offY, width, height, startAngle, arcAngle);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#drawFocus(int, int, int, int)
     */ 
    public void drawFocus(int x, int y, int width, int height) {
        gc.drawFocus(x + offX, y + offY, width, height);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#drawImage(Image, int, int)
     */ 
    public void drawImage(Image image, int x, int y) {
        gc.drawImage(image, x + offX, y + offY);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#drawImage(org.eclipse.swt.graphics.Image, int, int, int, int, int, int, int, int)
     */ 
    public void drawImage(Image image, int srcX, int srcY, int srcWidth,
            int srcHeight, int destX, int destY, int destWidth, int destHeight) {
     	gc.drawImage(image, srcX, srcY, srcWidth, srcHeight, destX + offX,
                destY + offY, destWidth, destHeight);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#drawLine(int, int, int, int)
     */ 
    public void drawLine(int x1, int y1, int x2, int y2) {
        gc.drawLine(x1 + offX, y1 + offY, x2 + offX, y2 + offY);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#drawOval(int, int, int, int)
     */ 
    public void drawOval(int x, int y, int width, int height) {
        gc.drawOval(x + offX, y + offY, width, height);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#drawPath(org.eclipse.swt.graphics.Path)
     */ 
    public void drawPath(Path path) {
        gc.drawPath(adjustPath(path));
    }

    /**
     * @see org.eclipse.swt.graphics.GC#drawPoint(int, int)
     */ 
    public void drawPoint(int x, int y) {
        gc.drawPoint(x + offX, y + offY);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#drawPolygon(int[])
     */ 
    public void drawPolygon(int[] pointArray) {
        gc.drawPolygon(adjustArray(pointArray));
    }

    /**
     * @see org.eclipse.swt.graphics.GC#drawPolyline(int[])
     */ 
    public void drawPolyline(int[] pointArray) {
        gc.drawPolyline(adjustArray(pointArray));
    }

    /**
     * @see org.eclipse.swt.graphics.GC#drawRectangle(int, int, int, int)
     */ 
    public void drawRectangle(int x, int y, int width, int height) {
        gc.drawRectangle(x + offX, y + offY, width, height);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#drawRectangle(org.eclipse.swt.graphics.Rectangle)
     */ 
    public void drawRectangle(Rectangle rect) {
        gc.drawRectangle(adjustRectangle(rect));
    }

    /**
     * @see org.eclipse.swt.graphics.GC#drawRoundRectangle(int, int, int, int, int, int)
     */ 
    public void drawRoundRectangle(int x, int y, int width, int height,
            int arcWidth, int arcHeight) {
        gc.drawRoundRectangle(x + offX, y + offY, width, height, arcWidth,
                arcHeight);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#drawString(java.lang.String, int, int)
     */ 
    public void drawString(String string, int x, int y) {
        if (iFont == null)
            throw new SWTException("font not set"); //$NON-NLS-1$
        TextRendering.drawFormattedString((AwtFont) iFont, 
        		this, string, 
                new Rectangle(x, y, 0, 0), 
                IFont.ALIGN_LEFT 
                + IFont.DIRECTION_DEFAULT_LEFT_TO_RIGHT
                + IFont.OVERFLOW_IGNORE
                + IFont.DRAW_OPAQUE
                + IFont.WRAPPING_NONE, 
                0,
                null
                );
    }

    /**
     * @see org.eclipse.swt.graphics.GC#drawString(java.lang.String, int, int, boolean)
     */ 
    public void drawString(String string, int x, int y, boolean isTransparent) {
        if (iFont == null)
            throw new SWTException("font not set"); //$NON-NLS-1$
        TextRendering.drawFormattedString((AwtFont)iFont,
        		this, string, 
                new Rectangle(x, y, 0, 0), 
                IFont.ALIGN_LEFT 
                + IFont.DIRECTION_DEFAULT_LEFT_TO_RIGHT
                + IFont.OVERFLOW_IGNORE
                + IFont.WRAPPING_NONE
                + (isTransparent ? IFont.DRAW_TRANSPARENT : IFont.DRAW_OPAQUE),
                0,
                null);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#drawText(java.lang.String, int, int)
     */ 
    void drawText(String string, int x, int y) {
        // not supported
        gc.drawText(string, x + offX, y + offY);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#drawText(java.lang.String, int, int, boolean)
     */ 
    void drawText(String string, int x, int y, boolean isTransparent) {
        // not supported
        gc.drawText(string, x + offX, y + offY, isTransparent);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#drawText(java.lang.String, int, int, int)
     */ 
    void drawText(String string, int x, int y, int flags) {
        // not supported
        gc.drawText(string, x + offX, y + offY, flags);
    }

    /**
     * Compares the argument to the receiver, and returns true if they represent
     * the <em>same</em> object using a class specific comparison.
     * 
     * @param object
     *            the object to compare with this object
     * @return <code>true</code> if the object is the same as this object and
     *         <code>false</code> otherwise
     * 
     * @see #hashCode
     */
    public boolean equals(Object object) {
        return (this == object)
                || (object instanceof GC 
                        && ((GC) object).gc.equals(gc)
                        && ((GC) object).iFont.equals(iFont));
    }

    /**
     * @see org.eclipse.swt.graphics.GC#fillArc(int, int, int, int, int, int)
     */ 
    public void fillArc(int x, int y, int width, int height, int startAngle,
            int arcAngle) {
        gc.fillArc(x + offX, y + offY, width, height, startAngle, arcAngle);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#fillGradientRectangle(int, int, int, int, boolean)
     */ 
    public void fillGradientRectangle(int x, int y, int width, int height,
            boolean vertical) {
        gc.fillGradientRectangle(x + offX, y + offY, width, height, vertical);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#fillOval(int, int, int, int)
     */ 
    public void fillOval(int x, int y, int width, int height) {
        gc.fillOval(x + offX, y + offY, width, height);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#fillPath(org.eclipse.swt.graphics.Path)
     */ 
    public void fillPath(Path path) {
        gc.fillPath(adjustPath(path));
    }

    /**
     * @see org.eclipse.swt.graphics.GC#fillPolygon(int[])
     */ 
    public void fillPolygon(int[] pointArray) {
        gc.fillPolygon(adjustArray(pointArray));
    }

    /**
     * @see org.eclipse.swt.graphics.GC#fillRectangle(int, int, int, int)
     */ 
    public void fillRectangle(int x, int y, int width, int height) {
        gc.fillRectangle(x + offX, y + offY, width, height);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#fillRectangle(Rectangle)
     */ 
    public void fillRectangle(Rectangle rect) {
        gc.fillRectangle(adjustRectangle(rect));
    }

    /**
     * @see org.eclipse.swt.graphics.GC#fillRoundRectangle(int, int, int, int, int, int)
     */ 
    public void fillRoundRectangle(int x, int y, int width, int height,
            int arcWidth, int arcHeight) {
        gc.fillRoundRectangle(x + offX, y + offY, width, height, arcWidth,
                arcHeight);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#getAdvanceWidth(char)
     */ 
    public int getAdvanceWidth(char ch) {
        if (iFont == null)
            throw new SWTException("font not set"); //$NON-NLS-1$
        return iFont.getAdvanceWidth(ch);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#getAdvanced()
     */ 
    public boolean getAdvanced() {
        return gc.getAdvanced();
    }

    /**
     * @see org.eclipse.swt.graphics.GC#getAlpha()
     */ 
    public int getAlpha() {
        return gc.getAlpha();
    }
    
    /**
     * @see org.eclipse.swt.graphics.GC#getAntialias()
     */ 
    public int getAntialias() {
        return gc.getAntialias();
    }
        
    /**
     * @see org.eclipse.swt.graphics.GC#getBackground()
     */ 
    public Color getBackground() {
        return gc.getBackground();

    }

    /**
     * @see org.eclipse.swt.graphics.GC#getBackgroundPattern()
     */ 
    public Pattern getBackgroundPattern() {
        return gc.getBackgroundPattern();
    }

    /**
     * @see org.eclipse.swt.graphics.GC#getCharWidth(char)
     */ 
    public int getCharWidth(char ch) {
        if (iFont == null)
            throw new SWTException("font not set"); //$NON-NLS-1$
        return iFont.getCharWidth(ch);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#getClipping()
     */ 
    public Rectangle getClipping() {
        return unadjustRectangle(gc.getClipping());
    }

    /**
     * @see org.eclipse.swt.graphics.GC#getClipping(org.eclipse.swt.graphics.Region)
     */ 
    public void getClipping(Region region) {
        gc.getClipping(region);
        region.translate(-offX, -offY);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#getFillRule()
     */ 
    public int getFillRule() {
        return gc.getFillRule();
    }
    
    /**
     * @see org.eclipse.swt.graphics.GC#getFont()
     */ 
    public IFont getFont() {
        return iFont;
    }

    /**
     * @see org.eclipse.swt.graphics.GC#getFontMetrics()
     */ 
    public FontMetrics getFontMetrics() {
        return new FontMetrics(iFont);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#getForeground()
     */ 
    public Color getForeground() {
        return gc.getForeground();

    }

    /**
     * @see org.eclipse.swt.graphics.GC#getForegroundPattern()
     */ 
    public Pattern getForegroundPattern() {
        return gc.getForegroundPattern();
    }

    /**
     * @see org.eclipse.swt.graphics.GC#getInterpolation()
     */ 
    public int getInterpolation() {
        return gc.getInterpolation();
    }
    
    /**
     * @see org.eclipse.swt.graphics.GC#getLineCap()
     */ 
    public int getLineCap() {
        return gc.getLineCap();
   }

    /**
     * @see org.eclipse.swt.graphics.GC#getLineDash()
     */ 
    public int[] getLineDash() {
        return gc.getLineDash();
    }

    /**
     * @see org.eclipse.swt.graphics.GC#getLineJoin()
     */ 
    public int getLineJoin() {
        return gc.getLineJoin();
    }

    /**
     * @see org.eclipse.swt.graphics.GC#getLineStyle()
     */ 
    public int getLineStyle() {
        return gc.getLineStyle();
    }

    /**
     * @see org.eclipse.swt.graphics.GC#getLineWidth()
     */ 
    public int getLineWidth() {
        return gc.getLineWidth();
    }

    /**
     * @see org.eclipse.swt.graphics.GC#getStyle()
     */ 
    public int getStyle() {
        return gc.getStyle();
    }

    /**
     * @see org.eclipse.swt.graphics.GC#getTextAntialias()
     */ 
    public int getTextAntialias() {
        return gc.getTextAntialias();
    }
    
    /**
     * @see org.eclipse.swt.graphics.GC#getTransform(org.eclipse.swt.graphics.Transform)
     */ 
    public void getTransform(Transform transform) {
        gc.getTransform(transform);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#getXORMode()
     */ 
    public boolean getXORMode() {
        return gc.getXORMode();
    }

    public int hashCode() {
        return gc.hashCode() ^ 0x13456abc;
    }

    /**
     * @see org.eclipse.swt.graphics.GC#isClipped()
     */ 
    public boolean isClipped() {
        return gc.isClipped();
    }

    /**
     * @see org.eclipse.swt.graphics.GC#isDisposed()
     */ 
    public boolean isDisposed() {
        return gc.isDisposed();
    }

    /**
     * @see org.eclipse.swt.graphics.GC#setAdvanced(boolean)
     */ 
    public void setAdvanced(boolean advanced) {
        gc.setAdvanced(advanced);
    }
    
    /**
     * @see org.eclipse.swt.graphics.GC#setAlpha(int)
     */ 
    public void setAlpha(int alpha) {
        gc.setAlpha(alpha);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#setAntialias(int)
     */ 
    public void setAntialias(int antialias)  {
        gc.setAntialias(antialias);
    }
    
    /**
     * @see org.eclipse.swt.graphics.GC#setBackground(org.eclipse.swt.graphics.Color)
     */ 
   public void setBackground(Color color) {
        gc.setBackground(adjustColor(color));
    }

   /**
    * @see org.eclipse.swt.graphics.GC#setBackgroundPattern(org.eclipse.swt.graphics.Pattern)
    */ 
    public void setBackgroundPattern (Pattern pattern) {
        gc.setBackgroundPattern(adjustPattern(pattern));
    }

    /**
     * @see org.eclipse.swt.graphics.GC#setClipping(int, int, int, int)
     */ 
    public void setClipping(int x, int y, int width, int height) {
        gc.setClipping(x + offX, y + offY, width, height);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#setClipping(org.eclipse.swt.graphics.Path)
     */ 
    public void setClipping(Path path) {
        gc.setClipping(adjustPath(path));
    }

    /**
     * @see org.eclipse.swt.graphics.GC#setClipping(org.eclipse.swt.graphics.Rectangle)
     */ 
    public void setClipping(Rectangle rect) {
        gc.setClipping(adjustRectangle(rect));
    }

    /**
     * @see org.eclipse.swt.graphics.GC#setClipping(org.eclipse.swt.graphics.Region)
     */ 
    public void setClipping(Region region) {
        Region rgn = new Region();
        rgn.add(region);
        rgn.translate(offX, offY);
        gc.setClipping(rgn);
        rgn.dispose();
    }

    /**
     * @see org.eclipse.swt.graphics.GC#setFillRule(int)
     */ 
    public void setFillRule(int rule) {
        gc.setFillRule(rule);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#setFont(org.eclipse.swt.graphics.Font)
     */ 
    public void setFont(IFont font) {
        //gc.setFont(font);
        iFont = font;
    }

    /**
     * @see org.eclipse.swt.graphics.GC#setForeground(org.eclipse.swt.graphics.Color)
     */ 
    public void setForeground(Color color) {
        gc.setForeground(adjustColor(color));
    }

    /**
     * @see org.eclipse.swt.graphics.GC#setForegroundPattern(org.eclipse.swt.graphics.Pattern)
     */ 
    public void setForegroundPattern (Pattern pattern) {
        gc.setForegroundPattern(adjustPattern(pattern));
    }
    
    /**
     * @see org.eclipse.swt.graphics.GC#setInterpolation(int)
     */ 
    public void setInterpolation(int interpolation) {
        gc.setInterpolation(interpolation);
    }
    
    /**
     * @see org.eclipse.swt.graphics.GC#setLineCap(int)
     */ 
    public void setLineCap(int cap) {
        gc.setLineCap(cap);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#setLineDash(int[])
     */ 
    public void setLineDash(int[] dashes) {
        gc.setLineDash(dashes);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#setLineJoin(int)
     */ 
    public void setLineJoin(int join) {
        gc.setLineJoin(join);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#setLineStyle(int)
     */ 
    public void setLineStyle(int lineStyle) {
        gc.setLineStyle(lineStyle);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#setLineWidth(int)
     */ 
    public void setLineWidth(int lineWidth) {
        gc.setLineWidth(lineWidth);
    }

    /**
     * @deprecated
     * @see org.eclipse.swt.graphics.GC#setXORMode(boolean)
     */
    public void setXORMode(boolean xor) {
        gc.setXORMode(xor);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#setTextAntialias(int)
     */ 
    public void setTextAntialias(int antialias) {
        gc.setTextAntialias(antialias);
    }
    
    /**
     * @see org.eclipse.swt.graphics.GC#setTransform(org.eclipse.swt.graphics.Transform)
     */ 
    public void setTransform(Transform transform) {
        gc.setTransform(transform);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#stringExtent(java.lang.String)
     */ 
    public Point stringExtent(String string) {
        if (iFont == null)
            return new Point(0, 0);
        return iFont.stringExtent(string);
    }

    /**
     * Get the extent of formatted text.
     * @see #drawFormattedString(String, Rectangle, int)
     * @see IFont#formattedStringExtent(String, Point, int, int)
     */ 
    public Point formattedStringExtent(String string, Point bounds, int flags, int lineGap) {
        if (iFont == null)
            return new Point(0, 0);
        return TextRendering.formattedStringExtent(iFont, string, bounds, flags, lineGap);
    }

    /**
     * Get the extent of formatted text.
     * @see #drawFormattedString(String, Rectangle, int)
     * @see IFont#formattedStringExtent(String, Point, int, int)
     */ 
    public Rectangle formattedStringExtent(String string, Rectangle bounds, int flags, int lineGap) {
        if (iFont == null)
            return new Rectangle(bounds.x, bounds.y, 0, 0);
        Point pt = TextRendering.formattedStringExtent(iFont, string, new Point(bounds.width, bounds.height), flags, lineGap);
        return new Rectangle(bounds.x, bounds.y, pt.x, pt.y);
    }

    /**
     * Get the extent of formatted text.
     * @see #drawFormattedString(String, Rectangle, int)
     * @see IFont#formattedStringExtent(String, Point, int, int)
     */ 
    public Point formattedStringExtent(String string, Point bounds, int flags) {
        return formattedStringExtent(string, bounds, flags, 0);
    }

    /**
     * Get the extent of formatted text.
     * @see #drawFormattedString(String, Rectangle, int)
     * @see IFont#formattedStringExtent(String, Point, int, int)
     */ 
    public Rectangle formattedStringExtent(String string, Rectangle bounds, int flags) {
        return formattedStringExtent(string, bounds, flags, 0);
    }

    /**
     * @see org.eclipse.swt.graphics.GC#textExtent(java.lang.String)
     */ 
    Point textExtent(String string) {
        // not supported (too hard with IFont)
        return null;

    }

    /**
     * @see org.eclipse.swt.graphics.GC#textExtent(java.lang.String, int)
     */ 
    Point textExtent(String string, int flags) {
        // not supported (too hard with IFont)
        return null;

    }

    public String toString () {
    	StringBuffer buf = new StringBuffer("wrapped"); //$NON-NLS-1$
    	buf.append("("); //$NON-NLS-1$
    	buf.append(serial);
    	buf.append(")@"); //$NON-NLS-1$
    	buf.append(Integer.toHexString(hashCode()));
    	buf.append("["); //$NON-NLS-1$
    	buf.append(gc.toString());
    	buf.append("]"); //$NON-NLS-1$
    	return buf.toString();
    }
    
    /**
     * Get the device the GC was created with
     * @return Device
     */
    public Device getDevice() {
        return device;
    }

    /** 
     * Get the X offset all drawing is translated by
     * 
     * @return x offset
     */
    public int getOffX() {
        return offX;
    }
    
    /** 
     * Set the X offset
     * <p>
     * This is separate from gc.setTransform since the latter
     * is "advanced" and also we don't want to conflict with its API
     * 
     * @param offX x offset
     */
    public void setOffX(int offX) {
        this.offX = offX;
    }
    

    /** 
     * Get the Y offset
     * @return y offset
     */
    public int getOffY() {
        return offY;
    }
    
    /** 
     * Set the Y offset all drawing is translated by
     * <p>
     * This is separate from gc.setTransform since the latter
     * is "advanced" and also we don't want to conflict with its API
     * 
     * @param offY y offset
     */
    public void setOffY(int offY) {
        this.offY = offY;
    }

    /**
     * Get the color allocated for the transparent pixel.
     * @return Color
     */
    public Color getTransparentColor() {
        return transparentColor;
    }
    
    /**
     * Get the color used when drawing operations try to use the transparent pixel.
     * @return Color
     */
    public Color getSubstituteColor() {
        return substituteColor;
    }

}
