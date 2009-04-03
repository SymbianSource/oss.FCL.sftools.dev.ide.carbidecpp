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
include("../../../editorLibrary.js")


function CQikDateEditor() {
}

////////////////////////////////////////////////////////////////////////////////
//IVisualAppearance

CQikDateEditor.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;	
	if(properties.isVisible != null && properties.isVisible == false)
      return;
	draw(instance, laf, graphics, false);
}

CQikDateEditor.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
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

	var t = properties.date;
    var pixelGapBetweenLines= 0;
	var flags= 0;	
	if (t != null) {
		return font.formattedStringExtent(formatDate(t), bounds, flags, pixelGapBetweenLines);
	}
	else
		return null;
}


function draw(instance, laf, graphics) {
	var properties = instance.properties	

	var font = laf.getFont("NormalFont");
	graphics.setFont(font);

	//var drawBg = false;
	graphics.setBackground(getBackgroundColor(instance, laf));
	
	// get bounding rect
	var rect = instance.getRenderingBounds();

	var t = properties.date;

	var flags= 0;	

	graphics.fillRectangle(rect);

	/* *****drawing the text into the defined bounds***** */
	if (t != null){
		graphics.drawFormattedString(formatDate(t), rect, flags);
	}
}

////////////////////////////////////////////////////////////////////////////////
// IComponentValidator

function dateToInteger(d) {
	// ROUGH estimate, just for range checking
	// 372 = 31*12
	// the -0 changes text to integer
	if (d != null) {
		var v = ((d.year - 0) * 372) + ((d.month - 0) * 31) + (d.day - 0);
		//println("---------------year="+ d.year+", month="+d.month+", day="+d.day+" v = "+v);
		return v;
	} else
		return null
}

setupCommonRangeCheckingValidation(CQikDateEditor.prototype, 
		lookupString("date"), lookupString("dates"),
		"minDate", "maxDate", "date", 
		dateToInteger);



////////////////////////////////////////////////////////////////////////////////
// IDirectLabelEdit

setupCommonDirectLabelEditing(CQikDateEditor.prototype,
	"date",
	areaWithParentWidth, // areafunction
	CQikDateEditor.prototype.getFont
);

CQikDateEditor.prototype.getFont = function(instance, laf) {
	return laf.getFont("NormalFont");
}
