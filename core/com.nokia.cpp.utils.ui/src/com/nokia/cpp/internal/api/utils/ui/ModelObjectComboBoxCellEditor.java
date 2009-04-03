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
package com.nokia.cpp.internal.api.utils.ui;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import java.util.*;

/**
 * This is a cell editor that manages a list of model objects
 * whose strings are provided through an ILabelProvider.  It is
 * read only so items cannot be typed in.
 * <p>
 * Use instead of EMF's ExtendedComboBoxCellEditor which is overloaded
 * with buggy features that make it hard to use.
 *
 */
public class ModelObjectComboBoxCellEditor extends ComboBoxCellEditor {

	protected List<?> modelItems;

	public ModelObjectComboBoxCellEditor(Composite parent,
			List<?> items,
			ILabelProvider labelProvider) {
		super(parent, getItemStrings(items, labelProvider), SWT.READ_ONLY);
		this.modelItems = items;
	}

	public static String[] getItemStrings(List<?> items, ILabelProvider labelProvider) {
		String[] array = new String[items.size()];
		int i = 0;
		for (Object obj : items) {
			array[i++] = labelProvider.getText(obj); 
		}
		return array;
	}
	
	@Override
	protected Object doGetValue() {
		Object value = super.doGetValue();
		if (value instanceof Number) {
			// look up model at index
			int index = ((Number) value).intValue();
			if (index < 0)
				return null;
			value = modelItems.get(index);
		}
		return value;
	}
	
	@Override
	protected void doSetValue(Object value) {
		// look up the model object to reestablish index
		int index = modelItems.indexOf(value);
		if (index != -1) {
			super.doSetValue(index);
		}
	}

	public void setModelItems(List<?> items, ILabelProvider labelProvider) {
		setItems(getItemStrings(items, labelProvider));
		this.modelItems = items;
	}
}
