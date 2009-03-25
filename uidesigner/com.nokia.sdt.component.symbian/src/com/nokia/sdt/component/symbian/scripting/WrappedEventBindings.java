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
import com.nokia.sdt.datamodel.adapter.IEventBinding;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * The array of event bindings from instance.events
 * 
 * 
 *
 */
public class WrappedEventBindings extends ScriptableObject {

    private static final long serialVersionUID = 1L;

    IComponentInstance instance;

    /** 
     * This constructor is needed to prevent Rhino from barfing on our Java-only constructors --
     * don't call this directly!
     */
    public WrappedEventBindings() {
    }
    
    // needed to prevent Rhino from barfing on our Java-only constructors
    public void jsConstructor() {
    }

    WrappedEventBindings(IComponentInstance instance) {
        this();
        
        Check.checkArg(instance);
        this.instance = instance;
    }
    
    public void init() {
        defineFunctionProperties(new String[] { 
                "toString",             // for printing foo 
                }, WrappedEventBindings.class, ScriptableObject.READONLY); //$NON-NLS-1$
        
    }

    public String toString() {
        return "<WrappedEventBindings for " + instance + ">"; //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    public String getClassName() {
        return "EventBindings"; //$NON-NLS-1$
    }

    public Object get(String name, Scriptable start) {
    	IEventBinding binding = instance.findEventBinding(name);
    	if (binding != null)
    		return ComponentWrappers.getInstance(instance).getWrappedEventBinding(binding);
        return null;
    }

    public Object get(int index, Scriptable start) {
        return null;
    }

    public boolean has(String name, Scriptable start) {
    	IEventBinding binding = instance.findEventBinding(name);
    	if (binding != null)
    		return true;
        return false;
    }

    public boolean has(int index, Scriptable start) {
        return false;
    }

    /*
     *  (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#getDefaultValue(java.lang.Class)
     */
    public Object getDefaultValue(Class hint) {
        return null;
    }
    
}
