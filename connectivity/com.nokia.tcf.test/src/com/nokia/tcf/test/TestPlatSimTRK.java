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
import java.nio.ByteBuffer;

import org.eclipse.core.runtime.IStatus;

import com.nokia.tcf.api.ITCMessage;
import com.nokia.tcf.api.ITCMessageIds;
import com.nokia.tcf.api.ITCMessageInputStream;
import com.nokia.tcf.api.ITCMessageOptions;
import com.nokia.tcf.api.ITCRealTCPConnection;
import com.nokia.tcf.api.ITCAPIConnection;
import com.nokia.tcf.api.TCFClassFactory;
import com.nokia.tcf.impl.TCAPIConnection;

import junit.framework.TestCase;

/**
 *
 */
public class TestPlatSimTRK extends TestCase {

	public void testPlatSimTRK() {
		// connection
		// PlatSim 
		ITCRealTCPConnection conn = (ITCRealTCPConnection)TCFClassFactory.createITCRealTCPConnection("127.0.0.1", "7654");
		conn.setDecodeFormat("platsim");

		// message options
		ITCMessageOptions options = TCFClassFactory.createITCMessageOptions();
		// Ask TCF to encode the protocol (we will send raw TRK messages)
		options.setMessageEncodeFormat(ITCMessageOptions.ENCODE_FORMAT);
		// ask TCF to delete the protocol on incoming messages
		options.setUnWrapFormat(ITCMessageOptions.UNWRAP_DELETE_HEADERS);
//		options.setInputStreamOverflowOption(ITCMessageOptions.INPUTSTREAM_OVERFLOW_NONE);
		options.setInputStreamSize(64);
		
		// message Ids to capture (we must capture TRK response messages = id=0x45)
		ITCMessageIds ids = TCFClassFactory.createITCMessageIds();
		// The PN msg ID for TRK responses is 0x45 per DSS
		ids.addMessageId((byte)0x45); // TRK response

		// TRK ping message
		// RAW message
		byte[] trkping = {0x7e, 0, 0, (byte)0xff, 0x7e};
		// convert to ITCMessage
		ITCMessage tcMsgPing = TCFClassFactory.createITCMessage(trkping);
		// Ask TCF to use the PN msg ID for TRK requests (0x44) per DSS
		tcMsgPing.setUseMyMessageId(true, (byte)0x44);
		
		// TRK version message
		// RAW message
		byte[] trkversion = {0x7e, 0x08, 0x01, (byte)0xf6, 0x7e};
		// convert to ITCMessage
		ITCMessage tcMsgVersion = TCFClassFactory.createITCMessage(trkversion);
		// Ask TCF to use the PN msg ID for TRK requests (0x44) per DSS
		tcMsgVersion.setUseMyMessageId(true, (byte)0x44);

		// uncomment this to do a non-plugin Junit
		TCAPIConnection api2 = new TCAPIConnection();
		api2.nativeStartServer();

		int iconnect = 0;
		for (iconnect=0; iconnect < 1; iconnect++) {
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
					for (int i = 0; i < 200; i ++) {
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
						if (numberMessages >= 1) {
							break;
						}
					}
					System.out.printf("NumberMessages = %d numberPeeks = %d numberSleeps = %d\n", numberMessages, numberPeeks, numberSleeps);
					if (numberMessages > 0) {
						try {
							
//							ITCMessage[] message = stream.readMessages(1);
//							byte[] b = stream.readBytes(1);
							ByteBuffer barray = java.nio.ByteBuffer.wrap(stream.readBytes(1));
//							byte[] b = message[0].getMessage();
							System.out.printf("msg: length=%d\n", barray.limit());
							byte[] b = barray.array();
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
					for (int i = 0; i < 200; i ++) {
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
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// uncomment this when using a non-plugin junit test
		api2.nativeStopServer();
		
	}
}
