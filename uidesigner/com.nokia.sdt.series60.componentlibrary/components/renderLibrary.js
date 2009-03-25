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
		// children of form and settings list have parent-derived colors
		if (instance.parent != null && instance.parent.componentId == "com.nokia.sdt.series60.CAknForm")
			break;
		if (instance.parent != null && instance.parent.componentId == "com.nokia.sdt.series60.CAknSettingItemList") {
			color = laf.getColor("CAknSettingItemList.ContentBackground");
			break;
		}
			
		var bgProperty = null;
		var bgColor = null;
		if (instance.component != null) {
			bgProperty = instance.component.attributes
				["container-background-color-property-name"];
			bgColor = instance.component.attributes
				["container-background-color"];
		}
		if (bgProperty != null) {
			color = colorFromString(laf, instance.properties[bgProperty]);
			if (color != null) {
				//println("used attribute for " + color);
				break;
			}
		}
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

/**
 *	Get the real size of an image from property.
 *	This retrieves the unscaled image, which should 
 *	already be cached.
 *	@param instance the instance
 *	@param imageProperty the property, i.e. instance.properties.image
 *	@param multiImageAbstractImageId for a multi-image property, the abstract image id
 *	@return the Rectangle bounds, or null
 */
function getImageBounds(instance, imageProperty, multiImageAbstractImageId) {
 	var imageRendering = createImagePropertyRendering();
 	imageRendering.setImageProperty(instance, null, null);
 	imageRendering.setImagePropertySource(imageProperty);
 	if (multiImageAbstractImageId)
 		imageRendering.setMultiImagePropertyAbstractImageId(multiImageAbstractImageId);
	var img = imageRendering.getImageData();
	if (img == null)
		return null;
	return new Rectangle(0, 0, img.width, img.height);
}

/**
 *	Scale a size to fit in a given size
 *	@param insize incoming size to scale
 *	@param size the size to fit
 *	@param preserveAspect true: keep aspect ratio, false: use instance size exactly
 *	@return a Point
 */
function getSizeScaledToSize(insize, size, preserveAspect) {
	if (!preserveAspect || size.x == 0 || size.y == 0)	{
		return size;
	}
	
	if (insize.x == 0 || insize.y == 0)
		return insize;
		
	var iw, ih;
	if (size.x / size.y > insize.x / insize.y) {
		iw = insize.x * size.y / insize.y;
		ih = size.y;
	} else {
		iw = size.x;
		ih = insize.y * size.x / insize.x;
	}
	var scaled = new Point(iw, ih);
	//println("scaled to " + scaled);
	return scaled;
}

/**
 *	Scale a bounds to fit in a given bounding rectangle, and centered therein.
 *	@param inBounds incoming bounds to scale
 *	@param bounds the bounds to fit
 *	@param preserveAspect true: keep aspect ratio, false: use instance size exactly
 *	@return a Rectangle
 */
function getBoundsScaledToBounds(inBounds, bounds, preserveAspect) {
	var size = getSizeScaledToSize(new Point(inBounds.width, inBounds.height),
		new Point(bounds.width, bounds.height),
		preserveAspect);
		
	var scaled = new Rectangle((bounds.width - size.x) / 2, (bounds.height - size.y) / 2,
			size.x, size.y);
	//println("scaled to " + scaled);
	return scaled;
}

//	Draw an image into the given rectangle in the gc
//	@param instance the instance
//	@param graphics the GC
//	@param rect the rectangle to draw to.  If the rectangle is null
//		or the width/height are 0, no scaling is performed.
//		The x/y offset are used, if non-null, to offset the image.
//	@param doBlend true: blend smoothly with background (only works with solid background)
//	@return the Image, or null
function drawImage(instance, graphics, image, rect, doBlend) {
	if (image) {
		// show image in dialog
		//var dump = new ImageDump(null, image);
		//dump.open();

		var imgRect = image.getBounds()
		//println("image is " + imgRect);
		
		var blended = ImageUtils.flattenAlphaMaskedImage(graphics.getDevice(), image, 
			graphics.getBackground(), doBlend, true /* transparent */);

		// show blended image in dialog
		//var dump = new ImageDump(null, blended);
		//dump.open();

		if (rect) {
			var imgRect = blended.getBounds()
			if (rect.width != 0 && rect.height != 0)
				graphics.drawImage(blended, 0, 0, imgRect.width, imgRect.height, rect.x, rect.y, rect.width, rect.height);
			else
				graphics.drawImage(blended, 0, 0, imgRect.width, imgRect.height, rect.x, rect.y, imgRect.width, imgRect.height);
		} else {
			graphics.drawImage(blended, 0, 0);
		}
		
		blended.dispose();
	}
}


/**
 * Get the height of a font which properly encompasses its leading,
 * descent, and ascent.  font.getHeight() is not quite accurate, for some reason...
 */
function getFontHeight(font) {
	return font.formattedStringExtent("x", new Point(0, 0), 0, 0).y;
}

/**
 *	Render an image to the gc
 *	@param prototype a prototype implementing IImagePropertyRenderingInfo
 *	@param instance the component instance holding the image property
 *	@param laf the look and feel information
 *	@param graphics the GC
 *	@param x x offset of image (left)
 *	@param y y offset of image (top)
 *	@param propertyId the property path of the image compound property
 *	@param doBlend true: blend image with background when flattening alpha
 *  @param multiImageAbstractImageId if non-null, then the image property houses multiple images, and draw this one
 *	(@see IMultiImagePropertyInfo)
 */
function renderImage(prototype, instance, laf, graphics, x, y, propertyId, doBlend, multiImageAbstractImageId) {
	var imagePropertyRendering = createImagePropertyRendering();
	imagePropertyRendering.setImageProperty(instance, propertyId, laf);
	imagePropertyRendering.setViewableSize(prototype.getViewableSize(instance, propertyId, laf));
	imagePropertyRendering.setAlignmentWeights(prototype.getAlignmentWeights(instance, propertyId, laf));
	imagePropertyRendering.setScaling(prototype.isScaling(instance, propertyId, laf));
	imagePropertyRendering.setPreservingAspectRatio(prototype.isPreservingAspectRatio(instance, propertyId, laf));
	
	imagePropertyRendering.setTransparencyHandling(doBlend 
		? imagePropertyRendering.TRANSPARENCY_FLATTEN_AND_BLEND
		: imagePropertyRendering.TRANSPARENCY_FLATTEN);
		
	if (multiImageAbstractImageId)
		imagePropertyRendering.setMultiImagePropertyAbstractImageId(multiImageAbstractImageId);
	
	imagePropertyRendering.render(graphics.getWrappedGC(), x, y);
}


/**
 *	Get the bounds consumed by wrappable text, given a limiting width and 
 *	maximum number of lines.  This detects newlines embedded in text.
 *	@param string the text to measure
 *	@param width width in pixels
 *	@param font the font
 *	@param flags mask of IFont.XXX flags (wrapping flags ignored!)
 *	@param lineGap pixel gap b/t lines
 *	@param maxLines maximum # lines to emit
 *	@return Point (maxWidthUsed, requiredHeight)
 */
function calculateWrappedTextSize(string, width, font, flags, lineGap, maxLines) {
	var lines = TextRendering.formatIntoLines(font, string, width, flags, maxLines);
	var fontHeight = font.getHeight();
	var gappedLineHeight = fontHeight + lineGap;
	var maxWidth = 0;
	for (var i in lines) {
		var line = lines[i];
		maxWidth = Math.max(maxWidth, font.stringExtent(line).x);
	}
	return new Point(maxWidth, lines.length * gappedLineHeight);
}
