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
package com.nokia.sdt.component.symbian.delegate;

import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptAdapterImplBase;
import com.nokia.sdt.datamodel.adapter.IImplementationDelegate;
import com.nokia.sdt.scripting.ScriptException;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;

import java.util.ArrayList;
import java.util.List;

public class ImplementationDelegateAdapterScript 
			extends ScriptAdapterImplBase implements IImplementationDelegate {
	public ImplementationDelegateAdapterScript() {
        super(IImplementationDelegate.class, IScriptImplementationDelegate.class);
	}

    protected void registerScriptGlobals(INameRegistrar registrar) throws ScriptException {
        // none
    }

    public List<Class> getDelegateInterfaces() {
    	String[] interfaceNames = null;
    	try {
    		interfaceNames = (String[]) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

				public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
				}

				public Object run() {
				    WrappedInstance instance = getWrappedInstance();
					return ((IScriptImplementationDelegate) getImpl()).getDelegateInterfaceNames(instance);
				}
    			
    		});
    	} catch (ScriptException e) {
            // already logged
        }
    	if (interfaceNames != null) {
			List<Class> interfaces = new ArrayList();
			for (String interfaceName : interfaceNames) {
				Class interfaceType = null;
				try {
					interfaceType = Class.forName(interfaceName);
				} catch (ClassNotFoundException e) {
					Check.failedArg(e);
				}
				interfaces.add(interfaceType);
			}
			return interfaces;
    	}
    	
    	return null;
    }
    
    public List<EObject> getDelegates(final Class interfaceType) {
		WrappedInstance[] wrappedInstances = null;
        try {
        	wrappedInstances = (WrappedInstance[]) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

			public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
			}

			public Object run() {
			    WrappedInstance instance = getWrappedInstance();
			    String name = interfaceType.getName();
				return
			    	((IScriptImplementationDelegate) getImpl()).getDelegates(instance, name);
			}

         });
        } catch (ScriptException e) {
            // already logged
        }
		if (wrappedInstances != null) {
			List<EObject> objects = new ArrayList();
			for (WrappedInstance wrappedInstance : wrappedInstances) {
				objects.add(wrappedInstance.getEObject());
			}
			return objects;
		}
		
		return null;
	}
}
