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


function CommandList() {
}


////////////////////////////////////////////////////////////////////////////////
//IComponentValidator

CommandList.prototype.validate = function(instance) {
	var flag = 0;
	var listOfCommands = "";
	if (instance != null || instance != "") {
		for (var i = 0; i < instance.children.length; i++) {			
			if ( instance.children[i].properties.commandId==""){
				flag = 1;	
				listOfCommands += instance.children[i].properties.name + ",";						
			}	
			
		}
		if (flag == 1){
			var finalList = listOfCommands.substring(0,listOfCommands.length-1);
			var modelMessage = newModelMessage(IStatus.WARNING,formatString(lookupString("validate.flags1"),
							[instance.name, finalList ]),instance, "flags", null);
						return [ modelMessage ];
			}
			for (var i = 0; i < instance.children.length; i++) {
				for (var j = i+1; j < instance.children.length; j++) {
					if ( instance.children[i].isInstanceOf("com.nokia.carbide.uiq.Command")
						&& instance.children[j].isInstanceOf("com.nokia.carbide.uiq.Command")
						&& (instance.children[i].properties.commandId == instance.children[j].properties.commandId)  ) {
						var modelMessage = newModelMessage(IStatus.WARNING,formatString(lookupString("validate.flags2"),
							[instance.name ]),instance, "flags", null);
						return [ modelMessage ];					
					}
				}
			}
		}
		
	
	return null;
}

CommandList.prototype.queryPropertyChange = function(instance, propertyId, newValue) {
	return null;
}
