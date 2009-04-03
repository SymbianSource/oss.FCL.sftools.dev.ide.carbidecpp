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

package com.nokia.sdt.component.symbian.editor;

import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.editor.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

public class ComponentEditorAdapterCode extends AdapterImpl implements IComponentEditor, ICodeImplAdapter {
	IComponentEditor impl = null;
	
	public ComponentEditorAdapterCode() {
	}
	
	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);
		Check.checkContract(impl instanceof IComponentEditor);
		
		this.impl = (IComponentEditor) impl;
		setTarget(instance);
	}

	public void beginEditing(IDesignerEditor editor) {
		impl.beginEditing(editor);
	}

	public void endEditing() {
		impl.endEditing();
	}

	public boolean isTemporaryObject() {
		return impl.isTemporaryObject();
	}
	
	public boolean isUserRemovable() {
		return impl.isUserRemovable();
	}

	public Object getAdapter(Class adapter) {
		return impl.getAdapter(adapter);
	}
	
	public EObject getEObject() {
		return (EObject) getTarget();
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(IComponentEditor.class);
	}

	public Object getImpl() {
		return impl;
	}

}
