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


function StatusPaneVisual() {
}

var NAV_HEIGHT = 14;

StatusPaneVisual.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;
	var thisWidth = properties.size.width;
	var thisHeight = properties.size.height;
	
	var white = Colors.getColor(255, 255, 255);
	var navGradientStart = Colors.getColor(0, 0, 150);
	var navGradientEnd = Colors.getColor(0, 0, 255);

	graphics.setBackground(white);
	graphics.fillRectangle(0, 0, thisWidth, thisHeight);
	
	var navTop = thisHeight - NAV_HEIGHT;
	
	graphics.setForeground(navGradientStart);
	graphics.setBackground(navGradientEnd);
	graphics.fillGradientRectangle(0, navTop, thisWidth, NAV_HEIGHT, false);
}

StatusPaneVisual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null; // needs implementation	
}

