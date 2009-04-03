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
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IEventBinding;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.GlobalCache;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.IDisposable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;
import org.mozilla.javascript.Wrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Manage wrapping components, instances, attributes for script.
 * <p>
 * The wrappers are cached to avoid performance penalties in
 * recreating them and to ensure identity when comparing them
 * with each other.
 * <p>
 * The component and attribute wrappers are cached globally and instances
 * and properties cached per data model. 
 * 
 * 
 */
public abstract class ComponentWrappers {
    
	final static String GLOBAL_WRAPPERS_ID = "ComponentWrappers.GLOBAL_ID";
    final static String DM_WRAPPERS_ID = "ComponentWrappers.DM_ID";
    
    /**
     * Get the wrappers for global data like components, attributes
     * @return wrappers
     */
    public static IGlobalWrappers getInstance() {
    	GlobalWrappers globalWrappers = (GlobalWrappers) GlobalCache.getCache().get(GLOBAL_WRAPPERS_ID);
        if (globalWrappers == null) {
        	globalWrappers = new GlobalWrappers();
        	GlobalCache.getCache().put(GLOBAL_WRAPPERS_ID, globalWrappers);
        }
        return globalWrappers;
    }
    
    /**
     * Get wrappers for model-specific data like instances and property sources
     * @param model
     * @return wrappers
     */
    public static IModelWrappers getInstance(IDesignerDataModel model) {
    	ModelWrappers dmWrappers = (ModelWrappers) model.getCache().get(DM_WRAPPERS_ID);
    	if (dmWrappers == null) {
    		dmWrappers = new ModelWrappers();
    		model.getCache().put(DM_WRAPPERS_ID, dmWrappers);
    	}
    	return dmWrappers;
    }
    
    /**
     * Get wrappers for model-specific data like instances and property sources
     * @param object a model object
     * @return wrappers
     */
    public static IModelWrappers getInstance(EObject object) {
    	IComponentInstance instance = ModelUtils.getComponentInstance(object);
    	Check.checkArg(instance != null);
    	return getInstance(instance.getDesignerDataModel());
    }

    /**
     * Get wrappers for model-specific data like instances and property sources
     * @param instance
     * @return wrappers
     */
    public static IModelWrappers getInstance(IComponentInstance instance) {
    	Check.checkArg(instance != null);
    	return getInstance(instance.getDesignerDataModel());
    }

    static class GlobalWrappers implements IGlobalWrappers, IDisposable {
        /** Map of IAttributes to WrappedAttributes (global) */
        private Map attrsMap;
        /** Map of IComponent to WrappedComponent (global) */
        private Map componentMap;

        public GlobalWrappers() {
        	attrsMap = new HashMap();
        	componentMap = new HashMap();
		}
        
        public void dispose() {
            attrsMap.clear();
            componentMap.clear();
        }

        /** Maintain 1:1 mapping of IAttributes to WrappedAttributes */
        public WrappedAttributes getWrappedAttributes(IAttributes attributes) {
        	Check.checkContract(attrsMap != null);
            WrappedAttributes attrs = (WrappedAttributes) attrsMap.get(attributes);
            if (attrs == null) {
                attrs = new WrappedAttributes(attributes);
                attrsMap.put(attributes, attrs);
                
                // initialize Javascript properties which entail recursing
                // over attributes, AFTER we've registered this
                // wrapped object
                attrs.init();
            }
            return attrs;
        }

        /** Maintain 1:1 mapping of IComponent to WrappedComponent */
        public WrappedComponent getWrappedComponent(IComponent component) {
        	Check.checkContract(componentMap != null);
            WrappedComponent cmp = (WrappedComponent) componentMap.get(component);
            if (cmp == null) {
                cmp = new WrappedComponent(component);
                componentMap.put(component, cmp);
                
                // initialize Javascript properties which entail recursing
                // over attributes, AFTER we've registered this
                // wrapped object
                cmp.init();
            }
            return cmp;
        }

    }

    static class ModelWrappers implements IModelWrappers, IDisposable {
        /** Map of EObject[IComponentInstance] to WrappedInstance (datamodel) */
        private Map<IComponentInstance, WrappedInstance> instanceMap;
        /** Map of String to WrappedInstance (datamodel) */
        private Map<String, IComponentInstance> nameToInstanceMap;
        private Map<IComponentInstance, String> instanceToNameMap;
        /** Map of IEventBinding to WrappedEventBinding (datamodel) */
        private Map<IEventBinding, WrappedEventBinding> eventBindingsMap;

