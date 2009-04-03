/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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
* START_USECASES: CU19 END_USECASES
*
*
*/


include("../../../implLibrary.js") //for setupCommonRangeCheckingValidation 
include("../../../editorLibrary.js") //for setupEditorRendering

function CQikTimeEditor() {
}

////////////////////////////////////////////////////////////////////////////////
//IVisualAppearance

setupEditorRendering(CQikTimeEditor.prototype);

CQikTimeEditor.prototype.getFlags = commonEditorGetFlags;

CQikTimeEditor.prototype.getDisplayText = function(instance) {
	var properties = instance.properties;
	var t = properties.time;
	if (t != null) {
		if (properties.flags.EForce24HourFormat == true)  {
			return formatTime(t.hour, t.minute, t.second, 
				true, 
				true,
				false,
				false);	
		}	
		return formatTime(t.hour, t.minute, t.second, 
				true, 
				true,
				false,
				true);	
	} 
	return null;
}

CQikTimeEditor.prototype.getMaxLength = function(instance) {
	return 0;
}

////////////////////////////////////////////////////////////////////////////////
// IComponentValidator

function timeToInteger(t) {
	// the -0 changes text to integer
	if (t != null) {
		var v = ((t.hour - 0) * 3600) + ((t.minute - 0) * 60) + (t.second - 0);
		return v;
	} 
	return null;
}	

setupCommonRangeCheckingValidation(CQikTimeEditor.prototype, 
		lookupString("time"), lookupString("times"),
		"minTime", "maxTime", "time", 
		timeToInteger);

////////////////////////////////////////////////////////////////////////////////
// IDirectLabelEdit

setupCommonDirectLabelEditing(CQikTimeEditor.prototype,
	"time",
	areaWithParentWidth, // areafunction
	CQikTimeEditor.prototype.getFont
);

CQikTimeEditor.prototype.getFont = function(instance, laf) {
	var fontName = instance.properties.font;
	return laf.getFont(fontName);
}
