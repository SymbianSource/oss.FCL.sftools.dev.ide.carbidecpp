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


package com.nokia.sdt.component.symbian.creationTool;

import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.datamodel.adapter.ICreationToolProvider;
import com.nokia.sdt.editor.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

import java.util.Collection;

/**
 *
 */
public class CreationToolProviderAdapterCode extends AdapterImpl 
					implements ICreationToolProvider, ICodeImplAdapter {
	private ICreationToolProvider impl = null;
	
	public Collection<ICreationTool> getCreationTools(IDesignerEditor editor) {
		return impl.getCreationTools(editor);
	}

	public EObject getEObject() {
		return (EObject) getTarget();
	}

	public Object getImpl() {
		return impl;
	}

	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);
		Check.checkContract(impl instanceof ICreationToolProvider);
		
		this.impl = (ICreationToolProvider) impl;
		setTarget(instance);
	}

}
