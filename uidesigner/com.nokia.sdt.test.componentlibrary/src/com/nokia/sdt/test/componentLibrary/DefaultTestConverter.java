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
package com.nokia.sdt.test.componentLibrary;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.sdt.component.property.ICompoundPropertyValueConverter;

	// a test converter, used with compoundWidthDefault.component
	// doesn't do anything logical, it sets the int1 property to
	// 111 and the string1 property to the text of the editable value.
	// other properties are clear.

public class DefaultTestConverter implements ICompoundPropertyValueConverter {

	public Object getEditableValue(EObject object, IPropertySource propertyValue) {
		Object string1Val = propertyValue.getPropertyValue("string1");
		return string1Val;
	}

	public void applyEditableValue(EObject object, Object editableValue,
			IPropertySource propertySource) {
		if (editableValue == null)
			return;
		propertySource.setPropertyValue("int1", Integer.valueOf(111));
		propertySource.setPropertyValue("string1", editableValue.toString());
		propertySource.setPropertyValue("int2", null);
		propertySource.setPropertyValue("string2", null);
	}

}
