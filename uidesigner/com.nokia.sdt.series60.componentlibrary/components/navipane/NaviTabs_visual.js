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


include("../containers/containerLibrary.js")
include("../renderLibrary.js")
include("../srcgenLibrary.js")

function NaviTabsVisual() {
}

function getNaviSize(instance, laf) {
	var properties = instance.properties;
	return new Point(properties.size.width, properties.size.height);
}

function isLongTabs(instance) {
	var property = instance.properties.tabWidth;
	return ((property == "EAknTabWidthWithTwoLongTabs") || 
		(property == "EAknTabWidthWithThreeLongTabs"));
}

function isOneTab(property) {
	return (property == "EAknTabWidthWithOneTab"); 
}

function getNumVisualTabs(property) {
	if ((property == "EAknTabWidthWithTwoTabs") ||
		(property == "EAknTabWidthWithTwoLongTabs"))
		return 2;
	else if ((property == "EAknTabWidthWithThreeTabs") ||
		(property == "EAknTabWidthWithThreeLongTabs"))
		return 3;
	else if (isOneTab(property))
		return 1;
		
	return 4;
}

function getHorizontalInset(laf) {
	return laf.getDimension("navi.indicator.size").x;
}

function getTabOffset(totalWidth, longTabs, numVisualTabs, laf) {
	if (longTabs)
		return totalWidth / 18;
	
	var width = totalWidth - (2 * getHorizontalInset(laf));
	return (width / numVisualTabs) - (2 * laf.getInteger("tab.corner", 3));
}

function getTabWidth(totalWidth, numVisualTabs, longTabs, laf) {
	var sideInset = getHorizontalInset(laf);
	return totalWidth - (2 * sideInset) - ((numVisualTabs - 1) * getTabOffset(totalWidth, longTabs, numVisualTabs, laf));
}

function getNumTabs(instance) {
	var appUi = instance.parent.parent;
	var numTabs = 0;
	for (var i in appUi.children) {
		if (appUi.children[i].isInstanceOf("com.nokia.sdt.series60.AvkonViewReference")) {
			if (appUi.children[i].properties.inTabGroup)
				numTabs++;
		}
	}
	
	return numTabs;
}

function drawTab(r, laf, graphics, isActive) {
	var offset = laf.getInteger("tab.corner", 3);
	
	var intArray = [	
					r.x,
					r.y + r.height,
					r.x + offset,
					r.y + r.height - offset,
					r.x + (4 * offset),
					r.y + offset, 
					r.x + (5 * offset),
					r.y,
					r.x + r.width - (5 * offset),
					r.y,
					r.x + r.width - (4 * offset),
					r.y + offset,
					r.x + r.width - offset,
					r.y + r.height - offset,
					r.x + r.width,
					r.y + r.height,
					];

	if (laf.getBoolean("is.landscape", false)) {
		for (var i = 0; i < intArray.length; i++) {
			if ((i % 2) != 0) {
				var yval = intArray[i];
				intArray[i] = r.height - yval;
			}
		}
	}

	var faceColor = isActive ? laf.getColor("navi.tab.face") : laf.getColor("navi.tab.back");
	graphics.setBackground(faceColor);
	graphics.fillPolygon(intArray);
	graphics.setForeground(laf.getColor("navi.tab.shade"));
	graphics.drawPolyline(intArray);
}

function getViewRefFromIndex(instance, index) {
	var appUiChildren = instance.parent.parent.children;
	var tabIndex = -1;
	for (var i in appUiChildren) {
		var child = appUiChildren[i];
		if ((child.componentId == "com.nokia.sdt.series60.AvkonViewReference") && child.properties.inTabGroup)
			tabIndex++;
		if (tabIndex == index)
			return child;
	}
	
	return null;
}

function getText(instance, index) {
	var viewRef = getViewRefFromIndex(instance, index);
	if (viewRef != null)
		return viewRef.properties.tabText;
	
	return "";
}

function hasTabText(instance, index) {
	return getText(instance, index).length > 0;
}

function drawText(instance, rect, font, graphics, index) {
	var text = getText(instance, index);
	text = chooseScalableText(text, font, rect.width);
	var extent = font.stringExtent(text);
	var newrect = new Rectangle(rect.x, (rect.height - extent.y) / 2, rect.width, rect.height)
	graphics.drawFormattedString(text, newrect, Font.ALIGN_CENTER | Font.ANTIALIAS_OFF | Font.OVERFLOW_ELLIPSIS, 0);
}

function hasTabImage(instance, index) {
	var viewRef = getViewRefFromIndex(instance, index);
	if (viewRef != null) {
		return isImagePropertySet(viewRef.properties.tabImage);
	}
		
	return false;
}

