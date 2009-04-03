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
* START_USECASES: CU14 END_USECASES
*
*
*/



include("../../../renderLibrary.js")
include("../../../implLibrary.js")
include("../../../textLinesNumberLibrary.js")

function CEikLabel() {
}


////////////////////////////////////////////////////////////////////////////////
// IVisualAppearance


CEikLabel.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;	
	if(properties.isVisible != null && properties.isVisible == false)
      return;
	draw(instance, laf, graphics);
}


CEikLabel.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return getPreferredSize(instance, laf, wHint, hHint);
}


function getPreferredSize(instance, laf, wHint, hHint) {

	var width=0;
	var height=0;
	var properties = instance.properties;
	var font = laf.getFont(properties.standardFont);
	if (font == null)
		font = laf.getFont("NormalFont");	
				
	var flags = getFlags(instance);	

	if (wHint >= 0)
		width = wHint;
	if (hHint >= 0)
		height = hHint;

	var bounds = new Point(width, height);

	var text = (String)(properties.text);
	if (text.length == 0)
		text = " ";

	//Gap between rows in Layout Manager
	var rowsGap = laf.getInteger("RowLayoutManager.rowsGap",11);

	// pixelGapBetweenLines is not rendered due to the Emulator doesn't show it
	var pixelGapBetweenLines = rowsGap; 

	/* *****lines property ***** */
	var linesToDisplay = getMaxLinesAllowedByParent(instance); 
	var lines = TextRendering.formatIntoLines(font, text, width, flags, linesToDisplay);

	var newBounds = getTextBounds (lines, width, height, flags, font, pixelGapBetweenLines);

	if (newBounds.x > wHint && wHint != 0) {
		return new Point (wHint, newBounds.y - rowsGap) 
	} 
	newBounds.y = newBounds.y - rowsGap
	return newBounds
}

function getFlags(instance){
	var properties = instance.properties;
	var flags = 0;
	if (properties.flags != null) {
		if (properties.flags.underline != false)
			flags |= Font.OPTIONS_UNDERLINE;
		if (properties.flags.strikethrough != false)
			flags |= Font.OPTIONS_STRIKETHROUGH;

// "overrideAutoWrapping" flag commented due to emulator's performance always wraps the text.
/*		if (properties.flags.overrideAutoWrapping)
			flags |= Font.WRAPPING_NONE;
*/
// "autoWrappingOn" flag set to true due to emulator's performance always wraps the text.
		flags |= Font.WRAPPING_ENABLED;	

/*		if (properties.flags.autoWrappingOn)
			flags |= Font.WRAPPING_ENABLED;	
*/			
		switch (properties.horizontalAlignment) {
		case "EEikLabelAlignHCenter":
			flags |= Font.ALIGN_CENTER; break;
		case "EEikLabelAlignHLeft":
			flags |= Font.ALIGN_LEFT; break;
		case "EEikLabelAlignHRight":
			flags |= Font.ALIGN_RIGHT; break;
		}
		
		switch (properties.verticalAlignment) {
		case "EEikLabelAlignVBottom":
			flags |= Font.VERTICAL_ALIGN_BOTTOM; break;
		case "EEikLabelAlignVCenter":
			flags |= Font.VERTICAL_ALIGN_CENTER; break;
		case "EEikLabelAlignVTop":
			flags |= Font.VERTICAL_ALIGN_TOP; break;
		}
	}
 	return flags;
}

function draw(instance, laf, graphics) {
	var properties = instance.properties	
	var flags = getFlags(instance);			
	
	var font = laf.getFont("NormalFont");
	graphics.setFont(font);

	var drawBg = false;
	graphics.setBackground(getBackgroundColor(instance, laf));
	
	switch (properties.emphasis) {
	case "EPartialEmphasis":
		drawBg = true;
		graphics.setBackground(laf.getColor("EEikColorLabelBackgroundPartialEmphasis"));
		break;
	case "EFullEmphasis":
		drawBg = true;
		graphics.setBackground(laf.getColor("EEikColorLabelBackgroundFullEmphasis"));
	}
	
	// get bounding rect
	var rect = instance.getRenderingBounds();
	
	var reserveLengthText=properties.text;		
	if (properties.reserveLength > 0) {
			reserveLengthText=properties.text.substring(0, properties.reserveLength);
	}	
			
	var text = reserveLengthText;		
			
	if (drawBg) {
		graphics.fillRectangle(rect);
	}	
		
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
// IDirectLabelEdit

setupCommonDirectLabelEditing(CEikLabel.prototype, 
	"text", 
	areaWithParentWidth,
	function(instance, laf) { return laf.getFont(instance.properties.standardFont); } 
	)