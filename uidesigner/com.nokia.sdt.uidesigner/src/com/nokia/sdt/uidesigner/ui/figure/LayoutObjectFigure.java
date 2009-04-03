/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.uidesigner.ui.figure;

import org.eclipse.draw2d.*;

import java.util.Iterator;
import java.util.List;

import com.nokia.sdt.uidesigner.ui.editparts.IImageProvider;
import com.nokia.sdt.uidesigner.ui.utils.ImageFigureHelper;

/**
 * 
 *
 */
public class LayoutObjectFigure extends LocalCoordinatesFigure {

	private ImageFigureHelper helper;
	private IImageProvider imageProvider;
	private boolean dirty;

	public LayoutObjectFigure(IImageProvider imageProvider) {
		super();
		setOpaque(true); // enables hit testing
		setLayoutManager(new FreeformLayout());
		helper = new ImageFigureHelper(this);
		this.imageProvider = imageProvider;
		dirty = true;
	}

	protected void paintFigure(Graphics graphics) {
		if (dirty) {
			helper.setImage(imageProvider.getImage());
			dirty = false;
		}
		helper.paintImage(graphics);
	}

	public void removeNotify() {
		super.removeNotify();
		helper.disposeImage();
	}

	public void repaint() {
		dirty = true;
		List children = getChildren();
		for (Iterator<IFigure> iter = children.iterator(); iter.hasNext();) {
			iter.next().repaint();
		}
		super.repaint();
	}
}
