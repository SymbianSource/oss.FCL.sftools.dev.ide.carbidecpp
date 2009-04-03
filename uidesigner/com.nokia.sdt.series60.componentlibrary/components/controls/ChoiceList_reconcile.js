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

var kYes = "Yes";
var kNo = "No";


function ChoiceListReconcile() {
}

ChoiceListReconcile.prototype.createDisplayValue = function(instance, propertyTypeName, propertyValue) {
	if (!propertyTypeName.equals("com.nokia.sdt.series60.ButtonData")) {
		return null;
	}
	
	if (propertyValue.showAsButtonValue == true){
		return kYes;
	}
	else {
		return kNo;
	}
}

ChoiceListReconcile.prototype.isDisplayValueEditable = function(instance, propertyTypeName) {
	if (!propertyTypeName.equals("com.nokia.sdt.series60.ButtonData")) {
		return true;
	}
	
	return true;
}
	
ChoiceListReconcile.prototype.applyDisplayValue = function(instance, propertyTypeName, displayValue, propertyValue) {
	if (!propertyTypeName.equals("com.nokia.sdt.series60.ButtonData")) {
		return;
	}
	
	if (displayValue == kYes){
		propertyValue.showAsButtonValue = true;
	} else { 
		propertyValue.showAsButtonValue = false;
	}
}
