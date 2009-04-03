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
package com.nokia.sdt.emf.component.util;

import com.nokia.sdt.emf.component.*;

/**
 * Patterned after the EMF-generated switch class, PropertySwitch
 * is intented just for handling property types to simplify handling
 * the different cases and avoid unhandled types as they're added
 */
public abstract class PropertySwitch {

	public PropertySwitch() {
	}

	public Object doSwitch(AbstractPropertyType apt) {
		Object result = null;
		switch (apt.eClass().getClassifierID()) {
		case ComponentPackage.ARRAY_PROPERTY_TYPE:
			result = doArrayProperty((ArrayPropertyType)apt);
			break;
			
		case ComponentPackage.ENUM_PROPERTY_TYPE:
			result = doEnumProperty((EnumPropertyType)apt);
			break;
			
		case ComponentPackage.COMPOUND_PROPERTY_TYPE:
			result = doCompoundProperty((CompoundPropertyType)apt);
			break;
			
		case ComponentPackage.SIMPLE_PROPERTY_TYPE:
			result = doSimpleProperty((SimplePropertyType)apt);
			break;
			
		case ComponentPackage.COMPONENT_REFERENCE_PROPERTY_TYPE:
			result = doComponentReferenceProperty((ComponentReferencePropertyType)apt);
			break;
			
		default:
			unhandledType(apt);
			break;
		}
		return result;
	}
	
	protected Object unhandledType(AbstractPropertyType type) {
		throw new IllegalStateException("unhandled property type: "+ type.toString());
	}

	protected abstract Object doSimpleProperty(SimplePropertyType simpleProperty);
	protected abstract Object doCompoundProperty(CompoundPropertyType compoundProperty);
	protected abstract Object doEnumProperty(EnumPropertyType enumProperty);
	protected abstract Object doArrayProperty(ArrayPropertyType arrayProperty);
	protected abstract Object doComponentReferenceProperty(ComponentReferencePropertyType type);

}
