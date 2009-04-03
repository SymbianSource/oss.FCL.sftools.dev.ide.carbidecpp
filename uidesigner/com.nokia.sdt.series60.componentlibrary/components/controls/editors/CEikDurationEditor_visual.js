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

function CEikDurationEditorVisual() {
}

CEikDurationEditorVisual.prototype.getFlags = commonEditorGetFlags;

CEikDurationEditorVisual.prototype.getFont = function(instance, laf) {
	var fontName = instance.properties.font;
	return laf.getFont(fontName);
}

CEikDurationEditorVisual.prototype.getDisplayText = function(instance) {
	var seconds = instance.properties.durationSeconds;
	var s = Math.floor(seconds % 60);
	var minutes = seconds / 60;
	var m = Math.floor(minutes % 60);
	var hours = Math.floor(minutes / 60);
	var h = Math.floor(hours % 24);
	return formatTime(h, m, s, 
		!instance.properties.flags.EEikTimeWithoutHoursField, 
		true,
		!instance.properties.flags.EEikTimeWithoutSecondsField,
		false);
}

CEikDurationEditorVisual.prototype.getMaxLength = function(instance) {
	return 0;
}

setupEditorRendering(CEikDurationEditorVisual.prototype);

setupCommonEmbeddedDirectLabelEditing(CEikDurationEditorVisual.prototype,
	"durationSeconds",
	null, // areafunction
	CEikDurationEditorVisual.prototype.getFont
);

setupCommonRangeCheckingValidation(CEikDurationEditorVisual.prototype, 
		lookupString("duration"), lookupString("durations"),
		"minDurationSeconds", "maxDurationSeconds", "durationSeconds", null);

