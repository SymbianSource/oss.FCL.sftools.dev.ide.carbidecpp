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


include("../../../implLibrary.js")
include("../../../renderLibrary.js")
include("../../../srcgenLibrary.js")

function CEikImage() {
}

CEikImage.prototype.draw = function(instance, laf, graphics) {
	graphics.setBackground(getBackgroundColor(instance, laf));
	
	var bounds = instance.getLayoutBounds();
	renderImage(CEikImage.prototype, instance, laf, graphics, 
		0, 0, "image", true);
}

///////////////////

// The viewable size is effectively the layout size, and that size
// will fit any image.
//
// We don't scale the image and retain its aspect ratio.  Thus, the 
// alignment properties can be applied when rendering the image
// inside this larger box.

CEikImage.prototype.getViewableSize = function(instance, propertyId, laf) {
	
	var bounds = instance.getLayoutBounds();
	if (bounds == null) {
		if (instance.properties.image != null) {
			bounds = getImageBounds(instance, instance.properties[propertyId], null);
		}
	}
	if (bounds != null) {
		return new Point(bounds.width, bounds.height);
	}
	return null;	
}

CEikImage.prototype.isScaling = function(instance, propertyId, laf) {
	return false; //isScalingIcons();
}

CEikImage.prototype.getAlignmentWeights = function(instance, propertyId, laf) {
	var hWeight = ImageUtils.ALIGN_LEFT;
	var vWeight = ImageUtils.ALIGN_TOP;
	var hAlign = instance.properties.horizontalAlignment;
	if (hAlign == "EEikLabelAlignHLeft")
		hWeight = ImageUtils.ALIGN_LEFT;
	else if (hAlign == "EEikLabelAlignHCenter")
		hWeight = ImageUtils.ALIGN_CENTER;
	else if (hAlign == "EEikLabelAlignHRight")
		hWeight = ImageUtils.ALIGN_RIGHT;
		
	var vAlign = instance.properties.verticalAlignment;
	if (vAlign == "EEikLabelAlignVTop")
		vWeight = ImageUtils.ALIGN_TOP;
	else if (vAlign == "EEikLabelAlignVCenter")
		vWeight = ImageUtils.ALIGN_CENTER;
	else if (vAlign == "EEikLabelAlignVBottom")
		vWeight = ImageUtils.ALIGN_BOTTOM;
	
	return new Point(hWeight, vWeight);
}

CEikImage.prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
	return true;
}

//////////////////////

CEikImage.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	var properties = instance.properties;

	var width = -1;
	var height = -1;
	if (wHint >= 0)
		width = wHint;
	if (hHint >= 0)
		height = hHint;

	var imgBounds = getImageBounds(instance, properties.image);
	if (imgBounds) {
		// the layout grows to fit the image
		width = imgBounds.width;
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

////

setupCommonDirectImageEditing(CEikImage.prototype, "image", 
	null  	// areafunction
);
