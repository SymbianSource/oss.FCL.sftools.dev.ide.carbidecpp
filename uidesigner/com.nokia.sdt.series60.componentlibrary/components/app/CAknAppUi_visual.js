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


/*		
 *		graphics (wrapped SWT GC)
 *		Colors (object from which getColor(r,g,b) is available)
 *		Images (object from which newImage(device,w,h) is available)
 */
function CAknAppUiVisual() {
}

CAknAppUiVisual.prototype.draw = function(instance, laf, graphics) {

	var width = instance.properties.size.width;
	var height = instance.properties.size.height;

	var screenBackground = laf.getColor("screen.background");	
	graphics.setBackground(screenBackground);
	graphics.fillRectangle(0, 0, width, height);
	
	drawStatusBar(laf, graphics, "status.bar1.bounds");
	drawStatusBar(laf, graphics, "status.bar2.bounds");
}

function drawStatusBar(laf, graphics, boundsName) {
	var bounds = laf.getRectangle(boundsName);
	if (bounds != null) {
		var useGradient = laf.getBoolean("status.bar.gradient", false);
		if (useGradient) {
			var navGradientStart = laf.getColor("status.bar.gradient.start");
			var navGradientEnd = laf.getColor("status.bar.gradient.end");
			graphics.setForeground(navGradientStart);
			graphics.setBackground(navGradientEnd);
			var cbaW = 0;
			if (laf.getBoolean("is.landscape", true))
				cbaW = laf.getRectangle("control.pane.bounds").width;
			graphics.fillGradientRectangle(bounds.x, bounds.y, bounds.width - cbaW, bounds.height, false);
		}
		else {
		 	var color = laf.getColor("status.bar.color");
		 	graphics.setBackground(color);
		 	graphics.fillRectangle(bounds);
		}
	}
}

CAknAppUiVisual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null; // needs implementation	
}

