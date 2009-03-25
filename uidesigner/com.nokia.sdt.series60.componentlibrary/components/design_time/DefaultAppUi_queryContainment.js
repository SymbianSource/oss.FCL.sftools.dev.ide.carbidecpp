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

function DefaultAppUiQueryContainment() {
}

// can contain top-level content, or a CBA if the top-level content
// allows one (we keep the CBA at the same position in the model)

function canContainComponent(instance, component) {
	var hasInstance = false;
	var isAllowedType = false;

	if (hasTopLevelContentContainerAttribute(component.attributes)) {
		isAllowedType = true;
		if (getContents(instance.children) != null)
			hasInstance = true;	
	}

	if (isControlPane(component)) {
		var container = findImmediateChildByAttributeValue(instance.children,
			"allow-cba-in-parent", "true");
		if (container != null) {
			isAllowedType = true;
			if (getControlPane(instance.children) != null)
				hasInstance = true;
		}
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

DefaultAppUiQueryContainment.prototype.canContainComponent = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent);
}

DefaultAppUiQueryContainment.prototype.canContainChild = function(instance, child) {
	return canContainComponent(instance, child.component);
}

DefaultAppUiQueryContainment.prototype.canRemoveChild = function(instance, child) {
	return !hasTopLevelContentContainerAttribute(child.attributes) &&
			!isControlPane(child.component);
}

DefaultAppUiQueryContainment.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return hasTopLevelContentContainerAttribute(otherComponent.attributes)
		|| isControlPane(otherComponent);
}

