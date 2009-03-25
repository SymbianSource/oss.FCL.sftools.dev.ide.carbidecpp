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


include("../../implLibrary.js")    //for setupCommonDirectLabelEditing function
include("../popupDialogLibrary.js")//for calculateBounds, getIconRect functions

function InformationMessage() {
}

////////////////////////////////////////////////////////////////////////////////
// IVisualAppearance

InformationMessage.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;	
	if(properties.isVisible != null && properties.isVisible == false)
      return;
	draw(instance, laf, graphics, false);
}

InformationMessage.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null;
}

function draw(instance, laf, graphics) {
	var properties = instance.properties;	
	var font = laf.getFont("NormalFont");
	graphics.setFont(font);

	// get bounding rect
	var rect = instance.getRenderingBounds();
	
	var image = laf.getImage("informationMessage.content.background");
	if (image != null) {
		drawImage(instance, graphics, image, rect, false);
	}	

	/* *****drawing the text into the defined bounds***** */
	renderText( instance, laf, graphics, properties.text, rect.x, rect.y, rect.width, rect.height);

}

function renderText ( instance, laf, graphics, text, rectX, rectY, rectWidth, rectHeight ) {
	var pixelGapBetweenLines= 0;
	var flags = Font.ALIGN_LEFT | Font.WRAPPING_ENABLED | Font.OVERFLOW_ELLIPSIS | Font.ANTIALIAS_OFF;
	var textRectX = laf.getInteger("informationMessage.text.left.margin", 10); 
	var textRectY = laf.getInteger("informationMessage.text.top.margin", 9);
	var textRectWidth = rectWidth - (textRectX + laf.getInteger("informationMessage.text.right.margin", 9));  
	var textToDisplay = getTextToDisplay ( laf, textRectWidth, rectHeight, text, false, false, true );
	graphics.drawFormattedString( textToDisplay, 
			new Rectangle( textRectX, textRectY, textRectWidth, rectHeight ),
			flags, pixelGapBetweenLines);
}

