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
include("popupDialogLibrary.js")

function ConfirmationQueryVisual() {
}

ConfirmationQueryVisual.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;

	// first draw the CBA
	var leftText = lookupString("Yes");
	var rightText = lookupString("No");
	var middleText = "";
	var r = laf.getRectangle("control.pane.bounds");
	var rect = new Rectangle(r.x, r.y, r.width, r.height);
	rect.x -= properties.location.x;
	rect.y -= properties.location.y;
	drawCBA(leftText, rightText, middleText, new Point(rect.x, rect.y), new Point(rect.width, rect.height), laf, graphics);

	// then draw the query dialog
	var iconRect = getIconRect(getLeftOfDialogWithCBA(laf), laf);
	var flags = Font.ALIGN_LEFT | Font.WRAPPING_ENABLED;
	rect = calculateQueryBounds(properties, laf, flags, iconRect);
	drawPopupDialog(instance, laf, graphics, flags, rect, iconRect, getLeftOfDialogWithCBA(laf));	
}

function calculateQueryBounds(properties, laf, flags, iconRect) {
	var rect;
	
	// calculate the bounds of the dialog (just like a note)
	var bounds = calculateBounds(properties, laf, flags, iconRect.x, getLeftOfDialogWithCBA(laf));
	var portrait = laf.getBoolean("is.portrait", true);
	if (portrait)
		rect = new Rectangle(bounds.x, 0, bounds.width, bounds.height);
	else
		rect = new Rectangle(0, bounds.y, bounds.width, bounds.height);
		
	return rect;
}

ConfirmationQueryVisual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null;
}

// IDirectLabelEdit
ConfirmationQueryVisual.prototype.getPropertyPaths = function(instance) {
	return new Array("text");
}

ConfirmationQueryVisual.prototype.getLabelBounds = function(instance, propertyPath, laf) {
	var properties = instance.properties;
	var iconRect = getIconRect(getLeftOfDialogWithCBA(laf), laf);
	var flags = Font.ALIGN_LEFT | Font.WRAPPING_ENABLED;
	rect = calculateQueryBounds(properties, laf, flags, iconRect);
	rect.width -= iconRect.width;
	
	return rect;
}

ConfirmationQueryVisual.prototype.getLabelFont = function(instance, propertyPath, laf) {
	return laf.getFont("message.font");
}

ConfirmationQueryVisual.prototype.layout = function(instance, laf) {
	var properties = instance.properties;
	var portrait = laf.getBoolean("is.portrait", true);

	var flags = Font.ALIGN_LEFT | Font.WRAPPING_ENABLED;
	var iconRect = getIconRect(getLeftOfDialogWithCBA(laf), laf);
	var rect = calculateBounds(properties, laf, flags, iconRect.x, getLeftOfDialogWithCBA(laf));
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
}

ConfirmationQueryVisual.prototype.propertyChanged = function(instance, property) {
	if (property == "text") {
		instance.forceLayout();
	}
}

