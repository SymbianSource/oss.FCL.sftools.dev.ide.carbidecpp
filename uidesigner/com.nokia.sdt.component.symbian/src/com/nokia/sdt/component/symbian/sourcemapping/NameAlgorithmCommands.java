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

package com.nokia.sdt.component.symbian.sourcemapping;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.sourcegen.INameAlgorithm;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

public class NameAlgorithmCommands implements INameAlgorithm {
	public NameAlgorithmCommands() {
	}
	
    public String getEnumDeclarationName(IComponentInstance instance, String containerName, String instanceName, String propertyId) {
        // make enumeration like TMyClassCommands
        return "T" + TextUtils.titleCase(containerName) + "Commands"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    public String getEnumeratorName(IComponentInstance instance, String containerName, String instanceName, String propertyId) {
        // make enumerator like EMyClassCbaLeftId
        return "E" + TextUtils.titleCase(containerName) + TextUtils.titleCase(instanceName) + TextUtils.titleCase(propertyId); //$NON-NLS-1$
    }

    public String getInitialEnumeratorValue(IComponentInstance instance, String propertyId) {
        // these start at 0x6000
        return "0x6000"; //$NON-NLS-1$
    }
    
    public String getEnumeratorValue(IComponentInstance instance,
    		String propertyId) {
    	return null;
    }
}