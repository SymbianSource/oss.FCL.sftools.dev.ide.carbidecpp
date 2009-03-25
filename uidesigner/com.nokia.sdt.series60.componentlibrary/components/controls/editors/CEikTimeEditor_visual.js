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

function CEikTimeEditorVisual() {
}

CEikTimeEditorVisual.prototype.getFlags = commonEditorGetFlags;

CEikTimeEditorVisual.prototype.getFont = function(instance, laf) {
	return getEditorFontWithFontProperty(instance, laf);
}

CEikTimeEditorVisual.prototype.getDisplayText = function(instance) {
	var properties = instance.properties;
	var t = properties.time;
	var showSeconds = !properties.flags.EEikTimeWithoutSecondsField;
	var showHours = !properties.flags.EEikTimeWithoutHoursField;
	// these settings ignored in settings item view
    if (isSettingItemList(instance.parent)) {
    	showSeconds = false;
    	showHours = true;
    }
	return formatTime(t.hour, t.minute, t.second, 
		showHours, 
		true,
		showSeconds,
		true);
}

CEikTimeEditorVisual.prototype.getMaxLength = function(instance) {
	return 0;
}

setupEditorRendering(CEikTimeEditorVisual.prototype);

setupCommonEmbeddedDirectLabelEditing(CEikTimeEditorVisual.prototype,
	"time",
	null, // areafunction
	CEikTimeEditorVisual.prototype.getFont
);


function timeToInteger(t) {
	// the -0 changes text to integer
	var v = ((t.hour - 0) * 3600) + ((t.minute - 0) * 60) + (t.second - 0);
	return v;
}	

setupCommonRangeCheckingValidation(CEikTimeEditorVisual.prototype, 
		lookupString("time"), lookupString("times"),
		"minTime", "maxTime", "time", 
		timeToInteger);

