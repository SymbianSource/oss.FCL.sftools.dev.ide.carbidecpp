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
package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.component.property.IPropertyValueSource;
import com.nokia.sdt.component.property.ISequencePropertyValue;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.emf.dm.util.PropertyValueSwitch;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.*;


/**
 * Implementation of IPropertyValueSource for sequence properties.
 * As seen through the IPropertyValueSource interface, the sequence
 * is a set of properties whose ids are the zero-based index values.
 * Existing values can be modified, but sequence elements cannot be
 * added or removed.
 */
public class SequencePropertyValueSource implements IPropertyValueSource, ISequencePropertyValue {

	private IPropertyContainer parentContainer;
	private PropertyValueSequence sequence;
	
	public SequencePropertyValueSource( 
			IPropertyContainer parentContainer, PropertyValueSequence list) {
		Check.checkArg(list);
		Check.checkArg(parentContainer);
		this.parentContainer = parentContainer;
		this.sequence = list;
	}
	
	public Object getEditableValue() {
		return this;
	}
	
	public IPropertyValueSource getValueSource() {
		return this;
	}
	
	List getSequence() {
		return sequence;
	}

	public int addLocalizedString(int index, String value) {
		IPropertyValue pv = DmFactory.eINSTANCE.createIPropertyValue();
		pv.setStringValue(parentContainer.createLocalized(value));
		return addPropertyValue(index, pv);
	}

	public int addStringLiteral(int index, String value) {
		IPropertyValue pv = DmFactory.eINSTANCE.createIPropertyValue();
		pv.setStringValue(parentContainer.createLiteral(value));
		return addPropertyValue(index, pv);
	}
	
	public int addComponentReferenceString(int index, String value) {
		IPropertyValue pv = DmFactory.eINSTANCE.createIPropertyValue();
		pv.setStringValue(parentContainer.createReference(value));
		return addPropertyValue(index, pv);		
	}
	
	public IPropertyValueSource addChildValueSource(int index) {
		IPropertyValue containerPV = parentContainer.createPropertyContainerForProperty(Integer.toString(index));
		addPropertyValue(index, containerPV);
		IPropertyValueSource result = containerPV.getCompoundValue().getPropertyValueSource();
		return result;
	}
	
