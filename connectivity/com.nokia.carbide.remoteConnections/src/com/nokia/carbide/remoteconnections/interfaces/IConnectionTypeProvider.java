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

	IConnectionType getConnectionType(String identifier);
	
	Collection<IConnectionType> getConnectionTypes();
	
	Collection<IService> getCompatibleServices(IConnectionType connectionType);

	IService findServiceByID(String id);
	
	Collection<String> getCompatibleConnectionTypeIds(IService service);
}