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
package com.nokia.sdt.symbian.ui.editors;

import com.nokia.sdt.component.property.IPropertyInformation;
import com.nokia.sdt.component.symbian.properties.CompoundPropertyTypeDescriptor;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.*;

public class CompoundPropertySourceWrapper implements IPropertySource2 {

	private IPropertySource ps;
	private EObject object;
	private Object propertyID;
	private CompoundPropertyTypeDescriptor cptd;
	private PropertyDescriptor childPD;
	
	class PropertyDescriptor implements IPropertyDescriptor {

		public CellEditor createPropertyEditor(Composite parent) {
			String propertyPath;
			if (ps instanceof IPropertyInformation) {
				IPropertyInformation pi = (IPropertyInformation) ps;
				propertyPath = pi.getPropertyPath(propertyID);
			}
			else {
				propertyPath = propertyID.toString();
			}
			return cptd.createPropertyEditor(parent, SWT.NONE, object, propertyPath);
		}

		public String getCategory() {
			return null;
		}

		public String getDescription() {
			return null;
		}

		public String getDisplayName() {
			return propertyID.toString();
		}

		public String[] getFilterFlags() {
			return null;
		}

		public Object getHelpContextIds() {
			return null;
		}

		public Object getId() {
			return propertyID;
		}

		public ILabelProvider getLabelProvider() {
			return new LabelProvider();
		}

		public boolean isCompatibleWith(IPropertyDescriptor anotherProperty) {
			return false;
		}
	}

	public CompoundPropertySourceWrapper(IPropertySource compoundPropertySource,
				EObject object, Object propertyID, CompoundPropertyTypeDescriptor cptd) {
		Check.checkArg(compoundPropertySource);
		Check.checkArg(object);
		Check.checkArg(propertyID);
		Check.checkArg(cptd);
		this.ps = compoundPropertySource;
		this.object = object;
		this.propertyID = propertyID;
		this.cptd = cptd;
		
		childPD = new PropertyDescriptor();
	}

	public Object getEditableValue() {
		return ps;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] {childPD};
	}

	public Object getPropertyValue(Object id) {
		Object result = null;
		if (propertyID.equals(id)) {
			result = ps;
		}
		return result;
	}

	public boolean isPropertySet(Object id) {
		boolean result = false;
		if (propertyID.equals(id)) {
			result = true;
		}
		return result;
	}

	public void resetPropertyValue(Object id) {
		// we can't reset the property we're wrapping, we don't have its parent
	}

	public void setPropertyValue(Object id, Object value) {
		if (propertyID.equals(id)) {
			ps.setPropertyValue(id, value);
		}
	}

	public boolean isPropertyResettable(Object id) {
		return false;
	}
}
