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


import java.util.Collection;

/**
 * An interface encapsulating the manager of connection objects
 */
public interface IConnectionsManager {

	public interface IConnectionsManagerListener {
		void connectionStoreChanged();
		void displayChanged();
	}

	void loadConnections();

	void storeConnections();

	void addConnectionStoreChangedListener(IConnectionsManagerListener listener);
	
	void removeConnectionStoreChangedListener(IConnectionsManagerListener listener);

	void addConnection(IConnection connection);

	void removeConnection(IConnection connection);

	Collection<IConnection> getConnections();
	
	Collection<IConnectedService> getConnectedServices(IConnection connection);
	
	String getUniqueConnectionId();
	
	boolean connectionNameInUse(String name);
	
	void updateDisplays();
	
	IClientServiceSiteUI getClientSiteUI(IService service);
	
	IConnectedService createConnectedService(IService service, IConnection connection);
}