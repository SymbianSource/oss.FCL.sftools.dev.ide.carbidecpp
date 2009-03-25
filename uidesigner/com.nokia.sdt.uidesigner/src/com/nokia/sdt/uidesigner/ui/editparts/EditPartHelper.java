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

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.*;
import org.eclipse.ui.*;

/**
 * 
 *
 */
public class EditPartHelper implements IEditPartEditorProvider {

	private EditPart part;

	/**
	 * 
	 */
	public EditPartHelper(EditPart part) {
		Check.checkArg(part);
		this.part = part;
	}

	public IDesignerEditor getEditor() {
		EditPartViewer viewer = part.getViewer();
		Check.checkState(viewer != null);
		
		DefaultEditDomain domain = (DefaultEditDomain) part.getViewer().getEditDomain();
		IEditorPart editorPart = domain.getEditorPart();
		if (editorPart instanceof IDesignerEditor) {
			return (IDesignerEditor) editorPart;
		}
		
		return null;
	}

	public void setStatusLineErrorMessage(String errorMessage) {
		DefaultEditDomain domain = (DefaultEditDomain) part.getViewer().getEditDomain();
		IEditorPart editorPart = domain.getEditorPart();
		IActionBars bars = ((IEditorSite) editorPart.getSite()).getActionBars();
		bars.getStatusLineManager().setErrorMessage(errorMessage);
	}

	public IComponentInstance getComponentInstance() {
		Object model = part.getModel();
		if (model instanceof EObject)
			return Adapters.getComponentInstance((EObject) model);
		
		return null;
	}
}
