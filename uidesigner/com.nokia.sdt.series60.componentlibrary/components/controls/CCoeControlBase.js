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


include("../settingsListLibrary.js")

function CCoeControlBaseEventInfo() {
}

/** Only return a subset of events when a control is in a setting item list */
CCoeControlBaseEventInfo.prototype.getEventGroups = function(instance) {
	if (isSettingItemList(instance.parent))
		return ["SettingsList"];
	else if (instance.parent.attributes["is-transient-object"] == "true")
		return []; // no events
	else
		return ["CCoeControl"];
}

/** Only return a subset of events when a control is in a setting item list */
CCoeControlBaseEventInfo.prototype.getDefaultEventName = function(instance) {
	if (isSettingItemList(instance.parent))
		return "editingStopped";
	else
		return null;	// select component default
}
