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

import java.io.IOException;

import org.eclipse.core.runtime.IStatus;

import com.nokia.tcf.api.ITCMessage;
import com.nokia.tcf.api.ITCMessageInputStream;
import com.nokia.tcf.api.TCErrorConstants;
import com.nokia.tcf.api.TCFClassFactory;

public class TCMessageInputStream implements ITCMessageInputStream {

	private long inputStreamSize;
	private long clientId;
	private TCAPIConnection api;
	private boolean isOpen = false;
	private int blockingTime = 10;
	private long MAX_BYTES = 2*1024*1024;

	/**
	 * Constructor
	 * 
	 * @param inputStreamSize
	 * @param overFlowToFile
	 * @param clientId
	 */
	public TCMessageInputStream(TCAPIConnection apiConnection, long inputStreamSize, long clientId) {
		this.inputStreamSize = inputStreamSize;
		this.clientId = clientId;
		api = apiConnection;
	}

	public int peekMessages() throws IOException {
		int number = 0;
		if (!this.isOpen) {
			// stream not open
			String errString = TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_INPUTSTREAM_CLOSED);
			IOException e = new IOException(errString);
			e.fillInStackTrace();
			throw e;
		}
		// poll for # messages in stream
		long[] outNumberTotalMessages = new long[1];
		long ret = api.nativePollInputStream(this.clientId, outNumberTotalMessages);
		if (ret != TCErrorConstants.TCAPI_ERR_NONE) {
			// error on first poll
			String errString = TCErrorConstants.getErrorMessage((int)ret);
			IOException e = new IOException(errString);
			e.fillInStackTrace();
			throw e;
		}
		number = (int)outNumberTotalMessages[0];
		
