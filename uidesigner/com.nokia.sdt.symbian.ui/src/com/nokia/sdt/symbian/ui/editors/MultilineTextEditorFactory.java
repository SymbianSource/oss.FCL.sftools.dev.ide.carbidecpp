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
import com.nokia.sdt.component.symbian.properties.DelimitedTextCellEditor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;

import java.util.regex.Pattern;

public class MultilineTextEditorFactory extends AbstractPropertyEditorFactory {

	private static String delimiter = "\u2029";
	private static Pattern pattern = Pattern.compile(delimiter);

	@Override
	public CellEditor createCellEditor(Composite parent, EObject object, String propertyPath) {
		return new DelimitedTextCellEditor(parent) {

			@Override
			protected String[] splitDelimitedText(String delimitedText) {
				String[] result = null;
				if (delimitedText != null) {
					result = pattern.split(delimitedText);
				}
				return result;
			}

			@Override
			protected String getDelimiter() {
				return delimiter;
			}

			@Override
			protected String getDialogPrompt() {
				return Messages.getString("MultilineTextEditorFactory.dialogPrompt"); //$NON-NLS-1$
			}
		};
	}

}
