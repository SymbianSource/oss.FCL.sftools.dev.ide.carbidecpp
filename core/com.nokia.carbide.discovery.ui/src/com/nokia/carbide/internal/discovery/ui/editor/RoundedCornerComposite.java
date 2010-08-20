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

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;

class RoundedCornerComposite extends SharedBackgroundComposite {

	private static final int ARC = 9;
	private final Color background;
	private final Color outline;

	public RoundedCornerComposite(Composite parent, Composite backgroundParent, Color outline, Color background) {
		super(parent, backgroundParent);
		this.background = background;
		this.outline = outline;
	}

	@Override
	public void drawBackground(GC gc, int x, int y, int width, int height) {
		super.drawBackground(gc, x, y, width, height);
		if (background != null) {
			gc.setBackground(background);
			gc.fillRoundRectangle(0, 0, width, height, ARC, ARC);
		}
		if (outline != null) {
			gc.setForeground(outline);
			gc.drawRoundRectangle(0, 0, width - 1, height - 1, ARC, ARC);
		}
	}
}
