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

import com.nokia.sdt.component.property.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.*;
import org.mozilla.javascript.Scriptable;

/**
 * Wrap a property source for Javascript
 *  
 * 
 * @see org.eclipse.ui.views.properties.IPropertySource
 */
public class WrappedProperties implements Scriptable, IPropertySource2, IPropertyInformation /*, Wrapper*/ {

    IPropertySource propertySource;
    IPropertyInformation propertyInformation;
    private Scriptable parent;
    private Scriptable prototype;
	private IModelWrappers wrappers;
	private IPropertySource2 propertySource2;

    //static void init(Context cx, Scriptable scope, boolean sealed) {
    //    
   // }
    
    public void jsConstructor() {
        
    }

    public WrappedProperties() {
        
    }
    
    WrappedProperties(IModelWrappers wrappers, IPropertySource propertySource) {
        Check.checkArg(propertySource);
        this.wrappers = wrappers;
        this.propertySource = propertySource;
        if (propertySource instanceof IPropertySource2)
        	this.propertySource2 = (IPropertySource2) propertySource;
        if (propertySource instanceof IPropertyInformation)
        	this.propertyInformation = (IPropertyInformation) propertySource;
    }

    /*
     *  (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#getClassName()
     */
    public String getClassName() {
        return "Properties"; //$NON-NLS-1$
    }

    /* Get the named property
     * 
     *  (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#get(java.lang.String, org.mozilla.javascript.Scriptable)
     */
    public Object get(String name, Scriptable start) {
        if (propertySource instanceof ISequencePropertySource && name.equals("length")) { //$NON-NLS-1$
            return new Integer(((ISequencePropertySource) propertySource).size());
        }
        if (name.equals("editableValue")) { //$NON-NLS-1$
            return wrappers.wrapProperty(propertySource.getEditableValue());
        }

        Object obj = propertySource.getPropertyValue(name);
        if (obj == null)
            return null;
        return wrappers.wrapProperty(obj);
    }

    /*
     *  (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#get(int, org.mozilla.javascript.Scriptable)
     */
    public Object get(int index, Scriptable start) {
        if (propertySource instanceof ISequencePropertySource) {
            return get(Integer.toString(index), start);
        }
        return null;
    }

    /**
     * Defines the properties by returning true if name is
     * found in the property source.
     *
     * @param name the name of the property
     * @param start the object where lookup began
     */
    public boolean has(String name, Scriptable start) {
        IPropertyDescriptor descs[] = propertySource.getPropertyDescriptors();
        for (int i = 0; i < descs.length; i++) {
            if (name.equals((String)descs[i].getId()))
                return true;
        }
        if (propertySource instanceof ISequencePropertySource && name.equals("length")) //$NON-NLS-1$
            return true;
        if (name.equals("editableValue"))
            return true;
        return false;
    }

    /*
     * No numeric properties provided
     *  (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#has(int, org.mozilla.javascript.Scriptable)
     */
    public boolean has(int index, Scriptable start) {
        if (propertySource instanceof ISequencePropertySource) {
            return has(Integer.toString(index), start);
        } 
        return false;
    }

    /*
     *  (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#put(java.lang.String, org.mozilla.javascript.Scriptable, java.lang.Object)
     */
    public void put(String name, Scriptable start, Object value) {
        if (propertySource instanceof ISequencePropertySource && name.equals("length")) { //$NON-NLS-1$
        	value = ScriptingUtils.unwrap(value);
        	Check.checkArg(value instanceof Number);
        	int newSize = ((Number) value).intValue();
        	ISequencePropertySource sps = (ISequencePropertySource) propertySource;
            if (sps.size() < newSize) {
            	growArray(sps, newSize);
            } else if (sps.size() > newSize) {
            	shrinkArray(sps, newSize);
            }
            return;
        }
        propertySource.setPropertyValue(name, wrappers.unwrapProperty(value));
    }

	/*
     *  (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#put(int, org.mozilla.javascript.Scriptable, java.lang.Object)
     */
    public void put(int index, Scriptable start, Object value) {
        IPropertyDescriptor[] descriptors = propertySource.getPropertyDescriptors(); 
        if (index >= descriptors.length) {
            if (!(propertySource instanceof ISequencePropertySource))
                return;
            
            // extend the array as per Javascript
            growArray((ISequencePropertySource) propertySource, index + 1);
            
            descriptors = propertySource.getPropertyDescriptors(); 
        }
        put(descriptors[index].getId().toString(), start, value);
    }

