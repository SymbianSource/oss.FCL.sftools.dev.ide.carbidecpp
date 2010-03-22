/*
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
package com.nokia.carbide.remoteconnections.discovery.pccs.agent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.discovery.pccs.Activator;
import com.nokia.carbide.remoteconnections.discovery.pccs.Messages;
import com.nokia.carbide.remoteconnections.discovery.pccs.pccsnative.DeviceConnection;
import com.nokia.carbide.remoteconnections.discovery.pccs.pccsnative.PCCSConnection;
import com.nokia.carbide.remoteconnections.discovery.pccs.pccsnative.PCCSConnection.DeviceEventListener;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionFactory;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2;
import com.nokia.carbide.remoteconnections.internal.api.IDeviceDiscoveryAgent;

/**
 * Implementation of IDeviceDiscoveryAgent for PCCS USB connection.
 */
public class PCCSDiscoveryAgent implements IDeviceDiscoveryAgent, DeviceEventListener {

	private static final String USB_CONNECTION_TYPE = 
		"com.nokia.carbide.trk.support.connection.USBConnectionType"; //$NON-NLS-1$
	private static final String PORT_SETTING = "port"; //$NON-NLS-1$
	private boolean DEBUG = false;

	public class PCCSPrequisiteStatus implements IPrerequisiteStatus {
		private boolean isOK = true;
		private String errorText;
		private URL errorURL;
		
		public PCCSPrequisiteStatus() {
			isOK = true;
			errorText = null;
			errorURL = null;
		}

		public PCCSPrequisiteStatus(boolean ok, String msg, URL url) {
			isOK = ok;
			errorText = msg;
			errorURL = url;
		}
		/* (non-Javadoc)
		 * @see com.nokia.carbide.remoteconnections.internal.api.IDeviceDiscoveryAgent.IPrerequisiteStatus#getErrorText()
		 */
		public String getErrorText() {
			return errorText;
		}

		/* (non-Javadoc)
		 * @see com.nokia.carbide.remoteconnections.internal.api.IDeviceDiscoveryAgent.IPrerequisiteStatus#getURL()
		 */
		public URL getURL() {
			return errorURL;
		}

		/* (non-Javadoc)
		 * @see com.nokia.carbide.remoteconnections.internal.api.IDeviceDiscoveryAgent.IPrerequisiteStatus#isOK()
		 */
		public boolean isOK() {
			return isOK;
		}

	}

	protected IConnectionsManager manager;
	protected PCCSConnection pccsConnection;
	private IPrerequisiteStatus loadStatus = new PCCSPrequisiteStatus();
	private UpdateConnectionThread updateThread;
	private volatile int numPendingUpdates;

	/**
	 * Constructs a PCCSDiscoveryAgent object
	 */
	public PCCSDiscoveryAgent() {
//		connections = new HashMap<String, IConnection2>();
		manager = RemoteConnectionsActivator.getConnectionsManager();
		pccsConnection = new PCCSConnection();
	}

	private boolean isSymSEELayout() {
		return Activator.isSymSEELayout();
	}

	/*
	 * (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.internal.IDeviceDiscoveryAgent#getInformation()
	 */
	public URL getInformation() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.discovery.pccs.pccsnative.PCCSConnection.DeviceEventListener#onDeviceEvent(com.nokia.carbide.remoteconnections.discovery.pccs.pccsnative.PCCSConnection.DeviceEventListener.DeviceEvent, java.lang.String)
	 */
	public void onDeviceEvent(DeviceEvent eventType, String serialNumber) {
		if (DEBUG) System.out.println("onDeviceEvent");
		try {
			switch (eventType) {
			case DEVICE_LIST_UPDATED:
			case DEVICE_ADDED:
			case DEVICE_REMOVED:
			case DEVICE_UPDATED_ADDEDCONNECTION:
			case DEVICE_UPDATED_REMOVEDCONNECTION:
				if (updateThread != null && updateThread.isAlive()) {
					if (DEBUG) System.out.println("onDeviceEvent: update in progress still");
					numPendingUpdates++;
				} else {
					if (DEBUG) System.out.println("onDeviceEvent: update not started or has finished");
					numPendingUpdates = 1;
					updateThread = new UpdateConnectionThread(pccsConnection);
					updateThread.start();
				}
				
//				updateConnections(pccsConnection.getGoodConnectionList());
				break;
			case DEVICE_UPDATED_RENAMED:
				if (DEBUG) System.out.println("onDeviceEvent: updated renamed");
				break;
			default:
				if (DEBUG) System.out.println("onDeviceEvent: default");
				break;
			}
		} catch (Exception e) {
			RemoteConnectionsActivator.logError(e);
		}
	}

	private class UpdateConnectionThread extends Thread {
		final PCCSConnection pccs;
		public UpdateConnectionThread(final PCCSConnection pccs) {
			super(Activator.getDisplayName() + ":updateThread");
			this.pccs = pccs;
		}

		@Override
		public void run() {
			try {
				do {
					if (DEBUG) System.out.println("updateThread updating: " + numPendingUpdates);
					if (numPendingUpdates > 1) {
						numPendingUpdates--;
					} else {
						updateConnections2(pccs.getGoodConnectionList());
						numPendingUpdates--;
					}
				} while (numPendingUpdates > 0);
				
				if (DEBUG) System.out.println("updateThread exiting");
			} catch (CoreException e) {
				Activator.logError(e);
			}
		}		
	}
	/*
	 * (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.internal.IDeviceDiscoveryAgent#start()
	 */
	public void start() throws CoreException {
		try {
			pccsConnection.open();
		} catch (CoreException ce) {
			saveLoadStatus(ce);
			throw ce;		// rethrow
		}
		updateConnections2(pccsConnection.getGoodConnectionList());
		pccsConnection.addEventListenter(this);
	}

