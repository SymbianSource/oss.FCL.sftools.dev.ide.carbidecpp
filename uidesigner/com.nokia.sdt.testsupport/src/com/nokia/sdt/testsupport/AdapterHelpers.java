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
package com.nokia.sdt.testsupport;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;




/**
 * These routines are useful for unit tests.
 * Provides common patterns for looking up adapters on components
 * and instances.  (Embrace and extend as needed)
 * 
 * 
 *
 */
public class AdapterHelpers {

    /**
     * Get the component instance from the EObject
     */
    public static IComponentInstance getComponentInstance(EObject object) {
        Check.checkArg(object);
        return (IComponentInstance) EcoreUtil.getRegisteredAdapter(object,
                IComponentInstance.class);
    }

    /**
     * Get the component from the EObject
     */
    public static IComponent getComponent(EObject object) {
        Check.checkArg(object);
        IComponentInstance componentInstance = getComponentInstance(object);
        if (componentInstance != null) {
            return componentInstance.getComponent();
        }
        return null;
    }

    /**
     * Find a component instance with the given name
     */
    public static IComponentInstance findComponentInstance(IDesignerDataModel dm, String name) {
        EObject object = dm.findByNameProperty(name);
        if (object == null)
            return null;
        return getComponentInstance(object);
    }

    /**
     * Get the property source for the component instance
     */
    public static IPropertySource getPropertySource(IComponentInstance instance) {
        Check.checkArg(instance);
        return (IPropertySource) EcoreUtil.getRegisteredAdapter(
                instance.getEObject(),
                IPropertySource.class);
    }

    public static IPropertySource getPropertySource(EObject obj) {
        return (IPropertySource) EcoreUtil.getRegisteredAdapter(
                obj, IPropertySource.class);
    }
}
