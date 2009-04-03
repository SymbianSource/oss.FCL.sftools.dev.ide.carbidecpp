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


package com.nokia.carbide.remoteconnections.tests;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.*;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionsManagerListener;
import com.nokia.carbide.remoteconnections.internal.registry.Registry;
import com.nokia.carbide.remoteconnections.tests.extensions.*;

import java.util.Collection;
import java.util.Map;

import junit.framework.TestCase;

/**
 *
 */
public class RegistryTest extends TestCase {
	
	protected void setUp() throws Exception {
		super.setUp();
		TestFilter.isTest = true;
		Registry.instance().loadExtensions();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testTestExtensionsLoaded() {
		IConnectionType ct = 
			RemoteConnectionsActivator.getConnectionTypeProvider().getConnectionType(
					IntervalConnectionType.class.getName());
		assertNotNull(ct);
		
		IConnectionFactory cf = ct.getConnectionFactory();
		assertNotNull(cf);
		
		IConnection conn = cf.createConnection(cf.getSettingsFromUI());
		ct = conn.getConnectionType();
		Collection<IService> services = RemoteConnectionsActivator.getConnectionTypeProvider().getCompatibleServices(ct);
		assertEquals(2, services.size());
		
		IService service = services.iterator().next();
		assertEquals(RandomCycleService.class.getName(), service.getIdentifier());
		
		Collection<String> ctids = Registry.instance().getCompatibleConnectionTypeIds(service);
		String id = ctids.iterator().next();
		assertEquals(ct.getIdentifier(), id);
	}
	
	public void testStoreAndLoadConnections() {
		Registry.instance().disposeConnections();
		Collection<IConnection> connections = RemoteConnectionsActivator.getConnectionsManager().getConnections();
		assertTrue(connections.isEmpty());
		
		IConnectionType ct = 
			RemoteConnectionsActivator.getConnectionTypeProvider().getConnectionType(
					IntervalConnectionType.class.getName());
		assertNotNull(ct);
		IConnectionFactory cf = ct.getConnectionFactory();
		assertNotNull(cf);
		IConnection connection = cf.createConnection(cf.getSettingsFromUI());
		connection.setIdentifier("test 1");
		RemoteConnectionsActivator.getConnectionsManager().addConnection(connection);
		
		connections = RemoteConnectionsActivator.getConnectionsManager().getConnections();
		assertEquals(1, connections.size());
		RemoteConnectionsActivator.getConnectionsManager().storeConnections();
		
		RemoteConnectionsActivator.getConnectionsManager().removeConnection(connection);
		connections = RemoteConnectionsActivator.getConnectionsManager().getConnections();
		assertTrue(connections.isEmpty());
		
		RemoteConnectionsActivator.getConnectionsManager().loadConnections();
		connections = RemoteConnectionsActivator.getConnectionsManager().getConnections();
		assertEquals(1, connections.size());
		
		connection = connections.iterator().next();
		assertEquals(IntervalConnectionType.class.getName(), connection.getConnectionType().getIdentifier());
		
		Map<String, String> settings = connection.getSettings();
		assertEquals(1, settings.size());
		
		String value = settings.get(IntervalConnectionType.KEY);
		assertEquals(IntervalConnectionType.VALUE, value);
	}

	public void testConnectionStoreListener() {
		Registry.instance().disposeConnections();
		IConnectionType ct = 
			RemoteConnectionsActivator.getConnectionTypeProvider().getConnectionType(
					IntervalConnectionType.class.getName());
		assertNotNull(ct);
		IConnectionFactory cf = ct.getConnectionFactory();
		assertNotNull(cf);
		IConnection connection = cf.createConnection(cf.getSettingsFromUI());
		connection.setIdentifier("test 1");
		
		final boolean[] listenerCalled = new boolean[] { false };
		IConnectionsManagerListener listener = new IConnectionsManagerListener() {
			public void connectionStoreChanged() {
				listenerCalled[0] = true;
			}

			public void displayChanged() {}
		};
		RemoteConnectionsActivator.getConnectionsManager().addConnectionStoreChangedListener(listener);
		RemoteConnectionsActivator.getConnectionsManager().addConnection(connection);
		assertTrue(listenerCalled[0]);
		
		listenerCalled[0] = false;
		RemoteConnectionsActivator.getConnectionsManager().removeConnection(connection);
		assertTrue(listenerCalled[0]);

		RemoteConnectionsActivator.getConnectionsManager().removeConnectionStoreChangedListener(listener);
		listenerCalled[0] = false;
		RemoteConnectionsActivator.getConnectionsManager().addConnection(connection);
		assertFalse(listenerCalled[0]);
		RemoteConnectionsActivator.getConnectionsManager().removeConnection(connection);
		assertFalse(listenerCalled[0]);
	}
	
	public void testUniqueIdentifier() {
		Registry.instance().disposeConnections();
		IConnectionType ct = 
			RemoteConnectionsActivator.getConnectionTypeProvider().getConnectionType(
					IntervalConnectionType.class.getName());
		assertNotNull(ct);
		IConnectionFactory cf = ct.getConnectionFactory();
		assertNotNull(cf);
		IConnection connection = cf.createConnection(cf.getSettingsFromUI());
		connection.setDisplayName("foo");
		RemoteConnectionsActivator.getConnectionsManager().addConnection(connection);
		String id = connection.getIdentifier();
		connection = cf.createConnection(cf.getSettingsFromUI());
		connection.setIdentifier(id);
		connection.setDisplayName("foo");
		RemoteConnectionsActivator.getConnectionsManager().addConnection(connection);
		assertFalse(id.equals(connection.getIdentifier()));
	}

	public void testUniqueDisplayName() {
		Registry.instance().disposeConnections();
		IConnectionType ct = 
			RemoteConnectionsActivator.getConnectionTypeProvider().getConnectionType(
					IntervalConnectionType.class.getName());
		assertNotNull(ct);
		IConnectionFactory cf = ct.getConnectionFactory();
		assertNotNull(cf);
		IConnection connection = cf.createConnection(cf.getSettingsFromUI());
		connection.setDisplayName("foo");
		RemoteConnectionsActivator.getConnectionsManager().addConnection(connection);
		assertEquals("foo", connection.getDisplayName());
		connection = cf.createConnection(cf.getSettingsFromUI());
		connection.setDisplayName("foo");
		RemoteConnectionsActivator.getConnectionsManager().addConnection(connection);
		assertFalse("foo".equals(connection.getIdentifier()));
	}
	
}
