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

import com.nokia.sdt.displaymodel.IDisplayModel.LayoutAreaConfiguration;
import com.nokia.sdt.ui.skin.ISkin;
import com.swtdesigner.ResourceManager;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

import java.util.Iterator;

/**
 * 
 *
 */
public class LayoutContentsFigure extends LocalCoordinatesFigure {
	public class LayoutFigure extends LocalCoordinatesFigure {
		private Insets insets;
		private LayoutAreaConfiguration layout;
		
		public LayoutFigure() {
			setBackgroundColor(ColorConstants.button);
			setOpaque(true);
			setLayoutManager(new StackLayout());
			addMouseListener(new MouseListener.Stub() { 
				public void mousePressed(MouseEvent me) {
					repaint();
				}
			});
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.draw2d.Figure#getInsets()
		 */
		public Insets getInsets() {
			if (insets == null)
				return super.getInsets();
			return insets;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.draw2d.Figure#getPreferredSize(int, int)
		 */
		public Dimension getPreferredSize(int wHint, int hHint) {
			return getSize();
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
		 */
		protected void paintFigure(Graphics g) {
			super.paintFigure(g);
			if (layout != null && layout.getSkin() != null) {
				ISkin skin = layout.getSkin();
				Image image = skin.getImage();
				if (image != null) {
					Point origin = getLocation();
					g.drawImage(image, origin.x, origin.y);
					for (Iterator iter = skin.getHotzones().iterator(); iter.hasNext();) {
						ISkin.Hotzone hotzone = (ISkin.Hotzone) iter.next();
						if (hotzone.isSticky() && (hotzone.getCurrentState() != null)) {
							Image hotzoneImg = hotzone.getCurrentState().getImage();
							if (hotzoneImg != null) {
								org.eclipse.swt.graphics.Rectangle r = hotzone.getBounds();
								Rectangle bounds = 
									new Rectangle(r.x + origin.x, r.y + origin.y, r.width, r.height);
								Rectangle imageBounds = new Rectangle(hotzoneImg.getBounds());
								g.drawImage(hotzoneImg, new Rectangle(0, 0, imageBounds.width, imageBounds.height), bounds);
							}
						}
					}
				}
			}
		}

		public void setLayout(LayoutAreaConfiguration layout) {
			this.layout = layout;
			org.eclipse.swt.graphics.Point layoutSize = layout.getSize();
            //[[[
            // This code is copied from FlyoutPaleteComposite.RotatedTitleLabel. 
            // Resizing something to the same size doesn't trigger any events,
            // but in the case of changing skins, we do want to know soemthing
            // changed in order for related recalcuations/layout to happen,
            // even in the case where the skin itself is the same size.
			setSize(layoutSize.x+1, layoutSize.y+1);
            //]]]
			setSize(layoutSize.x, layoutSize.y);
			int top = 0;
			int left = 0;
			int bottom = 0;
			int right = 0;
			ISkin skin = layout.getSkin();
			if (skin != null) {
				org.eclipse.swt.graphics.Rectangle editorArea = skin.getEditorAreaBounds();
				/**
				 * Allow for the border width in the root container figure
				 * @see RootContainerLayoutFigure#RootContainerLayoutFigure(IImageProvider)
				 */
				top = editorArea.y - 2;
				left = editorArea.x - 2;
				bottom = layoutSize.y - top - editorArea.height - 3;
				right = layoutSize.x - left - editorArea.width - 3;
			}
			insets = new Insets(top, left, bottom, right);
		}

	}

	private LayoutFigure layoutFigure;
	private Insets insets;
	private ISkin currentSkin;
	private static final int DEFAULT_TOP_INSET = 20;

	public LayoutContentsFigure(final EditPartViewer viewer) {
		setBackgroundColor(ColorConstants.button);
		setOpaque(true);
		layoutFigure = new LayoutFigure();
		FlowLayout layout =  new FlowLayout(true);
		layout.setMajorAlignment(FlowLayout.ALIGN_CENTER);
		layout.setMinorSpacing(50);
		setLayoutManager(layout);
		add(layoutFigure);
	}

	/**
	 * @return Returns the layoutFigure.
	 */
	public LayoutFigure getLayoutFigure() {
		return layoutFigure;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.Figure#getInsets()
	 */
	public Insets getInsets() {
		if (insets == null)
			insets = new Insets(DEFAULT_TOP_INSET, 5, 5, 5);
		return insets;
	}
	
	public void moveVertically(int pixels) {
		insets.top += pixels;
		revalidate();
	}
	
	public void repositionToNormal() {
		insets.top = DEFAULT_TOP_INSET;
		revalidate();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.Figure#getMinimumSize(int, int)
	 */
	public Dimension getMinimumSize(int wHint, int hHint) {
		return getPreferredSize(wHint, hHint);
	}
	
	private void manageHotzonesAsMouseListeners(ISkin skin, boolean add) {
		if (skin == null)
			return;
		
		for (Iterator iter = skin.getHotzones().iterator(); iter.hasNext();) {
			ISkin.Hotzone hotzone = (ISkin.Hotzone) iter.next();
			if (hotzone instanceof MouseListener) {
				MouseListener listener = (MouseListener) hotzone;
				if (add)
					layoutFigure.addMouseListener(listener);
				else
					layoutFigure.removeMouseListener(listener);
			}
		}
	}

	public void setLayout(LayoutAreaConfiguration layout) {
		layoutFigure.setLayout(layout);
		ISkin skin = layout.getSkin();
		if (skin != null) {
			RGB backgroundRGB = skin.getBackgroundRGB();
			if (backgroundRGB != null) {
				setBackgroundColor(ResourceManager.getColor(backgroundRGB));
				getLayoutFigure().setBackgroundColor(getBackgroundColor());
			}
			manageHotzonesAsMouseListeners(currentSkin, false);
			currentSkin = skin;
			manageHotzonesAsMouseListeners(currentSkin, true);
		}
	}

}
