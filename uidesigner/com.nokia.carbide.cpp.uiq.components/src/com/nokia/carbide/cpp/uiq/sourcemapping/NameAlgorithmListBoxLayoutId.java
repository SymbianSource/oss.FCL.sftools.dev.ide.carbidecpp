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

import org.eclipse.emf.ecore.EObject;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.sourcegen.INameAlgorithm;
import com.nokia.sdt.symbian.dm.UIQModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

public class NameAlgorithmListBoxLayoutId implements INameAlgorithm{

	public NameAlgorithmListBoxLayoutId() {
	}

	public String getEnumDeclarationName(IComponentInstance instance,
			String containerName, String instanceName, String propertyId) {
		EObject layoutsParent = instance.getParent();
		IComponentInstance layoutsParentCI = ModelUtils.getComponentInstance(layoutsParent);
		return "T" + TextUtils.titleCase(containerName) + TextUtils.titleCase(layoutsParentCI.getName()) + "Ids"; //$NON-NLS-1$
	}
 
	public String getEnumeratorName(IComponentInstance instance,
			String containerName, String instanceName, String propertyId) {
		EObject layoutsParent = instance.getParent();
		IComponentInstance layoutsParentCI = ModelUtils.getComponentInstance(layoutsParent);
		return "E" + TextUtils.titleCase(containerName) + TextUtils.titleCase(layoutsParentCI.getName()) + TextUtils.titleCase(instanceName) + "Id"; //$NON-NLS-1$
	}

	public String getInitialEnumeratorValue(IComponentInstance instance,
			String propertyId) {
		// no initial value; every value is determined by position in parent
		return null;
	}	
	
	public String getEnumeratorValue(IComponentInstance instance,
			String propertyId) {
		if (instance.getParent() == null)
			return null;
		IComponentInstance parent = ModelUtils.getComponentInstance(instance.getParent());
		EObject[] kids = parent.getChildren();
		int layoutIndex = 0;
		for (int index = 0; index < kids.length; index++) {
			if (kids[index] == instance.getEObject())
				return "" + layoutIndex;
			
			// just being paranoid in case non-layout children ever get mixed in
			String componentId = ModelUtils.getComponentInstance(kids[index]).getComponentId();
			if (componentId.equals(UIQModelUtils.UIQ_LISTBOX_LAYOUT)) {
				layoutIndex++;
			}
		}
		Check.checkState(false);
		return null;
	}
	

}
