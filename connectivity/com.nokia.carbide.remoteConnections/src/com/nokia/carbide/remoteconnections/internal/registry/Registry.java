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


package com.nokia.carbide.remoteconnections.internal.registry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Display;

import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IClientServiceSiteUI;
import com.nokia.carbide.remoteconnections.interfaces.IClientServiceSiteUI2;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedServiceFactory;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionTypeProvider;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager;
import com.nokia.carbide.remoteconnections.interfaces.IExtensionFilter;
import com.nokia.carbide.remoteconnections.interfaces.IService;
import com.nokia.carbide.remoteconnections.interfaces.AbstractConnection.ConnectionStatus;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2;
import com.nokia.carbide.remoteconnections.internal.api.IToggleServicesTestingListener;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatus;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatusChangedListener;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatus.EConnectionStatus;
import com.nokia.carbide.remoteconnections.internal.ui.ClientServiceSiteUI2;
import com.nokia.carbide.remoteconnections.internal.ui.ConnectionUIUtils;
import com.nokia.carbide.remoteconnections.internal.ui.SelectConnectionDialog;
import com.nokia.carbide.remoteconnections.ui.ClientServiceSiteUI;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ListenerList;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

/**
 * A registry of connection type and service extensions
 */
@SuppressWarnings("deprecation")
public class Registry implements IConnectionTypeProvider, IConnectionsManager {

	private static final String FILTER_EXTENSION = RemoteConnectionsActivator.PLUGIN_ID + ".extensionFilter"; //$NON-NLS-1$
	private static final String CONNECTION_TYPE_EXTENSION = RemoteConnectionsActivator.PLUGIN_ID + ".connectionType"; //$NON-NLS-1$
	private static final String SERVICE_EXTENSION = RemoteConnectionsActivator.PLUGIN_ID + ".service"; //$NON-NLS-1$
	private static final String CONNECTED_SERVICE_FACTORY_EXTENSION = RemoteConnectionsActivator.PLUGIN_ID + ".connectedServiceFactory"; //$NON-NLS-1$
	private static final String CONNECTION_DATA_XML = "connectionData.xml"; //$NON-NLS-1$
	private static final String NAME_SUFFIX_PATTERN = "(.+) \\((\\d+)\\)"; //$NON-NLS-1$
	private static final String NAME_FMT = "{0} ({1})"; //$NON-NLS-1$
	
	// this is exposed to other clients inside this plugin but it is not public knowledge
	public static final String CURRENT_CONNECTION_ID = RemoteConnectionsActivator.PLUGIN_ID + ".currentConnection"; //$NON-NLS-1$
	
	private static final String LAST_CONNECTION_ID = "last_connection_id"; //$NON-NLS-1$
	
	private static Registry instance = new Registry();
	
	private List<IExtensionFilter> extensionFilters;
	private Map<String, IConnectionType> connectionTypeIdMap;
	private ArrayList<IService> services;
	private Map<IConnectionType, Collection<IService>> connectionTypeToServices;
	private Map<IConnection, List<IConnectedService>> connectionToConnectedServices;
	private ListenerList<IConnectionsManagerListener> listeners;
	private List<IConnectedServiceFactory> connectedServiceFactories;
	private ListenerList<IConnectionListener> connectionListeners;
	private IConnection currentConnection;
	private Map<IConnection, IConnectionStatusChangedListener> connectionListenerMap;

	public static Registry instance() {
		return instance;
	}
	
	private Registry() {
		// private because is singleton
		connectionToConnectedServices = new HashMap<IConnection, List<IConnectedService>>();
		connectionListenerMap = new HashMap<IConnection, IConnectionStatusChangedListener>();
	}

	public void loadExtensions() {
		loadExtensionFilters();
		loadConnectionTypeExtensions();
		loadServiceExtensions();
		loadConnectedServiceFactoryExtensions();
		mapConnectionTypeToServices();
		RemoteConnectionsActivator.getDefault().addToggleServicesTestingListener(new IToggleServicesTestingListener() {
			public void servicesTestingToggled(boolean enabled) {
				setShouldTestServices(enabled);
			}
		});
	}

