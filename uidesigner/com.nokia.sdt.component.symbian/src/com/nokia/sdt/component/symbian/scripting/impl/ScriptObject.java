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

package com.nokia.sdt.component.symbian.scripting.impl;

import com.nokia.sdt.component.symbian.scripting.ScriptingManager;
import com.nokia.sdt.scripting.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

public class ScriptObject implements IScriptObject {

    private ScriptableObject instance;
    ScriptScope scope;
    
    Context getRhinoContext() {
        return ScriptingManager.getInstance().getContext();
    }

    public ScriptObject(ScriptableObject instance, ScriptScope parentScope) {
        Check.checkArg(instance);
        this.instance = instance;
        this.scope = new ScriptScope(instance, parentScope);
    }

    public IScriptScope getScope() {
        return scope;
    }

    IPath getPath() {
        return scope.getNearestContext().getPath();
    }
    
    public Object wrapObjectInInterface(Class theInterface) throws ScriptException {
    	return ScriptingManager.getInstance().getWrappedInterface(
    			getPath(), theInterface, instance, scope.scope);
    }

    static Adapter getAdapter(EObject object, Class adapterClass) {
        return EcoreUtil.getRegisteredAdapter(object, adapterClass);
    }


}
