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
package com.nokia.sdt.series60.component.listCustomizer;

import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

import java.util.Collections;

public class ListLayoutEditorDialog extends org.eclipse.jface.dialogs.Dialog {

	private ListCustomizerComposite customizer;
	private EObject instance;
	private String value;
	
	public ListLayoutEditorDialog(Shell parentShell, EObject instance) {
		super(parentShell);
		this.instance = instance;
		setShellStyle(SWT.CLOSE | SWT.TITLE | SWT.BORDER
	               | SWT.APPLICATION_MODAL | SWT.RESIZE | getDefaultOrientation());
	}

	protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);

        GridData data = new GridData(GridData.FILL_BOTH);
        data.heightHint = 300;
        data.widthHint = 400;
        customizer = new ListCustomizerComposite(composite, SWT.NONE, instance);
        customizer.setLayoutData(data);
        
		Check.checkState(value != null);
		customizer.getThumbnailViewer().setSelection(new StructuredSelection(Collections.singletonList(value)), true);
		
		return composite;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	protected Point getInitialSize() {
		return new Point(630, 400);
	}
	
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("ListLayoutEditorDialog.Title")); //$NON-NLS-1$
	}

	public String getValue() {
		return value = customizer.getValue();
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
