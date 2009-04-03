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
/**
 * 
 */
package com.nokia.sdt.component.symbian.properties;

import com.nokia.sdt.component.property.IPropertyValueSource;
import com.nokia.sdt.datamodel.adapter.IComponentValidator;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.jface.viewers.ICellEditorValidator;

	/**
	 * This class provides a ICellEditorValidator that composes
	 * the behavior of an existing ICellEditorValidator with
	 * a component's IComponentValidator (if present).
	 * <p>
	 * This class also provides a method of working around a
	 * JFace problem which passes messages through MessageFormat
	 * again, losing the single quotes.
	 *
	 */
class ComposedCellEditorValidator implements ICellEditorValidator {
	
	private AbstractPropertyDescriptor propertyDescriptor;
	private IComponentValidator componentValidator;
	private ICellEditorValidator defaultValidator;
	
	ComposedCellEditorValidator(
				AbstractPropertyDescriptor propertyDescriptor,
				IComponentValidator componentValidator,
				ICellEditorValidator defaultValidator) {
		Check.checkArg(propertyDescriptor);
		this.propertyDescriptor = propertyDescriptor;
		this.componentValidator = componentValidator;
		this.defaultValidator = defaultValidator;
	}

	public String isValid(Object value) {
		String result = null;
		if (defaultValidator != null) {
			result = defaultValidator.isValid(value);
		}
		if (result == null && componentValidator != null) {
			IPropertyValueSource valueSource = propertyDescriptor.getValueSource();
			Object propertyId = propertyDescriptor.getId();
			value = propertyDescriptor.getTypeDescriptor().editableValueToPropertyValue(
					value, valueSource);
			
			result = componentValidator.queryPropertyChange(
					valueSource.getEObject(), valueSource.getPropertyPath(propertyId), value);
		}
		
		// For bug 1600, change single quotes to something else,
		// since JFace tries to send it to MessageFormat#format,
		// which removes them, and Eclipse property sheet UI 
		// uses it as a raw string.
		if (result != null && result.length() > 0) {
			// replace single quote with Unicode right single quote character
			result = result.replaceAll("'", "\u2019"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return result;
	}
	
}