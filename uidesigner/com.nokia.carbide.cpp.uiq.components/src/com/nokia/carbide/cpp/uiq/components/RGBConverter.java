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

package com.nokia.carbide.cpp.uiq.components;


import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.sdt.component.property.ICompoundPropertyValueConverter;

public class RGBConverter implements ICompoundPropertyValueConverter {
	
	public Object getEditableValue(EObject object, IPropertySource propertyValue) {
		
		return propertyValue.getPropertyValue("red")+","+propertyValue.getPropertyValue("green")+
				","+propertyValue.getPropertyValue("blue");
	}
	
	public void applyEditableValue(EObject object, Object editableValue,IPropertySource propertysource) {
		if(editableValue != "" && editableValue != null){
			String[] components = editableValue.toString().split(",");
			propertysource.setPropertyValue("red", components[0]);
			propertysource.setPropertyValue("green", components[1]);
			propertysource.setPropertyValue("blue", components[2]);	
		}
	}
}







