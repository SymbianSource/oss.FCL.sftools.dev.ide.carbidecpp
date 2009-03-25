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

import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

import org.eclipse.cdt.launch.ui.CLaunchConfigurationTab;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

import java.io.File;

public class SophiaTargetInterfaceTab extends CLaunchConfigurationTab {

	// Radio Buttons

	// Boolean widgets
	private Button viewMessages;
	private Combo jtagClockCombo;
	private Text text;
	private Combo emulatorTypeCombo;

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), LaunchTabHelpIds.STOP_MODE_SOPHIA_CONNECTION);
		
		GridLayout topLayout = new GridLayout();
		topLayout.numColumns = 2;
		comp.setLayout(topLayout);

		//createVerticalSpacer(comp, 2);
		//createSymbolicsLoadingGroup(comp, 2);
		//createVerticalSpacer(comp, 2);
		//createBooleanGroup(comp, 2);

		final Label sophiaTargetInterfaceLabel = new Label(comp, SWT.NONE);
		sophiaTargetInterfaceLabel.setText(Messages.getString("SophiaTargetInterfaceTab.0")); //$NON-NLS-1$
		
		sophiaTargetInterfaceLabel.setToolTipText(Messages.getString("SophiaTargetInterfaceTab.10")); //$NON-NLS-1$
		new Label(comp, SWT.NONE);

		text = new Text(comp, SWT.BORDER);
		text.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		text.setToolTipText(Messages.getString("SophiaTargetInterfaceTab.10")); //$NON-NLS-1$
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		final Button browseButton = new Button(comp, SWT.NONE);
		browseButton.addSelectionListener(new SelectionAdapter() {

				public void widgetSelected(SelectionEvent evt) {
					FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

					dialog.setText(Messages.getString("SophiaTargetInterfaceTab.7")); //$NON-NLS-1$
					dialog.setFilterExtensions(new String[] {"*.dll*", "*.*"}); //$NON-NLS-1$ //$NON-NLS-2$
					dialog.setFilterNames(new String[] {Messages.getString("SophiaTargetInterfaceTab.8"), Messages.getString("SophiaTargetInterfaceTab.9")}); //$NON-NLS-1$ //$NON-NLS-2$

					String result = dialog.open();
					if (result != null && new File(result).exists()) {
						text.setText(result);
						updateLaunchConfigurationDialog();
					}
				}
			});
	
		browseButton.setText(Messages.getString("SophiaTargetInterfaceTab.1")); //$NON-NLS-1$
		
		final Label jtagTypeLabel = new Label(comp, SWT.NONE);
		jtagTypeLabel.setText(Messages.getString("SophiaTargetInterfaceTab.11")); //$NON-NLS-1$
		jtagTypeLabel.setToolTipText(Messages.getString("SophiaTargetInterfaceTab.12")); //$NON-NLS-1$
		new Label(comp, SWT.NONE);

		emulatorTypeCombo = new Combo(comp, SWT.READ_ONLY);
		emulatorTypeCombo.setItems(new String[] {Messages.getString("SophiaTargetInterfaceTab.13"), Messages.getString("SophiaTargetInterfaceTab.14"), Messages.getString("SophiaTargetInterfaceTab.15"), Messages.getString("SophiaTargetInterfaceTab.16"), Messages.getString("SophiaTargetInterfaceTab.17")}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		emulatorTypeCombo.setText(Messages.getString("SophiaTargetInterfaceTab.13")); //$NON-NLS-1$
		emulatorTypeCombo.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, true, false));
		emulatorTypeCombo.setToolTipText(Messages.getString("SophiaTargetInterfaceTab.12")); //$NON-NLS-1$
		
		emulatorTypeCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		
		new Label(comp, SWT.NONE);
		
		final Label jtagRtckLabel = new Label(comp, SWT.NONE);
		jtagRtckLabel.setText(Messages.getString("SophiaTargetInterfaceTab.19")); //$NON-NLS-1$
		jtagRtckLabel.setToolTipText(Messages.getString("SophiaTargetInterfaceTab.20")); //$NON-NLS-1$
		new Label(comp, SWT.NONE);

		jtagClockCombo = new Combo(comp, SWT.READ_ONLY);
		jtagClockCombo.setItems(new String[] {Messages.getString("SophiaTargetInterfaceTab.13"), Messages.getString("SophiaTargetInterfaceTab.22"), Messages.getString("SophiaTargetInterfaceTab.23"), Messages.getString("SophiaTargetInterfaceTab.24"), Messages.getString("SophiaTargetInterfaceTab.25")}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		jtagClockCombo.setText(Messages.getString("SophiaTargetInterfaceTab.13")); //$NON-NLS-1$
		jtagClockCombo.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, true, false));
		jtagClockCombo.setToolTipText(Messages.getString("SophiaTargetInterfaceTab.20")); //$NON-NLS-1$
		
		jtagClockCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		
		new Label(comp, SWT.NONE);

		viewMessages = new Button(comp, SWT.CHECK);
		viewMessages.setLayoutData(new GridData());
		viewMessages.setText(Messages.getString("SophiaTargetInterfaceTab.27")); //$NON-NLS-1$
		viewMessages.setToolTipText(Messages.getString("SophiaTargetInterfaceTab.18")); //$NON-NLS-1$
		
		viewMessages.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		new Label(comp, SWT.NONE);

		Dialog.applyDialogFont(parent);
		checkControlState();
	}


	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			text.setText(configuration.getAttribute( SettingsData.spn_SophiaSTIConn_DllPath, "C:\\CarbideIF_ARM\\WTI.dll")); //$NON-NLS-1$
			jtagClockCombo.setText(configuration.getAttribute( SettingsData.spn_SophiaSTIConn_JtagClock, "Auto")); //$NON-NLS-1$
			viewMessages.setSelection(configuration.getAttribute( SettingsData.spn_SophiaSTIConn_LogOption, false));
			emulatorTypeCombo.setText(configuration.getAttribute(SettingsData.spn_SophiaSTIConn_StiEmulatorType, Messages.getString("SophiaTargetInterfaceTab.13"))); //$NON-NLS-1$
			checkControlState();
			
		} catch (CoreException e) {
			LaunchPlugin.log(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {

		configuration.setAttribute( SettingsData.spn_SophiaSTIConn_DllPath, text.getText());
		configuration.setAttribute( SettingsData.spn_SophiaSTIConn_JtagClock, jtagClockCombo.getText());
		configuration.setAttribute( SettingsData.spn_SophiaSTIConn_LogOption, viewMessages.getSelection());
		configuration.setAttribute( SettingsData.spn_SophiaSTIConn_StiEmulatorType, emulatorTypeCombo.getText());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return Messages.getString("SophiaTargetInterfaceTab.4"); //$NON-NLS-1$
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getImage()
	 */
	public Image getImage() {
		return LaunchImages.get(LaunchImages.IMG_VIEW_CONNECTION_TAB);
	}

	protected void checkControlState()
	{
	}
	
	public boolean isValid(ILaunchConfiguration config) {
		
		setErrorMessage(null);
		setMessage(null);

		boolean result = super.isValid(config);
		if (result){
			String pcPath = text.getText().trim();
			if (pcPath.length() < 1) {
				setErrorMessage(Messages.getString("SophiaTargetInterfaceTab.5")); //$NON-NLS-1$
				return false;
			} else {
				if (!new File(pcPath).exists()) {
					setErrorMessage(Messages.getString("SophiaTargetInterfaceTab.6")); //$NON-NLS-1$
					result = false;
				}
			}
		}
		return result;
	}

	@Override
	public void activated(ILaunchConfigurationWorkingCopy workingCopy) {
		super.activated(workingCopy);
		
		// forces page to get focus so that help works without having to select some control first.
		getControl().setFocus();
	}
}