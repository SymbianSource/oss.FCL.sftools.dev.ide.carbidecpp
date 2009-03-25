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

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * Wrap the IAttributes interface for use by Javascript
 * 
 * 
 *
 */
public class WrappedAttributes extends ScriptableObject implements IAttributes /*implements Wrapper*/ {

    private static final long serialVersionUID = 1L;
    
    IAttributes attrs;

    /** 
     * This constructor is needed to prevent Rhino from barfing on our Java-only constructors --
     * don't call this directly!
     */
    public WrappedAttributes() {
    }
    
    // needed to prevent Rhino from barfing on our Java-only constructors
    public void jsConstructor() {
    }

    WrappedAttributes(IAttributes attributes) {
        this();
        
        Check.checkArg(attributes);
        this.attrs = attributes;
    }
    
    public void init() {
        defineFunctionProperties(new String[] { 
                "toString",             // for printing foo 
                }, WrappedAttributes.class, ScriptableObject.READONLY); //$NON-NLS-1$
        
    }

    public String toString() {
        return "<WrappedAttributes " + attrs + ">"; //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    public String getClassName() {
        return "Attributes"; //$NON-NLS-1$
    }

    public Object unwrap() {
        return attrs;
    }

    public Object get(String name, Scriptable start) {
        return attrs.getAttribute(name);
    }

    public Object get(int index, Scriptable start) {
        return null;
    }

    public boolean has(String name, Scriptable start) {
        return attrs.getAttribute(name) != null;
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

    public String getAttribute(String key) {
        return attrs.getAttribute(key);
    }

    public boolean isAttributeDefined(String key) {
        return attrs.isAttributeDefined(key);
    }

    public int getIntegerAttribute(String key, int defaultValue) {
        return attrs.getIntegerAttribute(key, defaultValue);
    }

    public boolean getBooleanAttribute(String key, boolean defaultValue) {
        return attrs.getBooleanAttribute(key, defaultValue);
    }

    public IComponent getComponent() {
        return attrs.getComponent();
    }

}
