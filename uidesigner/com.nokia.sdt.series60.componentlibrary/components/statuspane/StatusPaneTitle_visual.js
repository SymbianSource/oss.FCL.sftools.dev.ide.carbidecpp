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


include("../implLibrary.js")
include("../renderLibrary.js")
include("../srcgenLibrary.js")
include("titleLibrary.js")

function StatusPaneTitleVisual() {
}

StatusPaneTitleVisual.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;
	var titleText = properties.titleText;
	
	// image title not shown in landscape, but currently we
	// don't retain the text property, so use default
	if (laf.getBoolean("is.landscape", false)) {
		if (titleText == "")
			titleText = getProjectName();
	}
	
	if (titleText != "") {
		drawTitleText(instance, laf, graphics, titleText);
	} else {
		// no text, try image
		renderImage(StatusPaneTitleVisual.prototype, instance, laf, graphics, 
			0, 0, "image", false);
	}
}

StatusPaneTitleVisual.prototype.getPreferredSize = function(instance, wHint, hHint, laf) {
	return null;
}

StatusPaneTitleVisual.prototype.getViewableSize = function(instance, propertyId, laf) {
	var	size = instance.properties.size;
	return new Point(size.width, size.height);
}

StatusPaneTitleVisual.prototype.isScaling = function(instance, propertyId, laf) {
	return isScalingIcons();
}

StatusPaneTitleVisual.prototype.getAlignmentWeights = function(instance, propertyId, laf) {
	return new Point(ImageUtils.ALIGN_CENTER_OR_LEFT, ImageUtils.ALIGN_CENTER_OR_TOP);
}

StatusPaneTitleVisual.prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
	return true;
}

StatusPaneTitleVisual.prototype.propertyChanged = function(instance, propertyId) {
	// reset title text when image is set
	if (propertyId == "image" && isImagePropertySet(instance.properties.image)) {
		instance.properties.titleText = "";
	}
}

setupCommonDirectLabelEditing(StatusPaneTitleVisual.prototype, "titleText",
	null,	
	function(instance, laf) { return laf.getFont("titleFont"); 
	}
)


setupCommonDirectImageEditing(StatusPaneTitleVisual.prototype, "image",
	null // areafunction
)


