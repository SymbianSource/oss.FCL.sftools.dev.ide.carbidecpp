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



include("../../formLibrary.js")
include("editorLibrary.js")


function CEikFixedPointEditorVisual() {
}

CEikFixedPointEditorVisual.prototype.getFlags = commonEditorGetFlags;

CEikFixedPointEditorVisual.prototype.getFont = function(instance, laf) {
	return laf.getFont("NormalFont");
}

CEikFixedPointEditorVisual.prototype.getDisplayText = function(instance) {
	var value = instance.properties.value + 0;
	var places = instance.properties.decimalPlaces + 0;
	return (value / Math.pow(10, places)).toFixed(places);
}

CEikFixedPointEditorVisual.prototype.getMaxLength = function(instance) {
	return 32;
}


setupEditorRendering(CEikFixedPointEditorVisual.prototype);

setupCommonEmbeddedDirectLabelEditing(CEikFixedPointEditorVisual.prototype,
	"value",
	null, // areafunction
	CEikFixedPointEditorVisual.prototype.getFont
);

function unsetValueChecker() { }

function defaultValueChecker() { }

setupCommonRangeCheckingValidation(defaultValueChecker, 
			lookupString("value"), lookupString("values"),	// not default 
			"minimum", "maximum", "default", null);

function valueChecker() { }

setupCommonRangeCheckingValidation(valueChecker, 
		lookupString("value"), lookupString("values"),
		"minimum", "maximum", "value", null);
	
CEikFixedPointEditorVisual.prototype.queryPropertyChange = function(instance, propertyPath,
					newValue, laf) {
	var message = defaultValueChecker.queryPropertyChange(instance, propertyPath, newValue, laf);
	if (message != null)
		return message;
	message = valueChecker.queryPropertyChange(instance, propertyPath, newValue, laf);
	if (message != null)
		return message;
	return null;
}

CEikFixedPointEditorVisual.prototype.validate = function(instance, laf) {
	var message = defaultValueChecker.validate(instance, laf);
	if (message != null)
		return message;
	message = valueChecker.validate(instance, laf);
	if (message != null)
		return message;
	return null;
}

