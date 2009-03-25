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


function CQikFloatingPointEditor() {
}

////////////////////////////////////////////////////////////////////////////////
//IVisualAppearance

CQikFloatingPointEditor.prototype.draw = function(instance, laf, graphics) {
	/*var properties = instance.properties;	
	if(properties.isVisible != null && properties.isVisible == false)
      return;*/
	draw(instance, laf, graphics, false);
}

CQikFloatingPointEditor.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return getPreferredSize(instance, laf, wHint, hHint);
}

function getPreferredSize(instance, laf, wHint, hHint) {

	var width=0;
	var height=0;
	var FLOATING_EDITOR_MAX_LINES_NUMBER = 2;
	var properties = instance.properties;
	var font = laf.getFont("NormalFont");
	
	if (wHint >= 0)
		width = wHint;
	if (hHint >= 0)
		height = hHint;

	var bounds = new Point(width, height);

	var value = (String)(properties.value);
    var pixelGapBetweenLines= 0;
	var flags= 0;
	flags |= Font.OVERFLOW_ELLIPSIS;		

	if (value.length == 0)
		value = " ";

	/* *****lines property ***** */
	var linesToDisplay = getMaxLinesAllowedByParent(instance); 
	var lines = TextRendering.formatIntoLines(font, value, width, flags, linesToDisplay);
	
	if (lines.length > FLOATING_EDITOR_MAX_LINES_NUMBER) {
		lines.length = FLOATING_EDITOR_MAX_LINES_NUMBER;
	}
	var newBounds = getTextBounds (lines, width, height, flags, font, pixelGapBetweenLines);

	if (newBounds.x > wHint && wHint != 0) {
		return new Point (wHint, newBounds.y) 
	} 
	return newBounds
	
}


function draw(instance, laf, graphics) {
	var properties = instance.properties	

	var font = laf.getFont("NormalFont");
	graphics.setFont(font);

	//var drawBg = false;
	graphics.setBackground(getBackgroundColor(instance, laf));
	
	// get bounding rect
	var rect = instance.getRenderingBounds();

	var textValue=properties.value;		

	var flags= 0;	
	flags |= Font.OVERFLOW_ELLIPSIS;		

	graphics.fillRectangle(rect);

	/* *****drawing the text into the defined bounds***** */
	graphics.drawFormattedString(textValue, rect, flags);

}


////////////////////////////////////////////////////////////////////////////////
// IDirectLabelEdit

setupCommonDirectLabelEditing(CQikFloatingPointEditor.prototype,
	"value",
	areaWithParentWidth, // areafunction
	CQikFloatingPointEditor.prototype.getFont
);

CQikFloatingPointEditor.prototype.getFont = function(instance, laf) {
	return laf.getFont("NormalFont");
}
