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

function CEikNumberEditorVisual() {
}

CEikNumberEditorVisual.prototype.getFlags = commonEditorGetFlags;

CEikNumberEditorVisual.prototype.getFont = function(instance, laf) {
	var fontName = instance.properties.font;
	return laf.getFont(fontName);
}

CEikNumberEditorVisual.prototype.getDisplayText = function(instance) {
	return instance.properties.number + "";
}

CEikNumberEditorVisual.prototype.getMaxLength = function(instance) {
	return 0;
}

setupEditorRendering(CEikNumberEditorVisual.prototype);

setupCommonEmbeddedDirectLabelEditing(CEikNumberEditorVisual.prototype,
	"number",
	null, // areafunction
	CEikNumberEditorVisual.prototype.getFont
);

setupCommonRangeCheckingValidation(CEikNumberEditorVisual.prototype, 
		lookupString("numberRC"), lookupString("numbersRC"),
		"minimum", "maximum", "number", null);
