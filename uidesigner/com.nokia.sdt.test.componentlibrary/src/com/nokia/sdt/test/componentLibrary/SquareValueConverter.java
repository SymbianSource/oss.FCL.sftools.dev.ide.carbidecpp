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

import com.nokia.sdt.component.property.ICompoundPropertyValueConverter;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

public class SquareValueConverter implements ICompoundPropertyValueConverter {

	public Object getEditableValue(EObject object, IPropertySource propertyValue) {
		int left = intProperty(propertyValue, "leftSide");
	
		// this is just a test, there would normally be no reason
		// to store all the sides
		int area = left*left;
		return Integer.valueOf((area));
	}
	
	private int intProperty(IPropertySource ps, Object id) {
		int value = 0;
		try {
			Object v = ps.getPropertyValue(id);
			if (v != null) {
				value = Integer.parseInt(v.toString());
			}
		}
		catch (NumberFormatException x) {
		}
		return value;
	}

	public void applyEditableValue(EObject object, Object editableValue,
			IPropertySource propertySource) {
		if (editableValue instanceof Number) {
			double area = ((Number)editableValue).doubleValue();
			double side = Math.sqrt(area);
			String value = Integer.toString((int)side);
			propertySource.setPropertyValue("leftSide", value);
			propertySource.setPropertyValue("topSide", value);
			propertySource.setPropertyValue("rightSide", value);
			propertySource.setPropertyValue("bottomSide", value);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

}
