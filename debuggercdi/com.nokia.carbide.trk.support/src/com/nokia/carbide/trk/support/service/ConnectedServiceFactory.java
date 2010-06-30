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


package com.nokia.carbide.trk.support.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.nokia.carbide.remoteconnections.interfaces.AbstractSynchronizedConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedServiceFactory;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IService;
import com.nokia.carbide.trk.support.connection.SerialBTConnectionType;
import com.nokia.carbide.trk.support.connection.SerialConnectionType;
import com.nokia.carbide.trk.support.connection.USBConnectionType;
import com.nokia.cpp.internal.api.utils.core.HostOS;

/**
 *
 */
public class ConnectedServiceFactory implements IConnectedServiceFactory {

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectedServiceFactory#createConnectedService(com.nokia.carbide.remoteconnections.interfaces.IService, com.nokia.carbide.remoteconnections.interfaces.IConnection)
	 */
	public IConnectedService createConnectedService(IService service, IConnection connection) {
		if (service instanceof TracingService &&
				isCompatibleConnection(getCompatibleTracingConnectionTypeIds(), connection)) {
			if (HostOS.IS_UNIX)
				return new RemoteConnectedService(service);		// TODO: not ported
			return new TracingConnectedService(service, (AbstractSynchronizedConnection) connection);
		}
		else if (service instanceof TRKService &&
				isCompatibleConnection(getCompatibleTRKConnectionTypeIds(), connection)) {
			if (HostOS.IS_UNIX)
				return new RemoteConnectedService(service);	// TODO: not ported
			return new TRKConnectedService(service, (AbstractSynchronizedConnection) connection);
		}
		return null;
	}
	
	private static boolean isCompatibleConnection(Collection<String> compatibleIds, IConnection connection) {
		String connectionTypeId = connection.getConnectionType().getIdentifier();
		for (String id : compatibleIds) {
			if (connectionTypeId.equals(id))
				return true;
		}
		
		return false;
	}

	private Collection<String> getCompatibleTracingConnectionTypeIds() {
		return Arrays.asList(new String[] {
				USBConnectionType.ID,
		});
	}
	
	private Collection<String> getCompatibleTRKConnectionTypeIds() {
		return Arrays.asList(new String[] {
				SerialConnectionType.ID,
				SerialBTConnectionType.ID,
				USBConnectionType.ID,
				}); 
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectedServiceFactory#getCompatibleConnectionTypeIds(com.nokia.carbide.remoteconnections.interfaces.IService)
	 */
	public Collection<String> getCompatibleConnectionTypeIds(IService service) {
		if (service instanceof TRKService)
			return getCompatibleTRKConnectionTypeIds();
		else if (service instanceof TracingService)
			return getCompatibleTracingConnectionTypeIds();
		return Collections.emptyList();
	}

}
