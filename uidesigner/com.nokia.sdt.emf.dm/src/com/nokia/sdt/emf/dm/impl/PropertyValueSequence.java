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

import com.nokia.sdt.emf.dm.IPropertyValue;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;

	/**
	 * Subclass of EObjectContainmentEList used to
	 * stored array property values. Provides changed
	 * notification
	 */
public class PropertyValueSequence extends EObjectContainmentEList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IPropertyContainerImpl propertyContainer;
	private Object id; // id for this sequence
	private String path; // full property path to this sequence

	public PropertyValueSequence(Class dataClass, IPropertyValueImpl owner, int featureID, IPropertyContainerImpl container) {
		super(dataClass, owner, featureID);
		this.propertyContainer = container;
	}	
	
	public Object getID() {
		return id;
	}
	
	void setID(Object id) {
		this.id = id;
	}
	
	public String getPath() {
		return path;
	}
	
	void setPath(String path) {
		this.path = path;
	}

	@Override
	protected void didChange() {
		super.didChange();
		IPropertyValueImpl owner = (IPropertyValueImpl) this.owner;
		if (propertyContainer != null) {
			String propertyID = propertyContainer.findPropertyIDForValue(owner);
			if (propertyID != null) {
				propertyContainer.propertyChanged(propertyID, owner, owner);
			}
		}
		
	}

	@Override
	protected void didAdd(int index, Object newObject) {
		super.didAdd(index, newObject);
		IPropertyValue pv = (IPropertyValue) newObject;
		if (pv.hasCompoundValue()) {
			IPropertyContainerImpl cv = (IPropertyContainerImpl) pv.getCompoundValue();
			cv.setPath(path + "." + Integer.toString(index));
		}
	}
	
}
