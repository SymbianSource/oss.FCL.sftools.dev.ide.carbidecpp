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
package com.nokia.sdt.component.symbian.events;

import com.nokia.sdt.component.symbian.scripting.WrappedInstance;

/**
 * This interface allows component instances to statically filter their
 * events by event groups.  If not implemented, all events
 * defined on a component are active.
 * @see com.nokia.sdt.datamodel.adapter.IComponentEventInfo
 */
public interface IScriptComponentEventInfo {

	/**
	 * @see com.nokia.sdt.datamodel.adapter.IComponentEventInfo#getEventGroups()
	 */
	String[] getEventGroups(WrappedInstance instance);

	/**
	 * @see com.nokia.sdt.datamodel.adapter.IComponentEventInfo#getDefaultEventName()
	 */
	String getDefaultEventName(WrappedInstance instance);
}
