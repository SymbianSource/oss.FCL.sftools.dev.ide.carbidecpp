/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.component.symbian.properties;

import com.nokia.sdt.ui.StringListEditorDialog;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

/**
 * A cell editor for editing S60 scalable text. The
 * normal text cell editor is used for editing the complete
 * string. A dialog is available for editing variants with
 * each variant on a separate line.
 */
public abstract class DelimitedTextCellEditor extends DialogCellEditor {

	private CustomTextCellEditor textCellEditor;
	private Text text;
	private Button dialogButton;
	public static final char QUOTE_CHAR = '"';
	
	public DelimitedTextCellEditor(Composite parent) {
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
			//	fireApplyEditorValue();				
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
		
        text.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
            	// If the dialog button is the new focus then don't trigger the whole process
            	// for applying the current editor value, as the cell editor is still active.
            	// Needed because UI Designer's PropertySheetEntry override disposes of the
            	// active cell editor whenever a value is applied.
            	if (!dialogButton.isDisposed() && !dialogButton.isFocusControl()) {
            		DelimitedTextCellEditor.this.focusLost();
            	}
            }
        });       
 
		return text;
	}
	
	@Override
	protected Button createButton(Composite parent) {
		dialogButton = super.createButton(parent);
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
        if (keyEvent.character == '\u001b') { // Escape character
            fireCancelEditor();
        } else if (keyEvent.character == '\t') { // tab key
            applyEditorValueAndDeactivate();
        }
    }
    
    protected abstract String[] splitDelimitedText(String delimitedText);
    
    protected abstract String getDelimiter();
    
    protected abstract String getDialogPrompt();

	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		Object value = getValue();
		StringBuffer buf = new StringBuffer(""); //$NON-NLS-1$
		String[] strings = splitDelimitedText(value.toString());
		String lineSeparator = System.getProperty("line.separator"); //$NON-NLS-1$
		boolean first = true;
		for (String s : strings) {
			if (!first) {
				buf.append(lineSeparator);
			}
			buf.append(s);
			first = false;
		}
		   
		StringListEditorDialog dialog = new StringListEditorDialog(
        						cellEditorWindow.getShell(), getDialogPrompt());
        dialog.setValue(buf.toString());
        int ret = dialog.open();
        Object result = null;
        if (ret == Dialog.OK) {
        	String newText = dialog.getValue();
        	if (newText.length() > 0) {
        		buf = new StringBuffer(""); //$NON-NLS-1$
        		strings = newText.split(lineSeparator);
        		first = true;
        		for (String s : strings) {
        			if (!first) {
        				buf.append(getDelimiter());
        			}
        			buf.append(s);
        			first = false;
        		}
        	}
        	result = buf.toString();
        }
        return result;
	}

	@Override
	protected void updateContents(Object value) {
		textCellEditor.setValue(value!= null? value : ""); //$NON-NLS-1$
	}

	@Override
	protected void doSetFocus() {
		text.setVisible(true);		
		textCellEditor.setFocus();
	}

	@Override
	protected Object doGetValue() {
		String result = (String) textCellEditor.getValue();
		if (result != null) {
			result = TextUtils.unescape(result);
		}
		return result;
	}

	@Override
	protected void doSetValue(Object value) {
		if (value instanceof String) {
			value = TextUtils.escape(value.toString(), QUOTE_CHAR);
		}
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