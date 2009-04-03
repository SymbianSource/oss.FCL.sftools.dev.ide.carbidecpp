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
package com.nokia.sdt.datamodel.adapter;

import com.nokia.sdt.component.event.IEventDescriptor;

/**
 * This describes the events supported by an instance of a component.
 * It takes the component's IEventDescriptorProvider interface and
 * filters with IComponentEventInfo to provide a synthesized
 * list of valid descriptors and a (possibly different) default event.
 */
public interface IComponentEventDescriptorProvider extends IModelAdapter {

	/**
	 * Returns descriptors for the events supported by a component instance.
	 * If event info is provided, this is either all the events or a
	 * subset of events with matching groups.
	 * @return array of descriptors, never null
	 */
	IEventDescriptor[] getEventDescriptors();
	
	/**
	 * Get a descriptor by event id (searches #getEventDescriptors())
	 */
	IEventDescriptor findEventDescriptor(String id);
	
	/**
	 * Returns the index of the default event, or -1 if none.
	 * This is an index into #getEventDescriptors()
	 */
	int getDefaultEventIndex();
}
