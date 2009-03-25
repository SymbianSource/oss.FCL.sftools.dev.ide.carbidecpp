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
*
*/


include("../implLibrary.js")
include("../renderLibrary.js")

function CAknButton() {
}

CAknButton.prototype.draw = function(instance, laf, graphics) {
	var borderPadding = 3;
	var properties = instance.properties;
	var width = properties.size.width - borderPadding;
	var height = properties.size.height - borderPadding;
	var x = 0;
	var y = 0;

	// fill 
	if (properties.buttonNoFrame == false){
		graphics.setBackground(laf.getColor("control.button.fill"));
		graphics.setForeground(laf.getColor("screen.background"));
		graphics.setBackground(laf.getColor("control.button.fill")); 
		graphics.fillGradientRectangle(x, y, width, height, true)
		// edge
		graphics.setForeground(laf.getColor("control.button.outline"))
		graphics.drawRectangle(new Rectangle(0, 0, width, height))
	
		// shadows
		graphics.setForeground(laf.getColor("control.button.shadow.inner"))
		graphics.drawLine(x + 1, y + height + 1, x + width + 1, y + height + 1)
		graphics.drawLine(x + width + 1, y + 1, x + width + 1, y + height + 1)
		
		graphics.setForeground(laf.getColor("control.button.shadow.outer"))
		graphics.drawLine(x + 0, y + height + 0, x + width + 0, y + height + 0)
		graphics.drawLine(x + width + 0, y + 0, x + width + 0, y + height + 0)
	}
	var fontFlags = getFontFlagsFromProperties(properties);
	
	if (properties.images.bmpfile != ""){
		
		var inToolbar = instance.parent.component.isOfType("com.nokia.sdt.series60.Toolbar");
		if (instance.properties.buttonText != "" && !inToolbar) {
			// there's an image and text and it's a button on the main pane (not in toolbar)
			renderImage(CAknButton.prototype, instance, laf, graphics, 
				borderPadding, borderPadding, "images", true, "normal");
			var imageBounds = getImageBounds(instance, properties.images, "normal");
			var textOffsetForImage = getTextOffsetForImage(imageBounds, properties);
			drawButtonTextItem(instance, instance.properties.buttonText, new Point(0, 0), new Point(width, height), laf, graphics, textOffsetForImage, fontFlags);
		} else {
			// there's only an image: draw the specified image on the button
			if (!inToolbar) // not in toolbar so center image horizontally
				x = width / 2 - height + 3*borderPadding;
			y = borderPadding;
			renderImage(CAknButton.prototype, instance, laf, graphics, 
				x, y, "images", true, "normal");
		}
	
	} else {
		// there's only text
		graphics.setForeground(laf.getColor("EEikColorButtonText"));
		var textOffsetForImage = new Point(0, 0);
		drawButtonTextItem(instance, instance.properties.buttonText, new Point(0, 0), new Point(width, height), laf, graphics, textOffsetForImage, fontFlags);
	}
}

/**
* Get text offsets when text and image are together so that the text is not overwriting the image
*/
function getTextOffsetForImage(imageBounds, properties){
	var imageDim = 0;
	if (imageBounds != null)
		imageDim = properties.size.height;
	// if it's left aligned and image is on left
	if (properties.textAndIconAlignment == "CAknButton::EIconBeforeText" && 
		(properties.horizTextAlign == "CGraphicsContext::ELeft" ||
		properties.horizTextAlign == "CGraphicsContext::ECenter"))  
	{
		return new Point(imageDim, 0);	
	}
	// if it's right aligned and image is on right
	if (properties.textAndIconAlignment == "CAknButton::EIconAfterText" && 
		properties.horizTextAlign == "CGraphicsContext::ERight") 
	{
		return new Point(imageDim, 0);	
	}
	// if it's top aligned and image is on top
	if (properties.textAndIconAlignment == "CAknButton::EIconOverText" && 
		properties.vertTextAlign == "CAknButton::ETop") 
	{
		return new Point(0, imageDim);	
	}
	// if it's bottom aligned and image is on bottom
	if (properties.textAndIconAlignment == "CAknButton::EIconUnderText" && 
		properties.vertTextAlign == "CAknButton::EBottom") 
	{
		return new Point(0, imageDim);	
	}
	return new Point(0, 0);
}

