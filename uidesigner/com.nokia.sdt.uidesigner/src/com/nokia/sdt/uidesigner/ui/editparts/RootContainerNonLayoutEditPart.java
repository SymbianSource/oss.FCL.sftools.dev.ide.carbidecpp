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


package com.nokia.sdt.uidesigner.ui.editparts;

import com.nokia.sdt.displaymodel.adapter.IDisplayObject;
import com.nokia.sdt.uidesigner.ui.editparts.policy.ContentsNonLayoutEditPolicy;
import com.nokia.sdt.uidesigner.ui.figure.RootContainerNonLayoutFigure;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

/**
 * 
 *
 */
public class RootContainerNonLayoutEditPart extends ModelObjectEditPart {

	/* (non-Javadoc)
	 * @see com.nokia.sdt.uidesigner.ui.editparts.ModelObjectEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		return new RootContainerNonLayoutFigure();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		// disallows the removal of this edit part from its parent
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new ContentsNonLayoutEditPolicy(this));
	}
	
	@Override
	public void childAdded(EObject parent, EObject child) {
		refreshChildren();
	}
	
	@Override
	public void childRemoved(EObject parent, EObject child) {
		refreshChildren();
	}

	@Override
	protected boolean isApplicableChild(EObject object) {
		IDisplayObject displayObject = Adapters.getDisplayObject(object);
		return (displayObject != null) && displayObject.isNonLayoutObject();
	}
	
}
