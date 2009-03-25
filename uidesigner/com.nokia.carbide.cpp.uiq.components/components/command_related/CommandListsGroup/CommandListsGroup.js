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

include("../../containerLibrary.js")
include("../../renderLibrary.js")


function CommandListsGroup() {
}


////////////////////////////////////////////////////////////////////////////////
//IComponentValidator

CommandListsGroup.prototype.validate = function(instance) {
	if (instance != null || instance != "") {
		for (var i = 0; i < instance.children.length; i++) {			
			for (var j = i+1; j < instance.children.length; j++) {
				if ( instance.children[i].isInstanceOf("com.nokia.carbide.uiq.CommandId")
						&& instance.children[j].isInstanceOf("com.nokia.carbide.uiq.CommandId")
						&& (instance.children[i].properties.handlerNameTemplate == instance.children[j].properties.handlerNameTemplate)  ) {
					var modelMessage = newModelMessage(IStatus.WARNING,formatString(lookupString("validate.flags"),
							[instance.name ]),instance, "flags", null);
					return [ modelMessage ];					
					}
			}
		}	
	}
	return null;
}

CommandListsGroup.prototype.queryPropertyChange = function(instance, propertyId, newValue) {
	return null;
}
