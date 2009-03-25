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

public class OSTFormat {
	// OST Protocol IDs
	// Version 1 offsets
	private static final int OST_HDR_LEN_1 = 4;	// header length
	private static final int OST_VER_BYTE_1 = 0;	// byte that contains the version
	private static final int OST_PROT_BYTE_1 = 1;	// protocol byte
	private static final int OST_LEN_BYTE_1 = 2;	// first byte of length (bytes 2-3)
	private static final int OST_MSG_BYTE_1 = 4;	// start of message bytes
	
	// Protocol constants
	private static final int OST_PROT_OST_SYSTEM = (0x00);		// OST System messages
	private static final int OST_PROT_OST_ACTIVATION = (0x01); // OST Activation messages
	private static final int OST_PROT_OST_ASCII = (0x02);		// OST ASCII messages
	private static final int OST_PROT_OST_SIMPLE = (0x03);		// OST Simple messages
	private static final int OST_PROT_OST_EXTENSIBLE = (0x04);	// OST Extensible messages 
	private static final int OST_PROT_OST_SYMBIAN = (0x05);	// OST Symbian messages
	private static final int OST_PROT_TRK = (0x90);			// OST TRK messages
	private static final int OST_PROT_TC = (0x91);				// OST TraceCore messages
	private static final int OST_PROT_TC_FW_ERROR = (0xF0);	// OST TCF Error messages (not final)

	/**
	 * Creates an OST message for a raw message. Currently only supports version 0x01.
	 * @param messageLength
	 * @param ostVersion
	 * @param protocolByte
	 * @param rawMessage
	 * @return
	 */
	public static byte[] formatMessage(int messageLength, byte ostVersion, byte protocolByte, byte[] rawMessage) {
		byte[] message = new byte[OST_HDR_LEN_1 + messageLength];
		// Only OST version 0x01 is supported
		message[OST_VER_BYTE_1] = ostVersion;
		message[OST_PROT_BYTE_1] = protocolByte;
		message[OST_LEN_BYTE_1] = (byte)((messageLength >> 8) & 0xff);
		message[OST_LEN_BYTE_1+1] = (byte)(messageLength & 0xff);

		// allow for no raw bytes (header is the message)
		if (rawMessage != null) {
			for (int i = 0; i < messageLength; i++) {
				message[OST_MSG_BYTE_1+i] = rawMessage[i];
			}
		}
		return message;
	}

	// This method is not used
	public static byte[] unWrapMessage(int messageLength, byte ostVersion, byte[] message) {
		byte[] rawMessage = null;
		if (ostVersion == 1) {
			int rawMsgLen = messageLength - OST_HDR_LEN_1;
			if (rawMsgLen > 0) {
				rawMessage = new byte[rawMsgLen];
				for (int i = 0; i < rawMsgLen; i++) {
					rawMessage[i] = message[i+OST_HDR_LEN_1];
				}
			}
		} else {
			// OST version 0x01 only supported
		}
		return rawMessage;
	}
}
