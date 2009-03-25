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
include("../containers/containerLibrary.js")
include("../renderLibrary.js")
include("../implLibrary.js")
include("popupDialogLibrary.js")

function CAknPreviewPopUpVisual() {
}

CAknPreviewPopUpVisual.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;
	
	var rect = new Rectangle(0, 0, properties.size.width, properties.size.height);
	
	// fill background
	graphics.setBackground(getBackgroundColor(instance, laf));
	graphics.fillRectangle(rect);

	// draw frame
	var colorArray = [ null, // no color
		Colors.getColor(242,242,242),
		Colors.getColor(222,231,247),
		Colors.getColor(234,235,234),
		Colors.getColor(214,227,239),
		Colors.getColor(247,247,255)]
	drawFrame(rect, colorArray, graphics);

	var contentRect = new Rectangle(6, 6, rect.width - 12, rect.height - 12);
	var color = colorFromString(laf, properties.backColor);
	if (color != null) {
		graphics.setBackground(color);
		graphics.fillRectangle(contentRect);
	}

	if (properties.headingText.length > 0) {
		drawPreviewPopUpHeading(instance, graphics, laf);
	}
}

CAknPreviewPopUpVisual.prototype.layout = function(instance, laf) {
	var screenSize = laf.getDimension("screen.size");
	if (instance.properties.EFixedMode) {
		var mainPane = laf.getRectangle("content.pane.bounds");
		var portrait = laf.getBoolean("is.portrait", true);
		if (portrait) {
			instance.properties.location.x = mainPane.x + mainPane.width / 3;
		}
		else {
			instance.properties.location.x = mainPane.x + mainPane.width / 2;
		}
		instance.properties.location.y = mainPane.y;
		instance.properties.size.width = mainPane.width - instance.properties.location.x;
		instance.properties.size.height = mainPane.height;
	}
	else {
		// center it if not fixed mode
		instance.properties.location.x = (screenSize.x - instance.properties.size.width) / 2;
		instance.properties.location.y = (screenSize.y - instance.properties.size.height) / 2;
	}
}

CAknPreviewPopUpVisual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null;
}

setupCommonDirectLabelEditing(CAknPreviewPopUpVisual.prototype, 
	"headingText", 
	null,
	function(instance, laf) { return getPreviewPopUpHeadingFont(laf); } 
	)

//====================================================================================

function CAknPreviewPopUpContainment() {
}

CAknPreviewPopUpContainment.prototype.getAllowedAttribute = function() {
	return "is-caknpreviewpopup-content";
}

setupAttributeBasedQueryContainment(CAknPreviewPopUpContainment.prototype);

//====================================================================================

function CAknPreviewPopUpInit() {
}

CAknPreviewPopUpInit.prototype.getClassName = function(instance) {
	return "C" + titleCase(instance.properties.name);
}

CAknPreviewPopUpInit.prototype.initialize = function(instance, isConfigured) {
	if (!isConfigured) {
		instance.properties.className = this.getClassName(instance);
	}
}

CAknPreviewPopUpInit.prototype.propertyChanged = function(instance, propertyId, laf) {
	if (propertyId == "name") {
		instance.properties.className = this.getClassName(instance);
	}
	else if (propertyId == "EFixedMode") {
		instance.forceLayout();
	}
}

//====================================================================================

function CAknPreviewPopUpEventInfo() {
}

CAknPreviewPopUpEventInfo.prototype.getEventGroups = function(instance) {
	return ["CAknPreviewPopUp"];
}

CAknPreviewPopUpEventInfo.prototype.getDefaultEventName = function(instance) {
	return "handlePointerEvent";
}

//====================================================================================

