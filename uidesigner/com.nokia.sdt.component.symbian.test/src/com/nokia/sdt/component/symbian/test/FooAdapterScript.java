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

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.symbian.implementations.IScriptImplAdapter;
import com.nokia.sdt.scripting.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

public class FooAdapterScript extends AdapterImpl implements IFoo, IScriptImplAdapter {
	private IScriptObject impl;
	private IFoo fooImpl;
    private IComponent component;
	
	public FooAdapterScript() {
	}
	
    /* (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.implementations.IScriptImplAdapter#init(com.nokia.sdt.component.symbian.scripting.IScriptObject, org.eclipse.emf.ecore.EObject, com.nokia.sdt.component.IComponent)
     */
    public void init(IScriptContext context, IScriptObject scriptObject, EObject instance, IComponent component) throws ScriptException {
		Check.checkArg(scriptObject);
		setTarget(instance);
		this.component = component;
		this.impl = scriptObject;
		this.fooImpl = (IFoo) scriptObject.wrapObjectInInterface(IFoo.class);
	}
	
	public int doFoo() {
		return fooImpl.doFoo();
	}

	public EObject getEObject() {
		return (EObject) getTarget();
	}

    /* (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.implementations.IScriptImplAdapter#getComponent()
     */
    public IComponent getComponent() {
        return component;
    }
	public boolean isAdapterForType(Object type) {
		return type.equals(IFoo.class);
	}

	public IScriptObject getScriptObject() {
		return impl;
	}

	
}
