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
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.emf.ecore.EObject;

import java.util.HashMap;


class MockPropertyValueSource implements IPropertyValueSource {

	private HashMap values = new HashMap();
	private Object propertyId;
	private String parentPath;
	
	public MockPropertyValueSource(Object propertyId) {
		this.propertyId = propertyId;
	}

	public MockPropertyValueSource(String parentPath) {
		this.parentPath = TextUtils.isEmpty(parentPath)? null : parentPath;
	}
 
	public boolean hasPropertyValue(Object propertyId) {
		return values.containsKey(propertyId);
	}

	public Object getPropertyValue(Object propertyId) {
		return values.get(propertyId);
	}

	public boolean canSetPropertyValue(Object propertyId) {
		return true;
	}

	public void setPropertyValue(Object propertyId, Object newValue) {
		values.put(propertyId, newValue);
	}

	public void resetPropertyValue(Object propertyId) {
		values.remove(propertyId);
	}

	public IPropertyValueSource createChildValueSource(Object propertyId, boolean storeValue) {
		return new MockPropertyValueSource(propertyId);
	}

	public EObject getEObject() {
		return null;
	}

	public IDesignerDataModel getDesignerDataModel() {
		return null;
	}

	public int numberOfProperties() {
		return values.size();
	}

	public Object[] getIds() {
		return values.keySet().toArray();
	}

	public ISequencePropertyValue createChildSequence(Object propertyId, boolean storeValue) {
		return new MockSequencePropertyValue(this, propertyId);
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
		if (parentPath != null) {
			result = parentPath + "." + propertyId;
		} else {
			result = propertyId.toString();
		}
		return result;
	}

	public String getPropertyPath(Object propertyId) {
		return getPropertyPath() + "." + propertyId;
	}
	
	public IPropertyValueSource lookupReferencePropertyValueSource(Object id) {
		throw new UnsupportedOperationException();
	}
}
