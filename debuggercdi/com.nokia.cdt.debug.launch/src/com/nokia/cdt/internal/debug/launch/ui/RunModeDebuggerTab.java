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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

import cwdbg.PreferenceConstants;

public class RunModeDebuggerTab extends CLaunchConfigurationTab {

	private Button breakAtMain;
	private Text mainBreakSymbol;
	private Button viewUnframed;
	private Button viewCommMessages;

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

		Dialog.applyDialogFont(parent);
		checkControlState();
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
			viewUnframed.setSelection(configuration.getAttribute( PreferenceConstants.J_PN_ViewUnframedData, true ));
			viewCommMessages.setSelection(configuration.getAttribute( PreferenceConstants.J_PN_ViewCommMessages, false ));

			breakAtMain.setSelection(configuration.getAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN , true ));
			mainBreakSymbol.setText(configuration.getAttribute( PreferenceConstants.J_PN_StopAtMainSymbol , "E32Main" )); //$NON-NLS-1$
			
			checkControlState();
		} catch (CoreException e) {
			LaunchPlugin.log(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute( PreferenceConstants.J_PN_ViewUnframedData, viewUnframed.getSelection());
		configuration.setAttribute( PreferenceConstants.J_PN_ViewCommMessages, viewCommMessages.getSelection());
		configuration.setAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN, breakAtMain.getSelection());
		configuration.setAttribute( PreferenceConstants.J_PN_StopAtMainSymbol, mainBreakSymbol.getText());
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
