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


/**
 * This interface allows component instances to statically control
 * which events are exposed in an instance.  If not implemented, all events
 * defined on a component are active and the component's default
 * event is the instance's default event.
 */
public interface IComponentEventInfo extends IModelAdapter {

	/**
	 * Return the array of event groups which are active.
	 * Only events with group ids will be selected -- un-grouped events
	 * can never be matched.
	 * @return array of event groups, or null for all.
	 * 
	 */
	String[] getEventGroups();
	
	/**
	 * Return the default event name.  If null, takes the
	 * component's default event.
	 */
	String getDefaultEventName();
}
