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
* START_USECASES: CU9 END_USECASES
*
*
*/

include("../../implLibrary.js")

function DialogConfigurations() {
}

////////////////////////////////////////////////////////////////////////////////
// IComponentValidator

DialogConfigurations.prototype.validate = function(instance) {
	return validateUIConfigurationInstances(instance, "uiconfig");
}

DialogConfigurations.prototype.queryPropertyChange = function(instance, propertyPath, newValue) {
	return null;
}

function validateUIConfigurationInstances (instance, propertyPath) {
	if (propertyPath == "uiconfig") {
		for (var i = 0; i < instance.children.length; i++) {
			for (var j = 0; j < instance.children.length; j++) {
				if (instance.children[i].properties.uiconfig == instance.children[j].properties.uiconfig && i != j) {
					return [createSimpleModelError(instance, 
							propertyPath,
							lookupString("uiConfigurationError"),
							instance.name )
						   ];
				}
			}
		}
	}
	return null;
}