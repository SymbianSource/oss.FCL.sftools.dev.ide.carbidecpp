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



include("../embeddedControlImplLibrary.js")
include("editors/editorLibrary.js")

function CAknPopupFieldText() {
}

CAknPopupFieldText.prototype.getFlags = function(instance, laf) {
	return Font.ALIGN_LEFT; 
}

CAknPopupFieldText.prototype.getFont = function(instance, laf) {
	var fontName = instance.properties.font;
	return laf.getFont(fontName);
}

CAknPopupFieldText.prototype.getDisplayText = function(instance) {
	var text = "";
	var items = instance.properties.items;
	if ((items != null) && (items.length > 0)) {
		var active = instance.properties.active;
		if (items.length > active) {
			text = items[active];
			if (text == null)
				text = "empty";
		}
		else {
			text = instance.properties.invalidText;
			if (text == "")
				text = "(invalid)";
		}
	}
	else {
		var emptyText = instance.properties.emptyText;
		if (emptyText != null)
			text = emptyText;
	}

	return text;
}

CAknPopupFieldText.prototype.getDisplayColor = function(instance, laf) {
	var text = "";
	var items = instance.properties.items;
	if ((items != null) && (items.length > 0)) {
		var active = instance.properties.active;
		if (active >= items.length) {
			return laf.getColor("EEikColorControlDimmedText");
		}
	}
	return null;
}

CAknPopupFieldText.prototype.getMaxLength = function(instance) {
	var items = instance.properties.items;
	if ((items != null) && (items.length > 0)) {
		var active = instance.properties.active;
		if (items.length > active) {
			return instance.properties.maxLength;
		}
	}
	return 0;
}


setupEditorRendering(CAknPopupFieldText.prototype);

// no direct label editing, just form prompt editing
setupCommonEmbeddedDirectLabelEditing(CAknPopupFieldText.prototype,
	null,
	null, // areafunction
	null
);

setupCommonEmbeddedDirectImageEditing(CAknPopupFieldText.prototype);
setupEmbeddedImagePropertyInfo(CAknPopupFieldText.prototype);

//////////////////////

		// note that laf will be null if a display model was not created
CAknPopupFieldText.prototype.validate = function(instance, laf) {
	var properties = instance.properties;

	var messages = null;
	if (properties.items.length == 0) {
		messages = new java.util.ArrayList();	
		messages.add(createSimpleModelError(instance, 
			"items", 
			lookupString("ErrorEmptyList"), 
				[instance.name]));
	} 
	return messages;
}

	// note that laf will be null if a display model was not created
CAknPopupFieldText.prototype.queryPropertyChange = function(instance, propertyPath,
				newVal, laf) {
				
	var properties = instance.properties;
	var message = null;

	// Don't toss away any changes at this point; otherwise,
	// the user will endure a dialog and have it all thrown away.
	// Better to let them go back and re-edit.

	return message;		
}

CAknPopupFieldText.prototype.initialize = function(instance, isConfigured) {
	if (isConfigured) return;
	// make at least one text entry
	instance.properties.items[0] = lookupString("InitialItemText");
}
