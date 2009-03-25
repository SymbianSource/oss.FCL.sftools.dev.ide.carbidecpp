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

import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.scripting.ScriptGlobals;
import com.nokia.sdt.component.symbian.scripting.ScriptingManager;
import com.nokia.sdt.scripting.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.mozilla.javascript.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ScriptContext implements IScriptContext, IDisposable {
    private ScriptScope parentScope;
    private ScriptScope transientGlobals;
    private ScriptScope scriptScope;
    private Script script;
    private IPath path;
    private Map<File, IScriptContext> includedFiles;
    
    Context getContext() {
        return ScriptingManager.getInstance().getContext();
    }
    
    /**
     * Create a context for the given file.  Link its globals to
     * parent scope (or the top-level globals if null).
     * @param parentScope_ the parent scope
     * @throws ScriptException
     */
    public ScriptContext(IScriptScope parentScope_, boolean provideTransient) throws ScriptException {
        this.includedFiles = new HashMap<File, IScriptContext>();
        
        if (parentScope_ == null)
            parentScope = (ScriptScope) ScriptingManager.getInstance().getGlobalScope();
        else
            parentScope = (ScriptScope) parentScope_;
        
        if (provideTransient) {
            this.transientGlobals = new ScriptScope(this, parentScope);
            this.transientGlobals.scope.setPrototype(parentScope.scope);
            this.transientGlobals.scope.setParentScope(null);
    
            this.scriptScope = new ScriptScope(this, transientGlobals);
            // link prototypes
            this.scriptScope.scope.setPrototype(transientGlobals.scope);
        } else {
            this.transientGlobals = null;
            this.scriptScope = new ScriptScope(this, parentScope);
            // link prototypes
            this.scriptScope.scope.setPrototype(parentScope.scope);
        }
        
    }

    /*
     *  (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.scripting.IScriptContext#compile(java.lang.String, java.io.Reader)
     */
    public void compileAndExecute(IPath path, Reader reader) throws ScriptException {
        // only call this family of methods once!
        Check.checkContract(script == null);

        this.path = path;
        
        try {
        	if (ScriptingManager.DEBUG)
        		System.out.println("compiling text " + path);

            script = getContext().compileReader(reader, path.toOSString(), 1, null);
        } catch (Throwable thr) {
            Messages.failure(thr, new MessageLocation(new Path(path.toFile().getAbsolutePath())),
                    "ScriptContext.CompilingScript", new Object[] { 
                    path.lastSegment(), thr.getLocalizedMessage() } );
        }
        
        // restrict all the defined names to this scope
        scriptScope.scope.setParentScope(null);

        try {
            script.exec(getContext(), scriptScope.scope);
        } catch (Throwable t) {
            Messages.failure(t, new MessageLocation(new Path(path.toFile().getAbsolutePath())),
                    "ScriptContext.ErrorEncounteredRunningScript", 
                    new Object[] { path.lastSegment(), t.getLocalizedMessage() } );
        } finally {
            scriptScope.scope.setParentScope(parentScope.scope);
        }
    }
 
    public void compileAndExecute(File incFile) throws ScriptException {
        // only call this family of methods once!
        Check.checkContract(script == null);

        this.path = new Path(incFile.getAbsolutePath());

        try {
        	script = ScriptingManager.getInstance().compileFile(incFile);
        } catch (IOException e) {
            Context.throwAsScriptRuntimeEx(e);
        }

        // restrict all the defined names to this scope
        scriptScope.scope.setParentScope(null);

        try {
        	scriptScope.scope.setPrototype(parentScope.scope);
            script.exec(getContext(), scriptScope.scope);
        } catch (Throwable t) {
            Messages.failure(t, new MessageLocation(path),
                    "ScriptContext.ErrorEncounteredRunningScript", 
                    new Object[] { path.lastSegment(), t.getLocalizedMessage() } );
        } finally {
            scriptScope.scope.setParentScope(parentScope.scope);
        }
    }
 
    public IPath getPath() {
        return path;
    }
    
    /*
     *  (non-Javadoc)
     * @see com.nokia.sdt.utils.IDisposable#dispose()
     */
    public void dispose() {
    }
    
    public IScriptScope getParentScope() {
        return parentScope;
    }

    public IScriptScope getTransientGlobalScope() {
        return transientGlobals;
    }
    /*
     *  (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.scripting.IScriptContext#getScope()
     */
    public IScriptScope getScope() {
        return scriptScope;
    }
    
    public Script getScript() {
        return script;
    }

    public void includeFile(File incFile) {
    	try {
    		incFile = incFile.getCanonicalFile();
    	} catch (IOException e) {
    		
    	}
    	
        // don't include the same file twice (includes during a script -- not globally)
        if (includedFiles.get(incFile) != null)
            return;

        try {
            // make a temporary context
            IScriptContext inclContext = ScriptingManager.getInstance().newScriptContext(null, false);
            
            File oldBaseDir = ScriptGlobals.getIncludeBase();
            try {
                ScriptGlobals.setIncludeBase(incFile.getParentFile());
    
                //inclContext.getScope().dump("for include="+incFile, System.out);
                //inclContext.compileAndExecute(incFile);
                
                Script script = ScriptingManager.getInstance().compileFile(incFile);
				if (script != null) {
					script.exec(getContext(), (Scriptable) scriptScope.getScriptable());
				}
				
                // copy new contents into parent scope
                getScope().include(inclContext.getScope());
            } catch (IOException e) {
            	ComponentSystemPlugin.log(e);
            } finally {
                ScriptGlobals.setIncludeBase(oldBaseDir);
            }
        
            includedFiles.put(incFile, inclContext);
            
        } catch (ScriptException e) {
            // already logged
            //Context.throwAsScriptRuntimeEx(e);
        }
    }

    
}
