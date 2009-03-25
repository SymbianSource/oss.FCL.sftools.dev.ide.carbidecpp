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
package com.nokia.sdt.utils;

import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.IMessageListener;

/**
 * This provides a default message listener for any routines
 * that take IMessageListener and forwards its messages directly
 * to MessageReporting.  So, never register this as a listener to
 * that class!
 *
 */
public class DefaultMessageListener implements IMessageListener {

	static final public DefaultMessageListener INSTANCE = new DefaultMessageListener();
	
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.IMessageListener#isHandlingMessage(com.nokia.sdt.utils.IMessage)
	 */
	public boolean isHandlingMessage(IMessage msg) {
		// in case this gets added as a MessageReporting listener
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.IMessageListener#emitMessage(com.nokia.sdt.utils.IMessage)
	 */
	public void emitMessage(IMessage msg) {
		MessageReporting.emitMessage(msg);
	}

}
