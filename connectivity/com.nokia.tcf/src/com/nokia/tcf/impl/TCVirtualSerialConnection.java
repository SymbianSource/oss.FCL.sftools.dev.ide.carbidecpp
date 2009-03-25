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
import org.eclipse.core.runtime.Status;

import com.nokia.tcf.Activator;
import com.nokia.tcf.api.ITCVirtualSerialConnection;
import com.nokia.tcf.api.TCErrorConstants;

public class TCVirtualSerialConnection extends TCBaseConnection implements
		ITCVirtualSerialConnection {

	private String comPort;
	/**
	 *  Create Virtual Serial connection with default COM port. Retry periods are defaulted.
	 */
	public TCVirtualSerialConnection() {
		super(MEDIA_VIRTUALSERIAL);
		this.comPort = ITCVirtualSerialConnection.DEFAULT_COM_PORT;
		decodeFormat = "ost";
	}

	/**
	 * Create virtual serial connection with default COM port. Retry periods are as specified.
	 * 
	 * @param connectionType
	 * @param retryInterval
	 * @param retryTimeout
	 */
	public TCVirtualSerialConnection(long retryInterval,
			long retryTimeout) {
		super(MEDIA_VIRTUALSERIAL, retryInterval, retryTimeout);
		this.comPort = ITCVirtualSerialConnection.DEFAULT_COM_PORT;
		decodeFormat = "ost";
	}

	/**
	 * Create virtual serial connection with specified COM port. Retry periods are defaulted.
	 * 
	 * @param inPort
	 */
	public TCVirtualSerialConnection(String inPort) {
		super(MEDIA_VIRTUALSERIAL);
		this.comPort = inPort;
		decodeFormat = "ost";
	}
	/* (non-Javadoc)
	 * @see com.nokia.tcf.internal.TCBaseConnection#getConnectionDescription()
	 */
	@Override
	public String getConnectionDescription() {
		return "VirtualSerial/" + comPort;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCVirtualSerialConnection#getComPort()
	 */
	public String getComPort() {
		return comPort;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCVirtualSerialConnection#setComPort(java.lang.String)
	 */
	public IStatus setComPort(String inPort) {
		IStatus status = new Status(Status.OK, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_NONE, "OK", null);
		if (inPort == null) {
			this.comPort = ITCVirtualSerialConnection.DEFAULT_COM_PORT;
		} else {
			this.comPort = inPort;
		}
		return status;
	}

	public IStatus setDecodeFormat(String inDecodeFormat) {
		return super.setDecodeFormat(inDecodeFormat);
	}

}
