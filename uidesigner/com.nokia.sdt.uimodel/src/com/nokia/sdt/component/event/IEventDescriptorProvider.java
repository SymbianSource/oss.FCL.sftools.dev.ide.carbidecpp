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
package com.nokia.sdt.component.event;

import com.nokia.sdt.component.IComponent;

/**
 * IEventDescriptorProvider describes the events supported by
 * a component. It is accessed as an adaptor interface on IComponent
 */
public interface IEventDescriptorProvider {

	IComponent getComponent();
	
	/**
	 * Returns descriptors for all events supported by a component.
	 */
	IEventDescriptor[] getEventDescriptors();
	
	/**
	 * Get a descriptor by event id
	 */
	IEventDescriptor findEventDescriptor(String id);
	
	/**
	 * Returns the index of the default event, or -1 if none.
	 */
	int getDefaultEventIndex();
}
