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

package com.nokia.sdt.symbian.dm;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.images.IProjectImageInfoProvider;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.emf.dm.IDesignerData;
import com.nokia.sdt.emf.dm.INode;
import com.nokia.sdt.symbian.images.ProjectImageInfoProvider;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 *
 */
public class ProjectImageInfoProviderAdapterFactory extends AdapterFactoryImpl {

    private Map<IDesignerDataModel, ProjectImageInfoProvider> dataModelToAdapterMap;

    /**
     * 
     */
    public ProjectImageInfoProviderAdapterFactory() {
        dataModelToAdapterMap = new HashMap<IDesignerDataModel, ProjectImageInfoProvider>();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.emf.common.notify.impl.AdapterFactoryImpl#isFactoryForType(java.lang.Object)
     */
    @Override
    public boolean isFactoryForType(Object klass) {
        return klass.equals(IProjectImageInfoProvider.class);
    }
    
    protected Adapter createAdapter(Notifier target, Object type) {
        Adapter result = null;
        IDesignerDataModel model = null;
        if (target instanceof IDesignerDataModel) {
            model = (IDesignerDataModel) target;
        } else if (target instanceof IComponentInstance) {
            model = ((IComponentInstance) target).getDesignerDataModel();
        } else if (target instanceof INode) {
            INode node = (INode) target;
            if (node.getDesignerData() != null)
                model = node.getDesignerData().getDesignerDataModel();
        } else if (target instanceof IDesignerData) {
            model = ((IDesignerData) target).getDesignerDataModel();
        } else if (target instanceof EObject) {
            IComponentInstance instance = ModelUtils.getComponentInstance((EObject) target);
            if (instance != null)
                model = instance.getDesignerDataModel();
        }
        if (model != null) {
            result = dataModelToAdapterMap.get(model);
            if (result == null) {
                result = new ProjectImageInfoProvider(model);
                dataModelToAdapterMap.put(model, (ProjectImageInfoProvider) result);
            }
        }
        return result;
    }
    
    
}
