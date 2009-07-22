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

import java.util.ArrayList;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import cwdbg.EclipseDEConstants;

public class OSObjectThread extends OSObject {

	// static value of the type of the OSObject.
	private static String s_typeName = "Symbian OS Thread";

	// Property Descriptors
	static protected IPropertyDescriptor[] s_propertyDescriptors = {
		new PropertyDescriptor(IOSObjectProperties.ID.ObjectType,
				IOSObjectProperties.DisplayName.ObjectType),
		new PropertyDescriptor(IOSObjectProperties.ID.Name,
				IOSObjectProperties.DisplayName.Name),
		new PropertyDescriptor(IOSObjectProperties.ID.ID,
				IOSObjectProperties.DisplayName.ID),
		new PropertyDescriptor(IOSObjectProperties.ID.Priority,
				IOSObjectProperties.DisplayName.Priority),
		new PropertyDescriptor(IOSObjectProperties.ID.State,
				IOSObjectProperties.DisplayName.State),
		new PropertyDescriptor(IOSObjectProperties.ID.OwningProcessName,
				IOSObjectProperties.DisplayName.OwningProcessName)
	};

	static {
		for (int i = 0; i < s_propertyDescriptors.length; i++)
			((PropertyDescriptor) s_propertyDescriptors[i])
					.setAlwaysIncompatible(true);
	}

	static private String[] s_sortableProperties = {
		IOSObjectProperties.ID.Name,
		IOSObjectProperties.ID.ID,
		IOSObjectProperties.ID.Priority,
		IOSObjectProperties.ID.State,
		IOSObjectProperties.ID.OwningProcessName
	};

	static private IPropertyDescriptor[] s_sortablePropertyDescriptors;
	static {
		s_sortablePropertyDescriptors = new IPropertyDescriptor[s_sortableProperties.length];
		int k = 0;
		for (int i = 0; i < s_sortableProperties.length; i++) {
			for (int j = 0; j < s_propertyDescriptors.length; j++)
				if (s_propertyDescriptors[j].getId().equals(
						s_sortableProperties[i])) {
					s_sortablePropertyDescriptors[k++] = s_propertyDescriptors[j];
					continue;
				}
		}
	}

	public OSObjectThread(OSObjectProperty[] properties) {
		// Record the property that's not from the backend.
		m_properties.put(IOSObjectProperties.ID.ObjectType, getTypeName());

		// Record the properties from the backend.
		for (int i = 0; i < properties.length; i++) {
			m_properties.put(properties[i].getID(), properties[i].getValue());
		}

		constructSupportedPropertyList();
	}

	static public IPropertyDescriptor[] getPropertyDescriptorList() {
		return s_propertyDescriptors;
	}

	static public IPropertyDescriptor[] getSortablePropertyDescriptorList() {
		return s_sortablePropertyDescriptors;
	}

	@Override
	public String getTypeName() {
		return s_typeName;
	}

	@Override
	/**
	 * For thread object, as we may add extra properties (owner ID & owner name)
	 * on the fly
	 */
	public void setPropertyValue(Object id, Object value) {
		boolean newProperty = false;
		if (m_properties.get(id) == null)
			newProperty = true;

		m_properties.put((String) id, value);

		if (newProperty)
			// a new property is added, reconstruct the following list.
			constructSupportedPropertyList();
	}

	/**
	 *  Get owner process ID
	 */
	public int getProcessID() {
		Object obj = getRawPropertyValue(IOSObjectProperties.ID.OwningProcessID);
		if (obj == null)
			return EclipseDEConstants.J_OSObjectID_Invalid;

		return ((Integer) obj).intValue();
	}
	
	/**
	 * Construct the PropertyDescriptors supported by the object. With different
	 * debugger (e.g. run mode debugger vs. Stop mode), the supported properties may
	 * be different.
	 */
	private void constructSupportedPropertyList() {
		ArrayList<IPropertyDescriptor> supportedProps = new ArrayList<IPropertyDescriptor>();

		IPropertyDescriptor[] allPD = getPropertyDescriptorList();

		for (int i = 0; i < allPD.length; i++) {
			if (m_properties.get(allPD[i].getId()) != null)
				supportedProps.add(allPD[i]);
		}

		m_supportedPropertyDescriptors = new PropertyDescriptor[supportedProps
				.size()];
		supportedProps.toArray(m_supportedPropertyDescriptors);
	}
}
