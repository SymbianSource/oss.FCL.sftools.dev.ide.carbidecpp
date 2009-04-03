/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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
include("../renderLibrary.js")

function CAknToolbar() {
	this.lafInfo = null;

	// the bounds of the dialog, rel. to instance
	this.dialogBounds = null;	
	// the bounds of the prompt part, rel. to dialogBounds
	this.promptBounds = null;
	// the bounds of the list box part, rel. to dialogBounds
	this.listBounds = null;
}

///////////////////////////////////
// IQueryContainment
///////////////////////////////////
function canContainComponent(instance, component) {
	
	if (isSupportedToolbarComponent(component))
		return null;
	
	return buildSimpleContainmentErrorStatus(
			lookupString("unsupportedComponentErr"), new Array( component.friendlyName ));			
}

CAknToolbar.prototype.canContainComponent = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent);
}

CAknToolbar.prototype.canContainChild = function(instance, child) {
	return canContainComponent(instance, child.component);
}

CAknToolbar.prototype.canRemoveChild = function(instance) {
	return true;
}

CAknToolbar.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent) == null;
}

function isSupportedToolbarComponent(component) {
	if (component.id == "com.nokia.sdt.series60.CAknChoiceList"){
		return true;
	} else if (component.id == "com.nokia.sdt.series60.CAknButton"){
		return true;
	} else {
		return false;
	}
}

///////////////////////////////////
// IVisualAppearance
///////////////////////////////////
CAknToolbar.prototype.draw = function(instance, laf, graphics) {

	var width = instance.properties.size.width - 3;
	var height = instance.properties.size.height - 3;
	var x = 0;
	var y = 0;
	
	// fill 
	graphics.setBackground(laf.getColor("control.button.fill"));
	graphics.setForeground(laf.getColor("screen.background"));
	graphics.setBackground(laf.getColor("control.button.fill")); 
	graphics.fillGradientRectangle(x, y, width, height, true)
	
	// edge
	graphics.setForeground(Colors.getColor(0, 0, 0))
	graphics.drawRectangle(new Rectangle(0, 0, width, height))
	
	// shadows
	graphics.setForeground(laf.getColor("control.shadow.inner"))
	graphics.drawLine(x + 1, y + height + 1, x + width + 1, y + height + 1)
	graphics.drawLine(x + width + 1, y + 1, x + width + 1, y + height + 1)
	
	graphics.setForeground(laf.getColor("control.shadow.outer"))
	graphics.drawLine(x + 2, y + height + 2, x + width + 2, y + height + 2)
	graphics.drawLine(x + width + 2, y + 2, x + width + 2, y + height + 2)

	graphics.setForeground(laf.getColor("EEikColorDialogText"));
	
	if (instance.children.length == 0) {
			//drawTextItem(" No data ", new Point(0, 0), new Point(width + 5, height + 2), laf, graphics);
	}
}

CAknToolbar.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null;
}

////////////////////////////////////////
// IComponentInstancePropertyListener
////////////////////////////////////////
CAknToolbar.prototype.propertyChanged = function(instance, property) {

	var messages = null;
	// insure that there isn't choice list in the small bar
	var properties = instance.properties; 
	if (properties.smallToolbar == true)
	{
		for (var i in instance.children) {
			var child = instance.children[i];
			if (child.component.isOfType("com.nokia.sdt.series60.CAknChoiceList")){
				// TODO: We're setting a property back but should create a status message
				// Not sure how to do that...
				properties.smallToolbar = false;
				instance.forceRedraw();  // refreshes the properties
			}
		}
	}

	if (property == "orientation" || property == "smallToolbar"){
		instance.forceLayout();
	}
	
	return messages;
}


///////////////////////////////////
// IComponentValidator
///////////////////////////////////
CAknToolbar.prototype.validate = function(instance) { 
	
	// Find out if there's a small toolbar
	// If a small toolbar has a choice list, emit error and set back to regular toolbar
	
	var properties = instance.properties;
	var messages = null;
	
	if (properties.smallToolbar == true)
	{
		for (var i in instance.children) {
			var child = instance.children[i];
		}
	}

	return messages;
}

