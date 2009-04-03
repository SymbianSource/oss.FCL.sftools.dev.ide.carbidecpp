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


function RowLayoutManager() {
	
}

////////////////////////////////////////////////////////////////////////////////
// IPropertyExtenders

RowLayoutManager.prototype.getPropertyExtenders = function(instance, targetInstance) {
	/* TODO: Extend with "default" extension set, if the target instance
	 * is a sibling of the (layout manager) instance and the target instance
	 * has the attribute is-qikcontainer-content="true" */
//	println("RowLayoutManager.getPropertyExtenders instance: " + instance);
//	println("RowLayoutManager.getPropertyExtenders targetInstance: " + targetInstance);
	var siblings = targetInstance.parent.children;
	if (siblings != null)
	{
//		for (var index=0; index < siblings.length; index++)
//		{
//			println("RowLayoutManager.getPropertyExtenders sibling[" + index + "] : " + siblings[index]);
//		}
		if ((targetInstance.parent == instance.parent) && (targetInstance.attributes["is-qikcontainer-content"] == "true")) {
			return [ instance ];
		}
	}
	return null
}

RowLayoutManager.prototype.getExtensionSetNames = function(instance, targetInstance) {
	return [ "default" ];
}

////////////////////////////////////////////////////////////////////////////////
// IComponentInstancePropertyListener

RowLayoutManager.prototype.propertyChanged = function(instance, propertyId) {
//	println("RowLayoutManager.propertyChanged instance: " + instance);
//	println("RowLayoutManager.propertyChanged propertyId: " + propertyId);
	if (propertyId == "mirrored" || propertyId == "normalizeRowHeights" || propertyId == "defaultLayoutData") {
		instance.forceLayout();
	}
}
