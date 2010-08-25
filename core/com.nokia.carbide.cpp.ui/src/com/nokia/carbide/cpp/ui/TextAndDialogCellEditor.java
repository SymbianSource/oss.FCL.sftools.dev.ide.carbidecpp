/*******************************************************************************
 * Copyright (c) 2010 Nokia Corporation
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Nokia - initial API and implementation
 *******************************************************************************/

package com.nokia.carbide.cpp.ui;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.*;

/**
 * This class allows a CellEditor to provide editable text as well
 * as a button which will launch a dialog to edit the data in a different way.
 * @author dpodwall
 * @author ddubrow
 * @author eswartz
 */
public abstract class TextAndDialogCellEditor extends DialogCellEditor {

	private TextAndDialogCellEditor.CustomTextCellEditor textCellEditor;
	private Text text;
	private Button dialogButton;
	
	public TextAndDialogCellEditor(Composite parent) {
		super(parent);
	}

	@Override
	protected Control createContents(Composite parent) {
		textCellEditor = new CustomTextCellEditor(parent);
		text = (Text) textCellEditor.getControl();
		
		// Forward all events from the TextCellEditor to
		// this cell editor's listener
		textCellEditor.addListener(new ICellEditorListener() {

			public void applyEditorValue() {
				// Ignore
			}

			public void cancelEditor() {
				fireCancelEditor();
			}

			public void editorValueChanged(boolean oldValidState, boolean newValidState) {
				fireEditorValueChanged(oldValidState, newValidState);
			}
		});
		
		textCellEditor.addPropertyChangeListener(new IPropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent event) {
				// text cell editor uses fireEnablementChanged
				fireEnablementChanged(event.getProperty());
			}
			
		});
		
		return text;
	}
	
	@Override
	protected Button createButton(Composite parent) {
		dialogButton = super.createButton(parent);
		return dialogButton;
	}
	
	protected Button getButton() {
		return dialogButton;
	}
	
	void applyEditorValueAndDeactivate() {
		String newValue = (String)getValue();

		doSetValue(newValue);
		markDirty();
		setValueValid(true);
		fireApplyEditorValue();
		deactivate();
	}
	
    protected void keyReleaseOccured(KeyEvent keyEvent) {
        if (keyEvent.character == '\r') { // Enter character
        	applyEditorValueAndDeactivate();
        }
        else if (keyEvent.character == '\u001b') { // Escape character
            fireCancelEditor();
        } 
        else if (keyEvent.character == '\t') { // tab key
            applyEditorValueAndDeactivate();
        }
    }

	@Override
	protected void updateContents(Object value) {
		textCellEditor.setValue(value != null ? value : ""); //$NON-NLS-1$
	}

	@Override
	protected void doSetFocus() {
		text.setVisible(true);		
		textCellEditor.setFocus();
	}

	@Override
	protected Object doGetValue() {
		String result = (String) textCellEditor.getValue();
		return result;
	}

	@Override
	protected void doSetValue(Object value) {
		textCellEditor.setValue(value);
	}

	@Override
	protected void focusLost() {
		super.focusLost();
	}

	public boolean isCopyEnabled() {
		return textCellEditor.isCopyEnabled();
	}

	public boolean isCutEnabled() {
		return textCellEditor.isCutEnabled();
	}

	public boolean isDeleteEnabled() {
		return textCellEditor.isDeleteEnabled();
	}

	public boolean isFindEnabled() {
		return textCellEditor.isFindEnabled();
	}

	public boolean isPasteEnabled() {
		return textCellEditor.isPasteEnabled();
	}

	public boolean isRedoEnabled() {
		return textCellEditor.isRedoEnabled();
	}

	public boolean isSaveAllEnabled() {
		return textCellEditor.isSaveAllEnabled();
	}

	public boolean isSelectAllEnabled() {
		return textCellEditor.isSelectAllEnabled();
	}

	public boolean isUndoEnabled() {
		return textCellEditor.isUndoEnabled();
	}

	public boolean isValueValid() {
		return textCellEditor.isValueValid();
	}

	public void performCopy() {
		textCellEditor.performCopy();
	}

	public void performCut() {
		textCellEditor.performCut();
	}

	public void performDelete() {
		textCellEditor.performDelete();
	}

	public void performFind() {
		textCellEditor.performFind();
	}

	public void performPaste() {
		textCellEditor.performPaste();
	}

	public void performRedo() {
		textCellEditor.performRedo();
	}

	public void performSelectAll() {
		textCellEditor.performSelectAll();
	}

	public void performUndo() {
		textCellEditor.performUndo();
	}
	
	static class CustomTextCellEditor extends TextCellEditor {

		public CustomTextCellEditor(Composite parent) {
			super(parent);
		}

		@Override
		protected void focusLost() {
			// don't deactivate everything when focus is lost,
			// it will be handled in the outer cell editor
			if (isActivated()) {
				fireApplyEditorValue();
			}		
		}
	}
	
}



