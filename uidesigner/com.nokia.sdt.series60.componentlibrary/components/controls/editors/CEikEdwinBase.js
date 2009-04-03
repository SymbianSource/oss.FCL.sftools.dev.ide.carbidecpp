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

include("../../implLibrary.js")

function CEikEdwinBase() {
}

function validateCaseMode(messages, instance, properties, mode) {
	if (!properties.allowedCaseModes[mode] && properties.defaultCase == mode)
		messages.add(
			createSimpleModelError(instance, 
					mode, 
					lookupString("InvalidCaseModeSetting"), 
					[ instance.name, lookupString(mode) ]));
}

function validateInputMode(messages, instance, properties, mode) {
	if (!properties.allowedInputModes[mode] && properties.defaultInputMode == mode)
		messages.add(
			createSimpleModelError(instance, 
				mode,
				lookupString("InvalidInputModeSetting"),
				[ instance.name, lookupString(mode) ]));
}

function getPropertyNameFromPropertyPath(propertyPath) {
	return propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
}

function isTrue(value) {
	return (value != null) && (value.toString() != "false");
}

function isUndefinedInSDK(propertyName, value) {
	var version = getComponentVersions();
	if ((version.major == 2) && (version.minor < 6)) {
		if ((propertyName == "EAknEditorKatakanaInputMode") ||
			(propertyName == "EAknEditorFullWidthTextInputMode") ||
			(propertyName == "EAknEditorFullWidthNumericInputMode") ||
			(propertyName == "EAknEditorFullWidthKatakanaInputMode") ||
			(propertyName == "EAknEditorHiraganaKanjiInputMode") ||
			(propertyName == "EAknEditorHiraganaInputMode") ||
			(propertyName == "EAknEditorHalfWidthTextInputMode"))
			return isTrue(value);
		if ((propertyName == "numericKeymap") && (value == "EAknEditorAlphanumericNumberModeKeymap"))
			return true;
		if (propertyName == "EAknEditorFlagAllowEntersWithScrollDown")
			return isTrue(value);
	}
	if ((version.major == 2) && (version.minor < 8)) {
		if (propertyName == "EAknEditorFlagEnablePictographInput")
			return isTrue(value);
	}
	if (version.major == 2) {
		if (propertyName == "EAknEditorFlagFindPane")
			return isTrue(value);
	}

	return false;	
}

var jModes = [ "EAknEditorKatakanaInputMode",
			"EAknEditorFullWidthTextInputMode", "EAknEditorFullWidthNumericInputMode",
			"EAknEditorFullWidthKatakanaInputMode", "EAknEditorHiraganaKanjiInputMode",
			"EAknEditorHiraganaInputMode", "EAknEditorHalfWidthTextInputMode" ];
	

	// note that laf will be null if a display model was not created
CEikEdwinBase.prototype.validate = function(instance, laf) {
	var properties = instance.properties;
	var messages = new java.util.ArrayList();
	
	// this is shared with several components that do not have these
	// properties at all, so do a dumb query
	if ("defaultCase" in properties) {
		// test invariants
		var caseModes = [ "EAknEditorUpperCase", "EAknEditorLowerCase", "EAknEditorTextCase" ];
		for (var i in caseModes)
			validateCaseMode(messages, instance, properties, caseModes[i]);
			
		var inputModes = [ "EAknEditorTextInputMode", "EAknEditorNumericInputMode",
			"EAknEditorSecretAlphaInputMode", "EAknEditorKatakanaInputMode",
			"EAknEditorFullWidthTextInputMode", "EAknEditorFullWidthNumericInputMode",
			"EAknEditorFullWidthKatakanaInputMode", "EAknEditorHiraganaKanjiInputMode",
			"EAknEditorHiraganaInputMode", "EAknEditorHalfWidthTextInputMode" ];
		for (var i in inputModes)
			validateInputMode(messages, instance, properties, inputModes[i]);
	}
	
	if ("allowedInputModes" in properties) {
		for (i in jModes) {
			var jMode = jModes[i];
			var value = properties.allowedInputModes[jMode];
			if (isUndefinedInSDK(jMode, value))
				messages.add(createSimpleModelError(instance, jMode,
						lookupString("UndefinedSDKPropertyValue"),
						[ instance.name, lookupString(jMode), value ]));
		}
	}
	
	if ("numericKeymap" in properties) {
		var value = properties.numericKeymap;
		if (isUndefinedInSDK("numericKeymap", value))
			messages.add(createSimpleModelError(instance, "numericKeymap",
					lookupString("UndefinedSDKPropertyValue"),
					[ instance.name, lookupString("numericKeymap"), value ]));
			
	}

	if ("avkonFlags" in properties) {
		var value = properties.avkonFlags.EAknEditorFlagEnablePictographInput;
		if (isUndefinedInSDK("EAknEditorFlagEnablePictographInput", value))
			messages.add(createSimpleModelError(instance, "EAknEditorFlagEnablePictographInput",
					lookupString("UndefinedSDKPropertyValue"),
					[ instance.name, lookupString("EAknEditorFlagEnablePictographInput"), value ]));
			
		value = properties.avkonFlags.EAknEditorFlagAllowEntersWithScrollDown;
		if (isUndefinedInSDK("EAknEditorFlagAllowEntersWithScrollDown", value))
			messages.add(createSimpleModelError(instance, "EAknEditorFlagAllowEntersWithScrollDown",
					lookupString("UndefinedSDKPropertyValue"),
					[ instance.name, lookupString("EAknEditorFlagAllowEntersWithScrollDown"), value ]));
			
	}
	
	if (isChildOfSettingItemList(instance) && ("maxViewHeightInLines" in properties)) {
		var value = properties.maxViewHeightInLines;
		if (value > 0)
			messages.add(createSimpleModelError(instance, "maxViewHeightInLines",
					lookupString("MaxViewHeightInLinesError"),
					[ instance.name, lookupString("maxViewHeightInLines"), value ]));
			
	}

	return messages;
}

function isChildOfSettingItemList(instance) {
	return instance.parent.isInstanceOf("com.nokia.sdt.series60.CAknSettingItemList");
}
	
	// note that laf will be null if a display model was not created
CEikEdwinBase.prototype.queryPropertyChange = function(instance, propertyPath, newVal, laf) {
	var propertyName = getPropertyNameFromPropertyPath(propertyPath);
	if (isUndefinedInSDK(propertyName, newVal)) {
		return formatString(lookupString("UndefinedSDKPropertyValue"),
				[ instance.name, lookupString(propertyName), newVal ]);
	}
	
	if (isChildOfSettingItemList(instance)) {
		if ((propertyName == "maxViewHeightInLines") && (newVal != "0")) {
			return formatString(lookupString("MaxViewHeightInLinesError"),
				[ instance.name, lookupString("maxViewHeightInLines") ]);
		}
	}
					
	return null;
}


CEikEdwinBase.prototype.initialize = function(instance) {
	var properties = instance.properties;
	if ("allowedInputModes" in properties) {
		var version = getComponentVersions();
		if ((version.major > 2) || ((version.major == 2) && (version.minor >= 6))) {
			for (i in jModes) {
				var jMode = jModes[i];
				if (jMode != "EAknEditorHiraganaInputMode")
					properties.allowedInputModes[jMode] = true;
			}
		}
	}
}
