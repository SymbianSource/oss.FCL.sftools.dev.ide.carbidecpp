/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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
import com.nokia.sdt.component.symbian.properties.TypeDescriptors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import java.text.MessageFormat;

/**
 * An integer cell editor that stores a value that is one less than that manipulated in the UI
 */
public class MinusOneIntEditorFactory extends AbstractPropertyEditorFactory {
	
	public ILabelProvider createLabelProvider(EObject object, String propertyPath) {
		return new LabelProvider() {
			public Image getImage(Object element) {
				return TypeDescriptors.getImage(TypeDescriptors.INTEGER_IMAGE);
			}
			
			public String getText(Object element) {
				if (element instanceof Integer) {
					Integer integer = (Integer) element;
					int plusOneVal = integer.intValue() + 1;
					return "" + plusOneVal; //$NON-NLS-1$
				}

				return "" + element; //$NON-NLS-1$
			}
		};
	}
	
	public ICellEditorValidator createCellEditorValidator(EObject object, String propertyPath) {
		return new ICellEditorValidator() {
			public String isValid(Object value) {
				try {
					Integer.parseInt(value.toString());
				} catch (NumberFormatException e) {
					return MessageFormat.format(Messages.getString("MinusOneIntEditorFactory.InvalidIntegerError"), new Object[] { value }); //$NON-NLS-1$
				}
				return null;
			}
		};
	}

	public CellEditor createCellEditor(Composite parent, EObject object, String propertyPath) {		
		CellEditor cellEditor = new TextCellEditor() {
			public Object doGetValue() {
				try {
					int rawValue = Integer.parseInt((String) super.doGetValue()) - 1;
					return new Integer(rawValue);
				} catch (NumberFormatException e) {
				}
				return new Integer(0);
			}

			public void doSetValue(Object value) {
				if (value instanceof Integer) {
					int plusOneValue = ((Integer) value).intValue() + 1;
					super.doSetValue("" + plusOneValue); //$NON-NLS-1$
				}
			}
		};
		cellEditor.setValidator(createCellEditorValidator(object, propertyPath));
		cellEditor.create(parent);
		return cellEditor;
	}
	
}
