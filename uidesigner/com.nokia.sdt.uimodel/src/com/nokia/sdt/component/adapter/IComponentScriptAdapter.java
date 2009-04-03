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

package com.nokia.sdt.component.adapter;

import com.nokia.sdt.scripting.*;

import java.io.File;

/**
 * This is an adapter which handles associating Javascript with a component,
 * e.g., for use by other component adapters.
 * <li>This returns script contexts for scripts, built from external files or
 * strings.
 * <li>It maintains one context per script or string, so globals/etc. are
 * shared.
 * 
 * 
 * 
 */
public interface IComponentScriptAdapter extends IComponentAdapter {

    /**
     * Get the context for the given script file. This only exists if the script
     * has been explicitly compiled before.
     * 
     * @param scriptFile
     *            a file
     * @return context for the script, or null
     * @see #getContextForFile(File, IScriptScope)
     */
    public IScriptContext findContextForFile(File scriptFile);

    /**
     * Get the context for the given script, which was compiled as literal text.
     * This only exists if the script text has been explicitly created before.
     * 
     * @param uniqueId
     *            identifier for script text
     * @return context for the script, or null
     * @see #getContextForText(String, String, IScriptScope)
     */
    public IScriptContext findContextForText(String uniqueId);

    /**
     * Find or create a context for the given script file. When created, this
     * loads the script, sets up a scope and links it to an optional parent scope, 
     * compiles the script, then runs it to set up its
     * global state and code.  Nothing happens if the context already
     * existed.
     * 
     * @param scriptFile
     *            a file
     * @param parentScope
     *            the scope which will be accessible from the new
     *            context, may be <code>null</code> to indicate
     *            top-level globals
     * @return new context for the script
     * @throws IllegalArgumentException
     *             if the file has already been compiled
     * @see #findContextForFile(File)
     */
    public IScriptContext getContextForFile(File scriptFile,
            IScriptScope parentScope) throws ScriptException;

    /**
     * Find or create a context for the given script text. When creating, this
     * sets up a scope and links it to the optional parent scope,
     * compiles the script, then runs it to set up its global state and code.
     * Nothing happens if the context already exists.
     * 
     * @param scriptText
     *            script text
     * @param parentScope
     *            the scope which will be accessible from the new
     *            context, may be <code>null</code>
     * @return new context for the script
     * @throws IllegalArgumentException
     *             if the file has already been compiled
     * @see #findContextForText(String)
     */
    public IScriptContext getContextForText(String uniqueId, String scriptText,
            IScriptScope parentScope) throws ScriptException;
    
}
