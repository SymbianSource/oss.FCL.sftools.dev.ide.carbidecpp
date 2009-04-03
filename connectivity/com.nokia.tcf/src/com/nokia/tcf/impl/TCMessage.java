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

import com.nokia.tcf.api.ITCMessage;

public class TCMessage implements ITCMessage {

	private byte[] message;
	private boolean useMyMessageId;
	private byte myMessageId;

	/**
	 *  Creates empty message. Using specific message ID is false.
	 */
	public TCMessage() {
		super();
		this.message = null;
		this.useMyMessageId = false;
		this.myMessageId = 0;
	}
	/**
	 * Creates message from byte array. Using specific message ID is false
	 * 
	 * @param message
	 */
	public TCMessage(byte[] message) {
		super();
		this.message = message;
		this.useMyMessageId = false;
		this.myMessageId = 0;
	}
	/**
	 * Creates message from byte array. Using specific message ID is passed in.
	 * 
	 * @param message (may be null if sending only header)
	 * @param useMyMessageId
	 * @param myMessageId
	 */
	public TCMessage(byte[] message, boolean useMyMessageId, byte myMessageId) {
		super();
		this.message = message;
		this.useMyMessageId = useMyMessageId;
		this.myMessageId = myMessageId;
	}
	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessage#getMessage()
	 */
	public byte[] getMessage() {
		return this.message;
	}
	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessage#getMyMessageId()
	 */
	public byte getMyMessageId() {
		return this.myMessageId;
	}
	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessage#isUseMyMessageId()
	 */
	public boolean isUseMyMessageId() {
		return this.useMyMessageId;
	}
	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessage#setMessage(byte[])
	 */
	public void setMessage(byte[] inMessage) {
		this.message = inMessage;		
	}
	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessage#setUseMyMessageId(boolean, byte)
	 */
	public void setUseMyMessageId(boolean inUseMyMessageId, byte inMyId) {
		this.useMyMessageId = inUseMyMessageId;
		if (inUseMyMessageId) {
			this.myMessageId = inMyId;
		} else {
			this.myMessageId = 0;
		}
		
	}
	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessage#size()
	 */
	public long size() {
		if (this.message == null)
			return 0;
		else
			return this.message.length;
	}
}
