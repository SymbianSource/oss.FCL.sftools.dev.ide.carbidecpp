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
* START_USECASES: CU16 END_USECASES
*
*
*/


include("../../../renderLibrary.js")
include("../../../implLibrary.js")

function CEikImage() {
}

////////////////////////////////////////////////////////////////////////////////
// IVisualAppearance

CEikImage.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;
	if(properties.isVisible != null && properties.isVisible == false) {
	      return;
	}

	var renderingBounds = instance.getRenderingBounds();
	if ( properties.image.bmpfile=="" && properties.image.uri=="" ){
		var image = laf.getImage("image.placeholder.small");	
		graphics.drawImage(image, renderingBounds.width/2-9, renderingBounds.height/2-9);
	} else {	
		graphics.setBackground(getBackgroundColor(instance, laf));
		var x = 0;
		var imgBounds = getImageBounds(instance, properties.image);
		if(imgBounds!=null){
			if (imgBounds.width < renderingBounds.width) {
				x = (renderingBounds.width - imgBounds.width ) / 2;
			}
			renderImage(CEikImage.prototype, instance, laf, graphics, 
				x, 0, "image", true);
		}
	}
}

//If we have RowLayoutManager and bitmap image's width is larger than wHint, then image
//will be scaled (aspect ratio not preserved) in order to fit image on CQikContainer.
CEikImage.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	var properties = instance.properties;
	var width = -1;
	var height = -1;

	if (wHint >= 0)
		width = wHint;
	if (hHint >= 0)
		height = hHint;
	
	var imgBounds = getImageBounds(instance, properties.image);
	if (imgBounds != null) {
		if (isIconSlotChild(instance)) {
			width = imgBounds.width;
		} else {
			width = imgBounds.width > wHint ? wHint : imgBounds.width;
		}
		height = imgBounds.height;
	} else {
		if (width < 0)
			width = 18;
		if (height < 0)
			height = 18;
	}
	
	return new Point(width, height);
}

////////////////////////////////////////////////////////////////////////////////
// IImagePropertyRenderingInfo

CEikImage.prototype.getViewableSize = function(instance, propertyId, laf) {
	if (propertyId != "image") {
		return null;
	}
	var imgBounds = null;
	var renderingBounds = instance.getRenderingBounds();
	if (instance.properties.image != null) {
		imgBounds = getImageBounds(instance, instance.properties[propertyId], null);
	}
	if (imgBounds != null) {
		if (renderingBounds != null) {
			imgBounds.width = imgBounds.width > renderingBounds.width ? renderingBounds.width : imgBounds.width;
			if (isIconSlotChild(instance)) {
				imgBounds.height = imgBounds.height > renderingBounds.height ? renderingBounds.height : imgBounds.height;
			}
		}
		return new Point(imgBounds.width, imgBounds.height);
	}
}

CEikImage.prototype.getAlignmentWeights = function(instance, propertyId, laf) {
	return new Point(ImageUtils.ALIGN_CENTER, ImageUtils.ALIGN_CENTER);
}

CEikImage.prototype.isScaling = function(instance, propertyId, laf) {
	return true;
}

CEikImage.prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
		return false;
}


function isIconSlotChild(instance) {
	var parent = instance.parent;
	if (parent != null) {
		return parent.isInstanceOf("com.nokia.carbide.uiq.IconSlot");
	}
	return false;
}

////////////////////////////////////////////////////////////////////////////////
//IDirectLabelEdit

setupCommonDirectImageEditing(CEikImage.prototype, "image", 
		null  	// areafunction
);
