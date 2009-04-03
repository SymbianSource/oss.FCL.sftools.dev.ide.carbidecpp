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
package com.nokia.sdt.emf.dm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * @model
 */
public interface IPropertyValue extends EObject{

	/**
	 * @model changeable="false" volatile="true" transient="true"
	 */
	Object getValue();
	
	/**
	 * @model
	 */
	StringValue getStringValue();
	
	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IPropertyValue#getStringValue <em>String Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>String Value</em>' attribute.
	 * @see #getStringValue()
	 * @generated
	 */
	void setStringValue(StringValue value);

	boolean hasStringValue();
	
	/**
	 * @model containment="true" changeable="false"
	 */
	IPropertyContainer getCompoundValue();
	
	boolean hasCompoundValue();
	
	// NOTE: the EList returned is always of type PropertyValueSequence
	/**
	 * @model type="IPropertyValue" containment="true"
	 */
	EList getSequenceValue();
	
	boolean hasSequenceValue();
}
