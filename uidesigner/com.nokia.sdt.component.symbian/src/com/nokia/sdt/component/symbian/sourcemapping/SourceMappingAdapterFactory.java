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
package com.nokia.sdt.component.symbian.sourcemapping;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.symbian.IFacetContainer;
import com.nokia.sdt.emf.component.SourceMappingType;
import com.nokia.sdt.sourcegen.IComponentSourceMapping;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class SourceMappingAdapterFactory implements IAdapterFactory {
	
	private Plugin plugin;
	private EStructuralFeature sourceMappingFeature;
	private Class adapterList[] = { IComponentSourceMapping.class };
	
	/**
	 * @param sourceMappingFeature the structural feature whose type is SrcgenType
	 */
	public SourceMappingAdapterFactory(Plugin plugin, EStructuralFeature sourceMappingFeature) {
	    //Check.checkArg(plugin); // EJS: not needed
		Check.checkArg(sourceMappingFeature);
		this.plugin = plugin;
		this.sourceMappingFeature = sourceMappingFeature;
	}

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		Object result = null;
		if (adaptableObject instanceof IFacetContainer &&
			adaptableObject instanceof IComponent &&
			IComponentSourceMapping.class.equals(adapterType)) 
        {
            IComponent component = (IComponent) adaptableObject;
            while (component != null) {
                SourceMappingType srcmap = getSourceMappingTypeFromContainer((IFacetContainer) component);
                if (srcmap != null) {
                    result = new SourceMappingAdapterXML(plugin, component, srcmap);
                    break;
                }
                component = component.getComponentBase();
            }
        }
        return result;
    }

    private SourceMappingType getSourceMappingTypeFromContainer(IFacetContainer fc) {
        SourceMappingType srcmapObj = null;
        EObject container = fc.getEMFContainer();
        Object featureObj = container.eGet(sourceMappingFeature);
        if (featureObj instanceof SourceMappingType)
            srcmapObj = (SourceMappingType) featureObj;
        
        return srcmapObj;
    }
    
    
    public Class[] getAdapterList() {
        return adapterList;
    }
    
}
