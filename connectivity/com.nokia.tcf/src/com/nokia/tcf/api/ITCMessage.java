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

/**
 * A message used in sending to the connection.
 */
public interface ITCMessage {
	
	/**
	 * Return the message as byte[]
	 * 
	 * @return byte[]
	 */
	public byte[] getMessage();
	
	/**
	 * Set the message from a byte array
	 * 
	 * @param inMessage
	 */
	public void setMessage(byte[] inMessage);
	
	/**
	 * Set outgoing message properties:
	 * 	use my message ID and the message ID.
	 * 
	 * If outgoing message formatting is not on, these are ignored.
	 * 
	 * @param inUseMyMessageId - if true, use my ID
	 * @param inMyId - my ID to use
	 */
	public void setUseMyMessageId(boolean inUseMyMessageId, byte inMyId);
	
	/**
	 * Gets setting of using specified message ID.
	 * 
	 * @return
	 */
	public boolean isUseMyMessageId();
	
	/**
	 * Returns my messsage ID, if used.
	 * 
	 * @return
	 */
	public byte getMyMessageId();
	
	/**
	 * Return size of this message
	 * 
	 * @return size
	 */
	public long size();
}
