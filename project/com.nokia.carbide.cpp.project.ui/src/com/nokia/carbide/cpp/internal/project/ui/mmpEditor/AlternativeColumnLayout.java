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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.forms.widgets.ColumnLayoutData;
import org.eclipse.ui.forms.widgets.ILayoutExtension;

import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * Column-oriented layout manager with different behavior (and omitting bugs)
 * of ColumnLayout. Uses ColumnLayoutData for per-control layout parameters.
 * 
 * General behavior of this layout manager:
 * - sizes controls at their preferred size, respecting ColumnLayoutData parameters, if any
 * - fills in columns left to right, top to bottom.
 * - The minimum number of columns is 1 and cannot be changed, but the maximum can.
 * - columns after column zero never extend beyond the client area, so vertical scrolling is preferred
 *   to horizontal scrolling.
 * - this layout manager doesn't implement the "fill in" feature of ColumnLayout. Since columns
 *   wrap to the client height, if there's too many controls the last column will be longer than 
 *   the rest. ColumnLayout would fill in columns, assigning the overflow controls to prior columns
 *   in an attempt to even things out. That may be nice, but it also arbitrarily re-orders, moving
 *   content from the order the user may expect. 
 */
public class AlternativeColumnLayout extends Layout implements ILayoutExtension {
	
	public int horizontalSpacing = 5;
	public int verticalSpacing = 5;
	public int topMargin = 5;
	public int leftMargin = 5;
	public int bottomMargin = 5;
	public int rightMargin = 5;

	public int maxColumns = 3;
	
	private LayoutInfo cachedLayout;
	
	private static class LayoutInfo {
		int availableWidth;
		int availableHeight;
		int numColumns;
		int maxControlWidth;
		int contentWidth;
		int contentHeight;
		Map<Control, Point> controlSizes;
		Column columns[]; // size == numColumns
	}
	
	private static class Column {
		int width;
		int height;
		List<Control> controls;
		List<Rectangle> bounds;
	}

	public AlternativeColumnLayout() {
	}
	
	protected Point computeSize(Composite parent, int wHint, int hHint, boolean flushCache) {
		if (flushCache) {
			cachedLayout = null;
		}
		LayoutInfo li = calculateLayout(parent, wHint, hHint);
		Point result = new Point(li.contentWidth, li.contentHeight);
		return result;
	}
	
	protected void layout(Composite parent, boolean flushCache) {
		if (flushCache) {
			cachedLayout = null;
		}
		Rectangle clientArea = parent.getClientArea();
		LayoutInfo li = calculateLayout(parent, clientArea.width, clientArea.height);
		for (Column column : li.columns) {
			for (int i = 0; i < column.controls.size(); i++) {
				Control control = column.controls.get(i);
				control.setBounds(column.bounds.get(i));
			}
		}
	}
	
	private LayoutInfo calculateLayout(Composite parent, int wHint, int hHint) {
		if (cachedLayout != null) {
			if (cachedLayout.availableWidth == wHint && 
				cachedLayout.availableHeight == hHint) {
				return cachedLayout;
			}
			cachedLayout = null;
		}
		LayoutInfo li = new LayoutInfo();
		li.availableWidth = wHint;
		li.availableHeight = hHint;
		Control[] children = parent.getChildren();
		li.controlSizes = new HashMap<Control, Point>();
		for (int i = 0; i < children.length; i++) {
			Point size = computeControlSize(children[i], SWT.DEFAULT);
			li.controlSizes.put(children[i], size);
			li.maxControlWidth = Math.max(li.maxControlWidth, size.x);
		}
		int numColumns;
		if (wHint == 0) {
			// when wHint is zero we're calculating our minimum width
			numColumns = 1;
		} else if (wHint == SWT.DEFAULT) {
			numColumns = maxColumns;
		} else {
			// constrain columns to avoid horizontal scrolling
			numColumns = (wHint - leftMargin) / (li.maxControlWidth + horizontalSpacing);
		}
		numColumns = Math.max(numColumns, 1);

		while (true) {
			li.numColumns = numColumns;
			li.columns = new Column[numColumns];
			for (int i = 0; i < numColumns; i++) {
				li.columns[i] = new Column();
				li.columns[i].controls = new ArrayList<Control>();
				li.columns[i].bounds = new ArrayList<Rectangle>();
				li.columns[i].height = topMargin;
			}
			if (li.availableHeight > 0) {
				layoutToFixedHeight(children, li);
			} else {
				layoutToEqualControlsPerColumn(children, li);
			}
			if (li.numColumns == 1) {
				break;
			}
			// layout is acceptable if number of columns is 1 or if these conditions hold:
			// a) hHint is not exceeded, or hHint < 0 (SWT.DEFAULT)
			// b) the last column is not overly long relative to
			// the previous column. We define this by whether the delta between the last
			// two columns is bigger than the height of the first item in the last column.
			// That indicates we could have put that control in the previous columns and got
			// a better layout
			int lastColHeight = li.columns[numColumns-1].height;
			if (lastColHeight > topMargin) {
				int penultimateColHeight = li.columns[numColumns-2].height;
				Control firstInLastCol = li.columns[numColumns-1].controls.get(0);
				int firstInLastColHeight = li.controlSizes.get(firstInLastCol).y;
				int heightDelta = lastColHeight - penultimateColHeight;
				if (heightDelta <= firstInLastColHeight) {
					break;
				} else {
					// increase allowed height and try again
					li.availableHeight += firstInLastColHeight + horizontalSpacing;
				}
			} else {
				break;
			}
		}
		// determine used content area
		int usedColumns = 0;
		int maxColHeight = 0;
		for (int i = 0; i < li.numColumns; i++) {
			usedColumns++;
			if (li.columns[i].controls.size() == 0) {
				break;
			}
			maxColHeight = Math.max(maxColHeight, li.columns[i].height);
		}
		li.contentWidth = leftMargin + rightMargin + usedColumns * li.maxControlWidth;
		li.contentHeight = maxColHeight + bottomMargin;
		cachedLayout = li;
		return li;
	}
	
