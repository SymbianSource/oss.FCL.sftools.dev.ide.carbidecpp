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
include("../editors/editorLibrary.js")

function CAknEnumeratedTextPopup() {
}

//////////////

		// note that laf will be null if a display model was not created
CAknEnumeratedTextPopup.prototype.validate = function(instance, laf) {
	var properties = instance.properties;

	var messages = null;
	if (properties.items.length == 0) {
		messages = new java.util.ArrayList();	
		messages.add(createSimpleModelError(instance, 
			"items", 
			lookupString("ErrorEmptyList"), 
				[instance.name]));
	} else if (properties.active >= properties.items.length) {
 		messages = new java.util.ArrayList();	
		messages.add(createSimpleModelError(instance, 
			"active", 
			lookupString("ErrorInvalidValue"), 
				[instance.name, properties.active, properties.items.length]));
	}
	
	return messages;
}

	// note that laf will be null if a display model was not created
CAknEnumeratedTextPopup.prototype.queryPropertyChange = function(instance, propertyPath,
				newVal, laf) {
				
	var properties = instance.properties;
	var message = null;

	// Don't toss away any list changes at this point; otherwise,
	// the user will endure a dialog and have it all thrown away.
	// Better to let them go back and re-edit.
 
 	if (propertyPath == "active" 
 		&& properties.active >= properties.items.length) {
 		message = formatString(lookupString("ErrorInvalidValue"), 
				[instance.name, properties.active, properties.items.length]);
 	}
 	
	return message;		
}

