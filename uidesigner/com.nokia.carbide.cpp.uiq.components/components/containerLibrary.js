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


include("messageLibrary.js")

var STATUS_PANE_ID = "com.nokia.sdt.series60.StatusPane";

function setBounds(container, rect) {
	if (container.properties.location.x != rect.x
	|| container.properties.location.y != rect.y) {
		//println("Setting location for " + container.instanceName + " to " + rect.x + "," + rect.y);
		container.properties.location.x = rect.x;
		container.properties.location.y = rect.y;
	}
	if (container.properties.size.width != rect.width
	|| container.properties.size.height != rect.height) {
		//println("Setting size for " + container.instanceName + " to " + rect.width + "," + rect.height);
		container.properties.size.width = rect.width;
		container.properties.size.height = rect.height;
	}
}

function doScreenLayout(container, laf) {
	var screenWidth = container.properties.size.width;
	var screenHeight = container.properties.size.height;
	var children = container.children;
	
	var statusArea = getStatusPane(children);
	if (statusArea != null) {
		setBounds(statusArea, laf.getRectangle("status.pane.bounds"));
	}
	
	var cba = getControlPane(children);
	if (cba != null) {
		setBounds(cba, laf.getRectangle("control.pane.bounds"));
	}

	var contents = getContents(children);
	if (contents != null) {
		setBounds(contents, laf.getRectangle("content.pane.bounds"));
	}
}

function getControlPane(children) {
    return findImmediateChildByComponentID(children, "com.nokia.sdt.series60.CBABase");
}

function getStatusPane(children) {
    return findImmediateChildByComponentID(children, "com.nokia.sdt.series60.StatusPane");
}

function getNaviPaneContent(children) {
	return findImmediateChildByAttributeValue(children, "is-navipane-content", "true");
}

function getStatusPaneContent(children, componentIdArg) {
	return findImmediateChildByComponentID(children, componentIdArg);
}


function getContents(children) {
	return findImmediateChildByAttributeValue(children, "is-top-level-content-container", "true");
}

function findImmediateChildByAttributeValue(children, attrName, attrValue) {
	var result = null;
	if (children != null) {
		for (var i in children) {
			var child = children[i];
			if (child.component != null && child.component.attributes != null && 
			    child.component.attributes[attrName] == attrValue) {
				result = child;
				break;
			}
		}
	}
	return result;
}

function findImmediateChildByComponentID(children, componentID) {
    var result = null;
	for (var i in children) {
		var child = children[i];
		if (child.component != null && child.component.isOfType(componentID)) {
			result = child;
			break;
		}
	}
	return result;
}

function hasAttributeValue(attributesArg, attrName, attrValue) {
	return attributesArg[attrName] == attrValue;
}

function hasTopLevelContentContainerAttribute(attributesArg) {
	return hasAttributeValue(attributesArg, "is-top-level-content-container", "true");
}

function hasStatusPaneContentAttribute(attributesArg) {
	return hasAttributeValue(attributesArg, "is-status-pane-content", "true");
}

function buildSimpleContainmentErrorStatus(errorString, params) {
	return buildSimpleErrorStatus(errorString, params);
}

function buildMultipleContainmentErrorStatus(header, errorString, params, status) {
	return buildMultipleErrorStatus(header, errorString, params, status);
}

function isControlPane(component) {
	return component.isOfType("com.nokia.sdt.series60.CBABase");
}

function isStatusPaneId(componentIdArg) {
	return componentIdArg == STATUS_PANE_ID;
}

function isDesignRef(component) {
	return component.isOfType("com.nokia.sdt.series60.DesignReference");
}

function getLayoutChildren(children) {
	var result = [];
	var idx = 0;
	if (children != null) {
		for (var i in children) {
			var child = children[i];
			if (child.component != null && child.component.attributes != null 
				&& child.component.attributes["is-non-layout-object"] != "true"
				&& child.component.attributes["is-non-transient-object"] != "true") {
				result[idx++] = child;
			}
		}
	}
	return result;
}

function allowsCBAInParent(instance) {
	return instance.attributes["allow-cba-in-parent"] == "true";
}

function getInstanceFromChildName(children, name) {
	if (children != null) {
		for (i in children) {
			if (children[i].name == name) {
				return children[i];
			}
		}
	}
	
	return null;
}


/**
 *	Find a child of type CAknForm
 */
function findAknFormChild(children) {
    return findImmediateChildByComponentID(children, "com.nokia.sdt.series60.CAknForm");
}

/**
 *	Iterate children to find the one with exitsApp property and return property value
 */
function childWantsExitBehavior(children) {
	for (var i in children) {
		if (children[i].isInstanceOf("com.nokia.sdt.series60.ContainerBase")) {
			return children[i].properties.exitsApp;
		}
	}
	
	return false;
}


function findNaviTabs(appUiInstance) {
	var statusPane = findImmediateChildByComponentID(appUiInstance.children, "com.nokia.sdt.series60.StatusPane");
	if (statusPane != null) {
		var naviTabs = findImmediateChildByComponentID(statusPane.children, "com.nokia.sdt.series60.NaviTabs");
		return naviTabs;
	}
	
	return null;
}

