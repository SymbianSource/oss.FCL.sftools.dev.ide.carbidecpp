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

function ViewConfiguration() {
}

////////////////////////////////////////////////////////////////////////////////
// IComponentValidator

ViewConfiguration.prototype.validate = function(instance) {
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

ViewConfiguration.prototype.queryPropertyChange = function(instance, propertyPath, newValue) {
	return null;
}
