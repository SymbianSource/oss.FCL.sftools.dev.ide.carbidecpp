/**
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

package com.nokia.carbide.remoteconnections.tests.discovery;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionFactory;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2;
import com.nokia.carbide.remoteconnections.internal.api.IDeviceDiscoveryAgent;
import com.nokia.carbide.remoteconnections.tests.extensions.IntervalConnectionType;
import com.nokia.carbide.remoteconnections.tests.extensions.TestFilter;

public class RandomDiscoveryAgent implements IDeviceDiscoveryAgent {
	public class RandomPrerequisiteStatus implements IPrerequisiteStatus {

		private boolean ok;
		
		RandomPrerequisiteStatus() {
			ok = true; // modify to test
		}
		public String getErrorText() {
			return "Test error text";
		}

		public URL getURL() {
			try {
				return new URL("http://www.yahoo.com");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return null;
		}

		public boolean isOK() {
			return ok;
		}

	}

	private static final String CONNECTION_TYPE = 
		"com.nokia.carbide.remoteconnections.tests.extensions.IntervalConnectionType";
	private Random random = new Random();
	private Set<IConnection2> connections = new HashSet<IConnection2>();
	private IConnectionsManager manager = RemoteConnectionsActivator.getConnectionsManager();

	private final class DiscoveryThread extends Thread {
		private static final int MAX = 60000;
		private static final int MIN = 10000;
		private volatile boolean keepRunning;
		
		public void run() {
			keepRunning = true;
			while (keepRunning) {
				try {
					sleep(getRandomCreationIntervalMs());
				} catch (InterruptedException e) {
					keepRunning = false;
				}
				if (getRandomIntBetween(0, connections.size() + 1) == 0) {
					createNewConnection();
				}
				else if (!connections.isEmpty()) {
					IConnection2 connection = connections.iterator().next();
					connections.remove(connection);
					manager.disconnect(connection);
				}
			}
		}
		
		private int getRandomCreationIntervalMs() {
			return getRandomIntBetween(MIN, MAX);
		}

		public void stopRunning() {
			keepRunning = false;
		}
	}

	private DiscoveryThread thread = new DiscoveryThread();

	public URL getInformation() {
		return null;
	}

	private void createNewConnection() {
		IConnectionType connectionType = 
			RemoteConnectionsActivator.getConnectionTypeProvider().getConnectionType(CONNECTION_TYPE);
		IConnectionFactory factory = connectionType.getConnectionFactory();
		Map<String, String> settings = factory.getSettingsFromUI();
		String val = getRandomIntervalString();
		settings.put(IntervalConnectionType.KEY, val);
		IConnection2 connection = (IConnection2) factory.createConnection(settings);
		connection.setDisplayName(connection.getConnectionType().getDisplayName() + " " + val + " ms");
		connection.setDynamic(true);
		connections.add(connection);
		manager.addConnection(connection);
	}

	private String getRandomIntervalString() {
		int r = getRandomIntBetween(1000, 30000);
		return Integer.toString(r);
	}		
	
	private int getRandomIntBetween(int min, int max) {
		return (Math.abs(random.nextInt()) % (max - min)) + min;
	}
	
	public void start() throws CoreException {
		if (TestFilter.isTest)
			thread.start();
	}

	public void stop() throws CoreException {
		thread.stopRunning();
	}

	public String getDisplayName() {
		return "Random Test Discovery Agent";
	}

	public IPrerequisiteStatus getPrerequisiteStatus() {
		return (new RandomPrerequisiteStatus());
	}

}
