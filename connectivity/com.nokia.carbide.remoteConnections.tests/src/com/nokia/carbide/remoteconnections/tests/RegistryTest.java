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
		assertTrue(ctids.contains(ct.getIdentifier()));
	}
	
	public void testStoreAndLoadConnections() {
		Registry.instance().disposeConnections();
		Collection<IConnection> connections = Registry.instance().getConnections();
		assertTrue(connections.isEmpty());
		
		IConnectionType ct = 
			RemoteConnectionsActivator.getConnectionTypeProvider().getConnectionType(
					IntervalConnectionType.class.getName());
		assertNotNull(ct);
		IConnectionFactory cf = ct.getConnectionFactory();
		assertNotNull(cf);
		IConnection connection = cf.createConnection(cf.getSettingsFromUI());
		connection.setIdentifier("test 1");
		Registry.instance().addConnection(connection);
		
		connections = Registry.instance().getConnections();
		assertEquals(1, connections.size());
		Registry.instance().storeConnections();
		
		Registry.instance().removeConnection(connection);
		connections = Registry.instance().getConnections();
		assertTrue(connections.isEmpty());
		
		Registry.instance().loadConnections();
		connections = Registry.instance().getConnections();
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
		Registry.instance().addConnectionStoreChangedListener(listener);
		Registry.instance().addConnection(connection);
		assertTrue(listenerCalled[0]);
		
		listenerCalled[0] = false;
		Registry.instance().removeConnection(connection);
		assertTrue(listenerCalled[0]);

		Registry.instance().removeConnectionStoreChangedListener(listener);
		listenerCalled[0] = false;
		Registry.instance().addConnection(connection);
		assertFalse(listenerCalled[0]);
		Registry.instance().removeConnection(connection);
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
		Registry.instance().addConnection(connection);
		String id = connection.getIdentifier();
		connection = cf.createConnection(cf.getSettingsFromUI());
		connection.setIdentifier(id);
		connection.setDisplayName("foo");
		Registry.instance().addConnection(connection);
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
		Registry.instance().addConnection(connection);
		assertEquals("foo", connection.getDisplayName());
		connection = cf.createConnection(cf.getSettingsFromUI());
		connection.setDisplayName("foo");
		Registry.instance().addConnection(connection);
		assertFalse("foo".equals(connection.getIdentifier()));
	}
	
}
