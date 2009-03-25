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
* START_USECASES: CU10 END_USECASES
*
*
*/

include("../../containerLibrary.js")
include("../../renderLibrary.js")


function IconSlot() {
}


////////////////////////////////////////////////////////////////////////////////
// IQueryContainment

IconSlot.prototype.canContainComponent = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent);
}


IconSlot.prototype.canContainChild = function(instance, child) {
	return canContainComponent(instance, child.component);
}


IconSlot.prototype.canRemoveChild = function(instance, child) {
	return false;
}


IconSlot.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent) == null;
}


function canContainComponent(instance, component) {
	if (isLayoutControlBaseComponent(component)) {
		if (instance.children.length < 1)
			return null;
		else
			return buildSimpleContainmentErrorStatus(
				lookupString("iQueryContaimentError"), null);
	} else {
		return buildSimpleContainmentErrorStatus(
			lookupString("iQueryContaimentError"), new Array( component.friendlyName ));
	}			
}


function isLayoutControlBaseComponent(component) {
	return component.id == "com.nokia.carbide.uiq.CEikImage" || component.id == "com.nokia.carbide.uiq.CEikImage_Layout";
}
