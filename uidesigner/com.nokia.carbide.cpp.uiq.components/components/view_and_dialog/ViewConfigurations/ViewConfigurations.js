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

function ViewConfigurations() {
}

////////////////////////////////////////////////////////////////////////////////
// IComponentValidator

ViewConfigurations.prototype.validate = function( instance ) {
	var messages = new java.util.ArrayList();
	var uiConfigError = validateUIConfigurationInstances(instance, "uiconfig");
	var commandListWarning = validateCommandList ( instance );

	if ( uiConfigError != null ) {
		messages.addAll(java.util.Arrays.asList(uiConfigError));
	}

	if ( commandListWarning != null ) {
		messages.addAll(commandListWarning);
	}
	return messages;
}

ViewConfigurations.prototype.queryPropertyChange = function(instance, propertyPath, newValue) {
	return null;
}

function validateUIConfigurationInstances (instance, propertyPath) {
	if (propertyPath == "uiconfig") {
		for (var i = 0; i < instance.children.length; i++) {
			for (var j = 0; j < instance.children.length; j++) {
				//println(i + "-" + j  + " = " + instance.children[i].properties.uiconfig + "-" + instance.children[j].properties.uiconfig);
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

/////////////////////////////////////////////////////////////////////////
function validateCommandList ( instance) {
	var viewOrDialogInstance = getViewOrDialogInstance(instance);
	var viewOrDialogConfigurations;
	var configurationCommandList;
	var viewOrContainerCommandList;  
	var pageCommandList;
	var messages = new java.util.ArrayList();

	if ( viewOrDialogInstance == null ) {
		return null;
	}

	if (isViewConfigurationsComponent(viewOrDialogInstance)) {
		viewOrDialogConfigurations = getViewOrDialogConfigurations( 
									 viewOrDialogInstance, 
									 "com.nokia.carbide.uiq.ViewConfigurationsGroup");
	} 
	if (isDialogConfigurationsComponent(viewOrDialogInstance)) {
		viewOrDialogConfigurations = getViewOrDialogConfigurations( 
									 viewOrDialogInstance, 
									 "com.nokia.carbide.uiq.DialogConfigurationsGroup");
	}

	if ( viewOrDialogConfigurations == null ) {
		return null;
	}

	for (var i in viewOrDialogConfigurations.children ) {

		//println("********* viewOrDialogConfigurations.children[i]: "+ viewOrDialogConfigurations.children[i] );
		
		configurationCommandList = lookupInstanceByName (
			 (viewOrDialogConfigurations.children[i]).properties.commandList);
	
		//println(" ******** configurationCommandList: "+configurationCommandList );

		var viewOrContainer = lookupInstanceByName (
			  (viewOrDialogConfigurations.children[i]).properties.viewOrContainer);
		if ( viewOrContainer == null ) {
			return null;
		}
		viewOrContainerCommandList = lookupInstanceByName (
			  viewOrContainer.properties.commandList);
		
		//println("viewOrContainerCommandList: "+viewOrContainerCommandList );
		if (viewOrContainer.children.length <= 1) {
			return null;
		}

		for (var j in viewOrContainer.children) {

			var pages = viewOrContainer.children[j];
			if (!pages.isInstanceOf("com.nokia.carbide.uiq.ViewPage")) {
				return messages;
			}
			pageCommandList = lookupInstanceByName (
			  pages.properties.commandList);

			//println("pageCommandList: "+pageCommandList );

			if (pageCommandList != null && 
				configurationCommandList != null && 
				viewOrContainerCommandList != null) {
					if ( pageCommandList.properties.name == configurationCommandList.properties.name ){
					messages.add(newModelMessage(IStatus.WARNING,formatString(lookupString("validate.commandList"),
					[pageCommandList.properties.name, pages.properties.name, (viewOrDialogConfigurations.children[i]).properties.name]),instance, "commandList",null));
					} else {
						if (pageCommandList.properties.name == viewOrContainerCommandList.properties.name ){
								messages.add(newModelMessage(IStatus.WARNING,formatString(lookupString("validate.commandList"),
								[pageCommandList.properties.name, pages.properties.name, viewOrContainer.properties.name ]),instance, "commandList", null));
						}
					}
			}
		}
	}
	return messages;
}

function getViewOrDialogInstance(instance) {
	var viewOrDialogInstance = instance.rootContainer;
	return viewOrDialogInstance;
}

function getViewOrDialogConfigurations( viewOrDialogInstance, viewOrDialogQualifiedName ) { 

	if (viewOrDialogInstance == null) {
		return null;
	}

	// find the view configurations 
	var configsGroup = viewOrDialogInstance.findChildOfType(viewOrDialogQualifiedName);
	if ( configsGroup == null){
		return null;
	}
	//println("******** configsGroup: "+ configsGroup);
	return configsGroup;
}

function isViewConfigurationsComponent(instance) {
	return instance.attributes["ui-configuration-group-component-id"] == "com.nokia.carbide.uiq.ViewConfigurationsGroup";
}

function isDialogConfigurationsComponent(instance) {
	return instance.attributes["ui-configuration-group-component-id"] == "com.nokia.carbide.uiq.DialogConfigurationsGroup";
}