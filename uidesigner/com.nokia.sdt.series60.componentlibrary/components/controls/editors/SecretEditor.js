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



include("../../embeddedControlImplLibrary.js")
include("editorLibrary.js")


function SecretEditorVisual() {
}

SecretEditorVisual.prototype.getFlags = function(instance, laf) {
	var properties = instance.properties;
	var flags;
	
	switch (properties.alignment) {
		case "ECenter":
			flags = Font.ALIGN_CENTER; break;
		case "ELeft":
			flags = Font.ALIGN_LEFT; break;
		case "ERight":
			flags = Font.ALIGN_RIGHT; break;
		default:
			flags = Font.ALIGN_LEFT; break;
	}

	return flags;
}

SecretEditorVisual.prototype.getFont = function(instance, laf) {
	return getEditorFontWithFontProperty(instance, laf);
}

SecretEditorVisual.prototype.getDisplayText = function(instance) {
	var text = instance.properties.text;
	var displayText = "";

	// The default setting is obscured a setting list.
	// Only the indication of a set vs. unset value is indicated
	if (isSettingItemList(instance.parent)) {
		if (text != "") {
			var isNum = (instance.componentId.indexOf("Num") >= 0);
			displayText = isNum ? "****" : "******";
		}
	} else {
		for (var i = 0; i < text.length; i++) {
			displayText = displayText + "*";
		}
	}
	return displayText;
}

SecretEditorVisual.prototype.getMaxLength = function(instance) {
	return instance.properties.maxLength ;
}



setupEditorRendering(SecretEditorVisual.prototype);

setupCommonEmbeddedDirectLabelEditing(SecretEditorVisual.prototype,
	"text",
	null, // areafunction
	SecretEditorVisual.prototype.getFont
);

setupCommonEmbeddedDirectImageEditing(SecretEditorVisual.prototype);
setupEmbeddedImagePropertyInfo(SecretEditorVisual.prototype);

	// note that laf will be null if a display model was not created
SecretEditorVisual.prototype.validate = function(instance, laf) {
	var properties = instance.properties;
	var messages = null;
	if (properties.text.length > properties.maxLength ) {
		messages = new java.util.ArrayList();
		messages.add(createSimpleModelError(instance, 
			"text", 
			lookupString("stringExceedsBuffer"), 
				[instance.name]));
	}
	return messages;
}

	// note that laf will be null if a display model was not created
SecretEditorVisual.prototype.queryPropertyChange = function(instance, propertyPath,
					newValue, laf) {
	var properties = instance.properties;
	var message = null;
	if (propertyPath == "maxLength") {
		if (properties.text.length > newValue) {
			message = lookupString("maxLengthConstraint");
		}
	} else if (propertyPath == "text") {
		if (properties.maxLength < newValue.length()) {
			message = lookupString("textConstraint");
		}
	}
	return message;
}
