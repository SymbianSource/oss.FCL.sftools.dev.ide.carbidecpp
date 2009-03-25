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

import com.nokia.sdt.datamodel.adapter.IComponentEventDescriptorProvider;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.EObject;

/**
 * This factory creates component event adapters.
 * 
 *
 */
public class ComponentEventDescriptorProviderFactory implements IAdapterFactory {
	
	private Class adapterList[] = { IComponentEventDescriptorProvider.class };
	
	/**
	 */
	public ComponentEventDescriptorProviderFactory(Plugin plugin) {
	}

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		Object result = null;
		if (adaptableObject instanceof EObject &&
				IComponentEventDescriptorProvider.class.equals(adapterType)) {
			result = new ComponentEventDescriptorProvider((EObject) adaptableObject);
		}
		return result;
	}

	public Class[] getAdapterList() {
		return adapterList;
	}
}
