/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.component.symbian.childCommand;

import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.datamodel.adapter.IChildCommandExtender;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

import java.util.Collection;
import java.util.List;

public class ChildCommandExtenderAdapterCode extends AdapterImpl implements IChildCommandExtender, ICodeImplAdapter {
	IChildCommandExtender impl = null;
	
	public ChildCommandExtenderAdapterCode() {
	}
	
	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);
		Check.checkContract(impl instanceof IChildCommandExtender);
		
		this.impl = (IChildCommandExtender) impl;
		setTarget(instance);
	}
	
	public Command getExtendedAddNewComponentInstanceCommand(EObject owner, Collection<EObject> children, int insertionPosition, Command command) {
		return impl.getExtendedAddNewComponentInstanceCommand(owner, children, insertionPosition, command);
	}

	public Command getExtendedMoveComponentInstanceCommand(EObject targetObject, EObject newOwner, int insertionPosition, Command command) {
		return impl.getExtendedMoveComponentInstanceCommand(targetObject, newOwner, insertionPosition, command);
	}

	public Command getExtendedRemoveComponentInstancesCommand(List<EObject> objectsToRemove, Command command) {
		return impl.getExtendedRemoveComponentInstancesCommand(objectsToRemove, command);
	}

	public EObject getEObject() {
		return (EObject) getTarget();
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(IChildCommandExtender.class);
	}

	public Object getImpl() {
		return impl;
	}

}
