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
package com.nokia.sdt.symbian.dm;

import com.nokia.sdt.component.event.IEventDescriptor;
import com.nokia.sdt.component.event.IEventDescriptorProvider;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.ArrayList;
import java.util.List;

public class ComponentEventDescriptorProvider implements IComponentEventDescriptorProvider {

	private IComponentInstance instance;
	private IEventDescriptorProvider eventDescriptorProvider;
	private IEventDescriptor descriptors[];
	private int defaultEventIndex;
	private EObject eObject;
	
	public ComponentEventDescriptorProvider(EObject object) {
		Check.checkArg(object);
		this.eObject = object;
		this.instance = ModelUtils.getComponentInstance(object);
		this.eventDescriptorProvider = (IEventDescriptorProvider) instance.getComponent().getAdapter(IEventDescriptorProvider.class);

		// get the component tree's descriptor list
		IEventDescriptor[] allDescriptors = eventDescriptorProvider.getEventDescriptors();
		if (allDescriptors == null)
			allDescriptors = new IEventDescriptor[0];
		int allDescriptorsDefaultIndex = eventDescriptorProvider.getDefaultEventIndex();
		
		// get the filtered list, and also get the default event
		IComponentEventInfo componentEventFilter = (IComponentEventInfo) EcoreUtil.getRegisteredAdapter(instance.getEObject(), IComponentEventInfo.class);
		boolean filtered = false;
		if (componentEventFilter != null) {
			String defaultEvent = componentEventFilter.getDefaultEventName();
			String[] groups = componentEventFilter.getEventGroups();
			if (groups != null) {
				filtered = true;
				List<IEventDescriptor> allowed = new ArrayList<IEventDescriptor>();
				this.defaultEventIndex = -1;
				for (int j = 0; j < allDescriptors.length; j++) {
					IEventDescriptor descriptor = allDescriptors[j];
					boolean matched = false;
					for (int i = 0; !matched && i < groups.length; i++) {
						if (descriptor.getGroup() != null && 
								descriptor.getGroup().equals(groups[i])) {
							if (defaultEvent != null 
									? defaultEvent.equals(descriptor.getId())
											: j == allDescriptorsDefaultIndex)
								this.defaultEventIndex = allowed.size();
							allowed.add(descriptor);
							matched = true;
						}
					}
				}
				this.descriptors = (IEventDescriptor[]) allowed.toArray(new IEventDescriptor[allowed.size()]);
			}
		}
		if (!filtered) {
			this.descriptors = allDescriptors;
			this.defaultEventIndex = allDescriptorsDefaultIndex;
		}
	}

	public EObject getEObject() {
		return eObject;
	}
	
	public IEventDescriptor[] getEventDescriptors() {
		return descriptors;
	}

	public int getDefaultEventIndex() {
		return defaultEventIndex;
	}

	public IEventDescriptor findEventDescriptor(String id) {
		for (int i = 0; i < descriptors.length; i++) {
			IEventDescriptor desc = descriptors[i];
			if (desc.getId().equals(id))
				return desc;
		}
		return null;
	}
}
