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

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.scripting.ScriptException;

import org.eclipse.ui.views.properties.IPropertySource;
import org.osgi.framework.Bundle;

import java.io.File;

/**
 * This defines a JavaScript context, which encapsulates the
 * globals from one or more scripts.  This includes the definitions
 * of prototypes as well as other globals defined in those scripts. 
 * This context is shared among all the descendents of a component
 * hierarchy as well as multiple data models.
 * 
 *
 */
public interface IJavaScriptContext {
    /**
     * Register the given bundle as being available for dynamic
     * class lookups.  This is required to find interfaces outside
     * the class implementing the scripting.
     */
    void registerClassLoaderFor(Bundle bundle);
 
    /** 
     * Load and compile a script from a file.  The context
     * maintains a cache of scripts to enhance performance,
     * but checks the modification date to reload modified scripts.
     * <p>
     * This does not execute the global actions in the script.
     * 
     * @param scriptFile the script file
     * @return opaque script object
     * @throws ScriptException 
     */
    Object loadAndCompileScript(File scriptFile) throws ScriptException;

    /** 
     * Compile script from a string
     * <p>
     * This does not execute the global actions in the script.
     * 
     * @param scriptId a unique identifier for this script
     * @param scriptText the script text
     * @return opaque script object
     * @throws ScriptException note: message is already logged if failed
    */
    Object compileScript(String scriptId, String scriptText) throws ScriptException;

    /**
     * Push the global state for this Javascript context.
     * <p>
     * This initializes the global state to default then adds UI Designer 
     * functions and variables useful for scripting.
     * @return token to use when popping
     * @throws ScriptException note: message is already logged if failed
     */
    Object pushGlobalState() throws ScriptException;

    /**
     * Invoke the global actions in a script, adding the script's 
     * definitions to the global scope.  This must follow
     * a call to initializeGlobalState().
     *
     * @param script the compiled script from #loadAndCompileScript(File) or #compileScript(String, String)
     * @see #pushGlobalState()
     * @throws ScriptException note: message is already logged if failed
     */
    void runScript(Object script) throws ScriptException;
    
    /** 
     * Invoke a script which exposes the given prototype to
     * create a Rhino object of the given type (Scriptable 
     * under the covers).
     * This should follow a call to runScript().
     * 
     * @param script the compiled script from #loadAndCompileScript() or #compileScript()
     * @param prototype the name of the prototype class defined by the script
     * @return an instance of the prototype
     * @throws ScriptException note: message is already logged if failed
     * @see #runScript(Object)
     */
    Object createScriptObject(Object script, String prototype) throws ScriptException;

    /** 
     * Wrap a script object from createScriptObject() in a Java interface.
     * @param script the compiled script from #loadAndCompileScript() or #compileScript()
     * @param obj the object built from #createScriptObject() 
     * @param iface the interface we expect the prototype to implement
     * 
     * @return a wrapper for the prototype implementing iface
     * @throws ScriptException note: message is already logged if failed
     * @see #runScript(Object)
     * 
     */
    Object wrapScriptObjectInInterface(Object script, Object obj, Class iface) throws ScriptException;

    /**
     * Wrap a function from the script object 
     * @param script the compiled script
     * @param obj the object built from #createScriptObject() 
     * @param function the name of the function to look up
     * @return a wrapper for the prototype implementing iface
     * @throws ScriptException note: message is already logged if failed
     */
    IJavaScriptFunctionProxy getFunctionFromScriptObject(Object script, Object obj, String function) throws ScriptException;
    

    /**
     * Add global variables by exposing aspects of the given component instance.
     * <p>
     * The component instance is exposed by querying it for common properties
     * like name, parent, and children. The instance's component type is queried
     * for its properties and these are exposed as properties of the component
     * instance.
     * 
     * @param instance an instance of a component
     * @param propertySource
     *            the properties for the object
     * @throws ScriptException note: message is already logged if failed
     */
    void registerInstanceGlobals(IComponentInstance instance, IPropertySource propertySource) throws ScriptException;
    
    /**
     * Register another global into the scripting global scope.
     * This should be called after #initializeGlobalState().
     * <p>
     * Normal Objects are exposed as Scriptables.<br>
     * Booleans, Integers, Floats, and Doubles are exposed as ECMA types.<br>
     * Class is exposed as a prototype if Scriptable is implemented
     * (else a runtime error is thrown).
     * <p>
     * @param name the variable's name
     * @param obj the Java object exposed
     * @throws ScriptException note: message is already logged if failed
     * @see org.mozilla.javascript.Scriptable
     * @see #pushGlobalState()
     */
    void registerGlobal(String name, Object obj) throws ScriptException;

    /**
     * Pop the global state of the Javascript context.
     * <p>
     * This restores the global state to that of the outer caller.
     * @param token the value returned from #pushGlobalState()
     * @throws ScriptException note: message is already logged if failed
     */
    void popGlobalState(Object token) throws ScriptException;

    /**
     * Dispose the context by freeing all the generated objects
     * (#createScriptObjectImplementing) and the JavaScript scope.
     *
     */
    void dispose();
}
