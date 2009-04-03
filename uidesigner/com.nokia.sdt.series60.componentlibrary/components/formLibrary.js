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



DRAW_BOUNDING_RECTS = false;

include("renderLibrary.js")
include("implLibrary.js")
include("srcgenLibrary.js")

var FORM_ID = "com.nokia.sdt.series60.CAknForm";

function isForm(formInstance) {
	return formInstance.componentId == FORM_ID;
}

function isDoubleSpaced(formInstance) {
	return formInstance.properties.EEikFormUseDoubleSpacedFormat;
}

function isShowingBitmaps(formInstance) {
	return formInstance.properties.EEikFormShowBitmaps;
}
		
function getFormSingleDividerOffset(formInstance, laf) {
	return laf.getInteger("form.divider.offset.single", 20);
}

function getFormPromptDividerOffset(formInstance, laf) {
	if (isDoubleSpaced(formInstance)) {
		if (isShowingBitmaps(formInstance))
			return laf.getInteger("form.inset", 20);
		else
			return laf.getInteger("form.divider.offset.double", 6);
	}
	return getFormSingleDividerOffset(formInstance, laf);
}

function getFormLineGap(laf) {
	return laf.getInteger("form.padding", 5);
}

function getFormPadding(laf) {
	return laf.getInteger("form.padding", 5);
}

/**
 *	Get all the rectangles associated with an item in a form.
 *
 *	@param rect the item's rectangle (i.e. an entire row)
 * @returns an array with:
 *
 *	0) Rectangle prompt bounds, as a whole
 *	1) Rectangle content bounds, as a whole
 *	2) Rectangle prompt image bounds, or null
 *	3) Rectangle prompt label bounds
 *	4) int column where divider is drawn
 */
var FORM_PROMPT_RECT_INDEX = 0;
var FORM_CONTENT_RECT_INDEX = 1;
var FORM_PROMPT_IMAGE_RECT_INDEX = 2;
var FORM_PROMPT_LABEL_RECT_INDEX = 3;
var FORM_DIVIDER_OFFSET_INDEX = 4;

function getFormItemRectanglesInRect(instance, laf, rect) {
	var formInstance = instance.parent;
	var dividerOffset = getFormPromptDividerOffset(instance.parent, laf);

	var bmsize = laf.getDimension("form.image.size").x;
	
	var promptRect, contentRect, promptImageRect = null, promptLabelRect;
	
	// NOTE: S60 seems to NOT add padding for prompts, but we do it anyway
	if (isDoubleSpaced(formInstance)) {
		var promptExtent = getFontHeight(getFormPromptFont(laf));
	
		//		println("rect="+rect+", dividerOffset="+dividerOffset+", promptExtent="+promptExtent);
		promptRect = new Rectangle(rect.x + dividerOffset + getFormPadding(laf), rect.y + getFormPadding(laf), 
			rect.width - dividerOffset - getFormPadding(laf), promptExtent);
		promptLabelRect = new Rectangle(promptRect.x, promptRect.y,
			promptRect.width, promptRect.height);
			
		if (isShowingBitmaps(formInstance)) {
			// image is on left side of divider, flush right
			promptImageRect = new Rectangle(
				rect.x + dividerOffset - bmsize - getFormPadding(laf)/3, 
				rect.y,
				bmsize,
				bmsize);
				
		}
		
		var leftMargin = dividerOffset + getFormPadding(laf) * 2;
		contentRect = new Rectangle(rect.x + leftMargin, 
				rect.y + promptExtent + getFormLineGap(laf),
				rect.width - leftMargin, 
				rect.height - promptExtent);
		
	} else {
		promptRect = new Rectangle(rect.x, rect.y, 
						dividerOffset - getFormPadding(laf), rect.height);

		if (isShowingBitmaps(formInstance)) {
			// image is on left side of label, flush left
			promptImageRect = new Rectangle(
				promptRect.x, 
				promptRect.y + (promptRect.height - bmsize) / 2,
				bmsize,
				bmsize);

			promptLabelRect = new Rectangle(
				promptRect.x + bmsize + 2, promptRect.y, 
				promptRect.width - bmsize, promptRect.height);
		} else {
			promptLabelRect = new Rectangle(
				promptRect.x, promptRect.y, 
				promptRect.width, promptRect.height);
		}
		
		var leftMargin = dividerOffset + getFormPadding(laf);
		contentRect = new Rectangle(rect.x + leftMargin, rect.y, 
				rect.width - leftMargin, rect.height);
		
	}
		
	var rects = [ promptRect, contentRect, promptImageRect, promptLabelRect, dividerOffset ];
	//println( "rects = " + rects);
	return rects;
}

function getFormItemRectangles(instance, laf) {
	var rect = new Rectangle(0, 0, 
		instance.properties.size.width, instance.properties.size.height);
	return getFormItemRectanglesInRect(instance, laf, rect);
}

function getFormPromptFont(laf) {
	return laf.getFont("DenseFont");
}

function getFormPromptFlags(instance) {
	var flags = Font.OVERFLOW_ELLIPSIS;
	
	if (isDoubleSpaced(instance.parent) || isShowingBitmaps(instance.parent))
		flags |= Font.ALIGN_LEFT;
	else
		flags |= Font.ALIGN_RIGHT;
	
	return flags;
}

function isFirstField(instance) {
	var siblings = instance.parent.children;
	if (siblings != null)
		return instance == siblings[0];
		
	return false;
}

function drawFormPrompt(prototype, instance, laf, graphics, rects) {
	var formInstance = instance.parent;
	var properties = instance.properties;
	var prompt = properties.prompt;
	
	var font = getFormPromptFont(laf);
	graphics.setFont(font);

	var rect = rects[FORM_PROMPT_RECT_INDEX];	
	graphics.setBackground(laf.getColor("EEikColorWindowBackground"));
	graphics.fillRectangle(rect);

	graphics.setForeground(laf.getColor("listitem.text"));

	// in single spaced only, draw line before prompt if separator before or after is specified
	// and this is not the first form field (2.x only)
	var version = getComponentVersions();
	if (!isDoubleSpaced(formInstance) && !isFirstField(instance) && properties.EEikDlgItemSeparatorBefore
		&& version.getMajor() < 3) {
		graphics.drawLine(rect.x, rect.y, rect.width + getFormPadding(laf) - 1, rect.y);
	}

	// draw bitmap on the left side of the divider
	if (isShowingBitmaps(formInstance)) {
		var imageRect = rects[FORM_PROMPT_IMAGE_RECT_INDEX];
		renderImage(prototype, instance, laf, graphics, 
			0, 0, "promptImage", false);
	}
	
	rect = rects[FORM_PROMPT_LABEL_RECT_INDEX];
	var height = getFontHeight(font);
	var textRect = new Rectangle(rect.x, rect.y + (rect.height - height)/2,
			rect.width, height);
	var promptText = chooseScalableText(prompt, font, textRect.width);
	graphics.drawFormattedString(promptText, textRect, getFormPromptFlags(instance), 0);	
}

function getFormContentBounds(instance, laf) {
	var rect = new Rectangle(0, 0, 
		instance.properties.size.width, instance.properties.size.height);
	if (isForm(instance.parent)) {
		return getFormItemRectanglesInRect(instance, laf, rect)[FORM_CONTENT_RECT_INDEX];
	}
	return rect;		
}



