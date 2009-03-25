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


include("../containers/containerLibrary.js")

function SystemMenuItem() {
}


// IQueryContainment

function canContainComponent(instance, component) {
	return buildSimpleContainmentErrorStatus(
			lookupString("systemMenuItemContainmentErr"), []);			
}

SystemMenuItem.prototype.canContainComponent = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent);
}

SystemMenuItem.prototype.canContainChild = function(instance, child) {
	return canContainComponent(instance, child.component);
}

SystemMenuItem.prototype.canRemoveChild = function(instance) {
	return false;
}

SystemMenuItem.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return false;
}