	private void loadConnectedServiceFactoryExtensions() {
		connectedServiceFactories = new ArrayList<IConnectedServiceFactory>();
		String loadError = Messages.getString("Registry.ConnectedServiceFactoryExtensionLoadError"); //$NON-NLS-1$
		RemoteConnectionsActivator.loadExtensions(CONNECTED_SERVICE_FACTORY_EXTENSION, loadError, 
				connectedServiceFactories, null);
	}

	private void loadExtensionFilters() {
		extensionFilters = new ArrayList<IExtensionFilter>();
		String loadError = Messages.getString("Registry.FilterExtensionLoadError"); //$NON-NLS-1$
		RemoteConnectionsActivator.loadExtensions(FILTER_EXTENSION, loadError, extensionFilters, null);
	}

	private void loadConnectionTypeExtensions() {
		List<IConnectionType> connectionTypeExtensions = new ArrayList<IConnectionType>();
		String loadError = Messages.getString("Registry.ConnectionTypeExtensionLoadError"); //$NON-NLS-1$
		RemoteConnectionsActivator.loadExtensions(CONNECTION_TYPE_EXTENSION, loadError, 
				connectionTypeExtensions, new IFilter() {
			public boolean select(Object toTest) {
				return acceptConnectionType(((IConnectionType) toTest).getIdentifier());
			}
		});
		
		connectionTypeIdMap = new HashMap<String, IConnectionType>();
		for (IConnectionType connectionType : connectionTypeExtensions) {
			connectionTypeIdMap.put(connectionType.getIdentifier(), connectionType);
		}
	}
	
	private boolean acceptConnectionType(String connectionTypeId) {
		for (IExtensionFilter extensionFilter : extensionFilters) {
			if (!extensionFilter.acceptConnectionType(connectionTypeId))
				return false;
		}
		return true;
	}
	
	private void loadServiceExtensions() {
		services = new ArrayList<IService>();
		String loadError = Messages.getString("Registry.ServiceExtensionLoadError"); //$NON-NLS-1$
		RemoteConnectionsActivator.loadExtensions(SERVICE_EXTENSION, loadError, services, new IFilter() {
			public boolean select(Object toTest) {
				return acceptService(((IService) toTest).getIdentifier());
			}
		});
	}
	
	private boolean acceptService(String serviceId) {
		for (IExtensionFilter extensionFilter : extensionFilters) {
			if (!extensionFilter.acceptService(serviceId))
				return false;
		}
		return true;
	}
	
	private void mapConnectionTypeToServices() {
		connectionTypeToServices = new HashMap<IConnectionType, Collection<IService>>();
		Set<String> connectionTypeIds = connectionTypeIdMap.keySet();
		for (IService service : services) {
			Collection<String> compatibleConnectionTypeIds = getCompatibleConnectionTypeIds(service);
			String serviceId = service.getIdentifier();
			for (String connectionTypeId : compatibleConnectionTypeIds) {
				if (connectionTypeIds.contains(connectionTypeId)) {
					IConnectionType connectionType = connectionTypeIdMap.get(connectionTypeId);
					if (!connectionTypeToServices.containsKey(connectionType)) {
						connectionTypeToServices.put(connectionType, 
								new TreeSet<IService>(new Comparator<IService>() {
							public int compare(IService o1, IService o2) {
								return o1.getDisplayName().compareToIgnoreCase(o2.getDisplayName());
							}
						}));
					}
					Set<IService> servicesForConnectionType = 
						(Set<IService>) connectionTypeToServices.get(connectionType);
					if (acceptConnectedService(serviceId, connectionTypeId))
						servicesForConnectionType.add(service);
				}
			}
		}
	}

	public Collection<String> getCompatibleConnectionTypeIds(IService service) {
		Collection<String> compatibleConnectionTypeIds = new HashSet<String>();
		if (service != null) {
			for (IConnectedServiceFactory factory : connectedServiceFactories) {
				compatibleConnectionTypeIds.addAll(factory.getCompatibleConnectionTypeIds(service));
			}
		}
		return compatibleConnectionTypeIds;
	}

