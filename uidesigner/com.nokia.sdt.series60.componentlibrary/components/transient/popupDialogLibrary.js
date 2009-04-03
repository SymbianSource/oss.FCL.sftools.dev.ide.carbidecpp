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


include("../renderLibrary.js")

// Note that the rendering is not quite accurate with long text, since
// text flows around the icon.

/**
 *	Get the width of a standard popup dialog
 */
function getStandardDialogWidth(laf) {
	var padding = laf.getInteger("note.padding", 8);
	var screenSize = laf.getDimension("screen.size");
	return Math.min(screenSize.x, screenSize.y) - 4 * padding;
}

/**
 *	Get the left edge of a popup dialog relative to its instance bounds 
 *	when the instance also draws a CBA 
 */
function getLeftOfDialogWithCBA(laf) {
	var width = getStandardDialogWidth(laf);
	if (laf.getBoolean("is.portrait", false)) {
		var contentBounds = laf.getRectangle("content.pane.bounds");
		return contentBounds.x + (contentBounds.width - width) / 2; 
	}

	return 0; // in landscape the dialog and instance are the same
}

/**
 *	Get the rectangle in which to draw the icon
 */
function getIconRect(dialogLeft, laf) {
	var iconSize = laf.getDimension("note.icon.size");
	var padding = laf.getInteger("note.padding", 2);
	var width = getStandardDialogWidth(laf);
	var iconRect = new Rectangle(
			width - iconSize.x - padding*2, 
			0, iconSize.x, iconSize.y);
	return iconRect;
}

/**
 *	Calculate the bounding rectangle,
 *	adjusting the height to contain all the text.
 *	@param flags the Font flags
 */
function calculateBounds(properties, laf, flags, iconLeft, dialogLeft) {
	var rect = new Rectangle(0, 0, 0, 0);
	
	var portrait = laf.getBoolean("is.portrait", true);
	var d = laf.getDimension("screen.size");
	var content = laf.getRectangle("content.pane.bounds");
	
	var padding = laf.getInteger("note.padding", 2);
	rect.width = Math.min(d.x, d.y) - padding*2;
	if (portrait) {
		rect.x = content.x + (content.width - rect.width) / 2; 
	}
	else {
		rect.x = d.x - rect.width;
	}
		
	var font = laf.getFont("message.font");

	var info = getTextInfo(properties, laf, font, flags, iconLeft, dialogLeft);
	var extent = info[0];
	var text = info[1];
	var maxWidth = info[2];
	var lineGap = info[3];
	
	var height = extent.y + 7*2 + lineGap
	
	if (portrait)
		rect.y = content.y + content.height - height - padding;
	else
		rect.y = (d.y - height) / 2;

	rect.height = height;
	
	// adjust for insets
	var inset = laf.getDimension("note.inset");
	rect.x += inset.x;
	rect.width -= (2 * inset.x);
	rect.height -= (2 * inset.y);
	
	// add pixels for shadow
	rect.width += 3;
	rect.height += 3;
	
	return rect;
}

function getTextInfo(properties, laf, font, flags, iconLeft, dialogLeft) {
	var padding = laf.getInteger("note.padding", 8);

	var maxWidth = iconLeft - padding*(3+2) - dialogLeft;
	
	var lineGap = laf.getInteger("note.text.lineGap", 0);
	var minExtent = font.formattedStringExtent("Hello",
		new Point(maxWidth, 0), flags, lineGap);
	minExtent.y *= 3;
	var text = chooseScalableText(properties.text, font, maxWidth);
	var extent = calculateWrappedTextSize(text, maxWidth, font, flags, lineGap, 5);

	if (extent.y < minExtent.y)
		extent.y = minExtent.y;

	return [extent, text, maxWidth, lineGap];
}

function isNote(instance) {
	return (instance.isInstanceOf("com.nokia.sdt.series60.StandardNote") ||
		    instance.isInstanceOf("com.nokia.sdt.series60.GlobalNote"));
}

function isConfirmationQuery(instance) {
	return instance.isInstanceOf("com.nokia.sdt.series60.ConfirmationQuery");
}

function isProgress(instance) {
	// add progress dialog here when we add that component
	return instance.isInstanceOf("com.nokia.sdt.series60.WaitDialog");
}

function isDataQuery(instance) {
	return instance.isInstanceOf("com.nokia.sdt.series60.SingleLineDataQuery") ||
		instance.isInstanceOf("com.nokia.sdt.series60.MultiLineDataQuery");
}

function getIcon(instance, laf) {
	var properties = instance.properties;
	if (isNote(instance)) {
		if (properties.type != null) {
			var fileBase = "note." + properties.type.toString() + ".icon";
			return laf.getImage(fileBase);
		}
	}
	else if (isConfirmationQuery(instance)) {
		return laf.getImage("note.query.icon");
	} else if (isProgress(instance)) {
		return laf.getImage("note.progress.icon");
	}
	
	return null;
}

