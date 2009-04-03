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

import com.nokia.tcf.api.ITCAPIConnection;
import com.nokia.tcf.api.ITCMessage;
import com.nokia.tcf.api.ITCMessageIds;
import com.nokia.tcf.api.ITCMessageInputStream;
import com.nokia.tcf.api.ITCMessageOptions;
import com.nokia.tcf.api.ITCRealTCPConnection;
import com.nokia.tcf.api.TCFClassFactory;
import com.nokia.tcf.impl.TCAPIConnection;

import junit.framework.TestCase;

/**
 *
 */
public class TestPlatSim extends TestCase {

	public void testPlatSim() {
		// connection
		ITCRealTCPConnection conn = (ITCRealTCPConnection)TCFClassFactory.createITCRealTCPConnection("127.0.0.1", "7654");
		// set platsim decoding for this connection
		conn.setDecodeFormat("platsim");
		
		// message options
		ITCMessageOptions options = TCFClassFactory.createITCMessageOptions();
		// Ask TCF to encode the protocol (we will send raw messages)
		options.setMessageEncodeFormat(ITCMessageOptions.ENCODE_FORMAT);
		// ask TCF to delete the protocol on incoming messages
		options.setUnWrapFormat(ITCMessageOptions.UNWRAP_DELETE_HEADERS);
		options.setInputStreamSize(64);
		
		// Message ID to capture is 0x32 (TraceCore configure response)
		ITCMessageIds ids = TCFClassFactory.createITCMessageIds();
		ids.addMessageId((byte)0x32); // Tracecore response

		// TraceCore configure message
		// RAW message
		byte[] tcConfigMsg = {0x1, 0x1, 0x0, 0x0};
		// convert to ITCMessage
		ITCMessage tcTCFConfigMsg = TCFClassFactory.createITCMessage(tcConfigMsg);
		// Use the TraceCore configure message req ID (0x31)
		tcTCFConfigMsg.setUseMyMessageId(true, (byte)0x31);

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
				IStatus sendStatus = api.sendMessage(tcTCFConfigMsg);
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
				if (connStatus.isOK()) {
					api.disconnect();
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		// uncomment this when using a non-plugin junit test
		api2.nativeStopServer();
	}
}