	private boolean acceptConnectedService(String serviceId, String connectionTypeId) {
		boolean accept = true;
		for (IExtensionFilter extensionFilter : extensionFilters) {
			if (!extensionFilter.acceptConnectedService(connectionTypeId, serviceId)) {
				accept = false;
				break;
			}
		}
		return accept;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.registry.IConnectionStore#loadConnections()
	 */
	public void loadConnections() {
		File connectionStorageFile = getConnectionStorageFile();
		if (!connectionStorageFile.exists())
			return;
		connectionToConnectedServices = new HashMap<IConnection, List<IConnectedService>>();
		try {
			List<IConnection> connections = Reader.readFromXML(this, new FileInputStream(connectionStorageFile));
			for (IConnection connection : connections) {
				List<IConnectedService> connectedServices = createConnectedServicesForConnection(connection);
				connectionToConnectedServices.put(connection, connectedServices);
			}
			fireConnectionStoreChanged();
		} 
		catch (Throwable e) {
			RemoteConnectionsActivator.log(Messages.getString("Registry.ConnectionLoadError"), e); //$NON-NLS-1$
		}
		
	}

	private List<IConnectedService> createConnectedServicesForConnection(IConnection connection) {
		List<IConnectedService> connectedServices = new ArrayList<IConnectedService>();
		for (IService service : getCompatibleServices(connection.getConnectionType())) {
			IConnectedService connectedService = createConnectedService(service, connection);
			if (connectedService != null)
				connectedServices.add(connectedService);
		}
		return connectedServices;
	}
	
	public IConnectedService createConnectedService(IService service, IConnection connection) {
		// use first created connected service returned from a factory
		for (IConnectedServiceFactory factory : connectedServiceFactories) {
			IConnectedService connectedService = factory.createConnectedService(service, connection);
			if (connectedService != null)
				return connectedService;
		}
		RemoteConnectionsActivator.log(MessageFormat.format(
				Messages.getString("Registry.ConnectedServiceFactoryError"), //$NON-NLS-1$
				service.getDisplayName(), connection.getConnectionType().getDisplayName()), null);
		return null;
	}

	public void storeConnections() {
		try {
			OutputStream os = new FileOutputStream(getConnectionStorageFile());
			Writer.writeToXML(os, getNonDynamicConnections());
		} 
		catch (Exception e) {
			RemoteConnectionsActivator.log(Messages.getString("Registry.ConnectionStoreError"), e); //$NON-NLS-1$
		}
	}
	
	private Collection<IConnection> getNonDynamicConnections() {
		List<IConnection> nonDynamicConnections = new ArrayList<IConnection>();
		for (IConnection connection : connectionToConnectedServices.keySet()) {
			if (!(connection instanceof IConnection2) ||
					!((IConnection2) connection).isDynamic())
				nonDynamicConnections.add(connection);
		}
		return nonDynamicConnections;
	}

	public void addConnectionStoreChangedListener(IConnectionsManagerListener listener) {
		if (listeners == null)
			listeners = new ListenerList<IConnectionsManagerListener>();
		listeners.add(listener);
	}

	public void removeConnectionStoreChangedListener(IConnectionsManagerListener listener) {
		if (listeners != null)
			listeners.remove(listener);
	}
	
	private void fireConnectionStoreChanged() {
		if (listeners == null)
			return;
		for (IConnectionsManagerListener listener : listeners) {
			try {
				listener.connectionStoreChanged();
			} catch (Throwable t) {
				RemoteConnectionsActivator.logError(t);	
			}
		}
	}

	public IConnectionType getConnectionType(String identifier) {
		Check.checkContract(connectionTypeIdMap != null);
		return connectionTypeIdMap.get(identifier);
	}

	public Collection<IConnectionType> getConnectionTypes() {
		return new ArrayList<IConnectionType>(connectionTypeIdMap.values());
	}
	
	public Collection<IService> getCompatibleServices(IConnectionType connectionType) {
		Check.checkContract(connectionTypeToServices != null);
		Collection<IService> services = connectionTypeToServices.get(connectionType);
		if (services != null)
			return new ArrayList<IService>(services);
		return Collections.emptyList();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.registry.IConnectionStore#addConnection(com.nokia.carbide.remoteconnections.extensions.IConnection)
	 */
	public void addConnection(IConnection connection) {
		ensureUniqueId(connection);
		ensureUniqueName(connection);
		List<IConnectedService> connectedServices = createConnectedServicesForConnection(connection);
		connectionToConnectedServices.put(connection, connectedServices);
		fireConnectionStoreChanged();
		fireConnectionAdded(connection);
		
		setLastConnectionId(connection.getIdentifier());
		for (IConnectedService connectedService : connectedServices) {
			connectedService.setEnabled(RemoteConnectionsActivator.getDefault().getShouldTestServices());
		}
	}
	
	private void ensureUniqueId(IConnection connection) {
		String id = connection.getIdentifier();
		if (id == null || id.length() == 0 || findConnection(id) != null)
			connection.setIdentifier(getUniqueConnectionId());
	}
	
	public String getUniqueConnectionId() {
		return UUID.randomUUID().toString();
	}
	
	public IConnection findConnection(String connectionId) {
		if (CURRENT_CONNECTION_ID.equals(connectionId))
			return getCurrentConnection();
		
		for (IConnection connection : connectionToConnectedServices.keySet()) {
			if (connection.getIdentifier().equals(connectionId)) {
				return connection;
			}
		}
		return null;
	}

	private void ensureUniqueName(IConnection connection) {
		String name = connection.getDisplayName();
		if (name == null)
			connection.setDisplayName(connection.getIdentifier());
		if (connectionNameInUse(name)) {
			// check if already has the suffix " (num)"
			Pattern pattern = Pattern.compile(NAME_SUFFIX_PATTERN);
			Matcher matcher = pattern.matcher(name);
			boolean hasSuffix = matcher.matches();
			String namePart;
			int number;
			if (hasSuffix) { // extract the name part and the number to increment
				namePart = matcher.group(1);
				String numberPart = matcher.group(2);
				number = Integer.parseInt(numberPart); // all digits, so should not throw
				number = (number <= 1) ? 2 : number++;
			}
			else { // just use the name and add the number starting with 2
				namePart = name;
				number = 2;
			}
			while (true) {
				String newName = MessageFormat.format(NAME_FMT, new Object[] { namePart, number++ });
				if (!connectionNameInUse(newName)) {
					connection.setDisplayName(newName);
					break;
				}
			}
		}
	}

	public boolean connectionNameInUse(String name) {
		boolean used = false;
		for (IConnection c : connectionToConnectedServices.keySet()) {
			if (c.getDisplayName().equals(name)) {
				used = true;
				break;
			}
		}
		return used;
	}

	public void removeConnection(IConnection connection) {
		disposeConnection(connection);
		connectionToConnectedServices.remove(connection);
		if (connection == currentConnection) {
			currentConnection = null;
		}
		fireConnectionStoreChanged();
		fireConnectionRemoved(connection);
	}

	private void disposeConnection(IConnection connection) {
		List<IConnectedService> connectedServices = connectionToConnectedServices.get(connection);
		if (connectedServices != null) {
			for (IConnectedService connectedService : connectedServices) {
				connectedService.dispose();
			}
		}
		connection.dispose();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.registry.IConnectionStore#getConnections()
	 */
	public Collection<IConnection> getConnections() {
		return new ArrayList<IConnection>(connectionToConnectedServices.keySet());
	}
	
	public Collection<IConnectedService> getConnectedServices(IConnection connection) {
		List<IConnectedService> connectedServices = connectionToConnectedServices.get(connection);
		if (connectedServices != null)
			return new ArrayList<IConnectedService>(connectedServices);
		
		return Collections.emptyList();
	}

	private File getConnectionStorageFile() {
		IPath path = RemoteConnectionsActivator.getDefault().getStateLocation().append(CONNECTION_DATA_XML);
		return path.toFile();
	}

	public void disposeConnections() {
		for (IConnection connection : connectionToConnectedServices.keySet()) {
			disposeConnection(connection);
		}
		connectionToConnectedServices.clear();
	}

	private void fireDisplayChanged() {
		if (listeners == null)
			return;
		for (IConnectionsManagerListener listener : listeners) {
			try {
				listener.displayChanged();
			} catch (Throwable t) {
				RemoteConnectionsActivator.logError(t);	
			}
		}
	}
	
	public void updateDisplays() {
		fireDisplayChanged();
	}

	public IClientServiceSiteUI getClientSiteUI(IService service) {
		return new ClientServiceSiteUI(service);
	}
	
	public IClientServiceSiteUI2 getClientSiteUI2(IService service) {
		return new ClientServiceSiteUI2(service);
	}
	
	public Collection<IService> getServices() {
		return new ArrayList<IService>(services);
	}

	public IService findServiceByID(String id) {
		for (IService service : services) {
			if (service.getIdentifier().equals(id))
				return service;
		}
		return null;
	}

	public void addConnectionListener(IConnectionListener listener) {
		if (connectionListeners == null)
			connectionListeners = new ListenerList<IConnectionListener>();
		connectionListeners.add(listener);
	}

	public void removeConnectionListener(IConnectionListener listener) {
		if (connectionListeners != null)
			connectionListeners.remove(listener);
	}
	
	private void fireConnectionAdded(IConnection connection) {
		if (connectionListeners == null)
			return;
		for (IConnectionListener listener : connectionListeners) {
			try {
				listener.connectionAdded(connection);
			} catch (Throwable t) {
				RemoteConnectionsActivator.logError(t);	
			}
		}
	}
	
	private void fireConnectionRemoved(IConnection connection) {
		if (connectionListeners == null)
			return;
		for (IConnectionListener listener : connectionListeners) {
			try {
				listener.connectionRemoved(connection);
			} catch (Throwable t) {
				RemoteConnectionsActivator.logError(t);	
			}
		}
	}
	
	private void fireCurrentConnectionSet(IConnection connection) {
		if (connectionListeners == null)
			return;
		for (IConnectionListener listener : connectionListeners) {
			try {
				listener.currentConnectionSet(connection);
			} catch (Throwable t) {
				RemoteConnectionsActivator.logError(t);	
			}
		}
	}
	
	public ISelectedConnectionInfo ensureConnection(String id, IService service) throws CoreException {
		final boolean wasCurrentConnection = CURRENT_CONNECTION_ID.equals(id);
		final IConnection[] connectionHolder = { findConnection(id) };
		final String[] storableIdHolder = { id };
		if (!isCompatibleConnection(connectionHolder[0], service)) {
			connectionHolder[0] = getCompatibleConnectionFromUser(service, storableIdHolder);
			if (connectionHolder[0] == null) {
				throw new CoreException(
						Logging.newStatus(RemoteConnectionsActivator.getDefault(), IStatus.CANCEL, 
								Messages.getString("Registry.NoCompatibleConnectionMsg"))); //$NON-NLS-1$
			}
			else if (wasCurrentConnection && !connectionHolder[0].getIdentifier().equals(CURRENT_CONNECTION_ID)) {
				setCurrentConnection(connectionHolder[0]);
				storableIdHolder[0] = CURRENT_CONNECTION_ID;
			}
		}
		return new ISelectedConnectionInfo() {
			public String getStorableId() {
				return storableIdHolder[0];
			}
			
			public IConnection getConnection() {
				return connectionHolder[0];
			}
		};
	}

	private IConnection getCompatibleConnectionFromUser(final IService service, final String[] storableIdHolder) {
		final IConnection[] connectionHolder = { null };
		if (!WorkbenchUtils.isJUnitRunning()) {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					// First, see if any connections could possibly be selected.
					// If not, immediately offer to create a new connection.
					
					final IClientServiceSiteUI2 ui = getClientSiteUI2(service);
					final TitleAreaDialog dialog = new SelectConnectionDialog(WorkbenchUtils.getSafeShell(), ui);
					dialog.setBlockOnOpen(true);
					if (dialog.open() == Dialog.OK) {
						storableIdHolder[0] = ui.getSelectedConnection();
						connectionHolder[0] = findConnection(storableIdHolder[0]);
					}
				}
			});
		}
		return connectionHolder[0];
	}

