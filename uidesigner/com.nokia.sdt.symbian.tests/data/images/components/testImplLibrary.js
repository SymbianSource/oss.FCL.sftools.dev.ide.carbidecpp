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
// This file has routines which initialize component implementations
// according to patterns.

include ("testMessageLibrary.js")

strings = getLocalizedStrings("testImplLibrary");

//	Set up direct label editing implementation for a component with
//	one editable label
//	@param prototype the impl prototype to update
//	@param property the name of the edited property
//	@param areafunction a function, taking an instance and laf, which returns the
//		editable area of the label (or null).  If null, the default behavior is
//		to use the entire rendered area.
//	@param fontfunction a function, taking an instance and laf, which returns the
//		IFont to edit with (or null).  If null, the default behavior is to return
//		null, indicating a default system font.
function setupCommonDirectLabelEditing(prototype, property, areafunction, fontfunction) {

	prototype.getPropertyPaths = function(instance) {
		return [ property ];
	}

	prototype.getLabelBounds = function(instance, propertyPath, laf) {
		if (areafunction)
			return areafunction(instance, laf);
		var size = instance.properties.size;
	    return new Rectangle(0, 0, size.width, size.height);
	}

	prototype.getLabelFont = function(instance, propertyPath, laf) {
		if (fontfunction)
			return fontfunction(instance, laf)
		return null;
	}
}

//	Set up direct image editing implementation for a component
//	with one image property.  This sets up a standard validation
//	check (images must be BMP or SVG if scalable UI enabled), and
//	warns about scaled images if the image is a bitmap and its size 
//	doesn't match the rendered area.
//	<p>
//	The validation logic is added to the prototype as "commonValidateImage" --
//	you may override this behavior in your "validateImage" call.
//
//	@param prototype the impl prototype to update
//	@param property the name of the edited property
//	@param areafunction a function, taking an instance and laf, or null.
//		If not null, the function which returns a rectangle for the clickable area 
//		of the rendered area to respond to as well as the preferred size of the image.
//		If null, the default behavior is to use the entire rendered area.
function setupCommonDirectImageEditing(prototype, property, areafunction) {

	prototype.getImagePropertyPaths = function(instance) {
		return [ property ];
	}

	prototype.getImageBounds = function(instance, propertyPath, laf) {
		println( "image: " + instance.properties[propertyPath].bmpfile + "/" 
				+instance.properties[propertyPath].bmpid + "/" 
				+instance.properties[propertyPath].bmpmask );
				
		if (areafunction) 
			return areafunction(instance, laf);
		var size = instance.properties.size;
	    return new Rectangle(0, 0, size.width, size.height);
	}

	function isBMPFile(file) {
		var lower = file.toLowerCase();
		return lower.endsWith(".bmp");
	}

	function isSVGFile(file) {
		var lower = file.toLowerCase();
		println ("SVG check: lower="+lower);
		return lower.endsWith(".svg") || lower.endsWith(".svgt");
	}

	prototype.commonValidateImage = function(instance, propertyId, laf, file, size) {
	
		var bounds = this.getImageBounds(instance, propertyId.substring(propertyId.lastIndexOf('.')+1), laf)
		
		//println("validating " + file + " @" + size +" for " + propertyId + " in " + bounds);
	
		var lower = file.toLowerCase();
		if (!isBMPFile(file) && !isSVGFile(file)) {
			return buildSimpleErrorStatus(
				strings.getString("imageTypeOnlyBmpOrSvgError"), [ file ]);
		}
	
	 	// unknown size --> ignore
		if (size == null)
			return null;
		
		if (!isSVGFile(file)) {
			if (size.x != bounds.width || size.y != bounds.height)
				return buildSimpleWarningStatus(
					strings.getString("scaledOrCroppedImageSizeMsg"), 
					new Array( bounds.width, bounds.height ));
		}
		
		return null;
	}

	prototype.validateImage = prototype.commonValidateImage;
}
