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
package com.nokia.sdt.component.symbian.properties;

import com.nokia.sdt.component.ITypeDescriptor;
import com.nokia.sdt.component.property.IPropertyValueSource;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.util.NamePropertySupport;
import com.nokia.sdt.emf.component.SimplePropertyType;
import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class UniqueNamePropertyDescriptor extends SimplePropertyDescriptor {

	UniqueNamePropertyDescriptor(SimplePropertyType propertyType, 
			ITypeDescriptor typeDescriptor, IPropertyValueSource valueSource,
			ILocalizedStrings strings, boolean forceReadOnly) {
		super(propertyType, typeDescriptor, valueSource, strings, forceReadOnly);
	}

	public CellEditor doCreatePropertyEditor(Composite parent, int style) {
		CellEditor result = super.doCreatePropertyEditor(parent, style);
		if (result != null) {
			IDesignerDataModel model = getValueSource().getDesignerDataModel();
			EObject obj = getValueSource().getEObject();
			result.setValidator(new Validator(model, obj));
		}
		return result;
	}

	public boolean isCompatibleWith(IPropertyDescriptor anotherProperty) {
		// Since this is a property descriptor for a unique value,
		// it can't be compatible with any other property descriptors
		return this.equals(anotherProperty);
	}

	static class Validator implements ICellEditorValidator {
		
		IDesignerDataModel model;
		EObject object;

		public Validator(IDesignerDataModel model, EObject object) {
			this.model = model;
			this.object = object;
		}

		public String isValid(Object value) {
			String result = null;
			if (!(value instanceof String) || !NamePropertySupport.isLegalName((String)value)) {
				result = NamePropertySupport.illegalNameMessage(value);
			}
			else if (model != null && object != null) {
				EObject foundObj = model.findByNameProperty((String)value);
				if (foundObj != null && foundObj != object) {
					result = NamePropertySupport.duplicateNameMessage(value);
				}
			}
			return result;
		}	
	}
}
