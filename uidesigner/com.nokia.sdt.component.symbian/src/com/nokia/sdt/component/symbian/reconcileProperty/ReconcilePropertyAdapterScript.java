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


package com.nokia.sdt.component.symbian.reconcileProperty;

import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptAdapterImplBase;
import com.nokia.sdt.datamodel.adapter.IReconcileProperty;
import com.nokia.sdt.scripting.ScriptException;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.ui.views.properties.IPropertySource;

public class ReconcilePropertyAdapterScript extends ScriptAdapterImplBase implements IReconcileProperty {
	
	public ReconcilePropertyAdapterScript() {
        super(IReconcileProperty.class, IScriptReconcileProperty.class);

	}
	
    protected void registerScriptGlobals(INameRegistrar registrar) throws ScriptException {
        // none
    }

	public Object createDisplayValue(final String propertyTypeName, final Object propertyValue) {
        Object returnValue = null;
        try {
            returnValue = invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                    // nothing new
                }

                public Object run() {
                    /* get the display value for the property source! */
                    Check.checkContract(propertyValue instanceof IPropertySource);
                    WrappedInstance instance = getWrappedInstance();
                    WrappedProperties wrappedProperties = getWrappedProperties((IPropertySource) propertyValue);
                    return ((IScriptReconcileProperty) getImpl()).createDisplayValue(instance, propertyTypeName, wrappedProperties);
                }}
            );
        } catch (ScriptException e) {
            
        }
        
        return returnValue;
	}

    public boolean isDisplayValueEditable(final String propertyTypeName) {
	    boolean returnValue = false;
        try {
            Object retObj = invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                    // nothing new
                }

                public Object run() {
                    /* tell whether display value is editable! */
                    WrappedInstance instance = getWrappedInstance();
                    return Boolean.valueOf(((IScriptReconcileProperty) getImpl()).isDisplayValueEditable(instance, propertyTypeName));
                }}
            );
            returnValue = ((Boolean) retObj).booleanValue();
        } catch (ScriptException e) {
        }
        
        return returnValue;
	}

	public void applyDisplayValue(final String propertyTypeName, final Object displayValue, final Object propertyValue) {
        try {
            invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                    // nothing new
                }

                public Object run() {
                    /* reconcile! */
                    Check.checkContract(propertyValue instanceof IPropertySource);
                    WrappedInstance instance = getWrappedInstance();
                    WrappedProperties wrappedProperties = getWrappedProperties((IPropertySource) propertyValue);
                    ((IScriptReconcileProperty) getImpl()).applyDisplayValue(instance, propertyTypeName, displayValue, wrappedProperties);
                    return null;
                }}
            );
        } catch (ScriptException e) {
        }
	}

}