function drawPopupDialog(instance, laf, graphics, flags, bounds, iconRect, dialogLeft) {
	var properties = instance.properties;
	var font = laf.getFont("message.font");
	graphics.setFont(font);
	
	var padding = laf.getInteger("note.padding", 8);
	
	// get bounding rects
	var info = getTextInfo(properties, laf, font, flags, iconRect.x, dialogLeft);
	var extent = info[0];
	extent.x += dialogLeft;
	var text = info[1];
	var maxWidth = info[2];
	var lineGap = info[3];

	var x = bounds.x;
	var y = bounds.y;
	var width = bounds.width - 3;
	var height = bounds.height - 3;

	// fill 
	graphics.setBackground(getBackgroundColor(instance, laf))
	graphics.fillRectangle(new Rectangle(x, y, width, height))
	
	// edge
	graphics.setForeground(Colors.getColor(0, 0, 0))
	graphics.drawRectangle(new Rectangle(x, y, width, height))

	// shadows
	graphics.setForeground(laf.getColor("control.shadow.inner"))
	graphics.drawLine(x + 1, y + height + 1, x + width + 1, y + height + 1)
	graphics.drawLine(x + width + 1, y + 1, x + width + 1, y + height + 1)
	
	graphics.setForeground(laf.getColor("control.shadow.outer"))
	graphics.drawLine(x + 2, y + height + 2, x + width + 2, y + height + 2)
	graphics.drawLine(x + width + 2, y + 2, x + width + 2, y + height + 2)

	graphics.setForeground(laf.getColor("listitem.text"));
	
	// draw text
	var textRect = new Rectangle(padding*4 + bounds.x, padding*3 + bounds.y, 
		iconRect.width + iconRect.x - padding*4, bounds.height - padding*3);

	// get widths of each text line, where the first two are on
	// the same line as the icon
	var widths = [ 
		extent.x, // first two lines ...
		extent.x, // ... go to the edge of the icon
		textRect.width ];	// and the remaining lines fill the dialog
	
	//println("textRect="+textRect);
	graphics.drawFormattedString(text,
			textRect,
			flags | Font.OVERFLOW_ELLIPSIS,
			lineGap,
			widths);

	// draw icon
	var image = getIcon(instance, laf);
	if (image != null) {
		var imageData = image.getImageData();
		graphics.drawImage(image, 0, 0, imageData.width, imageData.height,
			dialogLeft + iconRect.x, bounds.y + iconRect.y, iconRect.width, iconRect.height);
	}
	
	// progress bar
	if (isProgress(instance)) {
		drawProgressBar(instance, graphics, laf, bounds);
	}
}

function drawProgressBar(instance, graphics, laf, bounds) {
	var isPortrait = laf.getBoolean("is.portrait", true);
	var padding = laf.getInteger("note.padding", 8);
	var progressX = padding*4+bounds.x;
	var progressHeight = laf.getInteger("progress.bar.height", 10);
	var progressWidth = bounds.width - (padding*8);
	var progressY = bounds.y + bounds.height - laf.getInteger("note.progress.offsetFromBottom", 0);
	var image = laf.getImage("note.progress.bar");
	var imageData = image.getImageData();
	graphics.drawImage(image, 0, 0, imageData.width, imageData.height,
			progressX, progressY, progressWidth, progressHeight);

}

function getChildAttribute(instance, childIndex, attributeName) {
	var children = instance.children;
	if (children.length > childIndex) {
		var child = instance.children[childIndex];
		return child.attributes[attributeName];
	}
	
	return null;
}

/**
 *	Combine two bounds into one, vertically stacking them, getting
 *	the maximum combined horizontal extent, and adding vertical
 *	padding in between.
 *	@param rect1 one rectangle
 *	@param rect2 another rectangle
 *	@return a rectangle at y=0.
 */
function getStackedBounds(rect1, rect2) {
	var height = rect1.height + this.padding + rect2.height;
	var maxX = Math.max(rect1.x + rect1.width, rect2.x + rect2.width);
	var minX = Math.min(rect1.x, rect2.x);
	return new Rectangle(minX, 0, maxX - minX, height);
}

//////////////////////////////////////////////////////////////
// Popup Visual Helper
//////////////////////////////////////////////////////////////

include("../renderLibrary.js")

/**
 *
 * Required prototype implementations
 * 	IVisualAppearance
 * 	ILayout
 * 	IDirectLabelEdit
 *
 * Required additional prototype functions:
 * getIconRect();
 *
 */
