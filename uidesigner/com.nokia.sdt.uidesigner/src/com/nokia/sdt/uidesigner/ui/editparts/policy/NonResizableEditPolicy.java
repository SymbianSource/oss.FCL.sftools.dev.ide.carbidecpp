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
package com.nokia.sdt.uidesigner.ui.editparts.policy;

import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.editparts.TransientObjectEditPart;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;

public class NonResizableEditPolicy extends
		org.eclipse.gef.editpolicies.NonResizableEditPolicy {

	private IDesignerEditor editor;
	
	public NonResizableEditPolicy(IDesignerEditor editor) {
		this.editor = editor;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.SelectionEditPolicy#setSelectedState(int)
	 */
	protected void setSelectedState(int type) {
		if (type == EditPart.SELECTED_PRIMARY)
			showPrimarySelection();
		else if (type == EditPart.SELECTED)
			showSelection();
		else
			hideSelection();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.SelectionHandlesEditPolicy#showSelection()
	 */
	protected void showSelection() {
		if (!editor.isTransientMode() && (getHost() instanceof TransientObjectEditPart))
			hideSelection();
		else
			super.showSelection();
	}

	@Override
	public EditPart getTargetEditPart(Request request) {
		if (EditorUtils.isRequestForTargetFeedback(request) && 
				Adapters.getLayoutContainer((EObject) getHost().getModel()) == null)
			return getHost().getParent();
		
		return super.getTargetEditPart(request);
	}
}
