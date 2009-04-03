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
package com.nokia.tcf.api;

/**
 * Real Serial connections that require all serial port options.
 */
public interface ITCRealSerialConnection extends ITCConnection {

	public final String BAUD_110 = "110"; //$NON-NLS-1$
	public final String BAUD_300 = "300"; //$NON-NLS-1$
	public final String BAUD_600 = "600"; //$NON-NLS-1$
	public final String BAUD_1200 = "1200"; //$NON-NLS-1$
	public final String BAUD_2400 = "2400"; //$NON-NLS-1$
	public final String BAUD_4800 = "4800"; //$NON-NLS-1$
	public final String BAUD_9600 = "9600"; //$NON-NLS-1$
	public final String BAUD_14400 = "14400"; //$NON-NLS-1$
	public final String BAUD_19200 = "19200"; //$NON-NLS-1$
	public final String BAUD_38400 = "38400"; //$NON-NLS-1$
	public final String BAUD_56000 = "56000"; //$NON-NLS-1$
	public final String BAUD_56700 = "56700"; //$NON-NLS-1$
	public final String BAUD_115200 = "115200"; //$NON-NLS-1$
	public final String BAUD_128000 = "128000"; //$NON-NLS-1$
	public final String BAUD_256000 = "256000"; //$NON-NLS-1$
	public final String DEFAULT_BAUD = BAUD_115200;
	
	public final String PARITY_NONE = "none"; //$NON-NLS-1$
	public final String PARITY_EVEN = "even"; //$NON-NLS-1$
	public final String PARITY_ODD = "odd"; //$NON-NLS-1$
	public final String DEFAULT_PARITY = PARITY_NONE;

	public final String DATABITS_4 = "4"; //$NON-NLS-1$
	public final String DATABITS_5 = "5"; //$NON-NLS-1$
	public final String DATABITS_6 = "6"; //$NON-NLS-1$
	public final String DATABITS_7 = "7"; //$NON-NLS-1$
	public final String DATABITS_8 = "8"; //$NON-NLS-1$
	public final String DEFAULT_DATABITS = DATABITS_8;
	
	public final String STOPBITS_1 = "1"; //$NON-NLS-1$
	public final String STOPBITS_1_5 = "1.5"; //$NON-NLS-1$
	public final String STOPBITS_2 = "2"; //$NON-NLS-1$
	public final String DEFAULT_STOPBITS = STOPBITS_1;
	
	public final String FLOWCONTROL_NONE = "none"; //$NON-NLS-1$
	public final String FLOWCONTROL_HW = "hardware"; //$NON-NLS-1$
	public final String FLOWCONTROL_SW = "software"; //$NON-NLS-1$
	public final String DEFAULT_FLOWCONTROL = FLOWCONTROL_NONE;

	public final String DEFAULT_COM_PORT = "COM0"; //$NON-NLS-1$
	
	/**
	 * Set the COM Port
	 * 
	 * @param inPort
	 */
	public void setComPort(String inPort);
	/**
	 * Set the Baud Rate
	 * 
	 * @param inBaudRate
	 */
	public void setBaudRate(String inBaudRate);
	/**
	 * Set the Flow Control
	 * 
	 * @param inFlowControl
	 */
	public void setFlowControl(String inFlowControl);
	/**
	 * Set the Parity Option
	 * 
	 * @param inParity
	 */
	public void setParity(String inParity);
	/**
	 * Set the Databits
	 * 
	 * @param inDataBits
	 */
	public void setDataBits(String inDataBits);
	/**
	 * Set the Stop Bits
	 * 
	 * @param inStopBits
	 */
	public void setStopBits(String inStopBits);
	
	
	/**
	 * Get the COMM Port
	 */
	public String getComPort();
	/**
	 * Get the COMM Port
	 */
	public String getBaudRate();
	/**
	 * Get the COMM Port
	 */
	public String getFlowControl();
	/**
	 * Get the COMM Port
	 */
	public String getParity();
	/**
	 * Get the COMM Port
	 */
	public String getDataBits();
	/**
	 * Get the COMM Port
	 */
	public String getStopBits();
}
