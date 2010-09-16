/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.internal.discovery.ui.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

class SharedBackgroundComposite extends Canvas {

	private final Composite backgroundParent;

	public SharedBackgroundComposite(Composite parent, Composite backgroundParent) {
		super(parent, SWT.NO_BACKGROUND);
		this.backgroundParent = backgroundParent;
		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				Rectangle b = getBounds();
				drawBackground(e.gc, b.x, b.y, b.width, b.height);
			}
		});
	}
	
	@Override
	public void drawBackground(GC gc, int x, int y, int width, int height) {
		Composite relParent = getParent();
		while (relParent != backgroundParent) {
			Rectangle relB = relParent.getBounds();
			x += relB.x;
			y += relB.y;
			relParent = relParent.getParent();
		}

		Image image = backgroundParent.getBackgroundImage();
		Rectangle imageBounds = image.getBounds();
		width = Math.min(width, imageBounds.width - x);
		height = Math.min(height, imageBounds.height - y);
		if (width > 0 && height > 0)
			gc.drawImage(image, x, y, width, height, 0, 0, width, height);
//		gc.drawText(getClass().getSimpleName(), 2, 2);
//		gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_WHITE));
//		gc.drawRectangle(1, 1, width - 2, height - 2);
	}

	@Override
	public void layout() {
		super.layout();
		pack();
	}
}
