/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.component.symbian.reconcileProperty;

import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.datamodel.adapter.IReconcileProperty;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

public class ReconcilePropertyAdapterCode extends AdapterImpl implements IReconcileProperty, ICodeImplAdapter {
	IReconcileProperty impl = null;
	
	public ReconcilePropertyAdapterCode() {
	}
	
	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);
		Check.checkContract(impl instanceof IReconcileProperty);
		
		this.impl = (IReconcileProperty) impl;
		setTarget(instance);
	}
	
	public Object createDisplayValue(String propertyTypeName, Object propertyValue) {
		return impl.createDisplayValue(propertyTypeName, propertyValue);
	}

	public boolean isDisplayValueEditable(String propertyTypeName) {
		return impl.isDisplayValueEditable(propertyTypeName);
	}

	public void applyDisplayValue(String propertyTypeName, Object displayValue, Object propertyValue) {
		impl.applyDisplayValue(propertyTypeName, displayValue, propertyValue);
	}

	public EObject getEObject() {
		return (EObject) getTarget();
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(IReconcileProperty.class);
	}

	public Object getImpl() {
		return impl;
	}

}
