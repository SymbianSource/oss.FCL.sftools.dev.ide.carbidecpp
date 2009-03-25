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


include("../../implLibrary.js")
include("../../containerLibrary.js")

function CQikContainer() {
}

////////////////////////////////////////////////////////////////////////////////
// IComponentValidator

CQikContainer.prototype.validate = function(instance) {
	if (instance != null || instance != "") {
		var properties = instance.properties;
		if (properties != null || properties != "") {
			var isScrollable = properties.scrollable;
			if (isScrollable != null || isScrollable != "") {
				if (isScrollable == "EQikCtScrollableContainer") {
					if (properties.flags.handleRelayoutRequests == true ||
							properties.flags.persistsAfterRelayout == true ||
							properties.flags.verticalWrappingEnabled == true ||
							properties.flags.scrollHorizontalOff == true ||
							properties.flags.scrollHorizontalNormal == true ||
							properties.flags.scrollVerticalAuto == true ||
							properties.flags.scrollVerticalNormal == true) {
						var modelMessage = newModelMessage(IStatus.WARNING,
								formatString(lookupString("validate.flags1"),
								[instance.name ]),
								instance, "flags", null);
							return [ modelMessage ];
					}
				}
			}
		}
		if (instance.parent.isInstanceOf("com.nokia.carbide.uiq.CQikContainer")&& instance.properties.scrollable=="EQikCtScrollableContainer" ){
			var modelMessage = newModelMessage(IStatus.WARNING,formatString(lookupString("validate.flags2"),
					[instance.name ]),instance, "flags", null);
			return [ modelMessage ];											
		}
	}
	return null;
}

CQikContainer.prototype.queryPropertyChange = function(instance, propertyId, newValue) {
	return null;
}

////////////////////////////////////////////////////////////////////////////////
// IPropertyExtenders

CQikContainer.prototype.getPropertyExtenders = function(instance, targetInstance) {
//	println("CQikContainer.getPropertyExtenders instance: " + instance);
//	println("CQikContainer.getPropertyExtenders targetInstance: " + targetInstance);
	if ((targetInstance == instance) && (targetInstance.parent.attributes != null) && (targetInstance.parent.attributes["is-qikcontainer"] == "true")) {
		var targetInstanceParent = targetInstance.parent;
		if ( targetInstanceParent != null ) {
			targetInstanceParentChildren = targetInstanceParent.children;
			if ( targetInstanceParentChildren != null ) {
				layoutManager = findImmediateChildByAttributeValue(targetInstanceParentChildren, "is-layout-manager", "true");
				if ((layoutManager != null) && (targetInstance.attributes["is-qikcontainer-content"] == "true")) {
					return [ instance, layoutManager ];
				}
				else {
					return [ instance ];
				}
			}
		}
	}
	
	return null;
}

CQikContainer.prototype.getExtensionSetNames = function(instance, targetInstance) {
	return [ "default" ];
}


