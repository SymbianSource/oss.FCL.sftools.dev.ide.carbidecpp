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

import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.AbstractHintLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.*;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.swt.widgets.Control;

import java.util.List;

public class NonLayoutContentsFigure extends LocalCoordinatesFigure {

	class SingleChildRestrictedWidthLayout extends AbstractHintLayout {
		
		private int getAllowedWidth() {
			Viewport viewport = figureCanvas.getViewport();
			Rectangle clientArea = viewport.getClientArea();
			Insets insets = viewport.getInsets();
			return clientArea.width - insets.getWidth();
		}
		
		protected Dimension calculatePreferredSize(IFigure container, int wHint, int hHint) {
			List children = container.getChildren();
			Check.checkContract(children != null && children.size() == 1);
			Dimension d = ((IFigure) children.get(0)).getPreferredSize(getAllowedWidth(), hHint).getCopy();
			Insets insets = container.getInsets();
			d.expand(insets.getWidth(), insets.getHeight());

			return d;
		}

		public void layout(IFigure container) {
			List children = container.getChildren();
			Check.checkContract(children != null && children.size() == 1);
			IFigure child = (IFigure) children.get(0);
			Rectangle r = container.getClientArea().getCopy();
			Insets insets = container.getInsets();
			r.shrink(insets.getWidth(), insets.getHeight());
			child.setBounds(r);
		}
	}

	private FigureCanvas figureCanvas;
	
	public NonLayoutContentsFigure(ScrollingGraphicalViewer viewer) {
		Control control = viewer.getControl();
		Check.checkContract(control instanceof FigureCanvas);
		this.figureCanvas = (FigureCanvas) control;

		setLayoutManager(new SingleChildRestrictedWidthLayout());
	}
}
