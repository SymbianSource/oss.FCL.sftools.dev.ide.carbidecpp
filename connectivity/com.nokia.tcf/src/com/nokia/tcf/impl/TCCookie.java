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

import org.eclipse.core.runtime.IStatus;

public class TCCookie implements ITCCookie {

	private long clientId;
	private IStatus status;
	private boolean isConnected;
	private boolean isMessageProcessing;
	
	/**
	 * @param clientId
	 * @param status
	 * @param isConnected
	 * @param isMessageProcessing
	 */
	public TCCookie(long clientId, IStatus status, boolean isConnected,
			boolean isMessageProcessing) {
		super();
		this.clientId = clientId;
		this.status = status;
		this.isConnected = isConnected;
		this.isMessageProcessing = isMessageProcessing;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.internal.ITCCookie#getClientId()
	 */
	public long getClientId() {
		return this.clientId;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.internal.ITCCookie#getStatus()
	 */
	public IStatus getStatus() {
		return this.status;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.internal.ITCCookie#isConnected()
	 */
	public boolean isConnected() {
		return this.isConnected;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.internal.ITCCookie#isMessageProcessing()
	 */
	public boolean isMessageProcessing() {
		return this.isMessageProcessing;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.internal.ITCCookie#setClientId(long)
	 */
	public void setClientId(long inClientId) {
		this.clientId = inClientId;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.internal.ITCCookie#setConnected(boolean)
	 */
	public void setConnected(boolean connected) {
		this.isConnected = connected;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.internal.ITCCookie#setMessageProcessing(boolean)
	 */
	public void setMessageProcessing(boolean inMessageProcessing) {
		this.isMessageProcessing = inMessageProcessing;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.internal.ITCCookie#setStatus(org.eclipse.core.runtime.IStatus)
	 */
	public void setStatus(IStatus inStatus) {
		this.status = inStatus;
	}

}
