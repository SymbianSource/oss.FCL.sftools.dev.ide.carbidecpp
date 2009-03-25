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


function FilteredEvents() {
}

FilteredEvents.prototype.getEventGroups = function(instance) {
	if (instance.parent.componentId == "com.nokia.examples.specialContainer") {
		return ["special"];
	}
	return null;
}
FilteredEvents.prototype.getDefaultEventName = function(instance) {
	if (instance.parent.componentId == "com.nokia.examples.specialContainer") {
		return "com.nokia.examples.baseComponent.specialEvent";
	}
	return "com.nokia.examples.baseComponent.somethingHappened";
}
