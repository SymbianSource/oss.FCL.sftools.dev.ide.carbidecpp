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

package com.nokia.sdt.component.symbian.querycontainment;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IQueryContainment;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.StatusHolder;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

public class QueryContainmentAdapterCode extends AdapterImpl implements IQueryContainment, ICodeImplAdapter {
	IQueryContainment impl = null;
	
	public QueryContainmentAdapterCode() {
	}
	
	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);
		Check.checkContract(impl instanceof IQueryContainment);
		
		this.impl = (IQueryContainment) impl;
		setTarget(instance);
	}
	
	public boolean canContainComponent(IComponent component, StatusHolder statusHolder) {
		return impl.canContainComponent(component, statusHolder);
	}
	
	public boolean canContainChild(IComponentInstance child, StatusHolder statusHolder) {
		return impl.canContainChild(child, statusHolder);
	}
	
	public boolean canRemoveChild(IComponentInstance child) {
		return impl.canRemoveChild(child);
	}
	
	public boolean isValidComponentInPalette(IComponent component) {
		return impl.isValidComponentInPalette(component);
	}

	public EObject getEObject() {
		return (EObject) getTarget();
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(IQueryContainment.class);
	}

	public Object getImpl() {
		return impl;
	}

}
