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
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatusChangedListener;
import com.nokia.carbide.remoteconnections.internal.registry.Registry;
import com.nokia.carbide.remoteconnections.tests.extensions.*;

import java.util.Collection;
import java.util.Map;

import junit.framework.TestCase;

/**
 *
 */
public class ServiceTest extends TestCase {

	private IntervalConnection connection;
	private RandomCycleConnectedService connectedService;
	
	protected void setUp() throws Exception {
		super.setUp();
		TestFilter.isTest = true;
		Registry.instance().loadExtensions();
		IConnectionType ct = RemoteConnectionsActivator.getConnectionTypeProvider().getConnectionType(IntervalConnectionType.class.getName());
		IConnectionFactory cf = ct.getConnectionFactory();
		assertNotNull(cf);
		Map<String, String> settings = cf.getSettingsFromUI();
		IConnection c = cf.createConnection(settings);
		assertTrue(c instanceof IntervalConnection);
		connection = (IntervalConnection) c;
		connection.setIdentifier("Connection 1");
		RemoteConnectionsActivator.getConnectionsManager().addConnection(connection);
		Collection<IService> services = RemoteConnectionsActivator.getConnectionTypeProvider().getCompatibleServices(ct);
		assertNotNull(services);
		assertFalse(services.isEmpty());
		assertEquals(2, services.size());
		IService service = services.iterator().next();
		IConnectedService s = Registry.instance().createConnectedService(service, connection);
		assertTrue(s instanceof RandomCycleConnectedService);
		connectedService = (RandomCycleConnectedService) s;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		RemoteConnectionsActivator.getConnectionsManager().removeConnection(connection);
		connectedService.dispose();
		connection.dispose();
	}
	
	public void testCycleStates() throws Exception {
		boolean isOK = testCycleForInterval(connection.getInterval());
		assertFalse(isOK);
		isOK = testCycleForInterval(connection.getInterval());
		assertTrue(isOK);
		isOK = testCycleForInterval(connection.getInterval());
		assertFalse(isOK);
	}
	
	private boolean testCycleForInterval(int interval) throws Exception {
		final boolean isOK[] = { false };
		connectedService.addStatusChangedListener(new IStatusChangedListener() {
			public void statusChanged(IStatus status) {
				if (status.getEStatus().equals(IStatus.EStatus.UP)) {
					isOK[0] = true;
				}
			}
		});
		if (connectedService.getService().isTestable())
			connectedService.testStatus();
		Thread.sleep(interval);
		return isOK[0];
	}
	
	public void testMultTestStatusNoDeadlock() {
		for (int i = 0; i < 1000; i++) {
			connectedService.testStatus();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void testGetStatusEquals() {
		connectedService.addStatusChangedListener(new IStatusChangedListener() {
			public void statusChanged(IStatus status) {
				assertEquals(connectedService.getStatus(), status);
				assertFalse(new RandomCycleConnectedService.TestStatus(status.getConnectedService(), 
						IStatus.EStatus.UNKNOWN).equals(status));
			}
		});
		connectedService.testStatus();
	}
}
