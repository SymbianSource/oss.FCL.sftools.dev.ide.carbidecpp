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
include("testImplLibrary.js")
include("testRenderLibrary.js")

function StatusPaneContextVisual() {
}

var NAV_HEIGHT = 14;

StatusPaneContextVisual.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;
	var thisWidth = properties.size.width;
	var thisHeight = properties.size.height;
	
	if (laf.getBoolean("show.context.icon", true)) {
		var rect = this.getImageBounds(instance, "image", laf);
		drawImageFromProperty(instance, graphics, properties.image, rect);
	}
	
}

StatusPaneContextVisual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null; // needs implementation	
}

setupCommonDirectImageEditing(StatusPaneContextVisual.prototype, "image")