function setupPopupVisualHelper(prototype) {

/**
 *	Calculate the text bounds for a dialog, allowing for an icon,
 *	wrapping, and a range of allowed lines.  This assumes the
 *	width of the dialog to start with.
 *	@return bounds of text in dialog
 */
prototype.calculateTextBounds = function(properties, laf, text, font, flags, minLines, maxLines) {
	var rect = new Rectangle(0, 0, 0, 0);
	
	var portrait = laf.getBoolean("is.portrait", true);
	var d = laf.getDimension("screen.size");
	var content = laf.getRectangle("content.pane.bounds");
	
	var padding = laf.getInteger("note.padding", 2);
	if (portrait) {
		rect.x = padding;
		rect.width = d.x - padding*2;
	}
	else {
		rect.x = d.x / 5;
		rect.width = d.x - rect.x;
	}
		
	var iconRect = this.getIconRect();
	var maxWidth = rect.width - iconRect.x - padding*(3+2);
	
	var lineGap = laf.getInteger("note.text.lineGap", 0);
	
	var minExtent = font.formattedStringExtent("Hello", new Point(maxWidth, 0), flags, lineGap);
	minExtent.y *= minLines;
	
	var text = chooseScalableText(properties.text, font, maxWidth);
	var extent;
	
	if ((flags & Font.WRAPPING_ENABLED) != 0)
		extent = calculateWrappedTextSize(text, maxWidth, font, flags, lineGap, maxLines);
	else
		extent = font.formattedStringExtent(text, 
			new Point(maxWidth, 0), flags, lineGap);
	
	//println("extent="+extent);
	if (extent.y < minExtent.y)
		extent.y = minExtent.y;

	return new Rectangle(rect.x, rect.y, extent.x, extent.y);
}

/**
 *	Calculate the bounding rectangle for the dialog so it contains the
 *	text, icon, and editor.
 *	@param textExtent size of text
 *	@param editRect rect for editor content at bottom (only size used)
 *	@return Rectangle (0, 0, width, height)
 */
prototype.calculateDialogBounds = function(properties, laf, textExtent, editRect) {
	var rect = new Rectangle(0, 0, 0, 0);
	
	var portrait = laf.getBoolean("is.portrait", true);
	var d = laf.getDimension("screen.size");
	var content = laf.getRectangle("content.pane.bounds");
	
	var padding = laf.getInteger("note.padding", 2);
	if (portrait) {
		rect.x = padding;
		rect.width = d.x - padding*2;
	}
	else {
		rect.x = d.x / 5;
		rect.width = d.x - rect.x;
	}
		
	var lineGap = laf.getInteger("note.text.lineGap", 0);
	var height = textExtent.y + 7*2 + lineGap
	
	if (editRect)
		height += editRect.height + lineGap*2;
	
	rect.height = height;
	
	// adjust for insets
	var inset = laf.getDimension("note.inset");
	rect.x += inset.x;
	rect.width -= (2 * inset.x);
	rect.height -= (2 * inset.y);
	
	return rect;
}


//	Draw a simple dialog with using the 'text' property and the label font
prototype.drawPopupDialog = function(instance, laf, graphics, font, flags, textRect, bounds) {
	var properties = instance.properties;

	this.drawPopupDialogBorder(instance, laf, graphics, bounds);

	var text = chooseScalableText(properties.text, font, textRect.width);
	
	var offset = new Point(bounds.x, bounds.y);
	this.drawPopupDialogPromptAndIcon(instance, laf, graphics, text, font, flags, offset, textRect);
}

/**
 *	Draw dialog prompt and icon.
 *	@param flags text flags
 *	@param offset Point offset at which to bias the iconRect and textRect
 */
prototype.drawPopupDialogPromptAndIcon = function(instance, laf, graphics, text, font, flags, offset, textRect) {
	var properties = instance.properties;
	graphics.setFont(font);

	var padding = laf.getInteger("note.padding", 8);
	
	graphics.setForeground(laf.getColor("listitem.text"));

	// draw text
	textRect.x = properties.location.x + offset.x;
	textRect.y = properties.location.y + offset.y;
	
	//println("drawPopupDialogPromptAndIcon textRect = " + textRect);
	graphics.drawFormattedString(text,
			textRect,
			flags | Font.OVERFLOW_ELLIPSIS,
			laf.getInteger("note.text.lineGap", 0));

	// draw icon
	var iconRect = this.getIconRect();
	var image = getIcon(instance, laf);
	if (image != null) {
		var imageData = image.getImageData();
		graphics.drawImage(image, 0, 0, imageData.width, imageData.height,
			iconRect.x, offset.y + iconRect.y, iconRect.width, iconRect.height);
	}
}

/**
 *	Draw dialog border, with shadows.  The bounds should enclose only
 *	the contents of the dialog (shadows drawn outside).
 */
prototype.drawPopupDialogBorder = function(instance, laf, graphics, bounds) {
	var padding = laf.getInteger("note.padding", 8);
	
	// get bounding rects
	
	var x = bounds.x;
	var y = bounds.y;
	var width = bounds.width;
	var height = bounds.height;

	// fill 
	graphics.setBackground(getBackgroundColor(instance, laf))
	graphics.fillRectangle(new Rectangle(x, y, width, height))
	
	// edge
	graphics.setForeground(Colors.getColor(0, 0, 0))
	graphics.drawRectangle(new Rectangle(x, y, width, height))

	// shadows
	graphics.setForeground(laf.getColor("control.shadow.inner"))
	graphics.drawLine(x + 1, y + height + 1, x + width + 1, y + height + 1)
	graphics.drawLine(x + width + 1, y + 1, x + width + 1, y + height + 1)
	
	graphics.setForeground(laf.getColor("control.shadow.outer"))
	graphics.drawLine(x + 2, y + height + 2, x + width + 2, y + height + 2)
	graphics.drawLine(x + width + 2, y + 2, x + width + 2, y + height + 2)
}

} // setupPopupVisualHelper

