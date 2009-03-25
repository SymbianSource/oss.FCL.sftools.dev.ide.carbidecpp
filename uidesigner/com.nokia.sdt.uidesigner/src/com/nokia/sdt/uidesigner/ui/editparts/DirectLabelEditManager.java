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

import com.nokia.sdt.component.property.IPropertyDescriptorExtensions;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NodePathLookupResult;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.CellEditorActionHandler;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public abstract class DirectLabelEditManager extends org.eclipse.gef.tools.DirectEditManager {
	protected Font font;
	protected ReturnListener returnListener;
	protected KeyEvent initialKeyEvent;
	protected IActionBars actionBars;
	protected IAction cut, copy, paste, undo, redo, selectAll, delete;
	protected CellEditorActionHandler actionHandler;
	protected IDesignerEditor editor;
	private boolean wantsCancel;

	class ReturnListener implements TraverseListener {
		/* (non-Javadoc)
		 * @see org.eclipse.swt.events.TraverseListener#keyTraversed(org.eclipse.swt.events.TraverseEvent)
		 */
		public void keyTraversed(TraverseEvent e) {
			if (e.detail == SWT.TRAVERSE_ESCAPE) {
				wantsCancel = true;
				e.doit = true;
			}
			else if (e.detail == SWT.TRAVERSE_RETURN) {
				e.doit = false;
				commit();
			}
		}
		
	}
	
	public DirectLabelEditManager(GraphicalEditPart source, Class editorType, CellEditorLocator locator, IDesignerEditor editor) {
		super(source, editorType, locator);
		returnListener = new ReturnListener();
		this.editor = editor;
		wantsCancel = false;
	}

	public void setInitialKeyEvent(KeyEvent initial) {
		this.initialKeyEvent = initial;
	}
	
	/**
	 * @see org.eclipse.gef.tools.DirectEditManager#bringDown()
	 */
	protected void bringDown() {
		if (!wantsCancel)
			commit();
		unhookCellEditor();
		super.bringDown();
	}
	
	protected void unhookCellEditor() {
		CellEditor cellEditor = getCellEditor();
		if ((cellEditor != null) && !isEditorDisposed())
			cellEditor.getControl().removeTraverseListener(returnListener);

		if (actionHandler != null) {
			actionHandler.dispose();
			actionHandler = null;
		}
		if (actionBars != null) {
			restoreEditorActions(actionBars);
			actionBars.updateActionBars();
			actionBars = null;
		}
	}
	
	protected void hookCellEditor() {
		actionBars = ((IEditorSite) editor.getAdapter(IEditorSite.class)).getActionBars();
		saveEditorActions(actionBars);
		actionHandler = new CellEditorActionHandler(actionBars);
		actionHandler.addCellEditor(getCellEditor());
		actionBars.updateActionBars();
	}

	protected void restoreEditorActions(IActionBars actionBars){
		actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), copy);
		actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), paste);
		actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), delete);
		actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(), selectAll);
		actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(), cut);
		actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), undo);
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), redo);
	}

	protected void saveEditorActions(IActionBars actionBars) {
		copy = actionBars.getGlobalActionHandler(ActionFactory.COPY.getId());
		paste = actionBars.getGlobalActionHandler(ActionFactory.PASTE.getId());
		delete = actionBars.getGlobalActionHandler(ActionFactory.DELETE.getId());
		selectAll = actionBars.getGlobalActionHandler(ActionFactory.SELECT_ALL.getId());
		cut = actionBars.getGlobalActionHandler(ActionFactory.CUT.getId());
		undo = actionBars.getGlobalActionHandler(ActionFactory.UNDO.getId());
		redo = actionBars.getGlobalActionHandler(ActionFactory.REDO.getId());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.tools.DirectEditManager#setCellEditor(org.eclipse.jface.viewers.CellEditor)
	 */
	protected void setCellEditor(CellEditor editor) {
		super.setCellEditor(editor);
		if (editor != null)
			editor.getControl().addTraverseListener(returnListener);
	}

	// This gets called from the initial show as well as from handleValueChanged
	// This is the last place after the show call that eventually calls selectAll on the Text control
	// that we can reset the selection to the end to allow seamless typing from the initialKeyEvent
	// however, we now need to ensure that we do this once after the selectAll call has been made
	// because it is possible to get a handleValueChanged prior to the call to show.
	// Checking the selection is a good way to determine this. 
	public void showFeedback() {
		super.showFeedback();
		if (initialKeyEvent != null) {
			Text text = (Text) getCellEditor().getControl(); /* @see createCellEditorOn */
			String string = text.getText();
			String selectionText = text.getSelectionText();
			text.setSelection(text.getText().length());
			if (selectionText.equals(string))
				initialKeyEvent = null;
		}
	}
	
	protected abstract String getPropertyPath();
	protected abstract EObject getInstance();

	/**
	 * Creates the cell editor on the given composite.  The cell editor is created by 
	 * instantiating the cell editor type passed into this DirectEditManager's constuctor.
	 * @param composite the composite to create the cell editor on
	 * @return the newly created cell editor
	 */
	protected CellEditor createCellEditorOn(Composite composite) {
		String propertyPath = getPropertyPath();
		NodePathLookupResult lookupResult = ModelUtils.readProperty(getInstance(), propertyPath, false);
		if (lookupResult.status == null) {
			IPropertyDescriptor[] propertyDescriptors = lookupResult.properties.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				IPropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
				if (descriptor.getId().equals(propertyName)) {
					CellEditor cellEditor;
					if (descriptor instanceof IPropertyDescriptorExtensions) {
						IPropertyDescriptorExtensions pde = (IPropertyDescriptorExtensions) descriptor;
						cellEditor = pde.createDirectLabelPropertyEditor(composite, SWT.WRAP);
					}
					else {
						cellEditor = descriptor.createPropertyEditor(composite);
					}
					// Since we now get the cell editor from the property descriptor
					// instead of creating it ourselves here, we should ensure this is a TextCellEditor
					// (or null)
					Check.checkContract(cellEditor == null || cellEditor instanceof TextCellEditor);
					return cellEditor;
				}
			}
		}
		
		return null;
	}
	
	public boolean isDirty() {
		return super.isDirty();
	}
	
	protected boolean isEditorDisposed() {
		CellEditor cellEditor = getCellEditor();
		if (cellEditor != null) {
			Control control = cellEditor.getControl();
			return (control == null) || control.isDisposed();
		}
		
		return false;
	}

	public void commit() {
		if (!isEditorDisposed())
			super.commit();
	}
}

