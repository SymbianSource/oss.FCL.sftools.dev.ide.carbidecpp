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

import java.util.List;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;

import com.nokia.sdt.uidesigner.ui.editparts.IImageProvider;

public class TransientObjectFigure extends LayoutObjectFigure {

	private boolean noChildClipping;

	public TransientObjectFigure(IImageProvider imageProvider, boolean noChildClipping) {
		super(imageProvider);
		this.noChildClipping = noChildClipping;
	}

	protected void paintClientArea(Graphics g) {
		if (getChildren().isEmpty())
			return;

		if (noChildClipping) {
			g.pushState();
			g.translate(getBounds().x + getInsets().left,
					getBounds().y + getInsets().top);
			paintChildren(g);
			g.restoreState();
			g.popState();
		}
		else{
			super.paintClientArea(g);
		}
	}
	
	protected void paintChildren(Graphics g) {
		if (noChildClipping) {
			IFigure child;
			List children = getChildren();
			for (int i = 0; i < children.size(); i++) {
				child = (IFigure) children.get(i);
				if (child.isVisible()) {
					child.paint(g);
				}
			}
		}
		else {
			super.paintChildren(g);
		}
	}
	
}
