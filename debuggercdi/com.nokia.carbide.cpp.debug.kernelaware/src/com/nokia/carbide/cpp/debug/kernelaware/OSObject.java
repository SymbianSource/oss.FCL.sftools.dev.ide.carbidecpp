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
package com.nokia.carbide.cpp.debug.kernelaware;

import java.util.Hashtable;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

abstract public class OSObject implements IPropertySource {

	protected Hashtable<String, Object> m_properties = new Hashtable<String, Object>(
			23);

	// This is a subset of all properties of the object.
	protected IPropertyDescriptor[] m_supportedPropertyDescriptors;

	public OSObject() {
	}

	public String toString() {
		return (String) getFormatedPropertyValue(IOSObjectProperties.ID.Name);
	}

	static public boolean isPropertySortable(String id) {
		IPropertyDescriptor[] pd = getSortablePropertyDescriptorList();
		for (int i = 0; i < pd.length; i++)
			if (id.equals(pd[i].getId()))
				return true;

		return false;
	}

	// Must be overriden by subclass. But as I want it to be static, it cannot
	// be abstract.
	static public IPropertyDescriptor[] getPropertyDescriptorList() {
		return null;
	};

	static public IPropertyDescriptor[] getSortablePropertyDescriptorList() {
		return null;
	}

	public abstract String getTypeName();

	public Object getEditableValue() {
		return this;
	}

	// Only return properties available in this object so that no meaningless
	// properties will be displayed in Properties View.
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return m_supportedPropertyDescriptors;
	}

	// This inherited interface actually asks for value for display.
	public Object getPropertyValue(Object id) {
		return getFormatedPropertyValue(id);
	}

	public Object getRawPropertyValue(Object id) {
		Object value = m_properties.get(id);
		return value;
	}

	public String getFormatedPropertyValue(Object id) {
		Object value = getRawPropertyValue(id);

		if (value != null) {
			String ret = value.toString();

			// Display "unsigned int" in 8-digit hex format (mostly address
			// values).
			if (value instanceof Long) {
				Long longVal = (Long) value;
				// # - prepend "0x"
				// 0 - padding with zero
				// 10 - number of total chars including "0x"
				// x - hex format
				if (((String) id).toLowerCase().contains("size"))	
					// For "size" related properties, display in compact instead of
					// padded hex.
					ret = String.format("%#1x", longVal);
				else
					ret = String.format("%#010x", longVal);
			}

			return ret;
		} else {
			return null;
		}
	}

	public boolean isPropertySet(Object id) {
		return false;
	}

	public void resetPropertyValue(Object id) {
	}

	public void setPropertyValue(Object id, Object value) {
		m_properties.put((String) id, value);
	}
	
	public int getID() {
		Integer id = (Integer)getRawPropertyValue(IOSObjectProperties.ID.ID);
		if (id != null)
			return id.intValue();
		else
			return -1;
	}

	public String getName() {
		return (String)getRawPropertyValue(IOSObjectProperties.ID.Name);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof OSObject) {
			OSObject that = (OSObject)arg0;
			
			if (getID() == that.getID() && getName().equals(that.getName()))
				return true;
		}
		
		return false;
	}

	/* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return getName().hashCode() ^ (getID() << 4) ^ 391;
    }
}
