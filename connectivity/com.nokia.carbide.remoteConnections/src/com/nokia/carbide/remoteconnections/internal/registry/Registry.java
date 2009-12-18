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
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.IFilter;

import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IClientServiceSiteUI;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedServiceFactory;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionTypeProvider;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager;
import com.nokia.carbide.remoteconnections.interfaces.IExtensionFilter;
import com.nokia.carbide.remoteconnections.interfaces.IService;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2;
import com.nokia.carbide.remoteconnections.ui.ClientServiceSiteUI;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ListenerList;
import com.nokia.cpp.internal.api.utils.core.Logging;

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
	private static Registry instance;
	
	private List<IExtensionFilter> extensionFilters;
	private Map<String, IConnectionType> connectionTypeIdMap;
	private ArrayList<IService> services;
	private Map<IConnectionType, Collection<IService>> connectionTypeToServices;
	private Map<IConnection, List<IConnectedService>> connectionToConnectedServices;
	private ListenerList<IConnectionsManagerListener> listeners;
	private List<IConnectedServiceFactory> connectedServiceFactories;
	private ListenerList<IConnectionListener> connectionListeners;
	private IConnection defaultConnection;

	public static Registry instance() {
		if (instance == null) {
			instance = new Registry();
		}
		
		return instance;
	}
	
	private Registry() {
		// private because is singleton
		connectionToConnectedServices = new HashMap<IConnection, List<IConnectedService>>();
	}

	public void loadExtensions() {
		loadExtensionFilters();
		loadConnectionTypeExtensions();
		loadServiceExtensions();
		loadConnectedServiceFactoryExtensions();
		mapConnectionTypeToServices();
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
		for (IConnectedServiceFactory factory : connectedServiceFactories) {
			compatibleConnectionTypeIds.addAll(factory.getCompatibleConnectionTypeIds(service));
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
		catch (Exception e) {
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
			listener.connectionStoreChanged();
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
	}
	
	private void ensureUniqueId(IConnection connection) {
		String id = connection.getIdentifier();
		if (id == null || id.length() == 0 || findConnectionById(id) != null)
			connection.setIdentifier(getUniqueConnectionId());
	}
	
	public String getUniqueConnectionId() {
		return UUID.randomUUID().toString();
	}
	
	public IConnection findConnectionById(String id) {
		for (IConnection connection : connectionToConnectedServices.keySet()) {
			if (connection.getIdentifier().equals(id)) {
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
		if (connection == defaultConnection) {
			defaultConnection = null;
			pickNewDefaultConnection();
		}
		fireConnectionStoreChanged();
		fireConnectionRemoved(connection);
	}

	private void pickNewDefaultConnection() {
		Set<IConnection> connections = connectionToConnectedServices.keySet();
		// try to get a dynamic connection
		for (IConnection connection : connections) {
			if (connection instanceof IConnection2 && 
					((IConnection2) connection).isDynamic()) {
				setDefaultConnection(connection);
				break;
			}
		}
		// otherwise, any connection will do
		if (defaultConnection == null && !connections.isEmpty())
			setDefaultConnection(connections.iterator().next());
	}

	private void disposeConnection(IConnection connection) {
		List<IConnectedService> connectedServices = connectionToConnectedServices.get(connection);
		for (IConnectedService connectedService : connectedServices) {
			connectedService.dispose();
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
		
		return null;
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
			listener.displayChanged();
		}
	}
	
	public void updateDisplays() {
		fireDisplayChanged();
	}

	public IClientServiceSiteUI getClientSiteUI(IService service) {
		return new ClientServiceSiteUI(service);
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
		if (listeners == null)
			return;
		for (IConnectionListener listener : connectionListeners) {
			listener.connectionAdded(connection);
		}
	}
	
	private void fireConnectionRemoved(IConnection connection) {
		if (listeners == null)
			return;
		for (IConnectionListener listener : connectionListeners) {
			listener.connectionRemoved(connection);
		}
	}
	
	private void fireDefaultConnectionSet(IConnection connection) {
		if (listeners == null)
			return;
		for (IConnectionListener listener : connectionListeners) {
			listener.defaultConnectionSet(connection);
		}
	}
	
	public IConnection ensureConnection(String id, IService service) throws CoreException {
		Check.checkArg(service);
		IConnection connection = findConnectionById(id);
		if (!isCompatibleConnection(connection, service)) {
			// TODO ask user to connect a device or cancel
			throw new CoreException(
					Logging.newStatus(RemoteConnectionsActivator.getDefault(), IStatus.ERROR, 
							"No compatible connection found for this id"));
		}
		return connection;
	}

	private boolean isCompatibleConnection(IConnection connection, IService service) {
		if (connection == null)
			return false;
		return getCompatibleServices(connection.getConnectionType()).contains(service);
	}

	public void setDefaultConnection(IConnection connection) {
		if (connectionToConnectedServices.keySet().contains(connection)) {
			defaultConnection = connection;
			fireDefaultConnectionSet(connection);
		}
	}

	public IConnection getDefaultConnection() {
		if (defaultConnection == null)
			pickNewDefaultConnection();
		return defaultConnection;
	}

	public void disconnect(IConnection connection) {
		// TODO transition to disconnected state and wait:
		// if not in-use, remove and stop waiting
		// if reconnected, stop waiting
		
	}

	public boolean reconnect(IConnection connection) {
		// TODO if not removed, transition out of disconnected state
		// return not removed
		return false;
	}
}