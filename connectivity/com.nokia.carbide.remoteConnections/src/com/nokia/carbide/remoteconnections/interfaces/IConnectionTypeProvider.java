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
 * An interface for accessing connection type extensions from their identifier
 * and compatible services from connection types
 */
public interface IConnectionTypeProvider {

	/**
	 * Returns IConnectionType corresponding to the id or null
	 * @param id String
	 * @return IConnectionType
	 */
	IConnectionType getConnectionType(String id);
	
	/**
	 * Returns all the known connection types
	 * @return Collection<IConnectionType>
	 */
	Collection<IConnectionType> getConnectionTypes();
	
	/**
	 * Returns a collection of IService that are supported over a connection type
	 * @param connectionType IConnectionType
	 * @return Collection<IService>
	 */
	Collection<IService> getCompatibleServices(IConnectionType connectionType);

	/**
	 * Returns IService corresponding to the id or null
	 * @param id String
	 * @return IService
	 */
	IService findServiceByID(String id);
	
	/**
	 * Returns a collection of connection type ids that support a service
	 * @param service IService
	 * @return Collection<String>
	 */
	Collection<String> getCompatibleConnectionTypeIds(IService service);
}