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
 * The interface for creating connected services
 */
public interface IConnectedServiceFactory {

	/**
	 * Create an instance of a connected service from a connection and a service
	 * @param connection IConnection
	 * @return IConnectedService
	 */
	IConnectedService createConnectedService(IService service, IConnection connection);
	
	/**
	 * Return a collection of ids for connection types compatible with service
	 * @param service IService
	 * @return Collection
	 */
	Collection<String> getCompatibleConnectionTypeIds(IService service);
	
}
