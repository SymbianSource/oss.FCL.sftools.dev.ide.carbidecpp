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

import java.io.File;

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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import com.freescale.swt.widgets.CheckboxGroup;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;


import cwdbg.PreferenceConstants;

public class RomLogFileTab extends CLaunchConfigurationTab {
	
	private CheckboxGroup parseRomLogGroup;
	private Label romLogFileLabel;
	private Text romLogFilePath;
	private Button romLogFileBrowse;
	
	private Label epoc32DirLabel;
	private Text epoc32DirPath;
	private Button epoc32DirBrowse;
	
	private Button logUnresolvedModules;
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), LaunchTabHelpIds.RUN_MODE_ROM_LOG_FILE);
		
		GridLayout topLayout = new GridLayout();
		topLayout.numColumns = 2;
		comp.setLayout(topLayout);

		createVerticalSpacer(comp, 2);
		createRomLogFileGroup(comp, 2);
		createVerticalSpacer(comp, 2);

		Dialog.applyDialogFont(parent);
		checkControlState();
	}
	
	protected void createRomLogFileGroup(Composite parent, int colSpan) {

		Composite projComp = new Composite(parent, SWT.NONE);
		GridLayout projLayout = new GridLayout();
		projLayout.numColumns = 2;
		projLayout.marginHeight = 0;
		projLayout.marginWidth = 0;
		projComp.setLayout(projLayout);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = colSpan;
		projComp.setLayoutData(data);

		parseRomLogGroup = new CheckboxGroup(projComp, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		parseRomLogGroup.setLayout(layout);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = colSpan;
		parseRomLogGroup.setLayoutData(data);
		parseRomLogGroup.setText(Messages.getString("RomLogFileTab.1")); //$NON-NLS-1$
		parseRomLogGroup.setFont(parent.getFont());
		parseRomLogGroup.setToolTipText(Messages.getString("RomLogFileTab.2")); //$NON-NLS-1$
		parseRomLogGroup.setData(".uid", "RomLogFileTab.parseRomLogGroup");  //$NON-NLS-1$ //$NON-NLS-2$
		parseRomLogGroup.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		romLogFileLabel = new Label(parseRomLogGroup.getGroup(), SWT.NONE);
		romLogFileLabel.setText(Messages.getString("RomLogFileTab.3")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 2;
		romLogFileLabel.setLayoutData(data);
		romLogFileLabel.setToolTipText(Messages.getString("RomLogFileTab.4")); //$NON-NLS-1$
		romLogFileLabel.setData(".uid", "RomLogFileTab.romLogFileLabel");  //$NON-NLS-1$ //$NON-NLS-2$


		romLogFilePath = new Text(parseRomLogGroup.getGroup(), SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		romLogFilePath.setLayoutData(data);
		romLogFilePath.setToolTipText(Messages.getString("RomLogFileTab.4")); //$NON-NLS-1$
		romLogFilePath.setData(".uid", "RomLogFileTab.romLogFilePath");  //$NON-NLS-1$ //$NON-NLS-2$
		romLogFilePath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		romLogFileBrowse = createPushButton(parseRomLogGroup.getGroup(), Messages.getString("RomLogFileTab.5"), null); //$NON-NLS-1$
		romLogFileBrowse.setData(".uid", "RomLogFileTab.romLogFileBrowse");  //$NON-NLS-1$ //$NON-NLS-2$
		romLogFileBrowse.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.getString("RomLogFileTab.6")); //$NON-NLS-1$
				dialog.setFilterExtensions(new String[] {"*.log*", "*.*"}); //$NON-NLS-1$ //$NON-NLS-2$
				dialog.setFilterNames(new String[] {Messages.getString("RomLogFileTab.7"), Messages.getString("RomLogFileTab.8")}); //$NON-NLS-1$ //$NON-NLS-2$

				BrowseDialogUtils.initializeFrom(dialog, romLogFilePath);

				String result = dialog.open();
				if (result != null && new File(result).exists()) {
					romLogFilePath.setText(result);
					updateLaunchConfigurationDialog();
				}
			}
		});
		
		epoc32DirLabel = new Label(parseRomLogGroup.getGroup(), SWT.NONE);
		epoc32DirLabel.setText(Messages.getString("RomLogFileTab.9")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 2;
		epoc32DirLabel.setLayoutData(data);
		epoc32DirLabel.setToolTipText(Messages.getString("RomLogFileTab.10")); //$NON-NLS-1$
		epoc32DirLabel.setData(".uid", "RomLogFileTab.epoc32DirLabel");  //$NON-NLS-1$ //$NON-NLS-2$

		epoc32DirPath = new Text(parseRomLogGroup.getGroup(), SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		epoc32DirPath.setLayoutData(data);
		epoc32DirPath.setToolTipText(Messages.getString("RomLogFileTab.10")); //$NON-NLS-1$
		epoc32DirPath.setData(".uid", "RomLogFileTab.epoc32DirPath");  //$NON-NLS-1$ //$NON-NLS-2$
		epoc32DirPath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		epoc32DirBrowse = createPushButton(parseRomLogGroup.getGroup(), Messages.getString("RomLogFileTab.5"), null); //$NON-NLS-1$
		epoc32DirBrowse.setData(".uid", "RomLogFileTab.epoc32DirBrowse");  //$NON-NLS-1$ //$NON-NLS-2$
		epoc32DirBrowse.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent evt) {
				DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.NONE);
				BrowseDialogUtils.initializeFrom(dialog, epoc32DirPath);

				dialog.setText(Messages.getString("RomLogFileTab.11")); //$NON-NLS-1$
				String result = dialog.open();
				
				if (result != null && new File(result).exists()) {
					epoc32DirPath.setText(result);
					updateLaunchConfigurationDialog();
				}
			}
		});
		
		createVerticalSpacer(parent, 1);

		logUnresolvedModules = createCheckButton(parseRomLogGroup.getGroup(), Messages.getString("RomLogFileTab.13")); //$NON-NLS-1$
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 2;
		logUnresolvedModules.setLayoutData(data);
		logUnresolvedModules.setToolTipText(Messages.getString("RomLogFileTab.14")); //$NON-NLS-1$
		logUnresolvedModules.setData(".uid", "RomLogFileTab.logUnresolvedModules");  //$NON-NLS-1$ //$NON-NLS-2$
		logUnresolvedModules.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
			
		//createVerticalSpacer(parent, 1);
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
			parseRomLogGroup.setSelection(configuration.getAttribute( PreferenceConstants.J_PN_ParseRomLogFile , false ));
			romLogFilePath.setText(configuration.getAttribute( PreferenceConstants.J_PN_RomLogFilePath , "" )); //$NON-NLS-1$	
			epoc32DirPath.setText(configuration.getAttribute( PreferenceConstants.J_PN_SymbianKitEpoc32Dir , "" )); //$NON-NLS-1$
			logUnresolvedModules.setSelection(configuration.getAttribute( PreferenceConstants.J_PN_LogUnresolved , false ));
			
			checkControlState();
			
		} catch (CoreException e) {			
			LaunchPlugin.log(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {		
		configuration.setAttribute( PreferenceConstants.J_PN_ParseRomLogFile, parseRomLogGroup.getSelection());
		configuration.setAttribute( PreferenceConstants.J_PN_RomLogFilePath, romLogFilePath.getText());
	
		String epoc32Dir = epoc32DirPath.getText();
		if ((epoc32Dir!=null) && (epoc32Dir.length()>0) && (epoc32Dir.charAt(epoc32Dir.length()-1) == '\\')) {
			epoc32Dir = epoc32Dir.substring(0, epoc32Dir.length()-1);
		}	
		configuration.setAttribute( PreferenceConstants.J_PN_SymbianKitEpoc32Dir, epoc32Dir);
		configuration.setAttribute( PreferenceConstants.J_PN_LogUnresolved, logUnresolvedModules.getSelection());				
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return Messages.getString("RomLogFileTab.0"); //$NON-NLS-1$
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getImage()
	 */
	public Image getImage() {
		return LaunchImages.get(LaunchImages.IMG_ROM_LOG_TAB);
	}

	protected void checkControlState()
	{
		
	}

	public boolean isValid(ILaunchConfiguration config) {
		
		setErrorMessage(null);
		setMessage(null);
		
		boolean result = super.isValid(config);
		
		if (result) {
			
			if (parseRomLogGroup.getSelection()) {
			
				// check if rom log file is specified. If specified, check if it exists or not.
				String romLogFile = romLogFilePath.getText().trim();
				if (romLogFile.length() < 1) {
					setErrorMessage(Messages.getString("RomLogFileTab.17")); //$NON-NLS-1$
					result = false;
				} else {
					if (!new File(romLogFile).exists()) {
						setErrorMessage(Messages.getString("RomLogFileTab.18")); //$NON-NLS-1$
						result = false;
					}
				}
				
				// check if epoc32 dir is specified. If specified, check if it exists or not.
				String epoc32Dir = epoc32DirPath.getText().trim();
				if (epoc32Dir.length() < 1) {
					setErrorMessage(Messages.getString("RomLogFileTab.19")); //$NON-NLS-1$
					result = false;
				} else {
					if (!new File(epoc32Dir).exists()) {
						setErrorMessage(Messages.getString("RomLogFileTab.20")); //$NON-NLS-1$
						result = false;
					}
				}

			}
		}		
	
		return true;
	}

	@Override
	public void activated(ILaunchConfigurationWorkingCopy workingCopy) {
		super.activated(workingCopy);
		
		// forces page to get focus so that help works without having to select some control first.
		getControl().setFocus();
	}
}
