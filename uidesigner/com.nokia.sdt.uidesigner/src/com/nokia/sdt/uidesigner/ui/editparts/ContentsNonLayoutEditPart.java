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

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;

import com.nokia.sdt.uidesigner.ui.figure.NonLayoutContentsFigure;

/**
 * 
 *
 */
/**
 * 
 *
 */
public class ContentsNonLayoutEditPart extends AbstractContentsEditPart {

	NonLayoutContentsFigure contentPane;
	
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
		ScrollingGraphicalViewer lowerGraphicalViewer = 
			(ScrollingGraphicalViewer) getEditor().getLowerGraphicalViewer();
		contentPane = new NonLayoutContentsFigure(lowerGraphicalViewer);
		return contentPane;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getContentPane()
	 */
	public IFigure getContentPane() {
		return contentPane;
	}

	protected List getModelChildren() {
		return Collections.singletonList(getEditor().getNonLayoutRoot());
	}
}

