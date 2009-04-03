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

import com.nokia.sdt.utils.ImageUtils;

import org.eclipse.swt.graphics.*;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.widgets.Display;

/**
 * This class wraps an SWT font.
 *
 */
public class SwtFont implements IFont {

	private Font font;
	private Image image;
	private org.eclipse.swt.graphics.GC gc;
	private FontMetrics fontMetrics;

	public SwtFont(Font font) {
		this.font = font;
		this.image = new Image(Display.getDefault(), ImageUtils.createStandard32BitImageData(1, 1));
		this.gc = new org.eclipse.swt.graphics.GC(image);
		this.gc.setFont(font);
		this.fontMetrics = gc.getFontMetrics();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.drawing.IFont#dispose()
	 */
	public void dispose() {
		gc.dispose();
		image.dispose();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.drawing.IFont#getAdvanceWidth(char)
	 */
	public int getAdvanceWidth(char ch) {
		return gc.getAdvanceWidth(ch);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.drawing.IFont#getAscent()
	 */
	public int getAscent() {
		return fontMetrics.getAscent();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.drawing.IFont#getAverageCharWidth()
	 */
	public int getAverageCharWidth() {
		return fontMetrics.getAverageCharWidth();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.drawing.IFont#getCharWidth(char)
	 */
	public int getCharWidth(char ch) {
		return gc.getCharWidth(ch);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.drawing.IFont#getDescent()
	 */
	public int getDescent() {
		return fontMetrics.getDescent();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.drawing.IFont#getHeight()
	 */
	public int getHeight() {
		return fontMetrics.getHeight();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.drawing.IFont#getLeading()
	 */
	public int getLeading() {
		return fontMetrics.getLeading();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.drawing.IFont#getSWTFont(org.eclipse.swt.graphics.Device, int)
	 */
	public Font getSWTFont(Device device, int size) {
		return font;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.drawing.IFont#getSize()
	 */
	public float getSize() {
		return font.getFontData()[0].getHeight();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.drawing.IFont#isBitmapped()
	 */
	public boolean isBitmapped() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.drawing.IFont#stringExtent(java.lang.String)
	 */
	public Point stringExtent(String string) {
		return gc.stringExtent(string);
	}
}
