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




function ModelGroupBase() {
}
///////////////////////////////////////////////////////////


// IQueryContainment
ModelGroupBase.prototype.canContainComponent = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent);
}

ModelGroupBase.prototype.canContainChild = function(instance, child) {
	return canContainComponent(instance, child.component);
}

ModelGroupBase.prototype.canRemoveChild = function(instance) {
	return instance.attributes["allow-empty-group"] == "true" ||
		instance.children.length > 1;
}

ModelGroupBase.prototype.isValidComponentInPalette = function(instance, otherComponent) {
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

///////////////////////////////////
