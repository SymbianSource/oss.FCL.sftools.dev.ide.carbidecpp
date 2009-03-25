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

import com.nokia.sdt.component.symbian.*;
import com.nokia.sdt.component.symbian.scripting.impl.Messages;
import com.nokia.sdt.scripting.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.mozilla.javascript.*;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;

/**
 * This class defines global routines for use by Javascript.
 * 
 *
 */
public class ScriptGlobals {
    private static File includeBaseDir = new File(".");
    private static IScriptContext includeContext = null;

    public static void setupGlobals(IScriptScope scope) {
        try {
            scope.defineObject("include", 
                    ScriptGlobals.class.getMethod(
                            "scriptInclude", 
                            new Class[] { String.class }));

            scope.defineObject("getLocalizedStrings",
                    ScriptGlobals.class.getMethod(
                                    "scriptGetLocalizedStrings", 
                                    new Class[] { String.class })
                                    );

            scope.defineObject("print", 
                    ScriptGlobals.class.getMethod(
                            "scriptPrint", 
                            new Class[] { String.class }));
            
            scope.defineObject("println", 
                    ScriptGlobals.class.getMethod(
                            "scriptPrintln", 
                            new Class[] { String.class }));

            scope.defineObject("titleCase", 
                    ScriptGlobals.class.getMethod(
                            "scriptTitleCase", 
                            new Class[] { String.class }));
            
            scope.defineObject("getPluginClass", 
                    ScriptGlobals.class.getMethod(
                            "scriptGetPluginClass", 
                            new Class[] { String.class, String.class }));

            scope.defineObject("formatString", 
                    ScriptGlobals.class.getMethod(
                            "scriptFormatString", 
                            new Class[] { Context.class, Scriptable.class, Object[].class, Function.class }));

            scope.defineObject("makeValidIdentifier", 
                    ScriptGlobals.class.getMethod(
                            "scriptMakeValidIdentifier", 
                            new Class[] { String.class }));

            scope.defineObject("newStatusBuilder",
                    ScriptGlobals.class.getMethod(
                                    "scriptNewStatusBuilder", 
                                    new Class[0])
                                    );
            scope.defineObject("IStatus",
                    IStatus.class);
            

        } catch (Exception e) {
            ComponentSystemPlugin.log(e);
        }        
    }
    
    /**
     * Script-invoked include() routine
     * @param relativeFilePath
     */
    static public void scriptInclude(String relativeFilePath) {
        // need to call setIncludeContext()!
        Check.checkState(includeContext != null);
        
        File incFile = new File(includeBaseDir, relativeFilePath);
        if (!incFile.exists()) {
            Context.throwAsScriptRuntimeEx(new FileNotFoundException(
                    MessageFormat.format(
                            Messages.getString("ComponentGlobals.IncludeNotFound"), //$NON-NLS-1$
                            new Object[] { incFile.getAbsolutePath() })));
        }
        
        includeContext.includeFile(incFile);
    }

    /**
     * Get a localized strings container
     * @param basename the current file-relative path providing the
     * base name of a set of localized *.properties files 
     * @return ILocalizedStrings
     */
    public static ILocalizedStrings scriptGetLocalizedStrings(String basename) {
        return new LocalizedStrings(includeBaseDir, basename);
    }
    
    public static void scriptPrint(String str) {
        System.out.print(str);
    }

    public static void scriptPrintln(String str) {
        System.out.println(str);
    }

    public static String scriptTitleCase(String str) {
        return TextUtils.titleCase(str);
    }

    public static String scriptMakeValidIdentifier(String str) {
        return TextUtils.legalizeIdentifier(str);
    }

   /**
     * Format a string using MessageFormat#format().
     * <p>
     * Runtime arguments are varargs in the form:<br> 
     * 	(format_string, [ list, of, arguments ])<br>
     * or<br>
     * 	(format_string, arg0, arg1, ...)
     */
    public static String scriptFormatString(Context cx, Scriptable thisObj, Object[] args, Function func) {
        Object format = ScriptingUtils.unwrap(args[0]);
        Object[] params;
        if (args.length == 2) {
        	params = ScriptingUtils.unwrapArray(args[1], true);
        } else {
        	params = new Object[args.length - 1];
        	System.arraycopy(args, 1, params, 0, args.length - 1);
        }
    	return MessageFormat.format(format.toString(), params);
    }
    
    /**
     * Create a StatusBuilder object
     * @return new StatusBuilder
     */
    static public StatusBuilder scriptNewStatusBuilder() {
        return new StatusBuilder(ComponentSystemPlugin.getDefault());
    }



    ////////////////////////////
    
    /** 
     * Get the base directory for include() 
     * @return base directory
     */
    public static File getIncludeBase() {
        return includeBaseDir;
    }

    /** 
     * Get the global context for include() 
     * @return scope
     */
    public static IScriptContext getIncludeContext() {
        return includeContext;
    }

    /**
     * Set the base directory for include()
     * @param baseDir base directory for include() lookups
     */
    public static void setIncludeBase(File baseDir) {
        Check.checkArg(baseDir);
        ScriptGlobals.includeBaseDir = baseDir;
    }

    /**
     * Set the base directory for include()
     * @param globalContext the context into which include() contents are compiled
     */
    public static void setIncludeContext(IScriptContext globalContext) {
        ScriptGlobals.includeContext = globalContext;
    }

    /**
     * Load a class from another plugin.
     * @param pluginId
     * @param className
     * @return a wrapped class object, else an exception
     */
    public static Object scriptGetPluginClass(String pluginId, String className) {
        Bundle bundle = Platform.getBundle(pluginId);
        if (bundle == null)
            Context.throwAsScriptRuntimeEx(new BundleException(pluginId));
        
        try {
            Class cl = bundle.loadClass(className);
            return new NativeJavaClass((Scriptable) ScriptingManager.getInstance().getGlobalScope().getScriptable(), cl);
        } catch (ClassNotFoundException e) {
            Context.throwAsScriptRuntimeEx(e);
            return null;
        }
    }


    /**
     * Wrap the given code, which may include other scripts, in a
     * safe way 
     * @param context
     * @param wrapper wrapped code
     * @return anything returned from the wrapped code
     */
    public static Object wrapScriptHandlingCode(IScriptContext context, IScriptContextWrapper wrapper) throws ScriptException {
        IScriptContext previous = ScriptGlobals.getIncludeContext();
        ScriptGlobals.setIncludeContext(context);
        Object ret = null;
        try {
            ret = wrapper.run(context);
        } catch (ScriptException e) {
            throw e;
        } catch (Throwable e) {
            throw new ScriptException(e);        
        } finally {
            ScriptGlobals.setIncludeContext(previous);
        }
        return ret;
    }

    /**
     * Wrap the given code, which may include other scripts, in a
     * safe way 
     * @param context
     * @param includeBase base directory for any includes
     * @param wrapper wrapped code
     * @return anything returned from the wrapped code
     */
    public static Object wrapScriptHandlingCode(IScriptContext context, File includeBase, IScriptContextWrapper wrapper) throws ScriptException {
        IScriptContext previous = ScriptGlobals.getIncludeContext();
        File previousBase = ScriptGlobals.getIncludeBase();
        ScriptGlobals.setIncludeContext(context);
        ScriptGlobals.setIncludeBase(includeBase);
        Object ret = null;
        try {
            ret = wrapper.run(context);
        } catch (ScriptException e) {
            throw e;
        } catch (Throwable e) {
            throw new ScriptException(e);
        } finally {
            ScriptGlobals.setIncludeBase(previousBase);
            ScriptGlobals.setIncludeContext(previous);
        }
        return ret;
    }


}
