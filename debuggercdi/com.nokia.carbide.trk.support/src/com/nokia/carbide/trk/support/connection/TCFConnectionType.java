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
 * Implementation of IConnectionType for TCP/IP connections
 */
public class TCFConnectionType implements IConnectionType {

	public static final String ID = "com.nokia.carbide.connection.TCFConnectionType"; //$NON-NLS-1$

	public IConnectionFactory getConnectionFactory() {
		return new TCFConnectionFactory(this);
	}

	public String getDescription() {
		return Messages.getString("TCFConnectionType.Desc"); //$NON-NLS-1$
	}

	public String getDisplayName() {
		return Messages.getString("TCFConnectionType.Label"); //$NON-NLS-1$
	}

	public String getHelpContext() {
		return null;
	}

	public String getIdentifier() {
		return ID;
	}

}
