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
import com.nokia.sdt.utils.ImageUtils;
import com.nokia.sdt.utils.drawing.LineBreaker.ITextMeasurer;

import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.*;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.AttributedString;
import java.util.*;

/**
 * Routines for rendering text under various constraints.
 * <p>
 * Currently we assume that IFont is implemented by AwtFont:
 * routines that expect AwtFont take this as argument, and callers
 * cast IFont to AwtFont.  In the future these are the expected places
 * where SWT or other font routines should be added.
 *
 */
public abstract class TextRendering {
    // Tells whether we generate ARGB images (true) or RGB images with a
    // transparent pixel.
    // If this changes to 'true', the display model needs to be updated to use
    // full alpha images.
    // Note, this is very slow.
	private static final boolean SUPPORT_TRANSPARENCY = false;
 
	private static final char ELLIPSIS = '…';
	
    interface ILineHandler {
        void handleLine(TextLayout layout, int x, int y, int lineCounter, boolean wrapped);
    }

    static class AwtFontTextMeasurer implements ITextMeasurer {

    	private AwtFont font;
		private FontRenderContext frc;
		/**
		 * 
		 */
		public AwtFontTextMeasurer(AwtFont font) {
			this.font = font;
			boolean antiAlias = !font.isBitmapped();
			frc = new FontRenderContext(null, antiAlias, false);

		}
		
		/* (non-Javadoc)
		 * @see com.nokia.sdt.utils.drawing.LineBreaker.ITextMeasurer#dispose()
		 */
		public void dispose() {
			
		}
		
		/* (non-Javadoc)
		 * @see com.nokia.sdt.utils.drawing.LineBreaker.ITextMeasurer#getStringExtent(java.lang.String)
		 */
		public Point getStringExtent(String text) {
			TextLayout tl = new TextLayout(text, font.awtFont, frc);
			return new Point((int) tl.getVisibleAdvance(), (int) tl.getAscent());
		}
    	
    }
    
    static class SwtFontTextMeasurer implements ITextMeasurer {

    	private SwtFont font;
		public SwtFontTextMeasurer(SwtFont font) {
			this.font = font;
		}
		
		/* (non-Javadoc)
		 * @see com.nokia.sdt.utils.drawing.LineBreaker.ITextMeasurer#getStringExtent(java.lang.String)
		 */
		public Point getStringExtent(String text) {
			return font.stringExtent(text);
		}
    	
    }
    /**
     * Break the given text into lines wrapped at the given width.
     * @param font the font
     * @param paragraphs paragraphs to break
     * @param widths width in pixels for each line; the last value holds for all lines after
     * widths.length
     * @return array of strings, one per line
     */
    public static String[] breakIntoLines(IFont font, String[] paragraphs, int[] widths) {
    	Check.checkArg(widths != null && widths.length > 0);
    	
    	// For each paragraph, wrap to the given width
    	int widthIdx = 0;
    	List<String> lines = new ArrayList<String>();
    	ITextMeasurer textMeasurer = font instanceof AwtFont ? new AwtFontTextMeasurer((AwtFont) font) 
    		: new SwtFontTextMeasurer((SwtFont)font);
    	for (int i = 0; i < paragraphs.length; i++) {
			String para = paragraphs[i];

    		LineBreaker lineBreaker = new LineBreaker(textMeasurer, para);
    		while (!lineBreaker.empty()) {
    			String line = lineBreaker.getNextLine(widths[widthIdx]);
    			lines.add(line);
    			if (widthIdx + 1 < widths.length)
    				widthIdx++;
    		}
    		
		}
    	
    	return (String[]) lines.toArray(new String[lines.size()]);
    }

    /**
     * Break a string into paragraphs. 
     * @param text
     * @return list of Strings
     */
    public static List breakIntoParagraphs(String text) {
        int index = 0, last = 0;
        List mylist = new ArrayList();
        while (index < text.length()) {
            char c = text.charAt(index);
            if (c == '\n' 
                || c == '\u2028'
                || c == '\u2029') {
                mylist.add(text.substring(last, index));
                last = index+1;
            }
            index++;
        }
        if (last < index)
            mylist.add(text.substring(last, index));
        return mylist;
    }

    /**
     * Break text into paragraphs and lines all fitting into a maximum width.
     * @param font
     * @param text
     * @param width
     * @return
     */
    public static String[] breakIntoLines(IFont font, String text, int width) {
    	List<String> paragraphs = breakIntoParagraphs(text);
    	return breakIntoLines(font, (String[]) paragraphs.toArray(new String[paragraphs.size()]), new int[] { width }); 
    }

