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

import java.util.ArrayList;

/**
 * This interface is used to specify the message IDs that TCF will capture on incoming messages.
 * Use the TCFClassFactory to instantiate the implementation class.
 */
public interface ITCMessageIds {

	/**
	 * Add an ID to the message array.
	 * 
	 * If this ID is already in the list, it is not added.
	 * 
	 * @param id
	 */
	public void addMessageId(Byte id);
	
	/**
	 * Return the message Id at index.
	 * 
	 * @param index
	 * @return
	 */
	public Byte getMessageIdAt(int index);
	
	/**
	 * Return the message Id array.
	 * 
	 * @return
	 */
	public ArrayList<Byte> getMessageIds();
	
	/**
	 * Removes all message Ids in list.
	 */
	public void removeAllMessageIds();
	
	/**
	 * Removes an ID from the list.
	 * 
	 * No error if the ID is not in the list.
	 * 
	 * @param id
	 */
	public void removeMessageId(Byte id);

	/**
	 * Set the message Id array.
	 * 
	 * @param messageIds
	 */
	public void setMessageIds(ArrayList<Byte> inMessageIds);
	
	/**
	 * Return length of message Id array.
	 * 
	 * @return
	 */
	public long size();
}
