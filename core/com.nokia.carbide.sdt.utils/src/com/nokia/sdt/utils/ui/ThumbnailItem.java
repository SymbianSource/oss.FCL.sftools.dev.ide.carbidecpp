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
package com.nokia.sdt.utils.ui;

import com.nokia.cpp.internal.api.utils.ui.IToolTipLabelProvider;
import com.nokia.sdt.utils.drawing.*;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.*;

import java.lang.ref.WeakReference;

class ThumbnailItem extends Canvas implements PaintListener, MouseListener {
	private final int margin = 5;
	private Object element;
	private ThumbnailGridViewer viewer;
	private boolean selected;

	WeakReference<ImageData> imageDataRef;
	private String text;
	private Point textSize;
	Point imageSize;
	private final int index;
	private ILabelProvider labelProvider;
	private SwtFont itemFont;
	
	/**
	 * @param parent
	 * @param style
	 */
	public ThumbnailItem(final ThumbnailGridViewer viewer, ILabelProvider labelProvider, SwtFont itemFont, Object element, int index) {
		super(viewer.getComposite(), SWT.NONE);
		this.viewer = viewer;
		this.labelProvider = labelProvider;
		this.element = element;
		this.itemFont = itemFont;
		this.index = index;
		this.imageDataRef = null;
		this.text = null;
		this.imageSize = null;
		this.textSize = null;
		this.selected = false;
		
		addPaintListener(this);
		addMouseListener(this);
		addControlListener(new ControlListener() {

			public void controlMoved(ControlEvent e) {
				//ensureVisibleItemValid(Item.this);
			}

			public void controlResized(ControlEvent e) {
				//ensureVisibleItemValid(Item.this);
			}
			
		});

		setData(ThumbnailWithDescriptionComposite.NAME_KEY, 
				labelProvider.getText(getElement()));
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 */
	@Override
	public void dispose() {
		removePaintListener(this);
		removeMouseListener(this);
		super.dispose();
	}
	
	public void paintControl(PaintEvent e) {
		if (isDisposed())
			return;

		Point size = getSize();
		Display display = ((Widget) e.getSource()).getDisplay();
		
		int colorID = SWT.COLOR_LIST_BACKGROUND;
		e.gc.setBackground(display.getSystemColor(colorID));
		
		boolean focused = viewer.keyHandlingWidget.isFocusControl() || viewer.composite.isFocusControl();
		if (selected) {
			if (focused)
				colorID = SWT.COLOR_LIST_SELECTION;
			else
				colorID = SWT.COLOR_WIDGET_NORMAL_SHADOW;
		}
		e.gc.setBackground(display.getSystemColor(colorID));
		e.gc.fillRectangle(0, 0, size.x, size.y);
		
		int maxImageHeight = viewer.layout.maxImageHeight;
		if (selected) {
			e.gc.fillRectangle(size.x / 2 - imageSize.x / 2 - 1, 1, imageSize.x + 2, maxImageHeight + 2);
		}
		if (imageDataRef != null) { 
			int xoff = size.x / 2 - imageSize.x / 2;
			int yoff = margin + maxImageHeight / 2 - imageSize.y / 2;
			
			boolean dispose = false;
			Image image = null;
			ImageData imageData = imageDataRef.get();
			if (imageData == null) {
				image = labelProvider.getImage(getElement());
				imageData = image.getImageData();
				imageDataRef = new WeakReference<ImageData>(imageData);
			} else {
				image = new Image(getDisplay(), imageData);
				dispose = true;
			}
			
			e.gc.drawImage(image, xoff, yoff);
			
			if (dispose) {
				image.dispose();
			}
		}
		
		if (selected) {
			e.gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_LIST_SELECTION_TEXT));
			e.gc.fillRectangle(size.x / 2 - textSize.x / 2 - 1, maxImageHeight + margin - 1,
					textSize.x + 2, textSize.y + 2);
		}
		drawLabel(e.gc, text, size.x / 2, maxImageHeight + margin);
		
