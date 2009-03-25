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


function CQikListBox() {
}

////////////////////////////////////////////////////////////////////////////////
// IComponentValidator

CQikListBox.prototype.validate = function(instance, laf) {
	var properties = instance.properties;
    if (!instance.isInstanceOf("com.nokia.carbide.uiq.CQikListBox"))
			return null;
	if (properties == null) {
		return null;
	}
	if (properties.type == "EQikRowListBox") {
		if (properties.grid_columns != "-1" ||
				properties.grid_rows != "-1" ||
				properties.scroll_direction != "EQikVerticalDirection") {
			var fs = formatString(lookupString("validate.type"), [ instance.name, instance.component.friendlyName, properties.type ]);
			fs += lookupString("validate.type.ignoredProperties1");
			var warning = newModelMessage(IStatus.WARNING, fs, instance, null, null);
			return [ warning ];
		}
	} else {
		if (properties.type == "EQikGridListBox") {
			if (properties.centered_scrolling != "EQikListBoxSystem" ||
					properties.center_value != "-1" ||
					properties.partially_visible_items != "EQikListBoxSystem") {
				var fs = formatString(lookupString("validate.type"), [ instance.name,instance.component.friendlyName, properties.type ]);
				fs += lookupString("validate.type.ignoredProperties2");
				var warning = newModelMessage(IStatus.WARNING, fs, instance, null, null);
				return [ warning ];
			}
		}
	}
	return null;
}

CQikListBox.prototype.queryPropertyChange = function(instance, propertyPath, newValue, laf) {
	if (propertyPath == "incremental_match_slot" && newValue != "EQikListBoxDefault") {
		if (!isValidTextSlot(instance, newValue)) {
			return formatString(lookupString("validate.incremental_match_slot"),
					[ newValue, instance.name ]);
		}
	}
	if ( (propertyPath == "height_in_rows" || 
			propertyPath == "grid_columns" || 
			propertyPath == "grid_rows") && newValue == "0") {
		return lookupString("validate.dimmensionProperty");
	}
	return null;
}

function isValidTextSlot(instance, newValue) {
	//Get layouts
	var layoutsGroup = instance.findChildOfType("com.nokia.carbide.uiq.ListBoxLayoutGroup");
	var layouts = layoutsGroup.children;
	//Collect text slots provided by any of the layouts
	var textSlots = new java.util.ArrayList();
	for (var i = 0; i < layouts.length; i++) {
		var layout = layouts[i];
		var standardNormalLayout = layout.properties.standard_normal_layout;
		switch (standardNormalLayout) {
			case "EQikListBoxTwoLines":
			case "EQikListBoxIconTwoLines":
			case "EQikListBoxMediumIconTwoLines":
				if (!textSlots.contains("EQikListBoxSlotText2")) {
					textSlots.add("EQikListBoxSlotText2");
				}
			case "EQikListBoxLine":
			case "EQikListBoxIconLine":
			case "EQikListBoxLineIcon":
				if (!textSlots.contains("EQikListBoxSlotText1")) {
					textSlots.add("EQikListBoxSlotText1");
				}
				break;
			default:
				break;
		}
	}
	return textSlots.contains(newValue);
}
