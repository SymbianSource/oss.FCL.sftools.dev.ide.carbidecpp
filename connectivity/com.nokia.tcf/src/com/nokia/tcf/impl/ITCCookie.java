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

public interface ITCCookie {

	/**
	 * Returns true if client is successfully connected
	 * 
	 * @return
	 */
	public boolean isConnected();
	
	/**
	 * Sets connected status
	 * 
	 * @param connected
	 */
	
	public void setConnected(boolean connected);
	
	/**
	 * Get the IStatus portion
	 * 
	 * @return
	 */
	public IStatus getStatus();
	
	/**
	 * Set the IStatus portion
	 * 
	 * @param inStatus
	 */
	public void setStatus(IStatus inStatus);
	
	/**
	 * Get the client ID
	 * 
	 * @return
	 */
	public long getClientId();
	/**
	 * Set the client ID
	 * 
	 * @param inClientId
	 */
	public void setClientId(long inClientId);
	
	/**
	 * Is message processing in progress?
	 * 
	 * @return
	 */
	public boolean isMessageProcessing();
	
	/**
	 * Set message processing status
	 * 
	 * @param inMessageProcessing
	 */
	public void setMessageProcessing(boolean inMessageProcessing);
}
