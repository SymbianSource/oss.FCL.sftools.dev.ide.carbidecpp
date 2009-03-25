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

function MultiLineDataQueryVisual() {
}

MultiLineDataQueryVisual.prototype.draw = function(instance, laf, graphics) {
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
	var flags = Font.ALIGN_LEFT;
	rect = calculateMultiDataQueryBounds(properties, laf, flags);
	drawPopupDialog(instance, laf, graphics, flags, rect, getIconRect(0, laf), 0);	
}

function calculateMultiDataQueryBounds(properties, laf, flags) {
	var rect;
	
	// calculate the bounds of the dialog (just like a note)
	var bounds = calculateBounds(properties, laf, flags, 0, 0);
	var portrait = laf.getBoolean("is.portrait", true);
	if (portrait)
		rect = new Rectangle(bounds.x, 0, bounds.width, bounds.height);
	else
		rect = new Rectangle(0, bounds.y, bounds.width, bounds.height);
		
	return rect;
}

MultiLineDataQueryVisual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null;
}

// IDirectLabelEdit
MultiLineDataQueryVisual.prototype.getPropertyPaths = function(instance) {
	return ["text", "text2"];
}

MultiLineDataQueryVisual.prototype.getLabelBounds = function(instance, propertyPath, laf) {
	var properties = instance.properties;
	var flags = Font.ALIGN_LEFT;
	rect = calculateMultiDataQueryBounds(properties, laf, flags);
	
	return rect;
}

MultiLineDataQueryVisual.prototype.getLabelFont = function(instance, propertyPath, laf) {
	return laf.getFont("message.font");
}

MultiLineDataQueryVisual.prototype.layout = function(instance, laf) {
	var properties = instance.properties;
	var portrait = laf.getBoolean("is.portrait", true);

	var flags = Font.ALIGN_LEFT;
	var rect = calculateBounds(properties, laf, flags, 0, 0);
	var d = laf.getDimension("screen.size");

	if (portrait) {
		properties.location.x = 0;
		properties.location.y = rect.y;
		properties.size.width = d.x;
		properties.size.height = d.y - rect.y;
	}
	else {
		properties.location.x = rect.x;
		properties.location.y = 0;
		properties.size.width = d.x - rect.x;
		properties.size.height = d.y;
	}

//	println("rect="+rect);
	var child0 = instance.children[0];
	if (child0 != null) {
		var dims = laf.getRectangle("query.editor");
//		println("dims0="+dims);
		if (portrait) {
			child0.properties.location.x = rect.x + dims.x;
			child0.properties.location.y = dims.y;
		}
		else {
			child0.properties.location.x = dims.x;
			child0.properties.location.y = rect.y + dims.y;
		}
		child0.properties.size.width = rect.width - dims.width;
		child0.properties.size.height = dims.height;
	}

	var child1 = instance.children[0];
	if (child1 != null) {
		var dims = laf.getRectangle("query.editor");
		//println("dims1="+dims);
		if (portrait) {
			child1.properties.location.x = rect.x + dims.x;
			child1.properties.location.y = dims.y;
		}
		else {
			child1.properties.location.x = dims.x;
			child1.properties.location.y = rect.y + dims.y;
		}
		child1.properties.size.width = rect.width - dims.width;
		child1.properties.size.height = dims.height;
	}
}

/////////////////////


function MultiLineDataQueryContainment() {
}

function hasChildren(instance) {
	return (instance.children != null) && (instance.children.length > 1);
}

MultiLineDataQueryContainment.prototype.canContainComponent = function(instance, otherComponent) {
	if (hasChildren(instance)) {
		return buildSimpleContainmentErrorStatus(
			lookupString("twoChildContainmentError"), null);
	}
	
	return null;
}
 
MultiLineDataQueryContainment.prototype.canContainChild = function(instance, child) {
	if (hasChildren(instance)) {
		return buildSimpleContainmentErrorStatus(
			lookupString("twoChildContainmentError"), null);
	}
	
	return null;
}
 
MultiLineDataQueryContainment.prototype.canRemoveChild = function(instance, child) {
	return false;
}
 
MultiLineDataQueryContainment.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return true;
}

MultiLineDataQueryContainment.prototype.getAllowedAttribute = function() {
	return "is-dataquery-content";
}

setupAttributeBasedQueryContainment(MultiLineDataQueryContainment.prototype);

MultiLineDataQueryContainment.prototype.propertyChanged = function(instance, property) {
	if ((property == "text") || (property == "text2")) {
		instance.forceLayout();
	}
}


