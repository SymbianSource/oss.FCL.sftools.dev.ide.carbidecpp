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

function StatusPaneAppIcon() {
}

StatusPaneAppIcon.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;
	
	if (laf.getBoolean("show.context.icon", true)) {
		graphics.setBackground(getBackgroundColor(instance, laf));

		// get image from primary AIF file
		var imageAIFRendering = createSymbianImageAIFRendering();
		imageAIFRendering.setImageFromAIF(instance, "image", laf, null, 1);
	
		imageAIFRendering.setTransparencyHandling(
			imageAIFRendering.TRANSPARENCY_FLATTEN_AND_BLEND);
		imageAIFRendering.render(graphics.getWrappedGC(), 0, 0);
	}
	
}

StatusPaneAppIcon.prototype.getViewableSize = function(instance, propertyId, laf) {
	var	size = instance.properties.size;
	return new Point(size.width, size.height);
}

StatusPaneAppIcon.prototype.isScaling = function(instance, propertyId, laf) {
	return isScalingIcons();
}

StatusPaneAppIcon.prototype.getAlignmentWeights = function(instance, propertyId, laf) {
	return new Point(ImageUtils.ALIGN_CENTER_OR_LEFT, ImageUtils.ALIGN_CENTER_OR_TOP);
}

StatusPaneAppIcon.prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
	return true;
}


StatusPaneAppIcon.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null; // needs implementation	
}
