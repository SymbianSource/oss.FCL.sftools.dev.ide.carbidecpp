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
package com.nokia.sdt.uidesigner.ui.utils;

import com.nokia.sdt.utils.ImageUtils;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

public abstract class DrawingUtils {

	/**
	 * Draw an alpha blended rectangle onto the Draw2D graphics surface.
	 * This is aware of scaled contexts so the result always looks physically the same
	 * (ignoring zoom factor).<p>
	 * This falls back to a stipple pattern if alpha blending is not available.
	 * @param graphics
	 * @param rect
	 * @param alpha
	 * @param color
	 */
	public static void drawAlphaBlendedRectangle(Graphics graphics, Rectangle rect, int alpha, Color color) {
		if (ImageUtils.isAlphaBlendingSupported()) {
			graphics.setAlpha(alpha);
			graphics.setBackgroundColor(color);
			graphics.fillRectangle(rect);
		} else {
			// GDI+ not available: draw alternate pattern.
			
			// Ideally, this is an Image with a checkerboard pattern,
			// but Draw2D doesn't fully implement patterns for ScaledGraphics.
			// So instead, we draw lines with alternating on/off pixels.

			graphics.pushState();

			// Don't scale these dots
			double origScale = graphics.getAbsoluteScale();
			
			// watch out: the GDI+ problem will be triggered again if we're already
			// at 1.0, since the underlying graphics is not a ScaledGraphics yet
			if (Math.abs(origScale - 1.0) > 0.001)
				graphics.scale(1.0 / origScale);

			graphics.setLineDash(new int[] { 1 });
			graphics.setForegroundColor(color);

			int offset = 0;
			rect = rect.scale(origScale);
			
			for (int row = 0; row < rect.height; row++) {
				graphics.drawLine(rect.x + offset, rect.y + row, rect.x + rect.width, rect.y + row);
				offset ^= 1;
			}
			
			graphics.popState();
		}
		
	}
	

}
