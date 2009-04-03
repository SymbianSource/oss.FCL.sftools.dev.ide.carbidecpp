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


package com.nokia.sdt.uidesigner.ui.utils;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * 
 *
 */
public class ImageFigureHelper {
	private IFigure figure;
	private Image image;
	
	public ImageFigureHelper(IFigure figure) {
		this.figure = figure;
	}
	
	public void setImage(Image image) {
		disposeImage();
		if (image != null) {
			this.image = new Image(Display.getDefault(), image.getImageData());
			image.dispose();
		}
	}
	
	public void paintImage(Graphics g) {
		if (image == null)
			return;

		Rectangle imageBounds = new Rectangle(image.getBounds());
		Rectangle destBounds = new Rectangle(imageBounds);
		destBounds.setLocation(figure.getBounds().getLocation());
		
		// Account for offsets. Although it looks like borders are accounted for
		// in coordinate transforms, it seems they are not. One suspect
		// is ScalableLayerPane.paintClientArea
		Insets insets = figure.getInsets();
		if (insets != null) {
			destBounds.x += insets.left;
			destBounds.y += insets.top;
		}
		
		if (!destBounds.isEmpty()) {
			g.drawImage(image, imageBounds, destBounds);
		}
	}
	
	public void disposeImage() {
		if (image != null) {
			image.dispose();
			image = null;
		}
	}
}
