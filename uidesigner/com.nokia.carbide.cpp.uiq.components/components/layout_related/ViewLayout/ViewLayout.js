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


function ViewLayout() {
	activeTabIndex = 0;
}


////////////////////////////////////////////////////////////////////////////////
// IQueryContainment

ViewLayout.prototype.canContainComponent = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent);
}


ViewLayout.prototype.canContainChild = function(instance, child) {
	return canContainComponent(instance, child.component);
}


ViewLayout.prototype.canRemoveChild = function(instance, child) {
	return instance.children.length > 1 ;
}


ViewLayout.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent) == null;
}


function canContainComponent(instance, component) {
	if (isViewPageComponent(component)) {
		if (instance.children.length < 1 || instance.parent.parent.properties.type!="singlePage") {
			return null;
		} else {
			return buildSimpleContainmentErrorStatus(
				lookupString("iQueryContaimentErrorSinglePage"),new Array( instance.name));
		}
	} else {
		return buildSimpleContainmentErrorStatus(
			lookupString("iQueryContaimentError"), new Array( component.friendlyName, instance.name ));
	}			
}


function isViewPageComponent(component) {
	return component.id == "com.nokia.carbide.uiq.ViewPage";
}


////////////////////////////////////////////////////////////////////////////////
// IComponentValidator

ViewLayout.prototype.validate = function(instance) {
	return  validateChildNumber (instance);
}

ViewLayout.prototype.queryPropertyChange = function(instance, propertyId, newValue) {
	return  validateChildNumber (instance);
}

function validateChildNumber (instance) {
	if (instance.children.length < 1) {
		return [createSimpleModelError(instance, 
				"instance.children.length",
				lookupString("iContainerValidatorError - Ensure ViewLayout has at least one child "),
				[instance.name] )
			   ];
	}
	return null;
}

////////////////////////////////////////////////////////////////////////////////
// IVisualAppearance

