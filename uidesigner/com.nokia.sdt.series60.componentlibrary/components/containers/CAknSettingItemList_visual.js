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
include("../settingsListLibrary.js")

function CAknSettingItemListVisual() {
}

CAknSettingItemListVisual.prototype.draw = function(instance, laf, graphics) {
	if ((instance.children == null) || (instance.children.length == 0)) {
		graphics.setBackground(getBackgroundColor(instance.parent, laf));
		DialogUtils = getPluginClass("com.nokia.sdt.series60.componentlibrary", 
						"com.nokia.sdt.series60.component.DialogUtils");
		DialogUtils.drawDialogNoData(graphics, laf);
		return;	
	}
	
	var properties = instance.properties;
	var rowDimensions = getLineBounds(instance.properties, laf);
	
	graphics.setBackground(getBackgroundColor(instance, laf));
	graphics.fillRectangle(0, 0, properties.size.width, properties.size.height);
	graphics.setFont(fontForType(properties, laf));
	
	var width = properties.size.width;
	var height = properties.size.height;
	
	var maxDisplayed = maxDisplayableItems(properties, laf);
	var header = laf.getInteger("settingitemlist.titlepadding", 8);

	// draw decorations
	
	// no decorations on 3.1+
	if (!laf.getBoolean("decorations", true))
		return;
	
	var bottom = header + rowDimensions.y * maxDisplayed - 1;
	var column = getPromptDividerOffset(instance, laf);
	
	setSettingsListLineStyle(laf, graphics);
	
	graphics.drawLine(column, 0, column, bottom);
	graphics.drawLine(column, bottom, width, bottom);
	
}

function horizontalContentMargin(properties, laf) {
	var column;
	if (properties.EAknSettingItemNumberedStyle)
		column = laf.getInteger("settingitemlist.numbered.padding", 20);
	else
		column = laf.getInteger("settingitemlist.padding", 8);
	return column;

}

CAknSettingItemListVisual.prototype.layout = function(instance, laf) {
	//println("layout");
	var width = instance.properties.size.width;
	var height = instance.properties.size.height;
	var header = (instance.children.length > 3) ? 0 : laf.getInteger("settingitemlist.titlepadding", 8);
	var row = 0;
	var rowDimensions = getLineBounds(instance.properties, laf);
	var rowHeight = rowDimensions.y;
	
	//println("width="+width+", height="+height+", header="+header+", rowDim="+rowDimensions+", rowHeight="+rowHeight);
	
	for (var i in instance.children) {
		var child = instance.children[i];
		child.properties.location.x = 0;
		child.properties.location.y = rowHeight * row;
		if (!child.properties.itemHidden) {
			child.properties.size.height = rowHeight;
			++row;
		} else {
			child.properties.size.height = 0;
		}
		child.properties.size.width = width;
	}
}

CAknSettingItemListVisual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null; // needs implementation	
}

// IScrollBoundsProvider

CAknSettingItemListVisual.prototype.getScrollBounds = function(instance, laf) {
	//println("getScrollBounds");
	var width = instance.properties.size.width;
	var header = laf.getInteger("settingitemlist.titlepadding", 8);
	var rowDimensions = getLineBounds(instance.properties, laf);
	var rowHeight = rowDimensions.y;
	
	var r = new Rectangle(0, header, width, rowHeight * maxDisplayableItems(instance.properties, laf));
	//println("scrollBounds="+r);
	return r;
}


