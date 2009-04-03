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

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.figure.NonLayoutObjectFigure;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Text;

/**
 * 
 *
 */
public class NonLayoutObjectLabelEditManager extends DirectLabelEditManager {
	private IDesignerDataModel dataModel;
	
	public NonLayoutObjectLabelEditManager(GraphicalEditPart source, Class editorType, CellEditorLocator locator,
										IDesignerEditor editor) {
		super(source, editorType, locator, editor);
		this.dataModel = editor.getDataModel(); 
	}

	/**
	 * @see org.eclipse.gef.tools.DirectEditManager#bringDown()
	 */
	protected void bringDown() {
		super.bringDown();
		font = null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.tools.DirectEditManager#initCellEditor()
	 */
	protected void initCellEditor() {
		TextCellEditor editor = (TextCellEditor) getCellEditor();
		NonLayoutObjectEditPart editPart = (NonLayoutObjectEditPart) getEditPart();
		getDirectEditRequest().setDirectEditFeature(dataModel.getNamePropertyId());

		if (initialKeyEvent == null) {
			String initialLabelText = editPart.getComponentInstance().getName();
			editor.setValue(initialLabelText);
		}
		else {
			editor.setValue("" + initialKeyEvent.character); //$NON-NLS-1$
		}

		NonLayoutObjectFigure figure = (NonLayoutObjectFigure) editPart.getFigure();
		font = figure.getFont();
		
		Text text = (Text) editor.getControl(); /* @see DirectLabelEditManager.createCellEditorOn */
		text.setFont(font);
		hookCellEditor();
	}

	protected String getPropertyPath() {
		return (String) dataModel.getNamePropertyId();
	}

	protected EObject getInstance() {
		return (EObject) getEditPart().getModel();
	}
}
