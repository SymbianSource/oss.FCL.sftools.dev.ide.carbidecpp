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
package com.nokia.sdt.symbian.dm;

import com.nokia.sdt.component.property.IPropertyInformation;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.emf.dm.impl.INodeImpl;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySource2;

	/**
	 * Provides all the basic adapters for component instances
	 *
	 */
public class ComponentAdapterFactory extends AdapterFactoryImpl {

	protected Adapter createAdapter(Notifier target, Object type) {
		Adapter result = null;
		if (target instanceof INodeImpl) {
			result = new ComponentInstanceAdapter();
		}
		return result;
	}

	public boolean isFactoryForType(Object type) {
		return type.equals(IComponentInstance.class) 
			|| type.equals(IPropertySource.class)
			|| type.equals(IPropertySource2.class)
			|| type.equals(IPropertyInformation.class);
	}

}
