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

import com.nokia.cpp.internal.api.utils.core.CacheMap;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.IDisposable;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.sdt.component.symbian.scripting.ScriptingManager;
import com.nokia.sdt.scripting.*;

import org.eclipse.core.runtime.Path;
import org.mozilla.javascript.*;

import java.io.PrintStream;
import java.lang.reflect.*;
import java.util.*;

public class ScriptScope implements IScriptScope {

    private IScriptContext context;
    Scriptable scope;
    private ScriptScope parentScope;

    /** Disposable objects */
    private CacheMap objects;
    
    Context getContext() {
        return ScriptingManager.getInstance().getContext();
    }


    /** Create the global scope */
    public ScriptScope() {
        objects = new CacheMap();
        this.context = null;
        this.parentScope = null;
        this.scope = (ScriptableObject) getContext().initStandardObjects(null, true);
    }
    
    /** Create a global scope for the given context
     * @param context 
     * @param parentScope
     */  
    public ScriptScope(IScriptContext context, IScriptScope parentScope) {
        this.context = context;
        this.parentScope = (ScriptScope) parentScope;
        //this.scope = getContext().newObject(((ScriptScope)ScriptingManager.getInstance().getGlobalScope()).getScriptable()); // ((ScriptScope) parentScope).getScriptable());
        //this.scope.setParentScope(((ScriptScope) parentScope).getScriptable());
        //this.scope = getContext().newObject(((ScriptScope) parentScope).getScriptable());
        scope = new ScriptableObject() {
            private static final long serialVersionUID = -8789621630593000374L;
            public Object get(String name, Scriptable start) {
                return super.get(name, start);
            }
            public boolean has(String name, Scriptable start) {
                return super.has(name, start);
            }
            public String getClassName() {
                return null;
            }};
        //getContext().initStandardObjects((ScriptableObject) scope);
        scope.setPrototype(((ScriptScope)parentScope).scope);
    }

    /** Create a scope which is a child of another,
     * so that everything in the parent is visible
     * @param scope 
     * @param parentScope
     */  
    public ScriptScope(ScriptableObject scope, ScriptScope parentScope) {
        this.context = null;
        if (scope == null)
            scope = new ScriptableObject() {
                private static final long serialVersionUID = -8789621630593000374L;
                public Object get(String name, Scriptable start) {
                    return super.get(name, start);
                }
                public boolean has(String name, Scriptable start) {
                    return super.has(name, start);
                }
                public String getClassName() {
                    return null;
                }};
        this.parentScope = parentScope;
        this.scope = scope;
        //this.scope.setParentScope(parentScope.scope);
    }

    public IScriptContext getNearestContext() {
        ScriptScope scope = this;
        while (scope != null) {
            if (scope.context != null)
                return scope.context;
            scope = scope.parentScope;
        }
        return null;
    }


    MessageLocation getMessageLocation() {
        IScriptContext context = getNearestContext();
        if (context == null || context.getPath() == null)
            return new MessageLocation(new Path("."), 0, 0);
        return new MessageLocation(context.getPath());
    }
    
    /*
     *  (non-Javadoc)
     * @see com.nokia.sdt.utils.IDisposable#dispose()
     */
    public void dispose() {
        objects.disposeAll();
    }
    
    /*
     *  (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.scripting.IScriptScope#getScriptContext()
     */
    public IScriptContext getScriptContext() {
        ScriptScope owningScope = this;
        while (owningScope != null) {
            if (owningScope.context != null)
                return owningScope.context;
            owningScope = owningScope.parentScope; 
        }
        return null;
    }
    
    /*
     *  (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.scripting.IScriptScope#defineVariable(java.lang.String, java.lang.Object)
     */
    public void defineObject(String name, Object value)
            throws ScriptException {
        try {
            registerName(name, value);
        } catch (Throwable t) {
            Messages.failure(t,
                    getMessageLocation(),
                    "ScriptScope.AddingVariableToGlobals", 
                    new Object[] { name, t.getLocalizedMessage() } ); //$NON-NLS-1$
        }
    }

