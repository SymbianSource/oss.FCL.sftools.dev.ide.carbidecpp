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
package com.nokia.sdt.symbian.ui.valueConverter;

import com.nokia.sdt.component.property.ICompoundPropertyValueConverter;
import com.nokia.sdt.symbian.ui.UIPlugin;
import com.nokia.sdt.symbian.ui.editors.IPFieldEditorFactory.IPField;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.ParseException;

public class IPFieldValueConverter implements ICompoundPropertyValueConverter {

	public Object getEditableValue(EObject object, IPropertySource ps) {
		Integer first = (Integer) ps.getPropertyValue("firstField"); //$NON-NLS-1$
		Integer second = (Integer) ps.getPropertyValue("secondField"); //$NON-NLS-1$
		Integer third = (Integer) ps.getPropertyValue("thirdField"); //$NON-NLS-1$
		Integer fourth = (Integer) ps.getPropertyValue("fourthField"); //$NON-NLS-1$

		IPField field = new IPField();
		int[] values = new int[4];
		values[0] = first.intValue();
		values[1] = second.intValue();
		values[2] = third.intValue();
		values[3] = fourth.intValue();
		field.setFromValues(values);
		return field;
	}

	public void applyEditableValue(EObject object, Object editableValue, IPropertySource ps) {
		IPField field = null;
		if (editableValue instanceof IPField) {
			field = (IPField) editableValue;
		}
		else if (editableValue != null) {
			field = new IPField();
			String error = field.setFromString(editableValue.toString());
			if (error != null)
				UIPlugin.log(new ParseException(error, 0));
		}
		if (field == null) {
			throw new IllegalArgumentException();
		}
		
		ps.setPropertyValue("firstField", field.getField(0)); //$NON-NLS-1$
		ps.setPropertyValue("secondField", field.getField(1)); //$NON-NLS-1$
		ps.setPropertyValue("thirdField", field.getField(2)); //$NON-NLS-1$
		ps.setPropertyValue("fourthField", field.getField(3)); //$NON-NLS-1$
	}

}
