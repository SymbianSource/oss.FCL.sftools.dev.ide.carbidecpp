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


package com.nokia.sdt.component.symbian.implementations;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IComponentImplementations;
import com.nokia.sdt.component.symbian.IFacetContainer;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.EStructuralFeature;

public class ComponentImplementationsAdapterFactory implements IAdapterFactory {
	
	private Plugin plugin;
	private EStructuralFeature implementationsFeature;
	private Class adapterList[] = {IComponentImplementations.class};
	
	/**
	 * @param implementationsFeature the structural feature whose type is ImplementationsType
	 */
	public ComponentImplementationsAdapterFactory(Plugin plugin, EStructuralFeature implementationsFeature) {
		Check.checkArg(implementationsFeature);
		this.plugin = plugin;
		this.implementationsFeature = implementationsFeature;
	}

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		Object result = null;
		if (adaptableObject instanceof IFacetContainer &&
			adaptableObject instanceof IComponent &&
			IComponentImplementations.class.equals(adapterType)) {
			result = new ComponentImplementationsAdapter(plugin, 
						(IComponent) adaptableObject, implementationsFeature);
		}
		return result;
    }

    public Class[] getAdapterList() {
        return adapterList;
    }
}
