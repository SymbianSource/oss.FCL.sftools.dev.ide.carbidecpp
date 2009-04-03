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
package com.nokia.sdt.component.symbian.designerimages;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IDesignerImages;
import com.nokia.sdt.component.symbian.IFacetContainer;
import com.nokia.sdt.emf.component.DesignerImagesType;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class DesignerImageAdapterFactory implements IAdapterFactory {

	private Plugin plugin;
	private EStructuralFeature designerImagesFeature;
	private Class adapterList[] = {IDesignerImages.class};

	public DesignerImageAdapterFactory(Plugin plugin, EStructuralFeature designerImagesFeature) {
		//Check.checkArg(plugin); // EJS: not needed
		Check.checkArg(designerImagesFeature);
		this.plugin = plugin;
		this.designerImagesFeature = designerImagesFeature;
	}

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		Object result = null;
		if (adaptableObject instanceof IFacetContainer &&
			adaptableObject instanceof IComponent &&
			IDesignerImages.class.equals(adapterType)) {
			IFacetContainer fc = (IFacetContainer) adaptableObject;
			EObject container = fc.getEMFContainer();
			Object featureObj = container.eGet(designerImagesFeature);
			if (featureObj instanceof DesignerImagesType) {
				DesignerImagesType designerImages = (DesignerImagesType) featureObj;
				result = new DesignerImageAdapter(plugin, (IComponent) adaptableObject, designerImages);
			}
		}
		return result;
	}

	public Class[] getAdapterList() {
		return adapterList;
	}
}
