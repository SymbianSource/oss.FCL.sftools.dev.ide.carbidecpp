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


/**
 * An interface for the extension filter extension which allows filtering extensions 
 */
public interface IExtensionFilter {

	/**
	 * For the given connection type identifier, return whether should be allowed in the current session
	 * @param connectionTypeId
	 * @return boolean
	 */
	boolean acceptConnectionType(String connectionTypeId);
	
	/**
	 * For the given service identifier, return whether should be allowed in the current session
	 * @param serviceId
	 * @return boolean
	 */
	boolean acceptService(String serviceId);
	
	/**
	 * For the given connection type and service identifier pair, 
	 * return whether the connected service should be allowed in the current session
	 * @param serviceId
	 * @return boolean
	 */
	boolean acceptConnectedService(String connectionTypeId, String serviceId);
	
}
