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

function CAknIpFieldEditorVisual() {
}

CAknIpFieldEditorVisual.prototype.getFlags = commonEditorGetFlags;

CAknIpFieldEditorVisual.prototype.getFont = function(instance, laf) {
	return getEditorFontWithFontProperty(instance, laf);
}

var separator = ".";

function formatIPField(ipField) {
	return ipField.firstField + 
		separator + ipField.secondField + 
		separator + ipField.thirdField + 
		separator + ipField.fourthField;
}

CAknIpFieldEditorVisual.prototype.getDisplayText = function(instance) {
	var address = instance.properties.address;
	return formatIPField(address);
}

CAknIpFieldEditorVisual.prototype.getMaxLength = function(instance) {
	return 15;
}

setupEditorRendering(CAknIpFieldEditorVisual.prototype);

setupCommonEmbeddedDirectLabelEditing(CAknIpFieldEditorVisual.prototype,
	"address",
	null, // areafunction
	CAknIpFieldEditorVisual.prototype.getFont
);

CAknIpFieldEditorVisual.prototype.validate = function(instance, laf) {
	return null;
}

function anyFieldsGreaterThan(i1, i2) {
	return (i1.firstField > i2.firstField) ||
		(i1.secondField > i2.secondField) ||
		(i1.thirdField > i2.thirdField) ||
		(i1.fourthField > i2.fourthField);
}

CAknIpFieldEditorVisual.prototype.queryPropertyChange = function(instance, propertyPath, newValue, laf) {
	var maxFields = instance.properties.maxFieldValues;
	var minFields = instance.properties.minFieldValues;
	var address = instance.properties.address;
	var message = null;
	if (propertyPath == "maxFieldValues") {
		if (anyFieldsGreaterThan(address, newValue)
			|| anyFieldsGreaterThan(minFields, newValue)) {
			message = formatString(lookupString("minMaxConstraint"),
				formatIPField(minFields), formatIPField(address), formatIPField(newValue));
		}
	} else if (propertyPath == "minFieldValues") {
		if (anyFieldsGreaterThan(newValue, maxFields)
			|| anyFieldsGreaterThan(newValue, address)) {
			message = formatString(lookupString("minMaxConstraint"),
				formatIPField(newValue), formatIPField(address), formatIPField(maxFields));
		}
	} else if (propertyPath == "address") {
		if (anyFieldsGreaterThan(newValue, maxFields)
			|| anyFieldsGreaterThan(minFields, newValue)) {
			message = formatString(lookupString("minMaxConstraint"),
				formatIPField(minFields), formatIPField(newValue), formatIPField(maxFields));
		}
	}
	return message;
}
