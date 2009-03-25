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

import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.ITypeDescriptor;
import com.nokia.sdt.component.property.*;
import com.nokia.sdt.component.symbian.*;
import com.nokia.sdt.emf.component.CompoundPropertyDeclarationType;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySource;
import org.osgi.framework.Bundle;

import java.util.Collection;

public class CompoundPropertyTypeDescriptor implements ITypeDescriptor, Cloneable {

	private IComponentSet componentSet;
	private Bundle bundle;
	private CompoundPropertyDeclarationType cpdt;
	private ILocalizedStrings strings;
	private ICompoundPropertyValueConverter valueConverter;
	
	public CompoundPropertyTypeDescriptor(CompoundPropertyDeclarationType cpdt,
					Bundle bundle,
					ILocalizedStrings ls) {
		Check.checkArg(cpdt);
		Check.checkArg(ls);
		//Check.checkArg(bundle); // check later 
		this.cpdt = cpdt;
		this.strings = ls;
		this.bundle = bundle;
	}
	
	public Object clone() {
		CompoundPropertyTypeDescriptor copy = null;
		try {
			copy = (CompoundPropertyTypeDescriptor) super.clone();
			copy.componentSet = null;
		}
		catch (CloneNotSupportedException x) {
			// should not happen since this class supports cloning
		}
		return copy;
	}
	
	public void setComponentSet(IComponentSet componentSet) {
		this.componentSet = componentSet;
	}

	public IComponentSet getComponentSet() {
		return componentSet;
	}
	
	public CompoundPropertyDeclarationType getCompoundPropertyDeclarationType() {
		return cpdt;
	}
	
	public ICompoundPropertyValueConverter getValueConverter() {
		if (valueConverter == null) {
			String converterClass = cpdt.getConverterClass();
			if (!TextUtils.isEmpty(converterClass)) {
				valueConverter = createValueConverter(converterClass, bundle);
			}
		}
		return valueConverter;
	}
	
	static ICompoundPropertyValueConverter createValueConverter(
			String className, Bundle bundle) {
		String errorFormat = Messages.getString("CompoundPropertyTypeDescriptor.0"); //$NON-NLS-1$
		Object errorParams[] = {className};
		Object createdObj = ClassUtils.loadAndCreateInstance(
				bundle, className, ICompoundPropertyValueConverter.class,
				ComponentSystemPlugin.getDefault(), errorFormat, errorParams);
		return (ICompoundPropertyValueConverter) createdObj;
	}

	public ITypeDescriptor getEditableType() {
		ITypeDescriptor result = null;
		String editableType = cpdt.getEditableType();
		if (editableType != null) {
			result = componentSet.lookupTypeDescriptor(editableType);
		}
		else
			result = componentSet.lookupTypeDescriptor(ITypeDescriptor.VOID_TYPE);
		return result;
	}
	
	public Object valueOf(String text) {
		return null;
	}

	public String toDisplayString(Object value) {
		String result = null;
		// TODO - this isn't finished
		// If some editable value has been generated then turn it
		// into a string. If we're getting the property source then
		// that's not going to provide a useful string
		if (value != null && !(value instanceof IPropertySource)) {
			result = value.toString();
		}
		return result != null? result : ""; //$NON-NLS-1$
	}

	public String toStorageString(Object value) {
		// should not be called, since compound properties
		// are stored as a set of contained properties
		return null;
	}

	public Object defaultValue(IPropertyValueSource valueSource, Object propertyId) {
		IPropertySource result = null;
		// note that if a propertyId is passed it will be attached to the parent 
		// property source. That's an error if the property already had a value.
		// When null is passed the property is not set, so it's an unattached
		// property source.
		if (valueSource != null) {
			IPropertyValueSource childValueSource = valueSource.createChildValueSource(propertyId, propertyId != null);
			if (childValueSource != null) {
				result = new CompoundPropertySource(this, (ComponentSet)componentSet, 
						childValueSource, strings, false);
			}
		}
		return result;
	}
	
	public Object editableValueToPropertyValue(Object editableValue, 
			IPropertyValueSource parentValueSource) {
		if (editableValue == null)
			return null;
		Object result = editableValue;
		ICompoundPropertyValueConverter converter = getValueConverter();
		if (converter != null) {
			IPropertySource ps = (IPropertySource) defaultValue(parentValueSource, null);
			converter.applyEditableValue(parentValueSource.getEObject(), editableValue, ps);
			result = ps;
		}
		return result;
	}

	public String isValid(Object value) {
		return null;
	}

	public CellEditor createPropertyEditor(Composite parent, int style, EObject object, String propertyPath) {
		CellEditor result = null;
		
		String editorClass = getEditorFactoryClass();
		if (!TextUtils.isEmpty(editorClass)) {
			IPropertyEditorFactory factory = 
				AbstractPropertyDescriptor.createPropertyEditorFactory(
						editorClass, bundle);
			if (factory != null) {
				result = factory.createCellEditor(parent, object, propertyPath);
			}
		}
		if (result == null) {
			ITypeDescriptor td = getEditableType();
			if (td != null) {
				result = td.createPropertyEditor(parent, style, object, propertyPath);
			}
		}
		return result;
	}
	
	public boolean hasCellEditor() {
		boolean result = false;
		// This is imperfect, because the presence of a factory
		// does not imply that factory returns non-null for 
		// createPropertyEditorFactory. Consider adding another method
		// to provide that info
		String editorClass = getEditorFactoryClass();
		if (!TextUtils.isEmpty(editorClass)) {
			result = true;
		}
		if (!result) {
			ITypeDescriptor td = getEditableType();
			result = td != null;
		}
		
		return result;
	}
	
	public String getEditorFactoryClass() {
		return cpdt.getEditorClass();
	}

	public ICellEditorValidator getValidator() {
		return null;
	}

	public Image getImage() {
		Image result = null;
		ITypeDescriptor td = getEditableType();
		if (td != null)
			result = td.getImage();
		return result;
	}

	public Collection getChoiceOfValues() {
		return null;
	}	
	
	public ILocalizedStrings getLocalizedStrings() {
		return strings;
	}

	public boolean isLocalizable() {
		return false;
	}

	public boolean isReference() {
		return false;
	}
	
	public ILocalizedStrings getStrings() {
		return strings;
	}
}