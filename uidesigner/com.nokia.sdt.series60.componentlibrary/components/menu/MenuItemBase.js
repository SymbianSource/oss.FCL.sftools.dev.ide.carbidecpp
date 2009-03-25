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


include("../messageLibrary.js")
include("../implLibrary.js")

function MenuItemBase() {

}

// IComponentValidator
MenuItemBase.prototype.validate = function(instance) {

	var messages = null;
	
	// cannot have a "selected" event if a submenu is present
	if (instance.children.length != 0 && "selected" in instance.events) {
		var messages = new java.util.ArrayList();
		var modelMessage = newModelMessage(IStatus.WARNING, 
			formatString(
				lookupString("selectedEventInCascadingMenuItem"), 
				[ instance.name ]), 
			instance, null, "selected");
		messages.add(modelMessage);
	} 
	return messages;
}

MenuItemBase.prototype.queryPropertyChange = function(instance, propertyId, newValue) {
	return null;
}

