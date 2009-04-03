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
include("../formLibrary.js")

function CAknAppUIpropertyExtender() {
}

// Return instances that may provide extension properties
// The target instance parameter is the instance to receive the
// additional properties
CAknAppUIpropertyExtender.prototype.getPropertyExtenders = function(instance, targetInstance) {
	for (var i in targetInstance.children) {
		
		if (isForm(targetInstance.children[i])) {
			return null;
		}
	}
	return [instance];
}
	
CAknAppUIpropertyExtender.prototype.getExtensionSetNames = function(instance, targetInstance) {
	if (instance == targetInstance && hasMSKSupport()){
			return [ "default" ];
	}
	return null;
}

function hasMSKSupport(){
	var version = getComponentVersions();
	if (version.getMajor() > 3 || (version.getMajor() == 3 && version.getMinor() >= 2)){
		return true;
	}
	
	return false;
}
