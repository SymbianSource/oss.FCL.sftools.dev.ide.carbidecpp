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


include("../debugLibrary.js")
include("../renderLibrary.js")

include("../embeddedControlImplLibrary.js")

//	Calculated drawing values
//	this.xValueLabelPos -- x coordinate for drawing the value label
//	this.yValueLabelPos -- y coordinate for drawing the value label
//	this.ySlider -- y coordinate for drawing the slider line
//	this.xSliderStart -- x coordinate for drawing the start of the slider line
//	this.xSliderEnd -- x coordinate for drawing the end of the slider line
//	this.xMinLabelPos -- x coordinate for drawing the min label
//	this.yMinLabelPos -- y coordinate for drawing the min label
//	this.xMaxLabelPos -- x coordinate for drawing the max label
//	this.yMaxLabelPos -- y coordinate for drawing the max label
//	this.requiredHeight -- calculated height for returning in getPreferredSize
//  this.requiredWidth -- calculated width for returning in getPreferredSize

CAknSliderVisual.prototype.printValues = function() {
	println("this.xValueLabelPos: " + this.xValueLabelPos);
	println("this.yValueLabelPos: " + this.yValueLabelPos);
	println("this.ySlider: " + this.ySlider);
	println("this.xSliderStart: " + this.xSliderStart);
	println("this.xSliderEnd: " + this.xSliderEnd);
	println("this.xMinLabelPos: " + this.xMinLabelPos);
	println("this.yMinLabelPos: " + this.yMinLabelPos);
	println("this.xMaxLabelPos: " + this.xMaxLabelPos);
	println("this.yMaxLabelPos: " + this.yMaxLabelPos);
	println("this.requiredHeight: " + this.requiredHeight);
}

function CAknSliderVisual() {
}

CAknSliderVisual.prototype.drawContent = function(instance, laf, graphics, rect) {
	var properties = instance.properties;
	this.calcLayout(instance, properties.layout, laf, rect);

	var y = rect.height/2;
	var image = this.getImage(instance, laf);
	var font = laf.getFont("NormalFont");
	graphics.setFont(font);
	var range = properties.maxValue - properties.minValue;
	
	var sliderStart = rect.x + this.xSliderStart;
	var sliderEnd = this.xSliderEnd;
	var sliderY = rect.y + this.ySlider;
	if (isSettingItemList(instance.parent))
		graphics.setForeground(Colors.getColor(255, 255, 255));
	else
		graphics.setForeground(Colors.getColor(0, 0, 0));
	graphics.setLineStyle(SWT.LINE_SOLID);
	graphics.drawLine(sliderStart, sliderY, sliderEnd, sliderY);
	
	if (image != null && range > 0) {
		// slider image is centered around value. Account for image
		// size so we can fully draw at the min and max values
		var imageBounds = image.getBounds();
		var valueWidth = sliderEnd - sliderStart - 8;
		
		var value = properties.value;
		if (value < properties.minValue)
			value = properties.minValue;
		else if (value > properties.maxValue)
			value = properties.maxValue;
			
		var xOffset = (((value - properties.minValue)/range) * 100) * valueWidth;
		xOffset = xOffset/100 -2;
		y = y - imageBounds.height/2;
		graphics.drawImage(image, sliderStart + xOffset, sliderY - imageBounds.height/2 + 1);
	}
	
	graphics.setBackground(getBackgroundColor(instance, laf))
	drawLabel(graphics, getFormattedValue(properties), this.xValueLabelPos, this.yValueLabelPos, rect);
	drawLabel(graphics, properties.minLabel, this.xMinLabelPos, this.yMinLabelPos, rect);
	drawLabel(graphics, properties.maxLabel, this.xMaxLabelPos - rect.x, this.yMaxLabelPos, rect);
}

CAknSliderVisual.prototype.getContentSize = function(instance, laf, size) {
	var parentWidth = instance.parent.properties.size.width;
	this.calcLayout(instance, instance.properties.layout, laf, new Rectangle(0, 0, parentWidth, size.y));
	return new Point(this.requiredWidth, this.requiredHeight);
}

setupEmbeddedRendering(CAknSliderVisual.prototype);

///////////

function formatSymbianString(str, val) {
	return str.replace("%U", val);
}

