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

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;

import com.nokia.cpp.internal.api.utils.core.Check;

import java.util.HashMap;

/**
 * This layout class arranges all its items in a row-major grid of 
 * boxes of the same size.  It supports virtual items, meaning it does not
 * actually constrain its size to the items in the composite, because there
 * may be a smaller number present than actually addressable.  Thus, the
 * Items present are the sole determiner of the maximum size of any Item.
 * That maximum size determines the number of rows and columns in the grid.
 * A given Item's #getIndex() is used to determine its position in the grid.
 *
 */
class ThumbnailItemLayout extends Layout {

	private int defaultColumns;
	private int spacing;
	int maxHeight;
	int maxWidth;
	int columns;
	int rows;
	int maxImageHeight;
	private boolean needsLayout;
	private int newMaxWidth;
	private int newMaxHeight;
	private int newMaxImageHeight;
	//private int newColumns;
	//private int newRows;
	private HashMap<Widget, Point> sizeMap;
	private int childCount;
	private Point scrollOrigin;
	int visibleRows;

	public ThumbnailItemLayout(int default_cols, int spacing) {
		this.sizeMap = new HashMap<Widget, Point>();
		this.defaultColumns = default_cols;
		this.spacing = spacing;
		this.needsLayout = true;
		this.childCount = 0;
		this.scrollOrigin = new Point(0, 0);
	}

	/** Set the number of children which will be virtually represented. 
	 * This, combined with an ever-updated idea of the maximum width and height
	 * of any item, determines how many rows are considered available.  */
	public void setVirtualChildCount(int count) {
		this.childCount = count;
	}
	
	/**
	 * Set the offset in which to scroll.
	 * @param origin
	 */
	public void setScrollOrigin(Point origin) {
		this.scrollOrigin = origin;
	}
	
	/** Determine the maximum sizes based on the children present */
	private void getNewLayoutInfo(Composite composite, int wHint, int hHint, boolean flush) {
		
		int maxY = ThumbnailGridViewer.DEFAULT_HEIGHT;
		int maxX = ThumbnailGridViewer.DEFAULT_WIDTH;
		int maxImg = 0;
		for (Control control : composite.getChildren()) {
			Point itemSize = flush ? null : sizeMap.get(control);
			if (itemSize == null) {
				itemSize = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				sizeMap.put(control, itemSize);
			}
			if (control instanceof ThumbnailItem) {
				maxImg = Math.max(((ThumbnailItem) control).imageSize.y, maxImg);
			}
			maxY = Math.max(maxY, itemSize.y);
			maxX = Math.max(maxX, itemSize.x);
		}
		newMaxWidth = maxX + spacing * 2;
		newMaxHeight = maxY + spacing * 2;
		newMaxImageHeight = maxImg;
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Layout#computeSize(org.eclipse.swt.widgets.Composite, int, int, boolean)
	 */
	@Override
	protected Point computeSize(final Composite composite, final int wHint, final int hHint, final boolean flushCache) {
		if (flushCache) {
			sizeMap.clear();
		}

		BusyIndicator.showWhile(composite.getDisplay(), new Runnable() {

			public void run() {
				getNewLayoutInfo(composite, wHint, hHint, flushCache);
			}
		});

		visibleRows = 1;
		if (hHint != SWT.DEFAULT) {
			visibleRows = (hHint) / newMaxHeight;
			if (visibleRows == 0)
				visibleRows = 1;
		}
		
		if (wHint != SWT.DEFAULT) {
			// gross, need to be pessimistic here since GridLayout steals too much space
			columns = newMaxWidth > 0 ? (wHint + 20) / (newMaxWidth) : 1;
		} else
			columns = defaultColumns;
		
		if (columns <= 0)
			columns = 1;
		
		rows = (childCount + columns - 1) /columns;
		if (rows == 0)
			rows = 1;

		Point size = new Point(newMaxWidth * columns, 
				newMaxHeight * rows);

		// since items are virtual, never shrink sizes
		if (maxWidth < newMaxWidth || newMaxImageHeight < maxImageHeight
				//|| columns < newColumns //|| newRows != rows
				) {
			maxImageHeight = Math.max(maxImageHeight, newMaxImageHeight);
			maxWidth = Math.max(maxWidth, newMaxWidth);
			maxHeight = Math.max(maxHeight, newMaxHeight);
			//columns = newColumns;
			//rows = newRows;
		}
		
		return size;
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Layout#layout(org.eclipse.swt.widgets.Composite, boolean)
	 */
	@Override
	protected void layout(final Composite composite, final boolean flushCache) {
		//System.out.println(this+"-> layout");
		//new Exception().printStackTrace(System.out);
		
		if (flushCache || needsLayout) {
			final Point size = composite.getSize();
			BusyIndicator.showWhile(composite.getDisplay(), new Runnable() {

				public void run() {
					getNewLayoutInfo(composite, size.x, size.y, flushCache);
				}
			});

			maxImageHeight = Math.max(maxImageHeight, newMaxImageHeight);
			maxWidth = Math.max(maxWidth, newMaxWidth);
			maxHeight = Math.max(maxHeight, newMaxHeight);
			
			
			//columns = newColumns;
			//rows = newRows;
		
			visibleRows = size.y / maxHeight;
			
			if (maxWidth > 0)
				columns = size.x / maxWidth;
			else
				columns = 1;
			
			if (columns == 0)
				columns = 1;
			
			rows = (childCount + columns - 1) /columns;
			if (rows == 0)
				rows = 1;
			
			sizeMap.clear();
		}
		
		
		//int x = spacing, y = spacing, col = 0;
		
		for (Control control : composite.getChildren()) {
			Check.checkState(control instanceof ThumbnailItem);
			ThumbnailItem item = (ThumbnailItem) control;
			/*
			Point itemSize = sizeMap.get(item);
			if (itemSize == null) {
				itemSize = item.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				sizeMap.put(item, itemSize);
			}*/
			
			int x, y;
			if (columns > 0) {
				y = spacing + (item.getIndex() / columns) * maxHeight; 
				x = spacing + (item.getIndex() % columns) * maxWidth; 
			} else {
				x = spacing;
				y = spacing;
			}
			
			item.setBounds(x - scrollOrigin.x, y - scrollOrigin.y, 
					maxWidth - spacing * 2, maxHeight - spacing * 2);
			
			/*
			x += maxWidth;
			col++;
			if (col >= columns) {
				col = 0;
				x = spacing;
				y += maxHeight;
			}*/
		}
		
		needsLayout = false;
	}

	/**
	 * Get the item index which will appear at the given point
	 * @param point
	 * @return 0-based index
	 */
	public int getIndexAtPosition(Point point) {
		if (maxWidth == 0 || maxHeight == 0)
			return 0;
		
		int column = point.x / maxWidth;
		int row = point.y / maxHeight;
		return row * columns + column;
	}

	/**
	 * Given an index we want to be visible, adjust it so it aligns with an edge index
	 * (i.e. align it to the start of the row). 
	 * @param index
	 * @return adjusted index
	 */
	public int adjustEdgeIndex(int index) {
		if (columns == 0)
			return index;
		return index - (index % columns);
	}

	/**
	 * @param index
	 * @return
	 */
	public Point getPositionForIndex(int index) {
		if (columns == 0)
			return new Point(0, 0);
		
		int row = index / columns;
		int column = index % columns;
		return new Point(spacing + column * maxWidth, 
				spacing + row * maxHeight);
	}
	
}