    /**
     * Format the given text into a maximum number of lines, each bounded by a distinct width.
     * @param font
     * @param text
     * @param widths maximum pixel width for each line; if less than maxLines, the last width
     * holds for all remaining lines; or null to measure length of text
     * @param flags rendering flags (IFont.XXX, wrapping and ellipis used)
     * @param maxLines maximum number of lines
     * @return array of strings fitting into the given width
     */
    public static String[] formatIntoLines(IFont font, String text, int[] widths, int flags, int maxLines) {
    	// First, break into absolute lines 
    	List list = breakIntoParagraphs(text);
    	String[] allLines;
    	if ((flags & IFont.WRAPPING_MASK) != IFont.WRAPPING_ENABLED || widths == null) {
    		// If no wrapping, each paragraph is on one line
    		allLines = (String[]) list.toArray(new String[list.size()]);
    	} else {
    		// Wrap each paragraph
    		allLines = breakIntoLines(font, 
    			(String[]) list.toArray(new String[list.size()]), 
    			widths);
    	}
    	
		// if we get here, you must want to see how many lines will be generated,
    	// otherwise the answer is a no-brainer
		if (maxLines == 0)
			maxLines = allLines.length;
    	
    	if ((flags & IFont.OVERFLOW_MASK) == IFont.OVERFLOW_ELLIPSIS) {
        	if (allLines.length >= maxLines) {
    			// add ellipsis to last line if it's too long
    			String last = allLines[maxLines - 1];
    			int ellipsisWidth = font.getCharWidth(ELLIPSIS);
    			int lastLineWidth = widths.length < maxLines ? widths[widths.length - 1] : widths[maxLines - 1];
    			if (allLines.length > maxLines || font.stringExtent(last).x > lastLineWidth) {
    				while (last.length() > 0 && font.stringExtent(last).x + ellipsisWidth > lastLineWidth)
    					last = last.substring(0, last.length() - 1);
    				allLines[maxLines - 1] = last + ELLIPSIS; //$NON-NLS-1$
    			}
    		}
    	}
    	
    	String[] lines = new String[Math.min(allLines.length, maxLines)];
    	System.arraycopy(allLines, 0, lines, 0, lines.length);
    	return lines;
    }

    /**
     * Format the given text into a maximum number of lines, all bounded by the same width.
     * @param font
     * @param text
     * @param width maximum pixel width
     * @param flags rendering flags (IFont.XXX, wrapping and ellipis used)
     * @param maxLines maximum number of lines
     * @return array of strings fitting into the given width
     */
    public static String[] formatIntoLines(IFont font, String text, int width, int flags, int maxLines) {
    	return formatIntoLines(font, text, new int[] { width }, flags, maxLines);
    }

    /**
     * Test...
     * @param args
     */
    public static void main(String[] args) {
    	IFont font = FontFactory.createFromFile("../com.nokia.sdt.component.symbian/data/s60/fonts/albi12.9.ttf", 9);
    	String text = args[0];
    	for (int i = 1; i < args.length; i++) {
    		int width = Integer.parseInt(args[i]);
    		System.out.println("for width: " + width);
    		String[] strings = breakIntoLines(font, text, width);
    		for (int j = 0; j < strings.length; j++) {
				System.out.println("line: "+strings[j] + " (" + font.stringExtent(strings[j]).x+")");
				System.out.flush();
			}
		}
    	
    	for (int i = 1; i < args.length; i++) {
    		int width = Integer.parseInt(args[i]);
    		System.out.println("for truncated width: " + width);
    		String[] strings = formatIntoLines(font, text, width, IFont.WRAPPING_ENABLED + IFont.OVERFLOW_ELLIPSIS, 3);
    		for (int j = 0; j < strings.length; j++) {
				System.out.println("line: "+strings[j] + " (" + font.stringExtent(strings[j]).x+")");
				System.out.flush();
			}
		}

    	for (int i = 1; i < args.length; i++) {
    		int width = Integer.parseInt(args[i]);
    		System.out.println("for multi-line widths:");
    		int[] lengths = new int[] { width/2, width, width*2 }; 
    		String[] strings = formatIntoLines(font, text,
    				lengths,
    				IFont.WRAPPING_ENABLED + IFont.OVERFLOW_ELLIPSIS, 5);
    		for (int j = 0; j < strings.length; j++) {
    			int lineWidth = j < lengths.length ? lengths[j] : lengths[lengths.length - 1];
				System.out.println("line ["+lineWidth+"]: "+strings[j] + " (" + font.stringExtent(strings[j]).x+")");
				System.out.flush();
			}
		}

    }
    
