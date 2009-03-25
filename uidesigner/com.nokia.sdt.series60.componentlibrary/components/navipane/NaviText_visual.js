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


function NaviTextVisual() {
}

NaviTextVisual.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;
	
	var flags = Font.ANTIALIAS_OFF;
	
	var align = laf.getInteger("navi.text.align.weight", -1);
	switch (align) {
		case 1:
			flags |= Font.ALIGN_RIGHT;
			break;
		case 0:
			flags |= Font.ALIGN_CENTER;
			break;
		default:
			flags |= Font.ALIGN_LEFT;
			break;
	}
	
	var rect = new Rectangle(0, 0,
		properties.size.width, properties.size.height)
		
	// oops, can't do this since we don't own the leftmost part...
	graphics.setBackground(laf.getColor("status.bar.gradient.start"))
	//graphics.fillRectangle(rect);
	
	var color = laf.getColor("navi.pane.text");
		
	var font = laf.getFont("navi.text.font");
	graphics.setFont(font);
	graphics.setForeground(color);
	
	var text = chooseScalableText(properties.text, font, rect.width);
	
	var extent = font.stringExtent(text);
	var newrect = new Rectangle(0, (rect.height - extent.y) / 2, rect.width, rect.height)
	graphics.drawFormattedString(properties.text,
			newrect, flags, 0);
}

NaviTextVisual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null; // needs implementation	
}

// IDirectLabelEdit
NaviTextVisual.prototype.getPropertyPaths = function(instance) {
	return new Array("text");
}

NaviTextVisual.prototype.getLabelBounds = function(instance, propertyPath, laf) {
	return new Rectangle(0, 0,
		instance.properties.size.width, instance.properties.size.height);
}

NaviTextVisual.prototype.getLabelFont = function(instance, propertyPath, laf) {
	return laf.getFont("navi.text.font");
}

