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

import com.nokia.sdt.emf.dm.IPropertyContainer;
import com.nokia.sdt.emf.dm.IPropertyValue;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreEMap;

public class PropertyMap extends EcoreEMap {
	
	private static final long serialVersionUID = 3544676191450444857L;
	private IInternalPropertyChangeListener propertyListener;
	private boolean deliverEvents = true;
	private IPropertyContainer propertyContainer;

	PropertyMap(IPropertyContainer propertyContainer, 
			EClass entryEClass, Class entryClass, InternalEObject owner, int featureID) {
		super(entryEClass, entryClass, owner, featureID);
		Check.checkArg(propertyContainer);
		this.propertyContainer = propertyContainer;
	}
	
	boolean getDeliver() {
		return deliverEvents;
	}
	
	protected void ensureEntryDataExists() {
		boolean savedDeliver = deliverEvents;
		deliverEvents = false;
		try {
			super.ensureEntryDataExists();
		}finally{
			deliverEvents = savedDeliver;
		}
	}

	boolean setDeliver(boolean deliver) {
		boolean result = deliverEvents;
		deliverEvents = deliver;
		return result;
	}
	
	void setPropertyChangeListener(IInternalPropertyChangeListener listener) {
		this.propertyListener = listener;
	}
		
	public Object put(Object key, Object value) {
		// the map allows null keys, but it makes no sense for properties
		Check.checkArg(key != null);
		
		if (value != null) {
			Check.checkArg(value instanceof IPropertyValue);
			IPropertyValue pv = (IPropertyValue)value;
			Check.checkArg(pv.getValue() != null);
		}
		// This is an internal invariant check. Validation of
		// user entered values and error report should have
		// happened before we got here.
		if (shouldDeliver() && !propertyListener.queryPropertyChange(key, value)) {
			throw new IllegalStateException();
		}
		
		return super.put(key, value);
	}

	private boolean shouldDeliver() {
		return propertyListener != null && deliverEvents;
	}

	protected void didAdd(Entry entry) {
		super.didAdd(entry);
		// a property went from default to set
		if (shouldDeliver()) {
			EStringToIPropertyValueMapEntryImpl e = (EStringToIPropertyValueMapEntryImpl) entry;
			propertyListener.propertyChanged(entry.getKey(), null, e.getTypedValue());
		}
	}

	protected void didClear(BasicEList[] oldEntryData) {
		super.didClear(oldEntryData);
	}
	
	void valueRemoved(Object value) {
		if (value != null) {
			propertyContainer.releasePropertyValue((IPropertyValue)value);
		}
	}

	protected void didModify(Entry entry, Object oldValue) {
		super.didModify(entry, oldValue);		
		// a property value was changed. Filter out changes to 
		// the same value for the internal valueRemoved handling,
		// but fire event in all cases
		if (!ObjectUtils.equals(entry.getValue(), oldValue)) {
			valueRemoved(oldValue);
		}
		if (shouldDeliver()) {
			EStringToIPropertyValueMapEntryImpl e = (EStringToIPropertyValueMapEntryImpl) entry;
			propertyListener.propertyChanged(entry.getKey(), (IPropertyValue) oldValue, e.getTypedValue());
		}
	}

	protected void didRemove(Entry entry) {
		super.didRemove(entry);
		// a property went from set to default
		valueRemoved(entry.getValue());
		if (shouldDeliver()) {
			EStringToIPropertyValueMapEntryImpl e = (EStringToIPropertyValueMapEntryImpl) entry;
			propertyListener.propertyChanged(entry.getKey(), e.getTypedValue(), null);
		}
	}
}