    /**
     * Get the extent of the given text when broken into paragraphs,
     * and possibly wrapped to the given bounds.
     * @param font
     * @param text
     * @param bounds
     * @param flags_
     * @param lineGap
     * @return
     */
    public static Point formattedStringExtent(IFont font, String text, final Point bounds, int flags_, final int lineGap) {
        final Point extents = new Point(0, 0);
        
        // Antialiasing doesn't work with bitmapped fonts,
        // since this requires intermediate drawing at a larger
        // size, which doesn't exist in such fonts
        if (font.isBitmapped())
            flags_ = (flags_ & ~IFont.ANTIALIAS_MASK) | IFont.ANTIALIAS_OFF;
        
        final int flags = flags_;

        String[] lines = formatIntoLines(font, text, bounds.x, flags, Integer.MAX_VALUE);

        ILineHandler handler = new ILineHandler() {
            public void handleLine(TextLayout layout, int x, int y, int lineCounter, boolean wrapped) {

                float extentContribX;
                if (layout.isLeftToRight()) {
                    extentContribX = layout.getAdvance();
                } else {
                    extentContribX = bounds.x;
                }

                float extentContribY = (y + layout.getDescent() + layout.getLeading() + lineGap);
                
                // we need to do this here (rather than by asking
                // AWT to make the font boldface) because our fonts
                // do not incorporate bold variants
                if ((flags & IFont.OPTIONS_BOLD) != 0) {
                    extentContribX += 1;
                    if ((flags & IFont.OPTIONS_EXTRABOLD) == IFont.OPTIONS_EXTRABOLD)
                        extentContribY += 1;
                }

                if (wrapped)
                    extents.x = bounds.x;
                else if (extentContribX > extents.x)
                    extents.x = (int) extentContribX;

                extents.y = (int) extentContribY;
            }
        };
        
        iterateLines((AwtFont)font, lines, handler, flags, lineGap);
        
        return extents;
    }
    
    /** Draw text into the GC within the given bounds. 
     * 
     * @param font the font
     * @param gc the graphics context
     * @param text the text to render
     * @param bounds indicates the coordinates delimiting the text box.
     * Should be at least as high as the font. 
     * @param flags combination of OVERFLOW_xxx, DIRECTION_xxx, and DRAW_xxx constants
     * @param lineGap additional pixel gap between lines
     * @param widths if non-null, maximum line widths for each line.  If more
     * lines than entries, those lines use the final width in the array.
     */
    public static void drawFormattedString(final AwtFont font, GC gc, final String text, final Rectangle bounds, int flags_, final int lineGap, int[] widths) {
        BufferedImage image;
        final Point imageExtent;
        
        // Antialiasing doesn't work with bitmapped fonts,
        // since this requires intermediate drawing at a larger
        // size, which doesn't exist in such fonts
        if (font.isBitmapped())
            flags_ = (flags_ & ~IFont.ANTIALIAS_MASK) | IFont.ANTIALIAS_OFF;
        
        final int flags = flags_;

        int maxLines = bounds.height / (font.getHeight() + lineGap);
        if (widths == null && bounds.width > 0)
        	widths = new int[] { bounds.width };
        final String[] lines = formatIntoLines(font, text, widths, flags, maxLines);

        if (bounds.width == 0 && bounds.height == 0) {
            if ((flags & IFont.WRAPPING_MASK) == IFont.WRAPPING_NONE) {
                // need to determine size of text for image
            	int width = 0;
            	int height = 0;
            	for (int i = 0; i < lines.length; i++) {
            		Point extent = font.stringExtent(lines[i]);
            		width = Math.max(width, extent.x);
            		height += extent.y + lineGap;
				}
                imageExtent = new Point(width, height);
                if (imageExtent.x == 0)
                    ++imageExtent.x;
                if (imageExtent.y == 0)
                    ++imageExtent.y;
                image = new BufferedImage(imageExtent.x, imageExtent.y, BufferedImage.TYPE_4BYTE_ABGR);
            }
            else
                return;
        }
        else {
            // use bounding box
            imageExtent = new Point(bounds.width, bounds.height);
            image = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_4BYTE_ABGR);
        }
        
        final Graphics2D g2d = image.createGraphics();
        final Point extents = new Point(0, 0);

