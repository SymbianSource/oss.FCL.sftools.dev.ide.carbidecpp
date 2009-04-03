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

import java.util.Map;

/**
 * An interface for a connection object
 */
public interface IConnection {
	
	/**
	 * Set the unique identifier of this connection object
	 * @param id String
	 */
	void setIdentifier(String id);
	
	/**
	 * Return the unique identifier of this connection object
	 * @return String
	 */
	String getIdentifier();
	
	/**
	 * Set the display name of this connection object
	 * @param name String
	 */
	void setDisplayName(String name);

	/**
	 * Return the display name of this connection object
	 * @return String
	 */
	String getDisplayName();
	
	/**
	 * Destroy this connection
	 */
	void dispose();
	
	/**
	 * Return the settings for this connection as name value pairs
	 * @return Map
	 */
	Map<String, String> getSettings();

	/**
	 * Update the settings for this connection
	 * @param newSettings
	 */
	void updateSettings(Map<String, String> newSettings);
	
	/**
	 * Return the connection type for this connection
	 * @return IConnectionType
	 */
	IConnectionType getConnectionType();
	
	/**
	 * set a flag that the connection is being used
	 * @param inUse boolean
	 */
	void useConnection(boolean use);
}
