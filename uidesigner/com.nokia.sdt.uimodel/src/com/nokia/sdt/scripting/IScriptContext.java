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

import org.eclipse.core.runtime.IPath;

import java.io.File;
import java.io.Reader;

/**
 * This interface maintains the shared context of one script:
 * the code associated with it, the globals that are shared
 * among all instances of prototypes created by the script, etc.
 * <li>Prototypes from the scripts may be instantiated and wrapped
 * in Java interfaces.
 * <li>Scripts may instantiated with a set of global variables
 * for more compact script code.
 * <li>Namespaces/scopes defined by scripts for a component can be
 * linked to other contexts. 
 * 
 * 
 *
 */
public interface IScriptContext {
    /**
     * Compile the contents of the given reader and execute it
     * in the script's scope.  
     * <p>
     * This should be run only once.
     * 
     * @param path the path providing the text being read (e.g. for errors)
     * @param reader a reader providing script text
     */
    void compileAndExecute(IPath path, Reader reader) throws ScriptException;

    /**
     * Compile the contents of the given file and execute it
     * in the script's scope.  This variant caches compiled files.    
     * <p>
     * This should be run only once.
     * 
     * @param file the file to read
     */
    void compileAndExecute(File file) throws ScriptException;

    /**
     * Get the path associated last with this context
     */
    IPath getPath();
    
    /**
     * Get the parent scope.  This is never null.  The root of
     * the scopes is an implicit global scope shared by all
     * scopes.  This will be the script scope of the parent
     * context.
     * @return scope 
     */
    IScriptScope getParentScope();

    /**
     * Get the scope used for transient globals.  Any globals
     * defined by script (and not previously defined at script
     * level) go here. 
     * @return scope for transient globals
     */
    IScriptScope getTransientGlobalScope();

    /** 
     * Get the scope for the script, where its
     * functions and variables are defined.
     */
    IScriptScope getScope();
    
    /**
     * Include another file into this context
     */
    void includeFile(File incFile);
    
}
