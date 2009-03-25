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


function CQikNumberEditor() {
}

////////////////////////////////////////////////////////////////////////////////
//IVisualAppearance

CQikNumberEditor.prototype.draw = function(instance, laf, graphics) {
	/*var properties = instance.properties;	
	if(properties.isVisible != null && properties.isVisible == false)
      return;*/
	draw(instance, laf, graphics, false);
}

CQikNumberEditor.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return getPreferredSize(instance, laf, wHint, hHint);
}

function getPreferredSize(instance, laf, wHint, hHint) {

	var width=0;
	var height=0;
	var properties = instance.properties;
	var font = laf.getFont("NormalFont");

	if (wHint >= 0)
		width = wHint;
	if (hHint >= 0)
		height = hHint;

	var bounds = new Point(width, height);

	var textValue=(String)(properties.value);
    var pixelGapBetweenLines= 0;
	var flags= 0;	
	flags |= Font.OVERFLOW_ELLIPSIS;		

	if (textValue.length == 0)
		textValue = " ";

	return font.formattedStringExtent(textValue, bounds, flags, pixelGapBetweenLines);
}


function draw(instance, laf, graphics) {
	var properties = instance.properties	

	var font = laf.getFont("NormalFont");
	graphics.setFont(font);

	graphics.setBackground(getBackgroundColor(instance, laf));
	
	var rect = instance.getRenderingBounds();// get bounding rect

	var textValue=properties.value;	

	var flags= 0;		
	flags |= Font.OVERFLOW_ELLIPSIS;		

	graphics.fillRectangle(rect);

	/* *****drawing the text into the defined bounds***** */
	graphics.drawFormattedString(textValue, rect, flags);

}


////////////////////////////////////////////////////////////////////////////////
// IDirectLabelEdit

setupCommonDirectLabelEditing(CQikNumberEditor.prototype,
	"value",
	areaWithParentWidth, // areafunction
	CQikNumberEditor.prototype.getFont
);

CQikNumberEditor.prototype.getFont = function(instance, laf) {
	return laf.getFont("NormalFont");
}
