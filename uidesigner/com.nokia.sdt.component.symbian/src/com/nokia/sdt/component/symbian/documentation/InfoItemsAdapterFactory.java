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
package com.nokia.sdt.component.symbian.documentation;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IInfoItems;
import com.nokia.sdt.component.symbian.IFacetContainer;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.EStructuralFeature;

public class InfoItemsAdapterFactory implements IAdapterFactory {
	
	private Plugin plugin;
	private EStructuralFeature symbianFeature;
	private Class adapterList[] = {IInfoItems.class};
	
	/**
	 * @param symbianFeature the structural feature whose type is SymbianType
	 */
	public InfoItemsAdapterFactory(Plugin plugin, EStructuralFeature symbianFeature) {
		//Check.checkArg(plugin); // EJS: not needed
		Check.checkArg(symbianFeature);
		this.plugin = plugin;
		this.symbianFeature = symbianFeature;
	}

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		Object result = null;
		if (adaptableObject instanceof IFacetContainer &&
			adaptableObject instanceof IComponent &&
			IInfoItems.class.equals(adapterType)) {
			result = new InfoItemsAdapter(plugin, 
						(IComponent) adaptableObject, symbianFeature);
		}
		return result;
	}

	public Class[] getAdapterList() {
		return adapterList;
	}
}
