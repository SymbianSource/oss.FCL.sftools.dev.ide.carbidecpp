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

import java.util.ArrayList;

import com.nokia.tcf.api.ITCMessageIds;

public class TCMessageIds implements ITCMessageIds {

	private ArrayList<Byte> messageIds = null;

	/**
	 *  Constructor creates empty list of message Ids
	 */
	public TCMessageIds() {
		messageIds = new ArrayList<Byte>();
		this.messageIds.clear();
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageIds#addMessageId(java.lang.Byte)
	 */
	public void addMessageId(Byte id) {
		if (!messageIds.contains(id))
			this.messageIds.add(id);
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageIds#getMessageIdAt(int)
	 */
	public Byte getMessageIdAt(int index) {
		return this.messageIds.get(index);
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageIds#getMessageIds()
	 */
	public ArrayList<Byte> getMessageIds() {
		return this.messageIds;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageIds#removeAllMessageIds()
	 */
	public void removeAllMessageIds() {
		this.messageIds.clear();		
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageIds#removeMessageId(java.lang.Byte)
	 */
	public void removeMessageId(Byte id) {
		this.messageIds.remove(id);
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageIds#setMessageIds(java.util.ArrayList)
	 */
	public void setMessageIds(ArrayList<Byte> inMessageIds) {
		this.messageIds = inMessageIds;		
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCMessageIds#size()
	 */
	public long size() {
		return this.messageIds.size();
	}
	
}
