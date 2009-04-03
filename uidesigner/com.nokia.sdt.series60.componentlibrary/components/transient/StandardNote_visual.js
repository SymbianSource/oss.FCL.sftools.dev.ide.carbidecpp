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
include("../messageLibrary.js")
include("popupDialogLibrary.js")

function StandardNoteVisual() {
}

StandardNoteVisual.prototype.draw = function(instance, laf, graphics) {

	var flags = 0;
	flags |= Font.WRAPPING_ENABLED;
	flags |= Font.ALIGN_LEFT;

	var bounds = new Rectangle(0, 0, instance.properties.size.width, instance.properties.size.height);
	drawPopupDialog(instance, laf, graphics, flags, bounds, getIconRect(0, laf), 0);	
}

StandardNoteVisual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null;
}

// IDirectLabelEdit
StandardNoteVisual.prototype.getPropertyPaths = function(instance) {
	return new Array("text");
}

StandardNoteVisual.prototype.getLabelBounds = function(instance, propertyPath, laf) {
	var properties = instance.properties;
	var iconRect = getIconRect(0, laf);

	return new Rectangle(0, 0, iconRect.x, properties.size.height);
}

StandardNoteVisual.prototype.getLabelFont = function(instance, propertyPath, laf) {
	return laf.getFont("message.font");
}

