/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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

function StatusPaneQueryContainment() {
}

function isNaviTabsComponent(component) {
	var NAVI_TABS_ID = "com.nokia.sdt.series60.NaviTabs";
	return component.isOfType(NAVI_TABS_ID);
}

// can contain single instance of any allowed status pane content
function canContainComponent(component, children) {

	// early exit if not status content (we depend on other attributes)
	if (!hasStatusPaneContentAttribute(component.attributes)) {
		return buildSimpleContainmentErrorStatus(
			lookupString("spGeneralContainmentError"), 
			new Array( component.friendlyName ));
	}	

	// throw out if incorrect model type
	var disposition = component.attributes["model-type-disposition"];
	if (disposition == null)
		disposition = "any";
	if (disposition == "root" && !isInRootModel())
		return buildSimpleContainmentErrorStatus(
			lookupString("spStatusContentNotInRootContainmentError"), 
			[ component.friendlyName ]);			
	if (disposition == "view" && isInRootModel())
		return buildSimpleContainmentErrorStatus(
			lookupString("spStatusContentNotInViewContainmentError"), 
			[ component.friendlyName ]);			
	
	var hasInstance = false;

	var NAVI_BASE_ID = "com.nokia.sdt.series60.NaviBase";
	var isNaviContent = component.isOfType(NAVI_BASE_ID);
	var hasNaviContent = false;
	
//	println("cmp = " + component.id + ", attrs = " + component.attributes);	
	
	if (isNaviContent) {
		if (getStatusPaneContent(children, NAVI_BASE_ID) != null) {
			hasInstance = true;
			hasNaviContent = true;
		}
	} else {
		if (getStatusPaneContent(children, component.id) != null)
			hasInstance = true;
	}

	if (hasNaviContent)
		return buildSimpleContainmentErrorStatus(
			lookupString("spSingleNaviInstanceContainmentError"), 
			new Array( component.friendlyName ));			
	else if (hasInstance)
		return buildSimpleContainmentErrorStatus(
			lookupString("spSingleInstanceContainmentError"), 
			new Array( component.friendlyName ));			

	return null;
}

StatusPaneQueryContainment.prototype.canContainComponent = function(instance, otherComponent) {
	return canContainComponent(otherComponent, instance.children);
}

StatusPaneQueryContainment.prototype.canContainChild = function(instance, child) {
	return canContainComponent(child.component, instance.children);
}

StatusPaneQueryContainment.prototype.canRemoveChild = function(instance, child) {
	return true;
}

StatusPaneQueryContainment.prototype.isValidComponentInPalette = function(instance, otherComponent) {

	// throw out if incorrect model type
	var disposition = otherComponent.attributes["model-type-disposition"];
	if (disposition == null)
		disposition = "any";
	if (disposition == "root" && !isInRootModel())
		return false;
	if (disposition == "view" && isInRootModel())
		return false;

	var a = otherComponent.attributes;
	return hasStatusPaneContentAttribute(a);
}

