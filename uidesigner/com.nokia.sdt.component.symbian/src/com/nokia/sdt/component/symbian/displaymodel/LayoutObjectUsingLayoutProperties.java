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
package com.nokia.sdt.component.symbian.displaymodel;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Rectangle;

/**
 * This is a layout object whose position and size are dictated by
 * layout properties (location / size).  It reacts to changes on those
 * properties to tell its parent to layout again.
 * 
 *
 */
public class LayoutObjectUsingLayoutProperties extends LayoutObjectBase implements ILayoutObject {
	
	public LayoutObjectUsingLayoutProperties(IDisplayModel displayModel, IComponentInstance instance) {
		super(displayModel, instance);
	}
	
	@Override
	protected void handlePropertyChange(EObject componentInstance,
			Object propertyId) {
		super.handlePropertyChange(componentInstance, propertyId);
		if (propertyId.equals(Utilities.LOCATION_PROPERTY) || 
						propertyId.equals(Utilities.SIZE_PROPERTY)) {
			fireBoundsChanged();
		}
	}

	public Rectangle getBounds() {
		return Utilities.getBoundsFromLayoutProperties(instance.getEObject());
	}

	public void setBounds(Rectangle newBounds) {
		Utilities.setBoundsIntoLayoutProperties(instance.getEObject(), newBounds);
	}
}
