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

import com.nokia.sdt.component.*;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.symbian.Component;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;

import org.mozilla.javascript.ScriptableObject;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import java.io.*;
import java.lang.reflect.Method;

/**
 * This class wraps IComponent for Javascript
 * 
 * 
 * @see com.nokia.sdt.component.IComponent
 */
public class WrappedComponent extends ScriptableObject implements IComponent /*implements Wrapper*/ {

    /**
     * 
     */
    private static final long serialVersionUID = 7910990487871719241L;

    IComponent component;

    private IAttributes attributes;
    
    /** 
     * This constructor is needed to prevent Rhino from barfing on our Java-only constructors --
     * don't call this directly!
     */
    public WrappedComponent() {
    }
    
    // needed to prevent Rhino from barfing on our Java-only constructors
    public void jsConstructor() {
    }
    
    public void init() {
        // for foo.properties.value
        ScriptableObject.putProperty(this, "id", jsGet_id()); //$NON-NLS-1$
        ScriptableObject.putProperty(this, "friendlyName", jsGet_friendlyName()); //$NON-NLS-1$
        
        try {
            Class cls = getClass();
            int propertyFlags = ScriptableObject.READONLY|ScriptableObject.PERMANENT;
            Method getterMethod = cls.getMethod("jsGet_attributes", (Class[])null);//$NON-NLS-1$
            defineProperty("attributes", null, getterMethod, null, propertyFlags);//$NON-NLS-1$
            getterMethod = cls.getMethod("jsGet_minSDKVersion", (Class[])null);//$NON-NLS-1$
            defineProperty("minSDKVersion", null, getterMethod, null, propertyFlags);//$NON-NLS-1$
            getterMethod = cls.getMethod("jsGet_maxSDKVersion", (Class[])null);//$NON-NLS-1$
            defineProperty("maxSDKVersion", null, getterMethod, null, propertyFlags);//$NON-NLS-1$
       }
        catch (NoSuchMethodException x) {
            // shouldn't happen
            ComponentSystemPlugin.log(x);
        }

        defineFunctionProperties(new String[] { 
                "toString",             // for printing foo 
                "isOfType",				// is inherited from component type
               }, WrappedComponent.class, ScriptableObject.READONLY); //$NON-NLS-1$
    }
    
    /**
     * Create a Rhino wrapper for the component
     * 
     * Do not call this from client code!
     * 
     * @see ComponentWrappers#getWrappedComponent(IComponent)
     */
    WrappedComponent(IComponent component) {
        this();
        this.component = component;
        this.attributes = (IAttributes) component.getAdapter(IAttributes.class);
    }

    public String getClassName() {
        return "Component"; //$NON-NLS-1$
    }
    
	public boolean isOfType(String componentID) {
		return ModelUtils.isOfType(component, componentID);
	}

    public String jsGet_id() {
        return component.getId();
    }
    
    public String jsGet_friendlyName() {
        return component.getFriendlyName();
    }

    public Version jsGet_minSDKVersion() {
		return ((Component) component).getMinSDKVersion();
    }
    
    public Version jsGet_maxSDKVersion() {
		return ((Component) component).getMaxSDKVersion();
	}

    public WrappedAttributes jsGet_attributes() {
        if (attributes != null)
            return ComponentWrappers.getInstance().getWrappedAttributes(attributes);
        else
            return null;
    }

    public String toString() {
        return "<component " + component.getId() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    public Object unwrap() {
        return component;
    }

    //////////////////////
    
    public IComponentProvider getProvider() {
        return component.getProvider();
    }

    public Bundle getBundle() {
        return component.getBundle();
    }

    public String getId() {
        return component.getId();
    }

    public Version getComponentVersion() {
		return component.getComponentVersion();
	}

	public String getCategory() {
        return component.getCategory();
    }

    public String getInstanceNameRoot() {
        return component.getInstanceNameRoot();
    }

    public String getFriendlyName() {
        return component.getFriendlyName();
    }

    public String getBaseComponentId() {
		return component.getBaseComponentId();
	}

	public IComponent getComponentBase() {
        return component.getComponentBase();
    }

    public boolean isAbstract() {
        return component.isAbstract();
    }

    public IComponentSet getComponentSet() {
        return component.getComponentSet();
    }

    public Object getAdapter(Class adapter) {
        return component.getAdapter(adapter);
    }
    
    public MessageLocation createMessageLocation() {
        return component.createMessageLocation();
    }
    
    public File getBaseDirectory() {
    	return component.getBaseDirectory();
    }
}