function getUnmaskedFillColor(isOneTab, isActiveMulti, laf) {
	if (isOneTab)
		return laf.getColor("navi.pane.text");
	else if (isActiveMulti)
		return laf.getColor("navi.tab.face")
	else
		return laf.getColor("navi.tab.backText");
}

function renderTabImage(viewRef, isOneTab, isActiveMulti, laf, graphics, rect, color) {

	var imagePropertyRendering = createImagePropertyRendering();
	imagePropertyRendering.setImageProperty(viewRef, "tabImage", laf);

	var imageProperty = viewRef.properties.tabImage.editableValue;
	
	// Different behavior for masked and unmasked images, in terms of the size
	// of renderable area, the way the bitmap and mask are treated, etc.
	 
	if (imageProperty.isMasked()) {
		// set the color used for monochrome rendering
		graphics.setForeground(color);
	} 
	else {
		graphics.setBackground(getUnmaskedFillColor(isOneTab, isActiveMulti, laf));
		var fillRect = new Rectangle(rect.x, rect.y, rect.width, rect.height);
		var hShrink = 0;
		if (isOneTab)
			hShrink = rect.width / 3;
		else
			hShrink = 4 * laf.getInteger("tab.corner", 3);
		shrinkRect(fillRect, hShrink, 0);
		graphics.fillRectangle(fillRect.x, fillRect.y, fillRect.width, fillRect.height);
	}

	imagePropertyRendering.setViewableSize(new Point(rect.width, rect.height));
	imagePropertyRendering.setAlignmentWeights(new Point(ImageUtils.ALIGN_CENTER, ImageUtils.ALIGN_CENTER_OR_TOP));
	imagePropertyRendering.setScaling(isScalingIcons());
	imagePropertyRendering.setPreservingAspectRatio(true);
	
	imagePropertyRendering.setTransparencyHandling(imagePropertyRendering.TRANSPARENCY_FLATTEN);
	
	// use this model in S60ImagePropertyRendering to handle the weird (mis-)use of
	// bitmap and mask in the navi pane
	imagePropertyRendering.setRenderingModel(imagePropertyRendering.MODEL_NAVI_PANE_TABS);

	imagePropertyRendering.render(graphics.getWrappedGC(), rect.x, rect.y);

}

function shrinkRect(rect, h, v) {
	rect.x += h; 
	rect.width -= (2 * h);
	rect.y += v; 
	rect.height -= (2 * v);
}

function drawTabImage(isOneTab, isActiveMulti, rect, instance, laf, graphics, index) {
	var viewRef = getViewRefFromIndex(instance, index);
	var color = getTextColor(isOneTab, isActiveMulti, laf);
	var shrinkage = laf.getInteger("tab.corner", 3);
	var drawRect = new Rectangle(rect.x, rect.y, rect.width, rect.height);
	shrinkRect(drawRect, shrinkage, shrinkage);
	renderTabImage(viewRef, isOneTab, isActiveMulti, laf, graphics, drawRect, color);
}

function drawOneTabContents(instance, rect, font, laf, graphics, index) {
	var hasImage = hasTabImage(instance, index);
	var hasText = hasTabText(instance, index);
/*	
NOT TRYING TO RENDER IMAGE AND TEXT, BECAUSE EXCEPT FOR VERY FEW CASES, IT LOOKS HOORIBLE ON THE EMULATOR!
	if (hasImage && hasText) {
		var imageRect = new Rectangle(rect.x, rect.y, rect.width / 2, rect.height);
		drawTabImage(true, false, imageRect, instance, laf, graphics, index);
		var text = getText(instance, index);
		var textRect = new Rectangle(rect.x + (rect.width / 2), rect.y, rect.width / 2, rect.height);
		drawText(instance, textRect, font, graphics, index);
	}
*/	
	if (hasText) {
		var text = getText(instance, index);
		drawText(instance, rect, font, graphics, index);
	}
	else if (hasImage) {
		drawTabImage(true, false, rect, instance, laf, graphics, index);
	}
}

function drawOneTab(instance, rect, laf, graphics, index) {
	var color = getTextColor(true, false, laf);
		
	var font = laf.getFont("navi.text.font");
	graphics.setFont(font);
	graphics.setForeground(color);

	drawOneTabContents(instance, rect, font, laf, graphics, index);
}

function getTextColor(isOneTab, isActiveMulti, laf) {
	if (isOneTab)
		return laf.getColor("navi.pane.text");
	else if (isActiveMulti)
		return laf.getColor("navi.tab.text")
	else
		return laf.getColor("navi.tab.backText");
}

function drawTabText(instance, rect, laf, graphics, isActive, index) {
	var color = getTextColor(false, isActive, laf);
	var font = laf.getFont("navi.tab.font");
	graphics.setFont(font);
	graphics.setForeground(color);
	
	drawText(instance, rect, font, graphics, index);
}

