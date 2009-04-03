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

function LayoutControlBase() {
	
}

////////////////////////////////////////////////////////////////////////////////
// IPropertyExtenders

LayoutControlBase.prototype.getPropertyExtenders = function(instance, targetInstance) {
	/* Extend with "default" extension set, if the target instance
	 * is a sibling of the (layout manager) instance and the target instance
	 * has the attribute is-qikcontainer-content="true" */
//	println("LayoutControlBase.getPropertyExtenders instance: " + instance);
//	println("LayoutControlBase.getPropertyExtenders targetInstance: " + targetInstance);
	var targetInstanceParent = targetInstance.parent;
	if ( targetInstanceParent != null ) {
		targetInstanceParentChildren = targetInstanceParent.children;
		if ( targetInstanceParentChildren != null ) {
			layoutManager = findImmediateChildByAttributeValue(targetInstanceParentChildren, "is-layout-manager", "true");
			if ((layoutManager != null) && (targetInstance.attributes["is-qikcontainer-content"] == "true")) {
				return [ layoutManager ];
			}
		}
	}
	
	return null;
}

LayoutControlBase.prototype.getExtensionSetNames = function(instance, targetInstance) {
	return [ "default" ];
}
