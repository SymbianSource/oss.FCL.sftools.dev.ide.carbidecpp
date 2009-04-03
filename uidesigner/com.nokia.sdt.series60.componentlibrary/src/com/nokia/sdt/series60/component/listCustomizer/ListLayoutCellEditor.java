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
package com.nokia.sdt.series60.component.listCustomizer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

class ListLayoutCellEditor extends DialogCellEditor {

	/**
	 * 
	 */
	CCombo comboBox;
	int comboBoxSelection;
	private Button dialogButton;

	private ILabelProvider labelProvider;

	private EObject instance;
	private ListLayoutData data;
	
	public ListLayoutCellEditor(
			ILabelProvider labelProvider,
			EObject instance) {
		this.data = new ListLayoutData(ListLayoutData.getInstanceListBoxStyles(instance));
		this.labelProvider = labelProvider;
		this.instance = instance;
	}

	public void dispose() {
		labelProvider.dispose();
		super.dispose();
	}

	@Override
	protected Button createButton(Composite parent) {
		dialogButton = super.createButton(parent);
		return dialogButton;
	}

	protected Control createContents(Composite cell) {
		comboBox = new CCombo(cell, getStyle() | SWT.READ_ONLY);
		comboBox.setFont(cell.getFont());

		comboBox.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				keyReleaseOccured(e);
			}
		});

		comboBox.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent event) {
				applyEditorValueAndDeactivate();
			}

			public void widgetSelected(SelectionEvent event) {
				comboBoxSelection = comboBox.getSelectionIndex();
			}
		});

		comboBox.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_ESCAPE
						|| e.detail == SWT.TRAVERSE_RETURN) {
					e.doit = false;
				}
			}
		});

		comboBox.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
            	// If the dialog button is the new focus then don't trigger the whole process
            	// for applying the current editor value, as the cell editor is still active.
            	// Needed because UI Designer's PropertySheetEntry override disposes of the
				// active cell editor whenever a value is applied.
            	if (!dialogButton.isFocusControl()) {
            		ListLayoutCellEditor.this.focusLost();
            	}
			}
		});

		populateComboBoxItems();
		return comboBox;
	}

	
	@Override
	protected void doSetFocus() {
		// override this because otherwise, 
		// editor tries to set the value and keeps combo from activating!
		comboBox.setFocus();
	}

	protected void populateComboBoxItems() {
		comboBox.setItems(data.orderedDisplayValues);
	}

	protected String comboBoxSelectionToValue(int selectionIndex) {
		return data.displayToValueMap.get(comboBox.getItem(selectionIndex));
	}


	protected Object openDialogBox(Control cellEditorWindow) {
		ListLayoutEditorDialog dialog = new ListLayoutEditorDialog(
				cellEditorWindow.getShell(), instance);
		dialog.setValue(getValue().toString());
		int ret = dialog.open();
		Object result = null;
		if (ret == Dialog.OK) {
			result = dialog.getValue();
		}

		return result;
	}

	protected void updateContents(Object value) {
		String text = labelProvider.getText(value);
		super.updateContents(text);
		comboBox.setText(text);
	}

	void applyEditorValueAndDeactivate() {
		String newValue = null;
		// must set the selection before getting value
		comboBoxSelection = comboBox.getSelectionIndex();
		newValue = comboBoxSelectionToValue(comboBoxSelection);
		if (newValue == null)
			newValue = comboBox.getText();

		doSetValue(newValue);
		markDirty();
		boolean isValid = isCorrect(newValue);
		setValueValid(isValid);
		fireApplyEditorValue();
		deactivate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.CellEditor#focusLost()
	 */
	protected void focusLost() {
		if (isActivated()) {
			applyEditorValueAndDeactivate();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.CellEditor#keyReleaseOccured(org.eclipse.swt.events.KeyEvent)
	 */
	protected void keyReleaseOccured(KeyEvent keyEvent) {
		if (keyEvent.character == SWT.ESC) {
			fireCancelEditor();
		} else if (keyEvent.character == SWT.TAB) {
			applyEditorValueAndDeactivate();
		}
	}
}