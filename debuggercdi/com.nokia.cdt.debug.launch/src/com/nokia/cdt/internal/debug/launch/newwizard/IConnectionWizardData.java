/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.cdt.internal.debug.launch.newwizard;

import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IService;

/**
 * Interface for connection wizard data
 */
public interface IConnectionWizardData {

	/**
	 * The service the connection must support
	 * @return IService
	 */
	IService getService();

	/**
	 * Set the connection
	 * @param connection IConnection
	 */
	void setConnection(IConnection connection);

	/**
	 * Return the connection
	 * @return IConnection
	 */
	IConnection getConnection();

	/**
	 * Return the connection name or null
	 * @return String
	 */
	String getConnectionName();


}
