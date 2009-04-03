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

function DialogConfiguration() {
}

////////////////////////////////////////////////////////////////////////////////
//IComponentValidator

DialogConfiguration.prototype.validate = function(instance) {
	var resultContainer = validateContainer (instance);
	if (resultContainer != null)
		return resultContainer;
	
	var viewOrContainerValue = instance.properties.viewOrContainer;
	if (viewOrContainerValue == null || viewOrContainerValue.length == 0) {
		return [createSimpleModelError(instance, 
			"viewOrContainer",
			lookupString("viewOrContainerError"),
			instance.name )
			];
	}
	return null;
}


DialogConfiguration.prototype.queryPropertyChange = function(instance, propertyId, newValue) {
	return  validateContainer (instance);
}

function validateContainer (instance) {
	var container = instance.properties.viewOrContainer;

	if (container != null || container != "") {
		var refInstance = lookupInstanceByName(container);
	
		if (refInstance != null) {
	    	var scrollable = refInstance.properties.scrollable;
	
			if (scrollable != "EQikCtScrollableContainer") {
				return [createSimpleModelError(instance, 
						"viewOrContainer",
						lookupString("iContainerValidatorError - "+ container+ " is not scrollable"),
						[instance.name] )
					   ];
			}
		}
	}
	return null;
}