/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian.displaymodel;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.displaymodel.IDisplayModel;

import org.eclipse.emf.ecore.EObject;

/**
 * This is a layout container whose position and size are dictated by
 * layout properties (location / size).  It reacts to changes on those
 * properties to initiate a layout of children.
 * 
 * 
 *
 */
public class LayoutContainerUsingLayoutProperties extends LayoutContainerBase {

	LayoutContainerUsingLayoutProperties(IDisplayModel displayModel, IComponentInstance instance) {
		super(displayModel, instance);

	}

	protected void handlePropertyChange(EObject componentInstance,
			Object propertyId) {
		if (propertyId.equals(Utilities.LOCATION_PROPERTY) || 
				propertyId.equals(Utilities.SIZE_PROPERTY)) {
			layoutChildren();
		}		
	}

}
