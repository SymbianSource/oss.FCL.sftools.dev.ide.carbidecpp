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
* START_USECASES: CU20 END_USECASES
*
*
*/


include("../../implLibrary.js")  //for setupCommonDirectLabelEditing function
include("../../renderLibrary.js")//for getBackgroundColor function

function InformationDialog() {
}

////////////////////////////////////////////////////////////////////////////////
// IVisualAppearance

InformationDialog.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;	
	if(properties.isVisible != null && properties.isVisible == false)
      return;
	draw(instance, laf, graphics, false);
}

InformationDialog.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null;
}

function draw(instance, laf, graphics) {
	var properties = instance.properties;	
	var font = laf.getFont("NormalFont");
	graphics.setFont(font);	
	graphics.setBackground(getBackgroundColor(instance, laf));
	
	// get bounding rect
	var rect = instance.getRenderingBounds();

	var pixelGapBetweenLines = laf.getInteger("informationDialog.lineGap", 0); 

	/* *****drawing the text into the defined bounds***** */	
	renderText( instance, laf, graphics, properties.text, 
			rect.x, rect.y, rect.width, rect.height, pixelGapBetweenLines );

	/* *****drawing the button's text into the defined bounds***** */
	renderButtonText( instance, laf, graphics, rect.height, pixelGapBetweenLines );

	/* *****drawing the title text into the defined bounds***** */
	renderTitle( instance, laf, graphics, font, properties.title, pixelGapBetweenLines );

	/* *****drawing the icon into the defined bounds***** */
	renderIcon( instance, laf, graphics);

}

function renderText ( instance, laf, graphics, text, rectX, rectY, rectWidth, rectHeight, pixelGapBetweenLines ) {
	var flags = Font.ALIGN_LEFT | Font.WRAPPING_ENABLED;

	var container = searchTopLevelContainerRectangle(instance, laf);
	var textHeightPermitted = container.height - container.y - getNoTextHeight( laf );

	var buttonRect =  laf.getRectangle("informationDialog.button");
	var height = rectHeight - buttonRect.height;
	var noTextTopHeight = getNoTextHeight( laf ) - getNoTextBottomHeight( laf );

	var image = laf.getImage("informationDialog.content.background");
	var imageScroll = laf.getImage("informationDialog.scrollContent.background");

	if (image != null) {
		drawImage(instance, graphics, image, new Rectangle (rectX, rectY, rectWidth, height), false);
	}

	if ( (rectHeight == (container.height - container.y)) && ( imageScroll != null) ) {
		var titleRect =  laf.getRectangle("informationDialog.title");
		var scrollY = titleRect.height + titleRect.y;
		var scrollHeight = rectHeight - (titleRect.height + titleRect.y) - buttonRect.height;
		drawImage(instance, graphics, imageScroll, 
			new Rectangle (rectX, scrollY, rectWidth, scrollHeight), 
			false);
	}

	rectX = laf.getInteger("informationDialog.left.text.margin", 12); 
	rectY = noTextTopHeight;	
	rectWidth = rectWidth - getLeftRightTextMargin ( laf );
//	println("PRINT 11 ***** X - TEXT - DRAWING = "+ rectX);
//	println("PRINT 12 ***** Y - TEXT - DRAWING = "+ rectY);

	var returnHeight = false;
	var textToDisplay = getTextToDisplay ( laf, rectWidth, textHeightPermitted,
							 text, textHeightPermitted, returnHeight );
	graphics.drawFormattedString( textToDisplay, 
						new Rectangle ( rectX, rectY, rectWidth, rectHeight ),
						flags, pixelGapBetweenLines);
}

function renderButtonText ( instance, laf, graphics, rectHeight, pixelGapBetweenLines ) {
	var flags = Font.ALIGN_CENTER | Font.WRAPPING_ENABLED;	
	var titlePadding = laf.getInteger("informationDialog.title.padding", 5); 
	var buttonRect =  laf.getRectangle("informationDialog.button");
	buttonRect.y = rectHeight - buttonRect.height;
	var image = laf.getImage("informationDialog.button.background");
	if (image != null) {
		drawImage(instance, graphics, image, buttonRect, false);
	}
}

