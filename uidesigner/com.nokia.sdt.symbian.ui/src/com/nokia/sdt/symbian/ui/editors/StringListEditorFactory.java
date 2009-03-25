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


package com.nokia.sdt.symbian.ui.editors;

import com.nokia.sdt.component.property.AbstractPropertyEditorFactory;
import com.nokia.sdt.component.property.ISequencePropertySource;
import com.nokia.sdt.component.symbian.properties.ArrayEditableValue;
import com.nokia.sdt.component.symbian.properties.ArrayPropertySource;
import com.nokia.sdt.ui.IDialogCellEditorActivator;
import com.nokia.sdt.ui.StringListEditorDialog;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import java.util.*;

/**
 * Creates editor for string list properties. 
 *
 */
public class StringListEditorFactory extends AbstractPropertyEditorFactory {

	private final static String NO_DATA_LABEL = "{}"; //$NON-NLS-1$
	protected ILabelProvider labelProvider;
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.property.IPropertyEditorFactory#createLabelProvider(org.eclipse.emf.ecore.EObject, java.lang.Object)
	 */
	public ILabelProvider createLabelProvider(EObject object, String propertyId) {
		return labelProvider = new ILabelProvider() {
			public Image getImage(Object element) {
				return null;
			}

			public String getText(Object element) {
				if (element == null)
					return NO_DATA_LABEL;

				
				List<String> strings = stringListFromValue(element);				
				if (strings.isEmpty())
					return NO_DATA_LABEL;

				String stringValue = "{ "; //$NON-NLS-1$
				for (Iterator iter = strings.iterator(); iter.hasNext();) {
					String string = iter.next().toString();
					stringValue += TextUtils.quote(TextUtils.escape(string, '"'), '"');
					if (iter.hasNext())
						stringValue += ", "; //$NON-NLS-1$
				}
				stringValue += " }"; //$NON-NLS-1$
				
				return stringValue;
			}

			public void addListener(ILabelProviderListener listener) {
			}

			public void dispose() {
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}
			
			public void removeListener(ILabelProviderListener listener) {
			}
		};
	}

	private class StringListCellEditor extends DialogCellEditor implements IDialogCellEditorActivator {

        private ILabelProvider labelProvider_;
        
        public StringListCellEditor(ILabelProvider labelProvider) {
        	this.labelProvider_ = labelProvider;
        }

        protected void updateContents(Object value) {
        	if (labelProvider_ != null)
        		super.updateContents(labelProvider_.getText(value));
        }

        protected Object openDialogBox(Control cellEditorWindow) {
			Object value = getValue();
			String stringValue = ""; //$NON-NLS-1$
			List<String> strings = stringListFromValue(value);
			
			for (Iterator iter = strings.iterator(); iter.hasNext();) {
				String string = iter.next().toString();
				stringValue += TextUtils.escape(string, '"');
				if (iter.hasNext())
					stringValue += "\r\n"; //$NON-NLS-1$
			}
        
            StringListEditorDialog dialog = new StringListEditorDialog(
            		cellEditorWindow.getShell(),
            		Messages.getString("StringListEditorFactory.StringEditorFactoryInfoMessage")); //$NON-NLS-1$
            dialog.setValue(stringValue);
            int ret = dialog.open();
            Object result = null;
            if (ret == Dialog.OK) {
            	stringValue = dialog.getValue();
            	List listValue = Collections.EMPTY_LIST;
            	if (stringValue.length() > 0) {
            		String[] stringArr = stringValue.split("\r\n");
            		for (int i = 0; i < stringArr.length; i++) {
						stringArr[i] = TextUtils.unescape(stringArr[i]);
					}
					listValue = Arrays.asList(stringArr); //$NON-NLS-1$
            	}
            	result = new ArrayList(listValue);
            }
            
            return result;
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.ui.IDialogCellEditorActivator#invokeEditor(org.eclipse.swt.widgets.Composite)
		 */
		public Object invokeEditor(Composite parent) {
			return openDialogBox(parent);
		}
        
        
	}
	
	private static List<String> stringListFromValue(Object value) {
		List<String> result = null;
		if (value instanceof ArrayPropertySource) {
			ArrayPropertySource propertySource = (ArrayPropertySource) value;
			result = new ArrayList<String>();
			for (Iterator iter = propertySource.iterator(); iter.hasNext();) {
				result.add(iter.next().toString());
			}
		} else if (value instanceof ArrayEditableValue) {
			ArrayEditableValue arrayValue = (ArrayEditableValue) value;
			ISequencePropertySource sequence = arrayValue.getValue();
			result = new ArrayList<String>();
			for (Iterator iter = sequence.iterator(); iter.hasNext();) {
				result.add(iter.next().toString());
			}
		}
		else if (value instanceof List) {
			// no way to check if it's a List<String>
			result = (List) value;
		} else if (value != null) {
			result = new ArrayList<String>();
			result.add(value.toString());
		}
		return result;
	}
		
		/* (non-Javadoc)
	 * @see com.nokia.sdt.component.property.IPropertyEditorFactory#createCellEditor(org.eclipse.swt.widgets.Composite, org.eclipse.emf.ecore.EObject, java.lang.Object)
	 */
	public CellEditor createCellEditor(Composite parent, EObject object, String propertyId) {
		CellEditor cellEditor = new StringListCellEditor(labelProvider);
		cellEditor.create(parent);
		return cellEditor;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.property.IPropertyEditorFactory#createCellEditorValidator(org.eclipse.emf.ecore.EObject, java.lang.Object)
	 */
	public ICellEditorValidator createCellEditorValidator(EObject object, String propertyId) {
		return null;
	}

}
