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
/**
 * 
 */
package com.nokia.tcf.impl;

import org.eclipse.core.runtime.IStatus;

import com.nokia.tcf.api.ITCRealTCPConnection;

/**
 * A real TCP/IP connection - used as basis for all TCP/IP connection types
 */
public class TCRealTCPConnection extends TCBaseConnection implements
		ITCRealTCPConnection {

	protected String ipAddress;
	protected String ipPort;

	/**
	 * Create TCP Connection with specified IP Address and Port.
	 * Retry times are default.
	 * 
	 * @param inIpAddress
	 * @param inIpPort
	 */
	public TCRealTCPConnection(String inIpAddress, String inPort) {
		super(MEDIA_REALTCP);
		ipAddress = inIpAddress;
		ipPort = inPort;
		decodeFormat = "ost";
	}
	/**
	 * Create TCP Connection with specified:
	 * 	IP Address and port
	 *  Retry times
	 *  
	 * @param inIpAddress
	 * @param inIpPort
	 * @param retryInterval
	 * @param retryTimeout
	 */
	public TCRealTCPConnection(String inIpAddress, String inIpPort, long retryInterval, long retryTimeout) {
		super(MEDIA_REALTCP, retryInterval, retryTimeout);
		ipAddress = inIpAddress;
		ipPort = inIpPort;
		decodeFormat = "ost";
		
	}
	/* (non-Javadoc)
	 * @see com.nokia.tcf.internal.TCBaseConnection#getConnectionDescription()
	 */
	@Override
	public String getConnectionDescription() {
		return "RealTCP/" + ipAddress + "/" + ipPort;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCRealTCPConnection#getIpAddress()
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCRealTCPConnection#getIpPort()
	 */
	public String getPort() {
		return ipPort;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCRealTCPConnection#setIpAddress(java.lang.String)
	 */
	public void setIpAddress(String inIpAddress) {
		ipAddress = inIpAddress;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCRealTCPConnection#setIpPort(java.lang.String)
	 */
	public void setPort(String inPort) {
		ipPort = inPort;
	}
	public IStatus setDecodeFormat(String inDecodeFormat) {
		return super.setDecodeFormat(inDecodeFormat);
	}

}
