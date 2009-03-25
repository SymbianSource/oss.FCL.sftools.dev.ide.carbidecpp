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
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.emf.component.PropertyDataType;
import com.nokia.sdt.emf.component.SimplePropertyType;
import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;

/**
 * IPropertyDescriptor implementation for simple property types
 */
public class SimplePropertyDescriptor extends AbstractPropertyDescriptor {

	SimplePropertyDescriptor(SimplePropertyType propertyType, 
			ITypeDescriptor typeDescriptor,
			IPropertyValueSource valueSource,
			ILocalizedStrings strings, boolean forceReadOnly) {
		super(propertyType, typeDescriptor, valueSource, strings, forceReadOnly);

		// establish initial value for the default value initializer
		SimplePropertyType spt = getPropertyType();
		ITypeDescriptor td = getTypeDescriptor();
		Object initializer = spt.getDefault();
		if (initializer == null) {
			initializer = td.defaultValue(getValueSource(), spt.getName());
		}
		if (initializer != null) {
			Object newInitializer = td.valueOf(initializer.toString());
			if (newInitializer == null) {
				ComponentSystemPlugin.log(new IllegalArgumentException("The default value for property " + propertyType.getName() + " is not valid: " + initializer));
			} else {
				initializer = newInitializer;
			}
		}
		setDefaultValueInitializer(initializer);
	}

	protected SimplePropertyType getPropertyType() {
		return (SimplePropertyType) super.getPropertyType();
	}

	@Override
	public Object getDefaultValue() {
		return getDefaultValueInitializer();
	}

	@Override
	public boolean isPropertyLocalizable() {
		return getPropertyType().getType().getValue() == PropertyDataType.LOCALIZED_STRING;
	}
}

