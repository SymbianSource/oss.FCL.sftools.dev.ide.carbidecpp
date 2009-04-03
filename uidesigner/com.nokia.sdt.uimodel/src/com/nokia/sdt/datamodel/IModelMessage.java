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
package com.nokia.sdt.datamodel;

import com.nokia.cpp.internal.api.utils.core.IMessage;

public interface IModelMessage extends IMessage {

	/**
	 * Get the component associated with this message,
	 * may be null
	 */
	String getComponentID();
	
	/**
	 * Get the component instance associated with this
	 * message, may be null.
	 */
	String getInstanceName();
	
	/**
	 * Get the property associated with this message,
	 * may be null
	 */
	Object getPropertyID();
	
	/**
	 * Get the event associated with this message,
	 * may be null
	 */
	Object getEventID();
}
