/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian.scripting;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IEventBinding;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

public interface IModelWrappers {
    /** Maintain 1:1 mapping of IComponentInstance to WrappedInstance */
    public WrappedInstance getWrappedInstance(IComponentInstance instance, IPropertySource properties);

    /** Maintain 1:1 mapping of IComponentInstance to WrappedInstance */
    public WrappedInstance getWrappedInstance(EObject object);

    /**
     * @return array of wrapped instances for objs
     */
    public WrappedInstance[] getWrappedInstanceArray(EObject objs[]);
    
    /** Maintain 1:1 mapping of IPropertySource to WrappedProperties */
    public WrappedProperties getWrappedProperties(IPropertySource propertySource);

    /** 
     * Wrap a property value for Javascript.
     * This means primarily to ensure EMF/adaptable objects are wrapped
     * if needed. 
     * @param obj incoming Java object
     * @return object or wrapped object
     */
    public Object wrapProperty(Object obj);

    /** 
     * Unwrap a property value from Javascript.
     * @param obj incoming Java object
     * @return object or wrapped object
     */
    public Object unwrapProperty(Object obj);

    /** Maintain 1:1 mapping of IEventBinding to WrappedEventBinding */
    public WrappedEventBinding getWrappedEventBinding(IEventBinding binding);
}