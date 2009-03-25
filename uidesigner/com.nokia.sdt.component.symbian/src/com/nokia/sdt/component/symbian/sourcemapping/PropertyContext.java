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

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.NodePathLookupResult;

import org.eclipse.ui.views.properties.IPropertySource;

public class PropertyContext {

    IComponentInstance instance;
    IPropertySource properties;
	String propertyId;

    public PropertyContext(NodePathLookupResult result, String propertyId) {
        this.instance = result.instance;
        this.properties = result.properties;
        this.propertyId = propertyId;
    }

    public PropertyContext(IComponentInstance instance, IPropertySource properties) {
        this.instance = instance;
        this.properties = properties;
        this.propertyId = "."; //$NON-NLS-1$
    }
}
