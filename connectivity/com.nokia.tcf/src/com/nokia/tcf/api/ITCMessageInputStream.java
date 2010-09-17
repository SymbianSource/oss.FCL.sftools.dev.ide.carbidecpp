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

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * This interface is used to access various features of the Message Input Stream. The implementation is created
 * after a successful connection is established. To get a reference to this stream, use the 
 * {@link #ITCAPIConnection.getInputStream} method.
 * @noimplement
 * @noextend
 */
public interface ITCMessageInputStream extends java.io.Closeable {

	/**
	 * Returns number messages in current input stream. This call is non-blocking.
	 * 
	 * @return number of messages - 0 if no messages exist - this number can then be used in readMessages or readBytes
	 * 
	 * @throws IOException - if input stream is closed
	 */
	public int peekMessages() throws IOException;
	
	/**
	 * Return one message from input stream. This call is blocking until at least 1 message exists.
	 * 
	 * @return ITCMessage - message returned.
	 * 
	 * @throws IOException - if input stream is closed
	 */
	public ITCMessage readMessage() throws IOException;
	
	/**
	 * Return messages from input stream. This call is blocking until at least 1 message exists. Number of
	 * messages returned may be less than requested. 
	 * 
	 * @param inNumberMessages - maximum number of messages to return. If this is zero, then all messages
	 * found are returned.
	 * 
	 * @return ITCMessage[] - messages returned.
	 * 
	 * @throws IOException - if input stream is closed
	 */
	public ITCMessage[] readMessages(int inNumberMessages) throws IOException;

	/**
	 * Open an input stream. If the stream is already open then invoking this 
     * method has no effect. 
     * Using the {@link com.nokia.tcf.api.ITCAPIConnection#connect} api automatically opens this stream.
	 * 
	 * @throws IOException - if an IO error occurs.
	 */
	public void open() throws IOException;
	
	/**
	 * Returns true if the stream is open.
	 * 
	 * @return true - if open, false - if closed.
	 */
	public boolean isOpen();
	
	/**
	 * Return messages from input stream. This call is blocking until at least 1 message exists. Number of
	 * messages returned may be less than requested. It is up to the caller to parse the byte array
	 * for individual messages. Only whole messages are returned in the byte stream.
	 * 
	 * @param inNumberMessages - maximum number of messages to return. If this is zero, then all messages
	 * currently in the buffer are returned that will fit into a 2MB byte[] array. 
	 * 
	 * @return byte[] - byte array
	 * 
	 * @throws IOException - if an IO error occurs
	 */
	public byte[] readBytes(int inNumberMessages) throws IOException;
	
	/**
	 * Return messages from input stream. This call is blocking until at least 1 message exists. Number of
	 * messages returned may be less than requested. It is up to the caller to parse the byte array
	 * for individual messages. Only whole messages are returned in the byte stream.
	 * 
	 * @param inNumberMessages - maximum number of messages to return. If this is zero, then all messages
	 * currently in the buffer are returned that will fit into a 2MB byte[] array. 
	 * 
	 * @param outNumberMessages - the actual number of messages returned.
	 * 
	 * @return byte[] - byte array
	 * 
	 * @throws IOException - if an IO error occurs
	 */
	public byte[] readBytes(int inNumberMessages, int[] outNumberMessages) throws IOException;
	
	/**
	 * Return messages from input stream. This call is blocking until at least 1 message exists. Number of
	 * messages returned may be less than requested. It is up to the caller to parse the byte array
	 * for individual messages. Only whole messages are returned in the byte stream.
	 * 
	 * @param inNumberMessages - maximum number of messages to return. If this is zero, then all messages
	 * currently in the buffer are returned that will fit into a 2MB byte[] array. 
	 * @param timeoutMs - timeout in milliseconds (values <= 0 mean wait forever)
	 * 
	 * @return byte[] - byte array
	 * 
	 * @throws IOException - if an IO error occurs
	 * @throws TimeoutException - if no messages are received in the given time frame 
	 */
	public byte[] readBytes(int inNumberMessages, long timeoutMs) throws IOException, TimeoutException;
	

}
