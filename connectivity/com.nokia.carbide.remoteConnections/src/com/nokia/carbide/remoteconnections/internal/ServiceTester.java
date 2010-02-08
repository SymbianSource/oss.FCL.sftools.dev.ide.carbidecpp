/**
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

package com.nokia.carbide.remoteconnections.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.nokia.carbide.remoteconnections.interfaces.AbstractConnectedService2;
import com.nokia.carbide.remoteconnections.interfaces.AbstractSynchronizedConnection;
import com.nokia.carbide.remoteconnections.interfaces.AbstractConnectedService2.Status;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus;


public class ServiceTester {

	public class TestRunner extends Thread {
		private final Collection<Set<AbstractConnectedService2>> connectedServicesSets;

		public TestRunner(Collection<Set<AbstractConnectedService2>> connectedServicesSets) {
			this.connectedServicesSets = connectedServicesSets;
		}

		@Override
		public void run() {
			for (Set<AbstractConnectedService2> connectedServices : connectedServicesSets) {
				// test first in the set and set status for others
				Iterator<AbstractConnectedService2> iterator = connectedServices.iterator();
				AbstractConnectedService2 toTest = iterator.next();
				toTest.testStatus();
				IStatus status = toTest.getStatus();
				while (iterator.hasNext()) {
					AbstractConnectedService2 next = iterator.next();
					next.setStatus((Status) status);
				}
			}
			unregisterThread(this);
		}
		
		@Override
		public synchronized void start() {
			registerThread(this);
			super.start();
		}

		public boolean contains(AbstractConnectedService2 connectedService) {
			for (Set<AbstractConnectedService2> csSet : connectedServicesSets) {
				if (csSet.contains(connectedService))
					return true;
			}
			
			return false;
		}

	}

	public interface IEquivalence {
		Object getRelation(AbstractConnectedService2 cs);
	}

	private static ServiceTester instance;
	
	private Set<AbstractConnectedService2> registry;
	private Set<TestRunner> runningThreads;

	public static ServiceTester getInstance() {
		if (instance == null)
			instance = new ServiceTester();
		return instance;
	}
	
	private ServiceTester() {
		registry = new HashSet<AbstractConnectedService2>();
		runningThreads = new HashSet<TestRunner>();
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (true) {
					Collection<Set<AbstractConnectedService2>> csSetsByResource = 
						createConnectedServiceSetsByResource(registry);
					for (Set<AbstractConnectedService2> set : csSetsByResource) {
						Collection<Set<AbstractConnectedService2>> csSetsByService = 
							createConnectedServiceSetsByService(set);
						runTests(csSetsByService);
					}
					try {
						Thread.sleep(AbstractConnectedService2.TIMEOUT);
					} catch (InterruptedException e) {
					}
				}
			}
		});
		t.start();
	}
	
	private void runTests(Collection<Set<AbstractConnectedService2>> csSetsByResource) {
		if (isRunningTest(csSetsByResource))
			return;
		
		if (csSetsByResource.isEmpty())
			return;
		
		TestRunner runner = new TestRunner(csSetsByResource);
		runner.start();
		
	}
	
	private Collection<Set<AbstractConnectedService2>> partition(
			Set<AbstractConnectedService2> connectedServices, IEquivalence equivalence) {
		Map<Object, Set<AbstractConnectedService2>> collatedConnectedServiceSetMap = 
			new HashMap<Object, Set<AbstractConnectedService2>>();
		for (AbstractConnectedService2 cs : connectedServices) {
			Object object = equivalence.getRelation(cs);
			if (object == null)
				continue;
			if (!collatedConnectedServiceSetMap.containsKey(object))
				collatedConnectedServiceSetMap.put(object, new HashSet<AbstractConnectedService2>());
			collatedConnectedServiceSetMap.get(object).add(cs);
		}
		return collatedConnectedServiceSetMap.values();
	}

	private Collection<Set<AbstractConnectedService2>> createConnectedServiceSetsByResource(Set<AbstractConnectedService2> connectedServices) {
		return partition(connectedServices, new IEquivalence() {
			public Object getRelation(AbstractConnectedService2 cs) {
				AbstractSynchronizedConnection connection = (AbstractSynchronizedConnection) cs.getConnection();
				if (connection != null)
					return connection.getCurrentResource();
				
				return null;
			}
		});
	}

	protected Collection<Set<AbstractConnectedService2>> createConnectedServiceSetsByService(Set<AbstractConnectedService2> connectedServices) {
		return partition(connectedServices, new IEquivalence() {
			public Object getRelation(AbstractConnectedService2 cs) {
				return cs.getService();
			}
		});
	}

	public void register(AbstractConnectedService2 connectedService) {
		registry.add(connectedService);
	}
	
	public boolean isRegistered(AbstractConnectedService2 connectedService) {
		return registry.contains(connectedService);
	}
	
	public void unregister(AbstractConnectedService2 connectedService) {
		registry.remove(connectedService);
	}

	public synchronized void registerThread(TestRunner testRunner) {
		runningThreads.add(testRunner);
	}
	
	public synchronized void unregisterThread(TestRunner testRunner) {
		runningThreads.remove(testRunner);
	}

	public synchronized boolean isRunningTest(Collection<Set<AbstractConnectedService2>> csSetsByResource) {
		for (Set<AbstractConnectedService2> set : csSetsByResource) {
			for (AbstractConnectedService2 cs : set) {
				if (isRunningTest(cs))
					return true;
			}
		}
		return false;
	}

	private boolean isRunningTest(AbstractConnectedService2 cs) {
		for (TestRunner runner : runningThreads) {
			if (runner.contains(cs))
				return true;
		}
		
		return false;
	}
}