        if ((flags & IFont.DRAW_OPAQUE) != 0) {
            Color bg = new Color(gc.getBackground().getRed(), gc.getBackground().getGreen(), gc.getBackground().getBlue());
            g2d.setBackground(bg);
            g2d.clearRect(0, 0, imageExtent.x, imageExtent.y);
        }
        
        Color fg = new Color(gc.getForeground().getRed(), gc.getForeground().getGreen(), gc.getForeground().getBlue());
        g2d.setColor(fg);
        
        ILineHandler handler = new ILineHandler() {
            public void handleLine(TextLayout layout, int x, int y, int lineCounter, boolean wrapped) {

                float extentContribX;
                if (layout.isLeftToRight()) {
                    extentContribX = layout.getAdvance();
                } else {
                    extentContribX = imageExtent.x;
                }
                if (extentContribX > extents.x)
                    extents.x = (int) extentContribX;
                            
                int dx, dy;               
                
                if ((flags & IFont.HORIZONTAL_ALIGN_MASK) == IFont.HORIZONTAL_ALIGN_RIGHT) {
                    dx = (int) (imageExtent.x - layout.getAdvance());
                }
                else if ((flags & IFont.HORIZONTAL_ALIGN_MASK) == IFont.HORIZONTAL_ALIGN_CENTER) {
                    dx = (int) ((imageExtent.x - layout.getAdvance()) / 2);
                } else {
                    dx = x;
                }
               	
                int heightTotalLines = 0;
                int heightBottomLines = 0;
                int heightCenterLines = 0;
                	
                for (int counter = 0; counter < lines.length; counter++) {
                	Point extent = font.stringExtent(lines[counter]);
                	heightTotalLines += extent.y + lineGap;
                	if (counter >= lineCounter) {
                		heightBottomLines += extent.y + lineGap;
                	} else {
                		heightCenterLines += extent.y + lineGap;
                	}
    			}   
                if (heightCenterLines == 0 && heightTotalLines != 0){
                	heightCenterLines = heightTotalLines;
                }
               
                if ((flags & IFont.VERTICAL_ALIGN_MASK) == IFont.VERTICAL_ALIGN_BOTTOM) {
                    dy = (int) (imageExtent.y - heightBottomLines);
                }
                else if ((flags & IFont.VERTICAL_ALIGN_MASK) == IFont.VERTICAL_ALIGN_CENTER) {
                   	dy = (int) (((imageExtent.y - heightTotalLines)/ 2) + heightCenterLines);
                } else {
                    dy = y;
                }

                layout.draw(g2d, dx, dy);                
                
                // we need to do this here (rather than by asking
                // AWT to make the font boldface) because our fonts
                // do not incorporate bold variants
                if ((flags & IFont.OPTIONS_BOLD) != 0) {
                    layout.draw(g2d, dx+1, dy);
                    if ((flags & IFont.OPTIONS_EXTRABOLD) == IFont.OPTIONS_EXTRABOLD)
                        layout.draw(g2d, dx, dy+1);
                }
                
                extents.y = y;
            }
        };
        
        iterateLines(font, lines, handler, flags, lineGap);

        org.eclipse.swt.graphics.Image img = ImageUtils.convertAwtImage(Display.getDefault(), 
                image);

        // manually composite with SWT pattern, if used
        org.eclipse.swt.graphics.Pattern pattern = gc.getForegroundPattern();
        if (pattern != null) {
            // make a temporary image of the same size as the text,
            // and fill it with the pattern
            ImageData data = img.getImageData();
            ImageData patternData = new ImageData(data.width, data.height, data.depth, data.palette);
            Image patimg = new Image(gc.getDevice(), patternData); 
            GC patGC = new GC(gc.getDevice(), patimg);
            patGC.setBackgroundPattern(pattern);
            patGC.fillRectangle(img.getBounds());
            patGC.dispose();
            
            ImageData patData = patimg.getImageData();
            
            // now, change the colors of the text image
            // to match those of the pattern
            for (int y = 0; y < data.height; y++) {
                for (int x = 0; x < data.width; x++) {
                    // preserves existing alpha value
                    data.setPixel(x, y, patData.getPixel(x, y));
                }
            }
            img.dispose();
            img = new Image(gc.getDevice(), data);
            patimg.dispose();
        }

