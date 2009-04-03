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
package com.nokia.sdt.component.symbian.attributes;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.symbian.IFacetContainer;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.EStructuralFeature;

public class AttributeAdapterFactory implements IAdapterFactory {
	
	private Plugin plugin;
	private EStructuralFeature attributesFeature;
	private Class adapterList[] = {IAttributes.class};
	
	/**
	 * Construct the adapter factory with the metainformation for the
	 * attribute list. This allows this class to get the attribute
	 * without relying on the specific class containing them. That
	 * allows the component facet to be more easily re-used.
	 * @param attributesFeature the structural feature whose type is AttributesType
	 */
	public AttributeAdapterFactory(Plugin plugin, EStructuralFeature attributesFeature) {
	    //Check.checkArg(plugin); // EJS: not needed
		Check.checkArg(attributesFeature);
		this.plugin = plugin;
		this.attributesFeature = attributesFeature;
	}

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		Object result = null;
		if (adaptableObject instanceof IFacetContainer &&
			adaptableObject instanceof IComponent &&
			IAttributes.class.equals(adapterType)) {
			@SuppressWarnings("unused") 
			IFacetContainer fc = (IFacetContainer) adaptableObject;
			result = new AttributeAdapter(plugin, 
						(IComponent) adaptableObject, attributesFeature);
		}
		return result;
	}

	public Class[] getAdapterList() {
		return adapterList;
	}
}
