/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
include("../implLibrary.js")

// IComponentValidator

function SingleLineDataQuery20Validator() {
}

SingleLineDataQuery20Validator.prototype.validate = function(instance, laf) {
	var messages = null;
	// in case the baseline sdk is changed from 2.1 and 2.0
	var properties = instance.properties;
	if (properties.type == null) {
		var message = createSimpleModelError(instance, 
			"type",
			lookupString("InvalidTypeError"),
			[ instance.name ] );
		messages = new java.util.ArrayList();
		messages.add(message);
	}
	return messages;
}

SingleLineDataQuery20Validator.prototype.queryPropertyChange = function(instance, propertyPath,
				newVal, laf) {
	// Only need to check at load time
	return null;
}

