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

function CEikImageVisual() {
}

CEikImageVisual.prototype.draw = function(instance, laf, graphics) {
	graphics.setBackground(getBackgroundColor(instance, laf));
	
	renderImage(CEikImageVisual.prototype, instance, laf, graphics, 
		0, 0, "image", true);
}

CEikImageVisual.prototype.getViewableSize = function(instance, propertyId, laf) {
	var	size = instance.properties.size;
	return new Point(size.width, size.height);
}

CEikImageVisual.prototype.isScaling = function(instance, propertyId, laf) {
	return isScalingIcons();
}

CEikImageVisual.prototype.getAlignmentWeights = function(instance, propertyId, laf) {
	var version = getComponentVersions();
	if (version.getMajor() >= 3 || version.getMinor() >= 8)
		return new Point(ImageUtils.ALIGN_CENTER_OR_LEFT, ImageUtils.ALIGN_CENTER_OR_TOP);
	else
		return new Point(ImageUtils.ALIGN_LEFT, ImageUtils.ALIGN_TOP);
}

CEikImageVisual.prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
	return true;
}


CEikImageVisual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	var properties = instance.properties;

	var width = -1;
	var height = -1;
	if (wHint >= 0)
		width = wHint;
	if (hHint >= 0)
		height = hHint;

	var imgBounds = getImageBounds(instance, properties.image);
	if (imgBounds) {
		if (imgBounds.width > width)
			width = imgBounds.width;
		if (imgBounds.height > height)
			height = imgBounds.height;
	} else {
		if (width < 0)
			width = 16;
		if (height < 0)
			height = 16;
	}
		
	var bounds = new Point(width, height);
	return bounds;
}

setupCommonDirectImageEditing(CEikImageVisual.prototype, "image", 
	null  	// areafunction
);
