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

package com.nokia.sdt.component.property;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * An adapter interface for components providing property
 * support. 
 */
public interface IPropertyDescriptorProvider {

	/**
	 * Returns a list of property descriptors
	 * for an object.
	 */
	IPropertyDescriptor[] getPropertyDescriptors(IPropertyValueSource valueSource);
	
	/**
	 * Returns an appropriate default value for the property,
	 * or null.
	 */
	Object getDefaultValue(Object propertyId);
}