        public ModelWrappers() {
        	instanceMap = new HashMap();
        	instanceToNameMap = new HashMap<IComponentInstance, String>();
        	nameToInstanceMap = new HashMap<String, IComponentInstance>();
    		eventBindingsMap = new HashMap();
		}
        public void dispose() {
        	instanceMap.clear();
        	instanceToNameMap.clear();
        	nameToInstanceMap.clear();
        	eventBindingsMap.clear();
        }

        /** Maintain 1:1 mapping of IComponentInstance to WrappedInstance */
        public WrappedInstance getWrappedInstance(IComponentInstance instance, IPropertySource properties) {
        	Check.checkArg(instance);
        	Check.checkContract(instanceMap != null);
            WrappedInstance winstance = (WrappedInstance)instanceMap.get(instance);
            if (winstance == null) {
            	// first, remove any existing references to the same-named instance
            	// that are obsoleted by the new instance 
            	String name = instance.getName();
            	IComponentInstance oldInstance = nameToInstanceMap.get(name);
            	if (oldInstance != null) {
            		instanceMap.remove(oldInstance);
            		nameToInstanceMap.remove(name);
            		instanceToNameMap.remove(oldInstance);
            	}
            	
                winstance = new WrappedInstance(instance, properties);
                instanceMap.put(instance, winstance);
                instanceToNameMap.put(instance, name);
                nameToInstanceMap.put(name, instance);
                
                // initialize Javascript properties which entail recursing
                // over children and parents, AFTER we've registered this
                // wrapped object
                winstance.init();
            }
            return winstance;
        }

        /** Maintain 1:1 mapping of IComponentInstance to WrappedInstance */
        public WrappedInstance getWrappedInstance(EObject object) {
            IComponentInstance instance = (IComponentInstance) EcoreUtil.getRegisteredAdapter(object, IComponentInstance.class);
            return getWrappedInstance(instance, null);
        }

        /**
         * @return array of wrapped instances for objs
         */
        public WrappedInstance[] getWrappedInstanceArray(EObject objs[]) {
            if (objs == null)
                return null;
            
            WrappedInstance[] insts = new WrappedInstance[objs.length];
            for (int i = 0; i < objs.length; i++) {
                insts[i] = getWrappedInstance(objs[i]);
            }
            
            return insts;
        }
        
        /** Maintain 1:1 mapping of IPropertySource to WrappedProperties, for complex property sources */
        public WrappedProperties getWrappedProperties(IPropertySource propertySource) {
        	return new WrappedProperties(this, propertySource);
        }

        /** 
         * Wrap a property value for Javascript.
         * This means primarily to ensure EMF/adaptable objects are wrapped
         * if needed. 
         * @param obj incoming Java object
         * @return object or wrapped object
         */
        public Object wrapProperty(Object obj) {
            // watch for wrapped component instance references 
            if (obj instanceof EObject) {
                WrappedInstance instance = getWrappedInstance((EObject)obj);
                if (instance != null)
                    return instance;
            }
            // and complex properties
            if (obj instanceof IPropertySource) {
                WrappedProperties prop = getWrappedProperties((IPropertySource)obj);
                if (prop != null)
                    return prop;
            }
            
            // ordinary object
            return obj;
        }

        /** 
         * Unwrap a property value from Javascript.
         * @param obj incoming Java object
         * @return object or wrapped object
         */
        public Object unwrapProperty(Object obj) {
            if (obj instanceof WrappedInstance) {
                return ((WrappedInstance) obj).instance;
            }
            if (obj instanceof WrappedProperties) {
                return ((WrappedProperties) obj).propertySource;
            }
            if (obj instanceof WrappedComponent) {
                return ((WrappedComponent) obj).component;
            }
            if (obj instanceof WrappedAttributes) {
                return ((WrappedAttributes) obj).attrs;
            }
            if (obj instanceof Wrapper) {
                return ((Wrapper) obj).unwrap();
            }
            // ordinary object
            return obj;
        }

        /** Maintain 1:1 mapping of IEventBinding to WrappedEventBinding */
        public WrappedEventBinding getWrappedEventBinding(IEventBinding binding) {
        	Check.checkContract(eventBindingsMap != null);
            WrappedEventBinding eb = (WrappedEventBinding) eventBindingsMap.get(binding);
            if (eb == null) {
                eb = new WrappedEventBinding(binding);
                eventBindingsMap.put(binding, eb);
                
                // initialize Javascript properties which entail recursing
                // over attributes, AFTER we've registered this
                // wrapped object
                eb.init();
            }
            return eb;
        }

    }


}
