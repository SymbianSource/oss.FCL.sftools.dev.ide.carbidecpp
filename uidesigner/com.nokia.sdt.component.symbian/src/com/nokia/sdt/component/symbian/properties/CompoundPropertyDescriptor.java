/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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
import com.nokia.sdt.component.property.*;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.datamodel.adapter.IReconcileProperty;
import com.nokia.sdt.emf.component.CompoundPropertyType;
import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;
import com.nokia.cpp.internal.api.utils.ui.ModelObjectComboBoxCellEditor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.MessageFormat;
import java.util.ArrayList;

public class CompoundPropertyDescriptor extends AbstractPropertyDescriptor {

	CompoundPropertyDescriptor(CompoundPropertyType propertyType, 
			CompoundPropertyTypeDescriptor typeDescriptor,
			IPropertyValueSource valueSource,
			ILocalizedStrings strings, boolean forceReadOnly) {
		super(propertyType, typeDescriptor, valueSource, strings, forceReadOnly);
		checkReadOnly();
		
		// Establish initial value for the default value initializer
		// if set it's used to create initialized compound properties
		setDefaultValueInitializer(getPropertyType().getDefault());
	}
	
	@Override
	public Object getDefaultValue() {
		Object result = null;
		// The type descriptor creates a default compound property value.
		// If we have an initializer value then use the ICompoundPropertyValue
		// converter to apply the initializer to the compound property.
		CompoundPropertyTypeDescriptor cptd = (CompoundPropertyTypeDescriptor) getTypeDescriptor();
		IPropertySource ps = (IPropertySource) cptd.defaultValue(getValueSource(), getId());
		
		Object initializer = getDefaultValueInitializer();
		if (initializer != null) {
			ICompoundPropertyValueConverter valueConverter = cptd.getValueConverter();
			if (valueConverter != null) {
				valueConverter.applyEditableValue(getValueSource().getEObject(), initializer, ps);
				result = ps;
			}
			else {
				String fmt = Messages.getString("AbstractPropertySource.4"); //$NON-NLS-1$
				Object params[] = {getId(), cptd.getCompoundPropertyDeclarationType().getQualifiedName()};
				String msg = MessageFormat.format(fmt, params);
				ComponentSystemPlugin.log(null, msg);
			}
		} else {
			result = ps;
		}
		return result;
	}
	
	protected CompoundPropertyType getPropertyType() {
		return (CompoundPropertyType) super.getPropertyType();
	}
	
	protected Object convertDefaultValue(Object value) {
		if (!(value instanceof String)) return value;
		
		CompoundPropertyTypeDescriptor cptd = (CompoundPropertyTypeDescriptor) getTypeDescriptor();
		IPropertySource ps = (IPropertySource) cptd.defaultValue(getValueSource(), getId());
		ICompoundPropertyValueConverter valueConverter = cptd.getValueConverter();
		if (valueConverter != null) {
			valueConverter.applyEditableValue(getValueSource().getEObject(), value, ps);
			value = ps;
		}
		else {
			String fmt = Messages.getString("AbstractPropertySource.4"); //$NON-NLS-1$
			Object params[] = {getId(), cptd.getCompoundPropertyDeclarationType().getQualifiedName()};
			String msg = MessageFormat.format(fmt, params);
			ComponentSystemPlugin.log(null, msg);
		}
		return value;
	}

	
		// Allow an IReconcileProperty adapter to make the 
		// property read-only
	private void checkReadOnly() {
		if (!isReadOnly()) {
			EObject emfObject = getValueSource().getEObject();
			if (emfObject != null) {
				IReconcileProperty reconcileProperty = 
					(IReconcileProperty) EcoreUtil.getRegisteredAdapter(emfObject, IReconcileProperty.class);
				if (reconcileProperty != null && 
					!reconcileProperty.isDisplayValueEditable(getPropertyType().getType())) {
					setReadOnly(true);
				}
			}
		}
	}
	
	public CellEditor doCreatePropertyEditor(Composite parent, int style) {
		CellEditor result = null;
		IPropertyEditorFactory factory = getEditorFactory();
		if (factory != null) {
			EObject obj = getValueSource().getEObject();
			result = factory.createCellEditor(parent, obj, getPropertyPath());
		}
		
		if (result == null) {
			ITypeDescriptor td = getTypeDescriptor();
			if (td instanceof CompoundPropertyTypeDescriptor) {
				ITypeDescriptor editableType = ((CompoundPropertyTypeDescriptor) td).getEditableType();
				if (editableType instanceof EnumPropertyTypeDescriptor) {
					result = new ModelObjectComboBoxCellEditor(parent, 
											new ArrayList(editableType.getChoiceOfValues()), 
																	getLabelProvider()) {
						Object customObject;
						CCombo comboBox;
						
					    protected Control createControl(Composite parent) {
					    	comboBox = (CCombo) super.createControl(parent);
					    	return comboBox;
					    }
					    
						public Object doGetValue() {
							Object result = super.doGetValue();
							if (result == null)
								return customObject;
							
							return result;
						}

						public void doSetValue(Object value) {
							int index = modelItems.indexOf(value);
							if (index != -1) {
						    	super.doSetValue(value);
							}
							else {
								customObject = value;
								comboBox.setText(getLabelProvider().getText(customObject));
							}
						}
					};
				}
			}
			else
				result = td.createPropertyEditor(parent, style, 
						this.getValueSource().getEObject(), getPropertyPath());
		}
		return result;
	}
	
    public ILabelProvider getLabelProvider() {
		ILabelProvider result = null;
		IPropertyEditorFactory factory = getEditorFactory();
		if (factory != null) {
			EObject obj = getValueSource().getEObject();
			result = factory.createLabelProvider(obj, getPropertyPath());
		}
		
		if (result == null) {
			final ITypeDescriptor td = getTypeDescriptor();
			result = new LabelProvider() {
				public String getText(Object object) {
					if (td instanceof CompoundPropertyTypeDescriptor) {
						ITypeDescriptor editableType = ((CompoundPropertyTypeDescriptor) td).getEditableType();
						if ((editableType instanceof EnumPropertyTypeDescriptor) && (object instanceof String)) {
							String displayString = editableType.toDisplayString(object);
							if (displayString == null)
								return Messages.getString("CompoundPropertyDescriptor.CUSTOM"); //$NON-NLS-1$
							
							return displayString;
						}
					}
					return td.toDisplayString(object);
				}
				public Image getImage(Object object) {
					return td.getImage();
				}
			};
		}
		return result;
	}

	public boolean isCompatibleWith(IPropertyDescriptor anotherProperty) {
		boolean isCompatible = false;
		if (anotherProperty instanceof CompoundPropertyDescriptor) {
			CompoundPropertyDescriptor other = (CompoundPropertyDescriptor) anotherProperty;
			isCompatible = other.getPropertyType().equals(getPropertyType());
		}
		return isCompatible;
	}

}