function getFontFlagsFromProperties(properties){

		// text can be offset as: center, left hand side, right hand side, top, or bottom
		var fontFlags;
		if (properties.horizTextAlign == "CGraphicsContext::ELeft" || (properties.buttonTextLeft == true) ) {
			fontFlags |= Font.ALIGN_LEFT;
		}else if (properties.horizTextAlign == "CGraphicsContext::ERight") {
			fontFlags |= Font.ALIGN_RIGHT;
		} else {
			fontFlags |= Font.ALIGN_CENTER; // default alignment
		}
		
		if (properties.vertTextAlign == "CAknButton::ETop") {
			fontFlags |= Font.VERTICAL_ALIGN_TOP;
		}else if (properties.vertTextAlign == "CAknButton::EBottom") {
			fontFlags |= Font.VERTICAL_ALIGN_BOTTOM;
		} else {
			fontFlags |= Font.VERTICAL_ALIGN_CENTER;
		}
		return fontFlags;
}


/**
 *	Get the rectangle in which to draw the icon
 */
function getIconRect(properties, laf) {
	var iconSize = laf.getDimension("note.icon.size");
	var padding = laf.getInteger("note.padding", 2);
	var iconRect = new Rectangle(properties.size.width - iconSize.x - padding*2, 
			0,
			iconSize.x, iconSize.y);
	return iconRect;
}


CAknButton.prototype.getViewableSize = function(instance, propertyId, laf) {
	var	size = instance.properties.size;
	var padding = laf.getInteger("note.padding", 2);
	return new Point(size.height, size.height - padding*2);
}

CAknButton.prototype.isScaling = function(instance, propertyId, laf) {
	return true;
}


CAknButton.prototype.getAlignmentWeights = function(instance, propertyId, laf) {
		// If there's no button text align in the center, else align on the left
		var properties = instance.properties;
		
		if (properties.buttonText == "") {
			return new Point(ImageUtils.ALIGN_CENTER_OR_TOP, ImageUtils.ALIGN_CENTER_OR_TOP);
		} else {
			// image and text
				if (properties.textAndIconAlignment == "CAknButton::EIconBeforeText"){
					return new Point(ImageUtils.ALIGN_LEFT, ImageUtils.ALIGN_CENTER_OR_TOP);
				} else if (properties.textAndIconAlignment == "CAknButton::EIconAfterText"){
					return new Point(ImageUtils.ALIGN_RIGHT, ImageUtils.ALIGN_CENTER_OR_TOP);
				} else if (properties.textAndIconAlignment == "CAknButton::EIconUnderText"){
					return new Point(ImageUtils.ALIGN_CENTER, ImageUtils.ALIGN_BOTTOM);
				} else if (properties.textAndIconAlignment == "CAknButton::EIconOverText"){
					return new Point(ImageUtils.ALIGN_CENTER, ImageUtils.ALIGN_TOP);
				} else if (properties.textAndIconAlignment == "CAknButton::EOverlay"){
					return new Point(0, 0);
				}  else {
					return new Point(0, 0);
				}
		}
}

CAknButton.prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
	// Preserving aspect ratio only when button is child of toolbar
	if (instance.parent.component.isOfType("com.nokia.sdt.series60.Toolbar")){
		return true;
	} else {
		return false;
	}
}


CAknButton.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	var properties = instance.properties;

	var width = -1;
	var height = -1;
	if (wHint >= 0)
		width = wHint;
	if (hHint >= 0)
		height = hHint;

	var imgBounds = getImageBounds(instance, properties.images, "normal");
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

setupCommonDirectImageEditing(CAknButton.prototype, "images", 
	null  	// areafunction
);


