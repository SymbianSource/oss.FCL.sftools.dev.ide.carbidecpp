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
*
*/


function ControlBaseEventInfo() {
}

/** Only return a subset of events when a control is in a setting item list */
ControlBaseEventInfo.prototype.getEventGroups = function(instance) {
	if (instance.componentId == "com.nokia.carbide.uiq.CQikContainer")
		return ["Container"];
	else
		return ["Control"];
}

/** Only return a subset of events when a control is in a setting item list */
ControlBaseEventInfo.prototype.getDefaultEventName = function(instance) {
	return null;	// select component default
}
