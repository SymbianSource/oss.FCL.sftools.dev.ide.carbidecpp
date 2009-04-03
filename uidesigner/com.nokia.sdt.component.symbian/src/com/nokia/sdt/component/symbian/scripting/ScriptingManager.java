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
package com.nokia.sdt.component.symbian.scripting;

import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.scripting.impl.*;
import com.nokia.sdt.displaymodel.GlobalCache;
import com.nokia.sdt.scripting.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.*;
import org.eclipse.swt.widgets.Display;
import org.mozilla.javascript.*;
import org.osgi.framework.Bundle;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class handles the global scripting state.
 * One assumption is that all scripts will run on the
 * same thread.
 * 
 * 
 *
 */
public class ScriptingManager implements IDisposable {
	public static boolean DEBUG = false;

    private static final String COMPILED_SCRIPTS_ID = "ScriptContext.COMPILED_SCRIPTS_ID";
    /** 
     * Get the map of File to Script.  This is a global cache of every file
     * compiled.  A file is compiled only once (until the component system
     * is refreshed).
     * @return map
     */
    private static Map<File, Script> getCompiledScripts() {
        Map<File, Script> map = (Map<File, Script>) GlobalCache.getCache().get(COMPILED_SCRIPTS_ID);
        if (map == null) {
            map = new HashMap<File, Script>();
            GlobalCache.getCache().put(COMPILED_SCRIPTS_ID, map);
        }
        return map;
    }
    
    private static final String WRAPPED_INTERFACES_ID = "ScriptContext.WRAPPED_INTERFACES_ID";
    /** 
     * Get the map of interface name and scope to Class.  This is a global cache of every 
     * interface implemented in the component system.  The class is created
     * only once (until the component system is refreshed).
     * @return map
     */
    private static Map<WrappedInterfaceInfo, Class> getInterfaceToWrappedInterfaceMap() {
    	Map<WrappedInterfaceInfo, Class> map = (Map<WrappedInterfaceInfo, Class>) GlobalCache.getCache().get(WRAPPED_INTERFACES_ID);
    	if (map == null) {
    		map = new HashMap<WrappedInterfaceInfo, Class>();
    		GlobalCache.getCache().put(WRAPPED_INTERFACES_ID, map);
    	}
    	
    	return map;
    }
    

    /**
     * We need to enable dynamic scopes so that the transient
     * global scope feature works as expected when derived
     * scripts call base scripts.
     */
    static boolean dynamic = true;

    static class MyFactory extends ContextFactory {
        protected boolean hasFeature(Context cx, int featureIndex) {
            if (featureIndex == Context.FEATURE_DYNAMIC_SCOPE) {
                return dynamic;
            }
            return super.hasFeature(cx, featureIndex);
        }
    };

    static {
        ContextFactory.initGlobal(new MyFactory());
    }

    static final String MANAGER_ID = "ScriptingManager.INSTANCE";
    private Context context;
    
    private ClassLoader origClassLoader;
    private IScriptScope globalScope;
    
    private IScriptContextFactory contextFactory;
    
    private ScriptingManager() {
        context = null;
        contextFactory = new IScriptContextFactory() {
            public IScriptContext createScriptContext(IScriptScope parentScope,
                    boolean provideTransient) throws ScriptException {
                IScriptContext context = new ScriptContext(parentScope, provideTransient);
                ScriptGlobals.setupGlobals(context.getScope());
                return context;
            }};
    }

    public static ScriptingManager getInstance() {
        ScriptingManager manager = (ScriptingManager) GlobalCache.getCache().get(MANAGER_ID);
        if (manager == null) {
            manager = new ScriptingManager();
            GlobalCache.getCache().put(MANAGER_ID, manager);
        }
        return manager;
    }

    public void dispose() {
        if (globalScope != null) {
            globalScope.dispose();
            globalScope = null;
        }
        if (context != null) {
            context.setApplicationClassLoader(origClassLoader);
        	if (Platform.isRunning())
	            Display.getDefault().asyncExec(new Runnable() {
	            	public void run() {
	            		// script calls must be made from the UI thread
	            		Context.exit();
	            	}
	            });
        	else
        		Context.exit();
            context = null;
        }
        contextFactory = null;
    }
    
    /**
     * Create a script context using the current context factory.
     * This initializes globals with respect to the factory.
     * @param parentScope
     * @param provideTransient
     * @return new script context
     * @throws ScriptException
     */
    public IScriptContext newScriptContext(IScriptScope parentScope, boolean provideTransient) throws ScriptException {
        return contextFactory.createScriptContext(parentScope, provideTransient);
    }
    
