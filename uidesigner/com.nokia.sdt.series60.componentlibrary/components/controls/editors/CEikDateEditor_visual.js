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

function CEikDateEditorVisual() {
}

CEikDateEditorVisual.prototype.getFlags = commonEditorGetFlags;

CEikDateEditorVisual.prototype.getFont = function(instance, laf) {
	return getEditorFontWithFontProperty(instance, laf);
}

CEikDateEditorVisual.prototype.getDisplayText = function(instance) {
	var properties = instance.properties;
	var t = properties.date;
	return formatDate(t);
}

CEikDateEditorVisual.prototype.getMaxLength = function(instance) {
	return 0;
}

setupEditorRendering(CEikDateEditorVisual.prototype);

setupCommonEmbeddedDirectLabelEditing(CEikDateEditorVisual.prototype,
	"date",
	null, // areafunction
	CEikDateEditorVisual.prototype.getFont
);

function dateToInteger(d) {
	// ROUGH estimate, just for range checking
	// 372 = 31*12
	
	// the -0 changes text to integer
	var v = ((d.year - 0) * 372) + ((d.month - 0) * 31) + (d.day - 0);
	//println("year="+d.year+", month="+d.month+", day="+d.day+" v = "+v);
	return v;
}	

setupCommonRangeCheckingValidation(CEikDateEditorVisual.prototype, 
		lookupString("date"), lookupString("dates"),
		"minDate", "maxDate", "date", 
		dateToInteger);