	int addPropertyValue(int index, IPropertyValue value) {
		if (index < 0) {
			throw new IllegalArgumentException();
		}
		if (index > sequence.size()) {
			index = sequence.size();
		}
		sequence.add(index, value);
		return index;
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

	public Object getPropertyValue(Object propertyId) {
		int index = idToIndex(propertyId);
		return getPropertyValue(index);
	}
	
	Object getPropertyValue(int index) {
		Object result = null;
		if (index >= 0 && index < sequence.size()) {
			IPropertyValue value = (IPropertyValue) sequence.get(index);
			if (value != null) {
				if (value.hasStringValue()) {
					result = parentContainer.lookupString(value.getStringValue());
				}
				else if (value.hasCompoundValue()) {
					IPropertyValueSource ps = value.getCompoundValue().getPropertyValueSource(); 
					result = ps;
				}
				else if (value.hasSequenceValue()) {
					List l = value.getSequenceValue();
					result = l;
				}
			}
		}
		return result;
	}

	public boolean canSetPropertyValue(Object propertyId) {
		return hasPropertyValue(propertyId);
	}

	public int numberOfProperties() {
		return sequence.size();
	}

	public Object[] getIds() {
		String[] result = new String[sequence.size()];
		for (int i = 0; i < sequence.size(); i++) 
			result[i] = Integer.toString(i);
		return result;
	}
	
	private Object internalSet(int index, IPropertyValue value, boolean overrideLocalizationState) {
		Object result = null;
		boolean doSet = true;
		IPropertyValue currVal = (IPropertyValue) sequence.get(index);
		if (currVal != null) {
			if (currVal.hasStringValue() && value.hasStringValue() && 
				!overrideLocalizationState) {
				StringValue currStringVal = currVal.getStringValue();
				StringValue newValue = parentContainer.conserveStringType(
						currStringVal, value.getStringValue());
				if (newValue == currStringVal)
					doSet = false;
				else
					value.setStringValue(newValue);
			}
		}
		if (doSet) {
			result = sequence.set(index, value);
		}
		return result;
	}
	
	private void internalClear(int index) {
		IPropertyValue currVal = (IPropertyValue) sequence.get(index);
		if (currVal != null) {
			parentContainer.releasePropertyValue(currVal);
		}

	}

	public void setPropertyValue(Object propertyID, Object newValue) {
		setPropertyValue(propertyID, newValue, false);
	}

	void setPropertyValue(Object propertyId, Object newValue, boolean overrideLocalizationState) {
		Check.checkArg(propertyId != null);
		int index = idToIndex(propertyId);
		if (index >= 0 && index < sequence.size()) {
			if (newValue != null) {
				if (newValue instanceof IPropertySource) {
					// an entry for the compound property must have previously
					// been added, otherwise this is an error
					IPropertyValue pv = (IPropertyValue) sequence.get(index);
					if (pv != null && pv.hasCompoundValue()) {
						pv.getCompoundValue().setFromPropertySource((IPropertySource)newValue);
					}
					else {
						throw new IllegalStateException();
					}
				} else {
					IPropertyValue pv = createPropertyValue(index, newValue);
					internalSet(index, pv, overrideLocalizationState);
				}
			}
			else
				resetPropertyValue(propertyId);
		}
	}
	
	public void resetPropertyValue(final Object propertyId) {
		// Setting to null is not correct. If there's a value all
		// we can do is set to the default for the property type
		int index = idToIndex(propertyId);
		if (index >= 0 && index < sequence.size()) {
			IPropertyValue currValue = (IPropertyValue) sequence.get(index);
		
			PropertyValueSwitch pvs = new PropertyValueSwitch() {
				protected Object handleStringValue(IPropertyValue pv) {
					IPropertyValue newValue = DmFactory.eINSTANCE.createIPropertyValue();
					newValue.setStringValue(parentContainer.createLiteral(null));
					return newValue;
				}
				protected Object handleCompoundValue(IPropertyValue pv) {
					return parentContainer.createPropertyContainerForProperty(propertyId);
				}
				protected Object handleSequenceValue(IPropertyValue pv) {
					return parentContainer.createSequenceForProperty(propertyId);
				}
			};
			
			IPropertyValue newValue = (IPropertyValue) pvs.doSwitch(currValue);
			if (newValue != null)
				internalSet(index, newValue, false);
		}
	}

	public IPropertyValueSource createChildValueSource(Object propertyId, boolean storeValue) {
		IPropertyValue childValue = parentContainer.createPropertyContainerForProperty(propertyId);
		IPropertyValueSource pvs = childValue.getCompoundValue().getPropertyValueSource();
		return pvs;
	}

	public ISequencePropertyValue createChildSequence(Object propertyId, boolean storeValue) {
		// we don't support arrays of arrays, but we do need to generate temporary values for editing
		Check.checkArg(storeValue == false);
		IPropertyValue childValue = parentContainer.createSequenceForProperty(propertyId);
		ISequencePropertyValue pvs = new SequencePropertyValueSource(
				parentContainer, (PropertyValueSequence)childValue.getSequenceValue());
		return pvs;
	}

	public EObject getEObject() {
		return parentContainer.getOwner();
	}

	public IDesignerDataModel getDesignerDataModel() {
		return parentContainer.getDesignerDataModel();
	}
	
	private IPropertyValue createPropertyValue(int index, Object o) {
		IPropertyValue result = null;
		if (o instanceof IPropertyValue) {
			result = (IPropertyValue) o;
		}
		if (o instanceof String) {
			result = DmFactory.eINSTANCE.createIPropertyValue();
			result.setStringValue(parentContainer.createLiteral((String)o));
		}
		else if (o instanceof StringValue) {
			result = DmFactory.eINSTANCE.createIPropertyValue();
			result.setStringValue((StringValue)o);
		}
		else if (o instanceof ISequencePropertyValue) {
			result = parentContainer.createSequenceForProperty(Integer.toString(index));
			for (Iterator iter = ((ISequencePropertyValue)o).iterator(); iter.hasNext();) {
				Object element = iter.next();
				result.getSequenceValue().add(element);
			}
		}
		else if (o instanceof IPropertyValueSource) {
			result = parentContainer.createPropertyContainerForProperty(Integer.toString(index));
			IPropertyValueSource pvs = (IPropertyValueSource) o;
			Object ids[] = pvs.getIds();
			for (int i = 0; i < ids.length; i++) {
				result.getCompoundValue().getProperties().put(ids[i], pvs.getPropertyValue(ids[i]));
			}
		}
	
		if (result == null)
			throw new IllegalArgumentException();
		return result;
	}

	public void clear() {
		for (int i = 0; i < sequence.size(); i++) {
			internalClear(i);
		}
		sequence.clear();
	}

	public boolean isEmpty() {
		return sequence.isEmpty();
	}

	public Iterator iterator() {
		return new SeqListIterator();
	}
	
	public void remove(int index) {
		internalClear(index);
		sequence.remove(index);
	}
	
	public void move(int index, int newIndex) {
		Object obj = sequence.remove(index);
		sequence.add(newIndex, obj);
	}

	public int size() {
		return sequence.size();
	}

	public Object get(int index) {
		return getPropertyValue(index);
	}
	
	class SeqListIterator implements Iterator {
		ListIterator iter = sequence.listIterator();
		
		SeqListIterator() {
			iter = sequence.listIterator();
		}
		
		SeqListIterator(int index) {
			iter = sequence.listIterator(index);
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		public boolean hasNext() {
			return iter.hasNext();
		}
		public Object next() {
			int nextIndex = iter.nextIndex();
			iter.next();
			return getPropertyValue(nextIndex);
		}
	}

	public String getLocalizablePropertyKey(Object propertyId) {
		String result = null;
		int index = idToIndex(propertyId);
		if (index >= 0 && index < sequence.size()) {
			IPropertyValue value = (IPropertyValue) sequence.get(index);
			if (value != null) {
				if (value.hasStringValue() && value.getStringValue().isKey()) {
					result = value.getStringValue().getValue();
				}
			}
		}
		return result;
	}

	public void setLocalizableStringPropertyValue(Object propertyId, String newValue, boolean overrideCurrentState) {
		setPropertyValue(propertyId, parentContainer.createLocalized(newValue), overrideCurrentState);
	}

	public String getStringPropertyValueSymbol(Object propertyId) {
		String result = null;
		int index = idToIndex(propertyId);
		if (index >= 0 && index < sequence.size()) {
			IPropertyValue currValue = (IPropertyValue) sequence.get(index);
			if (currValue.hasStringValue() && currValue.getStringValue().isKey()) {
				result = currValue.getStringValue().getValue();
			}
		}
		return result;
	}

	public void setReferencePropertyValue(Object propertyId, String newValue) {
		setPropertyValue(propertyId, parentContainer.createReference(newValue));
	}

	public boolean isReferenceProperty(Object propertyId) {
		boolean result = false;
		int index = idToIndex(propertyId);
		if (index >= 0 && index < sequence.size()) {
			IPropertyValue currValue = (IPropertyValue) sequence.get(index);
			if (currValue.hasStringValue()) {
				result = currValue.getStringValue().isReference();
			}
		}
		return result;
	}

	public UndoValue createUndoValue() {
		UndoValue result = new PropertyContainerCopier(parentContainer);
		return result;
	}

	public void setFromUndoValue(UndoValue value, boolean preserveLocalizedStringKeys) {
		Check.checkArg(value instanceof PropertyContainerCopier);
		PropertyContainerCopier copier = (PropertyContainerCopier) value;
		copier.undo(true, preserveLocalizedStringKeys);
	}
	
	public String getPropertyPath() {
		return sequence.getPath();
	}

	public String getPropertyPath(Object propertyId) {
		String result;
		String path = sequence.getPath();
		if (path != null) {
			if (propertyId != null)
				result = path + "." + propertyId.toString();
			else
				result = path;
		}
		else {
			result = propertyId.toString();
		}
		return result;
	}

	public String getElementPath(int index) {
		return getPropertyPath(Integer.toString(index));
	}
	
	public IPropertyValueSource lookupReferencePropertyValueSource(Object id) {
		String referenceName = (String) getPropertyValue(id);
		if (referenceName != null && referenceName.length() > 0) {
			EObject reference = getDesignerDataModel().findByNameProperty(referenceName);
			if (reference != null) {
				return ((INode) reference).getProperties().getPropertyValueSource();
			}
		}
		return null;
	}
}
