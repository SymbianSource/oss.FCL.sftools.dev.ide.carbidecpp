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

package com.nokia.carbide.cpp.uiq.component.layoutManager;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.sdt.component.property.ICompoundPropertyValueConverter;

public class LayoutManagerConverter implements ICompoundPropertyValueConverter {
	
	public Object getEditableValue(EObject object, IPropertySource propertyValue) {
		
		return null;
	}
	
	public void applyEditableValue(EObject object, Object editableValue,
			IPropertySource propertySource) {
		
	}
}
