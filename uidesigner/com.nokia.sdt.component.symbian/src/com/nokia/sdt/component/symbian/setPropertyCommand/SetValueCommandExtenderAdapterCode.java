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

package com.nokia.sdt.component.symbian.setPropertyCommand;

import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.datamodel.adapter.ISetValueCommandExtender;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;

public class SetValueCommandExtenderAdapterCode extends AdapterImpl implements ISetValueCommandExtender, ICodeImplAdapter {
	ISetValueCommandExtender impl = null;
	
	public SetValueCommandExtenderAdapterCode() {
	}
	
	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);
		Check.checkContract(impl instanceof ISetValueCommandExtender);
		
		this.impl = (ISetValueCommandExtender) impl;
		setTarget(instance);
	}
	
	public Command getExtendedCommand(String propertyName, Object newValue, Command command) {
		return impl.getExtendedCommand(propertyName, newValue, command);
	}

	public EObject getEObject() {
		return (EObject) getTarget();
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(ISetValueCommandExtender.class);
	}

	public Object getImpl() {
		return impl;
	}

}