function drawTabContents(instance, rect, laf, graphics, isActive, index) {
	var hasImage = hasTabImage(instance, index);
	var hasText = hasTabText(instance, index);
/*	
NOT TRYING TO RENDER IMAGE AND TEXT, BECAUSE EXCEPT FOR VERY FEW CASES, IT LOOKS HOORIBLE ON THE EMULATOR!
	if (hasImage && hasText) {
		var text = getText(instance, index);
		var textRect = new Rectangle(rect.x, rect.y, rect.width / 2, rect.height);new Rectangle(rect.x, rect.y, rect.width / 2, rect.height);
		drawTabText(instance, textRect, laf, graphics, isActive, index);
		var imageRect = new Rectangle(rect.x + (rect.width / 2), rect.y, rect.width / 2, rect.height);
		drawTabImage(false, isActive, imageRect, instance, laf, graphics, index);
	}
*/
	if (hasText) {
		var text = getText(instance, index);
		drawTabText(instance, rect, laf, graphics, isActive, index);
	}
	else if (hasImage) {
		drawTabImage(false, isActive, rect, instance, laf, graphics, index);
	}
}

function drawMultipleTabs(instance, rect, laf, graphics, active) {
	var numVisualTabs = getNumVisualTabs(instance.properties.tabWidth);
	var longTabs = isLongTabs(instance);
	var hInset = getHorizontalInset(laf);
	var tabOffset = getTabOffset(rect.width, longTabs, numVisualTabs, laf);
	var tabWidth = getTabWidth(rect.width, numVisualTabs, longTabs, laf);
	
	var firstTab = 0;
	var numTabs = getNumTabs(instance);
	if (active >= numVisualTabs)
		firstTab = active;
	if (firstTab + numVisualTabs >= numTabs)
		firstTab = numTabs - numVisualTabs;
	if (firstTab < 0)
		firstTab = 0;
	
	// first draw the inactive tabs
	for (var i = firstTab + numVisualTabs - 1; i > active; i--) {
		var left = rect.x + hInset + ((i - firstTab) * tabOffset);
		var drawRect = new Rectangle(left, rect.y, tabWidth, rect.height);
		drawTab(drawRect, laf, graphics, false);
		if (!longTabs) {
			drawTabContents(instance, drawRect, laf, graphics, false, i);
		}
	}
	for (var i = firstTab; i < active; i++) {
		var left = rect.x + hInset + ((i - firstTab) * tabOffset);
		var drawRect = new Rectangle(left, rect.y, tabWidth, rect.height);
		drawTab(drawRect, laf, graphics, false);
		if (!longTabs) {
			drawTabContents(instance, drawRect, laf, graphics, false, i);
		}
	}
	
	// then draw the active tab
	{
		var left = rect.x + hInset + ((active - firstTab) * tabOffset);
		var drawRect = new Rectangle(left, rect.y, tabWidth, rect.height);
		drawTab(drawRect, laf, graphics, true);
		drawTabContents(instance, drawRect, laf, graphics, true, active);
	}
}

function drawArrow(onRight, rect, laf, graphics) {
	var arrowHeight = rect.height / 3;
	graphics.setForeground(laf.getColor("navi.tab.arrow"));
	var arrowTop = (rect.height - arrowHeight) / 2;
	var hInset = getHorizontalInset(laf);
	var arrowWidth = ((arrowHeight % 2) == 0) ? arrowHeight / 2 + 1 : arrowHeight / 2;
	
	for (var i = 0; i < arrowWidth; i++) {
		var x;
		if (onRight)
			x = rect.x + rect.width - hInset + i;
		else
			x = rect.x + hInset - i;
		var y0 = arrowTop + i;
		var y1 = arrowTop + arrowHeight - i;
		graphics.drawLine(x, y0, x, y1);
	}
}

NaviTabsVisual.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;
	var active = instance.properties.active;
	var numTabs = getNumTabs(instance);
	
	if (active > (numTabs - 1))
		active = numTabs - 1;

	if (active < 0)
		active = 0;
		
	var size = getNaviSize(instance, laf);
	var rect = new Rectangle(0, 0, size.x, size.y);
	if (!isOneTab(properties.tabWidth)) {
		drawMultipleTabs(instance, rect, laf, graphics, active);
	}
	else {
		drawOneTab(instance, rect, laf, graphics, active);
	}

	if (active > 0)
		drawArrow(false, rect, laf, graphics);
	
	if (active < (numTabs - 1))
		drawArrow(true, rect, laf, graphics);
}

NaviTabsVisual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null; // needs implementation	
}

