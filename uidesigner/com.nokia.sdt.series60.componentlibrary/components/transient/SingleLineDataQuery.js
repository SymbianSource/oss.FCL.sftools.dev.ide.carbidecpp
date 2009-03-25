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


include("../renderLibrary.js")
include("../messageLibrary.js")
include("../cba/cbaLibrary.js")
include("../containers/containerLibrary.js")
include("popupDialogLibrary.js")

function SingleLineDataQueryVisual() {
	setupPopupVisualHelper(SingleLineDataQueryVisual.prototype);
}


SingleLineDataQueryVisual.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;

	// first draw the CBA
	var leftText = lookupString("Ok");
	var rightText = lookupString("Cancel");
	var middleText = "";
	var r = laf.getRectangle("control.pane.bounds");
	var rect = new Rectangle(r.x, r.y, r.width, r.height);
	rect.x -= properties.location.x;
	rect.y -= properties.location.y;
	drawCBA(leftText, rightText, middleText, new Point(rect.x, rect.y), new Point(rect.width, rect.height), laf, graphics);

	// then draw the query dialog
	var flags = Font.ALIGN_LEFT | Font.WRAPPING_ENABLED;
	var font = laf.getFont("message.font");
	var textr = this.calculateTextBounds(properties, laf, "text", font, flags, 1, 3);
	var editDims = laf.getRectangle("query.editor");
	var editRect = new Rectangle(editDims.x, editDims.y, editDims.width, editDims.height);
	var bounds = this.calculateDialogBounds(properties, laf, new Point(textr.width, textr.height), editRect);
	//println("textr="+textr);
	//println("bounds="+bounds);
	var font = this.getLabelFont(instance, "text", laf);
	this.drawPopupDialog(instance, laf, graphics, font, flags, textr, bounds);
}

SingleLineDataQueryVisual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null;
}

// IDirectLabelEdit
SingleLineDataQueryVisual.prototype.getPropertyPaths = function(instance) {
	return new Array("text");
}

SingleLineDataQueryVisual.prototype.getLabelBounds = function(instance, propertyPath, laf) {
	var properties = instance.properties;
	var flags = Font.ALIGN_LEFT | Font.WRAPPING_ENABLED;
	var font = laf.getFont("message.font");
	rect = this.calculateTextBounds(properties, laf, "text", font, flags, 1, 3);
	
	return rect;
}

SingleLineDataQueryVisual.prototype.getLabelFont = function(instance, propertyPath, laf) {
	return laf.getFont("message.font");
}

SingleLineDataQueryVisual.prototype.getIconRect = function() {
	return new Rectangle(0,0,0,0);
}

SingleLineDataQueryVisual.prototype.layout = function(instance, laf) {
	var properties = instance.properties;
	var portrait = laf.getBoolean("is.portrait", true);

	var flags = Font.ALIGN_LEFT | Font.WRAPPING_ENABLED;
	var font = laf.getFont("message.font");
	var textr = this.calculateTextBounds(properties, laf, "text", font, flags, 1, 3);
	var editDims = laf.getRectangle("query.editor");
	var editRect = new Rectangle(editDims.x, editDims.y, editDims.width, editDims.height);
	editRect.y += textr.height;
	var rect = this.calculateDialogBounds(properties, laf, new Point(textr.width, textr.height), editRect);
	var d = laf.getDimension("screen.size");
	var cbaR = laf.getRectangle("control.pane.bounds");
	
	// position dialog
	if (portrait) {
		rect.y = (d.y - cbaR.height) - rect.height - rect.y;
	}
	else {
	
	}
	
	// set our bounds
	setBounds(instance, rect);

	// set child bounds
	var child = instance.children[0];
	var bounds = new Rectangle(0,0,0,0);
	if (child != null) {
		if (portrait) {
			bounds.x = rect.x + editRect.x;
			bounds.y = editRect.y;
		}
		else {
			bounds.x = editRect.x;
			bounds.y = rect.y + editRect.y;
		}
		bounds.width = editRect.width;
		bounds.height = editRect.height;
		setBounds(child, bounds);
	}
}

/////////////////////


function SingleLineDataQueryContainment() {
}

function hasChild(instance) {
	return (instance.children != null) && (instance.children.length > 0);
}

SingleLineDataQueryContainment.prototype.canContainComponent = function(instance, otherComponent) {
	if (hasChild(instance)) {
		return buildSimpleContainmentErrorStatus(
			lookupString("singleChildContainmentError"), null);
	}
	
	return null;
}
 
SingleLineDataQueryContainment.prototype.canContainChild = function(instance, child) {
	if (hasChild(instance)) {
		return buildSimpleContainmentErrorStatus(
			lookupString("singleChildContainmentError"), null);
	}
	
	return null;
}
 
SingleLineDataQueryContainment.prototype.canRemoveChild = function(instance, child) {
	return false;
}
 
SingleLineDataQueryContainment.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return true;
}

SingleLineDataQueryContainment.prototype.getAllowedAttribute = function() {
	return "is-dataquery-content";
}

setupAttributeBasedQueryContainment(SingleLineDataQueryContainment.prototype);

SingleLineDataQueryContainment.prototype.propertyChanged = function(instance, property) {
	if (property == "text") {
		instance.forceLayout();
	}
}


