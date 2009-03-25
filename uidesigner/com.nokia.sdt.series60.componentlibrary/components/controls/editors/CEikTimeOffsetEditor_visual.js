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

function CEikTimeOffsetEditorVisual() {
}

CEikTimeOffsetEditorVisual.prototype.getFlags = commonEditorGetFlags;

CEikTimeOffsetEditorVisual.prototype.getFont = function(instance, laf) {
	return getEditorFontWithFontProperty(instance, laf);
}

CEikTimeOffsetEditorVisual.prototype.getDisplayText = function(instance) {
	var seconds = instance.properties.timeOffsetSeconds;
	
	var sign = (seconds < 0) ? "- " : "+ ";
	seconds = Math.abs(seconds);
	
	var s = Math.floor(seconds % 60);
	var minutes = seconds / 60;
	var m = Math.floor(minutes % 60);
	var hours = minutes / 60;
	var h = Math.floor(hours % 24);	 // upper limit of editor
	var str;
	
	if (isSettingItemList(instance.parent))
		str = formatTime(h, m, s, true, true, false, false); 
	else
		str = formatTime(h, m, s, 
			!instance.properties.flags.EEikTimeWithoutHoursField, 
			true,
			!instance.properties.flags.EEikTimeWithoutSecondsField,
			false);
		
	return sign + str;
}

CEikTimeOffsetEditorVisual.prototype.getMaxLength = function(instance) {
	return 0;
}

setupEditorRendering(CEikTimeOffsetEditorVisual.prototype);

setupCommonEmbeddedDirectLabelEditing(CEikTimeOffsetEditorVisual.prototype,
	"timeOffsetSeconds",
	null, // areafunction
	CEikTimeOffsetEditorVisual.prototype.getFont
);


setupCommonRangeCheckingValidation(CEikTimeOffsetEditorVisual.prototype, 
		lookupString("time_offset"), lookupString("time_offsets"),
		"minTimeOffsetSeconds", "maxTimeOffsetSeconds", "timeOffsetSeconds", 
		null);

