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
package com.nokia.sdt.datamodel.util;

import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.ui.views.properties.*;

import java.util.HashMap;

/**
 * IPropertySource wrapper that can filter the exposed
 * properties.
 */
public class FilteredPropertySource implements IPropertySource2 {

	private IPropertySource ps;
	private HashMap<Object, IPropertyDescriptor> descriptors = new HashMap();
	
	interface IFilter {
		boolean accept(IPropertyDescriptor pd);
	}
	
	public FilteredPropertySource(IPropertySource ps) {
		Check.checkArg(ps);
		this.ps = ps;
	}
	
	public void setFilter(IFilter filter) {
		descriptors.clear();
		IPropertyDescriptor[] propertyDescriptors = ps.getPropertyDescriptors();
		for (IPropertyDescriptor pd : propertyDescriptors) {
			if (filter.accept(pd)) {
				descriptors.put(pd.getId(), pd);
			}
		}
	}

	public Object getEditableValue() {
		return ps.getEditableValue();
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return descriptors.values().toArray(new IPropertyDescriptor[descriptors.size()]);
	}

	public Object getPropertyValue(Object id) {
		Object result = null;
		IPropertyDescriptor pd = descriptors.get(id);
		if (pd != null) {
			result = ps.getPropertyValue(id);
		}
		return result;
	}

	public boolean isPropertySet(Object id) {
		boolean result = false;
		IPropertyDescriptor pd = descriptors.get(id);
		if (pd != null) {
			result = ps.isPropertySet(id);
		}
		return result;
	}

	public void resetPropertyValue(Object id) {
		IPropertyDescriptor pd = descriptors.get(id);
		if (pd != null) {
			ps.resetPropertyValue(id);
		}
	}

	public void setPropertyValue(Object id, Object value) {
		IPropertyDescriptor pd = descriptors.get(id);
		if (pd != null) {
			ps.setPropertyValue(id, value);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public boolean isPropertyResettable(Object id) {
		boolean result = false;
		IPropertyDescriptor pd = descriptors.get(id);
		if (pd != null && ps instanceof IPropertySource2) {
			IPropertySource2 ps2 = (IPropertySource2) ps;
			result = ps2.isPropertyResettable(id);
		}
		return result;
	}
}
