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

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.nokia.tcf.Activator;
import com.nokia.tcf.api.ITCMessageOptions;
import com.nokia.tcf.api.TCErrorConstants;

public class TCMessageOptions implements ITCMessageOptions {

	private long encodeFormat;
	private byte ostVersion;
	private long unWrapFormat;
	private long inputStreamSize;
	private IStatus statusOK;
	private long messageDestination;
	private IPath messageFile;
	
	
	/**
	 * All parameters are defaulted.
	 */
	public TCMessageOptions() {
		super();
		encodeFormat = ITCMessageOptions.DEFAULT_ENCODE_FORMAT;
		inputStreamSize = ITCMessageOptions.DEFAULT_BUFFER_SIZE;
		ostVersion = ITCMessageOptions.DEFAULT_OST_VERSION;
		unWrapFormat = ITCMessageOptions.DEFAULT_UNWRAP_OPTION;
		messageDestination = ITCMessageOptions.DEFAULT_DESTINATION;
		statusOK = new Status(Status.OK, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_NONE, "OK", null);
	}

	/**
	 * Uses a client specified message file instead of the input stream.
	 * All other parameters are defaulted.
	 * 
	 * @param inMessageFile - full path to client's file to write.
	 */
	public TCMessageOptions(IPath inMessageFile) {
		this();
		messageDestination = ITCMessageOptions.DESTINATION_CLIENTFILE;
		messageFile = inMessageFile;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageOptions#getInputStreamSize()
	 */
	public long getInputStreamSize() {
		return this.inputStreamSize;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageOptions#getMessageEncodeFormat()
	 */
	public long getMessageEncodeFormat() {
		return this.encodeFormat;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageOptions#getOSTVersion()
	 */
	public byte getOSTVersion() {
		return this.ostVersion;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageOptions#getUnWrapFormat()
	 */
	public long getUnWrapFormat() {
		return this.unWrapFormat;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageOptions#setInputStreamSize(long)
	 */
	public IStatus setInputStreamSize(long inBufferSize) {
		IStatus status = this.statusOK; 
		this.inputStreamSize = inBufferSize;
		// parameter is check when used
		return status;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageOptions#setMessageEncodeFormat(long)
	 */
	public IStatus setMessageEncodeFormat(long inMessageEncodeFormat) {
		IStatus status = this.statusOK; 
		this.encodeFormat = inMessageEncodeFormat;
		// parameter is check when used
		return status;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageOptions#setOSTVersion(byte)
	 */
	public IStatus setOSTVersion(byte inOSTVersion) {
		IStatus status = this.statusOK; 
		this.ostVersion = inOSTVersion;
		// parameter is check when used
		return status;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageOptions#setUnWrapFormat(long)
	 */
	public IStatus setUnWrapFormat(long inUnWrapFormat) {
		IStatus status = this.statusOK; 
		this.unWrapFormat = inUnWrapFormat;
		// parameter is check when used
		return status;
	}

	/*
	 * (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageOptions#getMessageDestination()
	 */
	public long getMessageDestination() {
		return messageDestination;
	}

	/*
	 * (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageOptions#getMessageFile()
	 */
	public IPath getMessageFile() {
		return messageFile;
	}

	/*
	 * (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageOptions#setMessageDestination(long)
	 */
	public IStatus setMessageDestination(long inDestination) {
		IStatus status = this.statusOK; 
		messageDestination = inDestination;
		return status;
	}

	/*
	 * (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageOptions#setMessageFile(java.lang.String)
	 */
	public IStatus setMessageFile(IPath inFile) {
		IStatus status = this.statusOK;
		messageFile = inFile;
		return status;
	}
}
