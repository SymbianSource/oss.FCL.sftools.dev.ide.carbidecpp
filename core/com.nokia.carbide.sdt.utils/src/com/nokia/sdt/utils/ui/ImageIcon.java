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

import com.nokia.sdt.utils.ImageUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/**
 * This class defines an icon which holds an image.
 * It does not own the image passed in.
 * It has a minimum and maximum size policy.
 *
 */
public class ImageIcon extends Canvas {

    private static final int PADDING = 4;
    private static Point DEFAULT_MINIMUM_SIZE = new Point(32, 32);
    private static Point DEFAULT_MAXIMUM_SIZE = new Point(128, 128);
    private Point minimumSize;
    private Point maximumSize;
    private Image image;
    private Image scaledImage;
	private boolean allowScaling;
    private Point cropSize;

    public ImageIcon(Composite parent, int style) {
    	this(parent, style, true);
    }
    	 
    public ImageIcon(Composite parent, int style, boolean allowScaling) {
        super(parent, style);

        setMinimumSize(new Point(SWT.DEFAULT, SWT.DEFAULT));
        setMaximumSize(new Point(SWT.DEFAULT, SWT.DEFAULT));
        setAllowScaling(allowScaling);
        setCropSize(null);
        setImage(null);
        
        addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
                paint(e);
            }
        });
        addControlListener(new ControlListener() {

            public void controlMoved(ControlEvent e) {
            }

            public void controlResized(ControlEvent e) {
                if (scaledImage != null) {
                    Rectangle bounds = scaledImage.getBounds();
                    setSize(bounds.width + PADDING, bounds.height + PADDING);
                }
            }
            
        });
    }

    public void setCropSize(Point size) {
        this.cropSize = size;
        updateSize();
        redraw();
    }

    /**
	 * @param allowScaling
	 */
	public void setAllowScaling(boolean allowScaling) {
		this.allowScaling = allowScaling;
		updateSize();
		redraw();
	}

	/* (non-Javadoc)
     * @see org.eclipse.swt.widgets.Widget#dispose()
     */
    @Override
    public void dispose() {
        super.dispose();
        if (this.scaledImage != null)
            this.scaledImage.dispose();
        // we don't own the other image
    }
    
    /**
     * @return Returns the image.  This is not owned.
     */
    public Image getImage() {
        return image;
    }
    
    /**
     * Set the image.  This is not owned.
     * @param image
     */
    public void setImage(Image image) {
        this.image = image;
        updateSize();
        redraw();
    }

    /**
     * Set the minimum size the icon will take.  Images below this size
     * are scaled up.
     * @param size null for no limit, or Point(SWT.DEFAULT, SWT.DEFAULT) for default minimum
     */
    public void setMinimumSize(Point size) {
    	if (size != null) {
        	this.minimumSize = new Point(
    			size.x == SWT.DEFAULT ? DEFAULT_MINIMUM_SIZE.x : size.x,
				size.y == SWT.DEFAULT ? DEFAULT_MINIMUM_SIZE.y : size.y
        	);        					
        } else {
        	this.minimumSize = null;
        }
        updateSize();
        redraw();
    }
    
    /**
     * Set the maximum size the icon will take.
     * @param size null for no limit, or Point(SWT.DEFAULT, SWT.DEFAULT) for default maximum
     */
    public void setMaximumSize(Point size) {
        if (size != null) {
        	this.maximumSize = new Point(
    			size.x == SWT.DEFAULT ? DEFAULT_MAXIMUM_SIZE.x : size.x,
				size.y == SWT.DEFAULT ? DEFAULT_MAXIMUM_SIZE.y : size.y
        	);        					
        } else {
        	this.maximumSize = null;
        }
        updateSize();
        redraw();
    }

    /**
     * Update the icon's size so that the image is scaled 1:1
     * if possible, else is scaled up or down to fit in the
     * minimum and maximum bounds.
     */
    private void updateSize() {
        Point imgSize = getImageSize();
        Point curSize;

        curSize = imgSize;
        if (allowScaling) {
        	Point minSize = minimumSize != null ? minimumSize : imgSize;
        	Point maxSize = maximumSize != null ? maximumSize : imgSize;
            curSize = ImageUtils.constrainSizePreservingAspect(imgSize, minSize, maxSize);
        }

        if (cropSize != null) {
            Point curCropSize = new Point(
                    curSize.x * cropSize.x / imgSize.x,
                    curSize.y * cropSize.y / imgSize.y);
            updateScaledImage(imgSize, cropSize, curCropSize);
            setImageSize(curCropSize);
        } else {
        	updateScaledImage(imgSize, imgSize, curSize);
            setImageSize(curSize);
            
        }
        
    }
    
    /**
     * Update the scaled image
     * @param curCropSize 
     */
    private void updateScaledImage(Point imgSize, Point cropSize, Point curSize) {
        if (scaledImage != null) {
            scaledImage.dispose();
        }
        if (image == null)
            scaledImage = null;
        else {
            if (imgSize.equals(curSize) && imgSize.equals(cropSize))
                scaledImage = ImageUtils.copyImage(getDisplay(), image);
            else { 
                if (!cropSize.equals(imgSize)) {
                    Image tmpImage = ImageUtils.cropImage(getDisplay(), image, cropSize);
                    scaledImage = ImageUtils.scaleImage(getDisplay(), tmpImage, curSize);
                } else {
                    scaledImage = ImageUtils.scaleImage(getDisplay(), image, curSize);
                }
            }
        }
    }

    private void setImageSize(Point curSize) {
    	setSize(new Point(curSize.x + PADDING, curSize.y + PADDING));
    }


    /* (non-Javadoc)
     * @see org.eclipse.swt.widgets.Control#computeSize(int, int, changed)
     */
    @Override
    public Point computeSize(int wHint, int hHint, boolean changed) {
        Point size;
        if (scaledImage != null) { 
            Rectangle bounds = scaledImage.getBounds();
            size = new Point(bounds.width, bounds.height);
        } else if (minimumSize != null) {
        	size = new Point(minimumSize.x, minimumSize.y);
        } else {
        	size = new Point(1, 1);
        }
        size.x += PADDING;
        size.y += PADDING;
        return size;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.swt.widgets.Control#getSize()
     */
    @Override
    public Point getSize() {
    	//return computeSize(SWT.DEFAULT, SWT.DEFAULT);
    	return super.getSize();
    }
    
    /**
     * Paint self
     */
    protected void paint(PaintEvent e) {
        // the image should be the same size as the control
        Rectangle ctlBounds = getBounds();
        
        // do not force a background: allows alpha images
        //e.gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
        e.gc.fillRectangle(new Rectangle(0, 0, getSize().x, getSize().y));

        Point borderSize = new Point(ctlBounds.width, ctlBounds.height);
        
        if (scaledImage != null) {
            Rectangle imgBounds = scaledImage.getBounds();
            if (imgBounds.width + PADDING == ctlBounds.width && imgBounds.height + PADDING == ctlBounds.height) {
                // our redraw rect includes the borders
                Rectangle drawRect = new Rectangle(e.x, e.y, e.width, e.height).intersection(
                        new Rectangle(PADDING/2, PADDING/2, imgBounds.width, imgBounds.height));
                e.gc.drawImage(scaledImage, 
                        drawRect.x - PADDING/2, drawRect.y - PADDING/2, drawRect.width, drawRect.height, 
                        drawRect.x, drawRect.y, drawRect.width, drawRect.height);
            } /*else {
                // hmm, something's wrong
                e.gc.drawImage(scaledImage, 
                        0, 0, imgBounds.width, imgBounds.height, 
                        2, 2, ctlBounds.width - PADDING, ctlBounds.height - PADDING);
            }*/
        } else if (minimumSize != null) {
        	borderSize = minimumSize;
        }
         
        e.gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
        e.gc.drawRectangle(0, 0, borderSize.x - 1, borderSize.y - 1);
    }

    /**
     * Get the size of the image
     */
    public Point getImageSize() {
        if (image != null) {
            return new Point(image.getBounds().width, image.getBounds().height);
        } else if (minimumSize != null ){
            return new Point(minimumSize.x, minimumSize.y); 
        } else {
        	return new Point(1, 1);
        }
    }
}
