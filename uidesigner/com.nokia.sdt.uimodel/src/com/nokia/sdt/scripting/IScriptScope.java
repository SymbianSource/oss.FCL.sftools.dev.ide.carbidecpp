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

package com.nokia.sdt.scripting;

import com.nokia.cpp.internal.api.utils.core.IDisposable;

import java.io.PrintStream;

/**
 * This interface represents the scope (namespace) associated
 * with a script object.  One exists for globals, for
 * prototypes compiled into scripts, and for instances of
 * objects created by script.
 * 
 * 
 *
 */
public interface IScriptScope extends IDisposable {
    /**
     * Get the context that owns this scope
     */
    IScriptContext getScriptContext();
    
    /**
     * Get the parent scope.
     */
    IScriptScope getParentScope();

    /**
     * Create a new scope with this one as parent.
     */
    IScriptScope createScope();
    
    /**
     * Delete the given child scope, disposing any objects it
     * references and removing it from the scope chain
     */
    void deleteScope(IScriptScope scope);

    /** 
     * Create an unnamed instance of the given prototype from the script.
     * This script will run in its own scope.
     * 
     * @param prototype the name of the prototype class defined by the script
     * @return an instance of the prototype
     * @throws ScriptException 
     */
    IScriptObject createInstance(String prototype) throws ScriptException;

    /** 
     * Create a named instance of the given prototype from the script.
     * This script will run in its own scope.
     * 
     * @param prototype the name of the prototype class defined by the script
     * @param name the name of the created object
     * @return an instance of the prototype
     * @throws ScriptException 
     */
    IScriptObject createInstance(String prototype, String name) throws ScriptException;

    /**
     * Define a variable/property in the scope.  
     * This overwrites any previous definition without error.
     * @param name the name used for the item 
     * @param value the value of the item.  This will be automatically
     * wrapped for use by script, if possible.  Classes will be exposed
     * as prototypes, constants visible from interfaces will be exported
     * as well.
     */
    void defineObject(String name, Object value) throws ScriptException;

    /**
     * Tell whether an object with the given name exists in scope.
     * @param name
     * @return true if exists
     */
    boolean objectExists(String name);
    
    /**
     * Find the named object.  This is automatically converted
     * to a Java-usable value.  It is not guaranteed to be
     * identical to a value set via defineObject(), if script
     * code has modified the value.
     * @param name the object name
     * @return the value of the object, or null if not found
     * @see #objectExists(String)
     */
    Object findObject(String name);
    
    /**
     * Delete a variable or function
     */
    void deleteObject(String name);
    
    /**
     * Clear all objects in the scope
     */
    void deleteAllObjects();

    /**
     * See if an object is visible in the scope chain
     * @param name object name
     * @return true if visible 
     */
    boolean objectVisible(String name);

    /**
     * Search the scope chain for the named object
     * @param name
     * @return object value
     * @see #findObject(String)
     */
    Object searchObject(String name);

    /**
     * Dump the contents of the scope
     * @param name 
     * @param stream
     */
    void dump(String name, PrintStream stream);

    /**
     * Call the given function
     * @param execScope the scope to use at execution time
     * @param functionName the function to execute
     * @param args the list of arguments
     * @return anything returned by function
     */
    Object callFunction(IScriptScope execScope, String functionName, Object[] args);

    /**
     * Include the contents of the given scope in our global scope
     * @param scope
     */
    void include(IScriptScope scope);

    /**
     * @return the scriptable behind the script (org.mozilla.javascript.Scriptable)
     */
    Object getScriptable();
}
