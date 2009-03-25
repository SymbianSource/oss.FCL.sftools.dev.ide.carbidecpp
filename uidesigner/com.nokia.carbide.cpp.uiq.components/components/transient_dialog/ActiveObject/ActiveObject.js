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
* START_USECASES: CU18 END_USECASES
*
*
*/


include("../../containerLibrary.js")

function ActiveObject() {	
}

///////////////////////////////////////////////////////////////////////////////////////
//IPropertyListener

ActiveObject.prototype.propertyChanged = function(instance, property) {
	properties = instance.properties;
	if(property=="progressInfo" ){
		if(properties.progressInfo != "addNewProgressInfo"){
			instance.forceRedraw();
			instance.forceLayout();
			instance.properties.progressInfoHidden = instance.properties.progressInfo;
		}
	}					
}