ViewLayout.prototype.draw = function(instance, laf, graphics) {
	var TEMP_XSHIFT = laf.getInteger("temp.xshift",53);
	var TEMP_YSHIFT = laf.getInteger("temp.yshift",0);
	var properties = instance.properties;
	var renderActiveTab = 0; //false 
	
	// instance bounds
	var maxWidth = properties.size.width - TEMP_XSHIFT;
	var maxHeight = properties.size.height - TEMP_YSHIFT;
	var x =  properties.location.x + TEMP_XSHIFT;
	var y =  properties.location.y + TEMP_YSHIFT;
	setActiveTabIndex(instance);

	// instance calculated bounds
	var maxX = x + maxWidth;
	
	// Constants
	var TAB_INTERNAL_SPACE_START =laf.getInteger("tab.internal.space.start",7);
	var TAB_INTERNAL_SPACE_MIDDLE =laf.getInteger("tab.internal.space.middle",5);
	var TAB_INTERNAL_SPACE_END = laf.getInteger("tab.internal.space.end",7);
	var TAB_TOTAL_HEIGHT = laf.getInteger("tab.total.height",25);
	var TAB_ICON_WIDTH = laf.getInteger("tab.icon.width",18);
	var TAB_ICON_HEIGHT = laf.getInteger("tab.icon.height",18);
	var TAB_ICON_VERTICAL_PITCH= laf.getInteger("tab.icon.vertical.pitch",7);
	var TAB_BORDER_COLOR = laf.getColor("tab.border.color");
	var TAB_CAPTION_FONT = laf.getFont("ArialNarrowFont");
	var NAVIGATION_KEY_VERTICAL_PITCH = laf.getInteger("navigation.key.vertical.pitch",8);
	var NAVIGATION_KEY_HORIZONTAL_PITCH = laf.getInteger("navigation.key.horizontal.pitch",5);
	var NAVIGATION_KEY_HEIGHT = laf.getInteger("navigation.key.height",10);
	var NAVIGATION_KEY_WIDTH = laf.getInteger("navigation.key.width",7);
	var NAVIGATION_KEY_BACKGROUND_COLOR = laf.getColor("navigation.key.background.color");
	
	// tab font configuration
	graphics.setFont(TAB_CAPTION_FONT);		
	var flags = getFlags(instance);			
	
	var totalTabLength=0;
	var tabWidth;
	for (var i in instance.children) {
		var text = instance.children[i].properties.tabCaption;
		var bmpId = instance.children[i].properties.tabImage.bmpid;
		
		var textWidth = graphics.stringExtent(text).x;
		tabWidth = 	TAB_INTERNAL_SPACE_START +  
					   	textWidth + 
					   	TAB_INTERNAL_SPACE_END;
		var includeTAB_INTERNAL_SPACE_MIDDLE = (text != null && text.length > 0 && bmpId != null && bmpId.length > 0);
		if (includeTAB_INTERNAL_SPACE_MIDDLE) {
			tabWidth += TAB_INTERNAL_SPACE_MIDDLE;
		}
		var includeIconSpace = (bmpId != null && bmpId.length);
		if (includeIconSpace) {
			tabWidth += TAB_ICON_WIDTH;
		}
		
		totalTabLength += tabWidth;
	}
	
	var includeNavigationKeys = totalTabLength > maxWidth && instance.children.length > 1; // no navigation without siblings
	
	if (includeNavigationKeys) {
		x += NAVIGATION_KEY_HORIZONTAL_PITCH;
		var pointArray = new Array (x, y + NAVIGATION_KEY_VERTICAL_PITCH+NAVIGATION_KEY_HEIGHT / 2,
									x + NAVIGATION_KEY_WIDTH, y + NAVIGATION_KEY_VERTICAL_PITCH,
									x + NAVIGATION_KEY_WIDTH, y + NAVIGATION_KEY_VERTICAL_PITCH+NAVIGATION_KEY_HEIGHT);
		graphics.setForeground(TAB_BORDER_COLOR);				
		graphics.setBackground(NAVIGATION_KEY_BACKGROUND_COLOR);
		graphics.fillPolygon(pointArray); 		
		graphics.drawPolygon(pointArray);
		x += NAVIGATION_KEY_WIDTH+NAVIGATION_KEY_HORIZONTAL_PITCH;
		
		maxX -= (NAVIGATION_KEY_HORIZONTAL_PITCH +NAVIGATION_KEY_WIDTH+NAVIGATION_KEY_HORIZONTAL_PITCH);
	} 
	var xWherePagesBegin = x;

	// children rendering
	if (instance.parent.parent.properties.type!="singlePage" && instance.children.length > 1) {
		for (var i in instance.children) {
			// retrieve property values
			var tabCaption = instance.children[i].properties.tabCaption;
			var bmpId = instance.children[i].properties.tabImage.bmpid;
			
			// calculate spacing
			var textWidth = graphics.stringExtent(tabCaption).x;
			tabWidth = 	TAB_INTERNAL_SPACE_START +  
						   	textWidth + 
						   	TAB_INTERNAL_SPACE_END;
			var includeTAB_INTERNAL_SPACE_MIDDLE = (tabCaption != null && tabCaption.length > 0 && bmpId != null && bmpId.length > 0);
			if (includeTAB_INTERNAL_SPACE_MIDDLE) {
				tabWidth += TAB_INTERNAL_SPACE_MIDDLE;
			}
			var includeIconSpace = (bmpId != null && bmpId.length);
			if (includeIconSpace) {
				tabWidth += TAB_ICON_WIDTH;
			}
			
			if (x + tabWidth > maxX ) {
				renderActiveTab = 1; //true 
				break;
			}
		
			x += tabWidth;		
		}
		if ( renderActiveTab == 1 ) {
			tabWidth = getTabWidth( laf, instance, graphics, activeTabIndex );
			var xNavigationKeys = tabWidth;
			if ( xWherePagesBegin + tabWidth > maxX ) {
				tabWidth = maxX - xWherePagesBegin;
			}
			xNavigationKeys = tabWidth + xWherePagesBegin; 
			drawTabPolygon( laf, graphics, activeTabIndex, xWherePagesBegin, y, tabWidth );
	
			textWidth = graphics.stringExtent(instance.children[activeTabIndex].properties.tabCaption).x;
			iconX = getIconXPosition( laf, instance.children[activeTabIndex].properties.tabCaption, textWidth, xWherePagesBegin );
	
			if ( iconX + TAB_ICON_WIDTH > maxX ) {
				iconX = maxX - TAB_ICON_WIDTH - TAB_INTERNAL_SPACE_END;
				if (instance.children[activeTabIndex].properties.tabImage.bmpid != null && instance.children[activeTabIndex].properties.tabImage.bmpid.length > 0 ) {
					tabWidth = maxX - xWherePagesBegin - TAB_INTERNAL_SPACE_MIDDLE - TAB_ICON_WIDTH - TAB_INTERNAL_SPACE_END;
				}			
			}
	
			graphics.drawFormattedString( instance.children[activeTabIndex].properties.tabCaption,
										  new Rectangle( xWherePagesBegin +  TAB_INTERNAL_SPACE_START,
													y + 1,
													tabWidth,
													TAB_TOTAL_HEIGHT), 
										  flags, 0);
	
			renderTabIcon(ViewLayout.prototype, 
						  instance,
						  instance.children[activeTabIndex],
						  laf, graphics, iconX, TAB_ICON_VERTICAL_PITCH, "tabImage", true);
	
			drawNavigationKeys( laf, graphics, xNavigationKeys  , y );
	
		} else {
			renderAllPages( laf, instance, graphics, xWherePagesBegin, y, maxX, maxX, TAB_TOTAL_HEIGHT );
		}
	}
}

