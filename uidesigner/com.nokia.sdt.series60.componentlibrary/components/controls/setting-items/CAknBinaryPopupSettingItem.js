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



include("../../implLibrary.js")

function CAknBinaryPopupSettingItem() { }

//////////////

		// note that laf will be null if a display model was not created
CAknBinaryPopupSettingItem.prototype.validate = function(instance, laf) {
	var properties = instance.properties;

	var messages = null;
	if (properties.items.length != 2) {
		messages = new java.util.ArrayList();	
		messages.add(createSimpleModelError(instance, 
			"items", 
			lookupString("ErrorNotTwoItemsInList"), 
				[instance.name]));
	} else if (properties.items[0].value == 0 && properties.items[1].value == 1) {
		// okay
	} else if (properties.items[1].value == 0 && properties.items[0].value == 1) {
		// okay
	} else {
		messages = new java.util.ArrayList();	
		messages.add(createSimpleModelError(instance, 
			"items", 
			lookupString("ErrorNotTwoItemsInList"), 
				[instance.name]));
	} 
	
	return messages;
}

	// note that laf will be null if a display model was not created
CAknBinaryPopupSettingItem.prototype.queryPropertyChange = function(instance, propertyPath,
				newVal, laf) {
				
	var properties = instance.properties;
	var message = null;

	// Don't toss away any changes at this point; otherwise,
	// the user will endure a dialog and have it all thrown away.
	// Better to let them go back and re-edit.
	
	return message;		
}

