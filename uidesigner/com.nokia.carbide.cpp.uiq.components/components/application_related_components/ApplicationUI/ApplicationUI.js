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




function ApplicationUI() {
}

///////////////////////////////////////////////////////////
// IQueryContainment

ApplicationUI.prototype.canContainComponent = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent);
}

ApplicationUI.prototype.canContainChild = function(instance, child) {
	return canContainComponent(instance, child.component);
}

ApplicationUI.prototype.canRemoveChild = function(instance) {
	return true;
}

ApplicationUI.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent) == null;
}


function canContainComponent(instance, component) {
	if (isAllowedChildComponent(component))
		return null;
	
	return buildSimpleContainmentErrorStatus(
			lookupString("AppUiContainmentErr"), new Array( component.friendlyName ));			
}

function isAllowedChildComponent(component) {
	return component.id=="com.nokia.carbide.uiq.DesignReference"||component.id=="com.nokia.carbide.uiq.CEikLabel";
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
