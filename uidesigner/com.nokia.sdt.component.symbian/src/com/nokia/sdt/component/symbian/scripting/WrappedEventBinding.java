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

import com.nokia.sdt.component.event.IEventDescriptor;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.datamodel.adapter.IEventBinding;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.mozilla.javascript.ScriptableObject;

import java.lang.reflect.Method;

/**
 * Wrap the IEventBinding and IEventDescriptor interface for use by Javascript
 * 
 * 
 *
 */
public class WrappedEventBinding extends ScriptableObject implements IEventBinding {

    private static final long serialVersionUID = 1L;

    IEventBinding binding;

    /** 
     * This constructor is needed to prevent Rhino from barfing on our Java-only constructors --
     * don't call this directly!
     */
    public WrappedEventBinding() {
    }
    
    // needed to prevent Rhino from barfing on our Java-only constructors
    public void jsConstructor() {
    }

    WrappedEventBinding(IEventBinding binding) {
        this();
        
        Check.checkArg(binding);
        this.binding = binding;
    }
    
    public void init() {
        try {
	        Class cls = getClass();
	        int propertyFlags = ScriptableObject.READONLY|ScriptableObject.PERMANENT;
	        
	        // note: SymbianNameGenerator depends on these names
	        Method getterMethod = cls.getMethod("jsGet_eventId", (Class[])null);//$NON-NLS-1$
	        defineProperty("eventId", null, getterMethod, null, propertyFlags);//$NON-NLS-1$
	        getterMethod = cls.getMethod("jsGet_eventName", (Class[])null);//$NON-NLS-1$
	        defineProperty("eventName", null, getterMethod, null, propertyFlags);//$NON-NLS-1$
            getterMethod = cls.getMethod("jsGet_handlerName", (Class[])null);//$NON-NLS-1$
            defineProperty("handlerName", null, getterMethod, null, propertyFlags);//$NON-NLS-1$
            getterMethod = cls.getMethod("jsGet_handlerSymbol", (Class[])null);//$NON-NLS-1$
            defineProperty("handlerSymbol", null, getterMethod, null, propertyFlags);//$NON-NLS-1$
       }
        catch (NoSuchMethodException x) {
        	// shouldn't happen
        	ComponentSystemPlugin.log(x);
        }

    	defineFunctionProperties(new String[] { 
                "toString",             // for printing foo 
                }, WrappedEventBinding.class, ScriptableObject.READONLY); //$NON-NLS-1$
        
    }

    public String toString() {
        return "<WrappedEventBinding " + binding + ">"; //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    public String getClassName() {
        return "EventBinding"; //$NON-NLS-1$
    }

    public String jsGet_eventId() {
    	return binding.getEventDescriptor().getId();
    }
    
    public String jsGet_eventName() {
    	return binding.getEventDescriptor().getDisplayText();
    }
    
    public String jsGet_handlerName() {
    	return binding.getHandlerName();
    }

    public String jsGet_handlerSymbol() {
    	return binding.getHandlerSymbolInformation();
    }

    /*
     *  (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#getDefaultValue(java.lang.Class)
     */
    public Object getDefaultValue(Class hint) {
        return null;
    }
    
    ///////////////

	public EObject getOwner() {
		return binding.getOwner();
	}

	public IEventDescriptor getEventDescriptor() {
		return binding.getEventDescriptor();
	}

	public String getHandlerName() {
		return binding.getHandlerName();
	}

	public void setHandlerName(String text) {
		binding.setHandlerName(text);
	}

	public String getHandlerSymbolInformation() {
		return binding.getHandlerSymbolInformation();
	}

	public void setHandlerSymbolInformation(String symbolInformation) {
		binding.setHandlerSymbolInformation(symbolInformation);
	}

	public boolean isSameHandler(IEventBinding other) {
		return binding.isSameHandler(other);
	}

    
}
