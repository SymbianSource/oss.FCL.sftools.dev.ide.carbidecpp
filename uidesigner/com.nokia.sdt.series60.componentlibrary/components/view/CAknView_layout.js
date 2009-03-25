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



include("../containers/containerLibrary.js")
include("../toolbar/toolbar_utils.js")
function CAknViewLayout() {
}

CAknViewLayout.prototype.layout = function(instance, laf) {

	var screenWidth = instance.properties.size.width;
	var screenHeight = instance.properties.size.height;
	var children = instance.children;
	
	var statusArea = getStatusPane(children);
	if (statusArea != null) {
		setBounds(statusArea, laf.getRectangle("status.pane.bounds"));
	}

	// Check for toolbar. The toolbar is a child of a view and depending
	// on the size and orientation of the toolbar, the view needs to be adjusted
	var hasToolBar = false;
	var toolBarRect;
	var toolBar = getToolbar(children);
	if (toolBar) {
		hasToolBar = true;
		toolBarRect = setToolbarLayout(toolBar, laf);
	}
	
	var cba = getControlPane(children);
	if (cba != null) {
		setBounds(cba, laf.getRectangle("control.pane.bounds"));
	}

	var contents = getContents(children);
	if (contents != null) {
		var contentPaneRect = laf.getRectangle("content.pane.bounds");
		var contentRect = new Rectangle(contentPaneRect.x, contentPaneRect.y, contentPaneRect.width, contentPaneRect.height);
/*
In later 5.0 versions, toolbar draws over content!!
		if (hasToolBar){
			var toolBarMarginPadding = laf.getInteger("control.toolbar.margin", 0);
			// check if toolBar is vertical or horizontal
			var isHorizontal = toolBar.properties.orientation == "Horizontal" ? true : false;
			if (isHorizontal) {
				contentRect.height -= toolBar.properties.size.height + toolBarMarginPadding + 1;
			} else {
				contentRect.width -= toolBar.properties.size.width + toolBarMarginPadding + 1;
				contentRect.x += toolBar.properties.size.width + toolBarMarginPadding + 1;
			}
		}
*/
		
		setBounds(contents, contentRect);
	}
}

CAknViewLayout.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null;
}

// Sets the toolbar layout and its children as well as adjusts the parent view
// depending on the toolbar orientation. 
// Returns the toolbar's size as Rectangle
function setToolbarLayout(instance, laf) {
	
	var isPortrait = laf.getBoolean("is.portrait", true);
	var properties = instance.properties;
	var isHorizontal = properties.orientation == "Horizontal" ? true : false;
	var toolBarMarginPadding = laf.getInteger("control.toolbar.margin", 0);
	var contentRect = laf.getRectangle("content.pane.bounds");
	var controlPaneRect = laf.getRectangle("control.pane.bounds");
	var screenDim = laf.getDimension("screen.size");
	var toolBarRect = new Rectangle(contentRect.x, contentRect.y, contentRect.width, contentRect.height);
	var toolBarWidth = laf.getInteger("control.toolbar.height", 0);
	var toolBarDim = getToolBarSize(instance, laf);
	toolBarRect.width = toolBarDim.x; // width
	toolBarRect.height = toolBarDim.y; // height
	var toolBarHeight = toolBarWidth;
	var isSmallBar =   properties.smallToolbar == true ? true : false;
	
	if (isHorizontal){
		toolBarRect.x = contentRect.width / 2 - (toolBarRect.width / 2);
		if (isPortrait)
			toolBarRect.y = screenDim.y - controlPaneRect.height - toolBarHeight - toolBarMarginPadding;
		else {
			// landscape
			toolBarRect.y = screenDim.y - toolBarHeight*2;
			if (!isSmallBar){ 
				var sBarRect = laf.getRectangle("status.bar2.bounds");
				toolBarRect.y = screenDim.y - toolBarHeight - sBarRect.height - toolBarMarginPadding;
			}
		}
	} else {
		// vertical toolbar
		toolBarRect.y = screenDim.y / 2 - (toolBarRect.height / 2);
		if (isPortrait){
			 toolBarRect.y += (controlPaneRect.height/2);
		}
		toolBarRect.x = toolBarMarginPadding;
	}
	
	setBounds(instance, toolBarRect); // Set the bounds of the toolbar itself
	return toolBarRect;	
}


