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

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class OSObjectChunk extends OSObject {

	// static value of the type of the OSObject.
	private static String s_typeName = "Symbian OS Chunk";

	// Property Descriptors
	static protected IPropertyDescriptor[] s_propertyDescriptors = {
			new PropertyDescriptor(IOSObjectProperties.ID.ObjectType,
					IOSObjectProperties.DisplayName.ObjectType),
			new PropertyDescriptor(IOSObjectProperties.ID.Name,
					IOSObjectProperties.DisplayName.Name),

			new PropertyDescriptor(IOSObjectProperties.ID.Attribute,
					IOSObjectProperties.DisplayName.Attribute),
			new PropertyDescriptor(IOSObjectProperties.ID.HomeAddress,
					IOSObjectProperties.DisplayName.HomeAddress),
			new PropertyDescriptor(IOSObjectProperties.ID.Size,
					IOSObjectProperties.DisplayName.Size),
			new PropertyDescriptor(IOSObjectProperties.ID.State,
					IOSObjectProperties.DisplayName.State),
			new PropertyDescriptor(IOSObjectProperties.ID.Type,
					IOSObjectProperties.DisplayName.Type),
			new PropertyDescriptor(IOSObjectProperties.ID.OwningProcessName,
					IOSObjectProperties.DisplayName.OwningProcessName), };

	static {
		for (int i = 0; i < s_propertyDescriptors.length; i++)
			((PropertyDescriptor) s_propertyDescriptors[i])
					.setAlwaysIncompatible(true);
	}

	static private String[] s_sortableProperties = {
			IOSObjectProperties.ID.Name, IOSObjectProperties.ID.Type,
			IOSObjectProperties.ID.Size,
			IOSObjectProperties.ID.OwningProcessName,
			IOSObjectProperties.ID.State, };

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

	public OSObjectChunk(OSObjectProperty[] properties) {
		m_properties.put(IOSObjectProperties.ID.ObjectType, getTypeName());

		for (int i = 0; i < properties.length; i++) {
			m_properties.put(properties[i].getID(), properties[i].getValue());
		}

		// Construct the PropertyDescriptors supported by the object.
		// With different debugger (e.g. TRK debugger vs. Stop mode), the
		// supported properties may be different.
		m_supportedPropertyDescriptors = new PropertyDescriptor[properties.length + 1];
		IPropertyDescriptor[] allPD = getPropertyDescriptorList();

		int k = 0;
		for (int i = 0; i < allPD.length; i++) {
			if (m_properties.get(allPD[i].getId()) != null)
				m_supportedPropertyDescriptors[k++] = allPD[i];
		}
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
}
