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
* START_USECASES: CU19 END_USECASES
*
*
*/


include("../../../implLibrary.js")
include("../../../renderLibrary.js")
include("../../../textLinesNumberLibrary.js")

function CEikEdwin() {
}

////////////////////////////////////////////////////////////////////////////////
// IVisualAppearance

CEikEdwin.prototype.draw = function(instance, laf, graphics) {
	/*var properties = instance.properties;	
	if(properties.isVisible != null && properties.isVisible == false)
      return;*/
	draw(instance, laf, graphics, false);
}

CEikEdwin.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return getPreferredSize(instance, laf, wHint, hHint);
}

function getPreferredSize(instance, laf, wHint, hHint) {

	var width=0;
	var height=0;
	var properties = instance.properties;
	var font = laf.getFont("NormalFont");

	var flags = getFlags(instance);	
	
	if (wHint >= 0)
		width = wHint;
	if (hHint >= 0)
		height = hHint;

	var bounds = new Point(width, height);

	var text = (String)(properties.text);
    var pixelGapBetweenLines= 0;
	if (text.length == 0)
		text = " ";

	/* *****lines property ***** */
	var linesToDisplay = getMaxLinesAllowedByParent(instance); 
	var lines = TextRendering.formatIntoLines(font, text, width, flags, linesToDisplay);

	var newBounds = getTextBounds (lines, width, height, flags, font, pixelGapBetweenLines);

	if (newBounds.x > wHint && wHint != 0) {
		return new Point (wHint, newBounds.y) 
	}	
	return newBounds
}

function getFlags(instance){
	var properties = instance.properties;
	var flags = 0;

//	OVERFLOW_ELLIPSIS was included to add ellipsis.
	flags |= Font.OVERFLOW_ELLIPSIS;

	if (properties.flags != null) {
		if (properties.flags.EEikEdwinNoWrap){
			flags |= Font.WRAPPING_NONE;
		}else {
			flags |= Font.WRAPPING_ENABLED;	
		}
	}

 	return flags;
}

function draw(instance, laf, graphics) {
	var properties = instance.properties	
	var flags = getFlags(instance);			
	
	var font = laf.getFont("NormalFont");
	graphics.setFont(font);

	graphics.setBackground(getBackgroundColor(instance, laf));
	
	// get bounding rect
	var rect = instance.getRenderingBounds();

	/*maxLength property validation*/
	var maxLengthText=properties.text;		
	if (properties.maxLength > 0) {
		maxLengthText=properties.text.substring(0, properties.maxLength);
	}	
			
	var pixelGapBetweenLines= 0;	
			
	graphics.fillRectangle(rect);

	/* *****drawing the text into the defined bounds***** */
	graphics.drawFormattedString(maxLengthText,
			rect, flags, pixelGapBetweenLines);

}

////////////////////////////////////////////////////////////////////////////////
// IDirectLabelEdit

setupCommonDirectLabelEditing(CEikEdwin.prototype,
	"text",
	areaWithParentWidth, // areafunction
	CEikEdwin.prototype.getFont
);

CEikEdwin.prototype.getFont = function(instance, laf) {
	return laf.getFont("NormalFont");
}

////////////////////////////////////////////////////////////////////////////////
// IPropertyListener

CEikEdwin.prototype.propertyChanged = function(instance, property) {
	if (property != "size" && property != "location")
		instance.parent.forceLayout();
}

////////////////////////////////////////////////////////////////////////////////
//IComponentValidator

CEikEdwin.prototype.validate = function(instance) {
	var properties = instance.properties;
	if ( instance.isInstanceOf("com.nokia.carbide.uiq.CEikEdwin") && 
		properties.flags.EEikEdwinNoAutoSelection  &&
		properties.flags.EEikEdwinEnableAutoSelection ) {
		return [createSimpleModelError(instance, 
								"flags",
								formatString(lookupString("validateFlagsAutomaticSelection"),
								[instance.name ]),								
								null )
							   ];	
	}
	if ( instance.isInstanceOf("com.nokia.carbide.uiq.CEikEdwin") && 
		properties.flags.EEikEdwinDisableAutoCurEnd  && 
		(properties.flags.EEikEdwinFindStringMaxLen || 
		properties.flags.EEikEdwinJustAutoCurEnd )) {
		var flag ; 
		if (properties.flags.EEikEdwinFindStringMaxLen) {
			flag = lookupString("flags.EEikEdwinFindStringMaxLen");
		} else {
			flag = lookupString("flags.EEikEdwinJustAutoCurEnd");
		}
		return [createSimpleModelError(instance, 
							"flags",
							formatString(lookupString("validate.flags"),
							[flag, instance.name ]),
							null)
						   ];					
	}
	return validateTextLength (instance, "text", properties.text);
}

CEikEdwin.prototype.queryPropertyChange = function(instance, propertyId, newValue) {
	return null;//validateTextLength (instance, propertyId,(String)(newValue));
}

function validateTextLength (instance, propertyId, newValue) {
	if (propertyId == "text") {
		if (newValue.length > instance.properties.maxLength &&  
			instance.isInstanceOf("com.nokia.carbide.uiq.CEikEdwin")) {
			return [createSimpleModelError(instance, 
					"text",
					formatString(lookupString("textEditorMaxLengthValidationError"),
								[instance.name ]),	
					null )
				   ];
		}
	}
	return null;
}