function getFlags(instance){
	var properties = instance.properties;
	var flags = 0;

//	OVERFLOW_ELLIPSIS was included to add ellipsis.
	flags |= Font.OVERFLOW_ELLIPSIS;

 	return flags;
}

function renderAllPages( laf, instance, graphics, x, y, maxX, maximumWidth, maximumHeight ) {

	var TAB_INTERNAL_SPACE_START =laf.getInteger("tab.internal.space.start",7);
	var TAB_ICON_VERTICAL_PITCH= laf.getInteger("tab.icon.vertical.pitch",7);

		for (var i in instance.children) {		
			var tabWidth = getTabWidth( laf, instance, graphics, i );

			if ( i == 0) {
				maximumWidth = maximumWidth - x;
			} else {
				maximumWidth = maximumWidth - tabWidth;
			}		

			if (x + tabWidth < maxX) {
			 	drawTabPolygon( laf, graphics, i, x, y, tabWidth );				
	
				var flags = getFlags(instance);			
				graphics.drawFormattedString( instance.children[i].properties.tabCaption,
											  new Rectangle( x + TAB_INTERNAL_SPACE_START,
											  y + 1,
											  maximumWidth,
											  maximumHeight), 
									  		  flags, 0);

				textWidth = graphics.stringExtent(instance.children[i].properties.tabCaption).x;
				var iconX = getIconXPosition( laf, instance.children[i].properties.tabCaption, textWidth, x );
				
				renderTabIcon(ViewLayout.prototype, 
							  instance,
							  instance.children[i],
							  laf, graphics, iconX, TAB_ICON_VERTICAL_PITCH, "tabImage", true);

				//drawNavigationKeys( laf, graphics, x + tabWidth, y );

			} else {
				break;
			}		
			x += tabWidth;		
		}
}


function getTabWidth( laf, instance, graphics, tabIndex ) {

	var TAB_INTERNAL_SPACE_START =laf.getInteger("tab.internal.space.start",7);
	var TAB_INTERNAL_SPACE_MIDDLE =laf.getInteger("tab.internal.space.middle",5);
	var TAB_INTERNAL_SPACE_END = laf.getInteger("tab.internal.space.end",7);
	var TAB_ICON_WIDTH = laf.getInteger("tab.icon.width",18);

	// retrieve property values
	var tabCaption = instance.children[tabIndex].properties.tabCaption;
	var bmpId = instance.children[tabIndex].properties.tabImage.bmpid;
	
	// calculate spacing
	var textWidth = graphics.stringExtent(tabCaption).x;
	tabWidth = 	TAB_INTERNAL_SPACE_START + textWidth + TAB_INTERNAL_SPACE_END;

	// add an space between text and icon
	var includeTAB_INTERNAL_SPACE_MIDDLE = (tabCaption != null && tabCaption.length > 0 && bmpId != null && bmpId.length > 0);
	if (includeTAB_INTERNAL_SPACE_MIDDLE) {
		tabWidth += TAB_INTERNAL_SPACE_MIDDLE;
	}
		
	// add icon's width
	var includeIconSpace = (bmpId != null && bmpId.length);
	if (includeIconSpace) {
		tabWidth += TAB_ICON_WIDTH;
	}

	return tabWidth;
}

function getIconXPosition( laf, tabCaption, textWidth, xPosition ){

	var TAB_INTERNAL_SPACE_START =laf.getInteger("tab.internal.space.start",7);
	var TAB_INTERNAL_SPACE_MIDDLE =laf.getInteger("tab.internal.space.middle",5);

	var iconX = xPosition + TAB_INTERNAL_SPACE_START;
	var includeTextSpace = (tabCaption != null && tabCaption.length > 0);
	if (includeTextSpace) {
		iconX += textWidth + TAB_INTERNAL_SPACE_MIDDLE;
	}
	return iconX;
}