	private boolean isCompatibleConnection(IConnection connection, IService service) {
		if (connection == null)
			return false;
		return service == null || getCompatibleServices(connection.getConnectionType()).contains(service);
	}

	public void setCurrentConnection(IConnection connection) {
		if (connection == null) {
			currentConnection = null; // special case
			fireCurrentConnectionSet(null);
		}
		else if (connectionToConnectedServices.keySet().contains(connection)) {
			currentConnection = connection;
			fireCurrentConnectionSet(connection);
		}
	}

	public IConnection getCurrentConnection() {
		return currentConnection;
	}

	public void disconnect(final IConnection2 connection) {
		if (!connection.isDynamic())
			return;
		
		// transition to disconnected state and wait:
		// 	when not in-use, remove and stop waiting
		if (connection.getStatus().getEConnectionStatus().equals(EConnectionStatus.IN_USE)) {
			IConnectionStatus status = new ConnectionStatus(EConnectionStatus.IN_USE_DISCONNECTED, 
					Messages.getString("ConnectionsView.DisconnectedLabel"), //$NON-NLS-1$
					Messages.getString("ConnectionsView.DisconnectedDesc")); //$NON-NLS-1$
			connection.setStatus(status);
			IConnectionStatusChangedListener listener = new IConnectionStatusChangedListener() {
				public void statusChanged(IConnectionStatus status) {
					if (notInUse(status)) {
						IConnectionStatusChangedListener listener = 
							connectionListenerMap.remove(connection);
						connection.removeStatusChangedListener(listener);
						removeConnection(connection);
					}
				}

				private boolean notInUse(IConnectionStatus status) {
					EConnectionStatus eStatus = status.getEConnectionStatus();
					return !eStatus.equals(EConnectionStatus.IN_USE) &&
						!eStatus.equals(EConnectionStatus.IN_USE_DISCONNECTED);
				}
			};
			connectionListenerMap.put(connection, listener);
			connection.addStatusChangedListener(listener);
		}
		else {
			removeConnection(connection);
		}
	}

