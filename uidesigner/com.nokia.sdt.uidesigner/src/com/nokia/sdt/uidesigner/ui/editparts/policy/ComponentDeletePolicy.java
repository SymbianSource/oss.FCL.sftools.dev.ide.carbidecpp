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


package com.nokia.sdt.uidesigner.ui.editparts.policy;

import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.command.DeleteCommand;
import com.nokia.sdt.uidesigner.ui.editparts.IEditPartEditorProvider;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public final class ComponentDeletePolicy extends ComponentEditPolicy {
	/**
	 * @param part
	 */
	public ComponentDeletePolicy(EditPart part) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		IEditPartEditorProvider editorProvider = (IEditPartEditorProvider) getHost();
		IDesignerEditor editor = editorProvider.getEditor();
		if (editor == null)
			return null;
		
		return new DeleteCommand(deleteRequest.getEditParts(), 
				editorProvider.getEditor().getDataModel(), editor);
	}
}