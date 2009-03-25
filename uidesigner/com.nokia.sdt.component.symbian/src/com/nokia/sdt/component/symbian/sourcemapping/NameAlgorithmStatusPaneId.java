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

import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.sourcegen.IComponentSourceMapping;
import com.nokia.sdt.sourcegen.INameAlgorithm;
import com.nokia.sdt.symbian.S60ComponentAttributes;
import com.nokia.cpp.internal.api.utils.core.IMessage;

public class NameAlgorithmStatusPaneId implements INameAlgorithm {

    public NameAlgorithmStatusPaneId() {
    }
    
    // this should not be used to define new enums
    public String getEnumDeclarationName(IComponentInstance instance,
            String containerName, String instanceName, String propertyId) {
        return null;
    }

    // this should not be used to define new enums
    public String getInitialEnumeratorValue(IComponentInstance instance,
            String propertyId) {
        return null;
    }

    // The enumerator is the value, if any, declared by the component's
    // dialog-control-type attribute
    public String getEnumeratorName(IComponentInstance instance,
            String containerName, String instanceName, String propertyId) {
        String name = null;
        if (instance.getComponent() != null) {
            IAttributes attrs = (IAttributes) instance.getComponent().getAdapter(IAttributes.class);
            if (attrs != null)
                name = attrs.getAttribute(S60ComponentAttributes.STATUS_PANE_ID);
        }
        if (name == null) {
        	ComponentMessageUtils.emit(instance,
        			IMessage.ERROR,
                    "NameAlgorithm.NoNamingAttribute", //$NON-NLS-1$
                    new String[] { S60ComponentAttributes.STATUS_PANE_ID,
                            instance.getComponentId(),
                            IComponentSourceMapping.NAME_ALGORITHM_STATUS_PANE_ID.toString() } );
        }
        return name;
    }

    public String getEnumeratorValue(IComponentInstance instance,
    		String propertyId) {
    	return null;
    }

}
