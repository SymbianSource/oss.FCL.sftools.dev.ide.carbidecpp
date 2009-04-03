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

function CAknAppUiQueryContainment() {
}

// can contain single instance of navi content, control or status panes
function canContainComponent(component, children) {
	var hasInstance = false;
	var isAllowedType = false;
	
	if (isControlPane(component)) {
		isAllowedType = true;
		if (getControlPane(children) != null)
			hasInstance = true;
	}
	
	if (isStatusPaneId(component.id)) {
		isAllowedType = true;
		if (getStatusPane(children) != null)
			hasInstance = true;
	}
	
	if (isDesignRef(component))
		isAllowedType = true;
	
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

CAknAppUiQueryContainment.prototype.canContainComponent = function(instance, otherComponent) {
	return canContainComponent(otherComponent, instance.children);
}

CAknAppUiQueryContainment.prototype.canContainChild = function(instance, child) {
	return canContainComponent(child.component, instance.children);
}

CAknAppUiQueryContainment.prototype.canRemoveChild = function(instance, child) {
	// Attribute of is-not-user-removable on design refs will ensure that 
	// all deletion commands not enabled on them, 
	// however we must allow their programmatic deletion.
	if (isDesignRef(child.component))
		return true;
		
	return false;
}

CAknAppUiQueryContainment.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return isControlPane(otherComponent) || isStatusPaneId(otherComponent.id);
}