		return number;
	}

	public boolean isOpen() {
		return this.isOpen;
	}

	public void open() throws IOException {
		if (!this.isOpen) {
			// Remove overflow to file and input stream file path arguments from nativeOpenInputStream()
			long ret = api.nativeOpenInputStream(clientId, null, inputStreamSize, false);
			if (ret == TCErrorConstants.TCAPI_ERR_NONE) {
				isOpen = true;
			} else {
				String errString = TCErrorConstants.getErrorMessage((int)ret);
				IOException e = new IOException(errString);
				e.fillInStackTrace();
				throw e;
			}
		}
	}

	public ITCMessage readMessage() throws IOException {
		ITCMessage message = null;

		// if not open throw exception
		if (!this.isOpen) {
			// stream not open
			String errString = TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_INPUTSTREAM_CLOSED);
			IOException e = new IOException(errString);
			e.fillInStackTrace();
			throw e;
		}

		// poll for # messages in stream
		long[] outNumberTotalMessages = new long[1];
		outNumberTotalMessages[0] = 0;
		do {
			if (!this.isOpen) {
				// stream not open
				String errString = TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_INPUTSTREAM_CLOSED);
				IOException e = new IOException(errString);
				e.fillInStackTrace();
				throw e;
			}
			long ret = api.nativePollInputStream(this.clientId, outNumberTotalMessages);
			if (ret != TCErrorConstants.TCAPI_ERR_NONE) {
				// error on first poll
				String errString = TCErrorConstants.getErrorMessage((int)ret);
				IOException e = new IOException(errString);
				e.fillInStackTrace();
				throw e;
			}
			if (outNumberTotalMessages[0] == 0) {
				try {
					Thread.sleep(blockingTime);
				} catch (InterruptedException e) {
					break;
				}
			}
		} while (outNumberTotalMessages[0] == 0);
		
		// check # in stream
		if (outNumberTotalMessages[0] <= 0) {
			// no messages in stream
			return message;
		}
		// get number of bytes in first message
		long[] outNumberBytesInMessage = new long[1];
		long ret = api.nativeGetInputStreamMessageBytes(this.clientId, 1, outNumberBytesInMessage);
		if (ret != TCErrorConstants.TCAPI_ERR_NONE) {
			// error on getting bytes in message
			String errString = TCErrorConstants.getErrorMessage((int)ret);
			IOException e = new IOException(errString);
			e.fillInStackTrace();
			throw e;
		}

		// OK, read first message
		long totalBytes = outNumberBytesInMessage[0];
		byte[] outMessage = new byte[(int)totalBytes];
		long[] outNumberMessages = new long[1];
		long[] outNumberBytesRead = new long[1];
		ret = api.nativeReadInputStream(this.clientId, 1, outNumberMessages, outNumberBytesRead, totalBytes, outMessage);
		if (ret != TCErrorConstants.TCAPI_ERR_NONE) {
			// error on read message
			String errString = TCErrorConstants.getErrorMessage((int)ret);
			IOException e = new IOException(errString);
			e.fillInStackTrace();
			throw e;
		}
		
		// OK, pass back the message
		message = TCFClassFactory.createITCMessage(outMessage);
		
		return message;
	}

	public ITCMessage[] readMessages(int inNumberMessages) throws IOException {
		ITCMessage[] messages = null;

		// if not open throw exception
		if (!this.isOpen) {
			// stream not open
			String errString = TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_INPUTSTREAM_CLOSED);
			IOException e = new IOException(errString);
			e.fillInStackTrace();
			throw e;
		}
		// poll for # messages in stream
		long[] outNumberTotalMessages = new long[1];
		outNumberTotalMessages[0] = 0;
		do {
			if (!this.isOpen) {
				// stream not open
				String errString = TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_INPUTSTREAM_CLOSED);
				IOException e = new IOException(errString);
				e.fillInStackTrace();
				throw e;
			}
			long ret = api.nativePollInputStream(this.clientId, outNumberTotalMessages);
			if (ret != TCErrorConstants.TCAPI_ERR_NONE) {
				// error on first poll
				String errString = TCErrorConstants.getErrorMessage((int)ret);
				IOException e = new IOException(errString);
				e.fillInStackTrace();
				throw e;
			}
			if (outNumberTotalMessages[0] == 0) {
				try {
					Thread.sleep(blockingTime);
				} catch (InterruptedException e) {
					break;
				}
			}
		} while(outNumberTotalMessages[0] == 0);
		
		// check # in stream
		if (outNumberTotalMessages[0] <= 0) {
			// no messages in stream
			return messages;
		}
		if (inNumberMessages == 0) {
			inNumberMessages = (int)outNumberTotalMessages[0];
		} else if (inNumberMessages > outNumberTotalMessages[0]) {
			inNumberMessages = (int)outNumberTotalMessages[0];
		}
		// get number of bytes in each message
		long[] outNumberBytesInMessages = new long[inNumberMessages];
		long ret = api.nativeGetInputStreamMessageBytes(this.clientId, inNumberMessages, outNumberBytesInMessages);
		if (ret != TCErrorConstants.TCAPI_ERR_NONE) {
			// error on getting bytes in message
			String errString = TCErrorConstants.getErrorMessage((int)ret);
			IOException e = new IOException(errString);
			e.fillInStackTrace();
			throw e;
		}
		// read all requested messages
		int totalBytes = 0;
		for (int i = 0; i < inNumberMessages; i++) {
			totalBytes += outNumberBytesInMessages[i];
		}
		if (totalBytes > MAX_BYTES) totalBytes = (int)MAX_BYTES;
		byte[] messageData = new byte[totalBytes];
		long[] outNumberMessages = new long[1];
		long[] outNumberBytesRead = new long[1];
		ret = api.nativeReadInputStream(this.clientId, inNumberMessages, outNumberMessages, outNumberBytesRead, totalBytes, messageData);
		if (ret != TCErrorConstants.TCAPI_ERR_NONE) {
			// error on getting bytes in message
			String errString = TCErrorConstants.getErrorMessage((int)ret);
			IOException e = new IOException(errString);
			e.fillInStackTrace();
			throw e;
		}
		int messageDataStart = 0;
		messages = new TCMessage[(int)outNumberMessages[0]];
		for (int i = 0; i < outNumberMessages[0]; i++) {
			int size = (int)outNumberBytesInMessages[i];
			byte[] data = new byte[size];
			for (int j = 0; j < size; j++) {
				data[j] = messageData[messageDataStart + j];
			}
			messages[i] = TCFClassFactory.createITCMessage(data);
			messageDataStart += size;
		}
		return messages;
	}

	public void close() throws IOException {
		if (this.isOpen) {
			// Stop capturing first
			IStatus status = api.stop();
			if (status.isOK()) {
				// ok, now close the stream
				long ret = api.nativeCloseInputStream(this.clientId);
				if (ret == TCErrorConstants.TCAPI_ERR_NONE) {
					isOpen = false;
				} else {
					String errString = TCErrorConstants.getErrorMessage((int)ret);
					IOException e = new IOException(errString);
					e.fillInStackTrace();
					throw e;
				}
			} else {
				String errString = status.getMessage();
				IOException e = new IOException(errString);
				e.fillInStackTrace();
				throw e;
			}
		}		
	}
	public byte[] readBytes(int inNumberMessages, long timeoutMs) throws IOException {
		// if not open throw exception
		if (!this.isOpen) {
			// stream not open
			String errString = TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_INPUTSTREAM_CLOSED);
			IOException e = new IOException(errString);
			e.fillInStackTrace();
			throw e;
		}
