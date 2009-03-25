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
 * This is a layout object whose position and size are maintained in a
 * cached bounds rectangle.  Only explicit changes to the bounds from a
 * call to ILayout are detected.
 * 
 *
 */
public class LayoutObjectUsingCachedBounds extends LayoutObjectBase implements ILayoutObject {
	
	private Rectangle bounds;
	public LayoutObjectUsingCachedBounds(IDisplayModel displayModel, IComponentInstance instance) {
		super(displayModel, instance);
		this.bounds = new Rectangle(0, 0, 0, 0);
	}
	
	@Override
	protected void handlePropertyChange(EObject componentInstance,
			Object propertyId) {
		super.handlePropertyChange(componentInstance, propertyId);
	}

	public Rectangle getBounds() {
		return new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	public void setBounds(Rectangle newBounds) {
		this.bounds = new Rectangle(newBounds.x, newBounds.y, newBounds.width, newBounds.height);
	}
}
