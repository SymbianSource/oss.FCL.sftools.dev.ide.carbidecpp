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

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IComponentScriptAdapter;
import com.nokia.sdt.component.symbian.IFacetContainer;
import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.scripting.*;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.Path;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This adapter manages scripts for a component.
 * 
 */
public class ScriptAdapter implements IComponentScriptAdapter {

	private static final boolean USE_TRANSIENT_SCOPE = true;
    
    private IComponent component;
	private Map fileContextMap;
    private Map textContextMap;
    private File baseDirectory;
    
    /**
     * Initialize an adapter managing the scripts for this component.
     */
	ScriptAdapter(IComponent component) {
		Check.checkArg(component);
		this.component = component;
        this.baseDirectory = ((IFacetContainer) component).getBaseDirectory();
        this.fileContextMap = new HashMap();
        this.textContextMap = new HashMap();
	}
	
	public IComponent getComponent() {
		return component;
    }

    public IScriptContext findContextForFile(File scriptFile) {
        try {
            scriptFile = scriptFile.getCanonicalFile();
        } catch (IOException e) {
            
        }
        return (IScriptContext) fileContextMap.get(scriptFile);
    }

    public IScriptContext findContextForText(String uniqueId) {
        return (IScriptContext) textContextMap.get(uniqueId);
    }

    public IScriptContext getContextForFile(File scriptFile, IScriptScope parentScope) throws ScriptException {
        try {
            scriptFile = scriptFile.getCanonicalFile();
        } catch (IOException e) {
            
        }
        
        IScriptContext context = (IScriptContext) fileContextMap.get(scriptFile);
        if (context == null) {
        	Logging.timeStart("Getting context for " + scriptFile.getName()); //$NON-NLS-1$
            if (parentScope == null)
                parentScope = ComponentScriptingManager.getInstance().getComponentGlobalScope();

            context = ScriptingManager.getInstance().newScriptContext(parentScope, USE_TRANSIENT_SCOPE);
            
            // execute script and merge includes into this context
            final File scriptFile_ = scriptFile;
            ComponentGlobals.wrapScriptHandlingCode(
                    context,
                    scriptFile.getParentFile(),
                    component,
                    null,
                    new IScriptContextWrapper() {

                        public Object run(IScriptContext context) throws Exception {
                            ComponentGlobals.setupGlobals(context.getScope());
                            context.compileAndExecute(scriptFile_);
                            return null;
                        }
                    } 
                );
                
            fileContextMap.put(scriptFile, context);
            Logging.timeEnd();
        }
        return context;
    }

    public IScriptContext getContextForText(final String uniqueId, final String scriptText, IScriptScope parentScope) throws ScriptException {
        IScriptContext context = (IScriptContext) textContextMap.get(uniqueId);
        if (context == null) {
        	Logging.timeStart("Getting context for " + uniqueId); //$NON-NLS-1$
            if (parentScope == null)
                parentScope = ComponentScriptingManager.getInstance().getComponentGlobalScope();

            context = ScriptingManager.getInstance().newScriptContext(parentScope, USE_TRANSIENT_SCOPE);

            ScriptGlobals.wrapScriptHandlingCode(context,
                    baseDirectory,
                    new IScriptContextWrapper() {

                        public Object run(IScriptContext context) throws Exception {
                            ComponentGlobals.setupGlobals(context.getScope());
                            context.compileAndExecute(
                                    new Path(uniqueId), 
                                    new StringReader(scriptText));
                            return null;
                        }
                }
                );
                
            textContextMap.put(uniqueId, context);
            Logging.timeEnd();
        }
        return context;
    }
    
}
