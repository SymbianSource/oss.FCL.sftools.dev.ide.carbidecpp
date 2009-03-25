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

function CEikTimeAndDateEditorVisual() {
}

CEikTimeAndDateEditorVisual.prototype.getFlags = commonEditorGetFlags;

CEikTimeAndDateEditorVisual.prototype.getFont = function(instance, laf) {
	return laf.getFont(instance.properties.font);
}

CEikTimeAndDateEditorVisual.prototype.getDisplayText = function(instance) {
	var properties = instance.properties;
	var t = properties.timeAndDate;
	return formatTime(t.hour, t.minute, t.second, 
				!properties.flags.EEikTimeWithoutHoursField, 
				true,
				!properties.flags.EEikTimeWithoutSecondsField,
				true) + properties.interveningText +
			formatDateDMY(t.day, t.month, t.year);
}

CEikTimeAndDateEditorVisual.prototype.getMaxLength = function(instance) {
	return 0;
}

setupEditorRendering(CEikTimeAndDateEditorVisual.prototype);

setupCommonEmbeddedDirectLabelEditing(CEikTimeAndDateEditorVisual.prototype,
	"timeAndDate",
	null, // areafunction
	CEikTimeAndDateEditorVisual.prototype.getFont
);


function timeAndDateToInteger(t) {
	// ROUGH estimate, just for range checking
	// 372 = 31*12

	// the -0 changes text to integer
	var v = ((t.hour - 0) * 3600) + ((t.minute - 0) * 60) + (t.second - 0) + 
		(((t.year - 0) * 372) + ((t.month - 0) * 31) + (t.day - 0)) * (24 * 3600);
//	var s = formatTime(t.hour, t.minute, t.second, true, true, true, true) + " " + 
//			formatDateDMY(t.day, t.month, t.year);
//	println("time="+s+" "+"secs="+v);
	return v;
}	

setupCommonRangeCheckingValidation(CEikTimeAndDateEditorVisual.prototype, 
		lookupString("timeAndDate"), lookupString("timeAndDates"),
		"minTimeAndDate", "maxTimeAndDate", "timeAndDate", 
		timeAndDateToInteger);