    /**
     * Get the absolute global scope for all scripts.
     * @return IScriptScope
     */
    public IScriptScope getGlobalScope() {
        if (globalScope == null) {
            globalScope = new ScriptScope();
        }
        return globalScope;
    }

    /** Get the Rhino context */
    public Context getContext() {
        if (context == null) {
        	// this check ensures we are running from the UI thread
        	if (Platform.isRunning())
        		Check.checkState(Display.getCurrent() != null);
            context = Context.enter();
            context.setGeneratingDebug(true);
            origClassLoader = context.getApplicationClassLoader();
            registerClassLoaderFor(ComponentSystemPlugin.getDefault());
        }
        return context;
    }
    
    /**
     * Ensure that interfaces from this bundle are visible
     * to Rhino.  This is required to be able to wrap interfaces
     * from plugins other than this one.
     * @param plugin the plugin to register
     */
    public void registerClassLoaderFor(Plugin plugin) {
        if (plugin == null)
            return;
        Bundle bundle = plugin.getBundle();
        Check.checkArg(bundle);
        getContext().setApplicationClassLoader(
                    new MyEclipseClassLoader(
                            bundle, 
                            getContext().getApplicationClassLoader()));
    }
    
    /**
     * Set the factory for creating and initializing script contexts
     */
    public void setScriptContextFactory(IScriptContextFactory factory) {
        this.contextFactory = factory;
    }

    /**
     * Compile a script file.  Returns cached instance if previously built.
     * @param incFile
     * @return new or existing Script instance
     * @throws IOException
     * @throws ScriptException
     */
    public Script compileFile(File incFile) throws IOException, ScriptException {
    	try {
    		incFile = incFile.getCanonicalFile();
    	} catch (IOException e) {
    		// keep
    	}
    	
        Script script = getCompiledScripts().get(incFile);
        if (script == null) {
            FileReader reader = null;
            try {
                reader = new FileReader(incFile);
            } catch (FileNotFoundException e) {
                Context.throwAsScriptRuntimeEx(e);
            }

            try {
            	if (DEBUG)
            		System.out.println("compiling " + incFile + " at level " + getContext().getOptimizationLevel());
                script = getContext().compileReader(reader, incFile.getAbsolutePath(), 1, null);
                getCompiledScripts().put(incFile, script);
            } catch (Throwable thr) {
                Messages.failure(thr, new MessageLocation(new Path(incFile.getAbsolutePath())),
                        "ScriptContext.CompilingScript", new Object[] { //$NON-NLS-1$
                        incFile.getName(), thr.getLocalizedMessage() } );
            }
        }
        
        return script;
    }
    
    class WrappedInterfaceInfo extends Pair<Class, IPath> {

		public WrappedInterfaceInfo(Class first, IPath second) {
			super(first, second);
		}
    }
    
	public Object getWrappedInterface(IPath path, Class theInterface, Scriptable instance, Scriptable scope) throws ScriptException {
		// get the compiled class wrapped in the scriptable version of the interface
		WrappedInterfaceInfo info = new WrappedInterfaceInfo(theInterface, path);
		
		Class wrappedClass = getInterfaceToWrappedInterfaceMap().get(info); 
		if (wrappedClass == null) {
			wrappedClass = RhinoPrototypeWrapper.wrapClass(path, scope, theInterface.getName(), theInterface);
			if (wrappedClass == null)
				return null;
			getInterfaceToWrappedInterfaceMap().put(info, wrappedClass);
		}

		// make an instance of the interface
		try {
            Class[] ctorParms = { ScriptRuntime.ContextFactoryClass, ScriptRuntime.ScriptableClass };
            Object[] ctorArgs = { context.getFactory(), instance };
            Object adapter = wrappedClass.getConstructor(ctorParms).
                                 newInstance(ctorArgs);
            
            if (!theInterface.isAssignableFrom(adapter.getClass())) {
                Messages.failure(new ClassCastException(theInterface.getName()),
                        new MessageLocation(path),
                        "RhinoPrototypeWrapper.PrototypeDoesNotImplement", //$NON-NLS-1$ 
                        new Object[] { theInterface.getName(), theInterface.getName() });
            }
            return adapter;
        } catch (Throwable thr) {
            Messages.failure(thr,
                    new MessageLocation(path),
                    "RhinoPrototypeWrapper.WrappingInstance", //$NON-NLS-1$ 
                    new Object[] { theInterface.getName(), thr.getLocalizedMessage() } );
            return null;
        }
	}
    
}