function renderTitle( instance, laf, graphics, font, title, pixelGapBetweenLines) {
	var flags = Font.ALIGN_LEFT | Font.WRAPPING_ENABLED| Font.ANTIALIAS_OFF;
	var titleRect =  laf.getRectangle("informationDialog.title");
	var titlePadding = laf.getInteger("informationDialog.title.padding", 5); 
	var titleRectWidth = titleRect.width - ( titlePadding * 2);
	var image = laf.getImage("informationDialog.title.background");
	if (image != null) {
		drawImage(instance, graphics, image, titleRect, false);
	}
	if ( title != "" ) {
		//	OVERFLOW_ELLIPSIS was included to add ellipsis.
		flags |= Font.OVERFLOW_ELLIPSIS;
		/* *****lines is always set as 1***** */
		var linesToDisplay = 1; 
		var titleRectX = titleRect.x + titlePadding;
		var titleRectY = titleRect.y + titlePadding;
		var lines = TextRendering.formatIntoLines(font, title, titleRectWidth, flags, linesToDisplay);
		graphics.drawFormattedString( lines[0], 
			new Rectangle( titleRectX, titleRectY, titleRectWidth, titleRect.height ), 
			flags, pixelGapBetweenLines);
	}
}

function renderIcon( instance, laf, graphics) {
	var container = searchTopLevelContainerRectangle(instance, laf);
	var titleRectRightMargin = laf.getRectangle("informationDialog.title.rectangle.right.margin");
	var titleRect =  laf.getRectangle("informationDialog.title");

	var iconRect = laf.getRectangle("informationDialog.icon");
	var iconWidth = iconRect.width;
	var iconHeight = iconRect.height;
	var iconX = ( container.width - titleRectRightMargin - iconWidth ) / 2;
	var iconY = laf.getInteger("informationDialog.top.margin", 6) + 
				titleRect.height + 
				laf.getInteger("informationDialog.titleIcon.margin", 8);
	var image = laf.getImage("informationDialog.icon");
	if (image != null) {
		graphics.drawImage(image,  iconX, iconY);
	}
}

function searchTopLevelContainerRectangle(instance, laf) {
	var DEFAULT_VALUE = new Rectangle(0, 0, 240, 300);
	var VIEW_ID = "com.nokia.carbide.uiq.CQikView";
	var VIEW_KEY = "view.informationDialog.bounds";
	var SIMPLE_DIALOG_ID = "com.nokia.carbide.uiq.CQikSimpleDialog";
	var SIMPLE_DIALOG_KEY = "simpleDialog.informationDialog.bounds";
      
	if (instance.component.id == VIEW_ID) {
		return laf.getRectangle(VIEW_KEY);
	} else if (instance.component.id == SIMPLE_DIALOG_ID) {
		return laf.getRectangle(SIMPLE_DIALOG_KEY);
	} else if (instance.parent != null) {
		return searchTopLevelContainerRectangle(instance.parent, laf);
	} else {
		return DEFAULT_VALUE;
	}
}
////////////////////////////////////////////////////////////////////////////////
// IDirectLabelEdit

setupCommonDirectLabelEditing(InformationDialog.prototype,
	"text",
	areaWithParentWidth, // areafunction
	InformationDialog.prototype.getFont
);

InformationDialog.prototype.getFont = function(instance, laf) {
	return laf.getFont("NormalFont");
}


////////////////////////////////////////////////////////////////////////////////
// ILayout

InformationDialog.prototype.layout = function(instance, laf) {
	getLayout(instance, laf);
}

