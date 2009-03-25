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

import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.ITypeDescriptor;
import com.nokia.sdt.component.property.IPropertyValueSource;
import com.nokia.sdt.emf.component.EnumElementType;
import com.nokia.sdt.emf.component.EnumPropertyDeclarationType;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;
import com.nokia.cpp.internal.api.utils.ui.ModelObjectComboBoxCellEditor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import java.text.MessageFormat;
import java.util.*;

public class EnumPropertyTypeDescriptor implements ITypeDescriptor, Cloneable {
	
	private EnumPropertyDeclarationType epdt;
	private IComponentSet componentSet;
	private ILocalizedStrings strings;

	public EnumPropertyTypeDescriptor(EnumPropertyDeclarationType epdt,
					ILocalizedStrings ls) {
		Check.checkArg(epdt);
		Check.checkArg(ls);
		this.epdt = epdt;
		this.strings = ls;
	}

	public Object clone() {
		EnumPropertyTypeDescriptor copy = null;
		try {
			copy = (EnumPropertyTypeDescriptor) super.clone();
			copy.componentSet = null;
		}
		catch (CloneNotSupportedException x) {
			// should not happen since this class supports cloning
		}
		return copy;
	}

	public IComponentSet getComponentSet() {
		return componentSet;
	}

	public void setComponentSet(IComponentSet componentSet) {
		this.componentSet = componentSet;
	}

	public Object valueOf(String text) {
		// this used to act like #toDisplayString() but it implements the API as described now
		Object result;
		EnumElementType elem = epdt.findByDisplayValue(strings, text);
		if (elem != null)
			result = elem.getValue();
		else
			result = null;
		return result;
	}
	
	private String enumDisplayValue(EnumElementType elem) {
		String result = null;
		Object displayValue = elem.getDisplayValue();
		if (displayValue instanceof String) {
			result = (strings.checkPercentKey((String)displayValue));
		}
		// allow enum value to be a resource key
		if (result == null) {
			Object value = elem.getValue();
			if (value instanceof String && strings.hasString(((String)value))) {
				result = strings.getString((String) value);
			}
		}

		return result;
	}

	public String toDisplayString(Object value) {
		String result = null;
		if (value instanceof String) {
			EnumElementType elem = epdt.findByValue((String)value);
			if (elem != null) {
				Object objValue;
				objValue = enumDisplayValue(elem);
				if (objValue == null)
					objValue = elem.getValue();
				if (objValue != null)
					result = objValue.toString();
			}
		}
		return result;
	}
	
	public String toStorageString(Object value) {
		String result = null;
		if (value instanceof String) {
			if (epdt.findByValue((String)value) != null)
				result = value.toString();
		}
		else if (value instanceof Number) {
			int index = ((Number)value).intValue();
			List values = epdt.getEnumElement();
			if (index >= 0 && index < values.size())
				result = (String)((EnumElementType)values.get(index)).getValue();
		}
		return result;
	}

	public Object defaultValue(IPropertyValueSource valueSource,
			Object propertyId) {
		// Use the designated default value, or first value if none designated
		Object result = epdt.getDefaultValue();
		if (result == null) {
			List values = epdt.getEnumElement();
			if (values.size() > 0) {
				EnumElementType el = (EnumElementType) epdt.getEnumElement().get(0);
				result = el.getValue();
			}
		}
		return result;
	}

	public Object editableValueToPropertyValue(Object editableValue, IPropertyValueSource parentValueSource) {
		return editableValue;
	}

	private List getValueList() {
		ArrayList result = new ArrayList();
		for (Iterator iter = epdt.getEnumElement().iterator(); iter.hasNext();) {
			EnumElementType element = (EnumElementType) iter.next();
			result.add(element.getValue());
		}
		return result;
	}

	public Collection getChoiceOfValues() {
		return getValueList();
	}

	public ICellEditorValidator getValidator() {
		ICellEditorValidator validator = new ICellEditorValidator() {
			public String isValid(Object value) {
				if (getValueList().contains(value))
					return null;
				
				String format = Messages.getString("EnumPropertyTypeDescriptor.0"); //$NON-NLS-1$
				Object params[] = {value, EnumPropertyTypeDescriptor.this.epdt.getQualifiedName()};
				String result = MessageFormat.format(format, params);
				return result;
			}
		};
		return validator;
	}

	public CellEditor createPropertyEditor(Composite parent, int styl, EObject object, String propertyPath) {
		ILabelProvider labelProvider = new LabelProvider() {
			public String getText(Object element) {
				return toDisplayString(element);
			}
		};		
		return new ModelObjectComboBoxCellEditor(parent, getValueList(), labelProvider);
	}

	public Image getImage() {
		return TypeDescriptors.getImage(TypeDescriptors.ENUM_IMAGE);
	}

	public boolean isLocalizable() {
		return false;
	}

	public boolean isReference() {
		return false;
	}

	public String getEditorFactoryClass() {
		return null;
	}
	
	public EnumPropertyDeclarationType getEnumPropertyDeclarationType() {
		return epdt;
	}
	
	public ILocalizedStrings getStrings() {
		return strings;
	}
}
