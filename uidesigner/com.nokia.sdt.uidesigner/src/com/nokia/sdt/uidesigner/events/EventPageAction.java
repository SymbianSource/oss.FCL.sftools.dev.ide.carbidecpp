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
package com.nokia.sdt.uidesigner.events;

import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.jface.action.Action;

/**
 * Derived action class that has a link back to the EventPage
 */
public class EventPageAction extends Action {
	
	private EventPage page;
	
	public EventPageAction(EventPage page) {
		Check.checkArg(page);
		this.page = page;
	}
	
	public EventPage getEventPage() {
		return page;
	}
	
	public EventPage getPage() {
		return page;
	}
}
