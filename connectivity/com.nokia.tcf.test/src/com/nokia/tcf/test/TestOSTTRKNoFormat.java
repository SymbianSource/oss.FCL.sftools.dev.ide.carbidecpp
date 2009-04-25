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
package com.nokia.tcf.test;

import java.io.IOException;

import org.eclipse.core.runtime.IStatus;

import com.nokia.tcf.api.ITCAPIConnection;
import com.nokia.tcf.api.ITCConnection;
import com.nokia.tcf.api.ITCMessage;
import com.nokia.tcf.api.ITCMessageIds;
import com.nokia.tcf.api.ITCMessageInputStream;
import com.nokia.tcf.api.ITCMessageOptions;
import com.nokia.tcf.api.ITCVirtualSerialConnection;
import com.nokia.tcf.api.TCFClassFactory;
import com.nokia.tcf.impl.TCAPIConnection;

import junit.framework.TestCase;

/**
 * This is a basic test to ping TRK using Debug/Trace USB (OST)
 * OST headers are included
 */
public class TestOSTTRKNoFormat extends TestCase {

	private boolean startServer = true; // use false when doing Junit plugin test - use true when doing non-plugin junit test

	public void testOstTRK() {
		// for non-plugin junit test
		TCAPIConnection api2 = new TCAPIConnection();
		if (startServer) {
			api2.nativeStartServer();
		}

		// connection
		ITCVirtualSerialConnection conn = (ITCVirtualSerialConnection)TCFClassFactory.createITCVirtualSerialConnection("COM27");
		// set OST decoding for this connection
		conn.setDecodeFormat("ost");
		
		// message options
		ITCMessageOptions options = TCFClassFactory.createITCMessageOptions();
		// Ask TCF to encode the protocol (we will send raw TRK messages)
		options.setMessageEncodeFormat(ITCMessageOptions.ENCODE_NO_FORMAT);
		// ask TCF to delete the protocol on incoming messages
		options.setUnWrapFormat(ITCMessageOptions.UNWRAP_LEAVE_HEADERS);
		options.setInputStreamSize(64);
		
		// message Ids to capture
		ITCMessageIds ids = TCFClassFactory.createITCMessageIds();
		// The OST msg ID for TRK responses is 0x90 per DSS
		ids.addMessageId((byte)0x90); // TRK response

		// TRK ping message
		// RAW message
		byte[] trkping = {0x01, (byte)0x90, 0x00, 0x05, 0x7e, 0, 0, (byte)0xff, 0x7e};
		// convert to ITCMessage
		ITCMessage tcMsgPing = TCFClassFactory.createITCMessage(trkping);
		// Ask TCF to use the OST msg ID for TRK requests (0x90) per DSS
//		tcMsgPing.setUseMyMessageId(true, (byte)0x90);
		
		// TRK version message
		// RAW message
		byte[] trkversion = {0x01, (byte)0x90, 0x00, 0x05, 0x7e, 0x08, 0x01, (byte)0xf6, 0x7e};
		// convert to ITCMessage
		ITCMessage tcMsgVersion = TCFClassFactory.createITCMessage(trkversion);
		// Ask TCF to use the OST msg ID for TRK requests (0x90) per DSS
//		tcMsgVersion.setUseMyMessageId(true, (byte)0x90);

		for (int iconnect = 0; iconnect < 1; iconnect++) {
			// connect
			ITCAPIConnection api = TCFClassFactory.createITCAPIConnection();
			IStatus connStatus = api.connect(conn, options, ids);
			System.out.printf("iconnect=%d connStatus=%d\n", iconnect, connStatus.getCode());
			
			// get stream reference
			ITCMessageInputStream stream = null;
			if (connStatus.isOK()) {
				stream = api.getInputStream();
				System.out.printf("stream=%s\n", stream.toString());
			}
			
			// send trk ping
			if (connStatus.isOK()) {
				IStatus sendStatus = api.sendMessage(tcMsgPing);
				if (sendStatus.isOK()) {
					// wait for response
					int numberMessages = 0;
					int numberPeeks = 0;
					int numberSleeps = 0;
					for (int i = 0; i < 100; i ++) {
						try {
							if (stream != null)
								numberMessages = stream.peekMessages();
							numberPeeks++;
							if (numberMessages < 1) {
								try {
									Thread.sleep(100);
									numberSleeps++;
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						if (numberMessages == 1) {
							break;
						}
					}
					System.out.printf("NumberMessages = %d numberPeeks = %d numberSleeps = %d\n", numberMessages, numberPeeks, numberSleeps);
					if (numberMessages > 0) {
						try {
							
							ITCMessage message = stream.readMessage();
							byte[] b = message.getMessage();
							System.out.printf("msg: length=%d\n", b.length);
							for (int i = 0; i < b.length; i++) {
								System.out.printf("%x ", b[i]);
							}
							System.out.printf("\n");
							numberMessages = stream.peekMessages();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			// send trk version
			if (connStatus.isOK()) {
				IStatus sendStatus = api.sendMessage(tcMsgVersion); 
				if (sendStatus.isOK()) {
					// wait for response
					int numberMessages = 0;
					int numberPeeks = 0;
					int numberSleeps = 0;
					for (int i = 0; i < 100; i ++) {
						try {
							if (stream != null)
								numberMessages = stream.peekMessages();
							numberPeeks++;
							if (numberMessages < 1) {
								try {
									Thread.sleep(100);
									numberSleeps++;
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						if (numberMessages == 1) {
							break;
						}
					}
					System.out.printf("NumberMessages = %d numberPeeks = %d numberSleeps = %d\n", numberMessages, numberPeeks, numberSleeps);
					if (numberMessages > 0) {
						try {
							
							ITCMessage message = stream.readMessage();
							byte[] b = message.getMessage();
							System.out.printf("msg: length=%d\n", b.length);
							for (int i = 0; i < b.length; i++) {
								System.out.printf("%x ", b[i]);
							}
							System.out.printf("\n");
							numberMessages = stream.peekMessages();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			if (connStatus.isOK()) {
				api.disconnect();
			}
		}
		// for non-plugin junit test
		if (startServer) {
			api2.nativeStopServer();
		}
	}

}
