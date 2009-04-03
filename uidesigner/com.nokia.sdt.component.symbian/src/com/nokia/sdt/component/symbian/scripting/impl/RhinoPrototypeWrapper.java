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
import com.nokia.sdt.component.symbian.scripting.ScriptingManager;
import com.nokia.sdt.scripting.ScriptException;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.mozilla.javascript.*;

import java.io.*;

public class RhinoPrototypeWrapper {

    private static final boolean DUMP_BYTECODE = false;

    public RhinoPrototypeWrapper() {
        super();
    }

    /**
     * Wrap a prototype or a single function in an interface
     * @param path 
     */
    public static Class wrapClass(IPath path,
    		Scriptable scope,
            String prototype, 
            Class theInterface) throws ScriptException {
        // wrap the prototype
        try {
            Class klass;
            
            if (ScriptingManager.DEBUG)
	            System.out.println("generating class for " + path + ": " + prototype);
            
            klass = getAdapterClass(prototype, 
                    Object.class, new Class[] { theInterface },
                    scope);
        
            return klass;
        } catch (Throwable thr) {
            Messages.failure(thr,
                    new MessageLocation(path),
                    "RhinoPrototypeWrapper.WrappingInstance", //$NON-NLS-1$ 
                    new Object[] { theInterface.getName(), thr.getLocalizedMessage() } );
        }
        
        // should not get here
        Check.checkState(false);
        return null;    
    }


    /* Taken from org.mozilla.javascript.JavaAdapter */
    private static ObjToIntMap getObjectFunctionNames(Scriptable obj)
    {
        Object[] ids = ScriptableObject.getPropertyIds(obj);
        ObjToIntMap map = new ObjToIntMap(ids.length);
        for (int i = 0; i != ids.length; ++i) {
            if (!(ids[i] instanceof String))
                continue;
            String id = (String) ids[i];
            Object value = ScriptableObject.getProperty(obj, id);
            if (value instanceof Function) {
                Function f = (Function)value;
                int length = 0;
                try {
	                length = ScriptRuntime.toInt32(
	                                 ScriptableObject.getProperty(f, "length")); //$NON-NLS-1$
	                if (length < 0) {
	                	length = 0;
	               	}
                } catch (EvaluatorException e) {
                	// not a javascript function
                }
                map.put(id, length);
            }
        }
        return map;
    }

    /* Taken from org.mozilla.javascript.JavaAdapter */
    private static Class getAdapterClass(String prototype, 
            Class superClass,
            Class[] interfaces, Scriptable obj) {

        ObjToIntMap names = getObjectFunctionNames(obj);

        String adapterName = "adapter_" + prototype; //$NON-NLS-1$
        byte[] code = JavaAdapter.createAdapterCode(names, adapterName, superClass,
                interfaces, null);

        if (DUMP_BYTECODE) {
            try {
                File baseDir = ComponentSystemPlugin.getDefault() != null ?
                        FileUtils.pluginRelativeFile(ComponentSystemPlugin.getDefault(),
                                "tmp") : new File("tmp");
                baseDir.mkdir();
                File file =  new File(baseDir, adapterName + ".class");
                FileOutputStream fos;
                fos = new FileOutputStream(file);
                fos.write(code);
                fos.close();
            } catch (IOException e) {
            }
        }
        
        Class adapterClass = loadAdapterClass(adapterName, code);
        return adapterClass;
    }

    /* Taken from org.mozilla.javascript.JavaAdapter */
    private static Class loadAdapterClass(String className, byte[] classBytes) {
        GeneratedClassLoader loader = SecurityController.createLoader(null,
                null);
        Class result = loader.defineClass(className, classBytes);
        loader.linkClass(result);
        return result;
    }
}
