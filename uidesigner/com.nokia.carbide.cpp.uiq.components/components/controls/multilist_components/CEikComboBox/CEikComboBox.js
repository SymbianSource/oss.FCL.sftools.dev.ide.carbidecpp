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
* START_USECASES: CU12 END_USECASES
*
*
*/

include("../../../containerLibrary.js")
include("../../../renderLibrary.js")
include("../../../implLibrary.js")


function CEikComboBox() {
}

////////////////////////////////////////////////////////////////////////////////
//IVisualAppearance


CEikComboBox.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;	
	if(properties.isVisible != null && properties.isVisible == false)
   return;
	draw(instance, laf, graphics);
}


CEikComboBox.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return getPreferredSize(instance, laf, wHint, hHint);
}


function getPreferredSize(instance, laf, wHint, hHint) {
	var width=0;
	var height=0;
	var properties = instance.properties;

	var	font = laf.getFont("NormalFont");	
				
	var flags = getFlags(instance);	
	var extraspacex = 15;

	if (wHint >= 0)
		width = wHint;
	if (hHint >= 0)
		height = hHint;

	var bounds = new Point(width, height);

	var text = (String)(properties.activeText);
	if (text.length == 0)
		text = " ";

	//Gap between rows in Layout Manager
	var rowsGap = laf.getInteger("RowLayoutManager.rowsGap",11);

	// pixelGapBetweenLines is not rendered due to the Emulator doesn't show it
	var pixelGapBetweenLines = rowsGap; 

	var newBounds = font.formattedStringExtent(text, bounds, flags,pixelGapBetweenLines);
	if (newBounds.x + extraspacex > wHint && wHint != 0) {
		return new Point (wHint, newBounds.y - rowsGap); 
	} 
//	newBounds.y = newBounds.y - rowsGap;
	return new Point (newBounds.x + extraspacex, newBounds.y - rowsGap); 
//	return newBounds;
}

function getFlags(instance){
	var properties = instance.properties;
	var flags = 0;
	
	flags |= Font.VERTICAL_ALIGN_TOP;
	if(properties.textOnRight)
		flags |= Font.ALIGN_LEFT;
	/*else
		flags |= Font.ALIGN_RIGHT;*/
	flags |= Font.OVERFLOW_ELLIPSIS;		
	flags |= Font.ANTIALIAS_OFF;
	return flags;
}

function draw(instance, laf, graphics) {
	var properties = instance.properties	
	var flags = getFlags(instance);			
	
	var font = laf.getFont("NormalFont");
	graphics.setFont(font);
	var extraspacex = 15;		
	// get bounding rect
	var rect = instance.getRenderingBounds();
	rect.width-= extraspacex;
	if(rect.width<0){
		rect.width = 0;
	}
	rect.x = extraspacex;
	var reserveLengthText=properties.activeText;		
	if (properties.reserveLength > 0) {
			reserveLengthText=properties.text.substring(0, properties.reserveLength);
	}	
			
	var text = reserveLengthText;		
					
	//Gap between rows in Layout Manager.
	var rowsGap = laf.getInteger("RowLayoutManager.rowsGap",11);

	// pixelGapBetweenLines is not rendered due to the Emulator doesn't show it
	var pixelGapBetweenLines = rowsGap;

	rect.height = rect.height + rowsGap;

		graphics.drawFormattedString(reserveLengthText,
				rect,
				flags,
				pixelGapBetweenLines);
}

////////////////////////////////////////////////////////////////////////////////
//IComponentValidator

CEikComboBox.prototype.validate = function(instance) {
	properties = instance.properties;
	if (instance != null || instance != "") {
		if ( instance.isInstanceOf("com.nokia.carbide.uiq.CEikComboBox") && properties.items.length==0 ){
			return [createSimpleModelError(instance, 
					"flags",
					lookupString("validate.flags1"),
					instance.name )];												
		}	
		if ( instance.isInstanceOf("com.nokia.carbide.uiq.CEikComboBox") && properties.activeText.length == 0 ){
			return [createSimpleModelError(instance, 
					"flags",
					lookupString("validate.flags4"),
					instance.name )];				
		}		
		if ( instance.isInstanceOf("com.nokia.carbide.uiq.CEikComboBox") && properties.activeText.length > properties.maxTextLength ){
			return [createSimpleModelError(instance, 
					"flags",
					lookupString("validate.flags2"),
					instance.name )];				
		}	
		for (var i=0; i<properties.items.length; i++) {
			if(instance.isInstanceOf("com.nokia.carbide.uiq.CEikComboBox") && properties.items[i].length > properties.maxTextLength){
				return [createSimpleModelError(instance, 
						"flags",
						lookupString("validate.flags3"),
						instance.name )];														
			}
		}
		
	}
	return null;
}

CEikComboBox.prototype.queryPropertyChange = function(instance, propertyId, newValue) {
	return null;
}

///////////////////////////////////////////////////////////////////////////////////////
//IPropertyListener

CEikComboBox.prototype.propertyChanged = function(instance, property) {
	properties = instance.properties;
	if(property=="maxTextLength"){
		properties.maxTextLengthInc = properties.maxTextLength + 1; 			
	}			
}

////////////////////////////////////////////////////////////////////////////////
// IDirectLabelEdit

setupCommonDirectLabelEditing(CEikComboBox.prototype, 
	"activeText", 
	areaWithParentWidth,
	function(instance, laf) { return laf.getFont("NormalFont"); } 
	)