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

package com.nokia.sdt.component.symbian.customizerUI;

import com.nokia.sdt.component.customizer.IComponentCustomizerCommandFactory;
import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.datamodel.adapter.IComponentCustomizerUI;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;

public class CustomizerUIAdapterCode extends AdapterImpl implements IComponentCustomizerUI, ICodeImplAdapter {
	IComponentCustomizerUI impl = null;
	
	public CustomizerUIAdapterCode() {
	}
	
	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);
		Check.checkContract(impl instanceof IComponentCustomizerUI);
		
		this.impl = (IComponentCustomizerUI) impl;
		setTarget(instance);
	}
	
	public Composite getCustomizerComposite(Composite parent) {
		return impl.getCustomizerComposite(parent);
	}
	
	public IComponentCustomizerCommandFactory getCommandFactory() {
		return impl.getCommandFactory();
	}
	
	public EObject getEObject() {
		return (EObject) getTarget();
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(IComponentCustomizerUI.class);
	}

	public Object getImpl() {
		return impl;
	}

}
