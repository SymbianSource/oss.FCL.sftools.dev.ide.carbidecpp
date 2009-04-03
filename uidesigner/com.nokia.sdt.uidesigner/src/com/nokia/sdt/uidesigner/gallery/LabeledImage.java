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
package com.nokia.sdt.uidesigner.gallery;

import com.nokia.sdt.utils.ImageUtils;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

class LabeledImage extends Canvas {
	
	static final int FRAME_SIZE = 2;
	static final int SELECTED_FRAME_SIZE = 4;
	static final int MARGIN = FRAME_SIZE + SELECTED_FRAME_SIZE + 2;
	static final int GAP = 10;
	static final double MIN_SCALE = 0.50;
	static final double DEFAULT_SCALE = 0.75;	
	static final double MAX_SCALE = 1.0;
	
	Object modelObject;
	String	text;
		// both images are local copies owned by this control
	Image	fullSize;
	Image   scaledImage;
	boolean selected;
	double currentScale = DEFAULT_SCALE;
	
	private static int DRAW_FLAGS = SWT.DRAW_TAB | SWT.DRAW_DELIMITER;

	public LabeledImage(Composite parent) {
		super(parent, SWT.SHADOW_IN|SWT.SHADOW_OUT);
			
		addPaintListener(new PaintListener(){
			public void paintControl(PaintEvent event) {
				onPaint(event);
			}
		});
		
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if (scaledImage != null) {
					scaledImage.dispose();
					scaledImage = null;
				}
				if (fullSize != null) {
					fullSize.dispose();
					fullSize = null;
				}
			}
		});
	}
	
	public void setModelObject(Object modelObject) {
		this.modelObject = modelObject;
	}
	
	public Object getModelObject() {
		return modelObject;
	}
	
		// The control takes ownership of this image
	public void setImage(Image image) {
		checkWidget();
		fullSize = image;
		cacheScaledImage();
	}
	
	public void setText(String text) {
		checkWidget();
		this.text = text;
	}
	
	public void setSelected(boolean selected) {
		if (selected != this.selected) {
			this.selected = selected;
			redraw();
		}
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public boolean setFocus() {
		boolean hasFocus = super.setFocus();
		if (hasFocus) {
			
		}
		return hasFocus;
	}

	private Point computeSizeAtScale(double scale) {
		int x = 0;
		int y = 0;
		if (fullSize != null) {
			Rectangle imageBounds = fullSize.getBounds();
			int imageWidth = (int) (imageBounds.width * scale);
			int imageHeight = (int) (imageBounds.height * scale);
			x = imageWidth + 2*MARGIN;
			y = imageHeight + 2*MARGIN;
		}
		
		if (!TextUtils.isEmpty(text)) {
			GC gc = new GC(this);
			Point e = gc.textExtent(text, DRAW_FLAGS);
			y += e.y;
			if (fullSize != null) 
				y += GAP;
			gc.dispose();
		} 
		return new Point(x, y);
	}
	
	public void setScaling(double scaling) {
		scaling = pin(MIN_SCALE, scaling, MAX_SCALE);
		if (scaling != currentScale) {
			currentScale = scaling;
			cacheScaledImage();
		}
	}
	
	private Image copyImage(Image srcImage, double scale) {
		Rectangle imageBounds = srcImage.getBounds();
		int width = (int) (imageBounds.width * scale);
		int height = (int) (imageBounds.height * scale);
		Image result = new Image(Display.getDefault(), width, height);
				
	    GC gc = new GC(result);
	    if (ImageUtils.isAlphaBlendingSupported()) {
	    	gc.setAntialias(SWT.ON);
	    }
	    gc.drawImage(srcImage, 
	    		0, 0, imageBounds.width, imageBounds.height,
	    		0, 0, width, height);
	    gc.dispose();	
	    return result;
	}
	
	private void cacheScaledImage() {
		if (scaledImage != null) {
			scaledImage.dispose();
			scaledImage = null;
		}	
		
		if (fullSize != null && currentScale != 1.0) {
			scaledImage = copyImage(fullSize, currentScale);
		}
	}
	
	private double pin(double min, double val, double max) {
		if (val < min)
			return min;
		if (val > max)
			return max;
		return val;
	}

	public Point computeSize(int wHint, int hHint, boolean changed) {
		Point result = computeSizeAtScale(currentScale);
		return result;
	}

	void onPaint(PaintEvent event) {
		GC gc = event.gc;
		Rectangle clientArea = getClientArea();
	//	gc.drawRectangle(clientArea);
		
		int width = clientArea.width;
		Image image = scaledImage != null? scaledImage : fullSize;
		if (image != null) {
			Rectangle imageBounds = image.getBounds();
			width = imageBounds.width;
			int height = imageBounds.height;

			gc.drawImage(image, 0, 0, width, height, 
					MARGIN, MARGIN, width, height);

			gc.setLineWidth(FRAME_SIZE);
			Rectangle borderRect = new Rectangle(
					MARGIN-FRAME_SIZE-1, MARGIN-FRAME_SIZE-1, 
					width+2*FRAME_SIZE, height+2*FRAME_SIZE);
			gc.drawRectangle(borderRect);
			
			if (selected) {
				Color hiliteColor = getDisplay().getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
				gc.setForeground(hiliteColor);
				gc.setLineWidth(SELECTED_FRAME_SIZE);
				borderRect.x -= SELECTED_FRAME_SIZE-1;
				borderRect.y -= SELECTED_FRAME_SIZE-1;
				borderRect.width += SELECTED_FRAME_SIZE+2;
				borderRect.height += SELECTED_FRAME_SIZE+2;
				gc.drawRectangle(borderRect);
			}
			
		}
		if (text != null) {
			if (selected) {
				gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION_TEXT));
				gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION));
			}
			drawStringToFit(text, gc, width);
		}
	}
	
	static final char ELLIPSIS = '\u2026';
	private void drawStringToFit(String text, GC gc, int availableWidth) {
		Point e = gc.textExtent(text, DRAW_FLAGS);
		if (e.x > availableWidth) {
			StringBuffer buf = new StringBuffer(text);
			while (e.x > availableWidth) {
				buf.setLength(buf.length()-2);
				buf.append(ELLIPSIS);
				text = buf.toString();
				e = gc.textExtent(text, DRAW_FLAGS);
			}
		}
		int x = MARGIN + availableWidth/2 - e.x/2;
		int y = getClientArea().height - MARGIN - e.y;
		gc.drawText(text, x, y, DRAW_FLAGS);
	}
}
