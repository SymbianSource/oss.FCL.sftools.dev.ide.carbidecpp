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

import com.nokia.sdt.scripting.ScriptException;



/**
 * This interface wraps a call to script code for a particular
 * component.  
 */
public interface IScriptCodeWrapper {
    /** 
     * Register transient globals for the given script.
     * These are available only during the duration of the
     * execution and are cleared out later.
     * <p>
     * Use 'transientGlobals.defineObject(String, Object)' to register
     * globals.
     * @param registrar
     */
    public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException; 
    
    /**
     * Run the script code. 
     * @return value from script, or null.
     */
    public Object run();
}
