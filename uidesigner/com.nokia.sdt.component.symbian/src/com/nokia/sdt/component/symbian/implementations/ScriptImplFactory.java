/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.component.symbian.implementations;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IComponentScriptAdapter;
import com.nokia.sdt.component.symbian.IFacetContainer;
import com.nokia.sdt.emf.component.ScriptType;
import com.nokia.sdt.scripting.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ScriptImplFactory {
	private IComponent component;
	private String prototype;
	private IComponentScriptAdapter scriptAdapter;
	private Map instanceToImpl = null;
    private IScriptContext scriptContext;

    /**
     * Create a script implementation factory for the given component
     * and script facet.  The factory provides a unique
     * Javascript instance of a prototype per component instance.  
     * @param component the component
     * @param scriptType the script facet from component XML
     * @param inheritFromBase if true, allow script to inherit code from
     * a base component's implementation; else, script is standalone
     * @throws ScriptException
     */
	public ScriptImplFactory(IComponent component, ScriptType scriptType,
            boolean inheritFromBase) throws ScriptException {
		Check.checkArg(scriptType);
		Check.checkArg(component);

		this.component = component;
		
        // get Javascript context for component
        this.scriptAdapter = (IComponentScriptAdapter) component.getAdapter(IComponentScriptAdapter.class);

        // Get the file/prototype from facet
        if (scriptType != null) {
            String file = scriptType.getFile();
            Check.checkContract(file != null);
            prototype = scriptType.getPrototype();
            Check.checkContract(prototype != null);
            
            getCompiledScript(file, inheritFromBase);
        } 
		
		instanceToImpl = new HashMap();
	}

	private void getCompiledScript(String file, boolean inheritFromBase) throws ScriptException {
        Check.checkArg(file);
        File scriptFile;
        if (!new File(file).isAbsolute()) {
	        IFacetContainer fc = (IFacetContainer) component;
	        File basePath = fc.getBaseDirectory();
	        Check.checkState(basePath != null);
	        scriptFile = new File(basePath, file);
        }
        else {
        	scriptFile = new File(file);
        }
        
        // get script context, compiling the script
        scriptContext = scriptAdapter.getContextForFile(scriptFile, null);
	}
	
	public IScriptObject getScriptObject(EObject instance) throws ScriptException {
		IScriptObject impl = null;
		if (!instanceToImpl.containsKey(instance)) {
	        impl = scriptContext.getScope().createInstance(prototype);
	        instanceToImpl.put(instance, impl);
	    }
		else
			impl = (IScriptObject) instanceToImpl.get(instance);
		
		return impl;
	}

    public IScriptContext getScriptContext() {
        return scriptContext;
    }
}
