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
package com.nokia.sdt.symbian.dm;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.dm.SymbianModelUtils.ViewDesignInfo;

import org.eclipse.emf.ecore.EObject;

/**
 * Common information we need to look up on S60 view designs
 */
public class S60ViewDesignInfo extends ViewDesignInfo {
	/** tells whether the design is an Avkon view */
	public boolean isAvkonView;
	/** the component id for the Avkon view */
	public String avkonViewComponentID;
	/** the friendly name of the component for the Avkon view */
	public String avkonViewFriendlyName;
	
	S60ViewDesignInfo(IDesignerDataModel model) {
		// we expect only a single root for a model containing 
		// an avkon view
		EObject[] roots = model.getRootContainers();
		if (roots.length > 0) {
			IComponentInstance ci = ModelUtils.getComponentInstance(roots[0]);
			isAvkonView = ModelUtils.isInstanceOf(roots[0], S60ModelUtils.S60_AVKON_VIEW);
			designReferenceComponentId = isAvkonView ? S60ModelUtils.S60_AVKON_VIEW_REFERENCE : S60ModelUtils.S60_DESIGN_REFERENCE;
			baseName = SymbianModelUtils.getBaseName(ci.getEObject());
			IComponent component = ci.getComponent();
			if (isAvkonView) {
				avkonViewComponentID = component.getId();
				avkonViewFriendlyName = component.getFriendlyName();
			}
			
			EObject userContainer = ModelUtils.findImmediateChildWithAttributeValue(ci.getEObject(), "is-layout-container", "true"); //$NON-NLS-1$ //$NON-NLS-2$
			if (userContainer != null) {
				ci = ModelUtils.getComponentInstance(userContainer);
				component = ci.getComponent();
				userContainerComponentID = component.getId();
				userContainerFriendlyName = component.getFriendlyName();
			}
		}
	}
}