	public boolean reconnect(IConnection2 connection) {
		if (!connection.isDynamic())
			return false;
		
		if (!connectionToConnectedServices.containsKey(connection)) // connection does not exist
			return false;
		
		// if not removed, transition out of disconnected state 
		// return not removed
		IConnectionStatusChangedListener listener = connectionListenerMap.remove(connection);
		if (listener != null) { // is disconnected
			connection.removeStatusChangedListener(listener);
			if (connectionToConnectedServices.containsKey(connection)) {
				IConnectionStatus status;
				if (ConnectionUIUtils.isSomeServiceInUse(connection)) {
					status = new ConnectionStatus(EConnectionStatus.IN_USE, 
							Messages.getString("ConnectionsView.InUseLabel"), //$NON-NLS-1$
							Messages.getString("ConnectionsView.InUseDesc")); //$NON-NLS-1$
				}
				else {
					status = new ConnectionStatus(EConnectionStatus.NOT_READY, "", ""); //$NON-NLS-1$ //$NON-NLS-2$
				}
				connection.setStatus(status);
				return true;
			}
		}
		else // connection is not disconnected
			return true;
		
		return false;
	}

	/**
	 * Internal method:  get the last id of a connection created or selected in UI.  
	 * Used to prepopulate UI for selecting connections.
	 * @return connection id (not "current") or <code>null</code>
	 */
	public String getLastConnectionId() {
		return RemoteConnectionsActivator.getDefault().getPreferenceStore().getString(LAST_CONNECTION_ID);
	}
	
	/**
	 * Internal method:  remember the last id of a connection created or selected in UI.  
	 * @param id connection id.  Current connection is converted to an actual connection id.
	 */
	public void setLastConnectionId(String id) {
		if (CURRENT_CONNECTION_ID.equals(id)) 
			id = currentConnection != null ? currentConnection.getIdentifier() : null;
		if (id == null) 
			return;
		RemoteConnectionsActivator.getDefault().getPreferenceStore().setValue(LAST_CONNECTION_ID, id);
	}

	private void setShouldTestServices(boolean shouldTest) {
		for (Entry<IConnection, List<IConnectedService>> entry : connectionToConnectedServices.entrySet()) {
			for (IConnectedService connectedService : entry.getValue()) {
				connectedService.setEnabled(shouldTest);
			}	
		}
	}
}