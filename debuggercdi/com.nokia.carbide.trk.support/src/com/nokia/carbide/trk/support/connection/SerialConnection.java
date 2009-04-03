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

import com.freescale.cdt.debug.cw.core.SerialConnectionSettings;
import com.nokia.carbide.remoteconnections.interfaces.*;

import java.util.*;

/**
 * An implementation of IConnection for serial connections
 */
public class SerialConnection extends AbstractSynchronizedConnection {
	private static Map<String, Integer> portsInUse = new HashMap<String, Integer>();

	public SerialConnection(IConnectionType connectionType, Map<String, String> settings) {
		super(connectionType, settings);
	}
	
	private String getPort() {
		return getSettings().get(SerialConnectionSettings.PORT);
	}

	protected String getCurrentResourceString() {
		return getPort();
	}

	protected Map<String, Integer> getResourcesMap() {
		return portsInUse;
	}
	
}
