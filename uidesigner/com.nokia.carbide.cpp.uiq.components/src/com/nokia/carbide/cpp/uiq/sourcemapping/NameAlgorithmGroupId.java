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
package com.nokia.carbide.cpp.uiq.sourcemapping;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.sourcegen.INameAlgorithm;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

public class NameAlgorithmGroupId implements INameAlgorithm {

	public NameAlgorithmGroupId() {
	}

	public String getEnumDeclarationName(IComponentInstance instance,
			String containerName, String instanceName, String propertyId) {
		// TODO make enumeration like TMyClassControls
		//Enumeration= “T” + <root container name> + “PageIds”
		return "T" + TextUtils.titleCase(containerName) + "groupIds"; //$NON-NLS-1$ //$NON-NLS-2$;
	}
 
	public String getEnumeratorName(IComponentInstance instance,
			String containerName, String instanceName, String propertyId) {
		// TODO make enumerator like EMyClassInstanceName
		// Enumerator= “E” + <root container name> + “Page” + N (0..) 
        return "E" + TextUtils.titleCase(containerName) + "Group"; //$NON-NLS-1$
	}

	public String getInitialEnumeratorValue(IComponentInstance instance,
			String propertyId) {
		 return "1"; //$NON-NLS-1$
	}	
	
	public String getEnumeratorValue(IComponentInstance instance,
			String propertyId) {
		return null;
	}
	

}
