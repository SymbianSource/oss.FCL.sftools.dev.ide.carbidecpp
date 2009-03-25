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
import com.nokia.sdt.component.adapter.IDocumentation;
import com.nokia.sdt.component.symbian.IFacetContainer;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.EStructuralFeature;

public class DocumentationAdapterFactory implements IAdapterFactory {
	
	private Plugin plugin;
	private EStructuralFeature documentationFeature;
	private Class adapterList[] = {IDocumentation.class};
	
	/**
	 * @param documentationFeature the structural feature whose type is DocumentationType
	 */
	public DocumentationAdapterFactory(Plugin plugin, EStructuralFeature documentationFeature) {
		//Check.checkArg(plugin); // EJS: not needed
		Check.checkArg(documentationFeature);
		this.plugin = plugin;
		this.documentationFeature = documentationFeature;
	}

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		Object result = null;
		if (adaptableObject instanceof IFacetContainer &&
			adaptableObject instanceof IComponent &&
			IDocumentation.class.equals(adapterType)) {
			result = new DocumentationAdapter(plugin, 
						(IComponent) adaptableObject, documentationFeature);
		}
		return result;
	}

	public Class[] getAdapterList() {
		return adapterList;
	}
}
