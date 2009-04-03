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

import com.nokia.sdt.component.property.ISequencePropertySource;
import com.nokia.sdt.component.property.ISequencePropertyValue;
import com.nokia.sdt.datamodel.util.ModelUtils;

import org.eclipse.emf.ecore.EObject;

import java.util.Iterator;


/**
 * Object type returned by IPropertySource.getEditableValue
 * for array properties. Encapsulates the information needed
 * to edit an array property via a cell editor
 *
 */
public class ArrayEditableValue {
	
	private ArrayPropertySource editValue;
	private CompoundPropertyTypeDescriptor cpTypeDescriptor;

	/**
	 * Construct the editable value
	 * @param originalValue starting value, used to initialize this instance
	 * @param cpTypeDescriptor non-null if array elements are compound properties
	 */
	public ArrayEditableValue(ArrayPropertySource originalValue, CompoundPropertyTypeDescriptor cpTypeDescriptor) {
		this.cpTypeDescriptor = cpTypeDescriptor;
		String propertyPath = originalValue.getPropertyValueSource().getPropertyPath();
		copyFrom(originalValue, ModelUtils.getLeafFromPath(propertyPath));
	}
	
	public ArrayEditableValue(ArrayEditableValue arrayValue) {
		this.cpTypeDescriptor = arrayValue.cpTypeDescriptor;
		String propertyPath = arrayValue.editValue.getPropertyValueSource().getPropertyPath();
		copyFrom(arrayValue.editValue, ModelUtils.getLeafFromPath(propertyPath));
	}
	
	private void copyFrom(ArrayPropertySource aps, Object propertyID) {
		ISequencePropertyValue editValueSource = aps.getPropertyValueSource().createChildSequence(propertyID, false);
		this.editValue = new ArrayPropertySource(aps.getArrayPropertyType(),
				aps.getComponentSet(), editValueSource, 
				aps.getStrings());
		
		int index = 0;
		for (Iterator iter = aps.iterator(); iter.hasNext();) {
			Object currValue = iter.next();
			if (aps.isCompoundElement()) {
				editValue.addCompoundProperty(index);
				editValue.setPropertyValue(Integer.toString(index), currValue);
			} else {
				editValue.addSimpleProperty(index, currValue);
			}
			++index;
		}
	}
	
	public ISequencePropertySource getValue() {
		return editValue;
	}
	
	public EObject getEObject() {
		return editValue.getPropertyValueSource().getEObject();
	}

	public CompoundPropertyTypeDescriptor getCompoundPropertyTypeDescriptor() {
		return cpTypeDescriptor;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ArrayEditableValue)
			return ((ArrayEditableValue) obj).editValue.equals(editValue);
		
		return false;
	}
	
}
