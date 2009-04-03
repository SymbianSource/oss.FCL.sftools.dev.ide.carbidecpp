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
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;


public class RGBEditorFactory extends AbstractRGBEditorFactory {

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
				result = Messages.getString("RGBEditorFactory.4"); //$NON-NLS-1$
			}
			return result;
		}
    	
    }
	    
	static class RGBCellEditor extends DialogCellEditor {
		
		private ILabelProvider labelProvider;
		private Button dialogButton;
		private Text textEditor;
		
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
			textEditor = new Text(cell, getStyle());
			textEditor.setFont(cell.getFont());
			
	        textEditor.addKeyListener(new KeyAdapter() {
	            public void keyPressed(KeyEvent e) {
	                keyReleaseOccured(e);
	            }
	        });

	        textEditor.addSelectionListener(new SelectionAdapter() {
	            public void widgetDefaultSelected(SelectionEvent event) {
	                applyEditorValueAndDeactivate();
	            }
	        });

	        textEditor.addTraverseListener(new TraverseListener() {
	            public void keyTraversed(TraverseEvent e) {
	                if (e.detail == SWT.TRAVERSE_ESCAPE
	                        || e.detail == SWT.TRAVERSE_RETURN) {
	                    e.doit = false;
	                }
	            }
	        });

	        textEditor.addFocusListener(new FocusAdapter() {
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

			return textEditor;
		}
		
		@Override
		protected void doSetFocus() {
			// override this because otherwise, 
			// editor tries to set the value and keeps combo from activating!
			textEditor.setFocus();
		}

		
		protected Object openDialogBox(Control cellEditorWindow) {
			ColorDialog dialog = new ColorDialog(cellEditorWindow.getShell());
			RGB value = valueAsRGB(currentTextEditorValue(), null);
			if (value != null)
				dialog.setRGB((RGB) value);

			value = dialog.open();
			return rgbAsText(dialog.getRGB());
		}
		
		protected void updateContents(Object object) {
			String text = labelProvider.getText(object);
			textEditor.setText(text);
		}
		
		
		String currentTextEditorValue() {
			String result = null;
			result = textEditor.getText();
			return result;
		}
		
		void applyEditorValueAndDeactivate() {
			String newValue = currentTextEditorValue();
	
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