		/*
		// draw the selection rectangle 
		if (isMultiSelect() && focused && getIndex() == cursorIndex) {
			e.gc.setForeground(selected ? 
					display.getSystemColor(SWT.COLOR_LIST_BACKGROUND) :
						display.getSystemColor(SWT.COLOR_LIST_FOREGROUND));
			e.gc.setLineDash(new int[] {1, 1});
			e.gc.drawRectangle(0, 0, size.x - 1, size.y - 1);
		}
		*/
	}
		
	public void mouseDown(MouseEvent e) {
		if (isDisposed())
			return;
		
		viewer.keyHandlingWidget.forceFocus();
		boolean additive = viewer.isMultiSelect() && (e.stateMask & SWT.CTRL) != 0;
		boolean range = viewer.isMultiSelect() && (e.stateMask & SWT.SHIFT) != 0;
		if (range) {
			getViewer().selectRangeTo(getIndex(), additive);
		} else {
			if (additive) {
				if (!isSelected())
					getViewer().selectItem(getIndex(), true, true);
				else
					getViewer().unselectIndex(getIndex());
			} else {
				getViewer().selectItem(getIndex(), false, true);
			}
		}
		
		
	}
	
	public void mouseDoubleClick(MouseEvent e) {
		// ignore here
	}

	public void mouseUp(MouseEvent e) {
	}

	/**
	 * Draw the label, using word wrapping.  Each line is centered.
	 */
	protected void drawLabel(GC gc, String text, int x, int y) {
		Point size = getSize();
		SwtFont font = new SwtFont(gc.getFont());
		String[] strings = TextRendering.breakIntoLines(font, text, size.x - margin*2);
		for (String str : strings) {
			Point extent = font.stringExtent(str);
			gc.drawString(str, x - extent.x / 2, y);
			y += extent.y;
		}
		font.dispose();

	}

	/**
	 * Update the item's selected state.
	 * @param b
	 */
	public void setSelected(boolean b) {
		if (isDisposed())
			return;
		if (b != selected) {
			selected = b;
			//if (b)
			//	forceFocus();
		}
		redraw();
	}
	/**
	 * @return Returns the selected.
	 */
	public boolean isSelected() {
		return selected;
	}

	public Object getElement() {
		return element;
	}
	
	public int getIndex() {
		return index;
	}
	
	public Point computeSize(int wHint, int hHint, boolean changed) {
		if (changed) {
			imageDataRef = null;
			imageSize = null;
			text = null;
			textSize = null;
		}
		Point computedImageSize = imageSize;
		if (imageSize == null) {
			ImageData imageData = null;
			if (imageDataRef != null) {
				imageData = imageDataRef.get();
			}
			if (imageData == null) {
				Image image = labelProvider.getImage(getElement());
				if (image != null) {
					imageData = image.getImageData();
					imageDataRef = new WeakReference<ImageData>(imageData);
				}
			}
			if (imageData == null) {
				computedImageSize = new Point(ThumbnailGridViewer.DEFAULT_WIDTH, ThumbnailGridViewer.DEFAULT_HEIGHT);
			}
			else {
				imageSize = new Point(imageData.width, imageData.height);
				computedImageSize = imageSize;
			}
			// image size influence text size
			textSize = null;
		}
		//if (image != null) {
		//	System.out.println("image size: " + imageSize);
		//}
		if (textSize == null) {
			if (text == null) {
				text = labelProvider.getText(getElement());
			}
			if (text == null || itemFont == null) {
				textSize = new Point(0, 0);
				text = "";
			} else {
				int textWidth = Math.max(ThumbnailGridViewer.DEFAULT_WIDTH, computedImageSize.x) - margin*2;
				String[] lines = TextRendering.formatIntoLines(itemFont, text, textWidth, IFontConstants.WRAPPING_ENABLED, Integer.MAX_VALUE);
				textSize = new Point(textWidth, lines.length * itemFont.getHeight());
			}
		}

		// and update tooltip
		String tooltip = null;
		if (labelProvider instanceof IToolTipLabelProvider) {
			tooltip = ((IToolTipLabelProvider) labelProvider).getToolTipText(getElement());
		}
		setToolTipText(tooltip);
		
		Point size = new Point(Math.max(textSize.x, computedImageSize.x) + margin*2, 
				computedImageSize.y + margin*2 + textSize.y);
		return size;
	}
	
	/**
	 * @return Returns the viewer.
	 */
	public ThumbnailGridViewer getViewer() {
		return viewer;
	}
}