	private void layoutToFixedHeight(Control controls[], LayoutInfo li) {
		Check.checkArg(li.availableHeight > 0);
		int currCol = 0;
		int x = leftMargin;		
		
		for (int i = 0; i < controls.length; i++) {
			Control child = controls[i];
			Point controlSize = li.controlSizes.get(child);

			// check if we can put the current child in the current column without
			// exceeding the client height
			int proposedColHeight = li.columns[currCol].height;
			if (proposedColHeight > topMargin)
				proposedColHeight += verticalSpacing;
			proposedColHeight += controlSize.y;
			if (proposedColHeight >= (li.availableHeight - bottomMargin) &&
				currCol < li.numColumns - 1) {
				currCol++;
				x += li.maxControlWidth + horizontalSpacing;
			} else {
				if (li.columns[currCol].height > topMargin)
					li.columns[currCol].height += verticalSpacing;				
			}
			
			Rectangle r = new Rectangle(0, li.columns[currCol].height, 0, controlSize.y);
			ColumnLayoutData cld = (ColumnLayoutData) child.getLayoutData();
			int alignment = cld != null ? cld.horizontalAlignment : ColumnLayoutData.FILL;
			switch (alignment) {
				case ColumnLayoutData.LEFT :
					r.x = x;
					r.width = controlSize.x;
					break;
				case ColumnLayoutData.FILL :
					r.x = x;
					r.width = li.maxControlWidth;
					break;
				case ColumnLayoutData.CENTER :
					r.x = x + (li.maxControlWidth - controlSize.x)/2;
					r.width = controlSize.x;
					break;
				case ColumnLayoutData.RIGHT :
					r.x = x + li.maxControlWidth - controlSize.x;
					r.width = controlSize.x;
					break;
			}
			li.columns[currCol].controls.add(child);
			li.columns[currCol].bounds.add(r);
			li.columns[currCol].height += controlSize.y;
		}
	}
	
	private void layoutToEqualControlsPerColumn(Control controls[], LayoutInfo li) {
		final int childrenPerCol = (controls.length + li.numColumns - 1)/ li.numColumns;
		int x = 0;
		int controlIndex = 0;
		for (int currCol = 0; currCol < maxColumns; currCol++) {
			if (currCol > 0) {
				x += horizontalSpacing;
			}
			int childrenInCol = 0;
			while (childrenInCol < childrenPerCol && controlIndex < controls.length) {
				Control control = controls[controlIndex++];
				Point controlSize = li.controlSizes.get(control);
				Rectangle r = new Rectangle(0, li.columns[currCol].height, 0, controlSize.y);
				ColumnLayoutData cld = (ColumnLayoutData) control.getLayoutData();
				int alignment = cld != null ? cld.horizontalAlignment : ColumnLayoutData.FILL;
				switch (alignment) {
					case ColumnLayoutData.LEFT :
						r.x = x;
						r.width = controlSize.x;
						break;
					case ColumnLayoutData.FILL :
						r.x = x;
						r.width = li.maxControlWidth;
						break;
					case ColumnLayoutData.CENTER :
						r.x = x + (li.maxControlWidth - controlSize.x)/2;
						r.width = controlSize.x;
						break;
					case ColumnLayoutData.RIGHT :
						r.x = x + li.maxControlWidth - controlSize.x;
						r.width = controlSize.x;
						break;
				}
				li.columns[currCol].controls.add(control);
				li.columns[currCol].bounds.add(r);
				li.columns[currCol].height += controlSize.y;
				if (childrenInCol > 0) {
					li.columns[currCol].height += verticalSpacing;
				}
				++childrenInCol;
			}
			childrenInCol = 0;
			x += li.maxControlWidth;
		}
	}
	
	private Point computeControlSize(Control control, int wHint) {
		ColumnLayoutData cld = (ColumnLayoutData) control.getLayoutData();
		int widthHint;
		int heightHint;
		if (cld != null) {
			widthHint = cld.widthHint;
			heightHint = cld.heightHint;
		} else {
			widthHint = wHint;
			heightHint = SWT.DEFAULT;
		}
		return control.computeSize(widthHint, heightHint);
	}

	public int computeMaximumWidth(Composite parent, boolean changed) {
		Point size = computeSize(parent, SWT.DEFAULT, SWT.DEFAULT, changed);
		return size.x;
	}

	
	public int computeMinimumWidth(Composite parent, boolean changed) {
		Point size = computeSize(parent, 0, SWT.DEFAULT, changed);
		return size.x;
	}
}
