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
* START_USECASES: CU11 END_USECASES
*
*
*/


include("../../implLibrary.js")  

function Command() {
}

////////////////////////////////////////////////////////////////////////////////
//IComponentValidator

Command.prototype.validate = function(instance) {
	properties = instance.properties;
	if (instance != null || instance != "") {
		if(properties.text.length > 255  ){
			return [createSimpleModelError(instance, 
					"text",
					formatString(lookupString("validate.text"),
					[instance.name ]),
					null )
				   ];
		}				
		if(properties.shortText.length > 255  ){
			return [createSimpleModelError(instance, 
					"shortText",
					lookupString("validate.shortText"),
					instance.name )
				   ];
		}				
	}
	return null;
}

Command.prototype.queryPropertyChange = function(instance, propertyPath, newValue) {
	return null;
}
