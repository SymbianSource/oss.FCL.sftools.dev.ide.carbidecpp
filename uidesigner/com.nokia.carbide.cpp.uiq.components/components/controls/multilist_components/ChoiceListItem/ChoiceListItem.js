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

include("../../../containerLibrary.js")
include("../../../renderLibrary.js")
include("../../../implLibrary.js")


function ChoiceListItem() {
}


////////////////////////////////////////////////////////////////////////////////
//IPropertyListener

ChoiceListItem.prototype.propertyChanged = function(instance, property) {
	if (property == "label"){
		instance.parent.properties.label = instance.properties.label;
		vieworsimpledialog = searchViewOrSimpleDialog(instance);
		vieworsimpledialog.forceRedraw();
	}
}


function searchViewOrSimpleDialog(instance) {

    var VIEW_ID = "com.nokia.carbide.uiq.CQikView";

    var SIMPLE_DIALOG_ID = "com.nokia.carbide.uiq.CQikSimpleDialog";
    
    if (instance.attributes["ui-configuration-group-component-id"] != null
&& instance.attributes["ui-configuration-group-component-id"].length > 0) {
          if (instance.component.id == VIEW_ID || instance.component.id == SIMPLE_DIALOG_ID) {
                return instance;
          }      
    } else if (instance.parent != null) {
          return searchViewOrSimpleDialog(instance.parent);
    } else {
          return null;
    }
}

