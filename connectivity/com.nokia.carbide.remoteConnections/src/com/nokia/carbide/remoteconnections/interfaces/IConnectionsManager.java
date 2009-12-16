/*
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


package com.nokia.carbide.remoteconnections.interfaces;


import java.util.Collection;

import org.eclipse.core.runtime.CoreException;

/**
 * An interface encapsulating the manager of connection objects
 * @noimplement
 */
public interface IConnectionsManager {

	/**
	 * Listener interface for connection manager events
	 * (These tend to be used internally by remote connections UI)
	 * @deprecated
	 */
	public interface IConnectionsManagerListener {
		void connectionStoreChanged();
		void displayChanged();
	}

	/**
	 * Internal method for loading connections from persisted state
	 * @deprecated
	 */
	void loadConnections();

	/**
	 * Internal method for persisting connections
	 * @deprecated
	 */
	void storeConnections();

	
	/**
	 * Listener interface for connections which are added, removed and set as default
	 * @since 3.0
	 */
	public interface IConnectionListener {
		void connectionAdded(IConnection connection);
		void connectionRemoved(IConnection connection);
		void defaultConnectionSet(IConnection connection);
	}
	
	/**
	 * Add a listener for internal connection manager events
	 * @param listener IConnectionsManagerListener
	 * @deprecated
	 */
	void addConnectionStoreChangedListener(IConnectionsManagerListener listener);
	
	/**
	 * Remove a listener for internal connection manager events
	 * @param listener IConnectionsManagerListener
	 * @deprecated
	 */
	void removeConnectionStoreChangedListener(IConnectionsManagerListener listener);

	/**
	 * Add a connection to the system
	 * @param connection IConnection
	 */
	void addConnection(IConnection connection);

	/**
	 * Remove a connection from the system
	 * @param connection IConnection
	 */
	void removeConnection(IConnection connection);

	/**
	 * Return all connections in the system
	 * @return Collection<IConnection>
	 */
	Collection<IConnection> getConnections();
	
	/**
	 * Return all the connected services for a connection
	 * @param connection IConnection
	 * @return Collection<IConnectedService>
	 */
	Collection<IConnectedService> getConnectedServices(IConnection connection);
	
	/**
	 * Returns a unique id to be used for a new connection
	 * @return String
	 */
	String getUniqueConnectionId();
	
	/**
	 * Returns whether some display name is in use for any existing connections
	 * @param name String
	 * @return boolean
	 */
	boolean connectionNameInUse(String name);
	
	/**
	 * Fires internal display changed event
	 * @deprecated
	 */
	void updateDisplays();
	
	/**
	 * Returns the IClientServiceSiteUI for a service. Filters connection types to those that
	 * are supported by the service. Connection list UI as well as new and edit wizards are filtered.
	 * @param service IService
	 * @return IClientServiceSiteUI
	 */
	IClientServiceSiteUI getClientSiteUI(IService service);
	
	/**
	 * Internal method to create the connected service over a connection (for service testing)
	 * @param service IService
	 * @param connection IConnection
	 * @return IConnectedService
	 * @deprecated
	 */
	IConnectedService createConnectedService(IService service, IConnection connection);

	/**
	 * Add new IConnectionListener
	 * @param listener IConnectionListener
	 * @since 3.0
	 */
	void addConnectionListener(IConnectionListener listener);
	
	/**
	 * Remove IConnectionListener
	 * @param listener IConnectionListener
	 * @since 3.0
	 */
	void removeConnectionListener(IConnectionListener listener);

	/**
	 * Sets the default connection.
	 * @param connection IConnection
	 * @since 3.0
	 */
	void setDefaultConnection(IConnection connection);
	
	/**
	 * Can be called by specific service implementors (e.g., debugger) to ensure some connection
	 * exists and supports this service. If the connection does not exist or does not support
	 * the service, a CoreException may be thrown after the framework attempts to allow the user
	 * to correct the situation. If an IConnection is returned, it is assumed to be 
	 * a valid connection in the system that supports the service.
	 * @param connectionId String
	 * @param service IService
	 * @return IConnection
	 * @throws CoreException
	 * @since 3.0
	 */
	IConnection ensureConnection(String connectionId, IService service) throws CoreException;
	
	/**
	 * Sets a dynamic connection as disconnected. Is no-op on user generated connections.
	 * If a dynamic connection is disconnected, it is transitioned to a disconnected state while it
	 * is in use by some client service, and is eventually removed from the system once it is 
	 * no longer in use. 
	 * @param connection IConnection
	 * @since 3.0
	 */
	void disconnect(IConnection connection);
	
	/**
	 * Attempts to set a disconnected dynamic connection back to its in-use state. 
	 * If the connection has not been removed from the system, and is still in-use, 
	 * it will be restored. Returns true if successful in restoring the connection.
	 * @param connection IConnection
	 * @since 3.0
	 */
	boolean reconnect(IConnection connection);
}