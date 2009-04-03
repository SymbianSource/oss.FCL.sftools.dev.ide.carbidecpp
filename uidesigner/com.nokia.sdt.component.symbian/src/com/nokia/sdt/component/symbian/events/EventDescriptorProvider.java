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

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.event.IEventDescriptor;
import com.nokia.sdt.component.event.IEventDescriptorProvider;
import com.nokia.sdt.component.symbian.Component;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.emf.component.EventType;
import com.nokia.sdt.emf.component.EventsType;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class EventDescriptorProvider implements IEventDescriptorProvider {

	private Component component;
	private EventDescriptor descriptors[];
	private int defaultEventIndex;
	
	public EventDescriptorProvider(Component component) {
		this.component = component;
		this.defaultEventIndex = -1;
		
		// build list of event descriptors and initialize
		// default event
		ArrayList eventDescriptors = new ArrayList();
		Component currComponent = component;
		while (currComponent != null) {
			EventsType events = currComponent.getEMFComponent().getEvents();
			if (events != null) {
				// Each component may declare a default event, but there
				// can be only one. We take the one declared in the most
				// derived component
				String defaultEventName = null;
				if (defaultEventIndex == -1) {
					defaultEventName = events.getDefaultEventName();
				}
				for (Iterator iter = events.getEvent().iterator(); iter
						.hasNext();) {
					EventType currEvent = (EventType) iter.next();
					if (ObjectUtils.equals(defaultEventName, currEvent.getName())) {
						defaultEventIndex = eventDescriptors.size();
					}
					EventDescriptor ed = new EventDescriptor(currEvent, currComponent);
					eventDescriptors.add(ed);
				}
				// emit diagnostic if a default event was declared but didn't
				// match an event
				if (defaultEventName != null && defaultEventIndex == -1) {
					String fmt = Messages.EventDescriptorProvider0;
					Object params[] = {component.getId(), defaultEventName};
					String msg = MessageFormat.format(fmt, params);
					ComponentSystemPlugin.log(null, msg);
				}
			}
			currComponent = currComponent.getBaseComponent();
		}
		if (eventDescriptors.size() > 0) {
			descriptors = (EventDescriptor[]) eventDescriptors.toArray(new EventDescriptor[eventDescriptors.size()]);
		}
	}
	
	public IComponent getComponent() {
		return component;
	}

	public IEventDescriptor[] getEventDescriptors() {
		return descriptors;
	}

	public int getDefaultEventIndex() {
		return defaultEventIndex;
	}

	public IEventDescriptor findEventDescriptor(String id) {
		if (descriptors == null)
			return null;
		for (int i = 0; i < descriptors.length; i++) {
			IEventDescriptor desc = descriptors[i];
			if (desc.getId().equals(id))
				return desc;
		}
		return null;
	}
}
