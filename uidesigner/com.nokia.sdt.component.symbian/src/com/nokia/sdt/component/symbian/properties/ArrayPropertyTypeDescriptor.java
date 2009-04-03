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
import com.nokia.sdt.component.property.ISequencePropertyValue;
import com.nokia.sdt.component.symbian.ComponentSet;
import com.nokia.sdt.emf.component.ArrayPropertyType;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.Collection;

public class ArrayPropertyTypeDescriptor implements ITypeDescriptor {

	private IComponentSet componentSet;
	private ArrayPropertyType apt;
	private ILocalizedStrings strings;
	
	public ArrayPropertyTypeDescriptor(ArrayPropertyType apt,
					ILocalizedStrings ls) {
		Check.checkArg(apt);
		Check.checkArg(ls);
		this.apt = apt;
		this.strings = ls;
	}
	
	public Object clone() {
		ArrayPropertyTypeDescriptor copy = null;
		try {
			copy = (ArrayPropertyTypeDescriptor) super.clone();
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
	
	public ITypeDescriptor getElementTypeDescriptor() {
		ITypeDescriptor result = componentSet.lookupTypeDescriptor(apt.getType());
		return result;
	}
	
	public Object valueOf(String text) {
		return null;
	}

	public String toDisplayString(Object value) {
		return "";
	}

	public String toStorageString(Object value) {
		// should not be called, since compound properties
		// are stored as a set of contained properties
		return null;
	}

	public Object defaultValue(IPropertyValueSource valueSource, Object propertyId) {
		IPropertySource result = null;
		if (valueSource != null && propertyId != null) {
			ISequencePropertyValue spv = valueSource.createChildSequence(propertyId, true);
			result = new ArrayPropertySource(apt, 
					(ComponentSet)componentSet, spv, strings);
		}
		return result;
	}

	public Object editableValueToPropertyValue(Object editableValue, IPropertyValueSource parentValueSource) {
		return editableValue;
	}

	public String isValid(Object value) {
		return null;
	}

	public CellEditor createPropertyEditor(Composite parent, int style, EObject object, String propertyPath) {
		return null;
	}

	public Image getImage() {
		return TypeDescriptors.getImage(TypeDescriptors.GENERIC_IMAGE);
	}
	
	public ICellEditorValidator getValidator() {
		return null;
	}

	public Collection getChoiceOfValues() {
		return null;
	}	
	
	public ILocalizedStrings getLocalizedStrings() {
		return strings;
	}

	public boolean isLocalizable() {
		return getElementTypeDescriptor().isLocalizable();
	}

	public boolean isReference() {
		return getElementTypeDescriptor().isReference();
	}

	public String getEditorFactoryClass() {
		return null;
	}
	
	public ArrayPropertyType getArrayPropertyType() {
		return apt;
	}
	
	public ILocalizedStrings getStrings() {
		return strings;
	}
}
