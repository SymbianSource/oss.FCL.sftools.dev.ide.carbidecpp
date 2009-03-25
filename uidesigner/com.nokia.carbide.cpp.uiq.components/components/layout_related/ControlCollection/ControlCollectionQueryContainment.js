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




function ControlCollectionQueryContainment() {
}
///////////////////////////////////////////////////////////
// IQueryContainment

ControlCollectionQueryContainment.prototype.canContainComponent = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent);
}

ControlCollectionQueryContainment.prototype.canContainChild = function(instance, child) {
	return canContainComponent(instance, child.component);
}

ControlCollectionQueryContainment.prototype.canRemoveChild = function(instance, child) {
	return validateCanRemove(child);
}

ControlCollectionQueryContainment.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent) == null;
}

function canContainComponent(instance, component) {
	if (isAllowedChildComponent(instance, component))
		return null;
	
	return buildSimpleContainmentErrorStatus(
			lookupString("ModelGroupBaseContainmentErr"), new Array(instance.component.friendlyName, component.friendlyName ));			
}

function isAllowedChildComponent(instance, component) {
	return component.isOfType(instance.attributes["group-child-type"]);
}

function buildSimpleContainmentErrorStatus(errorString, params) {
	return buildSimpleErrorStatus(errorString, params);
}

function buildSimpleErrorStatus(errorString, params) {
	statusBuilder = newStatusBuilder();
	statusBuilder.add(IStatus.ERROR, errorString, params);
	return statusBuilder.createStatus("", null);
}

function validateCanRemove(childBeingRemoved) {
	if (!childBeingRemoved.isInstanceOf("com.nokia.carbide.uiq.CEikImage")) // progressInfo para Carlos
		return true;
	
	var SPECIAL_ICONSLOT_VALIDATION = true;
	//Get root container
	rootContainer = childBeingRemoved.rootContainer;
	
	//Get initial group
	var initialGroupType = ""; 
	if(rootContainer.isInstanceOf("com.nokia.carbide.uiq.CQikView")){
		initialGroupType = "com.nokia.carbide.uiq.LayoutsGroup";		
	}else{
		initialGroupType = "com.nokia.carbide.uiq.ContainersGroup"; 		
	}

	var initialGroupInstance = rootContainer.findChildOfType(initialGroupType);
	
	//start recursive calls
	return !hasLimitatingReferences(initialGroupInstance, childBeingRemoved.name, SPECIAL_ICONSLOT_VALIDATION);	
}

function hasLimitatingReferences(instance, controlCollectionItemInstanceName, specialIconSlotValidation) {
	if (instance.isInstanceOf("com.nokia.carbide.uiq.LayoutControlBase") && 
			instance.properties.control == controlCollectionItemInstanceName) { 
		if (specialIconSlotValidation) {
			if (instance.parent.isInstanceOf("com.nokia.carbide.uiq.IconSlot"))
				return true;
		} else {
			return true;
		}
	}
	
	for (var i=0; i<instance.children.length; i++) {
		var child = instance.children[i];
		if(hasLimitatingReferences(child, controlCollectionItemInstanceName, specialIconSlotValidation))
			return true;
	}
	return false;
}