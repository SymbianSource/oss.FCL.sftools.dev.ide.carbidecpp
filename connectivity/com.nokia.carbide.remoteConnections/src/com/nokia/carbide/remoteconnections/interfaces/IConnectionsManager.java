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

import com.nokia.carbide.remoteconnections.internal.api.IConnection2;

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
	 * Return value for IConnectionsManager.ensureConnection
	 */
	public interface ISelectedConnectionInfo {
		IConnection getConnection();
		String getStorableId();
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
	 * Listener interface for connections which are added, removed and set as current
	 * @since 2.5
	 */
	public interface IConnectionListener {
		void connectionAdded(IConnection connection);
		void connectionRemoved(IConnection connection);
		void currentConnectionSet(IConnection connection);
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
	 * @deprecated use {@link #getClientSiteUI2(IService)}
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
	 * @since 2.5
	 */
	void addConnectionListener(IConnectionListener listener);
	
	/**
	 * Remove IConnectionListener
	 * @param listener IConnectionListener
	 * @since 2.5
	 */
	void removeConnectionListener(IConnectionListener listener);

	/**
	 * Sets the current connection.
	 * @param connection IConnection
	 * @since 2.5
	 */
	void setCurrentConnection(IConnection connection);
	
	/**
	 * Returns the current connection.
	 * @return IConnection
	 * @since 2.5
	 */
	IConnection getCurrentConnection();
	
	/**
	 * Returns the IClientServiceSiteUI2 for selecting a connection.  
	 * Allows selecting a "current" connection which maps to #getCurrentConnection()
	 * when you use #ensureConnection().
	 * <p>
	 * Optionally filters connection types to those that are supported by the 
	 * service. Connection list UI as well as new and edit wizards are filtered.
	 * @param service IService or <code>null</code>
	 * @return IClientServiceSiteUI2
	 * @since 2.5
	 */
	IClientServiceSiteUI2 getClientSiteUI2(IService service);
	
	/**
	 * Can be called by specific service implementors (e.g., debugger) to ensure some connection
	 * exists and optionally supports this service.  If the connection does not exist or does not support
	 * the service, a CoreException may be thrown after the framework attempts to allow the user
	 * to correct the situation by showing a connection selection dialog. 
	 * If an ISelectedConnectionInfo is returned, {@link ISelectedConnectionInfo#getConnection()} 
	 * is assumed to be a valid connection in the system that supports the service 
	 * and {@link ISelectedConnectionInfo#getStorableId()} is the id that can
 	 * be stored by the caller that represents the user's selection.
	 * @param connectionId String
	 * @param service IService or <code>null</code>
	 * @return ISelectedConnectionInfo
	 * @throws CoreException
	 * @since 2.5
	 */
	ISelectedConnectionInfo ensureConnection(String connectionId, IService service) throws CoreException;
	
	/**
	 * Returns a connection from an id (including the current connection id) or null if none found.
	 * @param connectionId String
	 * @param service IService
	 * @return IConnection
	 * @throws CoreException
	 * @since 2.5
	 */
	IConnection findConnection(String connectionId);
	
	/**
	 * Sets a dynamic connection as disconnected. Is no-op on user generated connections.
	 * If a dynamic connection is disconnected, it is transitioned to a disconnected state while it
	 * is in use by some client service, and is eventually removed from the system once it is 
	 * no longer in use. 
	 * @param connection IConnection2
	 * @since 2.5
	 */
	void disconnect(IConnection2 connection);
	
	/**
	 * Attempts to set a disconnected dynamic connection back to its in-use state. 
	 * If the connection has not been removed from the system, and is still in-use, 
	 * it will be restored. Returns true if successful in restoring the connection.
	 * @param connection IConnection2
	 * @since 2.5
	 */
	boolean reconnect(IConnection2 connection);
}