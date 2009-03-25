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


include("containerLibrary.js")

function CAknDialogQueryContainment() {
}

function hasDialogContentAttribute(componentAttributes) {
	return hasAttributeValue(componentAttributes, "is-dialog-content", "true");
}

CAknDialogQueryContainment.prototype.canContainComponent = function(instance, otherComponent) {
	if (!hasDialogContentAttribute(otherComponent.attributes))
		return buildSimpleContainmentErrorStatus(
			lookupString("generalContainmentError"), 
			new Array( otherComponent.friendlyName ));
		
	return null;
}

CAknDialogQueryContainment.prototype.canContainChild = function(instance, child) {
	if (!hasDialogContentAttribute(child.component.attributes))
		return buildSimpleContainmentErrorStatus(
			lookupString("generalContainmentError"), 
			new Array( child.component.friendlyName ));
	
	return null;
}

CAknDialogQueryContainment.prototype.canRemoveChild = function(instance, child) {
	return true; // everything can be removed
}

CAknDialogQueryContainment.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return hasDialogContentAttribute(otherComponent.attributes);
}


