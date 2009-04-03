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

function CEikRangeEditorVisual() {
}

CEikRangeEditorVisual.prototype.getFlags = commonEditorGetFlags;

CEikRangeEditorVisual.prototype.getFont = function(instance, laf) {
	var fontName = instance.properties.font;
	return laf.getFont(fontName);
}

CEikRangeEditorVisual.prototype.getDisplayText = function(instance) {
	var ps = instance.properties;
	return ps.value.minimum + ps.value.separatorText + ps.value.maximum;
}

CEikRangeEditorVisual.prototype.getMaxLength = function(instance) {
	return 0;
}

setupEditorRendering(CEikRangeEditorVisual.prototype);

setupCommonEmbeddedDirectLabelEditing(CEikRangeEditorVisual.prototype,
	"value",
	null, // areafunction
	CEikRangeEditorVisual.prototype.getFont
);

	// note that laf will be null if a display model was not created
CEikRangeEditorVisual.prototype.validate = function(instance, laf) {
	var properties = instance.properties;
	var messages = null;
	if (properties.value.minimum < properties.min ||
		properties.value.maximum > properties.max) {
		messages = new java.util.ArrayList();
		messages.add(createSimpleModelError(instance, 
			"value", 
			lookupString("valueExceedsRange"), 
				[instance.name]));
	}
	return messages;
}

	// note that laf will be null if a display model was not created
CEikRangeEditorVisual.prototype.queryPropertyChange = function(instance, propertyPath,
					newValue, laf) {
	var properties = instance.properties;
	var message = null;
	if (propertyPath == "max") {
		if (properties.value.maximum > newValue) {
			message = formatString(lookupString("minMaxConstraint"),
				properties.value.minimum, "...", properties.value.maximum);
		}
	} else if (propertyPath == "min") {
		if (properties.value.minimum < newValue) {
			message = formatString(lookupString("minMaxConstraint"),
				properties.value.minimum, "...", properties.value.maximum);
		}
	} else if (propertyPath == "value") {
		if (properties.min > newValue.minimum
			|| properties.max < newValue.maximum) {
			message = formatString(lookupString("rangeConstraint"),
				properties.min, properties.max);
		}
	}
	return message;
}
