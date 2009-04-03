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


function CommandId() {
}


////////////////////////////////////////////////////////////////////////////////
//IComponentValidator

CommandId.prototype.validate = function(instance) {
	if (instance != null || instance != "") {
		if(instance.properties.commandId != "" && instance.properties.systemCommandId != "" ){
			var modelMessage = newModelMessage(IStatus.WARNING,formatString(lookupString("validate.flags"),
					[instance.name ]),instance, "flags", null);
			return [ modelMessage ];												
		}
	}
	return null;
}

CommandId.prototype.queryPropertyChange = function(instance, propertyId, newValue) {
	return null;
}
///////////////////////////////////////////////////////////////////////////////
//IInitializer
CommandId.prototype.initialize = function(instance, isConfigured) {
	if (isConfigured) 
		return;
	var initialCommandIdProposal = instance.properties.name;
	var commandListInstance = instance.parent;
	var tryCounter = 0;
	var finalCommandId = getValidCommandId(instance.properties.name, commandListInstance, initialCommandIdProposal, tryCounter);
	
	/*if (isConfigured && (finalCommandId != initialCommandIdProposal || instance.properties.systemCommandId.length > 0)) {
		return;
	}*/
	
	instance.properties.commandId = finalCommandId;
	instance.properties.handlerNameTemplate = instance.properties.commandId;
}

function getValidCommandId(commandIdName, commandList, initialCommandIdProposal, tryCounter) {
	/*
	println("------------" + tryCounter + "------------");
	println("commandIdName=" + commandIdName);
	println("initialCommandIdProposal=" + initialCommandIdProposal);
	println("tryCounter=" + tryCounter);
	println("initialCommandIdProposal + tryCounter=" + initialCommandIdProposal + tryCounter);
	*/
	
	var RECURSIVE_LIMIT = 100;
	if (tryCounter > RECURSIVE_LIMIT)
		return initialProposal;
		
	var commandIdProposal;	
	if (tryCounter != 0) {
		commandIdProposal = initialCommandIdProposal + tryCounter;
	} else {
		commandIdProposal = initialCommandIdProposal;
	}
	
	var repeated = false;
	for (var i=0; i<commandList.children.length; i++) {
		var childProperties = commandList.children[i].properties; 
		if(childProperties.name != commandIdName && childProperties.commandId == commandIdProposal) {
			repeated = true;
			break;
		}
	}
	
	if (repeated) {
		return getValidCommandId(commandIdName, commandList, initialCommandIdProposal, ++tryCounter);
	} else {
		return commandIdProposal;
	}
}


////////////////////////////////////////////////////////////////////////////////
//IPropertyListener

CommandId.prototype.propertyChanged = function(instance, property) {
	if (  (property=="commandId" && instance.properties.systemCommandId == "" && instance.properties.commandId != "")
		||(property=="systemCommandId" && instance.properties.systemCommandId == "" && instance.properties.commandId != "") ){
		instance.properties.handlerNameTemplate=instance.properties.commandId;
	}
	if (  (property=="commandId" && instance.properties.systemCommandId != "" && instance.properties.commandId == "")
			||(property=="systemCommandId" && instance.properties.systemCommandId != "" && instance.properties.commandId == "") ){
			instance.properties.handlerNameTemplate=instance.properties.systemCommandId;
	}
	if (  (property=="commandId" && instance.properties.systemCommandId != "" && instance.properties.commandId != "")
			||(property=="systemCommandId" && instance.properties.systemCommandId != "" && instance.properties.commandId != "") ){
			instance.properties.handlerNameTemplate=instance.properties.commandId;
	}

}
