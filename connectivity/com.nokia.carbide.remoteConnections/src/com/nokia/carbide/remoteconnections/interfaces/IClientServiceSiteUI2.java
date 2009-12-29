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


package com.nokia.carbide.remoteconnections.interfaces;

import org.eclipse.swt.widgets.Composite;

/**
 * The user interface for picking or defining a connection to use that can be added to client service user interfaces.
 * <p>
 * This variant allows selecting a "default connection" which will map to the currently selected default at runtime.
 * <p>
 * This requires that you use {@link IConnectionsManager#ensureConnection(String, IService)}
 * to map from a persisted connection identifier to an IConnection.  Do not rely on "validating"
 * the identifier manually by iterating the {@link IConnectionsManager#getConnections()}!
 * @noimplement
 * @noextend
 * @since 3.0
 */
public interface IClientServiceSiteUI2 {
	
	/**
	 * A listener interface to allow client sites to be notified when a new connection is selected
	 */
	public interface IListener {
		void connectionSelected();
	}

	/**
	 * Create the composite with the client site UI for IService
	 * @param parent Composite
	 */
	void createComposite(Composite parent);
	
	/**
	 * Select a specific connection object - must be called after create composite
	 * @param connection the connection id or <code>null</code>
	 */
	void selectConnection(String connection);
	
	/**
	 * Return the selected connection object - may be called after UI is disposed.
	 * <p>
	 * Do not expect to find this identifier in the {@link IConnectionsManager#getConnections()} list.  
	 * Instead, use {@link IConnectionsManager#ensureConnection(String, IService)} to find
	 * the actual IConnection at runtime.
	 * @return the connection id, which may represent a "default".  
	 */
	String getSelectedConnection();
	
	/**
	 * Get the display name of the selected connection id
	 * @param connection the connection id
	 * @return String, never <code>null</code>
	 */
	String getConnectionDisplayName(String connection);
	
	/**
	 * Adds a listener to the client site UI
	 * @param listener IListener
	 */
	void addListener(IListener listener);
	
	/**
	 * Removes a listener from the client site UI
	 * @param listener IListener
	 */
	void removeListener(IListener listener);
}
