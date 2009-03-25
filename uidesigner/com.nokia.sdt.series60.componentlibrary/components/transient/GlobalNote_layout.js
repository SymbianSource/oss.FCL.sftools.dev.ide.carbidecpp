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


include("popupDialogLibrary.js")

function GlobalNoteLayout() {
}

GlobalNoteLayout.prototype.layout = function(instance, laf) {
	var properties = instance.properties;
	var flags = Font.ALIGN_LEFT;
	if (properties.textProcessing)
		flags |= Font.WRAPPING_ENABLED;
			
	var iconRect = getIconRect(0, laf);
	var rect = calculateBounds(properties, laf, flags, iconRect.x, 0);

	properties.location.x = rect.x;
	properties.location.y = rect.y;
	properties.size.width = rect.width;
	properties.size.height = rect.height;
}

GlobalNoteLayout.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null;
}

GlobalNoteLayout.prototype.propertyChanged = function(instance, property) {
	if (property == "text" || property == "textProcessing") {
		instance.forceLayout();
	}
}