    private void growArray(ISequencePropertySource sps, int size) {
        while (size > sps.size()) {
        	if (sps.isCompoundElement()) {
        		sps.addCompoundProperty(ISequencePropertySource.AT_END);
        	} else {
        		sps.addSimpleProperty(ISequencePropertySource.AT_END, null);
        	}
        }
	}

    private void shrinkArray(ISequencePropertySource sps, int newSize) {
    	while (sps.size() > newSize) {
    		sps.remove(sps.size() - 1);
    	}
	}


	/*
     *  (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#delete(java.lang.String)
     */
    public void delete(String name) {
        // reset value to default
        if (propertySource instanceof IPropertySource2
                && ((IPropertySource2) propertySource).isPropertyResettable(name))
        {
            propertySource.resetPropertyValue(name);
        }
    }

    /*
     *  (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#delete(int)
     */
    public void delete(int index) {
        delete(Integer.toString(index));
    }

    /*
     *  (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#getPrototype()
     */
    public Scriptable getPrototype() {
        return prototype;
    }

    /*
     *  (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#setPrototype(org.mozilla.javascript.Scriptable)
     */
    public void setPrototype(Scriptable prototype) {
        this.prototype = prototype;
        
    }

    /*
     *  (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#getParentScope()
     */
    public Scriptable getParentScope() {
        return parent;
    }

    /*
     *  (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#setParentScope(org.mozilla.javascript.Scriptable)
     */
    public void setParentScope(Scriptable parent) {
        this.parent = parent;
    }

    /*
     *  (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#getIds()
     */
    public Object[] getIds() {
        IPropertyDescriptor descs[] = propertySource.getPropertyDescriptors();
        String[] names = new String[descs.length];
        for (int i = 0; i < descs.length; i++) {
            names[i] = (String)descs[i].getId();
        }
        return names;
    }

    /*
     *  (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#getDefaultValue(java.lang.Class)
     */
    public Object getDefaultValue(Class hint) {
        return null;
    }

    /*
     * instanceof operator.
     *
     * We mimick the normal JavaScript instanceof semantics, returning
     * true if <code>this</code> appears in <code>value</code>'s prototype
     * chain.
     *
     *  (non-Javadoc)
     * @see org.mozilla.javascript.Scriptable#hasInstance(org.mozilla.javascript.Scriptable)
     */
    public boolean hasInstance(Scriptable value) {
        Scriptable proto = value.getPrototype();
        while (proto != null) {
            if (proto.equals(this))
                return true;
            proto = proto.getPrototype();
        }

        return false;
    }

    public Object unwrap() {
        return propertySource;
    }

    public boolean isPropertySet(Object id) {
        return propertySource.isPropertySet(id);
    }

    public Object getEditableValue() {
        return propertySource.getEditableValue();
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        return propertySource.getPropertyDescriptors();
    }

    public Object getPropertyValue(Object id) {
        return propertySource.getPropertyValue(id);
    }

    public void resetPropertyValue(Object id) {
        propertySource.resetPropertyValue(id);
    }

    public void setPropertyValue(Object id, Object value) {
        propertySource.setPropertyValue(id, value);
    }

	public boolean isPropertyResettable(Object id) {
		if (propertySource2 != null)
			return propertySource2.isPropertyResettable(id);
		return true;
	}

	public ICompoundPropertyValueConverter getCompoundPropertyValueConverter() {
		if (propertyInformation != null)
			return propertyInformation.getCompoundPropertyValueConverter();
		return null;
	}

	public EObject getPropertyOwner(Object propertyId) {
		if (propertyInformation != null)
			return propertyInformation.getPropertyOwner(propertyId);
		return null;
	}

	public String getPropertyPath() {
		if (propertyInformation != null)
			return propertyInformation.getPropertyPath();
		return null;
	}

	public String getPropertyPath(Object propertyId) {
		if (propertyInformation != null)
			return propertyInformation.getPropertyPath(propertyId);
		return null;
	}

	public String getPropertyTypeName(Object propertyId) {
		if (propertyInformation != null)
			return propertyInformation.getPropertyTypeName(propertyId);
		return null;
	}

	public String getPropertyValueSymbol(Object propertyId) {
		if (propertyInformation != null)
			return propertyInformation.getPropertyValueSymbol(propertyId);
		return null;
	}

	public void refresh() {
		if (propertyInformation != null)
			propertyInformation.refresh();
	}
    
}
