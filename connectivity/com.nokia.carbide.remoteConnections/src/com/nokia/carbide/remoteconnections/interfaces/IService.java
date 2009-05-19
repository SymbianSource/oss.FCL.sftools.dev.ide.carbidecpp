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
 * The interface for remote service extension
 */
public interface IService {

	/**
	 * A unique identifier for this remote service
	 * @return String
	 */
	String getIdentifier();
	
	/**
	 * The display name
	 * @return String
	 */
	String getDisplayName();
	
	/**
	 * Additional information about this service
	 * @return String
	 */
	String getAdditionalServiceInfo();
	
	/**
	 * Return an interface to populate a tree of installer packages or <code>null</code> if no installers
	 * @return IRemoteAgentInstallerProvider
	 */
	IRemoteAgentInstallerProvider getInstallerProvider();
	
	/**
	 * Returns whether the remote agent represented by this service can be tested for status 
	 * @return boolean
	 */
	boolean isTestable();
}
