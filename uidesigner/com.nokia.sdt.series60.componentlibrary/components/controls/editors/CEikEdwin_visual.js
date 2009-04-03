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
include("../../implLibrary.js")
include("editorLibrary.js")

function CEikEdwinVisual() {
}


CEikEdwinVisual.prototype.getFlags = function (instance, laf) {
	var properties = instance.properties;
	var flags = Font.WRAPPING_ENABLED;
	
	switch (properties.alignment) {
	case "EAknEditorAlignCenter":
		flags |= Font.ALIGN_CENTER; break;
	case "EAknEditorAlignLeft":
		flags |= Font.ALIGN_LEFT; break;
	case "EAknEditorAlignRight":
		flags |= Font.ALIGN_RIGHT; break;
	}

	return flags;
}

CEikEdwinVisual.prototype.getFont = function(instance, laf) {
	return getStandardEditorFont(instance, laf);
}

CEikEdwinVisual.prototype.getDisplayText = function(instance) {
	return instance.properties.text;
}

CEikEdwinVisual.prototype.getDisplayColor = function(instance, laf) {
	if (isControl(instance.parent))
		return laf.getColor("CEikEdwin.ColorControlText");
	else
		return null;
}

CEikEdwinVisual.prototype.getMaxLength = function(instance) {
	return instance.properties.maxLength;
}

setupEditorRendering(CEikEdwinVisual.prototype);

setupCommonEmbeddedDirectLabelEditing(CEikEdwinVisual.prototype,
	"text",
	null, // areafunction
	CEikEdwinVisual.prototype.getFont
);

CEikEdwinVisual.prototype.propertyChanged = function(instance, property) {
	if (property != "size" && property != "location")
		instance.parent.forceLayout();
}
