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
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.List;

class PropertyValueSource implements IPropertyValueSource {
	
	private IPropertyContainer properties;
	
	PropertyValueSource(IPropertyContainer properties) {
		Check.checkArg(properties);
		this.properties = properties;
	}

	public boolean hasPropertyValue(Object propertyId) {
		return properties.getProperties().containsKey(propertyId);
	}

	public Object getPropertyValue(Object propertyId) {
		Object result = null;
		IPropertyValue value = (IPropertyValue) properties.getProperties().get(propertyId);
		if (value != null) {
			if (value.hasStringValue()) {
				result = properties.lookupString(value.getStringValue());
			}
			else if (value.hasCompoundValue()) {
				IPropertyValueSource ps = value.getCompoundValue().getPropertyValueSource(); 
				result = ps;
			}
			else if (value.hasSequenceValue()) {
				ISequencePropertyValue spv = new SequencePropertyValueSource(
						properties, (PropertyValueSequence)value.getSequenceValue());
				result = spv;
			}
		}
		return result;
	}

	public boolean canSetPropertyValue(Object propertyId) {
		return true;
	}
	
	private void setFromSequencePropertySource(String propertyId, SequencePropertyValueSource spvs) {
		properties.set(propertyId, spvs.getSequence());
	}

	public void setPropertyValue(Object propertyId, Object newValue) {
		Check.checkArg(propertyId != null);
		if (newValue != null) {
			String id = propertyId.toString();
			if (newValue instanceof String) {
				properties.set(id, properties.createLiteral((String)newValue), false);
			}
			else if (newValue instanceof StringValue) {
				properties.set(id, (StringValue)newValue, false);
			}
			else if (newValue instanceof SequencePropertyValueSource) {
				SequencePropertyValueSource spvs = (SequencePropertyValueSource) newValue;
				setFromSequencePropertySource(id, spvs);
			}
			else if (newValue instanceof IPropertySource) {
				// If possible, set the current property container from the given
				// property source. Otherwise, create a new container and init from
				// the property source. This inhibits event notification making it look
				// like the property is deleted, and helps preserve string variant types.
				IPropertyValue pv = (IPropertyValue) properties.getProperties().get(id);
				if (pv != null && pv.hasCompoundValue()) {
					pv.getCompoundValue().setFromPropertySource((IPropertySource)newValue);
				}
				else {
					pv = properties.createPropertyContainerForProperty(id);
					pv.getCompoundValue().setFromPropertySource((IPropertySource)newValue);
					properties.getProperties().put(id, pv);
				}
			}
			else if (newValue instanceof List) {
				properties.set(id, (List)newValue);
			}			
		}
		else
			resetPropertyValue(propertyId);
	}

	public void resetPropertyValue(Object propertyId) {
		properties.reset(propertyId.toString());
	}

	public IPropertyValueSource createChildValueSource(Object propertyId, boolean storeValue) {
		Check.checkArg(!storeValue || propertyId != null);
		// This method creates an empty compound property and assigns it as 
		// the current property value. Since semantically no values are changing
		// we inhibit notification around this operation
		PropertyMap propertyMap = (PropertyMap) properties.getProperties();
		if (propertyMap.get(propertyId) != null) {
			// should not create a default value with an existing value in place
			throw new IllegalStateException();
		}
		
		IPropertyValue childValue = properties.createPropertyContainerForProperty(propertyId);
		if (storeValue) {
			boolean savedDeliver = propertyMap.setDeliver(false);
			try {
				propertyMap.put(propertyId, childValue);
			}
			finally {
				propertyMap.setDeliver(savedDeliver);
			}
		}
		IPropertyValueSource pvs = childValue.getCompoundValue().getPropertyValueSource();
		return pvs;
	}
	
	public ISequencePropertyValue createChildSequence(Object propertyId, boolean storeValue) {
		Check.checkArg(!storeValue || propertyId != null);
		
		PropertyMap propertyMap = (PropertyMap) properties.getProperties();	
		IPropertyValue childValue = properties.createSequenceForProperty(propertyId);
		if (storeValue) {
			boolean savedDeliver = propertyMap.setDeliver(false);
			try {
				propertyMap.put(propertyId, childValue);
			}
			finally {
				propertyMap.setDeliver(savedDeliver);
			}
		}
		ISequencePropertyValue pvs = new SequencePropertyValueSource(
					properties, (PropertyValueSequence)childValue.getSequenceValue());
		return pvs;
	}

	public EObject getEObject() {
		return properties.getOwner();
	}

	public IDesignerDataModel getDesignerDataModel() {
		return properties.getDesignerDataModel();
	}
	
	public String getPropertyPath() {
		return properties.getPropertyPath();
	}
	
	public String getPropertyPath(Object propertyId) {
		Check.checkArg(propertyId);
		String result;
		String containerPath = properties.getPropertyPath();
		if (containerPath != null) {
			result = containerPath + "." + propertyId.toString();
		} else {
			result = propertyId.toString();
		}
		return result;
	}

	public int numberOfProperties() {
		return properties.getProperties().size();
	}

	public Object[] getIds() {
		EMap map = properties.getProperties();
		return map.keySet().toArray(new String[map.size()]);
	}

	public String getStringPropertyValueSymbol(Object propertyId) {
		String result = null;
		IPropertyValue pv = (IPropertyValue) properties.getProperties().get(propertyId);
		if (pv != null) {
			if (pv.hasStringValue()) {
				StringValue sv = pv.getStringValue();
				if (sv.isKey())
					result = sv.getValue();
			}
		}
		return result;
	}

	public void setLocalizableStringPropertyValue(Object propertyId, String newValue, boolean overrideCurrentState) {
		properties.set(propertyId.toString(), properties.createLocalized(newValue), overrideCurrentState);
	}

	public void setReferencePropertyValue(Object propertyId, String newValue) {
		properties.set(propertyId.toString(), properties.createReference(newValue), false);
	}

	public boolean isReferenceProperty(Object propertyId) {
		boolean result = false;
		IPropertyValue pv = (IPropertyValue) properties.getProperties().get(propertyId);
		if (pv != null) {
			if (pv.hasStringValue()) {
				result = pv.getStringValue().isReference();
			}
		}
		return result;
	}

	public UndoValue createUndoValue() {
		UndoValue result = new PropertyContainerCopier(properties);
		return result;
	}

	public void setFromUndoValue(UndoValue value, boolean preserveLocalizedStringKeys) {
		Check.checkArg(value instanceof PropertyContainerCopier);
		PropertyContainerCopier copier = (PropertyContainerCopier) value;
		copier.undo(true, preserveLocalizedStringKeys);
	}
	
	public IPropertyValueSource lookupReferencePropertyValueSource(Object id) {
		String referenceName = (String) getPropertyValue(id);
		if (referenceName != null && referenceName.length() > 0) {
			IDesignerDataModel model = getDesignerDataModel();
			if (model != null) {
				EObject reference = model.findByNameProperty(referenceName);
				if (reference != null) {
					return ((INode) reference).getProperties().getPropertyValueSource();
				}
			}
		}
		return null;
	}
}
