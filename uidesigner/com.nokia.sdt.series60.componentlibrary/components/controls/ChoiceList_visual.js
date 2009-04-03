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

choiceListStrings = getLocalizedStrings("ChoiceList");

function CAknChoiceListVisual() {
}

CAknChoiceListVisual.prototype.draw = function(instance, laf, graphics) {
	
	var borderPadding = 3;
	var width = instance.properties.size.width - borderPadding;
	var height = instance.properties.size.height - borderPadding;
	var x = 0;
	var y = 0;
	
	if (instance.properties.showAsButton.showAsButtonValue == true) {

		// fill 
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
		
		if (instance.properties.showAsButton.images.bmpfile != ""){
			var imgWidthOffset = 0;
			if (instance.properties.showAsButton.buttonText != "") {
				// there's an image and text...
				var imageBounds = getImageBounds(instance, instance.properties.showAsButton.images, "normal")
				imgWidthOffset = imageBounds.width
				graphics.setForeground(laf.getColor("EEikColorButtonText"))
				drawButtonTextItem(instance.properties.showAsButton.buttonText, new Point(0, 0), new Point(width, height), laf, graphics, imgWidthOffset);
			}
			// draw the specified image on the button
			renderImage(CAknChoiceListVisual.prototype, instance, laf, graphics, 
				0, 0, "showAsButton.images", true, "normal");
		} else {
			graphics.setForeground(laf.getColor("EEikColorButtonText"))
			if (instance.properties.showAsButton.buttonText == "") {
				//drawButtonTextItem("", new Point(0, 0), new Point(width, height), laf, graphics, 0);
			} else {
				drawButtonTextItem(instance.properties.showAsButton.buttonText, new Point(0, 0), new Point(width, height), laf, graphics, 0);
			}
		}
	} else {
		// show as combo box
		// 1) Get the pop-up menu icon drawn on the RHS
		var iconRect = getIconRect(instance.properties, laf);
		var image = getPopupSelectorIcon(laf);
		// draw icon
		if (image != null) {
			var imageData = image.getImageData();
			graphics.drawImage(image, 0, 0, imageData.width, imageData.height,
				width-imageData.width+borderPadding, 0, imageData.width, imageData.height);
		}
		graphics.setForeground(laf.getColor("EEikColorButtonText"))
		
		// 2) Get the text for the default index and draw that
		var defaultIndex = instance.properties.defaultMenuIndex
		var imageData = image.getImageData();
		var textMargin = laf.getInteger("control.button.text.margin", 0);
		if (instance.properties.size.width > imageData.width + textMargin) {
			if (instance.properties.items.length > 0){
			    if (defaultIndex > instance.properties.items.length-1){
			    	var text = choiceListStrings.getString("BadIndexPopupText");
			    	drawPopupTextItem(instance, text, new Point(0, 0), new Point(width, height), laf, graphics, imageData.width, true);
			    }
			    else {
					drawPopupTextItem(instance, instance.properties.items[defaultIndex].itemText, new Point(0, 0), new Point(width, height), laf, graphics, imageData.width, false);
				}
			} else {
				var text = choiceListStrings.getString("AddItemsPopupText");
				drawPopupTextItem(instance, text, new Point(0, 0), new Point(width, height), laf, graphics, imageData.width, true);
			}
		}	
	}
	
	// TODO: move to propertychange event, don't do layout changes under draw.
	if (instance.parent.component.isOfType("com.nokia.sdt.series60.Toolbar")){
		instance.parent.parent.forceLayout(); // force the view to resize the toolbar layout
	} 
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

CAknChoiceListVisual.prototype.getViewableSize = function(instance, propertyId, laf) {
	var	size = instance.properties.size;
	return new Point(size.width, size.height);
}

CAknChoiceListVisual.prototype.isScaling = function(instance, propertyId, laf) {
	//return isScalingIcons();
	// Button doesn't appear to have any way to scale the icon currently
	return false;
}

CAknChoiceListVisual.prototype.getAlignmentWeights = function(instance, propertyId, laf) {
		// If there's no button text align in the center, else align on the left
		if (instance.properties.showAsButton.buttonText == "") {
			return new Point(ImageUtils.ALIGN_CENTER_OR_TOP, ImageUtils.ALIGN_CENTER_OR_TOP);
		} else {
			// There's text and button so the image is left aligned
			return new Point(ImageUtils.ALIGN_LEFT, ImageUtils.ALIGN_CENTER_OR_TOP);
		}
}

CAknChoiceListVisual.prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
	return true;
}


