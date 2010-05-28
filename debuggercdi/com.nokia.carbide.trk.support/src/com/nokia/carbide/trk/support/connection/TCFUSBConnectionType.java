/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
 * Implementation of IConnectionType for USB TCF connections.  This also uses Nokia TCF but 
 * is given a unique identifier for now so we can debug it differently.
 */
public class TCFUSBConnectionType implements IUSBConnectionType {

	public static final String ID = "com.nokia.carbide.connection.TCFUSBConnectionType"; //$NON-NLS-1$

	public IConnectionFactory getConnectionFactory() {
		return new SerialConnectionFactory(this, true);
	}

	public String getDescription() {
		return Messages.getString("TCFUSBConnectionType.Desc"); //$NON-NLS-1$
	}

	public String getDisplayName() {
		return Messages.getString("TCFUSBConnectionType.Label"); //$NON-NLS-1$
	}

	public String getHelpContext() {
		return null;
	}

	public String getIdentifier() {
		return ID;
	}

}