function searchTopLevelContainerRectangle(instance, laf) {
	var DEFAULT_VALUE = new Rectangle(0, 0, 240, 300);
	var VIEW_ID = "com.nokia.carbide.uiq.CQikView";
	var VIEW_KEY = "view.container.bounds";
	var SIMPLE_DIALOG_ID = "com.nokia.carbide.uiq.CQikSimpleDialog";
	var SIMPLE_DIALOG_KEY = "simpleDialog.container.bounds";
      
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

setupCommonDirectLabelEditing(InformationMessage.prototype,
	"text",
	areaWithParentWidth, // areafunction
	InformationMessage.prototype.getFont
);

InformationMessage.prototype.getFont = function(instance, laf) {
	return laf.getFont("NormalFont");
}


////////////////////////////////////////////////////////////////////////////////
// ILayout

InformationMessage.prototype.layout = function(instance, laf) {
	getLayout(instance, laf);
}

function getLayout(instance, laf) {

	var properties = instance.properties;
	var text = (String)(properties.text);

	var rect = new Rectangle(0, 0, 0, 0);
	
	var container = searchTopLevelContainerRectangle(instance, laf);
	var padding = laf.getInteger("informationMessage.padding", 2);
	var leftMargin;
	var rightMargin;
	var topMargin;
	var bottomMargin;
	var rectWidth = 0;
	var x = 0;
	var y = 0;

	switch(properties.alignment){
		case "EHLeftVTop":
			leftMargin = laf.getInteger("informationMessage.alignment.EHLeftVTop.leftMargin", 13);
			rightMargin = laf.getInteger("informationMessage.alignment.EHLeftVTop.rightMargin", 15);
			rectWidth = container.width - leftMargin - rightMargin;
			x = leftMargin;
			y = container.y + laf.getInteger("informationMessage.alignment.EHLeftVTop.topMargin", 11);
			break;
		case "EHLeftVCenter":
			leftMargin = laf.getInteger("informationMessage.alignment.EHLeftVCenter.leftMargin", 13);
			rightMargin = laf.getInteger("informationMessage.alignment.EHLeftVCenter.rightMargin", 15);
			rectWidth = container.width - leftMargin - rightMargin;
			x = leftMargin;
			y = container.y + laf.getInteger("informationMessage.alignment.EHLeftVCenter.topMargin", 55);
			break;
		case "EHLeftVBottom":
			leftMargin = laf.getInteger("informationMessage.alignment.EHLeftVBottom.leftMargin", 13);
			rightMargin = laf.getInteger("informationMessage.alignment.EHLeftVBottom.rightMargin", 15);
			rectWidth = container.width - leftMargin - rightMargin;
			x = leftMargin;
			y = container.y + laf.getInteger("informationMessage.alignment.EHLeftVBottom.topMargin", 100);
			break;
		case "EHCenterVTop":
			leftMargin = laf.getInteger("informationMessage.alignment.EHCenterVTop.leftMargin", 14);
			rightMargin = laf.getInteger("informationMessage.alignment.EHCenterVTop.rightMargin", 14);
			rectWidth = container.width - leftMargin - rightMargin;
			x = leftMargin;
			y = container.y + laf.getInteger("informationMessage.alignment.EHCenterVTop.topMargin", 11);
			break;
		case "EHCenterVCenter":
			leftMargin = laf.getInteger("informationMessage.alignment.EHCenterVCenter.leftMargin", 14);
			rightMargin = laf.getInteger("informationMessage.alignment.EHCenterVCenter.rightMargin", 14);
			rectWidth = container.width - leftMargin - rightMargin;
			x = leftMargin;
			y = container.y + laf.getInteger("informationMessage.alignment.EHCenterVCenter.topMargin", 55);
			break;
		case "EHCenterVBottom":
			leftMargin = laf.getInteger("informationMessage.alignment.EHCenterVBottom.leftMargin", 14);
			rightMargin = laf.getInteger("informationMessage.alignment.EHCenterVBottom.rightMargin", 14);
			rectWidth = container.width - leftMargin - rightMargin;
			x = leftMargin;
			y = container.y + laf.getInteger("informationMessage.alignment.EHCenterVBottom.topMargin", 100);
			break;
		case "EHRightVTop":
			leftMargin = laf.getInteger("informationMessage.alignment.EHRightVTop.leftMargin", 15);
			rightMargin = laf.getInteger("informationMessage.alignment.EHRightVTop.rightMargin", 13);
			rectWidth = container.width - leftMargin - rightMargin;
			x = leftMargin;
			y = container.y + laf.getInteger("informationMessage.alignment.EHRightVTop.topMargin", 11);
			break;
		case "EHRightVCenter":
			leftMargin = laf.getInteger("informationMessage.alignment.EHRightVCenter.leftMargin", 15);
			rightMargin = laf.getInteger("informationMessage.alignment.EHRightVCenter.rightMargin", 13);
			rectWidth = container.width - leftMargin - rightMargin;
			x = leftMargin;
			y = container.y + laf.getInteger("informationMessage.alignment.EHRightVCenter.topMargin", 55);
			break;
		case "EHRightVBottom":
			leftMargin = laf.getInteger("informationMessage.alignment.EHRightVBottom.leftMargin", 15);
			rightMargin = laf.getInteger("informationMessage.alignment.EHRightVBottom.rightMargin", 13);
			rectWidth = container.width - leftMargin - rightMargin;
			x = leftMargin;
			y = container.y + laf.getInteger("informationMessage.alignment.EHRightVBottom.topMargin", 100);
			break;
		default:
			break;
	}
	var heightText = getTextToDisplay ( laf, rectWidth, container.height, properties.text, true, false, false );
	var widthText = getTextToDisplay ( laf, rectWidth, container.height, properties.text, false, true, false );

	var textRectLeft = laf.getInteger("informationMessage.text.left.margin", 10); 
	var textRectRigth = laf.getInteger("informationMessage.text.right.margin", 9);
	var textRectWidth = widthText + textRectLeft + textRectRigth;

	var textRectTop = laf.getInteger("informationMessage.text.top.margin", 9); 
	var textRectBottom = laf.getInteger("informationMessage.text.bottom.margin", 11);
	var textRectHeight= heightText + textRectTop + textRectBottom;	

	if (textRectWidth > rectWidth && rectWidth != 0) {
		rect.width = rectWidth;
	} else {
		rect.width = textRectWidth;
	}

	rect.y = y;

	switch(properties.alignment){
		case "EHLeftVTop":
 			rect.x = x ;
			break;
		case "EHLeftVCenter":
			rect.x = x ;
			break;
		case "EHLeftVBottom":
			rect.x = x ;
			break;
		case "EHCenterVTop":
			rect.x = (container.width - rect.width) / 2 ;
			break;
		case "EHCenterVCenter":
			rect.x = (container.width - rect.width) / 2 ;
			break;
		case "EHCenterVBottom":
			rect.x = (container.width - rect.width) / 2 ;
			break;
		case "EHRightVTop":
			rect.x = container.width - (rect.width + rightMargin) ;
			break;
		case "EHRightVCenter":
			rect.x = container.width - (rect.width + rightMargin) ;
			break;
		case "EHRightVBottom":
			rect.x = container.width - (rect.width + rightMargin) ;
			break;
		default:
			break;
	}

	properties.location.x = rect.x;
	properties.location.y = rect.y;
	properties.size.width = rect.width;
	properties.size.height = textRectHeight;
}

function getTextToDisplay ( laf, width, height, text, returnHeight, returnWidth, returnText ) {
	var pixelGapBetweenLines = laf.getInteger("informationMessage.lineGap", 0); 
	var flags = Font.ALIGN_LEFT | Font.WRAPPING_ENABLED | Font.OVERFLOW_ELLIPSIS | Font.ANTIALIAS_OFF;
	var font = laf.getFont("NormalFont");	

	/* *****lines property is always set as 2***** */
	if (text.length == 0) {
		textToDisplay = " ";
		textWidth = 19;
		textHeight = 15;
	} else  {
		var linesToDisplay = 2; 
		var lines = TextRendering.formatIntoLines(font, text, width, flags, linesToDisplay );
	
		var textToDisplay = "";
		var textHeight = 0;
		var textWidth = 0;
		for (var i = 0; i < lines.length; i++ ) {
			var newBounds = font.formattedStringExtent(lines[i], 
							new Point (width, height),
							flags, pixelGapBetweenLines);
			textHeight += newBounds.y;	
			textToDisplay += lines[i];
			if (newBounds.x > textWidth) {
				textWidth = newBounds.x;
			}
		}
	}

	if ( returnWidth ) {
		return textWidth;
	}
	if ( returnHeight ) {
		return textHeight;
	}
	if ( returnText ) {
		return textToDisplay;
	}
}

////////////////////////////////////////////////////////////////////////////////
//IComponentInstancePropertyListener

InformationMessage.prototype.propertyChanged = function(instance, property) {
	if (property != "size" && property != "location")
		instance.forceLayout();
}

////////////////////////////////////////////////////////////////////////////////
// IComponentValidator

InformationMessage.prototype.validate = function(instance) {
	return validateTextMaxLength(instance, "text", instance.properties.text);
}

InformationMessage.prototype.queryPropertyChange = function(instance, propertyPath, newValue) {
	return validateTextMaxLength(instance, propertyPath,(String)(newValue));
}

function validateTextMaxLength (instance, propertyPath, newValue) {
	if (propertyPath == "text") {
		if (newValue.length > 80) {
			return [createSimpleModelError(instance, 
					propertyPath,
					lookupString("textMaxLengthValidationError"),
					instance.name )
				   ];
		}
	}
	return null;
}
