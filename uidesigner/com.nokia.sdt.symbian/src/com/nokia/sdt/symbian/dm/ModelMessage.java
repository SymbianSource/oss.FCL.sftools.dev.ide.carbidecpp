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

package com.nokia.sdt.symbian.dm;

import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.Message;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.sdt.datamodel.IModelMessage;

public class ModelMessage extends Message implements IModelMessage {

	private final String componentID;
	private final String instanceName;
	private final Object propertyID;
	private final Object eventID;

	public ModelMessage(int severity, MessageLocation location, 
			String messageKey, String message, String componentID,
			String instanceName, Object propertyID, 
			Object eventID) {
		super(severity, location, messageKey, message);
		this.componentID = componentID;
		this.instanceName = instanceName;
		this.propertyID = propertyID;
		this.eventID = eventID;
	}
	
	public ModelMessage(IMessage message, String componentId, String instanceName, 
			String propertyId, String eventID) {
		this(message.getSeverity(), message.getMessageLocation(),
				message.getMessageKey(), message.getMessage(),
				componentId, instanceName, propertyId, eventID);
	}

	public String getComponentID() {
		return componentID;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public Object getPropertyID() {
		return propertyID;
	}

	public Object getEventID() {
		return eventID;
	}
}
