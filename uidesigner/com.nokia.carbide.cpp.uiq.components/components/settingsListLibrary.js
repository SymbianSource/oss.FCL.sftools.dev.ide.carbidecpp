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


function isSettingItemList(instance) {
	return instance.isInstanceOf("com.nokia.sdt.series60.CAknSettingItemList");
}



DRAW_BOUNDING_RECTS = false;

include("renderLibrary.js")
include("implLibrary.js")
include("srcgenLibrary.js")


function fontForType(silProperties, laf) {
	return laf.getFont("NormalFont");
}

function getLineBounds(silProperties, laf) {
	var font = fontForType(silProperties, laf);
	// * 2 accounts for label and content
	var p = new Point(0, getFontHeight(font) * 2 + laf.getInteger("list.padding", 8));
	p.y += laf.getInteger("extra.padding", 0) * 2;
	//println("getLineBounds="+p);
	return p;
}

function maxDisplayableItems(silProperties, laf) {
	var header = 0;
	var bounds = getLineBounds(silProperties, laf);
	var max = Math.floor((silProperties.size.height - laf.getInteger("list.padding", 8) / 2) / bounds.y)
	//println("maxDisplayableItems="+max+" bounds="+bounds+" silProperties.size.height="+silProperties.size.height);
	return max;
}

function verticalContentMargin(silProperties, laf) {
	var maxItems = maxDisplayableItems(silProperties, laf);
	var lineBounds = getLineBounds(silProperties, laf);
	return maxItems * lineBounds.y + laf.getInteger("list.padding", 8) / 2;
}


function isShowingNumbers(silInstance) {
	return silInstance.properties.EAknSettingItemNumberedStyle;
}
		
function getPromptDividerOffset(silInstance, laf) {
	var column;
	if (silInstance.properties.EAknSettingItemNumberedStyle)
		column = laf.getInteger("settingitemlist.numbered.padding", 20);
	else
		column = laf.getInteger("settingitemlist.padding", 6);
	return column;
}

function getSILPaddingY(laf) {
	return laf.getInteger("settingitemlist.padding.y", 6);
}

function getSILPaddingX(laf) {
	return laf.getInteger("settingitemlist.padding.x", 6);
}

/**
 *	Get all the rectangles associated with an item in a setting item list.
 *
 *	@param rect the item's rectangle (i.e. an entire row)
 * @returns an array with:
 *
 *	0) Rectangle numeric prompt bounds, as a whole
 *	1) Rectangle title bounds, as a whole
 *	2) Rectangle compulsory indicator bounds, as a whole
 *	3) Rectangle content bounds, as a whole
 *	4) int column where divider is drawn
 */
var SIL_ITEM_RECT_INDEX = 0;
var SIL_NUMBER_RECT_INDEX = 1;
var SIL_TITLE_RECT_INDEX = 2;
var SIL_INDICATOR_RECT_INDEX = 3;
var SIL_CONTENT_RECT_INDEX = 4;
var SIL_DIVIDER_OFFSET_INDEX = 5;

function getSettingItemRectanglesInRect(instance, laf, rect) {
	var silInstance = instance.parent;
	var dividerOffset = getPromptDividerOffset(instance.parent, laf);

	var promptRect, contentRect, titleRect, indiRect;
	
	var contentHeight = laf.getInteger("settingitemlist.content.height", 16);
	var vertPadding = getSILPaddingY(laf);	
	var horizPadding = getSILPaddingX(laf);
	
	promptRect = new Rectangle(rect.x, rect.y + vertPadding, 
					dividerOffset - horizPadding, rect.height - vertPadding);
	
	var leftMargin = dividerOffset + horizPadding;
	titleRect = new Rectangle(leftMargin, rect.y + vertPadding, 
		rect.width - leftMargin, contentHeight);

	var contentX = laf.getInteger("settingitemlist.content.x", 32);
	contentRect = new Rectangle(rect.x + contentX,
			rect.y + rect.height / 2 + vertPadding, 
			rect.width - contentX - horizPadding/2,
			contentHeight);

	indiRect = new Rectangle(leftMargin, contentRect.y,
			contentRect.x - leftMargin, contentRect.height);
			
	var rects = [ rect, promptRect, titleRect, indiRect, contentRect, dividerOffset ];
	//pr("Rects = " +rects);
	return rects;
}

function getSettingItemRectangles(instance, laf) {
	var rect = new Rectangle(0, 0, 
		instance.properties.size.width, instance.properties.size.height);
	return getSettingItemRectanglesInRect(instance, laf, rect);
}

