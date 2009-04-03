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
import com.nokia.sdt.datamodel.adapter.IComponentEventDescriptorProvider;
import com.nokia.sdt.datamodel.adapter.IEventBinding;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.emf.dm.INode;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;

import org.eclipse.emf.ecore.EObject;

public class EventBinding implements IEventBinding {
	
	private INode owner;
	private com.nokia.sdt.emf.dm.IEventBinding emfBinding;
	
	EventBinding(INode owner, com.nokia.sdt.emf.dm.IEventBinding emfBinding) {
		Check.checkArg(owner);
		Check.checkArg(emfBinding);
		this.owner = owner;
		this.emfBinding = emfBinding;
	}

	public EObject getOwner() {
		return owner;
	}
	
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof EventBinding) {
			EventBinding other = (EventBinding) obj;
			result = owner == other.owner &&
				ObjectUtils.equals(emfBinding.getEventID(), other.emfBinding.getEventID()) &&
				ObjectUtils.equals(emfBinding.getEventHandlerDisplayText(), other.emfBinding.getEventHandlerDisplayText()) &&
				ObjectUtils.equals(emfBinding.getEventHandlerInfo(), other.emfBinding.getEventHandlerInfo());
		}
		return result;
	}

	public IEventDescriptor getEventDescriptor() {
		IEventDescriptor result = null;
		IComponentEventDescriptorProvider edp = ModelUtils.getComponentEventDescriptorProvider(owner);
		if (edp != null) {
			IEventDescriptor eds[] = edp.getEventDescriptors();
			if (eds != null) {
				for (int i = 0; i < eds.length; i++) {
					if (ObjectUtils.equals(emfBinding.getEventID(), eds[i].getId())) {
						result = eds[i];
						break;
					}
				}
			}
		}
		return result;
	}

	public String getHandlerName() {
		return emfBinding.getEventHandlerDisplayText();
	}

	public void setHandlerName(String text) {
		emfBinding.setEventHandlerDisplayText(text);
	}

	public String getHandlerSymbolInformation() {
		return emfBinding.getEventHandlerInfo();
	}

	public void setHandlerSymbolInformation(String symbolInformation) {
		emfBinding.setEventHandlerInfo(symbolInformation);
	}

	public boolean isSameHandler(IEventBinding other) {
		return ObjectUtils.equals(getHandlerName(), other.getHandlerName()) &&
	    ObjectUtils.equals(getHandlerSymbolInformation(), other.getHandlerSymbolInformation());
	}

}
