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
include("StylusPopupItem.js")

function CAknStylusPopUpMenu() {
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
	if (isStylusPopupItemComponent(component))
		return null;
	
	return buildSimpleContainmentErrorStatus(
			lookupString("stylusPopupItemContainmentErr"), new Array( component.friendlyName ));			
}

CAknStylusPopUpMenu.prototype.canContainComponent = function(instance, otherComponent) {
	if (instance.children.length >= 4){
		return buildSimpleContainmentErrorStatus(
			lookupString("maxStylusPopupItemContainmentErr"), new Array( otherComponent.friendlyName ));			
	}
	return canContainComponent(instance, otherComponent);
}

CAknStylusPopUpMenu.prototype.canContainChild = function(instance, child) {
	return canContainComponent(instance, child.component);
}

CAknStylusPopUpMenu.prototype.canRemoveChild = function(instance) {
	return true;
}

CAknStylusPopUpMenu.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent) == null;
}

function isStylusPopupItemComponent(component) {
	return component.id == "com.nokia.sdt.series60.StylusPopupItem";
}

///////////////////////////////////
// IVisualAppearance
///////////////////////////////////
CAknStylusPopUpMenu.prototype.draw = function(instance, laf, graphics) {
	
	var width = instance.properties.size.width - 3;
	var height = instance.properties.size.height - 3;
	var x = 0;
	var y = 0;
	
	// fill 
	graphics.setBackground(getBackgroundColor(instance, laf))
	graphics.fillRectangle(new Rectangle(0, 0, width, height))
	
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
			drawTextItem(" No data ", new Point(0, 0), new Point(width + 5, height + 2), laf, graphics);
	}
}


CAknStylusPopUpMenu.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null;
}

///////////////////////////////////
// IDirectLableEdit
///////////////////////////////////


///////////////////////////////////
// ILayout
///////////////////////////////////
CAknStylusPopUpMenu.prototype.layout = function(instance, laf) {
	
	//Get the rectangle for the screen, we'll put the popup menu in the middle
	var d = laf.getDimension("screen.size");
	var textPadding = 10;
	var numChildren = instance.children.length;
	if (numChildren == 0){
		numChildren = 1; // Leave space for text to indicate no menu items present
	}
	
	var font = laf.getFont("menuitem.font");
	var extent = font.stringExtent("  No data  ");  // get default widest text length
	var menuWidth = extent.x + textPadding;
	var fontHeight = font.getHeight() + 8;
	
	for (var i in instance.children) {
	    // figure out the max width of the text item...
		var child = instance.children[i];
		var childProperties = child.properties;
		if (menuWidth < font.stringExtent(child.properties.textItem).x) {
		    if (font.stringExtent(child.properties.textItem).x > d.x / 2) {
		       // NOTE: I'm not sure about the termination. The popup menu doesn't seem to work
		       // on the emulator on all the resolutions so far, so proper elipsis length is unknown.
		       // However, currently it seems to be 1/2 of screen size.
		    	menuWidth = d.x /2 + 10; // menu width can only be 1/2 of screen width
		    } else {
				menuWidth = font.stringExtent(child.properties.textItem).x + textPadding;
			}
		}	
	}
	
	var menuLayoutHeight = fontHeight * numChildren + numChildren * 2;
	
	var contentRect = laf.getRectangle("content.pane.bounds");
	var menuRect = new Rectangle(contentRect.x, contentRect.y, contentRect.width, contentRect.height);
	menuRect.width = menuWidth + 10;
	menuRect.height = menuLayoutHeight + 4;
	menuRect.x = d.x / 2 - (menuRect.width / 2) + 2;
	menuRect.y = (d.y / 2) - (menuLayoutHeight / 2) + 2;
	
	setBounds(instance, menuRect); // Sets the bounds of the menu itself
	
	var properties = instance.properties;
	defaultX=6;
	defaultY=2;
	// set the layout bounds for each menu item child 
	for (var i in instance.children) {
		var child = instance.children[i];
		var childProperties = child.properties;
		// Set the offsets so that the text item shows with it's bounds inside the menu rectangle borders
		setBounds(child, new Rectangle(defaultX, defaultY, menuWidth-4, fontHeight-2));
		defaultY = defaultY + fontHeight + 2;
	}
		
}


