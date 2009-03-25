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

function CEikGlobalText() {
}


////////////////////////////////////////////////////////////////////////////////
// IVisualAppearance

CEikGlobalText.prototype.draw = function(instance, laf, graphics) {
	draw(instance, laf, graphics, false);
}

CEikGlobalText.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return getPreferredSize(instance, laf, wHint, hHint);
}

function getPreferredSize(instance, laf, wHint, hHint) {

	var width=0;
	var height=0;
	var properties = instance.properties;
	var font = laf.getFont("NormalFont");
	var flags = getFlags(instance);	
	
	// get bounding rect
	var layoutBounds = instance.getLayoutBounds();
	if (layoutBounds != null) {
		width = layoutBounds.width;
		height = layoutBounds.height;
	}

	// if either of these are empty, use the parent's bounds as starting point
	if ((width == 0) || (height == 0)) {
		layoutBounds = instance.parent.getLayoutBounds();
		if (layoutBounds != null) {
			width = layoutBounds.width;
			height = layoutBounds.height;
		}
	}
	
	if (wHint >= 0)
		width = wHint;
	if (hHint >= 0)
		height = hHint;

	var bounds = new Point(width, height);

	var text = (String)(properties.text);
    var pixelGapBetweenLines= 0;
	if (text.length == 0)
		text = " ";
	
	return font.formattedStringExtent(text, bounds, flags,pixelGapBetweenLines);
}

function getFlags(instance){
	var properties = instance.properties;
	var flags = 0;
	if (properties.flags != null) {
		if (properties.flags.EEikEdwinNoWrap)
			flags |= Font.WRAPPING_NONE;
		else
			flags |= Font.WRAPPING_ENABLED;	
	}
 	return flags;
}

function draw(instance, laf, graphics) {
	var properties = instance.properties	
	var flags = getFlags(instance);			
	
	var font = laf.getFont("NormalFont");
	graphics.setFont(font);

	//var drawBg = false;
	graphics.setBackground(getBackgroundColor(instance, laf));
	
	// get bounding rect
	var rect = instance.getRenderingBounds();

	/*textLimit property validation*/
	var textLimit=properties.text;		
	if (properties.textLimit > 0) {
			textLimit=properties.text.substring(0, properties.textLimit);
	}	

	var pixelGapBetweenLines= 0;	

	/* *****lines property is always set as 1***** */
	rect.height= getFontHeight(font);
			
	//if (drawBg) {
		graphics.fillRectangle(rect);
	//}	

	graphics.drawFormattedString(textLimit,
			rect, flags, pixelGapBetweenLines);

}

////////////////////////////////////////////////////////////////////////////////
// IPropertyListener


CEikGlobalText.prototype.propertyChanged = function(instance, property) {
	if (property != "size" && property != "location")
		instance.parent.forceLayout();
}