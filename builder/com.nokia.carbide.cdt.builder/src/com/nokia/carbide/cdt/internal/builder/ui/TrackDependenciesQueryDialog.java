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
package com.nokia.carbide.cdt.internal.builder.ui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import com.nokia.carbide.cdt.builder.BuilderPreferenceConstants;
import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectModifier;
import com.nokia.carbide.cdt.internal.builder.CarbideProjectInfo;


public class TrackDependenciesQueryDialog extends TrayDialog {

	private Button okButton;
	private Button trackDependenciesRadio;
	private Button dontTrackDependenciesRadio;
	private Button dontAskAgainCheck;
	
	private boolean userWantsToTrackDeps = false;
	
	private ICarbideProjectInfo cpi;
	
	/**
	 * Dialog presented to user when users is building with SBSv1 and built the project first from the command-line.
	 * The user will be asked whether or not Carbide should manage the dependencies. This dialog will also handle writing
	 * the persistent data for the global and project settings.
	 * 
	 * Unpon calling open(), the dialog will return IDialogConstants for OK and CANCEL, where OK means Carbide should track dependencies.
	 * 
	 * @param shell
	 * @param cpi, the current project to save data to
	 */
	public TrackDependenciesQueryDialog(Shell shell, ICarbideProjectInfo cpi) {
		super(shell);
		this.cpi = cpi;
	}

	/**
	 * @param parentShell
	 */
	public TrackDependenciesQueryDialog(IShellProvider parentShell) {
		super(parentShell);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Label epocrootLabel = new Label(composite, SWT.WRAP);
		epocrootLabel.setLayoutData(new GridData(450, SWT.DEFAULT));
		epocrootLabel.setText(Messages.getString("TrackDependenciesQueryDialog.0"));  //$NON-NLS-1$
		
		// filler
		new Label(composite, SWT.WRAP);
		
		trackDependenciesRadio = new Button(composite, SWT.RADIO);
		trackDependenciesRadio.setText(Messages.getString("TrackDependenciesQueryDialog.1"));  //$NON-NLS-1$
		trackDependenciesRadio.setToolTipText(Messages.getString("TrackDependenciesQueryDialog.2"));  //$NON-NLS-1$
		trackDependenciesRadio.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		trackDependenciesRadio.setSelection(false);
		addButtonListener(trackDependenciesRadio);
		
		dontTrackDependenciesRadio = new Button(composite, SWT.RADIO);
		dontTrackDependenciesRadio.setText(Messages.getString("TrackDependenciesQueryDialog.3")); //$NON-NLS-1$
		dontTrackDependenciesRadio.setToolTipText(Messages.getString("TrackDependenciesQueryDialog.4"));  //$NON-NLS-1$
		dontTrackDependenciesRadio.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		dontTrackDependenciesRadio.setSelection(true);
		addButtonListener(dontTrackDependenciesRadio);
		
		dontAskAgainCheck = new Button(composite, SWT.CHECK);
		dontAskAgainCheck.setText(Messages.getString("TrackDependenciesQueryDialog.5")); //$NON-NLS-1$
		dontAskAgainCheck.setToolTipText(Messages.getString("TrackDependenciesQueryDialog.6"));  //$NON-NLS-1$
		GridData gd = new GridData();
		gd.horizontalIndent = 25;
		dontAskAgainCheck.setLayoutData(gd);
		dontAskAgainCheck.setEnabled(true);
		dontAskAgainCheck.setSelection(false);
		
		return composite;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Project rebuild notification"); //$NON-NLS-1$
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID,
		IDialogConstants.OK_LABEL, true);
	}
	
	@Override
	protected void okPressed() {
		
		if (dontTrackDependenciesRadio.getSelection() == true){
			
			ICarbideProjectModifier cpm = CarbideBuilderPlugin.getBuildManager().getProjectModifier(cpi.getProject());
			cpm.writeProjectSetting(CarbideProjectInfo.MANAGE_DEPENDENCIES, "false");
			cpm.writeProjectSetting(CarbideProjectInfo.OVERRIDE_WORKSPACE_SETTINGS_KEY, "true");
			cpm.saveChanges();
			
			if (dontAskAgainCheck.getSelection() == true){
				// set the global pref
				IPreferenceStore prefsStore = CarbideBuilderPlugin.getDefault().getPreferenceStore();
				prefsStore.setValue(BuilderPreferenceConstants.PREF_DONT_PROMPT_FOR_DEPENDENCY_MISMATCH, true);
			}
		} else {
			// nothing pref option to save
		}
		
		// cache so we can get value after widgets disposed
		userWantsToTrackDeps = trackDependenciesRadio.getSelection();
		
		super.okPressed();
	}
	
	/**
	 * Sets the listener event to a button.
	 * 
	 * @param aButton
	 */
	private void addButtonListener( final Button aButton ) {
		SelectionListener listener = new SelectionAdapter() {
			public void widgetSelected( SelectionEvent e )  {
				if (e.getSource().equals(trackDependenciesRadio)) {
					dontAskAgainCheck.setEnabled(false);
				} else if (e.getSource().equals(dontTrackDependenciesRadio)) {
					dontAskAgainCheck.setEnabled(true);
				} 
			}
		};
		aButton.addSelectionListener(listener);
	}

	@Override
	public int open() {
		super.open();
		if (userWantsToTrackDeps == true){
			return IDialogConstants.OK_ID;
		} else {
			return IDialogConstants.CANCEL_ID;
		}
	}
	
}