function getFormattedValue(properties) {
	var valueLabel = "";
	
	if (properties.valueType == "EAknSliderValueBareFigure") {
		if (properties.value == 1 && properties.singularValueLabel != "")
			valueLabel = formatSymbianString(properties.singularValueLabel, properties.value);
		else
			valueLabel = "" + properties.value;
	}
	else if (properties.valueType == "EAknSliderValuePercentage") {
		var range = properties.maxValue - properties.minValue;
		var val = properties.value - properties.minValue;
		var pct = (100 * val) / range;
		valueLabel = pct.toFixed() + "%";	
	}
	else if (properties.valueType == "EAknSliderValueFraction") {
		valueLabel = "" + properties.value + "/" + properties.maxValue;
	}
	else if (properties.valueType == "EAknSliderValueDecimal") {
		var decPlaces = properties.decimalPlaces;
		if (decPlaces < 0 || decPlaces > 9) {
			decPlaces = 0;
		}
		valueLabel = properties.value.toFixed(decPlaces);
	}
	
	return valueLabel;
}

function drawLabel(graphics, text, xPos, yPos, rect) {
	if (xPos >= 0 && xPos >= 0) {
		var availableWidth = rect.width - xPos;
		text = chooseScalableText(text, graphics.getFont(), availableWidth);
		graphics.drawString(text, rect.x + xPos, rect.y + yPos, true);
	}
}

function getContentBounds(instance, laf) {
	if (isForm(instance.parent))
		return getFormContentBounds(instance, laf);
	else if (isSettingItemList(instance.parent))
		return getSettingItemContentBounds(instance, laf);
	else
		return new Rectangle(0, 0, 
			instance.properties.size.width, instance.properties.size.height);
	
}

CAknSliderVisual.prototype.getMinLabelBounds = function(instance, laf) {
	var properties = instance.properties;
	var extent = getLabelFont(laf).formattedStringExtent("XXXXXX", new Point(0, 0), 0, 0);
	var rect = getContentBounds(instance, laf);
	return new Rectangle(rect.x + this.xMinLabelPos, rect.y + this.yMinLabelPos, 
																	extent.x, extent.y);
}

CAknSliderVisual.prototype.getMaxLabelBounds = function(instance, laf) {
	var properties = instance.properties;
	var extent = getLabelFont(laf).formattedStringExtent("XXXXXX", new Point(0, 0), 0, 0);
	var rect = getContentBounds(instance, laf);
	return new Rectangle(rect.x + this.xMaxLabelPos, rect.y + this.yMaxLabelPos, 
						extent.x, extent.y);
}

CAknSliderVisual.prototype.getValueLabelBounds = function(instance, laf) {
	var properties = instance.properties;
	var extent = getLabelFont(laf).formattedStringExtent("XXXXXX", new Point(0, 0), 0, 0);
	var rect = getContentBounds(instance, laf);
	return new Rectangle(rect.x + this.xValueLabelPos, rect.y + this.yValueLabelPos, 
						extent.x, extent.y);
}

CAknSliderVisual.prototype.getImage = function(instance, laf) {
	// TODO allow for custom image
	var image;
	if (isSettingItemList(instance.parent))
		image = laf.getImage("slider.settingslist.image")
	else
		image = laf.getImage("slider.image");
	return image;
}

function getInset(instance, laf) {
	var parentIsForm = isForm(instance.parent);
	var inset = laf.getInteger("form.divider.offset.single", 10) +
				2*getFormPadding(laf);
	if (parentIsForm && !isDoubleSpaced(instance.parent))
		inset = 0;
	return inset;
}

function getFormMargin(instance, laf) {
	if (isForm(instance.parent))
		return 10;
		
	return 0;
}

function getLabelFont(laf) {
	return laf.getFont("NormalFont");
}

function getRowSpacing(laf) {
	return laf.getInteger("slider.rowSpacing", 8);
}

CAknSliderVisual.prototype.calcFormLayout1 = function(instance, laf, rect) {
	// two "rows"
	// left aligned value label on top
	// slider below
	var properties = instance.properties;
	var font = getLabelFont(laf);
	var image = this.getImage(instance, laf);
	var inset = getInset(instance, laf);
	var rowSpacing = getRowSpacing(laf);
	var imageHeight = 11;
	var imageWidth = 11;
	if (image != null) {
		var imageBounds = image.getBounds();
		imageHeight = imageBounds.height;
		imageWidth = imageBounds.width;
	}
	this.xValueLabelPos = inset;
	this.yValueLabelPos = 0;
	this.ySlider = font.getHeight() + imageHeight/2 + rowSpacing;
	this.xSliderStart = inset;
	this.xSliderEnd = rect.width - 2*getFormPadding(laf);
	this.xMinLabelPos = -1;
	this.yMinLabelPos = -1;
	this.xMaxLabelPos = -1;
	this.yMaxLabelPos = -1;
	this.requiredHeight = font.getHeight() + imageHeight + rowSpacing
						+ getFormMargin(instance, laf);
	this.requiredWidth = instance.parent.properties.size.width;
}

