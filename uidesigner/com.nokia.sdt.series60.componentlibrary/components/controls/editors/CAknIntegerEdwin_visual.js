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

function CAknIntegerEdwinVisual() {
}

CAknIntegerEdwinVisual.prototype.getFlags = commonEditorGetFlags;

CAknIntegerEdwinVisual.prototype.getFont = function(instance, laf) {
	return getStandardEditorFont(instance, laf);
}

CAknIntegerEdwinVisual.prototype.getDisplayText = function(instance) {
	return instance.properties.value + "";
}

CAknIntegerEdwinVisual.prototype.getMaxLength = function(instance) {
	return instance.properties.maxlength;
}


setupEditorRendering(CAknIntegerEdwinVisual.prototype);

setupCommonEmbeddedDirectLabelEditing(CAknIntegerEdwinVisual.prototype,
	"value",
	null, // areafunction
	CAknIntegerEdwinVisual.prototype.getFont
);

function unsetValueChecker() { }

setupCommonRangeCheckingValidation(unsetValueChecker, 
			lookupString("value"), lookupString("values"),	// not "unset value" 
			"minimum", "maximum", "unset_value", null);

function valueChecker() { }

setupCommonRangeCheckingValidation(valueChecker, 
		lookupString("value"), lookupString("values"),
		"minimum", "maximum", "value", null);
	
CAknIntegerEdwinVisual.prototype.queryPropertyChange = function(instance, propertyPath,
					newValue, laf) {
	var message = unsetValueChecker.queryPropertyChange(instance, propertyPath, newValue, laf);
	if (message != null)
		return message;
	message = valueChecker.queryPropertyChange(instance, propertyPath, newValue, laf);
	if (message != null)
		return message;
	return null;
}

CAknIntegerEdwinVisual.prototype.validate = function(instance, laf) {
	var message = unsetValueChecker.validate(instance, laf);
	if (message != null)
		return message;
	message = valueChecker.validate(instance, laf);
	if (message != null)
		return message;
	return null;
}

