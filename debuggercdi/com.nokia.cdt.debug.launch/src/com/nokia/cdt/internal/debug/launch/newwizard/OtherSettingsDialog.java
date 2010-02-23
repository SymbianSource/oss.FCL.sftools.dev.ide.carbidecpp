/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.cdt.internal.debug.launch.newwizard;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.PreferencesUtil;

import com.nokia.cdt.internal.debug.launch.newwizard.LaunchWizardData.EBuildBeforeLaunchOption;

/**
 *	This dialog allows in-depth configuration of the other settings in the launch.
 *  Currently this only covers the build-before-launch options.
 */
public class OtherSettingsDialog extends AbstractLaunchSettingsDialog {

	private Button fDisableBuildButton;
	private Button fEnableBuildButton;
	private Button fWorkspaceSettingsButton;
	private Link fWorkspaceSettingsLink;

	protected OtherSettingsDialog(Shell shell, LaunchWizardData data) {
		super(shell, data);
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = initDialogArea(parent, 
				Messages.getString("OtherSettingsDialog.Title"),  //$NON-NLS-1$
				LaunchWizardHelpIds.WIZARD_DIALOG_OTHER_SETTINGS);

		String description = Messages.getString("OtherSettingsDialog.Desc1") +  //$NON-NLS-1$
		Messages.getString("OtherSettingsDialog.Desc2") + //$NON-NLS-1$
		Messages.getString("OtherSettingsDialog.Desc3"); //$NON-NLS-1$
		
		final Label label = new Label(composite, SWT.WRAP);
		label.setText(description);
		GridDataFactory.fillDefaults().grab(true, false).hint(400, SWT.DEFAULT).applyTo(label);
		
		// spacer
		new Label(composite, SWT.NONE);
		
		final Composite radio = new Composite(composite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(radio);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(radio);
		
		composite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				label.pack();
			}
		});

		fDisableBuildButton = new Button(radio, SWT.RADIO);
		fDisableBuildButton.setText(Messages.getString("OtherSettingsDialog.DisableButtonLabel")); //$NON-NLS-1$
		fDisableBuildButton.setToolTipText(Messages.getString("OtherSettingsDialog.DisableButtonToolTip")); //$NON-NLS-1$
		fDisableBuildButton.setData(UID, "OtherSettingsDialog.disableBuildButton"); //$NON-NLS-1$
		
		GridDataFactory.fillDefaults().span(2, 1).applyTo(fDisableBuildButton);
		
		fDisableBuildButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				data.setBuildBeforeLaunchOption(EBuildBeforeLaunchOption.NEVER);
			}
		});
		
		
		fEnableBuildButton = new Button(radio, SWT.RADIO);
		fEnableBuildButton.setText(Messages.getString("OtherSettingsDialog.EnableButtonLabel")); //$NON-NLS-1$
		fEnableBuildButton.setToolTipText(Messages.getString("OtherSettingsDialog.EnableButtonToolTip")); //$NON-NLS-1$
		fEnableBuildButton.setData(UID, "OtherSettingsDialog.enableBuildButon"); //$NON-NLS-1$
		
		GridDataFactory.fillDefaults().span(2, 1).applyTo(fEnableBuildButton);
		
		fEnableBuildButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				data.setBuildBeforeLaunchOption(EBuildBeforeLaunchOption.ALWAYS);
			}
		});
		

		fWorkspaceSettingsButton = new Button(radio, SWT.RADIO);
		fWorkspaceSettingsButton.setText(Messages.getString("OtherSettingsDialog.WorkspaceSettingsButtonLabel")); //$NON-NLS-1$
		fWorkspaceSettingsButton.setToolTipText(Messages.getString("OtherSettingsDialog.WorkspaceSettingsButtonToolTip")); //$NON-NLS-1$
		fWorkspaceSettingsButton.setData(UID, "OtherSettingsDialog.workspaceSettingsButton"); //$NON-NLS-1$
		
		GridDataFactory.swtDefaults().span(1, 1).applyTo(fWorkspaceSettingsButton);

		fWorkspaceSettingsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				data.setBuildBeforeLaunchOption(EBuildBeforeLaunchOption.USE_WORKSPACE_SETTING);
			}
		});
		
		
		fWorkspaceSettingsLink = new Link(radio, SWT.NONE);
		fWorkspaceSettingsLink.setText(Messages.getString("OtherSettingsDialog.WorkspaceSettingsLinkLabel")); //$NON-NLS-1$
		fWorkspaceSettingsLink.setData(UID, "OtherSettingsDialog.workspaceSettingsLink"); //$NON-NLS-1$
		
		GridDataFactory.swtDefaults().align(SWT.RIGHT, SWT.CENTER).grab(true, false).span(1, 1).applyTo(fWorkspaceSettingsLink);
		
		fWorkspaceSettingsLink.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				PreferencesUtil.createPreferenceDialogOn(
						radio.getShell(), 
						Messages.getString("OtherSettingsDialog.WorkspaceSettingsPageID"), //$NON-NLS-1$
						null, 
						null).open();
				validate();
			}
		});
		
		switch (data.getBuildBeforeLaunch()) {
		case ALWAYS:
			fEnableBuildButton.setSelection(true);
			fEnableBuildButton.setFocus();
			break;
		case NEVER:
			fDisableBuildButton.setSelection(true);
			fDisableBuildButton.setFocus();
			break;
		case USE_WORKSPACE_SETTING:
			fWorkspaceSettingsButton.setSelection(true);
			fWorkspaceSettingsButton.setFocus();
			break;
		}
		
		validate();

		return radio;
	}
	
	@Override
	protected void validate() {
		IStatus status = Status.OK_STATUS;
		updateStatus(status);
		
		String wsState = ""; //$NON-NLS-1$
		if (data.isWorkspaceBuildBeforeLaunch())
			wsState = Messages.getString("OtherSettingsDialog.EnabledLabel"); //$NON-NLS-1$
		else
			wsState = Messages.getString("OtherSettingsDialog.DisabledLabel"); //$NON-NLS-1$
		
		fWorkspaceSettingsButton.setText(
				Messages.getString("OtherSettingsDialog.WorkspaceSettingsButtonLabel") + //$NON-NLS-1$
				wsState);
		fWorkspaceSettingsButton.pack();
		
	}
}