function getLayout(instance, laf) {
	var properties = instance.properties;
	var rect = new Rectangle(0, 0, 0, 0);
	var container = searchTopLevelContainerRectangle(instance, laf);

	var noTextheight = getNoTextHeight( laf );
//	println("PRINT 3 ***** HEIGHT - NO TEXT = "+ getNoTextHeight( laf ));
	var textHeightPermitted = container.height - container.y - noTextheight;
//	println("PRINT 4 ***** TEXT - HEIGHT - PERMITTED = "+ textHeightPermitted);
	var width = container.width - getLeftRightTextMargin ( laf );
	var returnHeight = true;
	var height = getTextToDisplay ( laf, width, textHeightPermitted, 
					properties.text, textHeightPermitted, returnHeight );
//	println("PRINT 6 ***** HEIGHT - TEXT - TO - DISPLAY = "+ height);

	if ( ( container.height != 0 ) && ( height > textHeightPermitted ) ) {		
		rect.height = container.height - container.y;
	} else {
		rect.height = height + noTextheight ;
	}

	rect.width = container.width;
	rect.y = container.height - rect.height;

	properties.location.x = rect.x;
	properties.location.y = rect.y;
	properties.size.width = rect.width;
	properties.size.height = rect.height;
//	println("PRINT 7 ***** HEIGHT - INFORMATION - DIALOG = "+ rect.height);
//	println("PRINT 8 ***** WIDTH - INFORMATION - DIALOG = "+ rect.width);
//	println("PRINT 9 ***** X - INFORMATION - DIALOG = "+ rect.x);
//	println("PRINT 10 ***** Y - INFORMATION - DIALOG = "+ rect.y);
}

function getNoTextHeight( laf ) {
	var titleRect =  laf.getRectangle("informationDialog.title");
	var iconRect = laf.getRectangle("informationDialog.icon");
	var topMargin = laf.getInteger("informationDialog.top.margin", 6); 
	var titleIconMargin = laf.getInteger("informationDialog.titleIcon.margin", 8); 
	var iconTextMargin = laf.getInteger("informationDialog.iconText.margin", 8); 
//	println("PRINT 2 ***** HEIGHT - TOP - NO TEXT = "+ (topMargin + titleRect.height + 
//			titleIconMargin + iconRect.height + 
//			iconTextMargin) );
	return ( topMargin + titleRect.height + 
			titleIconMargin + iconRect.height + 
			iconTextMargin + 
			getNoTextBottomHeight( laf ) );
}

function getNoTextBottomHeight( laf ) {
	var buttonRect = laf.getRectangle("informationDialog.button");
	var textButtonMargin = laf.getInteger("informationDialog.textButton.margin", 7); 
//	println("PRINT 1 ***** HEIGHT - BOTTOM - NO TEXT = "+ (textButtonMargin + buttonRect.height));
	return ( textButtonMargin + buttonRect.height );
}

function getLeftRightTextMargin ( laf ) {
	var leftTextMargin = laf.getInteger("informationDialog.left.text.margin", 12); 
	var rightTextMargin = laf.getInteger("informationDialog.right.text.margin", 21); 
	return ( leftTextMargin + rightTextMargin );
}

function getTextToDisplay ( laf, width, height, text, textHeightPermitted, returnHeight ) {
	var pixelGapBetweenLines = laf.getInteger("informationDialog.lineGap", 0); 
	var flags = Font.ALIGN_LEFT | Font.WRAPPING_ENABLED;
	var font = laf.getFont("NormalFont");	
//	println("PRINT 5 ***** WIDTH - TEXT - TO - DISPLAY = "+ width);
	var linesToDisplay = 2147483647; 
	var lines = TextRendering.formatIntoLines(font, text, width, flags, linesToDisplay );

	var newBounds;
	var textToDisplay = "";
	var textHeight = 0;

	for (var i = 0; i < lines.length; i++ ) {
		newBounds = font.formattedStringExtent(lines[i], 
						new Point (width, height),
						flags, pixelGapBetweenLines);
		textHeight += newBounds.y;	
		if ( textHeight <= textHeightPermitted ) {
			textToDisplay += lines[i];
		} else {
			break;
		}
	}

	if ( returnHeight ) {
		return textHeight;
	} else {
		return textToDisplay;
	}
}

////////////////////////////////////////////////////////////////////////////////
//IComponentInstancePropertyListener

InformationDialog.prototype.propertyChanged = function(instance, property) {
	if (property != "size" && property != "location")
		instance.forceLayout();
}