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
import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;
import com.nokia.carbide.trk.support.Messages;

/**
 * Implementation of IConnectionType for USB over TC framework connections
 */
public class USBConnectionType implements IConnectionType {

	public static final String ID = USBConnectionType.class.getName();

	public IConnectionFactory getConnectionFactory() {
		return new SerialConnectionFactory(this, true);
	}

	public String getDescription() {
		return Messages.getString("USBConnectionType.Desc"); //$NON-NLS-1$
	}

	public String getDisplayName() {
		return Messages.getString("USBConnectionType.Label"); //$NON-NLS-1$
	}

	public String getHelpContext() {
		return null;
	}

	public String getIdentifier() {
		return ID;
	}

}
