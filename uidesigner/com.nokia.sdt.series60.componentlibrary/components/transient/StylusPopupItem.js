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
*
*/


include("../renderLibrary.js")

function StylusPopupItem() {
	this.lafInfo = null;
}

/////////////////////////
// IVisualAppearance
/////////////////////////
StylusPopupItem.prototype.draw = function(instance, laf, graphics) {
    var props = instance.properties;
   
    if (props.textItem == ""){
    	// default text
		props.textItem = "new item";
	}
    graphics.setBackground(getBackgroundColor(instance, laf));
    drawTextItem(props.textItem, new Point(0, 0), new Point(props.size.width , props.size.height), laf, graphics);
}

StylusPopupItem.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null; 	
}

function drawTextItem(text, location, size, laf, graphics) {
	
	var x = location.x;
	var y = location.y;
	var width = size.x;
	var height = size.y;
	
	var font = laf.getFont("menuitem.font");
	graphics.setFont(font);
	
	var margin = laf.getInteger("control.pane.text.margin", 5);
	var extent = font.stringExtent(text)
	
	if (laf.getBoolean("is.portrait", true)) {
		var fontOffset = (height - extent.y) / 2

		var rect = new Rectangle(x + margin, y + fontOffset, 
				width - (2*margin), height );

		graphics.fillRectangle(x, y, width, height);

		text = chooseScalableText(text, font, rect.width);
		graphics.drawFormattedString(text, rect, Font.ALIGN_LEFT | Font.OVERFLOW_ELLIPSIS, 0);
	}
	else {
		var fontHeight = font.getHeight();
		var sbar1Bounds = laf.getRectangle("status.bar1.bounds");
		var sbar2Bounds = laf.getRectangle("status.bar2.bounds");
		
		var fontOffset = (sbar2Bounds.height - extent.y) / 2

		var rect = new Rectangle(x, y + fontOffset, width-margin, height-fontOffset);
		graphics.fillRectangle(x, y + sbar1Bounds.y, width, sbar1Bounds.height);
		
		rect.y = y + height - sbar2Bounds.height + fontOffset -5;
		rect.height = sbar2Bounds.height - fontOffset;

		graphics.fillRectangle(x, y + sbar2Bounds.y, width, sbar2Bounds.height);
		text = chooseScalableText(text, font, rect.width);
		graphics.drawFormattedString(text, rect, Font.ALIGN_LEFT | Font.OVERFLOW_ELLIPSIS, 0);
	}
}

//////////////////////////
// IDirectLableEdit
//////////////////////////
StylusPopupItem.prototype.getLabelFont = function(instance, propertyPath, laf) {
	return laf.getFont("menuitem.font");
}


StylusPopupItem.prototype.getPropertyPaths = function(instance) {
	return new Array("textItem");
}

StylusPopupItem.prototype.getLabelBounds = function(instance, propertyPath, laf) {
	var props = instance.properties;
	return new Rectangle(0, 0, props.size.width, props.size.height);
}