CAknToolbar.prototype.queryPropertyChange = function(instance, propertyPath,
					newValue, laf) {
	return null;
}

////////////////////////////////////
// IComponentInstanceChildListener
////////////////////////////////////

CAknToolbar.prototype.childAdded = function(parent, child){
	parent.parent.forceLayout();
}

CAknToolbar.prototype.childRemoved = function(parent, child){
	parent.parent.forceLayout();
}

CAknToolbar.prototype.childrenReordered = function(parent){
	parent.parent.forceLayout();
}
	
////////////////////////////////////
// ILayout
////////////////////////////////////	
CAknToolbar.prototype.layout = function(instance, laf) {
	
	var isPortrait = laf.getBoolean("is.portrait", true);
	var properties = instance.properties;
	var isHorizontal = properties.orientation == "Horizontal" ? true : false;
	var toolBarMarginPadding = laf.getInteger("control.toolbar.margin", 0);
	var isSmallBar =   properties.smallToolbar == true ? true : false;
	var toolBarWidth = laf.getInteger("control.toolbar.height", 0);
	var toolBarButtonWidth = toolBarWidth - toolBarMarginPadding*2;
	var toolBarButtonHeight = toolBarButtonWidth;
	var toolBarHeight = toolBarWidth;
	
	var numChillens = instance.children.length;
	
	if (isSmallBar){
		// init defaults for small bar, half size of regular toolbar
		toolBarHeight /= 2;
		toolBarWidth /= 2;
		toolBarButtonWidth = toolBarHeight;
		toolBarButtonHeight = toolBarWidth;
		toolBarMarginPadding = 0;
	}
	
	///////////////////////////
	// layout children
	///////////////////////////
	if (isHorizontal){
		// horizontal toolbar grows in width only
		var childX = toolBarMarginPadding; 
		var childY = toolBarMarginPadding;
		if (numChillens > 0) {
			toolBarWidth = toolBarMarginPadding*2 - toolBarMarginPadding; // remove padding for last item added
		}
		for (var i in instance.children) {
			var child = instance.children[i];
			var childProperties = child.properties;
			if (child.component.isOfType("com.nokia.sdt.series60.CAknButton")){
				// button is fixed width

				if (childProperties.size.width != toolBarButtonWidth){
					childProperties.size.width = toolBarButtonWidth;
				}
			} else {
				// other components can be variable width, minimum width preserved
				if (childProperties.size.width < toolBarButtonWidth){
					childProperties.size.width = toolBarButtonWidth;
				}
			}
			
			if (childProperties.size.height != toolBarButtonHeight){
				childProperties.size.height = toolBarButtonHeight; // fixed height
			}
			toolBarWidth += child.properties.size.width + toolBarMarginPadding;	
			childProperties.location.x = childX;
			childProperties.location.y = childY;
			childX += childProperties.size.width + toolBarMarginPadding;
		}

	} else {
		// Vertical orientation, all items are the same size
		var temp = toolBarWidth;
		toolBarWidth = toolBarHeight;
		toolBarHeight = temp;
		var childX = toolBarMarginPadding; 
		var childY = toolBarMarginPadding;
		
		if (numChillens > 0) {
			toolBarHeight = toolBarMarginPadding;
		}
		for (var i in instance.children) {
			var child = instance.children[i];
			var childProperties = child.properties;
			toolBarHeight += child.properties.size.height + toolBarMarginPadding;
			childProperties.size.width = toolBarWidth - toolBarMarginPadding*2;
			childProperties.size.height = toolBarButtonHeight;
			childProperties.location.x = childX;
			childProperties.location.y = childY;
			childY += toolBarButtonHeight + toolBarMarginPadding;	
		}
	}
	
}