CAknSliderVisual.prototype.calcFormLayout2 = function(instance, laf, rect) {
	// two "rows"
	// slider on top
	// min label on left, max label on right
	var properties = instance.properties;
	var font = getLabelFont(laf);
	var image = this.getImage(instance, laf);
	var inset = getInset(instance, laf);
	var rowSpacing = getRowSpacing(laf);
	var imageHeight = 11;
	var imageWidth = 11;
	if (image != null) {
		var imageBounds = image.getBounds();
		imageHeight = imageBounds.height;
		imageWidth = imageBounds.width;
	}
	this.ySlider = imageHeight/2;
	this.xSliderStart = inset;
	this.xSliderEnd = rect.width - 2*getFormPadding(laf);
	
	this.xMinLabelPos = inset;
	this.yMinLabelPos = imageHeight + rowSpacing;

	// may need to be smarter about available width
	var text = chooseScalableText(properties.maxLabel, font, rect.width);
	var maxLabelExtent = font.stringExtent(text);
	this.xMaxLabelPos = rect.width - maxLabelExtent.x;
	this.yMaxLabelPos = this.yMinLabelPos;
	
	this.xValueLabelPos = -1;
	this.yValueLabelPos = -1;
	this.requiredHeight = font.getHeight() + imageHeight + rowSpacing
						+ getFormMargin(instance, laf);
	this.requiredWidth = instance.parent.properties.size.width;
}

CAknSliderVisual.prototype.calcFormLayout3 = function(instance, laf, rect) {
	// three "rows"
	// 1: value on left
	// 2: slider
	// 3: min label on left, max label on right
	var properties = instance.properties;
	var font = getLabelFont(laf);
	var image = this.getImage(instance, laf);
	var inset = getInset(instance, laf);
	var rowSpacing = getRowSpacing(laf);
	var imageHeight = 11;
	var imageWidth = 11;
	if (image != null) {
		var imageBounds = image.getBounds();
		imageHeight = imageBounds.height;
		imageWidth = imageBounds.width;
	}
	this.xValueLabelPos = inset;
	this.yValueLabelPos = 0;
	this.ySlider = font.getHeight() + imageHeight/2 + rowSpacing;
	this.xSliderStart = inset;
	this.xSliderEnd = rect.width - 2*getFormPadding(laf);
	
	this.xMinLabelPos = inset;
	this.yMinLabelPos = imageHeight + font.getHeight() + 2*rowSpacing;

	// may need to be smarter about available width
	var text = chooseScalableText(properties.maxLabel, font, rect.width);
	var maxLabelExtent = font.stringExtent(text);
	this.xMaxLabelPos = rect.width - maxLabelExtent.x;
	this.yMaxLabelPos = this.yMinLabelPos;

	this.requiredHeight = 2*font.getHeight() + imageHeight + 2*rowSpacing;
						+ getFormMargin(instance, laf);
	this.requiredWidth = instance.parent.properties.size.width;
}

