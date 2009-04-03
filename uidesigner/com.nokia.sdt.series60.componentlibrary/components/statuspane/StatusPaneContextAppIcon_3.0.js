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

function StatusPaneContextAppIcon() {
}

// IComponentValidator
StatusPaneContextAppIcon.prototype.validate = function(instance) {
	var str = checkImageProperty(instance, instance.properties.image);
	if (str)
		return [ createSimpleModelError(instance, "image", str, []) ];
	else
		return null;
}

StatusPaneContextAppIcon.prototype.queryPropertyChange = function(instance, propertyId, newValue) {
	if (propertyId == "image") {
		var str = checkImageProperty(instance, newValue);
		return str;	
	} else {
		return null;
	}
}

/**
 *	The app icon is a strange beast since it is specified only by file,
 * 	not by file and image.  S60 picks images from fixed indices in the file.
 *	When MBM files are used, the app/grid icon is a different size than the
 *	context icon, so two images are candidates, and the second one (if existing)
 *	is seen in the context icon.  When MIF files are used, the icon can be
 *	scaled for either position, so only the first image is used.
 *
 *	Note: this validation and this component do not get across the point
 *	that the app/grid icon may be different, so the user needs to find this
 *	out from experience.
 */
function checkImageProperty(instance, imageProperty) {
	var imageInfo = imageProperty.editableValue;
	if (imageInfo != null && imageInfo.multiImageInfo != null) {
		var theImage;
		if (imageInfo.bitmapInfo == null)
			return lookupString("MustBeMaskedImage");
			
		// get the first and second image from this file
		var firstImage = imageInfo.multiImageInfo.getImageAtIndex(0);
		var secondImage = imageInfo.multiImageInfo.getImageAtIndex(2); // step by 2 (bmp, mask)
		
		if (imageInfo.multiImageInfo.getFileType() == imageInfo.multiImageInfo.MBM_FILE) {
			// for mbms, the first image becomes the app/grid icon and the second is the context icon
			theImage = secondImage;
			if (secondImage == null)
				theImage = firstImage;
			if (imageInfo.bitmapInfo != theImage)
				return lookupString("MustBeFirstOrSecondBmp");
		} else {
			// for mifs, the first image is used in both the app/grid and the context icon
			theImage = firstImage;
			if (imageInfo.bitmapInfo != theImage)
				return lookupString("MustBeFirstSvg");
		}
		
		// verify image has a mask, else we won't get any image
		if (!imageInfo.bitmapInfo.isSVG() && imageInfo.maskInfo == null)
			return lookupString("MustBeMaskedImage");
	}
	return null;
	
}
