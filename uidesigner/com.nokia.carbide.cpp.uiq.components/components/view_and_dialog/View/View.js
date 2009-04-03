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
* START_USECASES: CU10 END_USECASES
*
*
*/


include("../../containerLibrary.js")
include("../../renderLibrary.js")
include("../../implLibrary.js")


function View() {
}

////////////////////////////////////////////////////////////////////////////////
// IVisualAppearance


function getTitleBounds(laf) {
	return new Rectangle (laf.getInteger("TextApp.bound.x",55),
							  laf.getInteger("TextApp.bound.y",18),
							  laf.getInteger("TextApp.width",184),
							  laf.getInteger("TextApp.height",30));
}


View.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;
	var image = laf.getImage("view.screen.background");	
	graphics.drawImage(image, 0 , 0);

	var flags = Font.ALIGN_LEFT;	
    flags |= Font.ANTIALIAS_OFF;
	var font = laf.getFont("ArialNarrowFont");
	graphics.setFont(font);

	var rect = getTitleBounds(laf);
	
	var appUi = getRootModelInstanceOfType("com.nokia.carbide.uiq.ApplicationUI");

	if (properties.overriddenAppTitleName == null || properties.overriddenAppTitleName == "") {       
		if (appUi != null && appUi.properties.caption != "") { 
			graphics.drawFormattedString(appUi.properties.caption, rect, flags, 0);	
		} else {
			graphics.drawFormattedString(getProjectName(), rect, flags, 0);	
		}			
	} else {
		graphics.drawFormattedString(properties.overriddenAppTitleName, rect, flags, 0);	
	}
	
	if (properties.overriddenAppTitleIcon.bmpfile == null || properties.overriddenAppTitleIcon.bmpfile == "") {        
		if (appUi != null) {
			renderIconApp(View.prototype, instance,appUi, laf, graphics, 
			laf.getInteger("IconApp.bound.x",10),
			laf.getInteger("IconApp.bound.y",28), "image", true);			
		}		
	} else {
			renderIconApp(View.prototype, instance, instance, laf, graphics, 
			laf.getInteger("IconApp.bound.x",10),
			laf.getInteger("IconApp.bound.y",28), "overriddenAppTitleIcon", true);	
	}
	
	// Use this just for degugging purposes, disable it in final release
	if (laf.getBoolean("RowLayoutManager.debugMode", false)) {
		var viewLayoutsGroupBounds = laf.getRectangle("ViewLayoutsGroup.bounds");
		var viewLayoutBounds = laf.getRectangle("ViewLayout.bounds");
		var viewPageBounds = laf.getRectangle("ViewPage.bounds");
		var viewQikContainerBounds = laf.getRectangle("ViewCQikContainer.bounds");
		var topMargin = laf.getInteger("RowLayoutManager.View.topMargin", 2);
		var numberOfRows = laf.getInteger("RowLayoutManager.View.numberOfRows", 8);
		var baseLine = laf.getInteger("RowLayoutManager.View.baseLine", 5);
		var rowHeight = laf.getInteger("RowLayoutManager.View.rowHeight", 27);
		var rowSize = new Point(viewQikContainerBounds.width, rowHeight);
		var offset = viewLayoutsGroupBounds.y + viewLayoutBounds.y + viewPageBounds.y + viewQikContainerBounds.y + topMargin;
		for (var i = 0; i < numberOfRows; i++) {
			graphics.drawRectangle(0, offset, rowSize.x - 1, rowSize.y - 1);
			var dotLinePosition = new Point(0, offset + rowSize.y - baseLine - 1);
			drawHDottedLine(graphics, dotLinePosition, rowSize.x - 1);
			offset += rowSize.y;
		}
	}

}

function renderIconApp(prototype, instance, instanceWithProperty, laf, graphics, x, y, propertyId, doBlend) {
	var imagePropertyRendering = createImagePropertyRendering();
	imagePropertyRendering.setImageProperty(instanceWithProperty, propertyId, laf);
	imagePropertyRendering.setViewableSize(prototype.getViewableSize(instance, propertyId, laf));
	imagePropertyRendering.setAlignmentWeights(prototype.getAlignmentWeights(instance, propertyId, laf));
	imagePropertyRendering.setScaling(prototype.isScaling(instance, propertyId, laf));
	imagePropertyRendering.setPreservingAspectRatio(prototype.isPreservingAspectRatio(instance, propertyId, laf));

	imagePropertyRendering.setTransparencyHandling(doBlend 
		? getIImageRenderingClass().TRANSPARENCY_KEEP
		: getIImageRenderingClass().TRANSPARENCY_FLATTEN);
	imagePropertyRendering.render(graphics.getWrappedGC(), x, y);
}

function drawHDottedLine(graphics, position, width) {
	var dotWidth = 3;
	var dots = width / ( 2 * dotWidth );
	if ( 2 * dots * dotWidth > width )
		dots--;
	var nextDotX = position.x;
	for (var i = 0; i < dots; i++) {
		graphics.drawLine(nextDotX, position.y, nextDotX + dotWidth - 1, position.y);
		nextDotX += ( 2 * dotWidth );
	}
}

View.prototype.getViewableSize = function(instance, propertyId, laf) {
	return new Point (32, 36);
}


View.prototype.getAlignmentWeights = function(instance, propertyId, laf) {
	return new Point(ImageUtils.ALIGN_LEFT, ImageUtils.ALIGN_TOP);
}

View.prototype.isScaling = function(instance, propertyId, laf) {
	return true;
}

View.prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
	return true;
}

View.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {	
	return laf.getPoint("screen.size");
}


////////////////////////////////////////////////////////////////////////////////
// ILayout

View.prototype.layout = function(instance, laf) {
	var viewChildren = instance.children;
	var layoutsGroup = findImmediateChildByComponentID(viewChildren, "com.nokia.carbide.uiq.LayoutsGroup");
	if (layoutsGroup != null) {
		layoutsGroup.setLayoutBounds(laf.getRectangle("ViewLayoutsGroup.bounds"));
	}
}



////////////////////////////////////////////////////////////////////////////////
//IDirectLabelEdit

setupCommonDirectLabelEditing(View.prototype, 
	"overriddenAppTitleName", 
	function (instance, laf, propertyId) {
		return getTitleBounds(laf);
	},
	function(instance, laf) { return laf.getFont("ArialNarrowFont"); } 
	)
