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

package com.nokia.sdt.component.symbian.delegate;

import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.datamodel.adapter.IImplementationDelegate;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

import java.util.List;

public class ImplementationDelegateAdapterCode 
		extends AdapterImpl implements IImplementationDelegate, ICodeImplAdapter {
	IImplementationDelegate impl = null;
	
	public ImplementationDelegateAdapterCode() {
	}
	
	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);
		Check.checkContract(impl instanceof IImplementationDelegate);
		
		this.impl = (IImplementationDelegate) impl;
		setTarget(instance);
	}
	
	public List<Class> getDelegateInterfaces() {
		return impl.getDelegateInterfaces();
	}
	
	public List<EObject> getDelegates(Class interfaceType) {
		return impl.getDelegates(interfaceType);
	}

	public EObject getEObject() {
		return (EObject) getTarget();
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(IImplementationDelegate.class);
	}

	public Object getImpl() {
		return impl;
	}
	
}
