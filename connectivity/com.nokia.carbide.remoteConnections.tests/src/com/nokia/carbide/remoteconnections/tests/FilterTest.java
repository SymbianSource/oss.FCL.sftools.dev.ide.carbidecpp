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
import com.nokia.carbide.remoteconnections.internal.registry.Registry;
import com.nokia.carbide.remoteconnections.tests.extensions.*;

import java.util.Collection;

import junit.framework.TestCase;


/**
 *
 */
public class FilterTest extends TestCase {

	private static final String INTERVAL_CONN_ID = IntervalConnectionType.class.getName();
	private static final String NOSETT_CONN_ID = NoSettingsConnectionType.class.getName();
	private static final String UNKNOWN_SERV_ID = UnknownStatusService.class.getName();
	private static final String RANDOM_SERV_ID = RandomCycleService.class.getName();

	@Override
	protected void setUp() throws Exception {
		TestFilter.isTest = true;
		TestFilter.reset();
	}

	@Override
	protected void tearDown() throws Exception {
	}
	
	private boolean connectionTypeExists(String id) {
		IConnectionTypeProvider ctp = RemoteConnectionsActivator.getConnectionTypeProvider();
		IConnectionType connectionType = ctp.getConnectionType(id);
		return connectionType != null;
	}

	private boolean serviceExists(String id) {
		Collection<IService> services = Registry.instance().getServices();
		return serviceListContainsId(services, id);
	}

	private boolean serviceListContainsId(Collection<IService> services, String id) {
		for (IService service : services) {
			if (service.getIdentifier().equals(id))
				return true;
		}
		
		return false;
	}
	
	private boolean connectedServiceExists(String connId, String serviceId) {
		IConnectionTypeProvider ctp = RemoteConnectionsActivator.getConnectionTypeProvider();
		IConnectionType connectionType = ctp.getConnectionType(connId);
		if (connectionType != null) {
			Collection<IService> compatibleServices = ctp.getCompatibleServices(connectionType);
			return serviceListContainsId(compatibleServices, serviceId);
		}
		
		return false;
	}
	
	public void testNoFilter() throws Exception {
		Registry.instance().loadExtensions();
		assertTrue(connectionTypeExists(INTERVAL_CONN_ID));
		assertTrue(connectionTypeExists(NOSETT_CONN_ID));
		assertTrue(serviceExists(UNKNOWN_SERV_ID));
		assertTrue(serviceExists(RANDOM_SERV_ID));
		assertTrue(connectedServiceExists(INTERVAL_CONN_ID, UNKNOWN_SERV_ID));
		assertTrue(connectedServiceExists(INTERVAL_CONN_ID, RANDOM_SERV_ID));
		assertTrue(connectedServiceExists(NOSETT_CONN_ID, UNKNOWN_SERV_ID));
	}
	
	public void testFilterIntervalConnectionType() throws Exception {
		TestFilter.addConnectionTypeId(INTERVAL_CONN_ID);
		Registry.instance().loadExtensions();
		assertFalse(connectionTypeExists(INTERVAL_CONN_ID));
		assertTrue(connectionTypeExists(NOSETT_CONN_ID));
		assertTrue(serviceExists(UNKNOWN_SERV_ID));
		assertTrue(serviceExists(RANDOM_SERV_ID));
		assertFalse(connectedServiceExists(INTERVAL_CONN_ID, UNKNOWN_SERV_ID));
		assertFalse(connectedServiceExists(INTERVAL_CONN_ID, RANDOM_SERV_ID));
		assertTrue(connectedServiceExists(NOSETT_CONN_ID, UNKNOWN_SERV_ID));
	}
	
	public void testFilterBothConnectionTypes() throws Exception {
		TestFilter.addConnectionTypeId(INTERVAL_CONN_ID);
		TestFilter.addConnectionTypeId(NOSETT_CONN_ID);
		Registry.instance().loadExtensions();
		assertFalse(connectionTypeExists(INTERVAL_CONN_ID));
		assertFalse(connectionTypeExists(NOSETT_CONN_ID));
		assertTrue(serviceExists(UNKNOWN_SERV_ID));
		assertTrue(serviceExists(RANDOM_SERV_ID));
		assertFalse(connectedServiceExists(INTERVAL_CONN_ID, UNKNOWN_SERV_ID));
		assertFalse(connectedServiceExists(INTERVAL_CONN_ID, RANDOM_SERV_ID));
		assertFalse(connectedServiceExists(NOSETT_CONN_ID, UNKNOWN_SERV_ID));
	}
	
	public void testFilterUnknownStatusService() throws Exception {
		TestFilter.addServiceId(UNKNOWN_SERV_ID);
		Registry.instance().loadExtensions();
		assertTrue(connectionTypeExists(INTERVAL_CONN_ID));
		assertTrue(connectionTypeExists(NOSETT_CONN_ID));
		assertFalse(serviceExists(UNKNOWN_SERV_ID));
		assertTrue(serviceExists(RANDOM_SERV_ID));
		assertFalse(connectedServiceExists(INTERVAL_CONN_ID, UNKNOWN_SERV_ID));
		assertTrue(connectedServiceExists(INTERVAL_CONN_ID, RANDOM_SERV_ID));
		assertFalse(connectedServiceExists(NOSETT_CONN_ID, UNKNOWN_SERV_ID));
	}
	
	public void testFilterBothServices() throws Exception {
		TestFilter.addServiceId(UNKNOWN_SERV_ID);
		TestFilter.addServiceId(RANDOM_SERV_ID);
		Registry.instance().loadExtensions();
		assertTrue(connectionTypeExists(INTERVAL_CONN_ID));
		assertTrue(connectionTypeExists(NOSETT_CONN_ID));
		assertFalse(serviceExists(UNKNOWN_SERV_ID));
		assertFalse(serviceExists(RANDOM_SERV_ID));
		assertFalse(connectedServiceExists(INTERVAL_CONN_ID, UNKNOWN_SERV_ID));
		assertFalse(connectedServiceExists(INTERVAL_CONN_ID, RANDOM_SERV_ID));
		assertFalse(connectedServiceExists(NOSETT_CONN_ID, UNKNOWN_SERV_ID));
	}
	
	public void testFilterRandomCycleOnIntervalConnection() throws Exception {
		TestFilter.addConnectedServiceIdPair(INTERVAL_CONN_ID, UNKNOWN_SERV_ID);
		Registry.instance().loadExtensions();
		assertTrue(connectionTypeExists(INTERVAL_CONN_ID));
		assertTrue(connectionTypeExists(NOSETT_CONN_ID));
		assertTrue(serviceExists(UNKNOWN_SERV_ID));
		assertTrue(serviceExists(RANDOM_SERV_ID));
		assertFalse(connectedServiceExists(INTERVAL_CONN_ID, UNKNOWN_SERV_ID));
		assertTrue(connectedServiceExists(INTERVAL_CONN_ID, RANDOM_SERV_ID));
		assertTrue(connectedServiceExists(NOSETT_CONN_ID, UNKNOWN_SERV_ID));
	}
}
