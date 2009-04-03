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

function NaviImageVisual() {
}

function getNaviSize(instance, laf) {
	var properties = instance.properties;
	return new Point(
		properties.size.width - laf.getDimension("navi.indicator.size").x, 
		properties.size.height);
	return bounds;
}

NaviImageVisual.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;
	graphics.setBackground(laf.getColor("status.bar.gradient.end"));
	
	// SVG is treated specially
	var imageInfo = properties.image.editableValue;
	if (imageInfo.isSVG()) {
		var nsize = getNaviSize(instance, laf);
		if (imageInfo.getMaskDepth() == 0) {
			// and the entire BG is cleared is the bg is white
			graphics.setBackground(getBackgroundColor(instance, laf));
			graphics.fillRectangle(0, 0, nsize.x, nsize.y);
		}
	}

	renderImage(NaviImageVisual.prototype, instance, laf, graphics, 
		0, 0, "image", true);
}

NaviImageVisual.prototype.getViewableSize = function(instance, propertyId, laf) {
	var	size = instance.properties.size;
	return new Point(size.width, size.height);
}

NaviImageVisual.prototype.isScaling = function(instance, propertyId, laf) {
	return isScalingIcons();
}

NaviImageVisual.prototype.getAlignmentWeights = function(instance, propertyId, laf) {
	return new Point(ImageUtils.ALIGN_CENTER, ImageUtils.ALIGN_CENTER_OR_TOP);
}

NaviImageVisual.prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
	return true;
}

NaviImageVisual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null; // needs implementation	
}


setupCommonDirectImageEditing(NaviImageVisual.prototype, "image", 
	null  // areafunction
);
	
