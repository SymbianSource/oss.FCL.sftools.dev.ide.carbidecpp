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

import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.LayoutAreaConfigurationAdapter;
import com.nokia.sdt.displaymodel.IDisplayModel.LayoutAreaConfiguration;
import com.nokia.sdt.uidesigner.ui.figure.LayoutContentsFigure;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

/**
 * 
 *
 */
public class ContentsLayoutEditPart extends AbstractContentsEditPart {

	LayoutContentsFigure contents;
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		// disallows the removal of this edit part from its parent
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// disable selection feedback for this edit part
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, null);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		contents = new LayoutContentsFigure(getViewer());
		return contents;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getContentPane()
	 */
	public IFigure getContentPane() {
		return contents.getLayoutFigure();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	protected void refreshVisuals() {
		// do nothing at the root layout figure
	}

	public void setDisplayModel(IDisplayModel displayModel) {
		displayModel.addLayoutAreaListener(new LayoutAreaConfigurationAdapter() {
			public void configurationChanged(LayoutAreaConfiguration layout) {
				contents.setLayout(layout);
				contents.revalidate();
			}
		});
		LayoutAreaConfiguration currentConfiguration = displayModel.getCurrentConfiguration();
		if (currentConfiguration != null) {
			contents.setLayout(currentConfiguration);
			contents.revalidate();
		}
	}
	
}

