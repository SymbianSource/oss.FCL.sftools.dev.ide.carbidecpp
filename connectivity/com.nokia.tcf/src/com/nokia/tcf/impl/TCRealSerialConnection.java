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
import com.nokia.tcf.api.ITCRealSerialConnection;
import com.nokia.tcf.api.TCErrorConstants;

public class TCRealSerialConnection extends TCBaseConnection implements
		ITCRealSerialConnection {

	private String comPort;
	private String baudRate;
	private String parity;
	private String dataBits;
	private String stopBits;
	private String flowControl;
	
	/**
	 * Create Real Serial Connection.
	 * Retry times are defaulted.
	 * All serial parameters are defaulted.
	 */
	public TCRealSerialConnection() {
		super(MEDIA_REALSERIAL);
		comPort = DEFAULT_COM_PORT;
		baudRate = DEFAULT_BAUD;
		parity = DEFAULT_PARITY;
		dataBits = DEFAULT_DATABITS;
		stopBits = DEFAULT_STOPBITS;
		flowControl = DEFAULT_FLOWCONTROL;
		decodeFormat = "ost";
	}
	/**
	 * Create Real Serial Connection with specified COM port.
	 * Retry times are defaulted.
	 * All other serial parameters are defaulted.
	 * 
	 * @param inComPort
	 */
	public TCRealSerialConnection(String inComPort) {
		super(MEDIA_REALSERIAL);
		comPort = inComPort;
		baudRate = DEFAULT_BAUD;
		parity = DEFAULT_PARITY;
		dataBits = DEFAULT_DATABITS;
		stopBits = DEFAULT_STOPBITS;
		flowControl = DEFAULT_FLOWCONTROL;
		decodeFormat = "ost";
}
	/**
	 * Create Real Serial Connection with specified COM port.
	 * Retry times are also specified.
	 * Other serial parameters are defaulted.
	 * 
	 * @param inComPort
	 * @param retryInterval
	 * @param retryTimeout
	 */
	public TCRealSerialConnection(String inComPort, long retryInterval,
			long retryTimeout) {
		super(MEDIA_REALSERIAL, retryInterval, retryTimeout);
		comPort = inComPort;
		baudRate = DEFAULT_BAUD;
		parity = DEFAULT_PARITY;
		dataBits = DEFAULT_DATABITS;
		stopBits = DEFAULT_STOPBITS;
		flowControl = DEFAULT_FLOWCONTROL;
		decodeFormat = "ost";
	}

	/**
	 * Create Real Serial connection with specified COM port and baud rate.
	 * Retry times are defaulted.
	 * All other parameters are defaulted.
	 * 
	 * @param inComPort
	 * @param inBaudRate
	 */
	public TCRealSerialConnection(String inComPort, String inBaudRate) {
		super(MEDIA_REALSERIAL);
		comPort = inComPort;
		baudRate = inBaudRate;
		parity = DEFAULT_PARITY;
		dataBits = DEFAULT_DATABITS;
		stopBits = DEFAULT_STOPBITS;
		flowControl = DEFAULT_FLOWCONTROL;
		decodeFormat = "ost";
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.internal.TCBaseConnection#getConnectionDescription()
	 */
	@Override
	public String getConnectionDescription() {
		return "RealSerial/" + comPort +"/" + baudRate + "/" + parity + "/" + dataBits + "/" + stopBits + "/" + flowControl;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCRealSerialConnection#getBaudRate()
	 */
	public String getBaudRate() {
		return baudRate;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCRealSerialConnection#getComPort()
	 */
	public String getComPort() {
		return comPort;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCRealSerialConnection#getDataBits()
	 */
	public String getDataBits() {
		return dataBits;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCRealSerialConnection#getFlowControl()
	 */
	public String getFlowControl() {
		return flowControl;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCRealSerialConnection#getParity()
	 */
	public String getParity() {
		return parity;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCRealSerialConnection#getStopBits()
	 */
	public String getStopBits() {
		return stopBits;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCRealSerialConnection#setBaudRate(java.lang.String)
	 */
	public void setBaudRate(String inBaudRate) {
		baudRate = inBaudRate;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCRealSerialConnection#setComPort(java.lang.String)
	 */
	public void setComPort(String inPort) {
		comPort = inPort;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCRealSerialConnection#setDataBits(java.lang.String)
	 */
	public void setDataBits(String inDataBits) {
		dataBits = inDataBits;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCRealSerialConnection#setFlowControl(java.lang.String)
	 */
	public void setFlowControl(String inFlowControl) {
		flowControl = inFlowControl;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCRealSerialConnection#setParity(java.lang.String)
	 */
	public void setParity(String inParity) {
		parity = inParity;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCRealSerialConnection#setStopBits(java.lang.String)
	 */
	public void setStopBits(String inStopBits) {
		stopBits = inStopBits;
	}
	public IStatus setDecodeFormat(String inDecodeFormat) {
		return super.setDecodeFormat(inDecodeFormat);
	}
}