//	not used since we don't render the full screen
CAknSliderVisual.prototype.calcSettingsItemLayout_full = function(instance, laf, rect) {
	// four "rows"
	// 1: value label on left, indented
	// 2: blank
	// 3: slider
	// 4: min label on left, max label on right
	var properties = instance.properties;
	var font = getLabelFont(laf);
	var image = this.getImage(instance, laf);
	var rowSpacing = getRowSpacing(laf);
	var padding = getSILPaddingX(laf);
	var imageHeight = 11;
	var imageWidth = 11;
	if (image != null) {
		var imageBounds = image.getBounds();
		imageHeight = imageBounds.height;
		imageWidth = imageBounds.width;
	}
	var valueLabelIndent = 20; // TODO - determine indent
	this.xValueLabelPos = valueLabelIndent;
	this.yValueLabelPos = 0;
	
	var blankRowHeight = font.getHeight()*2; // TODO - determine this height
	
	this.ySlider = blankRowHeight + font.getHeight() + imageHeight/2 + rowSpacing;
	this.xSliderStart = 10;
	this.xSliderEnd = rect.width - (imageBounds.width/2 + 3*padding);
	
	this.xMinLabelPos = this.xSliderStart;
	this.yMinLabelPos = this.ySlider + imageHeight/2;

	// may need to be smarter about available width
	var text = chooseScalableText(properties.maxLabel, font, rect.width);
	var maxLabelExtent = font.stringExtent(text);
	this.xMaxLabelPos = rect.width - (maxLabelExtent.x + padding);
	this.yMaxLabelPos = this.yMinLabelPos;

	this.requiredHeight = this.yMaxLabelPos + maxLabelExtent.y;
	this.requiredWidth = rect.width - padding; 
}

// we show only the slider bar 
CAknSliderVisual.prototype.calcSettingsItemLayout = function(instance, laf, rect) {
	// one row
	// 1: slider
	var properties = instance.properties;
	var font = getLabelFont(laf);
	var image = this.getImage(instance, laf);
	var rowSpacing = getRowSpacing(laf);
	var padding = getSILPaddingX(laf);
	var imageHeight = 12;
	var imageWidth = 12;
	if (image != null) {
		var imageBounds = image.getBounds();
		imageHeight = imageBounds.height;
		imageWidth = imageBounds.width;
	}
	var blankRowHeight = font.getHeight();
	this.ySlider = blankRowHeight / 2 ; 
	this.xSliderStart = 10;
	this.xSliderEnd = rect.width - (imageBounds.width/2 + 3*padding);

	this.xValueLabelPos = -1;
	this.yValueLabelPos = -1;
	this.xMinLabelPos = -1;
	this.yMinLabelPos = -1;
	this.xMaxLabelPos = -1;
	this.yMaxLabelPos = -1;
	
	this.requiredHeight = font.getHeight();
	this.requiredWidth = rect.width - padding; 
}

CAknSliderVisual.prototype.calcSettingsItemLayoutWithGraphics = function(instance, laf, rect) {
	this.calcSettingsItemLayout(instance, laf, rect);
}

// use this form instead of switch()
layoutTable = {
	"EAknFormSliderLayout1" : CAknSliderVisual.prototype.calcFormLayout1,
	"EAknFormSliderLayout2" : CAknSliderVisual.prototype.calcFormLayout2,
	"EAknFormSliderLayout3" : CAknSliderVisual.prototype.calcFormLayout3,
	"EAknSettingsItemSliderLayout" : CAknSliderVisual.prototype.calcSettingsItemLayout,
	"EAknSettingsItemSliderLayoutWithGraphics" : CAknSliderVisual.prototype.calcSettingsItemLayoutWithGraphics
}

CAknSliderVisual.prototype.calcLayout = function(instance, layout, laf, rect) {
	// get the prototype function out of the table, and call the function
	// apply calls the function
	// this is passed explicitly and the other arguments are passed in an array
	layoutTable[layout].apply(this, [instance, laf, rect]);
}

// IDirectLabelEdit
CAknSliderVisual.prototype.getPropertyPaths = function(instance) {
	var properties = instance.properties;
	var paths = new Array;
	if (isForm(instance.parent))
		paths = paths.concat(["prompt"]);

	if (isSettingItemList(instance.parent))
		paths = paths.concat(["itemTitle", "compulsoryLabel"]);
	
	if (this.xValueLabelPos != -1)
		paths = paths.concat(["value"]);
	
	if (this.xMinLabelPos != -1)
		paths = paths.concat = ["minLabel", "maxLabel"];
	
	return paths;
}

CAknSliderVisual.prototype.getLabelBounds = function(instance, propertyPath, laf) {
	if (isForm(instance.parent)) {
		if (propertyPath == "prompt") {
			return getFormItemRectangles(instance, laf)[PROMPT_LABEL_RECT_INDEX];
		}
	}
	if (isSettingItemList(instance.parent)) {
		var rects = getSettingItemRectangles(instance, laf);
		if (propertyPath == "itemTitle") {
			return rects[SIL_TITLE_RECT_INDEX];
		}
		if (propertyPath == "compulsoryLabel") {
			return rects[SIL_INDICATOR_RECT_INDEX];
		}
	}
	
		
	var properties = instance.properties;
	var rect = getContentBounds(instance, laf);
	this.calcLayout(instance, properties.layout, laf, rect);
	
	if (propertyPath == "minLabel")
		return this.getMinLabelBounds(instance, laf);
	
	if (propertyPath == "maxLabel")
		return this.getMaxLabelBounds(instance, laf);

	if (propertyPath == "value")
		return this.getValueLabelBounds(instance, laf);
	
	return new Rectangle(0,0,0,0);
}

