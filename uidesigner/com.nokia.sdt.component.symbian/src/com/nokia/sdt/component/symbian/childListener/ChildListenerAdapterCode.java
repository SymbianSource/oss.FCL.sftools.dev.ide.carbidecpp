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

package com.nokia.sdt.component.symbian.childListener;

import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.datamodel.adapter.IComponentInstanceChildListener;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

public class ChildListenerAdapterCode extends AdapterImpl implements IComponentInstanceChildListener, ICodeImplAdapter {
	IComponentInstanceChildListener impl = null;
	
	public ChildListenerAdapterCode() {
	}
	
	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);
		Check.checkContract(impl instanceof IComponentInstanceChildListener);
		
		this.impl = (IComponentInstanceChildListener) impl;
		setTarget(instance);
	}
	
	public void childAdded(EObject parent, EObject child) {
		impl.childAdded(parent, child);
	}

	public void childRemoved(EObject parent, EObject child) {
		impl.childRemoved(parent, child);
	}

	public void childrenReordered(EObject parent) {
		impl.childrenReordered(parent);
	}

	public EObject getEObject() {
		return (EObject) getTarget();
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(IComponentInstanceChildListener.class);
	}

	public Object getImpl() {
		return impl;
	}

}
