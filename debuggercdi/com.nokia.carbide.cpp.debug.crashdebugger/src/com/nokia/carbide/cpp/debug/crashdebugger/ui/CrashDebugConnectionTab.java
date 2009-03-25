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
package com.nokia.carbide.cpp.debug.crashdebugger.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;

import com.freescale.cdt.debug.cw.core.ComPortHelper;
import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.debug.cw.symbian.SymbianPlugin;
import com.nokia.cdt.internal.debug.launch.ui.LaunchImages;
import com.nokia.cdt.internal.debug.launch.ui.LaunchTabHelpIds;


public class CrashDebugConnectionTab extends AbstractLaunchConfigurationTab {

	private Combo port;
	private Combo baudRate;
	private Combo dataBits;
	private Combo parity;
	private Combo stopBits;
	private Combo flowControl;


	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), LaunchTabHelpIds.RUN_MODE_CONNECTION);
		
		GridLayout topLayout = new GridLayout();
		topLayout.numColumns = 2;
		comp.setLayout(topLayout);

		createVerticalSpacer(comp, 2);
		createWidgets(comp, 2);

		Dialog.applyDialogFont(parent);
	}


	protected void createWidgets(Composite parent, int colSpan) {
		final Label portLabel = new Label(parent, SWT.NONE);
		portLabel.setText(Messages.getString("CrashDebugConnectionTab.0")); //$NON-NLS-1$
		GridData data = new GridData();
		data.horizontalSpan = 1;
		portLabel.setLayoutData(data);
		portLabel.setToolTipText(Messages.getString("CrashDebugConnectionTab.1")); //$NON-NLS-1$

		// COM port pulldown list.
		//
		port = new Combo(parent, SWT.READ_ONLY);
		port.setItems(ComPortHelper.getComPortNames());
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 1;
		port.setLayoutData(data);
		port.setToolTipText(Messages.getString("CrashDebugConnectionTab.1")); //$NON-NLS-1$
		port.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		final Label rateLabel = new Label(parent, SWT.NONE);
		rateLabel.setText(Messages.getString("CrashDebugConnectionTab.2")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 1;
		rateLabel.setLayoutData(data);
		rateLabel.setToolTipText(Messages.getString("CrashDebugConnectionTab.3")); //$NON-NLS-1$

		baudRate = new Combo(parent, SWT.READ_ONLY);
		baudRate.setItems(new String[] { "300", "1200", "2400", "4800", "9600", "19200", "38400", "57600", "115200", "230400" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 1;
		baudRate.setLayoutData(data);
		baudRate.setToolTipText(Messages.getString("CrashDebugConnectionTab.3")); //$NON-NLS-1$
		baudRate.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		final Label dataBitsLabel = new Label(parent, SWT.NONE);
		dataBitsLabel.setText(Messages.getString("CrashDebugConnectionTab.4")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 1;
		dataBitsLabel.setLayoutData(data);
		dataBitsLabel.setToolTipText(Messages.getString("CrashDebugConnectionTab.5")); //$NON-NLS-1$

		dataBits = new Combo(parent, SWT.READ_ONLY);
		dataBits.setItems(new String[] {"4", "5", "6", "7", "8"}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 1;
		dataBits.setLayoutData(data);
		dataBits.setToolTipText(Messages.getString("CrashDebugConnectionTab.5")); //$NON-NLS-1$
		dataBits.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		final Label parityLabel = new Label(parent, SWT.NONE);
		parityLabel.setText(Messages.getString("CrashDebugConnectionTab.6")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 1;
		parityLabel.setLayoutData(data);
		parityLabel.setToolTipText(Messages.getString("CrashDebugConnectionTab.7")); //$NON-NLS-1$

		parity = new Combo(parent, SWT.READ_ONLY);
		parity.setItems(new String[] {Messages.getString("CrashDebugConnectionTab.12"), Messages.getString("CrashDebugConnectionTab.13"), Messages.getString("CrashDebugConnectionTab.14")}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 1;
		parity.setLayoutData(data);
		parity.setToolTipText(Messages.getString("CrashDebugConnectionTab.7")); //$NON-NLS-1$
		parity.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		final Label stopBitsLabel = new Label(parent, SWT.NONE);
		stopBitsLabel.setText(Messages.getString("CrashDebugConnectionTab.8")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 1;
		stopBitsLabel.setLayoutData(data);
		stopBitsLabel.setToolTipText(Messages.getString("CrashDebugConnectionTab.9")); //$NON-NLS-1$

		stopBits = new Combo(parent, SWT.READ_ONLY);
		stopBits.setItems(new String[] {"1", "1.5", "2"}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 1;
		stopBits.setLayoutData(data);
		stopBits.setToolTipText(Messages.getString("CrashDebugConnectionTab.9")); //$NON-NLS-1$
		stopBits.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		final Label flowControlLabel = new Label(parent, SWT.NONE);
		flowControlLabel.setText(Messages.getString("CrashDebugConnectionTab.10")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 1;
		flowControlLabel.setLayoutData(data);
		flowControlLabel.setToolTipText(Messages.getString("CrashDebugConnectionTab.11")); //$NON-NLS-1$

		flowControl = new Combo(parent, SWT.READ_ONLY);
		flowControl.setItems(new String[] {Messages.getString("CrashDebugConnectionTab.12"), Messages.getString("CrashDebugConnectionTab.15"), Messages.getString("CrashDebugConnectionTab.16")}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 1;
		flowControl.setLayoutData(data);
		flowControl.setToolTipText(Messages.getString("CrashDebugConnectionTab.11")); //$NON-NLS-1$
		flowControl.addSelectionListener(new SelectionAdapter() {
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
			port.select(configuration.getAttribute(SettingsData.spn_SerialComm_port, 0));
			baudRate.select(configuration.getAttribute(SettingsData.spn_SerialComm_rate, 8));
			dataBits.select(configuration.getAttribute(SettingsData.spn_SerialComm_databits, 4));
			parity.select(configuration.getAttribute(SettingsData.spn_SerialComm_parity, 0));
			stopBits.select(configuration.getAttribute(SettingsData.spn_SerialComm_stopbits, 0));
			flowControl.select(configuration.getAttribute(SettingsData.spn_SerialComm_flowcontrol, 0));
			
		} catch (CoreException e) {
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
        configuration.setAttribute(SettingsData.spn_SerialComm_port, port.getSelectionIndex());
        SymbianPlugin.getDefault().getPluginPreferences().setValue(SettingsData.spn_SerialComm_port, port.getSelectionIndex());
        configuration.setAttribute(SettingsData.spn_SerialComm_rate, baudRate.getSelectionIndex());
        configuration.setAttribute(SettingsData.spn_SerialComm_databits, dataBits.getSelectionIndex());
        configuration.setAttribute(SettingsData.spn_SerialComm_parity, parity.getSelectionIndex());
        configuration.setAttribute(SettingsData.spn_SerialComm_stopbits, stopBits.getSelectionIndex());
        configuration.setAttribute(SettingsData.spn_SerialComm_flowcontrol, flowControl.getSelectionIndex());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return Messages.getString("CrashDebugConnectionTab.17"); //$NON-NLS-1$
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getImage()
	 */
	public Image getImage() {
		return LaunchImages.get(LaunchImages.IMG_VIEW_CONNECTION_TAB);
	}
}