CAknChoiceListVisual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	var properties = instance.properties;

	var width = -1;
	var height = -1;
	if (wHint >= 0)
		width = wHint;
	if (hHint >= 0)
		height = hHint;

	var imgBounds = getImageBounds(instance, properties.showAsButton.images, "normal");
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

setupCommonDirectImageEditing(CAknChoiceListVisual.prototype, "showAsButton.images", 
	null  	// areafunction
);

function drawButtonTextItem(text, location, size, laf, graphics, imgWidthOffset) {
	
	var x = location.x;
	var y = location.y;
	var width = size.x;
	var height = size.y;
	
	var font = laf.getFont("menuitem.font");
	graphics.setFont(font);
	var textMargin = laf.getInteger("control.button.text.margin", 0);
	if (size.x <= textMargin) {
		return; // will crash when drawing if no return
	}
	if (imgWidthOffset > textMargin){
		textMargin = imgWidthOffset
	} else {
		textMargin = textMargin + imgWidthOffset
	}
	
	var extent = font.stringExtent(text)
	var fontVertOffset = (height - extent.y) / 2
	if (laf.getBoolean("is.portrait", true)) {
		var fontHorizOffset = 0
		if (fontHorizOffset < 0)
			fontHorizOffset = 0;
		var rect = new Rectangle(x + fontHorizOffset, y + fontVertOffset, 
				width - (2*fontHorizOffset), height );
	
		rect.y = fontVertOffset;
		rect.height = extent.y;
		rect.x = textMargin;
		rect.width = rect.width - textMargin;
		text = chooseScalableText(text, font, rect.width);
		if (text != null && rect.width > 0) {
			graphics.drawFormattedString(text, rect, Font.ALIGN_CENTER | Font.OVERFLOW_ELLIPSIS, 0);
		}
	}
	else {
		var fontHeight = font.getHeight();
		var sbar1Bounds = laf.getRectangle("status.bar1.bounds");
		var sbar2Bounds = laf.getRectangle("status.bar2.bounds");
		
		var fontOffset = (sbar2Bounds.height - extent.y) / 2

		var rect = new Rectangle(x, y + fontOffset, width, height-fontOffset);
		
		rect.y = fontVertOffset;
		rect.height = sbar2Bounds.height - fontOffset;
		rect.x = textMargin;
		rect.width = rect.width - textMargin;
		graphics.fillRectangle(x, y + sbar2Bounds.y, width, sbar2Bounds.height);
		text = chooseScalableText(text, font, rect.width);
		graphics.drawFormattedString(text, rect, Font.ALIGN_CENTER | Font.OVERFLOW_ELLIPSIS, 0);
	}
}

function drawPopupTextItem(instance, text, location, size, laf, graphics, imageWidth, grayout) {
	
	var textMargin = laf.getInteger("control.button.text.margin", 0);
	
	var x = location.x + textMargin;
	var y = location.y;
	var width = size.x - (imageWidth + textMargin);
	var height = size.y;
	
	var font = laf.getFont("menuitem.font");
	graphics.setFont(font);
	
	var margin = 0; //laf.getInteger("control.pane.text.margin", 5);
	var extent = font.stringExtent(text)
	
	
	var fontOffset = (height - extent.y) / 2

	var rect = new Rectangle(x + margin, y + fontOffset, 
			width - (2*margin), height );

	graphics.fillRectangle(x, y, width, height);

	text = chooseScalableText(text, font, rect.width);
	if (size.x > imageWidth && width - (2*margin) > 0){
		graphics.setBackground(getBackgroundColor(instance, laf));
		if (grayout)
			graphics.setForeground(Colors.getColor(192, 192, 192));
		graphics.drawFormattedString(text, rect, Font.ALIGN_LEFT, 0);
	}

}

