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


function CEikFloatingPointEditorVisual() {
}

CEikFloatingPointEditorVisual.prototype.getFlags = commonEditorGetFlags;

CEikFloatingPointEditorVisual.prototype.getFont = function(instance, laf) {
	return laf.getFont("NormalFont");
}

CEikFloatingPointEditorVisual.prototype.getDisplayText = function(instance) {
	return instance.properties.value + "";
}

CEikFloatingPointEditorVisual.prototype.getMaxLength = function(instance) {
	return instance.properties.maxlength;
}


setupEditorRendering(CEikFloatingPointEditorVisual.prototype);

setupCommonEmbeddedDirectLabelEditing(CEikFloatingPointEditorVisual.prototype,
	"value",
	null, // areafunction
	CEikFloatingPointEditorVisual.prototype.getFont
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
	
CEikFloatingPointEditorVisual.prototype.queryPropertyChange = function(instance, propertyPath,
					newValue, laf) {
	var message = defaultValueChecker.queryPropertyChange(instance, propertyPath, newValue, laf);
	if (message != null)
		return message;
	message = valueChecker.queryPropertyChange(instance, propertyPath, newValue, laf);
	if (message != null)
		return message;
	return null;
}

CEikFloatingPointEditorVisual.prototype.validate = function(instance, laf) {
	var message = defaultValueChecker.validate(instance, laf);
	if (message != null)
		return message;
	message = valueChecker.validate(instance, laf);
	if (message != null)
		return message;
	return null;
}

