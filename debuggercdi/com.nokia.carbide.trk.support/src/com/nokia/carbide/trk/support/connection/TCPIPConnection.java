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

import com.nokia.carbide.remoteconnections.interfaces.*;

import java.util.*;

/**
 * An implementation of IConnection for TCP/IP connections
 */
public class TCPIPConnection extends AbstractSynchronizedConnection {
	protected static Map<String, Integer> addressesInUse = new HashMap<String, Integer>();

	public TCPIPConnection(IConnectionType connectionType, Map<String, String> settings) {
		super(connectionType, new HashMap<String, String>(settings));
	}

	protected String getCurrentResourceString() {
		return getAddress();
	}

	protected Map<String, Integer> getResourcesMap() {
		return addressesInUse;
	}
	
	private String getAddress() {
		return getSettings().get(TCPIPConnectionFactory.IP_ADDRESS);
	}
	
}
