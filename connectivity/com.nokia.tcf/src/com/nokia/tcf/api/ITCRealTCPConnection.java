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
package com.nokia.tcf.api;

/**
 * This interface is used for real TCP/IP connections.
 * Instantiate the implementation by using the TCFClassFactory.
 */
public interface ITCRealTCPConnection extends ITCConnection {

	/**
	 * Set the IP Address
	 * 
	 * @param inIpAddress
	 */
	public void setIpAddress(String inIpAddress);
	/**
	 * Get the IP Address
	 * @return
	 */
	public String getIpAddress();
	
	/**
	 * Set the IP port
	 * @param inIpPort
	 */
	public void setPort(String inIpPort);
	
	/**
	 * Get the IP port
	 * @return
	 */
	public String getPort();
	
}
