/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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


// Get the width and height of the toolbar in a Point.
// instance - the toolbar instance to get the width and height of
// laf - local laf
function getToolBarSize(instance, laf){
	
	props = instance.properties;
	var isSmallBar =   props.smallToolbar == true ? true : false;
	var toolBarMarginPadding = laf.getInteger("control.toolbar.margin", 0);
	var toolBarWidth = laf.getInteger("control.toolbar.height", 0);
	var toolBarHeight = toolBarWidth;
	var isHorizontal = props.orientation == "Horizontal" ? true : false;
	
	if (isSmallBar){
		toolBarMarginPadding = 0;
		toolBarHeight /= 2;
		toolBarWidth /= 2;
	}
	
	var numChillens = instance.children.length;
	var childWidth = 0;
	
	if (isHorizontal) {
		var lastChildWasNull = false;  // for some reason the width is zero after dragging an item in from the main pane
		for (var i in instance.children) {
			var child = instance.children[i];
			var childProperties = child.properties;
			childWidth += childProperties.size.width;
			if (lastChildWasNull == true) {
				lastChildWasNull = false;
				childWidth += childWidth;
			}
			if (childProperties.size.width == 0) {
				lastChildWasNull = true;
			}
		}
		
		childWidth += toolBarMarginPadding * (numChillens + 1);
		
		if (numChillens == 0) {
			// no items, set to default width
			childWidth = laf.getInteger("control.toolbar.height", 0);
		}
		
	} else {
		var lastChildWasNull = false;  // for some reason the height is zero after dragging an item in from the main pane
		childWidth = toolBarHeight;
		toolBarHeight = 0;
		for (var i in instance.children) {
			var child = instance.children[i];
			var childProperties = child.properties;
			toolBarHeight += childProperties.size.height;
			if (lastChildWasNull == true) {
				lastChildWasNull = false;
				toolBarHeight += toolBarHeight;
			}
			if (childProperties.size.height == 0) {
				lastChildWasNull = true;
			}
		}
		// Get default height of items
		
		toolBarHeight += toolBarMarginPadding * (numChillens + 1)
		
		if (numChillens == 0) {
			// no items, set to default width
			toolBarHeight = laf.getInteger("control.toolbar.height", 0);
		}
	}
	
	return new Point(childWidth, toolBarHeight);
}
	
