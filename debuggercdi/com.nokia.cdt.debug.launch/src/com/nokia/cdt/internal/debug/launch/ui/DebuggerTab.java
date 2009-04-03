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

import com.nokia.cdt.debug.cw.symbian.SymbianPlugin;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

import cwdbg.PreferenceConstants;

public class DebuggerTab extends CLaunchConfigurationTab {

	// Boolean widgets
	private Button viewOutput;
	private Button viewMessages;
	private Button breakAtMain;
	private Text mainBreakSymbol;
	private Button viewDebugTrace;

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), LaunchTabHelpIds.EMULATION_DEBUGGER);
		
		GridLayout topLayout = new GridLayout();
		topLayout.numColumns = 2;
		comp.setLayout(topLayout);

		createVerticalSpacer(comp, 2);
		createBooleanGroup(comp, 2);

		Dialog.applyDialogFont(parent);
		checkControlState();
	}

	protected void createBooleanGroup(Composite parent, int colSpan) {
		breakAtMain = createCheckButton(parent, Messages.getString("DebuggerTab.6")); //$NON-NLS-1$
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 1;
		breakAtMain.setLayoutData(data);
		breakAtMain.setFont(parent.getFont());
		breakAtMain.setToolTipText(Messages.getString("DebuggerTab.7")); //$NON-NLS-1$
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
		mainBreakSymbol.setToolTipText(Messages.getString("DebuggerTab.7")); //$NON-NLS-1$
		mainBreakSymbol.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		viewOutput = createCheckButton(parent, Messages.getString("DebuggerTab.9")); //$NON-NLS-1$
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = colSpan;
		viewOutput.setLayoutData(data);
		viewOutput.setFont(parent.getFont());
		viewOutput.setToolTipText(Messages.getString("DebuggerTab.10")); //$NON-NLS-1$
		viewOutput.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		viewDebugTrace = createCheckButton(parent, Messages.getString("DebuggerTab.16")); //$NON-NLS-1$
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = colSpan;
		viewDebugTrace.setLayoutData(data);
		viewDebugTrace.setFont(parent.getFont());
		viewDebugTrace.setToolTipText(Messages.getString("DebuggerTab.17")); //$NON-NLS-1$
		viewDebugTrace.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		viewMessages = createCheckButton(parent, Messages.getString("DebuggerTab.11")); //$NON-NLS-1$
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = colSpan;
		viewMessages.setLayoutData(data);
		viewMessages.setFont(parent.getFont());
		viewMessages.setToolTipText(Messages.getString("DebuggerTab.12")); //$NON-NLS-1$
		viewMessages.addSelectionListener(new SelectionAdapter() {
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

			viewOutput.setSelection(configuration.getAttribute( PreferenceConstants.J_PN_ViewProcessOutput, true ));
			viewMessages.setSelection(configuration.getAttribute( PreferenceConstants.J_PN_ViewSystemMessage, true ));

			breakAtMain.setSelection(configuration.getAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN , true ));
			mainBreakSymbol.setText(configuration.getAttribute( PreferenceConstants.J_PN_StopAtMainSymbol , "E32Main" )); //$NON-NLS-1$
			
			viewDebugTrace.setSelection(configuration.getAttribute( SymbianPlugin.DebugTraceLaunchSetting, true ));

			checkControlState();
		} catch (CoreException e) {
			LaunchPlugin.log(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute( PreferenceConstants.J_PN_ViewProcessOutput, viewOutput.getSelection());
		configuration.setAttribute( PreferenceConstants.J_PN_ViewSystemMessage, viewMessages.getSelection());
		configuration.setAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN, breakAtMain.getSelection());
		configuration.setAttribute( PreferenceConstants.J_PN_StopAtMainSymbol, mainBreakSymbol.getText());
		configuration.setAttribute( SymbianPlugin.DebugTraceLaunchSetting, viewDebugTrace.getSelection());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return Messages.getString("DebuggerTab.14"); //$NON-NLS-1$
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
					setErrorMessage(Messages.getString("DebuggerTab.15")); //$NON-NLS-1$
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
