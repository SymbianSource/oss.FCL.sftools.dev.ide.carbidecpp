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
function ContainerExtPropertyExtender() {
}

	// Return instances that may provide extension properties
	// The target instance parameter is the instance to receive the
	// additional properties
ContainerExtPropertyExtender.prototype.getPropertyExtenders = function(instance, targetInstance) {
	var result = null;
	if (targetInstance == instance) {
		result = [instance.parent];
	}
	else if (targetInstance.parent == instance) {
		// Add container's extension properties and those of a known child component, if present
		var extendingInstance = findImmediateChildByComponentID(instance.children, "com.nokia.examples.extensionChild");
		if (extendingInstance == null)
			extendingInstance = findImmediateChildByComponentID(instance.children, "com.nokia.examples.extensionChildMultiple");
		if (targetInstance != extendingInstance) {
			result = [instance];
			if (extendingInstance != null) {
				result[1] = extendingInstance;
			}
		}
	}
	return result;
}
	
ContainerExtPropertyExtender.prototype.getExtensionSetNames = function(thisInstance, targetInstance) {
	if (thisInstance.properties.controlConditionalExtensions)
		return [ "default", "extra"];
	else 
		return [ "default" ];
}

function findImmediateChildByComponentID(children, componentID) {
    var result = null;
	for (var i in children) {
		if (children[i].componentId == componentID) {
			result = children[i];
			break;
		}
	}
	return result;
}

ContainerExtPropertyExtender.prototype.propertyChanged = function(instance, propertyID, laf) {
	if (propertyID == "controlConditionalExtensions" && instance.children != null) {
	    var children = instance.children;
	    for (var i in children) {
	    	var child = children[i];
	    	child.updatePropertySource();
	    }
	}
}