	private void saveLoadStatus(CoreException ce) {
		String msg = null;
		URL location = null;
		if (isSymSEELayout()) {
			if (ce.getStatus().getCode() == PCCSConnection.PCCS_NOT_FOUND)
				msg = Messages.PCCSDiscoveryAgent_PCCS_Not_Found_Error;
			else
				msg = Messages.PCCSDiscoveryAgent_PCCS_Version_Error;
			
			try {
				location = new URL(Activator.getLoadErrorURL());
			} catch (MalformedURLException e) {
			}
		} else {
			if (ce.getStatus().getCode() == PCCSConnection.PCCS_NOT_FOUND)
				msg = Messages.PCCSDiscoveryAgent_PCSuite_Not_Found_Error;
			else
				msg = Messages.PCCSDiscoveryAgent_PCSuite_Version_Error;
			
			try {
				location = new URL(Activator.getLoadErrorURL());
			} catch (MalformedURLException e) {
			}
		}
		loadStatus = new PCCSPrequisiteStatus(false, msg, location);
	}

	/*
	 * (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.internal.IDeviceDiscoveryAgent#stop()
	 */
	public void stop() throws CoreException {
		pccsConnection.removeEventListener(this);
		pccsConnection.close();
	}

	/**
	 * Creates a new PCCS connection
	 * @param deviceConn - device/connection information from PCCS
	 */
	protected void createConnection(DeviceConnection deviceConn) {
		// TODO: currently only handles USB & Serial
		if (deviceConn.media.equals("usb") && deviceConn.mode.equals("serial")) { //$NON-NLS-1$
			IConnectionType connectionType = 
				RemoteConnectionsActivator.getConnectionTypeProvider().getConnectionType(USB_CONNECTION_TYPE);
			if (connectionType != null) {
				IConnectionFactory factory = connectionType.getConnectionFactory();
				Map<String, String> settings = factory.getSettingsFromUI();
				settings.put(PORT_SETTING, deviceConn.port);
				IConnection connection = factory.createConnection(settings);
				if (connection instanceof IConnection2) {
					IConnection2 connection2 = (IConnection2) connection;
					connection2.setIdentifier(createUniqueId(deviceConn));
					connection2.setDisplayName(deviceConn.friendlyName);
					connection2.setDynamic(true);
					String key = getKey(deviceConn);
					if (DEBUG) System.out.println("createConnection addConnection: " + key);
					manager.addConnection(connection2);
				}
				else {
					Activator.logMessage("Could not create dynamic serial connection", IStatus.ERROR);
				}
			}
			else {
				Activator.logMessage("USB connection type extension not found", IStatus.ERROR);
			}
		}
	}

	/**
	 * Create a unique ID based on this device/connection
	 * @param conn - device/connection information from PCCS
	 * @return
	 */
	private String createUniqueId(DeviceConnection conn) {
		return getClass().getSimpleName() + ": " + conn.friendlyName + ": " + conn.address; //$NON-NLS-1$
	}

	/**
	 * Return a string key based on the device and connection information
	 * @param conn - device/connection information
	 * @return
	 */
	protected String getKey(DeviceConnection conn) {
		String key = conn.friendlyName + conn.serialNumber + conn.address;
		return key;
	}
	/**
	 * update the remote connections
	 */
	private void updateConnections2(Collection<DeviceConnection> connList) {
	
		if (connList == null || connList.isEmpty()) {
			// disconnect all our connections
			Collection<IConnection> rConnList = manager.getConnections();
			for (IConnection rConn : rConnList) {
				if (rConn instanceof IConnection2) {
					if (((IConnection2) rConn).isDynamic()) {
						if (rConn.getIdentifier().contains(getClass().getSimpleName())) {
							if (DEBUG) System.out.println("new list empty, disconnect: "+ rConn.getIdentifier());
							manager.disconnect((IConnection2) rConn);
						}
					}
				}
			}
		} else {
			// new list not empty
			// disconnect all remote ones not in our list
			Collection<IConnection> rConnList = manager.getConnections();
			for (IConnection rConn : rConnList) {
				if (rConn instanceof IConnection2) {
					if (((IConnection2) rConn).isDynamic()) {
						String uid = rConn.getIdentifier();
						if (uid.contains(getClass().getSimpleName())) {
							boolean uidFound = false;
							for (DeviceConnection ourConn : connList) {
								if (createUniqueId(ourConn).equals(uid)) {
									uidFound = true;
									break;
								}
							}
							if (!uidFound) {
								if (DEBUG) System.out.println("new list not empty, in manager but not in new list  -- disconnect: "+ uid);
								manager.disconnect((IConnection2) rConn);
							}
						}
					}
				}
			}
			// create if manager doesn't have this connection
			// reconnect if manager still does
			for (DeviceConnection ourConn : connList) {
				String uid = createUniqueId(ourConn);
				IConnection2 rConn = (IConnection2) manager.findConnection(uid);
				if (rConn == null) {
					if (DEBUG) System.out.println("new list not empty, manager doesn't have this -- create: "+ uid);
					createConnection(ourConn);
				} else {
					if (DEBUG) System.out.println("new list not empty, manager has this -- reconnect: "+ uid);
					manager.reconnect(rConn);
				}
			}
		}
	}
	public String getDisplayName() {
		return Activator.getDisplayName();
	}

	public IPrerequisiteStatus getPrerequisiteStatus() {
		// Manager calls this first so we can check if we can load.
		// so let's open the discovery and close it catching any exceptions.
		try {
			pccsConnection.testPrerequisites();
		} catch (CoreException ce) {
			saveLoadStatus(ce);
		}
		
		return loadStatus;
	}
}
