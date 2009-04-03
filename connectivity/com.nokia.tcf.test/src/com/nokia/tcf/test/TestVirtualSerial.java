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
import com.nokia.tcf.api.ITCMessage;
import com.nokia.tcf.api.ITCMessageIds;
import com.nokia.tcf.api.ITCMessageInputStream;
import com.nokia.tcf.api.ITCMessageOptions;
import com.nokia.tcf.api.ITCVirtualSerialConnection;
import com.nokia.tcf.api.ITCConnection;
import com.nokia.tcf.api.TCFClassFactory;
import junit.framework.TestCase;

/**
 * 1 client 1 connection RAW TRK (non-TraceCore TRK) over USB
 */
public class TestVirtualSerial extends TestCase {

	public void testVirtualSerial() {
//		TCFClassFactory factory = new TCFClassFactory();
		
		// connection
		ITCVirtualSerialConnection conn = (ITCVirtualSerialConnection)TCFClassFactory.createITCVirtualSerialConnection("COM13");
		conn.setRetryInterval(5);
		conn.setRetryTimeout(60);
		String desc = conn.getConnectionDescription();
		System.out.println(desc);
		assertTrue("description != null", desc != null);
		
		// messageIds
		ITCMessageIds messageIds = TCFClassFactory.createITCMessageIds();
		messageIds.addMessageId((byte)0x7E);
		
		// messageOptions - all default
		ITCMessageOptions messageOptions = TCFClassFactory.createITCMessageOptions();
		conn.setDecodeFormat("rawtrk");
		messageOptions.setMessageEncodeFormat(ITCMessageOptions.ENCODE_NO_FORMAT);
		ITCAPIConnection api = TCFClassFactory.createITCAPIConnection();
		IStatus connStatus = api.connect(conn, messageOptions, messageIds);
		assertTrue("connect is not ok", connStatus.isOK());

		// message
		int[] trkpingi = {0x7e, 00, 00, 0xff, 0x7e};
		byte[] trkping = new byte[5];
		for (int i = 0; i < 5; i++) trkping[i] = (byte)(trkpingi[i] & 0xff);
		ITCMessage tcMsgPing = TCFClassFactory.createITCMessage(trkping);
		System.out.println("ping message:");
		for (int i = 0; i < tcMsgPing.size(); i++) {
			System.out.printf(" %x", tcMsgPing.getMessage()[i]);			
		}
		System.out.printf("\n");
		tcMsgPing.setUseMyMessageId(false, (byte)0);
		
		int[] trkversioni = {0x7e, 0x08, 0x01, 0xf6, 0x7e};
		byte[] trkversion = new byte[5];
		for (int i = 0; i < 5; i++) trkversion[i] = (byte)(trkversioni[i] & 0xff);
		ITCMessage tcMsgVersion = TCFClassFactory.createITCMessage(trkversion);
		System.out.println("version message:");
		for (int i = 0; i < tcMsgVersion.size(); i++) {
			System.out.printf(" %x", tcMsgVersion.getMessage()[i]);			
		}
		System.out.printf("\n");
		tcMsgVersion.setUseMyMessageId(false, (byte)0);
	
		
		IStatus sendStatus = null;

		if (connStatus.isOK()) {
			sendStatus = api.sendMessage(tcMsgPing);
			sendStatus = api.sendMessage(tcMsgVersion);
		}

		ITCMessageInputStream stream = api.getInputStream();
		ITCMessage msg = null;
		int numberMessages = 0;
		try {
			numberMessages = stream.peekMessages();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.printf("numberMessages = %d\n", numberMessages);
		if (numberMessages > 0) {
			try {
				msg = stream.readMessage();
				System.out.printf("messageSize = %d\n", msg.size());
				if (msg.size() > 0) {
					for (int i = 0; i < msg.size(); i++) {
						System.out.printf(" %x", msg.getMessage()[i]);
					}
					System.out.printf("\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// shutdown BT on phone then execute this
		if (connStatus.isOK()) {
			sendStatus = api.sendMessage(tcMsgPing);
			sendStatus = api.sendMessage(tcMsgVersion);
		}
		
		numberMessages = 0;
		try {
			numberMessages = stream.peekMessages();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.printf("numberMessages = %d\n", numberMessages);
		if (numberMessages > 0) {
			try {
				msg = stream.readMessage();
				System.out.printf("messageSize = %d\n", msg.size());
				if (msg.size() > 0) {
					for (int i = 0; i < msg.size(); i++) {
						System.out.printf(" %x", msg.getMessage()[i]);
					}
					System.out.printf("\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (connStatus.isOK()) {
			api.disconnect();
		}
		
	}
}