function getPromptFont(laf) {
	return laf.getFont("AnnotationFont");
}
function getTitleFont(laf) {
	return laf.getFont("NormalFont");
}
function getIndiFont(laf) {
	return laf.getFont("NormalFont");
}

function getPromptFlags(instance) {
	var flags = Font.OVERFLOW_ELLIPSIS;
	
	flags |= Font.ALIGN_CENTER;
	
	return flags;
}

function isFirstField(instance) {
	var siblings = instance.parent.children;
	if (siblings != null)
		return instance == siblings[0];
		
	return false;
}

function calcSettingItemNumber(silInstance, self) {
	var number = silInstance.properties.initialNumber;
	var numberHidden = silInstance.properties.EAknSettingItemIncludeHiddenInOrdinal;
	for (var c in silInstance.children) {
		var sib = silInstance.children[c];
		if (sib == self)
			break;
		if (numberHidden || !sib.properties.itemHidden)
			number++;
	}
	return number;
}

function setSettingsListLineStyle(laf, graphics) {
	var version = getComponentVersions();
	if ((version.getMajor() >= 3 || (version.getMajor() == 2 && version.getMinor() >= 8))
			&& laf.getDimension("screen.size").x > 208) {
		// use dashed line
		graphics.setLineWidth(2);
		graphics.setLineDash([ 2 ]);
	} else {
		// use solid line
		graphics.setLineWidth(1);
	}

}

function drawSettingItemPrompt(prototype, instance, laf, graphics, rects) {
	var formInstance = instance.parent;
	var properties = instance.properties;

	var number = calcSettingItemNumber(instance.parent, instance);

	var rect = rects[SIL_NUMBER_RECT_INDEX];	
	graphics.setBackground(laf.getColor("EEikColorControlBackground"));
	//graphics.setBackground(getBackgroundColor(instance, laf));
	
	graphics.fillRectangle(rect);

	graphics.setForeground(laf.getColor("EEikColorControlText"));

	var itemRect = rects[SIL_ITEM_RECT_INDEX];
	
	// no decorations on 3.1+
	if (laf.getBoolean("decorations", true)) {
		setSettingsListLineStyle(laf, graphics);
		graphics.drawLine(rects[SIL_DIVIDER_OFFSET_INDEX], 
			itemRect.y + itemRect.height - 1,  
			itemRect.width, itemRect.y + itemRect.height - 1);
	
		//graphics.drawLine(rects[SIL_DIVIDER_OFFSET_INDEX], itemRect.y, rects[SIL_DIVIDER_OFFSET_INDEX], itemRect.height);
	}
	
	// draw number
	var font = getPromptFont(laf);
	graphics.setFont(font);
	if (isShowingNumbers(formInstance)) {
		var numberRect = rects[SIL_NUMBER_RECT_INDEX];
		graphics.drawFormattedString(number, numberRect, 
			Font.ALIGN_RIGHT,
			0);	
	}
	
	// draw item title
	var font = getTitleFont(laf);
	graphics.setFont(font);
	rect = rects[SIL_TITLE_RECT_INDEX];
	var height = getFontHeight(font);
	var textRect = new Rectangle(rect.x, rect.y + (rect.height - height)/2,
			rect.width, height);
	var titleText = chooseScalableText(properties.itemTitle, font, textRect.width);
	graphics.drawFormattedString(titleText, textRect, 0, 0);	
	
	// draw compulsory indicator
	var font = getIndiFont(laf);
	graphics.setFont(font);	
	rect = rects[SIL_INDICATOR_RECT_INDEX];
	graphics.setForeground(laf.getColor("CAknSettingItemList.CompulsoryIndicator"));
	graphics.drawFormattedString(properties.compulsoryLabel, rect, 0, 0);
	
	// draw content rect
	rect = rects[SIL_CONTENT_RECT_INDEX];
	graphics.setBackground(laf.getColor("CAknSettingItemList.ContentBackground"));
	graphics.fillRectangle(rect);
	graphics.setForeground(laf.getColor("CAknSettingItemList.ContentForeground"));
}

function getSettingItemContentBounds(instance, laf) {
	var rect = new Rectangle(0, 0, 
		instance.properties.size.width, instance.properties.size.height);
	if (isForm(instance.parent)) {
		return getSettingItemRectanglesInRect(instance, laf, rect)[SIL_CONTENT_RECT_INDEX];
	}
	return rect;		
}


