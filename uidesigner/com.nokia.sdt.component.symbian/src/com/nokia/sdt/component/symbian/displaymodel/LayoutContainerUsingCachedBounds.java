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
 * This is a layout container whose position and size are cached
 * on the layout object.  Thus, this does nothing.
 * 
 *
 */
public class LayoutContainerUsingCachedBounds extends LayoutContainerBase {

	LayoutContainerUsingCachedBounds(IDisplayModel displayModel, IComponentInstance instance) {
		super(displayModel, instance);

	}

	protected void handlePropertyChange(EObject componentInstance,
			Object propertyId) {
	}

}
