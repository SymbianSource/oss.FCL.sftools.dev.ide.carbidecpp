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

package com.nokia.carbide.remoteconnections.tests;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.*;
import com.nokia.carbide.remoteconnections.tests.extensions.DefaultProvidingTCPIPService;
import com.nokia.carbide.remoteconnections.tests.extensions.TestFilter;
import com.nokia.carbide.trk.support.connection.TCPIPConnectionType;

import java.util.Map;

import junit.framework.TestCase;

@SuppressWarnings("restriction")
public class TCPIPConnectionTypeTests extends TestCase {

	private static IConnectionType connectionType;

	protected void setUp() throws Exception {
		TestFilter.isTest = true;
		IConnectionTypeProvider connectionTypeProvider = RemoteConnectionsActivator.getConnectionTypeProvider();
		if (connectionType == null)
			connectionType = connectionTypeProvider.getConnectionType(TCPIPConnectionType.ID);
	}

	protected void tearDown() throws Exception {
	}

	public void testTCPIPDefaultMappings() throws Exception {
		Map<String, String> settings = connectionType.getConnectionFactory().getSettingsFromUI();
		IConnection connection = connectionType.getConnectionFactory().createConnection(settings);
		RemoteConnectionsActivator.getConnectionsManager().addConnection(connection);
		settings = connection.getSettings();
		String defaultPort = settings.get(DefaultProvidingTCPIPService.ID);
		assertEquals(DefaultProvidingTCPIPService.DEFAULT_PORT, defaultPort);
	}
}
