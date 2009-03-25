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

function CAknViewScript() {
}

// can contain single instance of navi content, control or status panes or top-level content
function canContainComponent(component, children) {
	var hasInstance = false;
	var isAllowedType = false;
	
	if (isToolbar(component)) {
		isAllowedType = true;
		if (getToolbar(children) != null){
			hasInstance = true;
		}
	}
	
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
	
	if (hasTopLevelContentContainerAttribute(component.attributes)) {
		isAllowedType = true;
		if (getContents(children) != null)
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

CAknViewScript.prototype.canContainComponent = function(instance, otherComponent) {
	return canContainComponent(otherComponent, instance.children);
}

CAknViewScript.prototype.canContainChild = function(instance, child) {
	return canContainComponent(child.component, instance.children);
}

CAknViewScript.prototype.canRemoveChild = function(instance, child) {
	return !hasTopLevelContentContainerAttribute(child.attributes);
}

CAknViewScript.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return isControlPane(otherComponent) || 
			isStatusPaneId(otherComponent.id) || 
			hasTopLevelContentContainerAttribute(otherComponent.attributes);
}

	// note that laf will be null if a display model was not created
CAknViewScript.prototype.validate = function(instance, laf) {
	// S60 will panic if the CBA is configured for an options menu but
	// the Avkon view resource doesn't specify one.
	// Validate that if the CBA is so configured that the optionsMenu property
	// is not undefined.
	var result = null;
	var hasFormChild = findAknFormChild(instance.children) != null;
	var controlPane = getControlPane(instance.children);
	if (controlPane != null) {
		var needsOptionsMenu = controlPane.properties.info.leftId == "EAknSoftkeyOptions";
		var optionsMenuName = hasFormChild ? null : instance.properties.optionsMenu;
		if (needsOptionsMenu && ((optionsMenuName == null || optionsMenuName == ""))) {
    		result = new java.util.ArrayList;
			result.add(createSimpleModelError(instance, 
				"optionsMenu", 
				lookupString("validateOptionsMenu"), 
				[  ]));
		}
	}
	// Adding a menubar object to a View containing a Form could give the user the mistaken
	// impression that the menubar will be used in some way. 
	// Validate that there is no menubar child when there is a Form child
	if (hasFormChild) {
		if (findImmediateChildByComponentID(instance.children, "com.nokia.sdt.series60.MenuBar")) {
			if (result == null)
				result = new java.util.ArrayList;
			result.add(newModelMessage(IStatus.INFO, 
						formatString(lookupString("validateMenubarWithForm"), [ ]), 
						instance, null, null));
		}
	}
	return result;
 }

	// note that laf will be null if a display model was not created
CAknViewScript.prototype.queryPropertyChange = function(instance, propertyPath,
					newValue, laf) {
	return null;
}
