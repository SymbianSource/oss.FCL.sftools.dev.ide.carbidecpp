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

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.symbian.IFacetContainer;
import com.nokia.sdt.component.symbian.implementations.IScriptImplAdapter;
import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IModelAdapter;
import com.nokia.sdt.datamodel.util.MessageLocators;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.scripting.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;

import java.io.File;

/**
 * Helper class for script adapters
 * 
 * 
 *
 */
public abstract class ScriptAdapterImplBase extends AdapterImpl implements IScriptImplAdapter, IModelAdapter {
    private Class implClass;
    private IScriptContext scriptContext;
    private IScriptObject scriptObject;
    private IScriptScope scriptGlobals;
    private IScriptScope transientGlobals;
    private Object impl;
    private IComponent component;
    private Class scriptImplClass;
    private File componentBaseDir;

    /**
     * Set up the script adapter for the case where the Java and Javascript interfaces are the same
     * @param implClass the Java interface and Javascript interface implemented
     */
    public ScriptAdapterImplBase(Class implClass) {
        super();
        Check.checkArg(implClass);
        this.scriptImplClass = implClass;
        this.implClass = implClass;
    }
    
    /**
     * Set up the script adapter for the case where the Java
     * interface differs from the Javascript interface
     * @param implClass the Java interface and Javascript interface implemented
     * @param scriptImplClass the Java interface and Javascript interface implemented
     */
    public ScriptAdapterImplBase(Class implClass, Class scriptImplClass) {
        super();
        Check.checkArg(implClass);
        Check.checkArg(scriptImplClass);
        this.scriptImplClass = scriptImplClass;
        this.implClass = implClass;
    }

    
    public IScriptObject getScriptObject() {
        return scriptObject;
    }

    public EObject getEObject() {
        return (EObject) getTarget();
    }
    
    /**
     * @return Returns the impl.
     */
    public Object getImpl() {
        return impl;
    }

    public IComponent getComponent() {
        return component;
    }
    
    public void init(IScriptContext context, IScriptObject scriptObject, EObject instance, IComponent component) throws ScriptException {
        Check.checkArg(context);
        Check.checkArg(scriptObject);
        
        this.scriptContext = context;
        this.scriptObject = scriptObject;
        this.component = component;
        this.componentBaseDir = ((IFacetContainer) component).getBaseDirectory();
        
        this.scriptGlobals = scriptContext.getScope();
        this.transientGlobals = scriptContext.getTransientGlobalScope();
        if (transientGlobals == null)
            transientGlobals = scriptGlobals;
        Check.checkArg(transientGlobals);
        this.impl = scriptObject.wrapObjectInInterface(scriptImplClass);
        
        setTarget(instance);
        
        // register global variables for the script (adapter-specific)
        registerScriptGlobals(new INameRegistrar() {

            public void register(String name, Object value) throws ScriptException {
                scriptGlobals.defineObject(name, value);
            }});
    }
    
    public boolean isAdapterForType(Object type) {
        return type.equals(implClass);
    }

    /**
     * Register any adapter-specific globals for the script.<p>
     * This routine is called once per script.
     * @param registrar agent via which globals are registered
     */
    abstract protected void registerScriptGlobals(INameRegistrar registrar) throws ScriptException;
    
    protected WrappedInstance getWrappedInstance() {
        return getEObject() != null ? ComponentWrappers.getInstance(getEObject()).getWrappedInstance(getEObject()) : null;        
    }

    protected WrappedInstance getWrappedInstance(EObject other) {
        return ComponentWrappers.getInstance(other).getWrappedInstance(other);        
    }

    protected WrappedInstance getWrappedInstance(IDesignerDataModel model, EObject other) {
    	return ComponentWrappers.getInstance(model).getWrappedInstance(other);        
    }
    
    protected WrappedComponent getWrappedComponent() {
        return ComponentWrappers.getInstance().getWrappedComponent(component);
    }

