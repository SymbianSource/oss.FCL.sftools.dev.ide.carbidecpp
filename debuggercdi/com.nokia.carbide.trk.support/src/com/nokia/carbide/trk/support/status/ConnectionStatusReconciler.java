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

package com.nokia.carbide.trk.support.status;

import java.util.ArrayList;
import java.util.List;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager;
import com.nokia.carbide.remoteconnections.interfaces.AbstractConnection.ConnectionStatus;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatusChangedListener;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus.EStatus;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionListener;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatus.EConnectionStatus;
import com.nokia.carbide.trk.support.Messages;
import com.nokia.carbide.trk.support.connection.USBConnectionType;
import com.nokia.carbide.trk.support.service.TRKConnectedService;
import com.nokia.carbide.trk.support.service.TracingConnectedService;

/**
 * A singleton object that manages the device status of dynamic connections
 * based on the status of the TRK and Tracing services.
 */
public class ConnectionStatusReconciler {
	
	private class ConnectionListener implements IConnectionListener {

		public void connectionAdded(IConnection connection) {
			addConnection(connection);
		}

		public void connectionRemoved(IConnection connection) {
			removeConnection(connection);
		}
		
		public void defaultConnectionSet(IConnection connection) {}
		
	}
	
	private class ServiceStatusListener implements IStatusChangedListener {

		public void statusChanged(IStatus status) {
			handleServiceStatusChange(status);
		}
		
	}
	
	private static ConnectionStatusReconciler instance;
	private IConnectionsManager manager;
	private IConnectionListener connectionListener;
	private List<IConnection2> handledConnections;
	private ServiceStatusListener serviceStatusListener;
	
	private ConnectionStatusReconciler() {
		connectionListener = new ConnectionListener();
		manager = RemoteConnectionsActivator.getConnectionsManager();
		manager.addConnectionListener(connectionListener);
		handledConnections = new ArrayList<IConnection2>();
		serviceStatusListener = new ServiceStatusListener();
	}
	
	public static ConnectionStatusReconciler getInstance() {
		if (instance == null)
			instance = new ConnectionStatusReconciler();
		
		return instance;
	}

	public void dispose() {
		manager.removeConnectionListener(connectionListener);
		for (IConnection2 connection : new ArrayList<IConnection2>(handledConnections)) {
			removeConnection(connection);
		}
	}

	private boolean isDynamic(IConnection connection) {
		return connection instanceof IConnection2 && ((IConnection2) connection).isDynamic();
	}

	private boolean isSysTRK(TRKConnectedService service) {
		String value = service.getProperties().get(TRKConnectedService.PROP_SYS_TRK);
		return Boolean.parseBoolean(value);
	}
	
	private void addConnection(IConnection connection) {
		if (!isDynamic(connection))
			return;
		
		reconcileAsDefault(connection);
		
		handledConnections.add((IConnection2) connection);
		for (IConnectedService service : manager.getConnectedServices(connection)) {
			if (service instanceof TRKConnectedService ||
					service instanceof TracingConnectedService) {
				service.addStatusChangedListener(serviceStatusListener);
			}
		}
		reconcileConnection((IConnection2) connection);
	}
	
	private void reconcileAsDefault(IConnection connection) {
		// USB connections become default when added
		if (USBConnectionType.ID.equals(connection.getConnectionType().getIdentifier()))
			manager.setDefaultConnection(connection);
	}

	private void reconcileConnection(IConnection2 connection) {
		boolean isSysTRK = false;
		EStatus trkStatus = EStatus.UNKNOWN;
		EStatus traceStatus = EStatus.UNKNOWN;
		for (IConnectedService service : manager.getConnectedServices(connection)) {
			if (service instanceof TRKConnectedService) {
				isSysTRK = isSysTRK((TRKConnectedService) service);
				trkStatus = service.getStatus().getEStatus();
			}
			if (service instanceof TracingConnectedService) {
				traceStatus = service.getStatus().getEStatus();
			}
		}
		setConnectionStatus((IConnection2) connection, isSysTRK, trkStatus, traceStatus);
	}

	private void setConnectionStatus(IConnection2 connection, boolean isSysTRK, EStatus trkStatus, EStatus traceStatus) {
		// use trk status
		EConnectionStatus connectionStatus = service2ConnectionStatus(trkStatus);
		// if sys trk, tracing also used
		if (isSysTRK && connectionStatus.equals(EConnectionStatus.READY)) {
			connectionStatus = service2ConnectionStatus(traceStatus);
		}

		String shortDesc = getShortDescriptionForStatus(connectionStatus);
		StringBuilder longDesc = new StringBuilder(Messages.getString("ConnectionStatusReconciler_TRKServicePrefix")); //$NON-NLS-1$
		longDesc.append(getServiceStatusString(trkStatus));
		if (isSysTRK) {
			longDesc.append(Messages.getString("ConnectionStatusReconciler_TracingServicePrefix")); //$NON-NLS-1$
			longDesc.append(getServiceStatusString(traceStatus));
		}
		
		connection.setStatus(new ConnectionStatus(connectionStatus, shortDesc, longDesc.toString()));
	}

	private String getShortDescriptionForStatus(EConnectionStatus connectionStatus) {
		switch (connectionStatus) {
			case READY:
				return Messages.getString("ConnectionStatusReconciler_ReadyLabel"); //$NON-NLS-1$
			case NOT_READY:
				return Messages.getString("ConnectionStatusReconciler_NotReadyLabel"); //$NON-NLS-1$
			case IN_USE:
				return Messages.getString("ConnectionStatusReconciler_InUseLabel"); //$NON-NLS-1$
			case IN_USE_DISCONNECTED:
				return Messages.getString("ConnectionStatusReconciler_DisconnectedLabel"); //$NON-NLS-1$
			}
		return ""; //$NON-NLS-1$
	}

	private String getServiceStatusString(EStatus status) {
		switch (status) {
			case UP:
				return Messages.getString("ConnectionStatusReconciler_availableLabel"); //$NON-NLS-1$
			case DOWN:
				return Messages.getString("ConnectionStatusReconciler_unavailableLabel"); //$NON-NLS-1$
			case IN_USE:
				return Messages.getString("ConnectionStatusReconciler_inUseLabel_lower"); //$NON-NLS-1$
		}
		return ""; //$NON-NLS-1$
	}

	private EConnectionStatus service2ConnectionStatus(EStatus serviceStatus) {
		switch (serviceStatus) {
			case UP:
				return EConnectionStatus.READY;
			case DOWN:
				return EConnectionStatus.NOT_READY;
			case IN_USE:
				return EConnectionStatus.IN_USE;
		}
		return EConnectionStatus.NONE;
	}

	private void removeConnection(IConnection connection) {
		handledConnections.remove(connection);
	}

	private IConnection2 findConnection(IConnectedService cs) {
		for (IConnection2 connection : handledConnections) {
			for (IConnectedService connectedService : manager.getConnectedServices(connection)) {
				if (cs.equals(connectedService))
					return connection;
			}
		}
		return null;
	}

	public void handleServiceStatusChange(IStatus status) {
		IConnectedService service = status.getConnectedService();
		IConnection2 connection = findConnection(service);
		if (connection != null) {
			reconcileConnection(connection);
		}
	}

}
