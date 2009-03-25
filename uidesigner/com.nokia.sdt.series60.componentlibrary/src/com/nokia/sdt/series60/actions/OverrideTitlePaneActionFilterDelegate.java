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
/**
 * 
 */
package com.nokia.sdt.series60.actions;

import com.nokia.sdt.component.symbian.actionFilter.BaseAddComponentActionFilterDelegate;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.ui.images.DirectEditingUtilities;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;

/**
 * 
 *
 */
public class OverrideTitlePaneActionFilterDelegate extends BaseAddComponentActionFilterDelegate {

	private static final String STATUS_PANE_TITLE_ID = "com.nokia.sdt.series60.StatusPaneTitle"; //$NON-NLS-1$
	private static final String STATUS_PANE_ID = "com.nokia.sdt.series60.StatusPane"; //$NON-NLS-1$
	private static final String TITLE_TEXT_PROPERTY = "titleText"; //$NON-NLS-1$

	/* (non-Javadoc)
	 * @see com.nokia.sdt.series60.actions.BaseAddComponentActionFilterDelegate#getAddedComponentId(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected String getAddedComponentId(EObject target) {
		return STATUS_PANE_TITLE_ID;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.series60.actions.BaseAddComponentActionFilterDelegate#isLegalTarget(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected boolean isLegalTarget(EObject target) {
		return ModelUtils.isInstanceOf(target, STATUS_PANE_ID);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.actionFilter.BaseAddComponentActionFilterDelegate#getAddTarget(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected EObject getAddTarget(EObject target) {
		return target;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.series60.actions.BaseAddComponentActionFilterDelegate#getInitializeInstanceCommand(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected Command getInitializeInstanceCommand(EObject target, EObject instance) {
		// yuck, this directly touches the command stack
		EditPart part = EditorUtils.getPrimaryEditPartForObject(getEditor(), instance);
		if (part instanceof GraphicalEditPart)
			DirectEditingUtilities.editLabelProperty(getEditor(), (GraphicalEditPart)part, instance, TITLE_TEXT_PROPERTY);
		return null;
	}	
}
