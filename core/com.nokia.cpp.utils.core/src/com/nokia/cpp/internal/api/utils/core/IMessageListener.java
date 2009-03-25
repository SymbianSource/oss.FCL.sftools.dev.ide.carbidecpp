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
package com.nokia.cpp.internal.api.utils.core;

/**
 * This interface listens to messages reported from various
 * parts of UI Designer (sourcegen, scripting, ...) which should
 * be shown to the user.
 *
 */
public interface IMessageListener {
	/**
	 * Tell if the message should be emitted by this listener,
	 * usually true for a solitary listener.  It would return false,
	 * e.g., if several listeners are attached and each has a particular 
	 * resource scope for messages, and the given message doesn't match
	 * this listener's scope. 
	 */
	public boolean isHandlingMessage(IMessage msg);
	
    /**
     * Emit a message if #isHandlingMessage() returned true.
     * @param msg
     */
    public void emitMessage(IMessage msg);
}
