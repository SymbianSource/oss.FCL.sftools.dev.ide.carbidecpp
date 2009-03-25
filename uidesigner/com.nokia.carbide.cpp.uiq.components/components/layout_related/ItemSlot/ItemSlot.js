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
* START_USECASES: CU10 END_USECASES
*
*
*/

include("../../containerLibrary.js")
include("../../renderLibrary.js")
include("../../implLibrary.js")

function ItemSlot() {
}

////////////////////////////////////////////////////////////////////////////////
// IComponentValidator

ItemSlot.prototype.validate = function(instance) {
	if (instance != null || instance != "") {
		var properties = instance.properties;
		if ( instance.children.length == 0 && properties.caption == "" ) {
			var modelMessage = newModelMessage(IStatus.WARNING,formatString(lookupString("validate.flags1"),
								[instance.name ]),instance, "flags", null);
			return [ modelMessage ];					
		}
	}
	return null;
}

ItemSlot.prototype.queryPropertyChange = function(instance, propertyId, newValue) {
	return null;
}

////////////////////////////////////////////////////////////////////////////////
// IDirectLabelEdit

setupCommonDirectLabelEditing(ItemSlot.prototype, 
	"caption",
	areaWithParentWidth,
	function(instance, laf) { return laf.getFont(instance.properties.standardFont); } 
	)
