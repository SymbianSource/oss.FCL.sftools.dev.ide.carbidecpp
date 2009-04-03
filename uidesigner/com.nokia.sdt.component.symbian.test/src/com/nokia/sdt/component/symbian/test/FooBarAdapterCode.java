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

package com.nokia.sdt.component.symbian.test;

import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

public class FooBarAdapterCode extends AdapterImpl implements IBar, ICodeImplAdapter {
	IBar impl = null;
	
	public FooBarAdapterCode() {
	}
	
	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);
		Check.checkContract(impl instanceof IBar);
		
		this.impl = (IBar) impl;
		setTarget(instance);
	}
	
	public int doBar() {
		return impl.doBar();
	}

	public EObject getEObject() {
		return (EObject) getTarget();
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(IBar.class);
	}

	public Object getImpl() {
		return impl;
	}

	
}