function drawButtonTextItem(instance, text, location, size, laf, 
					graphics, textOffsetForImage, fontFlags) {

	var x = location.x;
	var y = location.y;
	var width = size.x;
	var height = size.y;
	var parentIsToolbar = instance.parent.component.isOfType("com.nokia.sdt.series60.Toolbar");
	var font = laf.getFont("ButtonFont");
	graphics.setFont(font);
	
	var textMargin = 1;  
	if (!parentIsToolbar){
		textMargin = laf.getInteger("control.button.text.margin", 0);
	}
	if (size.x <= textMargin) {
		return; // will crash when drawing if no return
	}
	if (textOffsetForImage.x > textMargin){
		textMargin = textOffsetForImage.x
	} else {
		textMargin = textMargin + textOffsetForImage.x
	}
	
	var extent = font.stringExtent(text)
	
	var fontVertOffset = 0;
	if ((fontFlags & Font.VERTICAL_ALIGN_BOTTOM) != 0){
		fontVertOffset = (height - extent.y) - textMargin - textOffsetForImage.y;
	} else if ((fontFlags & Font.VERTICAL_ALIGN_CENTER) != 0){
		fontVertOffset = (height - extent.y) / 2 - (laf.getInteger("control.button.text.margin", 0)/2); // center
	} else {
		// top aligned
		fontVertOffset = textMargin + textOffsetForImage.y;
	}
	
	if (parentIsToolbar){
		// always vertically center text in toolbar
		fontVertOffset = ((height - extent.y) / 2) - 5; // center
	}
	
	if (laf.getBoolean("is.portrait", true)) {
		var fontHorizOffset = 0
		var rect = new Rectangle(x + fontHorizOffset, y + fontVertOffset, 
				width - (2*fontHorizOffset), height );

		rect.y = fontVertOffset;
		rect.height = extent.y + 5;
		rect.x = textMargin;
		rect.width = rect.width - textMargin;
		text = chooseScalableText(text, font, rect.width);
		if (text != ""){
			if (rect.width > 0 && rect.height > 0) {
				graphics.drawFormattedString(text, rect, fontFlags | Font.OVERFLOW_ELLIPSIS, 0);
			}
		}
	}
	else {
		var fontHeight = font.getHeight();
		var sbar1Bounds = laf.getRectangle("status.bar1.bounds");
		var sbar2Bounds = laf.getRectangle("status.bar2.bounds");
		
		var fontOffset = (sbar2Bounds.height - extent.y) / 2

		var rect = new Rectangle(x, y + fontOffset, width, height);
		
		rect.y = fontVertOffset;
		rect.height = extent.y + 5;
		rect.x = textMargin;
		rect.width = rect.width - textMargin*2;
		graphics.fillRectangle(x, y + sbar2Bounds.y, width, sbar2Bounds.height);
		text = chooseScalableText(text, font, rect.width);
		
		if (text != "" && rect.width > 0 && rect.height > 0) {
			graphics.drawFormattedString(text, rect, fontFlags | Font.OVERFLOW_ELLIPSIS, 0);
		}
	}
}


//////////////////////////////
// IDirectLabelEdit
//////////////////////////////
CAknButton.prototype.getPropertyPaths = function(instance) {
	return new Array("buttonText");
}

CAknButton.prototype.getLabelBounds = function(instance, propertyPath, laf) {
	return new Rectangle(0, 0,
		instance.properties.size.width, instance.properties.size.height);
}

CAknButton.prototype.getLabelFont = function(instance, propertyPath, laf) {
	return laf.getFont("ButtonFont");
}

//////////////////////////////////////////
// IComponentEventInfo
//////////////////////////////////////////
CAknButton.prototype.getEventGroups = function(instance) {
	// do not provide Control events when child of Toolbar
	if (instance.parent.component.isOfType("com.nokia.sdt.series60.Toolbar")){
		return ["ToolbarEvent"];
	} else {
		return ["CCoeControl"];
	}
}
