/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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


include("../containers/containerLibrary.js")
include("../implLibrary.js")

function SystemMenuPane() {
}


// IQueryContainment

function canContainComponent(instance, component) {
	if (component.id == "com.nokia.sdt.series60.SystemMenuItem")
		return null;
	return buildSimpleContainmentErrorStatus(
			lookupString("systemMenuPaneContainmentErr"), []);			
}

SystemMenuPane.prototype.canContainComponent = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent);
}

SystemMenuPane.prototype.canContainChild = function(instance, child) {
	return canContainComponent(instance, child.component);
}

SystemMenuPane.prototype.canRemoveChild = function(instance) {
	return false;
}

SystemMenuPane.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return false;
}

// IComponentValidator
SystemMenuPane.prototype.validate = function(instance) {
	if (instance.properties.systemResourceName == "") {
		var modelMessage = newModelMessage(IStatus.WARNING, 
			formatString(lookupString("NoSystemMenuResource"), 
				[instance.name ]), 
			instance, "systemResourceName", null);
		return [ modelMessage ];
	}
	return null;
}

SystemMenuPane.prototype.queryPropertyChange = function(instance, propertyPath, newValue) {
	return null;
}

