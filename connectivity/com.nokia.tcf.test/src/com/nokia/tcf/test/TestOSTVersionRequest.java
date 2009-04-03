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
 * Tests using OST v0.3 Version Protocol
 */
public class TestOSTVersionRequest extends TestCase {

	public void testOSTVersionRequest() {
		// for non-plugin junit test
		TCAPIConnection api2 = new TCAPIConnection();
		api2.nativeStartServer();

		// connection
		ITCVirtualSerialConnection conn = (ITCVirtualSerialConnection)TCFClassFactory.createITCVirtualSerialConnection("COM25");
		// set OST decoding for this connection
		conn.setDecodeFormat("ost");
		
		// message options
		ITCMessageOptions options = TCFClassFactory.createITCMessageOptions();
		// Ask TCF to not format the protocol (we're sending entire message)
		options.setMessageEncodeFormat(ITCMessageOptions.ENCODE_NO_FORMAT);
		// ask TCF to leave the protocol headers on incoming messages
		options.setUnWrapFormat(ITCMessageOptions.UNWRAP_LEAVE_HEADERS);
		options.setInputStreamSize(64);
		
		// message Ids to capture
		ITCMessageIds ids = TCFClassFactory.createITCMessageIds();
		// This is the protocol ID to capture
		ids.addMessageId((byte)0x00); // OST version protocol byte = 0x00
//		ids.addMessageId((byte)0x02); // OST version protocol byte = 0x00

		// OST AlignVersionRequest message (including header)
		// RAW message     ver   00    len   len  trans  req    version to check (0.1)   
		byte[] alignreq = {0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x01};
		// convert to ITCMessage
		ITCMessage tcalignreq = TCFClassFactory.createITCMessage(alignreq);
		// Ask TCF to use the OST msg ID for TRK requests (0x90) per DSS
//		tcMsgPing.setUseMyMessageId(true, (byte)0x90);
		
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
			
			// send align req
			if (connStatus.isOK()) {
				IStatus sendStatus = api.sendMessage(tcalignreq);
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
				} else {
					System.out.printf("Error on send = %s\n", sendStatus.getMessage());
				}
			}
			if (connStatus.isOK()) {
				api.disconnect();
			}
		}
		// for non-plugin junit test
		api2.nativeStopServer();
	}
}