function getPreviewPopUpHeadingFont(laf) {
	return laf.getFont("message.font");
}

function getPreviewPopUpHeadingHeight(instance, laf) {
	if (instance.properties.headingText.length > 0) {
		var font = getPreviewPopUpHeadingFont(laf);
		var height = getFontHeight(font);
		var padding = height / 4;
		return height + 2 * padding;
	}
	
	return 0;
}

function drawFrame(rect, colorArray, graphics) {
	var saveColor = graphics.getForeground();
	for(var i in colorArray) {
		var color = colorArray[i];
		if (color != null) {
			graphics.setForeground(color);
			var sizeOffset = 2 * i;
			graphics.drawRectangle(rect.x + i, rect.y + i, rect.width - sizeOffset, rect.height - sizeOffset);
		}
	}
	graphics.setForeground(saveColor);
}

function drawPreviewPopUpHeading(instance, graphics, laf) {
	var properties = instance.properties;
	var width = properties.size.width;
	var height = properties.size.height
	var drawButton = !properties.EPermanentMode;
	var font = getPreviewPopUpHeadingFont(laf);
	graphics.setFont(font);
	var fontHeight = getFontHeight(font);
	var padding = fontHeight / 8;
	var textWidth = width - fontHeight - 2 * padding; // room for close button
	var textRect = new Rectangle(6 + padding, 6 + padding, textWidth, fontHeight + padding);
	graphics.setBackground(getBackgroundColor(instance, laf));
	if (!properties.EFixedMode) {
		var gradColor = Colors.getColor(214,223,222);
		graphics.setForeground(gradColor);
		var headingRect = new Rectangle(6, 6, width - 6, textRect.height + padding + 4);
		graphics.fillGradientRectangle(headingRect.x, headingRect.y, headingRect.width, headingRect.height, true);
		graphics.setLineWidth(1);
		graphics.setForeground(Colors.getColor(239,239,239));
		graphics.drawLine(headingRect.x, headingRect.y + headingRect.height, 
							headingRect.x + headingRect.width, headingRect.y + headingRect.height);
		graphics.setForeground(Colors.getColor(239,235,239));
		graphics.drawLine(headingRect.x, headingRect.y + headingRect.height + 1, 
							headingRect.x + headingRect.width, headingRect.y + headingRect.height + 1);
		if (drawButton) {
			var buttonSize = 22;
			var buttonRect = new Rectangle(width - buttonSize - 8, padding + 8, buttonSize, buttonSize);
			graphics.setForeground(getBackgroundColor(instance, laf));
			graphics.setBackground(gradColor);
			graphics.fillGradientRectangle(buttonRect.x, buttonRect.y, buttonRect.width, buttonRect.height, true);
			var middleRect = new Rectangle(
								buttonRect.x + buttonRect.width / 2 - 1, 
								buttonRect.y + buttonRect.height / 2 - 1,
								2, 2);
			graphics.setForeground(Colors.getColor(84,96,127));
			graphics.drawRectangle(middleRect);
			graphics.drawRectangle(buttonRect);
			textRect.width -= buttonRect.width;
		}
	}
	else {
		graphics.fillRectangle(0, 0, width, height);
	}
	graphics.setForeground(Colors.getColor(84,96,127));
	var titleText = chooseScalableText(properties.headingText, font, textRect.width);
	graphics.drawFormattedString(titleText, textRect, Font.ALIGN_LEFT | Font.OVERFLOW_ELLIPSIS, 0);	
}

