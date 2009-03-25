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

package com.nokia.sdt.component.symbian.properties;

import com.nokia.sdt.component.ITypeDescriptor;
import com.nokia.sdt.component.property.IPropertyValueSource;
import com.nokia.sdt.emf.component.EnumPropertyType;
import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;


public class EnumPropertyDescriptor extends AbstractPropertyDescriptor {

	EnumPropertyDescriptor(EnumPropertyType propertyType, 
			ITypeDescriptor typeDescriptor,
			IPropertyValueSource valueSource,
			ILocalizedStrings strings, boolean forceReadOnly) {
		super(propertyType, typeDescriptor, valueSource, strings, forceReadOnly);
		
		// establish initial value for the default value initializer
		EnumPropertyType ept = getPropertyType();
		ITypeDescriptor td = getTypeDescriptor();
		Object initializer = ept.getDefault();
		if (initializer == null) {
			initializer = td.defaultValue(getValueSource(), ept.getName());
		}
		setDefaultValueInitializer(initializer);
	}

	protected EnumPropertyType getPropertyType() {
		return (EnumPropertyType) super.getPropertyType();
	}
	
	@Override
	public Object getDefaultValue() {
		return getDefaultValueInitializer();
	}
}

