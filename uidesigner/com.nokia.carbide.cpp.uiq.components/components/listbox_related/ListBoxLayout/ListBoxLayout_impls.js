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
* START_USECASES: CU13 END_USECASES
*
*
*/


function ListBoxLayout() {
}

////////////////////////////////////////////////////////////////////////////////
// IComponentInstancePropertyListener

ListBoxLayout.prototype.propertyChanged = function(instance, propertyId) {
//	println("ListBoxLayout.prototype.propertyChanged instance: " + instance);
//	println("ListBoxLayout.prototype.propertyChanged propertyId: " + propertyId);
	if (propertyId == "standard_normal_layout" || propertyId == "standard_highlight_layout") {
		var items = instance.parent.parent.children;
		for (var i = 0; i < items.length; i++) {
			items[i].updatePropertySource();
			items[i].forceLayout();
			items[i].forceRedraw();
		}
		instance.parent.parent.forceLayout();
	}
}

////////////////////////////////////////////////////////////////////////////////
// IComponentValidator

ListBoxLayout.prototype.validate = function(instance, laf) {
	var properties = instance.properties;
	if (properties.standard_normal_layout == "EQikListBoxNoLayout") {
		var fs = formatString(lookupString("validate.standard_normal_layout"), [ instance.name ]);
		var warning = newModelMessage(IStatus.WARNING, fs, instance, null, null);
		return [ warning ];
	}
	return null;
}

ListBoxLayout.prototype.queryPropertyChange = function(instance, propertyPath, newValue, laf) {
	return null;
}
