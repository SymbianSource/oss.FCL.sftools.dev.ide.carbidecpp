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

import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.datamodel.adapter.IComponentEventInfo;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

public class ComponentEventInfoAdapterCode extends AdapterImpl implements IComponentEventInfo, ICodeImplAdapter {
	IComponentEventInfo impl = null;
	
	public ComponentEventInfoAdapterCode() {
	}
	
	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);
		Check.checkContract(impl instanceof IComponentEventInfo);
		
		this.impl = (IComponentEventInfo) impl;
		setTarget(instance);
	}
	
	public EObject getEObject() {
		return (EObject) getTarget();
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(IComponentEventInfo.class);
	}

	public Object getImpl() {
		return impl;
	}

	public String[] getEventGroups() {
		return impl.getEventGroups();
	}
	
	public String getDefaultEventName() {
		return impl.getDefaultEventName();
	}
}
