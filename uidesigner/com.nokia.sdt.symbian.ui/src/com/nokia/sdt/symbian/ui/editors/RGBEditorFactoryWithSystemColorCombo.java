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
package com.nokia.sdt.symbian.ui.editors;


import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;


import java.util.*;

public class RGBEditorFactoryWithSystemColorCombo extends AbstractRGBEditorFactory {

	public ICellEditorValidator createCellEditorValidator(EObject obj, String propertyPath) {
		return new RGBValidator();
	}

	public CellEditor createCellEditor(Composite parent, EObject object, String propertyPath) {
		CellEditor result = new RGBCellEditor(createLabelProvider(object, propertyPath));
		result.setValidator(createCellEditorValidator(object, propertyPath));
		result.create(parent);
		return result;
	}

    static class RGBValidator implements ICellEditorValidator {
    	
    	RGBValidator() {
    		initSysColorMaps();
    	}

		public String isValid(Object value) {
			boolean valid = localizedToSysColor.containsKey(value);
			if (!valid)
				valid = (value instanceof String) && ((String)value).length() == 0;
			if (!valid)
				valid = valueAsRGB(value, null) != null;
			String result = null;
			if (!valid) {
				result = Messages.getString("RGBEditorFactory.3"); //$NON-NLS-1$
			}
			return result;
		}
    	
    }
	
	static class RGBCellEditor extends DialogCellEditor {
		
		private ILabelProvider labelProvider;
		private CCombo comboBox;
		private Button dialogButton;
		private String comboBoxItems[];
		
		RGBCellEditor(ILabelProvider labelProvider) {
			initSysColorMaps();
			this.labelProvider = labelProvider;
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
		      comboBox = new CCombo(cell, getStyle());
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
		            		RGBCellEditor.this.focusLost();
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

		private void populateComboBoxItems() {
			comboBoxItems = (String[]) localizedToSysColor.keySet().toArray(
							new String[localizedToSysColor.size()]);
			Arrays.sort(comboBoxItems, String.CASE_INSENSITIVE_ORDER);
			
			comboBox.add(noneDisplayString);
			
			for (int i = 0; i < comboBoxItems.length; i++) {
				comboBox.add(comboBoxItems[i]);
			}
	    }
		
		protected Object openDialogBox(Control cellEditorWindow) {
			ColorDialog dialog = new ColorDialog(cellEditorWindow.getShell());
			RGB value = valueAsRGB(currentComboBoxValue(), null);
			if (value != null)
				dialog.setRGB((RGB) value);

			value = dialog.open();
			return rgbAsText(dialog.getRGB());
		}
		
		protected void updateContents(Object object) {
			String text = labelProvider.getText(object);
			comboBox.setText(text);
		}
		
		String comboBoxSelectionToValue(int selectionIndex) {
			String result = null;
			// the control has one more item than our array. The none item is first
			if (selectionIndex >= 0 && selectionIndex <= comboBoxItems.length) {
				if (selectionIndex == 0)
					result = noneValue;
				else
					result = comboBoxItems[selectionIndex-1];
			}
			return result;
		}
		
		String currentComboBoxValue() {
			String result = null;
			result = comboBoxSelectionToValue(comboBox.getSelectionIndex());
			if (result == null) {
				result = comboBox.getText();
			}
			return result;
		}
		
		void applyEditorValueAndDeactivate() {
			String newValue = currentComboBoxValue();
	
			doSetValue(newValue);
			markDirty();
			boolean isValid = isCorrect(newValue);
			setValueValid(isValid);
			fireApplyEditorValue();
			deactivate();
		}

		    /*
		     *  (non-Javadoc)
		     * @see org.eclipse.jface.viewers.CellEditor#focusLost()
		     */
		    protected void focusLost() {
		        if (isActivated()) {
		            applyEditorValueAndDeactivate();
		        }
		    }

		    /*
		     *  (non-Javadoc)
		     * @see org.eclipse.jface.viewers.CellEditor#keyReleaseOccured(org.eclipse.swt.events.KeyEvent)
		     */
		    protected void keyReleaseOccured(KeyEvent keyEvent) {
		        if (keyEvent.character == '\u001b') { // Escape character
		            fireCancelEditor();
		        } else if (keyEvent.character == '\t') { // tab key
		            applyEditorValueAndDeactivate();
		        }
		    }
		
	}
}
