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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.widgets.Composite;

/**
 * The user interface for picking or defining a connection to use that can be added to client service user interfaces.
 * <p>
 * This variant allows selecting a "current connection" which will map to the currently selected connection at runtime.
 * <p>
 * This requires that you use {@link IConnectionsManager#ensureConnection(String, IService)}
 * to map from a persisted connection identifier to an IConnection.  Do not rely on "validating"
 * the identifier manually by iterating the {@link IConnectionsManager#getConnections()}!
 * @noimplement
 * @noextend
 * @since 2.5
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
	 * @return the connection id, which may represent a "current" connection.  
	 */
	String getSelectedConnection();
	
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

	/**
	 * Validate the selected connection and return a status.
	 * <p>
	 * @return IStatus for the state of the selection:
	 * <ol>
	 * <li>If a connection is selected and it's compatible, return OK.
	 * <li>If no connection is selected, return ERROR.
	 * <li>If selected connection is current, but the current connection is incompatible,
	 * return WARNING.
	 * (Normally, the concrete connections are already filtered to show only
	 * compatible ones, but the current may be anything.)  This is only a warning
	 * because the current connection can be changed externally to this UI, thus
	 * isn't a fatal error.
	 * </ol>
	 */
	IStatus getSelectionStatus();
}
