/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.trk.support.connection;

import com.nokia.carbide.remoteconnections.interfaces.IConnectionFactory;
import com.nokia.carbide.trk.support.Messages;


/**
 * Implementation of IConnectionType for serial over Bluetooth connections
 */
public class SerialBTConnectionType extends SerialConnectionType {

	public static final String ID = SerialBTConnectionType.class.getName();

	public IConnectionFactory getConnectionFactory() {
		return new SerialConnectionFactory(this, true);
	}

	public String getDescription() {
		return Messages.getString("SerialBTConnectionType.Desc"); //$NON-NLS-1$
	}

	public String getDisplayName() {
		return Messages.getString("SerialBTConnectionType.Label"); //$NON-NLS-1$
	}

	public String getHelpContext() {
		return "com.nokia.carbide.cpp.debug.launch.trk_connection_bluetooth"; //$NON-NLS-1$
	}

	public String getIdentifier() {
		return ID;
	}
}