        if (!SUPPORT_TRANSPARENCY) {
            // now blend the RGBA image with the background
            // or make it into a transparent-pixel model
            Image transparentImage = ImageUtils.flattenAlphaMaskedImage(
                    gc.getDevice(), img, gc.getBackground(), 
                    (flags & IFont.ANTIALIAS_MASK) == IFont.ANTIALIAS_ON,
                    true /*transparent*/);
            img.dispose();
            img = transparentImage;
        }
        
        gc.drawImage(img, bounds.x, bounds.y);
        
        img.dispose();
    }

    /** 
     * Using AWT, iterate the text, breaking it into physical
     * lines of text, for use by a callback.
     * 
     * @param lines the lines to render
     * @param handler callback for each physical line of text
     * @param flags the IFontConstants.xxx flags controlling rendering
     * @param lineGap extra gap between lines
     * @see IFontConstants 
     */
    protected static void iterateLines(AwtFont font, String[] lines, ILineHandler handler, int flags, int lineGap) {
        if (handler == null)
            throw new NullPointerException();
        
        if (lines.length == 0)
            return;

        // for a bitmap render, we don't need the GC's info
        AffineTransform tr = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(tr, 
                (flags & IFont.ANTIALIAS_MASK) == IFont.ANTIALIAS_ON, 
                false);

        Point pen = new Point(0, 0);
       
        for (int i = 0; i < lines.length; i++) {
			String line = lines[i];

            AttributedString attrText = new AttributedString(line);
            attrText.addAttribute(TextAttribute.FONT, font.awtFont);

            if ((flags & IFont.OPTIONS_UNDERLINE) != 0)
                attrText.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

            if ((flags & IFont.OPTIONS_STRIKETHROUGH) != 0)
                attrText.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);

            TextLayout layout = new TextLayout(attrText.getIterator(), frc);
            
            pen.y += layout.getAscent() + layout.getDescent() + layout.getLeading();

            if ((flags & IFontConstants.VERTICAL_ALIGN_MASK) != IFontConstants.VERTICAL_ALIGN_TOP) { 
            	handler.handleLine(layout, (int)pen.x, Math.round(pen.y), i+1, false);
            } else {
            	handler.handleLine(layout, (int)pen.x, Math.round(pen.y), -1, false);
            }

            pen.y += lineGap;
        }
    }

    /** 
     * Using AWT, iterate the text, breaking it into physical
     * lines of text, for use by a callback.
     * 
     * @param text incoming text
     * @param bounds wrapping bounds, in pixels
     * @param handler callback for each physical line of text
     * @param flags the IFontConstants.xxx flags controlling rendering
     * @param lineGap extra gap between lines
     * @see IFontConstants 
     */
    protected static void iterateLines__old(AwtFont font, final String text, final Point bounds, ILineHandler handler, int flags, int lineGap) {
    	if (text == null)
    		return;
    	
        if (handler == null || bounds == null)
            throw new NullPointerException();
        
        if (text.length() == 0)
            return;

        // for a bitmap render, we don't need the GC's info
        AffineTransform tr = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(tr, 
                (flags & IFont.ANTIALIAS_MASK) == IFont.ANTIALIAS_ON, 
                false);

        // break text into paragraphs because AWT routines expect
        // this for BIDI analysis
        Point pen = new Point(0, 0);
        List lines = breakIntoParagraphs(text);
        
        boolean wrapped = false;
        for (Iterator iter = lines.iterator(); iter.hasNext();) {
            String line = (String) iter.next();

        	// If ellipsized, do that here.  This is a very stupid check
            // and would be better done line-by-line, if we could
            // extract that info from the TextLayout.
            
        	if ((flags & IFont.OVERFLOW_MASK) == IFont.OVERFLOW_ELLIPSIS) {
        		// Need to determine size of text for image.
        		//
        		// This routine can only handle one long line,
        		// so we need to fudge things to guess where to stop
        		// when wrapping is enabled.
        		Point lineExtent;
        		lineExtent = font.stringExtent(line);
        		lineExtent.y += lineGap;
        		
        		int nLines = 1;
        		if ((flags & IFontConstants.WRAPPING_ENABLED) != 0)
        			nLines = (bounds.y / lineExtent.y);
        		
        		int maxLength = bounds.x * nLines;
        		
        		// fudge factor for wrap positions
        		maxLength -= (nLines - 1) * lineExtent.y;
        		
        		// now add ellipsis and truncate (TODO: fix very slow algorithm)
        		if (lineExtent.x > maxLength) {
        			final String ellipsis = "…"; //$NON-NLS-1$
            		Point ellipsisExtent = font.stringExtent(ellipsis);
            		do {
            			line = line.substring(0, line.length() - 1);
            		} while (font.stringExtent(line).x + ellipsisExtent.x > maxLength && line.length() > 0);
            		line += ellipsis;
        		}
        	}
        	

            AttributedString attrText = new AttributedString(line);
            attrText.addAttribute(TextAttribute.FONT, font.awtFont);

            if ((flags & IFont.OPTIONS_UNDERLINE) != 0)
                attrText.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

            if ((flags & IFont.OPTIONS_STRIKETHROUGH) != 0)
                attrText.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);

            if ((flags & IFont.WRAPPING_MASK) == IFont.WRAPPING_NONE) {
                // do not wrap text
           
                TextLayout layout = new TextLayout(attrText.getIterator(), frc);
                
                pen.y += layout.getAscent() + layout.getDescent() + layout.getLeading();

                handler.handleLine(layout, (int)pen.x, Math.round(pen.y), -1, false);
                
                pen.y += lineGap;
                
            } else {
                // wrap text into lines
                
                LineBreakMeasurer measurer = new LineBreakMeasurer(attrText.getIterator(), frc);
                float wrappingWidth = bounds.x;
                if (wrappingWidth < 0) {
                    /*UtilsPlugin.log(Logging.newStatus(UtilsPlugin.getDefault(),
                            IStatus.WARNING,
                            "Negative bounds passed to IFont#drawFormattedString()"));*/
                    wrappingWidth = 1;
                }
                
                while (measurer.getPosition() < line.length()) {
                    if (measurer.getPosition() > 0)
                        wrapped = true;
                    
                    TextLayout layout = measurer.nextLayout(wrappingWidth);
        
                    pen.y += layout.getAscent() + layout.getDescent() + layout.getLeading();
        
                    handler.handleLine(layout, (int)pen.x, Math.round(pen.y), -1, wrapped);

                    pen.y += lineGap;
                }
            }

        }

        if (false && (flags & IFont.WRAPPING_MASK) == IFont.WRAPPING_ENABLED) {
            // handle last line (not handled by AWT for some reason)
            TextLayout layout = new TextLayout(" ", font.awtFont, frc); //$NON-NLS-1$

            pen.y += layout.getAscent() + layout.getDescent() + layout.getLeading();

            handler.handleLine(layout, (int)pen.x, Math.round(pen.y), -1, wrapped);
        }

    }

    /**
     * Draw a single line string within the given bounds, rotated to the given angle
     * @param gc the graphics context
     * @param text the text to render
     * @param bounds indicates the coordinates delimiting the text box.
     * Should be at least as high as the font. 
     * @param x the x location for the start of the string, must be within the bounds
     * @param y the y location for the start of the string, must be within the bounds
     * @param angle rotation in radians
     * @param antiAlias true to draw anti-aliased
     */

	public static void drawRotatedString(GC gc, String text, Rectangle bounds, int x, int y, float angle, boolean antiAlias) {

		IFont font = gc.getFont();
		Check.checkArg(font instanceof AwtFont);
		
	    if (font.isBitmapped()) {
	    	antiAlias = false;
	    }
		BufferedImage awtImage = new BufferedImage(bounds.width, bounds.height, 
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = awtImage.createGraphics();
	    Color fg = new Color(gc.getForeground().getRed(), gc.getForeground().getGreen(), gc.getForeground().getBlue());
	    g2d.setColor(fg);
	    
		FontRenderContext frc = new FontRenderContext(null, antiAlias, false);
	    TextLayout tl = new TextLayout(text, ((AwtFont)font).awtFont, frc);
	    Rectangle2D txtBounds = tl.getBounds();
	    g2d.translate(x, y);
		g2d.rotate(angle);
	    tl.draw(g2d, (float)0.0, (float)-txtBounds.getY());
	
	    org.eclipse.swt.graphics.Image swtImage = ImageUtils.convertAwtImage(
	    			gc.getDevice(), awtImage);
	    
	    if (!SUPPORT_TRANSPARENCY) {
	    	org.eclipse.swt.graphics.Image transparentImage = ImageUtils.flattenAlphaMaskedImage(
	    			gc.getDevice(), swtImage, gc.getBackground(), 
	    			antiAlias, true);
	    	swtImage.dispose();
	    	swtImage = transparentImage;
	    }
	    
	    gc.drawImage(swtImage, bounds.x, bounds.y);
	    swtImage.dispose();
	}
}
