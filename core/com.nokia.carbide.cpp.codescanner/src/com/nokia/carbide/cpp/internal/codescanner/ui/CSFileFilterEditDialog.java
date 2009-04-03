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

package com.nokia.carbide.cpp.internal.codescanner.ui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.internal.codescanner.Messages;

/**
 * A class to handle adding/editing CodeScanner file filters.
 */
public class CSFileFilterEditDialog extends TrayDialog {
	
	private Text fileFilterText;
	private String dialogTitle;
	private String fileFilter;
	
	/**
	 * Create the dialog
	 * @param parentShell
	 * @param aString - a string containing an existing file filter to be edited
	 */
	public CSFileFilterEditDialog (Shell parentShell, String title, String aString) {
		super(parentShell);
		dialogTitle = title;
		fileFilter = aString;
	}

	/**
	 * Return the edited file filter.
	 */
	public String getFileFilter() {
		return fileFilter;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(dialogTitle);
	}
	
	/**
	 * Create contents of this dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);
		
		final Label fileFilterLabel = new Label(container, SWT.NONE);
		fileFilterLabel.setToolTipText(Messages.getString("FileFiltersEdit.EditFileFilterMessage"));
		fileFilterLabel.setText(Messages.getString("FileFiltersEdit.FileFilterLabel"));

		fileFilterText = new Text(container, SWT.BORDER | SWT.WRAP);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 200;
		fileFilterText.setLayoutData(gridData);
		fileFilterText.setText(fileFilter);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, CSUIHelpIds.CODESCANNER_FILEFILTER_EDIT_DIALOG);
		return container;
	}
		
	/**
	 * Things to do when user hit the "OK" button.
	 */
	@Override
	protected void okPressed() {
		fileFilter = fileFilterText.getText();
		super.okPressed();
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
		return new Point(300, 100);
	}

}
