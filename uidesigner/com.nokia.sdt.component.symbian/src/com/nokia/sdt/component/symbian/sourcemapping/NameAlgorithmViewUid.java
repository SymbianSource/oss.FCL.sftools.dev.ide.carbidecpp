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
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.sourcegen.INameAlgorithm;
import com.nokia.sdt.symbian.dm.S60ModelUtils;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.workspace.IProjectContext;

import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Get unique ids for views.  Property ignored.
 * This can take a CAknView or AvkonViewReference instance.
 * 
 *
 */
public class NameAlgorithmViewUid implements INameAlgorithm {
	public NameAlgorithmViewUid() {
	}
	
    public String getEnumDeclarationName(IComponentInstance instance, String containerName, String instanceName, String propertyId) {
        // make enumeration like TProjectNameViewUids
    	IProjectContext projectContext = instance.getDesignerDataModel().getProjectContext();
    	if (projectContext != null && projectContext.getProject() != null) {
    		return "T" + projectContext.getProject().getName() + "ViewUids"; //$NON-NLS-1$ //$NON-NLS-2$
    	}
        return "TApplicationViewUids"; //$NON-NLS-1$ 
    }

    public String getEnumeratorName(IComponentInstance instance, String containerName, String instanceName, String propertyId) {
        // make enumerator like EMyContainerViewId
    	if (ModelUtils.isInstanceOf(instance.getEObject(), S60ModelUtils.S60_AVKON_VIEW_REFERENCE)) {
    		IPropertySource ps = ModelUtils.getPropertySource(instance.getEObject());
    		String avkonViewName = 
    			S60ModelUtils.getAvkonViewName((String) ps.getPropertyValue(S60ModelUtils.DESIGNREF_BASENAME));
    		return "E" + TextUtils.titleCase(avkonViewName) + "Id"; //$NON-NLS-1$ //$NON-NLS-2$
    	}
    	else // this is the instance in the view model
    		return "E" + TextUtils.titleCase(containerName) + "Id"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    public String getInitialEnumeratorValue(IComponentInstance instance, String propertyId) {
        // these start at 0x1
        return "1"; //$NON-NLS-1$
    }
    
    public String getEnumeratorValue(IComponentInstance instance,
    		String propertyId) {
    	return null;
    }
    
}