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

	// Turn a color property value string into
	// a color. The value is expected to either
	// be a comma delimited RGB or a system color
	
function colorFromString(laf, colorStr) {
	if (colorStr == null || colorStr == "")
		return null;

	var result = null;
	var elements = colorStr.split(",");
	if (elements.length == 3) {
		var valid = true;
		for (var i in elements) {
			var num = parseInt(elements[i]);
			if (isNaN(num))
				valid = false;
		}
		if (valid)
			result = Colors.getColor(elements[0], elements[1], elements[2]);
	}
	else {
		result = laf.getColor(colorStr);
	}
	return result;
}

// Get the effective background color, assuming that everything is transparent
// until we get to a component with an attribute describing how its
// background will be drawn
function getBackgroundColor(instance, laf) {
	var color = null;
	while (instance != null) {
		var bgProperty = instance.component.attributes
			["container-background-color-property-name"];
		if (bgProperty != null) {
			color = colorFromString(laf, instance.properties[bgProperty]);
			if (color != null) {
				//println("used attribute for " + color);
				break;
			}
		}
		var bgColor = instance.component.attributes
			["container-background-color"];
		if (bgColor != null) {
			color = laf.getColor(bgColor);
			if (color != null) {
				//println("used property for " + color);
				break;
			}
		}
		instance = instance.parent;
	}
	if (color == null) {
		color = laf.getColor("EEikColorWindowBackground");
		//println("using background color " + color);
		if (color == null) {
			color = Colors.getColor(255, 255, 255);
		}
	}
	return color;
}

ImageGlobals = getPluginClass("com.nokia.sdt.symbian", "com.nokia.sdt.symbian.images.ImageGlobals")


//	Get an image as specified in a com.nokia.sdt.symbian.ImageProperty
//	@param instance the instance
//	@param imageProperty the property, i.e. instance.properties.image
function getImageFromProperty(instance, graphics, imageProperty) {
	var info = ImageGlobals.getImageInfo(instance, imageProperty);
	return info.getComposedImage(graphics.getDevice())
}

//	Draw an image as specified in a com.nokia.sdt.symbian.ImageProperty
//	into the given rectangle in the gc
//	@param instance the instance
//	@param imageProperty the property, i.e. instance.properties.image
//	@param graphics the GC
//	@param rect the rectangle to draw in
function drawImageFromProperty(instance, graphics, imageProperty, rect) {
	var info = ImageGlobals.getImageInfo(instance, imageProperty);
	var image = info.getComposedImage(graphics.getDevice())
	if (image) {
		var imgRect = image.getBounds()
		graphics.drawImage(image, 0, 0, imgRect.width, imgRect.height, 
			rect.x, rect.y, rect.width, rect.height);
	}
}



