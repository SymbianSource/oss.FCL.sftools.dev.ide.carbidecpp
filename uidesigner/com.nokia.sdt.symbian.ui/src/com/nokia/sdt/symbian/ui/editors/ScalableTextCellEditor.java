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

import com.nokia.sdt.component.symbian.properties.DelimitedTextCellEditor;
import com.nokia.sdt.component.symbian.properties.Messages;
import com.nokia.sdt.symbian.ScalableText;

import org.eclipse.swt.widgets.Composite;

/**
 * A cell editor for editing S60 scalable text. The
 * normal text cell editor is used for editing the complete
 * string. A dialog is available for editing variants with
 * each variant on a separate line.
 */
public class ScalableTextCellEditor extends DelimitedTextCellEditor {

	public ScalableTextCellEditor(Composite parent) {
		super(parent);
	}

	@Override
	protected String[] splitDelimitedText(String delimitedText) {
		return ScalableText.splitScalableText(delimitedText);
	}

	@Override
	protected String getDelimiter() {
		return ScalableText.STR_SEPARATOR;
	}

	@Override
	protected String getDialogPrompt() {
   		return Messages.getString("ScalableTextCellEditor.dialogPrompt"); //$NON-NLS-1$
	}
}