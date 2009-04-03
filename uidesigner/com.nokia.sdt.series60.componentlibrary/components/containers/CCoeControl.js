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


include("../renderLibrary.js")
include("containerLibrary.js")

function CCoeControlLayout() {
}

CCoeControlLayout.prototype.layout = function(instance, laf) {
	var thisWidth = instance.properties.size.width;
	var thisHeight = instance.properties.size.height;
		
	var exclusiveChild = getExclusiveChild(instance.children);
	if (exclusiveChild != null) {
		setBounds(exclusiveChild, new Rectangle(0, 0, thisWidth, thisHeight));
	}
}

CCoeControlLayout.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null;
}

function getExclusiveChild(children) {
	var result = null;
	for (var i = 0; i < children.length; i++) {
		var child = children[i];
		if (child.component != null && child.component.attributes["is-exclusive-child-layout-object"] == "true") {
			return children[i];
		}
	}
	return null;
}

///////////////////



function CCoeControlVisual() {
}

CCoeControlVisual.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;
	var color = colorFromString(laf, properties.backColor);
	if (color != null) {
		graphics.setBackground(color);
		graphics.fillRectangle(0, 0, properties.size.width, properties.size.height);
	}
	else {
		var properties = instance.properties;	
		var width = properties.size.width;
		var height = properties.size.height;
		graphics.setBackground(getBackgroundColor(instance, laf));
		graphics.fillRectangle(new Rectangle(0, 0, width, height));
	}
}

CCoeControlVisual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null; // not needed	
}

/////////////////////

function CCoeControlPropertyExtenders() {
}

	// Return instances that may provide extension properties
	// The target instance parameter is the instance to receive the
	// additional properties
CCoeControlPropertyExtenders.prototype.getPropertyExtenders = function(instance, targetInstance) {
	if (isAvkonView(targetInstance.parent) && !isPreviewPopUp(instance)) {
		return [instance];
	}
	
	return null;
}
	
CCoeControlPropertyExtenders.prototype.getExtensionSetNames = function(instance, targetInstance) {
	if (instance == targetInstance)
		return [ "default" ];
		
	return null;
}

/////////////////////


function CCoeControlQueryContainment() {
}

CCoeControlQueryContainment.prototype.getAllowedAttribute = function() {
	return "is-ccoecontrol-content";
}

setupAttributeBasedQueryContainment(CCoeControlQueryContainment.prototype);

/////////////////////////

function CCoeControlPropertyListener() {
}

CCoeControlPropertyListener.prototype.propertyChanged = function(instance, propertyId) {
	if (propertyId == "backColor") {
		// anything hosted on top may depend on the color
		for (var c in instance.children) {
			instance.children[c].forceRedraw();
		}
	}
}

/////////////////////////

function CCoeControlChildListener() {
}

CCoeControlChildListener.prototype.childAdded = function(instance, child, laf) {
	var properties = instance.properties;
	var childWantsFocus = hasAttributeValue(child.attributes, "wants-initial-focus", "true");
	var numFocusableChildren = 
		countImmediateChildrenWithAttributeValue(instance.children, "wants-initial-focus", "true");
		
	// if has no initial focus, but the child just added is the only one that wants it
	if ((properties.initialFocus == "") && childWantsFocus && (numFocusableChildren == 1))
		properties.initialFocus = child.name;
}

CCoeControlChildListener.prototype.childRemoved = function(instance, child, laf) {
	// nothing to do
}

CCoeControlChildListener.prototype.childrenReordered = function(instance, laf) {
	// nothing to do
}

//////////////////////////////////////////
// IComponentEventInfo
//////////////////////////////////////////
function CCoeControlEventInfo(){}

CCoeControlEventInfo.prototype.getEventGroups = function(instance) {
	// Get the proper events depending on the SDK.
	version = getComponentVersions();
	if (version.major >= 5) {
		// Add touch events
		return ["TouchUIEvent", "CustomCCoeControl", "CCoeControl"];
	}
	else {
		// sdks prior to touch
		return ["CustomCCoeControl", "CCoeControl"];
	}
}
