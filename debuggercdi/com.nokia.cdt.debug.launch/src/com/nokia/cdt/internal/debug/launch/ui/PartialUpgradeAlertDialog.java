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
package com.nokia.cdt.internal.debug.launch.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class PartialUpgradeAlertDialog extends StatusDialog {

	private IProject project;
	private Button dontAskAgain;
	private boolean dontAskAgainValue;
	
	/**
	 * Create the dialog
	 * @param parent
	 */
	public PartialUpgradeAlertDialog(Shell parent, IProject project) {
		super(parent);
		this.project = project;
		setTitle(Messages.getString("PartialUpgradeAlertDialog.title")); //$NON-NLS-1$
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	/**
	 * @see Windows#configureShell
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell, LaunchTabHelpIds.PARTIAL_UPGRADE_ALERT_DIALOG);
	}		

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		initializeDialogUnits(parent);
		Composite composite= (Composite) super.createDialogArea(parent);
		
		GridLayout layout = (GridLayout) composite.getLayout();
		layout.numColumns = 1;
		
		final Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.getString("PartialUpgradeAlertDialog.text")); //$NON-NLS-1$
		label.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, false));

		Link link = new Link(composite, SWT.NONE);
		link.setText("<a>" + Messages.getString("InstallationTab.puHyperlink") + "...</a>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		link.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		link.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				PreferencesUtil.createPropertyDialogOn(getShell(), project, "com.nokia.carbide.cdt.internal.builder.ui.CarbideBuildConfigurationsPage", null, null).open(); //$NON-NLS-1$
			}
		});

		dontAskAgain = new Button(composite, SWT.CHECK);
		dontAskAgain.setText(Messages.getString("PartialUpgradeAlertDialog.dontAskAgain")); //$NON-NLS-1$
		dontAskAgain.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, false));
		
		applyDialogFont(composite);		

		return composite;
	}
	
	@Override
	protected void okPressed() {
		dontAskAgainValue = dontAskAgain.getSelection();

		super.okPressed();
	}

	public boolean dontAskAgain() {
		return dontAskAgainValue;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// create OK button
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
	}
}