// get the regular icon or the small popup icon depending on screen res.
function getPopupSelectorIcon(laf){
	if (laf) {
		var isPortrait = laf.getBoolean("is.portrait", true);
		var contentRect = laf.getRectangle("content.pane.bounds");
		if ((isPortrait && contentRect.width <= 240) || (!isPortrait && contentRect.height <= 240)){
			var image = laf.getImage("popup.selector.icon.small");
		} else {
			var image = laf.getImage("popup.selector.icon");
		}
		
		return image;
	} else {
		return null;
	}
}


///////////////////////////////////
// IComponentInstancePropertyListener
///////////////////////////////////
CAknChoiceListVisual.prototype.propertyChanged = function(instance, property) {
	if ((property == "size") || (property == "showAsButton")) {
		// if height has changed and it's not a button, then force the height
		if (instance.properties.showAsButton.showAsButtonValue == false){
			var laf = findExistingLookAndFeel(instance);
			var image = getPopupSelectorIcon(laf);
			if (image != null) {
				var imageData = image.getImageData();
				if (instance.properties.size.height != imageData.height){
					instance.properties.size.height = imageData.height;
					instance.forceLayout();
				}
			}
		}
	}
}

CAknChoiceListVisual.prototype.getPropertyPaths = function(instance) {
	var properties = instance.properties;
	if (properties.showAsButton.showAsButtonValue == true){
		return new Array("showAsButton.buttonText");
	} else {
		return null
	}
}

CAknChoiceListVisual.prototype.getLabelBounds = function(instance, propertyPath, laf) {
	return new Rectangle(0, 0,
		instance.properties.size.width, instance.properties.size.height);
}

CAknChoiceListVisual.prototype.getLabelFont = function(instance, propertyPath, laf) {
	return laf.getFont("navi.text.font");
}


///////////////////////////////////
// IComponentValidator
///////////////////////////////////
CAknChoiceListVisual.prototype.validate = function(instance) { 
	var properties = instance.properties;
	var messages = null;
	if (properties.items.length == 0){
		messages = new java.util.ArrayList();
		messages.add(createSimpleModelError(instance, 
			"items", 
			lookupString("noMenuItems"), 
			[instance.name]));
	}
	
	if (properties.defaultMenuIndex > properties.items.length-1){
		messages = new java.util.ArrayList();
		messages.add(createSimpleModelError(instance, 
			"defaultMenuIndex", 
			lookupString("indexOutOfRange"), 
			[instance.name]));
	}
	
	return messages;

}

CAknChoiceListVisual.prototype.queryPropertyChange = function(instance, propertyPath,
					newValue, laf) {
	return null;
}

//////////////////////////////////////////
// IComponentEventInfo
//////////////////////////////////////////
CAknChoiceListVisual.prototype.getEventGroups = function(instance) {
	// do not provide Control events when child of Toolbar
	if (instance.parent.component.isOfType("com.nokia.sdt.series60.Toolbar")){
		return ["ToolbarEvent"];
	} else {
		return ["CCoeControl"];
	}
}

//////////////////////////////
// ILayout
//////////////////////////////
CAknChoiceListVisual.prototype.layout = function(instance, laf) {
	if (instance.properties.showAsButton.showAsButtonValue == false){
		var laf = findExistingLookAndFeel(instance);
		var image = getPopupSelectorIcon(laf);
		if (image != null) {
			var imageData = image.getImageData();
			if (instance.properties.size.height != imageData.height){
				instance.properties.size.height = imageData.height;
				instance.forceRedraw();
			}
		}
	}
}

CAknChoiceListVisual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null;
}

