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
package com.nokia.sdt.component.symbian.test;

import com.nokia.sdt.component.property.IPropertyValueSource;
import com.nokia.sdt.component.property.ISequencePropertyValue;
import com.nokia.sdt.datamodel.IDesignerDataModel;

import org.eclipse.emf.ecore.EObject;

import java.util.*;


class MockSequencePropertyValue implements ISequencePropertyValue, IPropertyValueSource {
	
	MockPropertyValueSource parent;
	Object propertyID;
	List sequence;
	
	public MockSequencePropertyValue(MockPropertyValueSource parent, Object id) {
		this.parent = parent;
		this.propertyID = id;
		sequence = new ArrayList();
	}
	
	public boolean canSetPropertyValue(Object propertyId) {
		return true;
	}

	public IPropertyValueSource addChildValueSource(int index) {
		if (index > sequence.size()) {
			index = sequence.size();
		}
		IPropertyValueSource result = new MockPropertyValueSource(Integer.toString(index));
		sequence.add(index, result);
		return result;
	}

	public int addLocalizedString(int index, String value) {
		if (index > sequence.size()) {
			index = sequence.size();
		}
		sequence.add(index, value);
		return index;
	}

	public int addStringLiteral(int index, String value) {
		if (index > sequence.size()) {
			index = sequence.size();
		}
		sequence.add(index, value);
		return index;
	}

	public int addComponentReferenceString(int index, String value) {
		if (index > sequence.size()) {
			index = sequence.size();
		}
		sequence.add(index, value);
		return index;
	}

	public ISequencePropertyValue createChildSequence(Object propertyId, boolean storeValue) {
		return new MockSequencePropertyValue(parent, propertyId);
	}

	public IPropertyValueSource createChildValueSource(Object propertyId, boolean storeValue) {
		return new MockPropertyValueSource(propertyId);
	}

	public IDesignerDataModel getDesignerDataModel() {
		return null;
	}

	public EObject getEObject() {
		return null;
	}

	public Object[] getIds() {
		String[] result = new String[sequence.size()];
		for (int i = 0; i < sequence.size(); i++) 
			result[i] = Integer.toString(i);
		return result;
	}

	private int idToIndex(Object propertyId) {
		int result = -1;
		if (propertyId instanceof String) {
			try {
				result = Integer.parseInt((String)propertyId);
			}
			catch (NumberFormatException x) {
			}
		}
		else if (propertyId instanceof Number) {
			result = ((Number)propertyId).intValue();
		}
		return result;
	}

	public boolean hasPropertyValue(Object propertyId) {
		boolean result = false;
		int index = idToIndex(propertyId);
		if (index >= 0 && index < sequence.size()) {
			result = true;
		}
		return result;
	}

	public int numberOfProperties() {
		return sequence.size();
	}

	public Object getPropertyValue(Object id) {
		Object result = null;
		int index = idToIndex(id);
		if (index >= 0 && index < sequence.size()) {
			result = sequence.get(index);
		}
		return result;
	}

	public void setPropertyValue(Object id, Object value) {
		int index = idToIndex(id);
		if (index >= 0 && index < sequence.size()) {
			sequence.set(index, value);
		}
	}

	public void resetPropertyValue(Object propertyId) {
		// don't implement unless we need for unit tests
		throw new UnsupportedOperationException();
	}

	public void add(int index, Object element) {
		sequence.add(index, element);
	}
	
	public boolean add(Object o) {
		return sequence.add(o);
	}
	
	public boolean addAll(Collection c) {
		return sequence.addAll(c);
	}
	
	public boolean addAll(int index, Collection c) {
		return sequence.addAll(index, c);
	}
	
	public void clear() {
		sequence.clear();
	}
	
	public boolean contains(Object o) {
		return sequence.contains(o);
	}
	
	public boolean containsAll(Collection c) {
		return sequence.containsAll(c);
	}
	
	public boolean equals(Object o) {
		return sequence.equals(o);
	}
	
	public Object get(int index) {
		return sequence.get(index);
	}
	
	public void move(int index, int newIndex) {
		Object obj = sequence.remove(index);
		if (newIndex > index) {
			newIndex--;
		}
		sequence.add(newIndex, obj);
	}
	
	public int hashCode() {
		return sequence.hashCode();
	}
	
	public int indexOf(Object o) {
		return sequence.indexOf(o);
	}
	
	public boolean isEmpty() {
		return sequence.isEmpty();
	}
	
	public Iterator iterator() {
		return sequence.iterator();
	}
	
	public int lastIndexOf(Object o) {
		return sequence.lastIndexOf(o);
	}
	
	public ListIterator listIterator() {
		return sequence.listIterator();
	}
	
	public ListIterator listIterator(int index) {
		return sequence.listIterator(index);
	}
	
	public void remove(int index) {
		sequence.remove(index);
	}
	
	public int size() {
		return sequence.size();
	}
	
	public IPropertyValueSource getValueSource() {
		return this;
	}

	public void setLocalizableStringPropertyValue(Object propertyId, String newValue, boolean overrideCurrentState) {
		setPropertyValue(propertyId, newValue);		
	}

	public String getStringPropertyValueSymbol(Object propertyId) {
		return null;
	}

	public void setReferencePropertyValue(Object propertyId, String newValue) {
		setPropertyValue(propertyId, newValue);		
	}

	public boolean isReferenceProperty(Object propertyId) {
		return false;
	}
	public UndoValue createUndoValue() {
		throw new UnsupportedOperationException();
	}

	public void setFromUndoValue(UndoValue value, boolean preserveLocalizedStringKeys) {
		throw new UnsupportedOperationException();
	}
	
	public String getPropertyPath() {
		String result;
		String parentPath = parent.getPropertyPath();
		if (parentPath != null) {
			result = parentPath + "." + propertyID;
		} else {
			result = propertyID.toString();
		}
		return result;
	}

	public String getPropertyPath(Object propertyId) {
		return getPropertyPath() + "." + propertyId;
	}

	public String getElementPath(int index) {
		return getPropertyPath(Integer.toString(index));
	}
	
	public IPropertyValueSource lookupReferencePropertyValueSource(Object id) {
		throw new UnsupportedOperationException();
	}
}