    public IScriptScope getParentScope() {
        return parentScope;
    }
    
    /**
     * Register a Java object in Javascript with the given global name.
     * 
     * @param name
     *            the global name
     * @param obj
     *            the object to wrap
     * @throws Exception
     */
    private void registerName(final String name, Object obj) throws Exception {
        Object wrapped;
        
        if (obj instanceof Class) {
            // define the class as a prototype
            Class clazz = (Class) obj;
            
            if (Scriptable.class.isAssignableFrom(clazz)) {
                ScriptableObject.defineClass(scope, clazz, false);
            } else {
                
                wrapped = getContext().getWrapFactory().wrap(getContext(), scope, obj, null);
                ScriptableObject.putProperty(scope, name, wrapped);
            }
            
            // and add its constant fields
            try {
                Scriptable classScope = (Scriptable) scope.get(name, scope);
                ScriptableObject sObj;
                if (!(classScope instanceof ScriptableObject)) {
                    sObj = new ScriptableObject(scope, classScope) {
                        private static final long serialVersionUID = 2104472695251417740L;

                        public String getClassName() {
                            return name;
                        }
                    };
                    ScriptableObject.putProperty(scope, name, sObj);
                } else {
                    sObj = (ScriptableObject) classScope;
                }
                addConstants(clazz, sObj);
                if (classScope instanceof ScriptableObject)
                    ((ScriptableObject) classScope).sealObject();
            } catch (ClassCastException e) {
            } catch (NullPointerException e) {
            }
        } else if (obj instanceof Member) {
            FunctionObject fobj = new FunctionObject(name, (Member) obj, scope);
            wrapped = Context.javaToJS(fobj, scope);
            ScriptableObject.putProperty(scope, name, wrapped);
        } else {
            wrapped = Context.javaToJS(obj, scope);
            ScriptableObject.putProperty(scope, name, wrapped);
        }
    }