function hasNaviTabs(appUiInstance) {
	return findNaviTabs(appUiInstance) != null;
}

/**
 *	Get the view UID enumerator, adding the generating #include if necessary (for use from views)
 */
function getViewUidConstant(instance) {
	// the algorithm can deal with either CAknView or AvkonViewReference
	var name = Engine.queryEnumeratorForAlgorithm(instance, ".", 
		"com.nokia.sdt.component.symbian.NAME_ALG_VIEW_UID");
	return name;
}

/**
 *	Find or create the view UID enumerator (for use from appui)
 */
function findOrCreateViewUidConstant(instance) {
	// the algorithm can deal with either CAknView or AvkonViewReference
	return Engine.findOrCreateEnumeratorForAlgorithm(instance, ".", 
		"com.nokia.sdt.component.symbian.NAME_ALG_VIEW_UID");
}

/**
 *	Get the name of the project's HRH file (which contains view UID enums)
 */
function getProjectHrhFile() {
	return getProjectName() + ".hrh";
}

/**
 *	Get the name of the project's HRH file (which contains view UID enums)
 */
function includeProjectHrhFile(contribs) {
	var mycontrib = Engine.createContributionForPhase("MainUserIncludes")
	mycontrib.setText("#include \"" + getProjectHrhFile() + "\"\n");
	contribs.add(mycontrib);
}

/**
 *	Set up query containment based on an attribute in potential children.
 *
 *	@param prototype the prototype to add IQueryContainment to.
 *	The prototype must implement this functions:
 *	<p>
 *	<li>
 *		getAllowedAttribute(): return the attribute string for allowed children
 *
 *	The component must define a string with ID "generalContainmentError"
 * 	that takes a single argument for the type of component: e.g.,
 *	"A <container_type_name> can''t contain objects of type ''{0}''."
 */
function setupAttributeBasedQueryContainment(prototype) {
	var origCanContainComponent = null;
	if ("canContainComponent" in prototype)
		origCanContainComponent = prototype.canContainComponent;
	prototype.canContainComponent = function(instance, otherComponent) {
		var status = null;
		if (origCanContainComponent != null) {
			status = origCanContainComponent(instance, otherComponent);
		}
		var attrName = prototype.getAllowedAttribute();
		if (!hasAttributeValue(otherComponent.attributes, attrName, "true"))
			if (status != null)
				return buildMultipleContainmentErrorStatus(
					lookupString("containmentErrorHeader"),
					lookupString("generalContainmentError"), 
					new Array( otherComponent.friendlyName ), status);
			else
				return buildSimpleContainmentErrorStatus(
					lookupString("generalContainmentError"), 
					new Array( otherComponent.friendlyName ));
			
		return status;
	}

	var origCanContainChild = null;
	if ("canContainChild" in prototype)
		origCanContainChild = prototype.canContainChild;
	prototype.canContainChild = function(instance, child) {
		var status = null;
		if (origCanContainChild != null) {
			status = origCanContainChild(instance, child);
		}
		var attrName = prototype.getAllowedAttribute();
		if (!hasAttributeValue(child.component.attributes, attrName, "true"))
			if (status != null)
				return buildMultipleContainmentErrorStatus(
					lookupString("containmentErrorHeader"),
					lookupString("generalContainmentError"), 
					new Array( otherComponent.friendlyName ), status);
			else
				return buildSimpleContainmentErrorStatus(
					lookupString("generalContainmentError"), 
					new Array( child.component.friendlyName ));
		
		return status;
	}

	var origCanRemoveChild = null;
	if ("canRemoveChild" in prototype)
		origCanRemoveChild = prototype.canRemoveChild;
	prototype.canRemoveChild = function(instance, child) {
		if (origCanRemoveChild != null) {
			return origCanRemoveChild(instance, child);
		}
		return true; // everything can be removed
	}

	var origIsValidComponentInPalette = null;
	if ("isValidComponentInPalette" in prototype)
		origIsValidComponentInPalette = prototype.isValidComponentInPalette;
	prototype.isValidComponentInPalette = function(instance, otherComponent) {
		if (origIsValidComponentInPalette != null) {
			var result = origIsValidComponentInPalette(instance, otherComponent);
			if (!result)
				return result;
		}
		var attrName = prototype.getAllowedAttribute();
		return hasAttributeValue(otherComponent.attributes, attrName, "true");
	}
}

function countImmediateChildrenWithAttributeValue(children, attrName, attrValue) {
	var result = 0;
	if (children != null) {
		for (var i in children) {
			var child = children[i];
			if (child.component != null && child.component.attributes != null && 
			    child.component.attributes[attrName] == attrValue) {
				result++;
			}
		}
	}
	return result;
}

function isAvkonView(instance) {
	return (instance != null && instance.componentId == "com.nokia.sdt.series60.CAknView");
}
