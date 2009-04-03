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
package com.nokia.sdt.component.symbian.laf;

/**
 * Common key values for Series 60 layout and rendering look and feel
 * 
 * Used with ILookAndFeel
 * @see com.nokia.sdt.displayModel.ILookAndFeel
 *
 */
public interface Series60LAF {
	
	// Positions
	public static final String CONTEXT_ICON_POSITION = "context.icon.position";
	
	// Dimensions
	public static final String SCREEN_SIZE = "screen.size";
	public static final String CONTEXT_ICON_SIZE = "context.icon.size";
	
	// Rectangles
		// In UI Designer the status pane is the rectangle containing the title
		// area and context icon (when shown) only. The AppUI paints
		// the other typical status area decorations. In landscape mode these
		// items are no longer in a single rectangle at the top of the screen.
		// The filled (or gradient) bar at the top we refer to as status bar 1
		// In landscape mode there can be a second bar at the bottom
	public static final String STATUS_PANE_BOUNDS = "status.pane.bounds";
	public static final String CONTROL_PANE_BOUNDS = "control.pane.bounds";
	public static final String CONTENT_PANE_BOUNDS = "content.pane.bounds";
	public static final String STATUS_BAR1_BOUNDS = "status.bar1.bounds";
	public static final String STATUS_BAR2_BOUNDS = "status.bar2.bounds";
	public static final String NAVIPANE_CONTENT_BOUNDS = "navi.content.bounds";
	
	// Colors
	public static final String SCREEN_BACKGROUND = "screen.background";
	public static final String STATUS_BAR_COLOR = "status.bar.color";
	public static final String STATUS_TITLE_COLOR = "status.title.color";
	public static final String STATUS_BAR_GRADIENT_START = "status.bar.gradient.start";
	public static final String STATUS_BAR_GRADIENT_END = "status.bar.gradient.end";
	public static final String CONTROL_PANE_BACKGROUND = "control.pane.background";
	public static final String CONTROL_PANE_TEXT = "control.pane.text";
	public static final String NAVI_PANE_TEXT = "navi.pane.text";
	public static final String CONTROL_SHADOW_INNER = "control.shadow.inner";
	public static final String CONTROL_SHADOW_OUTER = "control.shadow.outer";

	// Fonts
	public static final String CONTROL_PANE_FONT = "control.pane.font";
	
	// Integer values
	public static final String STATUS_BAR_COUNT = "status.bar.count";
	public static final String CONTROL_PANE_TEXT_MARGIN = "control.pane.text.margin";
	public static final String FORM_DIVIDER_OFFSET_SINGLE = "form.divider.offset.single";
	public static final String FORM_DIVIDER_OFFSET_DOUBLE = "form.divider.offset.double";
	public static final String FORM_INSET = "form.inset";
	public static final String FORM_SINGLE_EXTRA_PADDING = "form.single.extra.padding";
	public static final String FORM_DOUBLE_EXTRA_PADDING = "form.double.extra.padding";
	public static final String EXTRA_PADDING = "extra.padding";
	
	// Boolean flags
	public static final String IS_PORTRAIT = "is.portrait";
	public static final String IS_LANDSCAPE = "is.landscape";
	// is the horizontal bar of the status pane drawn with a gradient?
	public static final String STATUS_BAR_GRADIENT = "status.bar.gradient";
	// is the context icon show?
	public static final String SHOW_CONTEXT_ICON = "show.context.icon";
	public static final String DECORATIONS = "decorations";

}