    /**
     * Add constants from class to prototype.
     * <p>
     * Adapted from Rhino code for defineClass().
     * 
     * @param clazz
     * @param classScope
     */
    private void addConstants(Class clazz, Scriptable classScope) {
        
        // also add constants
        Field[] fields = null;
        fields = clazz.getFields();
        for (int i=0; i < fields.length; i++) {
            if (Modifier.isPublic(fields[i].getModifiers())
                    && Modifier.isFinal(fields[i].getModifiers())
                    && Modifier.isStatic(fields[i].getModifiers())) {
                try {
                    classScope.put(fields[i].getName(), classScope, fields[i].get(null));
                } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e) {
                }
            }
        }
        
    }

    /*
     *  (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.scripting.IScriptContext#createInstance(java.lang.String)
     */
    public IScriptObject createInstance(String prototype, String name)
            throws ScriptException {

        scope.setParentScope(null);
        
        try {
            // create an instance of the prototype
            final ScriptableObject instance = (ScriptableObject) getContext().newObject(scope, prototype);
            
            // register name if needed
            if (name != null) {
                scope.put(name, scope, instance);
            }
            
            // remember how to dispose it, if necessary
            if (instance != null) {
                // check for a dispose() function
                Object obj = lookupInScope(instance, "dispose"); //$NON-NLS-1$
                if (obj != Scriptable.NOT_FOUND && obj instanceof Function) {
                    final Function theFunction = (Function) obj;

                    // now wrap it in IDisposable
                    IDisposable disposer = new IDisposable() {
                        public void dispose() {
                            theFunction.call(getContext(), scope, instance, new Object[0]);
                        }
                    };

                    objects.put(instance, disposer);
                }
            }
            
            return new ScriptObject(instance, this);
        } catch (RhinoException e) {
            Messages.failure(e,
                    getMessageLocation(),
                    "ScriptScope.CreatingInstance",
                    new Object[] { prototype, e.getLocalizedMessage() }); //$NON-NLS-1$
            return null;
        } finally {
            // restore scope
            if (parentScope != null)
                scope.setParentScope(parentScope.scope);
        }
        
    }
    
    public IScriptObject createInstance(String prototype) throws ScriptException {
        return createInstance(prototype, null);
    }

    static protected Object lookupInScope(Scriptable scope, String name) {
        while (scope != null) {
            Object obj = scope.get(name, scope);
            if (obj != null && obj != Scriptable.NOT_FOUND)
                return obj;
            if (scope.getPrototype() != null) {
                obj = scope.getPrototype().get(name, scope);
                if (obj != null && obj != Scriptable.NOT_FOUND)
                    return obj;
            }
            scope = scope.getParentScope();
        }
        return scope;
    }

    public Object getScriptable() {
        return scope;
    }
    
    public IScriptScope createScope() {
        Scriptable newScope = getContext().newObject(scope);
        newScope.setPrototype(scope);
        newScope.setParentScope(null);
        return new ScriptScope((ScriptableObject)newScope, this);
    }
    
    public boolean objectExists(String name) {
        return scope.get(name, scope) != Scriptable.NOT_FOUND;
    }
    
    private Object unwrapValue(Object jsVal) {
        if (jsVal instanceof Wrapper)
            return ((Wrapper) jsVal).unwrap();
        else if (jsVal == Scriptable.NOT_FOUND)
            return null;
        else
            return jsVal;
    }
    
    public Object findObject(String name) {
        Object val = scope.get(name, scope);
        return unwrapValue(val);
    }
    
    public void deleteObject(String name) {
        scope.delete(name);
    }
    
    public void deleteAllObjects() {
        Object[] ids = scope.getIds();
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] instanceof String)
                scope.delete((String) ids[i]);
            else
                scope.delete(((Integer)ids[i]).intValue());
        }
    }
    
    public void deleteScope(IScriptScope scope) {
        scope.deleteAllObjects();
        scope.dispose();
    }
    
    public boolean objectVisible(String name) {
        Object jsVal = lookupInScope(scope, name);
        return jsVal != Scriptable.NOT_FOUND;
    }
    
    public Object searchObject(String name) {
        Object jsVal = lookupInScope(scope, name);
        return unwrapValue(jsVal);
    }
    
    public void dump(String name, PrintStream stream) {
        Object[] ids = scope.getIds();
        List list = Arrays.asList(ids);
        Collections.sort(list);
        stream.println("=== Dump of globals in " + name);
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object id = (Object) iter.next();
            stream.print(id+",");
        }
        stream.println();
    }
    
    public Object callFunction(IScriptScope execScope, String functionName, Object[] args) {
        Object func = searchObject(functionName);
        Check.checkArg(func);
        Check.checkArg(func instanceof Function);
        return ((Function) func).call(getContext(), ((ScriptScope)execScope).scope, scope, args);
        
    }
    
    public void include(IScriptScope otherScope_) {
        Check.checkArg(otherScope_ instanceof ScriptScope);
        ScriptScope otherScope = (ScriptScope) otherScope_;
        Object ids[];
        Scriptable scriptable = otherScope.scope;
        
        // don't do this, since it gets weird getters and setters
        // that lead to infinite recursion
        //if (scriptable instanceof ScriptableObject)
//            ids = ((ScriptableObject) scriptable).getAllIds();
        //else
            ids = scriptable.getIds();
        
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] instanceof String) {
                String id = (String) ids[i];
                //System.out.println("copying id = " + id);
                Object value = scriptable.get(id, scriptable);
                scope.put(id, scope, value);
                if (value instanceof ScriptableObject) {
                    ((ScriptableObject) value).setParentScope(scope);
                }
                
            } else {
                Integer id = (Integer) ids[i];
                Object value = scriptable.get(id.intValue(), scriptable);
                scope.put(id.intValue(), scope, value);
                if (value instanceof ScriptableObject) {
                    ((ScriptableObject) value).setParentScope(scope);
                }

            }
        }
    }
    
    
}
