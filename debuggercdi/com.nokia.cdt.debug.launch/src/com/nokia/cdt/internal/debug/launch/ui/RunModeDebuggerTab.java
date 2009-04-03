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

import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.launch.ui.CLaunchConfigurationTab;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

import cwdbg.PreferenceConstants;

public class RunModeDebuggerTab extends CLaunchConfigurationTab {

	private Button breakAtMain;
	private Text mainBreakSymbol;
	private Button viewUnframed;
	private Button viewCommMessages;
	private Label trkTimeoutLabel;
	private Text trkTimeout;
	private Button autoButton;
	private Button armButton;
	private Button thumbButton;

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), LaunchTabHelpIds.RUN_MODE_DEBUGGER);
		
		GridLayout topLayout = new GridLayout();
		topLayout.numColumns = 2;
		comp.setLayout(topLayout);

		createVerticalSpacer(comp, 2);
		createBooleanGroup(comp, 2);
		createVerticalSpacer(comp, 2);
		createArmModeGroup(comp, 2);

		Dialog.applyDialogFont(parent);
		checkControlState();
	}


	protected void createArmModeGroup(Composite parent, int colSpan) {
		Group group = new Group(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		group.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = colSpan;
		group.setLayoutData(gd);
		group.setText(Messages.getString("RunModeDebuggerTab.0")); //$NON-NLS-1$
		group.setFont(parent.getFont());
		group.setToolTipText(Messages.getString("RunModeDebuggerTab.1")); //$NON-NLS-1$

		autoButton = new Button(group, SWT.RADIO);
		autoButton.setText(Messages.getString("RunModeDebuggerTab.17")); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalSpan = colSpan;
		autoButton.setLayoutData(gd);
		autoButton.setToolTipText(Messages.getString("RunModeDebuggerTab.1")); //$NON-NLS-1$
		autoButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		armButton = new Button(group, SWT.RADIO);
		armButton.setText(Messages.getString("RunModeDebuggerTab.2")); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalSpan = colSpan;
		armButton.setLayoutData(gd);
		armButton.setToolTipText(Messages.getString("RunModeDebuggerTab.1")); //$NON-NLS-1$
		armButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		thumbButton = new Button(group, SWT.RADIO);
		thumbButton.setText(Messages.getString("RunModeDebuggerTab.3")); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalSpan = colSpan;
		thumbButton.setLayoutData(gd);
		thumbButton.setToolTipText(Messages.getString("RunModeDebuggerTab.1")); //$NON-NLS-1$
		thumbButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

	}


	protected void createBooleanGroup(Composite parent, int colSpan) {
		breakAtMain = createCheckButton(parent, Messages.getString("RunModeDebuggerTab.4")); //$NON-NLS-1$
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 1;
		breakAtMain.setLayoutData(data);
		breakAtMain.setFont(parent.getFont());
		breakAtMain.setToolTipText(Messages.getString("RunModeDebuggerTab.5")); //$NON-NLS-1$
		breakAtMain.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
				checkControlState();
			}
		});

		mainBreakSymbol = new Text(parent, SWT.BORDER);
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		data.horizontalSpan = 1;
		mainBreakSymbol.setLayoutData(data);
		mainBreakSymbol.setToolTipText(Messages.getString("RunModeDebuggerTab.5")); //$NON-NLS-1$
		mainBreakSymbol.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		viewUnframed = createCheckButton(parent, Messages.getString("RunModeDebuggerTab.6")); //$NON-NLS-1$
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = colSpan;
		viewUnframed.setLayoutData(data);
		viewUnframed.setToolTipText(Messages.getString("RunModeDebuggerTab.7")); //$NON-NLS-1$
		viewUnframed.setFont(parent.getFont());
		viewUnframed.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		viewCommMessages = createCheckButton(parent, Messages.getString("RunModeDebuggerTab.8")); //$NON-NLS-1$
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = colSpan;
		viewCommMessages.setLayoutData(data);
		viewCommMessages.setFont(parent.getFont());
		viewCommMessages.setToolTipText(Messages.getString("RunModeDebuggerTab.9")); //$NON-NLS-1$
		viewCommMessages.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		trkTimeoutLabel = new Label(parent, SWT.NONE);
		trkTimeoutLabel.setText(Messages.getString("RunModeDebuggerTab.10")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 1;
		trkTimeoutLabel.setLayoutData(data);
		trkTimeoutLabel.setToolTipText(Messages.getString("RunModeDebuggerTab.11")); //$NON-NLS-1$

		trkTimeout = new Text(parent, SWT.BORDER);
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 1;
		trkTimeout.setLayoutData(data);
		trkTimeout.setToolTipText(Messages.getString("RunModeDebuggerTab.11")); //$NON-NLS-1$
		trkTimeout.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

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
			int defaultMode = configuration.getAttribute( PreferenceConstants.J_PN_DefaultInstructionSet, PreferenceConstants.J_PV_DefaultInstructionSet_Arm);
			autoButton.setSelection(defaultMode == PreferenceConstants.J_PV_DefaultInstructionSet_Auto);
			armButton.setSelection(defaultMode == PreferenceConstants.J_PV_DefaultInstructionSet_Arm);
			thumbButton.setSelection(defaultMode == PreferenceConstants.J_PV_DefaultInstructionSet_Thumb);

			viewUnframed.setSelection(configuration.getAttribute( PreferenceConstants.J_PN_ViewUnframedData, true ));
			viewCommMessages.setSelection(configuration.getAttribute( PreferenceConstants.J_PN_ViewCommMessages, false ));

			breakAtMain.setSelection(configuration.getAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN , true ));
			mainBreakSymbol.setText(configuration.getAttribute( PreferenceConstants.J_PN_StopAtMainSymbol , "E32Main" )); //$NON-NLS-1$

			int timeout = configuration.getAttribute( PreferenceConstants.J_PN_TRKMessageTimeout , SettingsData.J_PN_TrkTimeout_Default );
			trkTimeout.setText(Integer.toString(timeout));
			
			checkControlState();
		} catch (CoreException e) {
			LaunchPlugin.log(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		if (autoButton.getSelection())
			configuration.setAttribute( PreferenceConstants.J_PN_DefaultInstructionSet, PreferenceConstants.J_PV_DefaultInstructionSet_Auto);
		else if (armButton.getSelection())
			configuration.setAttribute( PreferenceConstants.J_PN_DefaultInstructionSet, PreferenceConstants.J_PV_DefaultInstructionSet_Arm);
		else if (thumbButton.getSelection())
			configuration.setAttribute( PreferenceConstants.J_PN_DefaultInstructionSet, PreferenceConstants.J_PV_DefaultInstructionSet_Thumb);

		configuration.setAttribute( PreferenceConstants.J_PN_ViewUnframedData, viewUnframed.getSelection());
		configuration.setAttribute( PreferenceConstants.J_PN_ViewCommMessages, viewCommMessages.getSelection());
		configuration.setAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN, breakAtMain.getSelection());
		configuration.setAttribute( PreferenceConstants.J_PN_StopAtMainSymbol, mainBreakSymbol.getText());
		configuration.setAttribute( PreferenceConstants.J_PN_TRKMessageTimeout, Integer.parseInt(trkTimeout.getText()));

	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return Messages.getString("RunModeDebuggerTab.12"); //$NON-NLS-1$
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getImage()
	 */
	public Image getImage() {
		return LaunchImages.get(LaunchImages.IMG_VIEW_DEBUGGER_TAB);
	}

	protected void checkControlState()
	{
		mainBreakSymbol.setEnabled(breakAtMain.getSelection());
	}

	public boolean isValid(ILaunchConfiguration config) {
		
		setErrorMessage(null);
		setMessage(null);

		boolean result = super.isValid(config);
		if (result){
			if (breakAtMain.getSelection()) {
				if (mainBreakSymbol.getText().trim().length() < 1) {
					setErrorMessage(Messages.getString("RunModeDebuggerTab.13")); //$NON-NLS-1$
					return false;
				}
			}
			
			String timeout = trkTimeout.getText().trim();
			if (timeout.length() < 1) {
				setErrorMessage(Messages.getString("RunModeDebuggerTab.14")); //$NON-NLS-1$
				result = false;
			} else {
				try {
					int delay = Integer.parseInt(timeout);
					if (delay < 100 || delay > 10000) {
						setErrorMessage(Messages.getString("RunModeDebuggerTab.15")); //$NON-NLS-1$
						result = false;
					}
				} catch (NumberFormatException e) {
					setErrorMessage(Messages.getString("RunModeDebuggerTab.16")); //$NON-NLS-1$
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
