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


function CQikSimpleDialog() {
}

////////////////////////////////////////////////////////////////////////////////
// IVisualAppearance


CQikSimpleDialog.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;
	var image = laf.getImage("simpledialog.screen.background");	
	graphics.drawImage(image, 0 , 0);

	var flags = Font.ALIGN_LEFT;	
    flags |= Font.ANTIALIAS_OFF;
	var font = laf.getFont("ArialNarrowFont");
	graphics.setFont(font);
	
	var rect = new Rectangle (laf.getInteger("DialogTextApp.bound.x",34),
			  				  laf.getInteger("DialogTextApp.bound.y",21),
			  				  laf.getInteger("DialogTextApp.width",182),
			  				  laf.getInteger("DialogTextApp.height",23));
	
	var appUi = getRootModelInstanceOfType("com.nokia.carbide.uiq.ApplicationUI");

	if (properties.overriddenAppTitleName == null) {        
		if (appUi != null) 
			graphics.drawFormattedString(appUi.properties.caption, rect, flags, 0);				
	} else {
		graphics.drawFormattedString(properties.overriddenAppTitleName, rect, flags, 0);	
	}
	
	if (properties.overriddenAppTitleIcon.bmpfile == null || properties.overriddenAppTitleIcon.bmpfile == "") {        
		if (appUi != null) {
			renderIconApp(CQikSimpleDialog.prototype, instance,appUi, laf, graphics, 
			laf.getInteger("DialogIconApp.bound.x",12),
			laf.getInteger("DialogIconApp.bound.y",26), "image", true);			
		}		
	} else {
			renderIconApp(CQikSimpleDialog.prototype, instance, instance, laf, graphics, 
			laf.getInteger("DialogIconApp.bound.x",12),
			laf.getInteger("DialogIconApp.bound.y",26), "overriddenAppTitleIcon", true);	
	}
	
	// Use this just for degugging purposes, disable it in final release
	if (laf.getBoolean("RowLayoutManager.debugMode", false)) {
		var simpleDialogLayoutsGroupBounds = laf.getRectangle("SimpleDialogLayoutsGroup.bounds");
		var simpleDialogCQikContainerBounds = laf.getRectangle("SimpleDialogCQikContainer.bounds")
		var topMargin = laf.getInteger("RowLayoutManager.Dialog.topMargin", 0);
		var numberOfRows = laf.getInteger("RowLayoutManager.Dialog.numberOfRows", 8);
		var baseLine = laf.getInteger("RowLayoutManager.Dialog.baseLine", 5);
		var rowHeight = laf.getInteger("RowLayoutManager.Dialog.rowHeight", 28);
		var rowSize = new Point(simpleDialogCQikContainerBounds.width, rowHeight);
		var offsetX = simpleDialogLayoutsGroupBounds.x + simpleDialogCQikContainerBounds.x;
		var offsetY = simpleDialogLayoutsGroupBounds.y + simpleDialogCQikContainerBounds.y + topMargin;
		for (var i = 0; i < numberOfRows; i++) {
			graphics.drawRectangle(offsetX, offsetY, rowSize.x - 1, rowSize.y - 1);
			var dotLinePosition = new Point(offsetX, offsetY + rowSize.y - baseLine - 1);
			drawHDottedLine(graphics, dotLinePosition, rowSize.x - 1);
			offsetY += rowSize.y;
		}
	}
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

CQikSimpleDialog.prototype.getViewableSize = function(instance, propertyId, laf) {
	return new Point (18, 18);
}


CQikSimpleDialog.prototype.getAlignmentWeights = function(instance, propertyId, laf) {
	return new Point(ImageUtils.ALIGN_LEFT, ImageUtils.ALIGN_TOP);
}

CQikSimpleDialog.prototype.isScaling = function(instance, propertyId, laf) {
	return true;
}

CQikSimpleDialog.prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
	return true;
}

CQikSimpleDialog.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {	
	return laf.getPoint("screen.size");
}

////////////////////////////////////////////////////////////////////////////////
//IComponentValidator

CQikSimpleDialog.prototype.validate = function(instance) {
	if (instance != null || instance != "") {
		var properties = instance.properties;
		if (properties.forcedExitCommandId != 0 && properties.allowForcedExit == false) {
			var modelMessage = newModelMessage(IStatus.WARNING,formatString(lookupString("validate.flags"),
								[instance.name ]),instance, "flags", null);
			return [ modelMessage ];
					
		}
	}
	return null;
}

CQikSimpleDialog.prototype.queryPropertyChange = function(instance, propertyId, newValue) {
	return null;
}

////////////////////////////////////////////////////////////////////////////////
//ILayout

CQikSimpleDialog.prototype.layout = function(instance, laf) {
	var dialogChildren = instance.children;
	var containersGroup = findImmediateChildByComponentID(dialogChildren, "com.nokia.carbide.uiq.ContainersGroup");
	if (containersGroup != null) {
		containersGroup.setLayoutBounds(laf.getRectangle("SimpleDialogLayoutsGroup.bounds"));
	}
}

