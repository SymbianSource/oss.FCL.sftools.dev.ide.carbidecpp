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
package com.nokia.carbide.cpp.debug.kernelaware.ui;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.nokia.carbide.cpp.debug.kernelaware.IOSObjectProperties;

public class OverviewTabPseudoNode implements IPropertySource {
	private int type;

	private Object parent;

	private String name;

	// static value of the type of the node.
	private static String s_typeValue = "Pseudo Node";  //$NON-NLS-1$

	// Property Descriptors
	static protected IPropertyDescriptor[] s_propertyDescriptors = {
			new PropertyDescriptor(IOSObjectProperties.ID.ObjectType,
					IOSObjectProperties.DisplayName.ObjectType),
			new PropertyDescriptor(IOSObjectProperties.ID.Name,
					IOSObjectProperties.DisplayName.Name), };

	static {
		for (int i = 0; i < s_propertyDescriptors.length; i++)
			((PropertyDescriptor) s_propertyDescriptors[i])
					.setAlwaysIncompatible(true);
	}

	public OverviewTabPseudoNode(int type, String name, Object parent) {
		this.type = type;
		this.parent = parent;
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public Object getParent() {
		return parent;
	}

	public String toString() {
		return name;
	}

	public Object getEditableValue() {
		return this;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return s_propertyDescriptors;
	}

	public Object getPropertyValue(Object id) {
		if (id.equals(IOSObjectProperties.ID.ObjectType))
			return s_typeValue;
		else if (id.equals(IOSObjectProperties.ID.Name))
			return name;

		return null;
	}

	public boolean isPropertySet(Object id) {
		return false;
	}

	public void resetPropertyValue(Object id) {
	}

	public void setPropertyValue(Object id, Object value) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof OverviewTabPseudoNode) {
			OverviewTabPseudoNode that = (OverviewTabPseudoNode) arg0;

			if (getParent() == null && that.getParent() != null
					|| getParent() != null && that.getParent() == null)
				return false;

			if (getType() == that.getType()
					&& toString().equals(that.toString())
					&& (getParent() == null && that.getParent() == null || getParent()
							.equals(that.getParent())))
				return true;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return toString().hashCode() ^ (getType() << 4)
				^ (getParent() != null ? getParent().toString().hashCode() : 0)
				^ 391;
	}
}
