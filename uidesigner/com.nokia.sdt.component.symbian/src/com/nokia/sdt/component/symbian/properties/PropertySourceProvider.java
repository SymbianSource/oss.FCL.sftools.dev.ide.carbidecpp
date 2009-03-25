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

import com.nokia.sdt.component.property.IPropertyValueSource;
import com.nokia.sdt.component.symbian.Component;
import com.nokia.sdt.component.symbian.ComponentProvider;

import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

/**
 * 
 */
public class PropertySourceProvider implements IPropertySourceProvider {

//	private ComponentProvider componentProvider;
	private Component component;
	
	public PropertySourceProvider(Component component, ComponentProvider componentProvider) {
	//	this.componentProvider = componentProvider;
		this.component = component;
	}

	public IPropertySource getPropertySource(Object object) {
		IPropertySource result = null;
		if (object instanceof IPropertyValueSource)
		{
			result = new ComponentPropertySource(component, (IPropertyValueSource)object,
							component.getLocalizedStrings());
		}
		return result;
	}
}