    protected WrappedComponent getWrappedComponent(IComponent other) {
        return ComponentWrappers.getInstance().getWrappedComponent(other);
    }

    protected WrappedProperties getWrappedProperties() {
        return ComponentWrappers.getInstance(getEObject()).getWrappedProperties(ModelUtils.getPropertySource(getEObject()));
    }

    protected WrappedProperties getWrappedProperties(IPropertySource source) {
        return ComponentWrappers.getInstance(getEObject()).getWrappedProperties(source);
    }


    protected WrappedAttributes getWrappedAttributes() {
        IAttributes attrs = (IAttributes) component.getAdapter(IAttributes.class);
        return ComponentWrappers.getInstance().getWrappedAttributes(attrs);
    }

    protected WrappedAttributes getWrappedAttributes(IComponent other) {
        IAttributes attrs = (IAttributes) other.getAdapter(IAttributes.class);
        return ComponentWrappers.getInstance().getWrappedAttributes(attrs);
    }

    /**
     * Invoke script code safely.
     * 
     * @param wrapper
     * @return return value from script
     * @throws ScriptException if script code failed to run (N.B.: exception will already be logged)
     */
    protected Object invokeScriptCode(EObject object, final IScriptCodeWrapper wrapper) throws ScriptException {
        // already failed to generate?
        if (scriptContext == null)
            return null;
        
        /* Get the instance and property source */
        IComponentInstance componentInstance = 
            (IComponentInstance) EcoreUtil.getRegisteredAdapter(object, IComponentInstance.class);
        Check.checkContract(componentInstance != null);
        IPropertySource properties = 
            (IPropertySource) EcoreUtil.getRegisteredAdapter(object, IPropertySource.class);
        Check.checkContract(properties != null);

        Object ret = null;
        
        try {
            // register new transient globals for the wrapper
            wrapper.registerTransientGlobals(new INameRegistrar() {
    
                public void register(String name, Object value) throws ScriptException {
                    transientGlobals.defineObject(name, value);
                    /*
                    if (name.equals("instanceName") || name.indexOf("resourceName") >= 0)
                        System.out.println("registering transient " + name +" = " +value);
                    */
                }});
            
            // migrate transients to all bases
            IScriptContext base = scriptContext.getParentScope().getScriptContext();
            while (base != null) {
                if (base.getTransientGlobalScope() != null)
                    base.getTransientGlobalScope().include(transientGlobals);
                base = base.getParentScope().getScriptContext();
            }
            
            ret = ComponentGlobals.wrapScriptHandlingCode(
                    scriptContext, 
                    componentBaseDir, 
                    component,
                    componentInstance.getDesignerDataModel(),
                    new IScriptContextWrapper() {
    
                public Object run(IScriptContext context) throws Exception {
                    /*
                    System.out.println("Before running script:");
                    IScriptScope tmp = scriptContext.getScope();
                    while (tmp != null) {
                        tmp.dump("Scope", System.out);
                        tmp = tmp.getParentScope();
                    }
                    */
                    
                    return wrapper.run();
                }}
            );

        } catch (Throwable thr) {
            Messages.failure(thr, 
                    MessageLocators.getComponentOrInstanceLocation(componentInstance),
                    "ScriptContext.ErrorEncounteredRunningScriptImpl", //$NON-NLS-1$ 
                    new Object[] { implClass.getName(), component.getId(), thr.getLocalizedMessage() }); //$NON-NLS-1$
            return null;
        } finally {
            // clear out transients to all bases
            
            // Turned off, because it breaks things occasionally.
            // viz: SymbianNameGenerator variables sometimes disappear
            // when they are still needed.
            /*IScriptContext base = scriptContext;
            while (base != null) {
                if (base.getTransientGlobalScope() != null)
                    base.getTransientGlobalScope().deleteAllObjects();
                base = base.getParentScope().getScriptContext();
            }*/
        }
        return ret;
    }
}
