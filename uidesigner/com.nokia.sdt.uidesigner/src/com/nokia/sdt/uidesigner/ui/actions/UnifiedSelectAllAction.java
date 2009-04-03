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

import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.actions.SelectAllAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;

import java.util.ArrayList;
import java.util.Iterator;

public class UnifiedSelectAllAction extends SelectAllAction {
	private IDesignerEditor editor;

	public UnifiedSelectAllAction(IEditorPart editor) {
		super(editor);
		Check.checkArg(editor instanceof IDesignerEditor);
		this.editor = (IDesignerEditor) editor;
	}
	
	private void collectEditPartChildren(EditPart part, ArrayList children) {
		Iterator iter = part.getChildren().iterator();
		while (iter.hasNext()) {
			EditPart child = (EditPart) iter.next();
			Object object = child.getModel();
			if ((object instanceof EObject) && editor.getDisplayModel().isSelectAllObject((EObject) child.getModel())) {
				children.add(child);
			}
			collectEditPartChildren(child, children);
		}
	}
	
	private ArrayList getAllChildren(GraphicalViewer viewer) {
		ArrayList result = new ArrayList();
		collectEditPartChildren(viewer.getContents(), result);
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.ui.actions.SelectAllAction#run()
	 */
	public void run() {
		GraphicalViewer upperViewer = editor.getUpperGraphicalViewer();
		Check.checkArg(upperViewer);
		ArrayList selectedParts = getAllChildren(upperViewer);

		GraphicalViewer lowerViewer = editor.getLowerGraphicalViewer();
		if (lowerViewer != null) {
			selectedParts.addAll(getAllChildren(lowerViewer));
		}
		
		editor.getSelectionManager().setSelection(new StructuredSelection(selectedParts));
	}
}
