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
 * The user interface for picking a connection to use that can be added to client service user interfaces
 */
public interface IClientServiceSiteUI {
	
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
	 * @param connection
	 */
	void selectConnection(IConnection connection);
	
	/**
	 * Return the selected connection object - may be called after UI is disposed
	 * @return IConnection
	 */
	IConnection getSelectedConnection();
	
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
