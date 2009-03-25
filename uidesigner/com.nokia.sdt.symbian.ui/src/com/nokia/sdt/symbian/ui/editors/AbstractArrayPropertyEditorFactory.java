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

import com.nokia.sdt.component.property.AbstractPropertyEditorFactory;
import com.nokia.sdt.component.property.ISequencePropertySource;
import com.nokia.sdt.component.symbian.properties.ArrayEditableValue;
import com.nokia.sdt.component.symbian.properties.ArrayPropertySource;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NodePathLookupResult;
import com.nokia.sdt.ui.IDialogCellEditorActivator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import java.util.*;

public abstract class AbstractArrayPropertyEditorFactory extends AbstractPropertyEditorFactory {
	
	private final static String NO_DATA_LABEL = "{}"; //$NON-NLS-1$
	

	/**
	 * Override to customize the cell editor to inhibit adding or removing items
	 */
	protected boolean isFixedLengthArrayProperty(EObject object, Object propertyID) {
		return false;
	}
	
	@Override
	public CellEditor createCellEditor(Composite parent, EObject object, String propertyPath) {
		ArrayCellEditor result = new ArrayCellEditor(parent, object, propertyPath, 
						createLabelProvider(object, propertyPath),
						createElementLabelProvider(object, propertyPath),
						isFixedLengthArrayProperty(object, propertyPath));
		return result;
	}

	@Override
	public ILabelProvider createLabelProvider(EObject object, String propertyPath) {
		return new ArrayLabelProvider(createElementLabelProvider(object, propertyPath));
	}
	
	static class ArrayLabelProvider extends LabelProvider {
		
		private ILabelProvider elementLabelProvider;
		
		ArrayLabelProvider(ILabelProvider elementLabelProvider) {
			this.elementLabelProvider = elementLabelProvider;
		}
		
		public String getText(Object element) {
			if (element == null)
				return NO_DATA_LABEL;

			// we only accept ArrayPropertySource or List
			List elements;
			ISequencePropertySource sps = null;
			if (element instanceof ArrayEditableValue) {
				ArrayEditableValue aev = (ArrayEditableValue) element;
				sps = aev.getValue();
			}
			else if (element instanceof ArrayPropertySource) {
				sps = (ISequencePropertySource) element;
			}
			
			if (sps != null) {
				elements = new ArrayList();
				for (Iterator iter = sps.iterator(); iter.hasNext();) {
					elements.add(iter.next());
				}
			}
			else {
				elements = (List) element;
			}
			
			if (elements.isEmpty())
				return NO_DATA_LABEL;

			StringBuffer stringValue = new StringBuffer("{ "); //$NON-NLS-1$
			for (Iterator iter = elements.iterator(); iter.hasNext();) {
				stringValue.append(elementLabelProvider.getText(iter.next()));
				if (iter.hasNext())
					stringValue.append(", "); //$NON-NLS-1$
			}
			stringValue.append(" }"); //$NON-NLS-1$
			
			return stringValue.toString();
		}
	}


	/**
	 * Return a label provider used in the array editor to create strings for a single
	 * array element. 
	 */
	public abstract ILabelProvider createElementLabelProvider(EObject object, Object propertyID);
	
	static class ArrayCellEditor extends DialogCellEditor implements IDialogCellEditorActivator {
		
		private EObject object;
		private String propertyPath;
		private ILabelProvider arrayLabelProvider;
		private ILabelProvider elementLabelProvider;
		private boolean fixedLengthArray;

		public ArrayCellEditor(Composite parent,
				EObject object, String propertyPath,
				ILabelProvider arrayLabelProvider,
				ILabelProvider elementLabelProvider,
				boolean fixedLengthArray) {
			super(parent);
			this.object = object;
			this.propertyPath = propertyPath;
			this.arrayLabelProvider = arrayLabelProvider;
			this.elementLabelProvider = elementLabelProvider;
			this.fixedLengthArray = fixedLengthArray;
		}
		
		@Override
		protected void updateContents(Object value) {
			if (arrayLabelProvider != null) {
				value = arrayLabelProvider.getText(value);
			}
			super.updateContents(value);
		}
		
		private ArrayEditableValue getArrayEditableValue() {
			NodePathLookupResult nplr = ModelUtils.readProperty(object, propertyPath, false);
			Object pv = nplr.result;
			if (!(pv instanceof ISequencePropertySource)) {
				throw new IllegalArgumentException();
			}
			ISequencePropertySource sps = (ISequencePropertySource) pv;
			Object editableValue = sps.getEditableValue();
			if (!(editableValue instanceof ArrayEditableValue)) {
				throw new IllegalArgumentException();
			}
			ArrayEditableValue arrayEditValue = (ArrayEditableValue) editableValue;
			return arrayEditValue;
		}
		
		@Override
		protected Object openDialogBox(Control cellEditorWindow) {
			ArrayEditorDialog dialog = new ArrayEditorDialog(cellEditorWindow.getShell(), 
						getArrayEditableValue(), elementLabelProvider, fixedLengthArray);
			int resultCode = dialog.open();
			ArrayEditableValue result = null;
			if (resultCode == Dialog.OK) {
				result = dialog.getValue();
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
}
