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


package com.nokia.sdt.datamodel.adapter;


/**
 * 
 * An interface to allow components to reconcile the display value with the actual value of a property
 */
public interface IReconcileProperty extends IModelAdapter {
	
	/**
	 * Generates the display value (editable value) for a given property value of some property type.
	 * This is most useful when the propertyValue is an IPropertySource
	 * and the display value is the summary (parent property's) editable value.
	 * 
	 * @param propertyTypeName
	 * @param propertyValue
	 * @return the display value
	 */
	Object createDisplayValue(String propertyTypeName, Object propertyValue);
	
	/**
	 * @return true if the display value can be applied, otherwise no cell editor is shown
	 */
	boolean isDisplayValueEditable(String propertyTypeName);
	
	/**
	 * Applies the display value for some property type to the property value.
	 * This is most useful when the propertyValue is an IPropertySource
	 * and the display value is the summary (parent property's) editable value.
	 * 
	 * @param propertyTypeName
	 * @param displayValue
	 * @param propertyValue
	 */
	void applyDisplayValue(String propertyTypeName, Object displayValue, Object propertyValue);
	
}
