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


package com.nokia.sdt.uidesigner.ui.actions;

import com.nokia.sdt.editor.EditorServices;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.gef.ui.actions.EditorPartAction;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.ActionFactory;

/**
 * An action to save the editor's current state.
 */
public class RevertAction extends EditorPartAction {

	/**
	 * Constructs a <code>RevertAction</code> and associates it with the given editor.
	 * 
	 * @param editor the IEditorPart
	 */
	public RevertAction(IEditorPart editor) {
		super(editor);
	}

	/**
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		return getEditorPart().isDirty();
	}

	/**
	 * Initializes this action's text.
	 */
	protected void init() {
		setId(ActionFactory.REVERT.getId());
	}

	/**
	 * Saves the state of the associated editor.
	 */
	public void run() {
    	// if an editor is not active, try to activate frontmost editor 
    	// to avoid properties view from being active - 
    	// some of the property sheet entries may end up with stale objects before
    	// they are completely updated
    	IWorkbenchPage page = getEditorPart().getSite().getPage();
		IWorkbenchPart part = page.getActivePart();
    	if (part.getAdapter(IDesignerDataModelEditor.class) == null) {
    		IEditorPart activeEditor = page.getActiveEditor();
    		if (activeEditor != null)
    			page.activate(activeEditor);
    	}
		// we can't just reload from the editorPart, because we may not be the top level editorPart
		IDesignerDataModelEditor editor = 
			(IDesignerDataModelEditor) getEditorPart().getAdapter(IDesignerDataModelEditor.class);
		Check.checkContract(editor != null);
		IEditorPart editorPart = EditorServices.findEditor(editor.getDataModel());
		editor = (IDesignerDataModelEditor) editorPart.getAdapter(IDesignerDataModelEditor.class);
		Check.checkContract(editor != null);
		editor.reload();
	}

}
