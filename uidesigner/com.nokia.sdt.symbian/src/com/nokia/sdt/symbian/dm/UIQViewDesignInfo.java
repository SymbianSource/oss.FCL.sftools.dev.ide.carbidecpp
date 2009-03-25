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
 * Common information we need to look up on UIQ view designs
 * <p>
 * TODO: get some useful information here.  Stubbed for now.
 */
public class UIQViewDesignInfo extends ViewDesignInfo {
	
	UIQViewDesignInfo(IDesignerDataModel model) {
		EObject[] roots = model.getRootContainers();
		if (roots.length > 0) {
			IComponentInstance ci = ModelUtils.getComponentInstance(roots[0]);
			baseName = SymbianModelUtils.getBaseName(ci.getEObject());
			designReferenceComponentId = UIQModelUtils.UIQ_DESIGN_REFERENCE;
			IComponent component = ci.getComponent();
			userContainerComponentID = component.getId();
			userContainerFriendlyName = component.getFriendlyName();
			Object value = ModelUtils.getEditablePropertyValue(ci.getEObject(), UIQModelUtils.VIEWDIALOG_PROPERTY_ISAPPUICONTAINER);
			if (value instanceof String) {
				isAppUIContainer = new Boolean((String)ModelUtils.getEditablePropertyValue(ci.getEObject(), UIQModelUtils.VIEWDIALOG_PROPERTY_ISAPPUICONTAINER)).booleanValue();
			} else if (value instanceof Boolean) {
				isAppUIContainer = ((Boolean)ModelUtils.getEditablePropertyValue(ci.getEObject(), UIQModelUtils.VIEWDIALOG_PROPERTY_ISAPPUICONTAINER)).booleanValue();
			}
			if (ModelUtils.getEditablePropertyValue(ci.getEObject(), "type") != null)
				userContainerFriendlyName += " - " + (String)ModelUtils.getEditablePropertyValue(ci.getEObject(), "type");
			
			/*
			if (isAppUIContainer != null && isAppUIContainer.equals("dialog")) {
				isAppUIContainer = false;
				userContainerFriendlyName = (String)ModelUtils.getEditablePropertyValue(ci.getEObject(), "type");
			}		 
			*/
		}
	}
}