//		inNumberMessages = 0; // 0 --> get all that fit in MAX_BYTES
		byte[] messageData = new byte[(int) MAX_BYTES];
		long[] outNumberMessagesRead = new long[1];
		outNumberMessagesRead[0] = 0;
		long[] outNumberBytesRead = new long[1];
		long deadline = timeoutMs > 0 ? System.currentTimeMillis() + timeoutMs : Long.MAX_VALUE;
		do {
			long ret = api.nativeReadInputStream(this.clientId, inNumberMessages, outNumberMessagesRead, outNumberBytesRead, MAX_BYTES, messageData);
			if (ret != TCErrorConstants.TCAPI_ERR_NONE) {
				// error on getting bytes in message
				String errString = TCErrorConstants.getErrorMessage((int)ret);
				IOException e = new IOException(errString);
				e.fillInStackTrace();
				throw e;
			}
			if (outNumberMessagesRead[0] == 0) {
				try {
					Thread.sleep(blockingTime);
				} catch (InterruptedException e) {
					break;
				}
			}
			if (timeoutMs != 0)
				timeoutMs -= blockingTime;
		} while (outNumberMessagesRead[0] == 0 && System.currentTimeMillis() < deadline);

		// return how many messages actually processed
		byte[] newMessageData = new byte[(int)outNumberBytesRead[0]];
		for (int i = 0; i < outNumberBytesRead[0]; i++) {
			newMessageData[i] = messageData[i]; 
		}
		return newMessageData;
	}
	
	public byte[] readBytes(int inNumberMessages) throws IOException {
		return readBytes(inNumberMessages, 0);
	}
	
	public byte[] readBytes(int inNumberMessages, int[] outNumberMessages) throws IOException {
		outNumberMessages[0] = 0;
		
		// if not open throw exception
		if (!this.isOpen) {
			// stream not open
			String errString = TCErrorConstants.getErrorMessage(TCErrorConstants.TCAPI_ERR_INPUTSTREAM_CLOSED);
			IOException e = new IOException(errString);
			e.fillInStackTrace();
			throw e;
		}
		inNumberMessages = 0; // 0 --> get all that fit in MAX_BYTES
		byte[] messageData = new byte[(int) MAX_BYTES];
		long[] outNumberMessagesRead = new long[1];
		outNumberMessagesRead[0] = 0;
		long[] outNumberBytesRead = new long[1];
		do {
			long ret = api.nativeReadInputStream(this.clientId, inNumberMessages, outNumberMessagesRead, outNumberBytesRead, MAX_BYTES, messageData);
			if (ret != TCErrorConstants.TCAPI_ERR_NONE) {
				// error on getting bytes in message
				String errString = TCErrorConstants.getErrorMessage((int)ret);
				IOException e = new IOException(errString);
				e.fillInStackTrace();
				throw e;
			}
			if (outNumberMessagesRead[0] == 0) {
				try {
					Thread.sleep(blockingTime);
				} catch (InterruptedException e) {
					break;
				}
			}
		} while (outNumberMessagesRead[0] == 0);
		
		// return how many messages actually processed
		byte[] newMessageData = new byte[(int)outNumberBytesRead[0]];
		for (int i = 0; i < outNumberBytesRead[0]; i++) {
			newMessageData[i] = messageData[i]; 
		}
		outNumberMessages[0] = (int)outNumberMessagesRead[0];
		return newMessageData;
		
	}
}
