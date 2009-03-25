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

package com.nokia.sdt.component.symbian.componentValidator;

import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.datamodel.IModelMessage;
import com.nokia.sdt.datamodel.adapter.IComponentValidator;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

import java.util.Collection;

public class ComponentValidatorAdapterCode extends AdapterImpl 
			implements IComponentValidator, ICodeImplAdapter {
	
	private IComponentValidator impl;

	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);		
		this.impl = (IComponentValidator) impl;
		setTarget(instance);
	}
	
	public Collection<IModelMessage> validate(EObject componentInstance) {
		return impl.validate(componentInstance);
	}

	public String queryPropertyChange(EObject componentInstance, String propertyPath, Object newValue) {
		return impl.queryPropertyChange(componentInstance, propertyPath, newValue);
	}
	
	public EObject getEObject() {
		return (EObject) getTarget();
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(IComponentValidator.class);
	}

	public Object getImpl() {
		return impl;
	}
}
