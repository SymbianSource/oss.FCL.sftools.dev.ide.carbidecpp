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
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.ScalableText;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class ScalableTextEditorFactory extends AbstractPropertyEditorFactory {

	/**
	 * Cell editor for editing string properties that escapes text
	 * for editing.
	 */
	public static class StringCellEditor extends TextCellEditor {
		private static final char QUOTE_CHAR = '"';

		public StringCellEditor(Composite composite, int style) {
			super(composite, style);
		}
		
		public Object doGetValue() {
			String result = (String) super.doGetValue();
			if (result != null) {
				result = TextUtils.unescape(result);
			}
			return result;
		}
		
		public void doSetValue(Object value) {
			if (value instanceof String) {
				value = TextUtils.escape(value.toString(), QUOTE_CHAR);
			}
			super.doSetValue(value);
		}
	}

	@Override
	public CellEditor createCellEditor(Composite parent, EObject object, String propertyPath) {
		if (ScalableText.isScalableTextAvailable(ModelUtils.getModel(object))) {
			return new ScalableTextCellEditor(parent);
		}
		
		return new StringCellEditor(parent, SWT.NONE);
	}

}
