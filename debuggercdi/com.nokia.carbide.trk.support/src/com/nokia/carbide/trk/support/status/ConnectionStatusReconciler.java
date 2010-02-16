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

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;

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
import com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatus;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatus.EConnectionStatus;
import com.nokia.carbide.trk.support.Messages;
import com.nokia.carbide.trk.support.connection.USBConnectionType;
import com.nokia.carbide.trk.support.service.TRKConnectedService;
import com.nokia.carbide.trk.support.service.TracingConnectedService;
import com.nokia.cpp.internal.api.utils.ui.RunRunnableWhenWorkbenchVisibleJob;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

/**
 * A singleton object that manages the device status of dynamic connections
 * based on the status of the TRK and Tracing services.
 */
public class ConnectionStatusReconciler {
	
	private static final String CONNECTIONS_VIEW_ID = 
		"com.nokia.carbide.remoteconnections.view.ConnectionsView"; //$NON-NLS-1$

	private class ConnectionListener implements IConnectionListener {

		public void connectionAdded(IConnection connection) {
			addConnection(connection);
		}

		public void connectionRemoved(IConnection connection) {
			if (connection.equals(userSetCurrentConnection))
				userSetCurrentConnection = null;
			removeConnection(connection);
		}
		
		public void currentConnectionSet(IConnection connection) {
			if (connection != null && !connection.equals(reconcilerSetCurrentConnection))
				userSetCurrentConnection = connection;
		}
		
	}
	
	private class ServiceStatusListener implements IStatusChangedListener {

		public void statusChanged(IStatus status) {
			handleServiceStatusChange(status);
		}
		
	}
	
	private static ConnectionStatusReconciler instance;
	private IConnectionsManager manager;
	private IConnectionListener connectionListener;
	private List<IConnection> handledConnections;
	private ServiceStatusListener serviceStatusListener;
	private IConnection reconcilerSetCurrentConnection;
	private IConnection userSetCurrentConnection;
	
	private ConnectionStatusReconciler() {
		connectionListener = new ConnectionListener();
		manager = RemoteConnectionsActivator.getConnectionsManager();
		manager.addConnectionListener(connectionListener);
		handledConnections = new ArrayList<IConnection>();
		serviceStatusListener = new ServiceStatusListener();
	}
	
	public static ConnectionStatusReconciler getInstance() {
		if (instance == null)
			instance = new ConnectionStatusReconciler();
		
		return instance;
	}

	public void dispose() {
		manager.removeConnectionListener(connectionListener);
		for (IConnection connection : new ArrayList<IConnection>(handledConnections)) {
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
		handledConnections.add(connection);
		for (IConnectedService service : manager.getConnectedServices(connection)) {
			if (service instanceof TRKConnectedService ||
					service instanceof TracingConnectedService) {
				service.addStatusChangedListener(serviceStatusListener);
			}
		}
		showConnectionsView();
	}

	private void showConnectionsView() {
		// avoid deadlock if this called as a result of a launch sequence issuing a "select connection" dialog
		final Shell shell = WorkbenchUtils.getActiveShell();
		final boolean isVisible[] = { true };
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				isVisible[0] = shell != null && shell.isVisible();
			}
		});
		
		Runnable runnable = new Runnable() {
			public void run() {
				try {
					WorkbenchUtils.getView(CONNECTIONS_VIEW_ID);
				} catch (PartInitException e) {
				}
			}
		};
		
		if (shell == null || !isVisible[0]) {
			RunRunnableWhenWorkbenchVisibleJob.start(runnable);
		} else {
			Display.getDefault().asyncExec(runnable);
		}
	}
	
	private void reconcileAsCurrent(IConnection connection) {
		if (canBeSetToCurrent(connection)) {
			if (isReady(connection)) { // set as current
				reconcilerSetCurrentConnection = connection;
				manager.setCurrentConnection(connection);
			} else if (isNotReady(connection) && connection.equals(manager.getCurrentConnection())) { 
				// unset current or set something else current
				if (isDynamic(connection) && userSetCurrentConnection != null) {
					manager.setCurrentConnection(userSetCurrentConnection);
				}
				else {
					// look for some other existing connection that is ready
					for (IConnection c : manager.getConnections()) {
						if (canBeSetToCurrent(c) && isReady(c)) {
							reconcilerSetCurrentConnection = connection;
							manager.setCurrentConnection(connection);
							return;
						}
					}
					// set to no current connection
					manager.setCurrentConnection(null);
				}
			}
		}
	}

	private boolean isReady(IConnection connection) {
		return equalsConnectionStatus(connection, EConnectionStatus.READY);
	}

	private boolean isNotReady(IConnection connection) {
		return equalsConnectionStatus(connection, EConnectionStatus.NOT_READY);
	}
	
	private boolean equalsConnectionStatus(IConnection connection, EConnectionStatus status) {
		if (connection instanceof IConnection2) {
			IConnectionStatus connectionStatus = ((IConnection2) connection).getStatus();
			if (connectionStatus != null)
				return connectionStatus.getEConnectionStatus().equals(status);
		}
		return false;
	}
	
	private boolean canBeSetToCurrent(IConnection connection) {
		// USB connections for now
		return USBConnectionType.ID.equals(connection.getConnectionType().getIdentifier());
	}

	private void reconcileStatus(IConnection connection) {
		if (!isDynamic(connection)) // don't set status for user generated connections
			return;
		
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

	private IConnection findConnection(IConnectedService cs) {
		for (IConnection connection : handledConnections) {
			for (IConnectedService connectedService : manager.getConnectedServices(connection)) {
				if (cs.equals(connectedService))
					return connection;
			}
		}
		return null;
	}

	public void handleServiceStatusChange(IStatus status) {
		IConnectedService service = status.getConnectedService();
		IConnection connection = findConnection(service);
		if (connection instanceof IConnection2) {
			reconcileStatus((IConnection2) connection);
		}
		if (connection != null)
			reconcileAsCurrent(connection);
	}

}
