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

public class OSObjectProcess extends OSObject {

	// static value of the type of the OSObject.
	private static String s_typeName = "Symbian OS Process";

	private OSObjectThread m_threads[] = new OSObjectThread[0];

	private OSObjectChunk m_chunks[] = new OSObjectChunk[0];

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
			new PropertyDescriptor(IOSObjectProperties.ID.Attribute,
					IOSObjectProperties.DisplayName.Attribute),
			new PropertyDescriptor(IOSObjectProperties.ID.CodeRunAddress,
					IOSObjectProperties.DisplayName.CodeRunAddress),
			new PropertyDescriptor(IOSObjectProperties.ID.CodeLoadAddress,
					IOSObjectProperties.DisplayName.CodeLoadAddress),
			new PropertyDescriptor(IOSObjectProperties.ID.TextSize,
					IOSObjectProperties.DisplayName.TextSize),
			new PropertyDescriptor(IOSObjectProperties.ID.DataSize,
					IOSObjectProperties.DisplayName.DataSize),
			new PropertyDescriptor(IOSObjectProperties.ID.BssSize,
					IOSObjectProperties.DisplayName.BssSize),
			new PropertyDescriptor(IOSObjectProperties.ID.DataBssRunAddress,
					IOSObjectProperties.DisplayName.DataBssRunAddress),
			new PropertyDescriptor(IOSObjectProperties.ID.DataRunAddress,
					IOSObjectProperties.DisplayName.DataRunAddress),
			new PropertyDescriptor(IOSObjectProperties.ID.DataLoadAddress,
					IOSObjectProperties.DisplayName.DataLoadAddress),
			new PropertyDescriptor(IOSObjectProperties.ID.DataBssStackChunk,
					IOSObjectProperties.DisplayName.DataBssStackChunk),
			new PropertyDescriptor(IOSObjectProperties.ID.DependencyCount,
					IOSObjectProperties.DisplayName.DependencyCount),
			new PropertyDescriptor(IOSObjectProperties.ID.DependencyFiles,
					IOSObjectProperties.DisplayName.DependencyFiles), };

	static {
		for (int i = 0; i < s_propertyDescriptors.length; i++)
			((PropertyDescriptor) s_propertyDescriptors[i])
					.setAlwaysIncompatible(true);
	}

	static private String[] s_sortableProperties = {
			IOSObjectProperties.ID.Name, IOSObjectProperties.ID.ID,
			IOSObjectProperties.ID.Priority,
			IOSObjectProperties.ID.CodeRunAddress,
			IOSObjectProperties.ID.CodeLoadAddress,
			IOSObjectProperties.ID.TextSize, IOSObjectProperties.ID.DataSize };

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

	public OSObjectProcess(OSObjectProperty[] properties) {

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

	public OSObjectThread[] getThreads() {
		return m_threads;
	}

	public void setThreads(OSObjectThread threads[]) {
		m_threads = threads;
	}

	public OSObjectChunk[] getChunks() {
		return m_chunks;
	}

	public void setChunks(OSObjectChunk chunks[]) {
		m_chunks = chunks;
	}
}