CAknSliderVisual.prototype.getFont = function(instance, propertyPath, laf) {
	if (propertyPath == "prompt")
		return getPromptFont(laf);
	
	return getLabelFont(laf);
}

CAknSliderVisual.prototype.propertyChanged = function(instance, propertyID, laf) {
	if (isForm(instance.parent) || isSettingItemList(instance.parent))
		return;
	var properties = instance.properties;
	if (propertyID == "layout") {
		var parentWidth = instance.parent.properties.size.width;
		this.calcLayout(instance, instance.properties.layout, laf, new Rectangle(0, 0, parentWidth, 0));
		if (this.requiredWidth == parentWidth && 
			instance.properties.location != 0) {
			instance.properties.location.x = 0;
		}
		instance.properties.size.width = this.requiredWidth;
		instance.properties.size.height = this.requiredHeight;
	}
	// we need to guard against recursive notifications when changing
	// the location property
	else if (propertyID == "location" && !this.inLocationChanged) {
		this.inLocationChanged = true;
		var parentWidth = instance.parent.properties.size.width;
		this.calcLayout(instance, instance.properties.layout, laf, new Rectangle(0, 0, parentWidth, 0));
		if (this.requiredWidth == parentWidth &&
			instance.properties.location != 0) {
			instance.properties.location.x = 0;
		}	
		this.inLocationChanged = false;
	}
}


setupCommonEmbeddedDirectImageEditing(CAknSliderVisual.prototype);
setupEmbeddedImagePropertyInfo(CAknSliderVisual.prototype);

	// note that laf will be null if a display model was not created
CAknSliderVisual.prototype.validate = function(instance, laf) {
	return null;
}

	// note that laf will be null if a display model was not created
CAknSliderVisual.prototype.queryPropertyChange = function(instance, propertyPath,
					newValue, laf) {
	var properties = instance.properties;
	var message = null;
	newValue = newValue - 0;
	if (propertyPath == "maxValue") {
		if (properties.minValue >= newValue || newValue < properties.value) {
			message = formatString(lookupString("maxValueConstraint"), 
				newValue, properties.minValue, properties.value);
		} else if ((newValue - properties.minValue) % properties.step != 0) {
			message = formatString(lookupString("minMaxStepConstraint"), 
				newValue, properties.maxValue, properties.minValue, 
				properties.maxValue - properties.minValue);
		}
	} else if (propertyPath == "minValue") {
		if (properties.maxValue <= newValue || newValue > properties.value) {
			message = formatString(lookupString("minValueConstraint"), 
				newValue, properties.maxValue, properties.value);
		} else if ((properties.maxValue - newValue) % properties.step != 0) {
			message = formatString(lookupString("minMaxStepConstraint"),
				newValue, properties.maxValue, properties.minValue, 
				properties.maxValue - properties.minValue);
		}
	} else if (propertyPath == "value") {
		if (properties.maxValue < newValue || newValue < properties.minValue) {
			message = formatString(lookupString("valueConstraint"), 
				newValue, properties.minValue, properties.maxValue);
		} else if ((newValue - properties.minValue) % properties.step != 0) {
			message = formatString(lookupString("valueStepConstraint"),
				newValue, properties.minValue, 
				newValue - properties.minValue, properties.step);
		}
	} else if (propertyPath == "step") {
		if ((properties.maxValue - properties.minValue) % newValue != 0) {
			message = formatString(lookupString("stepConstraint"),
				newValue, properties.maxValue, properties.minValue, 
				properties.maxValue - properties.minValue);
		} else if ((properties.value - properties.minValue) % newValue != 0 
			|| (properties.maxValue - properties.value) % newValue != 0) {
			message = formatString(lookupString("stepValueConstraint"),
				newValue, properties.value, properties.minValue, 
				properties.value - properties.minValue);
		}
	}
	return message;
}
