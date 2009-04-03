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
package com.nokia.sdt.component.property;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

	/**
	 * IEditableValueConverter acts as a bridge between compound
	 * property values and editable values. Given a compound
	 * property it supplies an editable value, and given an editable
	 * value it stores it back to the property
	 */
public interface ICompoundPropertyValueConverter {
	
	/**
	 * Create an editable value, suitable for passing
	 * as input to a cell editor.
	 * @param object the source instance, or null
	 * @param propertyValue may be a simple value, e.g. String,
	 * or, for a compound property, an IPropertySource 
	 */
	Object getEditableValue(EObject object, IPropertySource propertyValue);
		
	/**
	 * Applies an editable value back to an IPropertySource. If the
	 * property is an array then this will be an ISequencePropertySource
	 * @param object the source instance, or null
	 * @param editableValue the value returned by a cell editor
	 * @param propertySource the target property. It may be an ISequencePropertySource
	 */
	void applyEditableValue(EObject object, Object editableValue, IPropertySource propertySource);
}
