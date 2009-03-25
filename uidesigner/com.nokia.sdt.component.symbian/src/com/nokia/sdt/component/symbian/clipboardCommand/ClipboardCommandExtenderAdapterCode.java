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


package com.nokia.sdt.component.symbian.clipboardCommand;

import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.datamodel.adapter.IClipboardCommandExtender;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

public class ClipboardCommandExtenderAdapterCode extends AdapterImpl implements IClipboardCommandExtender, ICodeImplAdapter {
	IClipboardCommandExtender impl = null;
	
	public ClipboardCommandExtenderAdapterCode() {
	}
	
	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);
		Check.checkContract(impl instanceof IClipboardCommandExtender);
		
		this.impl = (IClipboardCommandExtender) impl;
		setTarget(instance);
	}
	
	public Command getExtendedCopyToClipboardCommand(EditingDomain editingDomain, Command command) {
		return impl.getExtendedCopyToClipboardCommand(editingDomain, command);
	}

	public Command getExtendedPasteFromClipboardCommand(EObject owner, EditingDomain editingDomain, Command command) {
		return impl.getExtendedPasteFromClipboardCommand(owner, editingDomain, command);
	}

	public EObject getEObject() {
		return (EObject) getTarget();
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(IClipboardCommandExtender.class);
	}

	public Object getImpl() {
		return impl;
	}

}
