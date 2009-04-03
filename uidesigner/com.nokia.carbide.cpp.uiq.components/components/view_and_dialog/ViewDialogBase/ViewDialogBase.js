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
*
*/

include("../../containerLibrary.js")
include("../../renderLibrary.js")


function ViewDialogBase() {
}


////////////////////////////////////////////////////////////////////////////////
// IQueryContainment

ViewDialogBase.prototype.canContainComponent = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent);
}


ViewDialogBase.prototype.canContainChild = function(instance, child) {
	return canContainComponent(instance, child.component);
}


ViewDialogBase.prototype.canRemoveChild = function(instance, child) {
	return instance.children.length > 1 ;
}


ViewDialogBase.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent) == null;
}


// Allow a single instance of
// [View|Dialog]ConfigurationsGroup
// CommandListsGroup
// [Layouts|Containers]Group
// ControlCollection
function canContainComponent(instance, component) {
	var children = instance.children;
	var hasInstance = false;
	var isAllowedType = false;
	
	if( component.isOfType("com.nokia.carbide.uiq.CommandListsGroup") ) {
		isAllowedType = true;
		if ( findImmediateChildByComponentID(children, "com.nokia.carbide.uiq.CommandListsGroup") != null )
			hasInstance = true;
	}
	
	if( component.isOfType("com.nokia.carbide.uiq.ControlCollection") ) {
		isAllowedType = true;
		if ( findImmediateChildByComponentID(children, "com.nokia.carbide.uiq.ControlCollection") != null )
			hasInstance = true;
	}
	
	if( component.isOfType(instance.attributes["ui-configuration-group-component-id"])) {
		isAllowedType = true;
		if ( findImmediateChildByComponentID(children, instance.attributes["ui-configuration-group-component-id"]) != null )
			hasInstance = true;
	}
	
	if( component.isOfType(instance.attributes["layout-group-component-id"]) ) {
		isAllowedType = true;
		if ( findImmediateChildByComponentID(children, instance.attributes["layout-group-component-id"]) != null )
			hasInstance = true;
	}
	
	if (hasInstance)
		return buildSimpleContainmentErrorStatus(
			lookupString("singleInstanceContainmentError"),
			new Array( component.friendlyName ));
	
	if (!isAllowedType)
		return buildSimpleContainmentErrorStatus(
			lookupString("generalContainmentError"),
			new Array( component.friendlyName ));
	
	return null;
}

