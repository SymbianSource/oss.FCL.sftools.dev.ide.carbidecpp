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
package com.nokia.sdt.ui;

import com.nokia.sdt.uimodel.Messages;
import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

public class StringListEditorDialog extends Dialog {

	private static final String HELP_CONTEXT_ID = UIModelPlugin.PLUGIN_ID + "stringListEditorDialogContextId";
	private Text text;
	private String value;
	private String infoMessage;
	public StringListEditorDialog(Shell parentShell, String infoMessage) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.infoMessage = infoMessage;
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		//RowLayout layout = new RowLayout(SWT.VERTICAL);
		//container.setLayout(layout);
		
		Label label = new Label(container, SWT.WRAP);
		label.setText(infoMessage);
		
		text = new Text(container, SWT.MULTI | SWT.BORDER);
		text.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				if (e.keyCode == SWT.TAB)
					e.doit = true;
			}
		});
		text.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				value = text.getText();
			} 
		});
		text.setText(value);
		
		final GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		text.setLayoutData(gridData);
		//
        WorkbenchUtils.setHelpContextId(container, HELP_CONTEXT_ID);
		return container;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	protected Point getInitialSize() {
		return new Point(380, 260);
	}
	
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("StringListEditorDialog.dialogTitle")); //$NON-NLS-1$
	}

	public String getValue() {
		if (value == null)
			return ""; //$NON-NLS-1$
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
