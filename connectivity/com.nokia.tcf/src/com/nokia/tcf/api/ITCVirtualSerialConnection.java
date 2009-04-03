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

import org.eclipse.core.runtime.IStatus;

/**
 * Virtual Serial connections are used for Bluetooth and USB connections
 * over a serial port.
 * 
 * Only the COM port is required. All other settings of serial ports are
 * defaulted.
 */
public interface ITCVirtualSerialConnection extends ITCConnection {

	/**
	 * Default constants
	 */
	public final String DEFAULT_COM_PORT = "COM0";
	
	/**
	 * Set the comm port. Default is COM0.
	 * 
	 * @param inPort
	 */
	public IStatus setComPort(String inPort);
	
	/**
	 * Get the comm port.
	 * 
	 * @return
	 */
	public String getComPort();
	
}
