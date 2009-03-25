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
package com.nokia.sdt.component.symbian.querycontainment;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptAdapterImplBase;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IQueryContainment;
import com.nokia.sdt.scripting.ScriptException;
import com.nokia.sdt.utils.StatusHolder;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;

public class QueryContainmentAdapterScript extends ScriptAdapterImplBase implements IQueryContainment {
	
	public QueryContainmentAdapterScript() {
        super(IQueryContainment.class, IScriptQueryContainment.class);
	}
	
    protected void registerScriptGlobals(INameRegistrar registrar) throws ScriptException {
        // none
    }

	public boolean canContainComponent(final IComponent component, StatusHolder statusHolder) {
		IStatus status = null;
		
        try {
            status = (IStatus) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

					public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
						// nothing new
					}

					public Object run() {
						/* query it! */
                        WrappedInstance instance = getWrappedInstance();
						WrappedComponent wrappedComponent = getWrappedComponent(component);
						return ((IScriptQueryContainment) getImpl()).canContainComponent(
												instance, wrappedComponent);
					}
				}
            );
        } 
        catch (ScriptException e) {
            // already logged
        }
        
        if ((status != null) && !status.isOK()) {
        	if (statusHolder != null)
        		statusHolder.setStatus(status);
        	return false;
        }
        
        return true;
	}
	
	public boolean canContainChild(final IComponentInstance child, StatusHolder statusHolder) {
		IStatus status = null;
		
        try {
            status = (IStatus) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

					public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
						// nothing new
					}

					public Object run() {
						/* query it! */
                        WrappedInstance instance = getWrappedInstance();
                        WrappedInstance childInstance = getWrappedInstance(child.getEObject());
						return ((IScriptQueryContainment) getImpl()).canContainChild(instance, childInstance);
					}
				}
            );
        } 
        catch (ScriptException e) {
            // already logged
        }
        
        if ((status != null) && !status.isOK()) {
        	if (statusHolder != null)
        		statusHolder.setStatus(status);
        	return false;
        }
        
        return true;
	}
	
	public boolean canRemoveChild(final IComponentInstance child) {
		boolean result = false;
		
        try {
            Boolean b = (Boolean) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

					public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
						// nothing new
					}

					public Object run() {
						/* query it! */
                        WrappedInstance instance = getWrappedInstance();
                        WrappedInstance childInstance = getWrappedInstance(child.getEObject());
						boolean result =
                            ((IScriptQueryContainment) getImpl()).canRemoveChild(
                                    instance, childInstance);
						return Boolean.valueOf(result);
					}
				}
            );
            result = b.booleanValue();
        } 
        catch (ScriptException e) {
            // already logged
        }
        
        return result;
	}

	public boolean isValidComponentInPalette(final IComponent component) {
		boolean result = false;
		
        try {
            Boolean b = (Boolean) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

					public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
						// nothing new
					}

					public Object run() {
						/* query it! */
                        WrappedInstance instance = getWrappedInstance();
                        WrappedComponent wrappedComponent = getWrappedComponent(component);
						boolean result =
                            ((IScriptQueryContainment) getImpl()).isValidComponentInPalette(instance, wrappedComponent);
						return Boolean.valueOf(result);
					}
				}
            );
            result = b.booleanValue();
        } 
        catch (ScriptException e) {
            // already logged
        }
        
        return result;
	}

    public EObject getEObject() {
		return (EObject) getTarget();
	}

}
