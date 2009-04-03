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

import org.eclipse.core.runtime.IPath;

import com.nokia.tcf.impl.TCAPIConnection;
import com.nokia.tcf.impl.TCMessage;
import com.nokia.tcf.impl.TCMessageIds;
import com.nokia.tcf.impl.TCMessageOptions;
import com.nokia.tcf.impl.TCRealSerialConnection;
import com.nokia.tcf.impl.TCRealTCPConnection;
import com.nokia.tcf.impl.TCVirtualSerialConnection;

/**
 * This class creates various objects that clients must use to communicate with the TCF.
 * 
 */
public class TCFClassFactory {
	/**
	 * Create the main API class
	 * 
	 * @return ITCAPIConnection
	 */
	public static ITCAPIConnection createITCAPIConnection() {
		return new TCAPIConnection();
	}
	/**
	 * Create message options with all defaults
	 * 
	 * @return ITCMessageOptions
	 */
	public static ITCMessageOptions createITCMessageOptions() {
		return new TCMessageOptions();
	}

	/**
	 * Create message options using a message output file instead of the input stream
	 * 
	 * @param filePath - path to file
	 * @return ITCMessageOptions
	 */
	public static ITCMessageOptions createITCMessageOptions(IPath filePath) {
		return new TCMessageOptions(filePath);
	}
	/**
	 * Create a message from a byte array. Assumes no formatting will be done.
	 * 
	 * @param message
	 * @return ITCMessage
	 */
	public static ITCMessage createITCMessage(byte[] message) {
		return new TCMessage(message, false, (byte)0);
	}

	/**
	 * Create a message from a byte array. No formatting is done at this point.
	 * Optionally for outgoing messages, use a specified ID. Formatting will be
	 * done by the framework, if that option is set in ITCMessageOptions.
	 * 
	 * @param message
	 * @param useMyId
	 * @param myId
	 * @return ITCMessage
	 */
	public static ITCMessage createITCMessage(byte[] message, boolean useMyId, byte myId) {
		return new TCMessage(message, useMyId, myId);
	}
	
	/**
	 * Creates empty list of message Ids
	 * 
	 * @return ITCMessageIds
	 */
	public static ITCMessageIds createITCMessageIds() {
		return new TCMessageIds();
	}
	
	/**
	 * Creates list of message Ids from Byte[] array. If Byte[] is empty, list is empty.
	 * 
	 * @param inMessageIds
	 * @return
	 */
	public static ITCMessageIds createITCMessageIds(Byte[] inMessageIds) {
		ITCMessageIds ids = new TCMessageIds();
		int size = inMessageIds.length;
		for (int i = 0; i < size; i++) {
			ids.addMessageId(inMessageIds[i]);
		}
		return ids;
	}
	
	/**
	 * Creates Virtual Serial connection object with specified COM port. Retry periods are defaulted.
	 * 
	 * @param inPort
	 * @return
	 */
	public static ITCConnection createITCVirtualSerialConnection(String inComPort) {
		return new TCVirtualSerialConnection(inComPort);
	}

	/**
	 * Creates Real Serial connection object with specified COM port. 
	 * Retry periods are defaults. All other serial parameters are defaulted.
	 * 
	 * @param inComPort
	 * @return
	 */
	public static ITCConnection createITCRealSerialConnection(String inComPort) {
		return new TCRealSerialConnection(inComPort);
	}
	/**
	 * Creates Real Serial connection object with specified COM port and baud rate. 
	 * Retry periods are defaulted.
	 * All other serial parameters are defaulted.
	 * 
	 * @param inComPort
	 * @param inBaudRate
	 * @return
	 */
	public static ITCConnection createITCRealSerialConnection(String inComPort, String inBaudRate) {
		return new TCRealSerialConnection(inComPort, inBaudRate);
	}
	/**
	 * Creates Real TCP connection with specified IP address and port. Retry periods are defaulted.
	 * 
	 * @param inIpAddress
	 * @param inPort
	 * @return
	 */
	public static ITCConnection createITCRealTCPConnection(String inIpAddress, String inPort) {
		return new TCRealTCPConnection(inIpAddress, inPort);
	}
}
