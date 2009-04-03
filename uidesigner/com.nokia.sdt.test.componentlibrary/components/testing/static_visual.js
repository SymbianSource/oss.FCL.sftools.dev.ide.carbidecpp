/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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

/*
 * Render static
 *
 * globals available are:
 *
 *		instance (WrappedInstance)
 *		properties (WrappedProperties) 
 *			properties["name"] or properties.name retrieves values
 *		parent (WrappedInstance)
 *		children (WrappedInstance[])
 *		
 * rendering globals:
 *		graphics (wrapped SWT GC)
 *		Colors (object from which getColor(r,g,b) is available)
 *		Fonts (object from which getFont("path") is available)
 *		Images (object from which newImage(device,w,h) is available)
 */

function Visual() {
}

Visual.prototype.draw = function() {

	var width = properties.size.width;
	var height = properties.size.height

	var base = "data/s60"

	graphics.setBackground(Colors.getColor(160, 64, 64))
	graphics.fillRectangle(0, 0, width, height);

	graphics.setForeground(Colors.getColor(255, 255, 255))
	graphics.setFont(Fonts.getGlobalFont(base + "/fonts/Alb12.9.ttf"))
	graphics.drawFormattedString("(static)", new Rectangle(0, height/2, width, height/2),
		Font.ALIGN_CENTER + Font.DRAW_TRANSPARENT);
}

Visual.prototype.getPreferredSize = function(wHint, hHint) {
	return null; // needs implementation	
}

