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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs;

import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.HelpContexts;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

public class EditLibraryDialog extends ValidatingDialog {
	
	private Text libraryTextControl;
	private String libraryName;

	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public EditLibraryDialog(Shell parentShell, String initialName) {
		super(parentShell);
		this.libraryName = initialName;
		setHelpAvailable(true);
	}
	
	public IStatus validate() {
		IStatus result = Status.OK_STATUS;
		if (libraryTextControl.getText().length() == 0) {
			result = new Status(IStatus.ERROR, ProjectUIPlugin.PLUGIN_ID, 0, 
					Messages.getString("EditLibraryDialog.libNameValidationErr"), //$NON-NLS-1$
					null);
		}
		return result;
	}

	@Override
	protected void captureResults() {
		libraryName = libraryTextControl.getText();
	}

	public String getLibraryName() {
		return libraryName;
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		final Label prompt = new Label(container, SWT.WRAP);
		prompt.setText(Messages.getString("EditLibraryDialog.editLibPrompt")); //$NON-NLS-1$

		libraryTextControl = new Text(container, SWT.BORDER);
		libraryTextControl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		if (libraryName != null) {
			libraryTextControl.setText(libraryName);
			libraryTextControl.selectAll();
		}
		
		libraryTextControl.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				boolean emptyText = TextUtils.isEmpty(libraryTextControl.getText());
				getButton(IDialogConstants.OK_ID).setEnabled(!emptyText);
			}
		});
		
		return container;
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(394, 133);
	}
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("EditLibraryDialog.editLibDialogTitle")); //$NON-NLS-1$
		WorkbenchUtils.setHelpContextId(newShell, HelpContexts.EDIT_LIBRARY_DIALOG);
	}

	public Text getLibraryNameText() {
		return libraryTextControl;
	}

}
