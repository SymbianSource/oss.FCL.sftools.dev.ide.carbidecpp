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
import org.eclipse.core.runtime.IStatus;

/**
 * 
 * This interface specifies the message handling options for this client.
 * Use the TCFClassFactory to instantiate a copy of the implementation class.
 */
public interface ITCMessageOptions {

	/**
	 * Message destination options
	 */
	public final long DESTINATION_INPUTSTREAM = 0;
	public final long DESTINATION_CLIENTFILE = 1;
	public final long DEFAULT_DESTINATION = DESTINATION_INPUTSTREAM;
	
	/**
	 * Default Input Stream buffer size
	 */
//	public final long DEFAULT_BUFFER_SIZE = (128*1024);	
	public final long DEFAULT_BUFFER_SIZE = (20*1024*1024); // changed to 20M for 2.0

	/**
	 * Incoming message unwrap options
	 */
	public final long UNWRAP_LEAVE_HEADERS = 0;
	public final long UNWRAP_DELETE_HEADERS = 1;
	public final long DEFAULT_UNWRAP_OPTION = UNWRAP_LEAVE_HEADERS;

	/**
	 * Outgoing message encoding
	 * This option only requests the TCF to encode this client's messages or use them as-is.
	 * The protocol format used is specified by ITCConnection.
	 */
	public final long ENCODE_NO_FORMAT = 0;
	public final long ENCODE_FORMAT = 1;
	public final long ENCODE_TRK_FORMAT = 2;
	public final long DEFAULT_ENCODE_FORMAT = ENCODE_NO_FORMAT;
	/**
	 * Default OST Version to use on outgoing messages if encoding format is on and 
	 * protocol specified in ITCConnection is OST.
	 */
	public final byte DEFAULT_OST_VERSION = 0x01;

	/**
	 * Returns setting of message destination (file or input stream).
	 * @see ITCMessageOptions#setMessageDestination(long)
	 * 
	 * @return
	 */
	public long getMessageDestination();
	
	/**
	 * If destination option is file, then this returns the path to the message file selected.
	 * @see ITCMessageOptions#setMessageFile(String)
	 * @return file - the current full path to the client's file
	 */
	public IPath getMessageFile();
		
	/**
	 * Return Input Stream buffer size.
	 * @see ITCMessageOptions#setInputStreamSize(long)
	 * @return outBufferSize - the current size of the buffer
	 */
	public long getInputStreamSize();
	
	/**
	 * Returns which message format to encode on outgoing messages:
	 * @see ITCMessageOptions#setMessageEncodeFormat(long)
	 * <p>
	 * Note: The protocol format is specified as part of the 
	 * {@link com.nokia.tcf.ITCConnection} interface.
	 * 
	 * @return
	 */
	public long getMessageEncodeFormat();
	/**
	 * Return OST Version to use on outgoing messages.
	 * @see ITCMessageOptions#setOSTVersion(byte)
	 * @return outOSTVersion - the OST version selected
	 */
	public byte getOSTVersion();
	
	/**
	 * Returns incoming message formatting
	 * @see ITCMessageOptions#setUnWrapFormat(long) 
	 * @return
	 */
	public long getUnWrapFormat();

	/**
	 * Specify the destination of messages (file or input stream)
	 * <li>{@link ITCMessageOptions#DESTINATION_INPUTSTREAM}: messages are written to input stream (client must then poll this stream) (default).
	 * <li>{@link ITCMessageOptions#DESTINATION_CLIENTFILE}: messages are written directly to a client specified file.
	 * <p>
	 * If destination is file, use {@link ITCMessageOptions#setMessageFile(String)} to specify the file.
	 * Currently this is work-in-progress. Only Input Stream is allowed at this time.
	 * 
	 * @param inDestination 
	 * @return
	 */
	public IStatus setMessageDestination(long inDestination);

	/**
	 * If message destination is file, then this specifies the path to the message file.
	 * Refer to {@link ITCMessageOptions#setMessageDestination(long)} for more explanation.
	 * 
	 * @param inFile - full path to the client's file to write
	 * @return
	 */
	public IStatus setMessageFile(IPath inFile);
	
	/**
	 * Specify the maximum size of the Input Stream buffer.
	 * Default is currently 20MB
	 * 
	 * @param inBufferSize
	 */
	public IStatus setInputStreamSize(long inBufferSize);
	/**
	 * Specify which message format to encode on outgoing messages:
	 * {@link ITCMessageOptions#ENCODE_NO_FORMAT}: do not encode outgoing messages (client must format the protocol) (default).
	 * {@link ITCMessageOptions#ENCODE_FORMAT}: format the message for protocol.
	 * {@link ITCMessageOptions#ENCODE_TRK_FORMAT}: outgoing messages for TRK need special processing with certain protocols.
	 * 
	 * Note: The protocol type is specified in the ITCConnection interface.
	 * 
	 * @param inMessageEncodeFormat
	 */
	public IStatus setMessageEncodeFormat(long inMessageEncodeFormat);
	
	/**
	 * Specify which OST version to encode in outgoing messages.
	 * If encoding option is different, this is ignored.
	 * Default is 0x01.
	 * 
	 * @param inOSTVersion
	 */
	public IStatus setOSTVersion(byte inOSTVersion);
	/**
	 * Specify whether to delete headers from incoming messages
	 * <li>{@link ITCMessageOptions#UNRAP_LEAVE_HEADERS}: protocol headers are left as-is (default).
	 * <li>{@link ITCMessageOptions#UNWRAP_DELETE_HEADERS}: protocol headers are deleted before passing to client.
	 * 
	 * @param inUnWrapFormat
	 */
	public IStatus setUnWrapFormat(long inUnWrapFormat);
	
}
