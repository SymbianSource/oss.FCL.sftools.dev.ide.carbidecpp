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

package com.nokia.sdt.component.symbian.properties;

import com.nokia.sdt.component.ITypeDescriptor;
import com.nokia.sdt.component.property.IPropertyValueSource;
import com.nokia.sdt.emf.component.ArrayPropertyType;
import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;

public class ArrayPropertyDescriptor extends AbstractPropertyDescriptor {
	
	ArrayPropertyDescriptor(ArrayPropertyType propertyType, 
			ITypeDescriptor arrayTypeDescriptor,
			ITypeDescriptor elementTypeDescriptor,
			IPropertyValueSource valueSource,
			ILocalizedStrings strings, boolean forceReadOnly) {
		super(propertyType, arrayTypeDescriptor, valueSource, strings, forceReadOnly);
	}
	
	protected ArrayPropertyType getPropertyType() {
		return (ArrayPropertyType) super.getPropertyType();
	}

	@Override
	public Object getDefaultValue() {
		return getTypeDescriptor().defaultValue(getValueSource(), 
				getPropertyType().getName());
	}
}
