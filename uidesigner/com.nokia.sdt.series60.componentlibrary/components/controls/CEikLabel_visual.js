/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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
*
*/


include("../renderLibrary.js")
include("../implLibrary.js")

function CEikLabelVisual() {
}

function isDialog(inst) {
	return inst.componentId == "com.nokia.sdt.series60.CAknDialog";
}

CEikLabelVisual.prototype.draw = function(instance, laf, graphics) {
	draw(instance, laf, graphics, false);
}

CEikLabelVisual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return getPreferredSize(instance, laf, wHint, hHint, !isDialog(instance.parent));
}


setupCommonDirectLabelEditing(CEikLabelVisual.prototype, 
	"text", 
	null,
	function(instance, laf) { return laf.getFont(instance.properties.font); } 
	)

function draw(instance, laf, graphics, wrap) {
	var properties = instance.properties
	var flags = 0;
	
	if (wrap)
		flags |= Font.WRAPPING_ENABLED;
	else
		flags |= Font.WRAPPING_NONE;
		
	switch (properties.alignment) {
	case "EEikLabelAlignHCenter":
		flags |= Font.ALIGN_CENTER; break;
	case "EEikLabelAlignHLeft":
		flags |= Font.ALIGN_LEFT; break;
	case "EEikLabelAlignHRight":
		flags |= Font.ALIGN_RIGHT; break;
	}
	if (properties.strikethrough != false)
		flags |= Font.OPTIONS_STRIKETHROUGH;
	if (properties.underline != false)
		flags |= Font.OPTIONS_UNDERLINE;

/*
	switch (properties.emphasis) {
	case "EPartialEmphasis":
		flags |= Font.OPTIONS_BOLD;
		break;
	case "EFullEmphasis":
		flags |= Font.OPTIONS_EXTRABOLD;
	}
*/
	
	var font = laf.getFont(properties.font);
	graphics.setFont(font);
		
	var pattern = null;
	var drawBg = false;
	graphics.setBackground(getBackgroundColor(instance, laf))

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
	var text = chooseScalableText(properties.text, font, rect.width);

	if (drawBg) {
		graphics.fillRectangle(rect);
	}
	
	if (properties.brushStyle != "ENullBrush") {
		pattern = getPattern(graphics, properties.brushStyle);
		graphics.setBackgroundPattern(pattern);
		graphics.fillRectangle(rect);
	}
	
		
	// Series 60 draws left justified if the extent is at least the bounds
	oversize = new Point(rect.width * 2, rect.height);
	size = graphics.formattedStringExtent(text, oversize, flags | Font.WRAPPING_NONE);
	if (size.x >= rect.width) {
		flags &= ~Font.ALIGN_MASK;
		flags |= Font.ALIGN_LEFT;
	}
	
	text = text.replace(/\u2028|\u2029/,"\u7fff");
	graphics.drawFormattedString(text,
			rect,
			flags,
			properties.pixelGapBetweenLines);
	
	if (pattern != null)
		pattern.dispose();
}

// N.B.: we must define this function outside the prototype
// in order for strings to persist as such.  Otherwise they
// are converted to Object and switch() no longer works!

function getPattern(graphics, patt) {

	if (this.image3 == null) {
		this.image3 = Images.newImage(graphics.getDevice(), 3, 3)
	}
	if (this.image4 == null) {
		this.image4 = Images.newImage(graphics.getDevice(), 4, 4)
	}

	//println("brush type: " +typeof(patt));
	
	var img;
	if (patt != "EDiamondCrossHatchBrush" 
	&& patt != "EVerticalHatchBrush" 
	&& patt != "EHorizontalHatchBrush")
		img = this.image3
	else
		img = this.image4
		
	var gc = new GC(graphics.getDevice(), img)

	//gc.setBackground(Colors.getColor(255, 255, 255))
	gc.setBackground(graphics.getBackground())
	gc.fillRectangle(0, 0, 4, 4);
	gc.setBackground(Colors.getColor(255, 255,255))
	gc.setForeground(Colors.getColor(0, 0, 0))
	
	switch (patt) {
	case "EVerticalHatchBrush":
		gc.drawLine(1, 0, 1, 3); 
		gc.drawLine(3, 0, 3, 3); 
		break;
	case "EForwardDiagonalHatchBrush":
		gc.drawLine(0, 2, 2, 0); 
		break;
	case "EHorizontalHatchBrush":
		gc.drawLine(0, 1, 3, 1);
		gc.drawLine(0, 3, 3, 3);
		break;
	case "ERearwardDiagonalHatchBrush":
		gc.drawLine(0, 0, 2, 2);
		break;
	case "ESquareCrossHatchBrush":
		gc.drawLine(1, 0, 1, 3);
		gc.drawLine(0, 1, 3, 1);
		break;
	case "EDiamondCrossHatchBrush":
		gc.drawLine(0, 0, 3, 3); 
		gc.drawLine(2, 0, 0, 2);
		break;
	case "ESolidBrush":
	default:
		// note: if all cases come here, it's because switch(string)
		// only makes sense with actual string types.  
		// If this function is part of a prototype that's wrapped
		// into JS by UI Designer, then Rhino tends to coerce the arguments
		// to Object.
		
		//println("default brush type: " +patt);
		gc.setBackground(Colors.getColor(0, 0, 0))
		gc.fillRectangle(0, 0, 5, 5); 
		break;
	}
	
	gc.dispose()
	
	try {
		return new Pattern(graphics.getDevice(), img)
	} catch (e) {
		// not GDI+
		return null;
	}
}

function getPreferredSize(instance, laf, wHint, hHint, wrap) {
	var properties = instance.properties;
	var flags = 0;
	
	if (wrap)
		flags |= Font.WRAPPING_ENABLED;
	else
		flags |= Font.WRAPPING_NONE;
		
	font = laf.getFont(properties.font);
	switch (properties.alignment) {
	case "EEikLabelAlignHCenter":
		flags |= Font.ALIGN_CENTER; break;
	case "EEikLabelAlignHLeft":
		flags |= Font.ALIGN_LEFT; break;
	case "EEikLabelAlignHRight":
		flags |= Font.ALIGN_RIGHT; break;
	}
	
	// get bounding rect
	var layoutBounds = instance.getLayoutBounds();
	width = layoutBounds.width;
	height = layoutBounds.height;
	// if either of these are empty, use the parent's bounds as starting point
	if ((width == 0) || (height == 0)) {
		layoutBounds = instance.parent.getLayoutBounds();
		width = layoutBounds.width;
		height = layoutBounds.height;
	}
	
	if (wHint >= 0)
		width = wHint;
	if (hHint >= 0)
		height = hHint;
	var bounds = new Point(width, height);
	
	var text = chooseScalableText(properties.text.toString(), font, width);
	if (text.length == 0)
		text = " ";
	return font.formattedStringExtent(text, bounds, flags, properties.pixelGapBetweenLines);
}