function drawTabPolygon( laf, graphics, tabIndex, x, y, tabWidth ) {

	var TAB_BACKGROUND_COLOR_ACTIVE = laf.getColor("tab.background.color.active");	
	var TAB_BACKGROUND_COLOR_INACTIVE =laf.getColor("tab.background.color.inactive");
	var TAB_BORDER_COLOR = laf.getColor("tab.border.color");
	var TAB_BORDER_SIZE = laf.getInteger("tab.border.size",1);	
	var TAB_TOTAL_HEIGHT = laf.getInteger("tab.total.height",25);
	var TAB_NOTCH_WIDTH = laf.getInteger("tab.notch.width",8);	
	var TAB_NOTCH_HEIGHT = laf.getInteger("tab.notch.height",9);

	// tab color configuration
	if ( tabIndex == activeTabIndex) {
		graphics.setBackground(TAB_BACKGROUND_COLOR_ACTIVE);
	} else {
		graphics.setBackground(TAB_BACKGROUND_COLOR_INACTIVE);
	}
	graphics.setForeground(TAB_BORDER_COLOR);
	
	// render the tab and its contents
	var pointArray = new Array (x, y,
								x + tabWidth - TAB_NOTCH_WIDTH, y,
								x + tabWidth, y + TAB_NOTCH_HEIGHT,
								x + tabWidth, y + TAB_TOTAL_HEIGHT,
								x, y + TAB_TOTAL_HEIGHT );
	graphics.fillPolygon(pointArray); 		
	graphics.drawPolygon(pointArray);

	if ( tabIndex == activeTabIndex) {
		graphics.setForeground(TAB_BACKGROUND_COLOR_ACTIVE);
		graphics.drawPolygon(new Array (x + TAB_BORDER_SIZE, 
										y + TAB_TOTAL_HEIGHT, 
										x + tabWidth - TAB_BORDER_SIZE,
										y + TAB_TOTAL_HEIGHT));
		graphics.setForeground(TAB_BORDER_COLOR);
	}
}

function drawNavigationKeys( laf, graphics, x, y ) {

	var NAVIGATION_KEY_VERTICAL_PITCH = laf.getInteger("navigation.key.vertical.pitch",8);
	var NAVIGATION_KEY_HORIZONTAL_PITCH = laf.getInteger("navigation.key.horizontal.pitch",5);
	var NAVIGATION_KEY_HEIGHT = laf.getInteger("navigation.key.height",10);
	var NAVIGATION_KEY_WIDTH = laf.getInteger("navigation.key.width",7);
	var NAVIGATION_KEY_BACKGROUND_COLOR = laf.getColor("navigation.key.background.color");
	var TAB_BORDER_COLOR = laf.getColor("tab.border.color");

	x = x + NAVIGATION_KEY_HORIZONTAL_PITCH;
	var pointArray = new Array (x, y + NAVIGATION_KEY_VERTICAL_PITCH,
								x + NAVIGATION_KEY_WIDTH, y + NAVIGATION_KEY_VERTICAL_PITCH + NAVIGATION_KEY_HEIGHT / 2,
								x, y + NAVIGATION_KEY_VERTICAL_PITCH + NAVIGATION_KEY_HEIGHT);
	graphics.setForeground(TAB_BORDER_COLOR);				
	graphics.setBackground(NAVIGATION_KEY_BACKGROUND_COLOR);
	graphics.fillPolygon(pointArray); 		
	graphics.drawPolygon(pointArray);
}

function renderTabIcon(prototype, instance, instanceWithProperty, laf, graphics, x, y, propertyId, doBlend) {
	var imagePropertyRendering = createImagePropertyRendering();
	imagePropertyRendering.setImageProperty(instanceWithProperty, propertyId, laf);
	imagePropertyRendering.setViewableSize(prototype.getViewableSize(instance, propertyId, laf));
	imagePropertyRendering.setAlignmentWeights(prototype.getAlignmentWeights(instance, propertyId, laf));
	imagePropertyRendering.setScaling(prototype.isScaling(instance, propertyId, laf));
	imagePropertyRendering.setPreservingAspectRatio(prototype.isPreservingAspectRatio(instance, propertyId, laf));

	imagePropertyRendering.setTransparencyHandling(doBlend 
		? getIImageRenderingClass().TRANSPARENCY_FLATTEN_AND_BLEND
		: getIImageRenderingClass().TRANSPARENCY_FLATTEN);
	imagePropertyRendering.render(graphics.getWrappedGC(), x, y);
}

function setActiveTabIndex(instance) {
	var visibleChildren = instance.getVisibleChildren();
	if (visibleChildren.length > 0) {
		var selectedPage = visibleChildren[0];
		for (var i in instance.children) {
			if (instance.children[i] == visibleChildren[0]) {
				activeTabIndex = i;
				break;
			}
		}
	}
}

ViewLayout.prototype.getViewableSize = function(instance, propertyId, laf) {
	return new Point (18, 18);
}


ViewLayout.prototype.isScaling = function(instance, propertyId, laf) {
	return true;
}


ViewLayout.prototype.getAlignmentWeights = function(instance, propertyId, laf) {
	return new Point(ImageUtils.ALIGN_LEFT, ImageUtils.ALIGN_TOP);
}


ViewLayout.prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
	return true;
}


ViewLayout.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {	
	return new Point(189, 26);

}