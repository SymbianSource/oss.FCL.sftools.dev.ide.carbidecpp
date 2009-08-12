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
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import com.freescale.swt.widgets.CheckboxGroup;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

import cwdbg.PreferenceConstants;

public class RomImageTab extends CLaunchConfigurationTab {

	private Label osImageLabel;
	private Text osImagePath;
	private Button osImageBrowse;
	private Label downloadAddressLabel;
	private Text downloadAddress;
	private Button askForDownload;
	private CheckboxGroup downloadImgGroup;
	
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
				
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), LaunchTabHelpIds.STOP_MODE_ROM_IMAGE);
		
		GridLayout topLayout = new GridLayout();
		topLayout.numColumns = 2;		
		comp.setLayout(topLayout);

		createVerticalSpacer(comp, 2);		
		createRomLogFileGroup(comp, 2);		
		createOsImgGroup(comp, 2);		
		
		Dialog.applyDialogFont(parent);
	}

	protected void createOsImgGroup(Composite parent, int colSpan) {

		Composite projComp = new Composite(parent, SWT.NONE);
		GridLayout projLayout = new GridLayout();
		projLayout.numColumns = 3;
		projLayout.marginHeight = 0;
		projLayout.marginWidth = 0;
		projComp.setLayout(projLayout);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = colSpan;
		projComp.setLayoutData(data);

		downloadImgGroup = new CheckboxGroup(projComp, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		downloadImgGroup.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = colSpan;
		downloadImgGroup.setLayoutData(gd);
		downloadImgGroup.setText(Messages.getString("RomImageTab.9")); //$NON-NLS-1$
		downloadImgGroup.setFont(parent.getFont());
		downloadImgGroup.setToolTipText(Messages.getString("RomImageTab.10")); //$NON-NLS-1$
		downloadImgGroup.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();				
			}
		});
		osImageLabel = new Label(downloadImgGroup.getGroup(), SWT.NONE);
		osImageLabel.setText(Messages.getString("RomImageTab.11")); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalSpan = 1;
		osImageLabel.setLayoutData(gd);
		osImageLabel.setToolTipText(Messages.getString("RomImageTab.12")); //$NON-NLS-1$

		osImagePath = new Text(downloadImgGroup.getGroup(), SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		osImagePath.setLayoutData(gd);
		osImagePath.setToolTipText(Messages.getString("RomImageTab.12")); //$NON-NLS-1$
		osImagePath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		osImageBrowse = createPushButton(downloadImgGroup.getGroup(), Messages.getString("RomImageTab.13"), null); //$NON-NLS-1$
		osImageBrowse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.getString("RomImageTab.14")); //$NON-NLS-1$
				dialog.setFilterExtensions(new String[] {"*.img*", "*.*"}); //$NON-NLS-1$ //$NON-NLS-2$
				dialog.setFilterNames(new String[] {Messages.getString("RomImageTab.15"), Messages.getString("RomImageTab.16")}); //$NON-NLS-1$ //$NON-NLS-2$

				String result = dialog.open();
				if (result != null && new File(result).exists()) {
					osImagePath.setText(result);
					updateLaunchConfigurationDialog();
				}
			}
		});

	
		downloadAddressLabel = new Label(downloadImgGroup.getGroup(), SWT.NONE);
		downloadAddressLabel.setText(Messages.getString("RomImageTab.17")); //$NON-NLS-1$
		gd = new GridData(); 
		gd.horizontalSpan = 1;
		downloadAddressLabel.setLayoutData(gd);
		downloadAddressLabel.setToolTipText(Messages.getString("RomImageTab.18")); //$NON-NLS-1$

		downloadAddress = new Text(downloadImgGroup.getGroup(), SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		downloadAddress.setLayoutData(gd);
		downloadAddress.setToolTipText(Messages.getString("RomImageTab.18")); //$NON-NLS-1$
		downloadAddress.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
					
		askForDownload = createCheckButton(downloadImgGroup.getGroup(), Messages.getString("RomImageTab.19")); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalSpan = colSpan;
		askForDownload.setLayoutData(gd);
		askForDownload.setToolTipText(Messages.getString("RomImageTab.20")); //$NON-NLS-1$
		askForDownload.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
	}
	
	protected void createRomLogFileGroup(Composite parent, int colSpan) {

		Composite projComp = new Composite(parent, SWT.NONE);
		GridLayout projLayout = new GridLayout();
		projLayout.numColumns = 3;
		projLayout.marginHeight = 0;
		projLayout.marginWidth = 0;
		projComp.setLayout(projLayout);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = colSpan;
		projComp.setLayoutData(data);

		parseRomLogGroup = new CheckboxGroup(projComp, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		parseRomLogGroup.setLayout(layout);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = colSpan;
		parseRomLogGroup.setLayoutData(data);
		parseRomLogGroup.setText(Messages.getString("RomLogFileTab.1")); //$NON-NLS-1$
		parseRomLogGroup.setFont(parent.getFont());
		parseRomLogGroup.setToolTipText(Messages.getString("RomLogFileTab.2")); //$NON-NLS-1$
		parseRomLogGroup.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		romLogFileLabel = new Label(parseRomLogGroup.getGroup(), SWT.NONE);
		romLogFileLabel.setText(Messages.getString("RomLogFileTab.3")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 1;
		romLogFileLabel.setLayoutData(data);
		romLogFileLabel.setToolTipText(Messages.getString("RomLogFileTab.4")); //$NON-NLS-1$

		romLogFilePath = new Text(parseRomLogGroup.getGroup(), SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		romLogFilePath.setLayoutData(data);
		romLogFilePath.setToolTipText(Messages.getString("RomLogFileTab.4")); //$NON-NLS-1$
		romLogFilePath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		romLogFileBrowse = createPushButton(parseRomLogGroup.getGroup(), Messages.getString("RomLogFileTab.5"), null); //$NON-NLS-1$
		romLogFileBrowse.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.getString("RomLogFileTab.6")); //$NON-NLS-1$
				dialog.setFilterExtensions(new String[] {"*.log*", "*.*"}); //$NON-NLS-1$ //$NON-NLS-2$
				dialog.setFilterNames(new String[] {Messages.getString("RomLogFileTab.7"), Messages.getString("RomLogFileTab.8")}); //$NON-NLS-1$ //$NON-NLS-2$

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
		data.horizontalSpan = 1;
		epoc32DirLabel.setLayoutData(data);
		epoc32DirLabel.setToolTipText(Messages.getString("RomLogFileTab.10")); //$NON-NLS-1$

		epoc32DirPath = new Text(parseRomLogGroup.getGroup(), SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		epoc32DirPath.setLayoutData(data);
		epoc32DirPath.setToolTipText(Messages.getString("RomLogFileTab.10")); //$NON-NLS-1$
		epoc32DirPath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		epoc32DirBrowse = createPushButton(parseRomLogGroup.getGroup(), Messages.getString("RomLogFileTab.5"), null); //$NON-NLS-1$
		epoc32DirBrowse.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent evt) {
				DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.getString("RomLogFileTab.11")); //$NON-NLS-1$
				String result = dialog.open();
				
				if (result != null && new File(result).exists()) {
					epoc32DirPath.setText(result);
					updateLaunchConfigurationDialog();
				}
			}
		});
		
		logUnresolvedModules = createCheckButton(parseRomLogGroup.getGroup(), Messages.getString("RomLogFileTab.13")); //$NON-NLS-1$
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		logUnresolvedModules.setLayoutData(data);
		logUnresolvedModules.setToolTipText(Messages.getString("RomLogFileTab.14")); //$NON-NLS-1$
		logUnresolvedModules.addSelectionListener(new SelectionAdapter() {
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
			
			parseRomLogGroup.setSelection(configuration.getAttribute( PreferenceConstants.J_PN_ParseRomLogFile , false ));
			romLogFilePath.setText(configuration.getAttribute( PreferenceConstants.J_PN_RomLogFilePath , "" )); //$NON-NLS-1$	
			epoc32DirPath.setText(configuration.getAttribute( PreferenceConstants.J_PN_SymbianKitEpoc32Dir , "" )); //$NON-NLS-1$
			logUnresolvedModules.setSelection(configuration.getAttribute( PreferenceConstants.J_PN_LogUnresolved , false ));

			downloadImgGroup.setSelection(configuration.getAttribute( PreferenceConstants.J_PN_DownloadRomImage , false ));
			osImagePath.setText(configuration.getAttribute( PreferenceConstants.J_PN_RomImagePath , "" )); //$NON-NLS-1$
			
			int downloadAddressValue = configuration.getAttribute( PreferenceConstants.J_PN_DownloadAddress , 0);
			downloadAddress.setText(new String("0x" + Integer.toHexString(downloadAddressValue))); //$NON-NLS-1$
			
			askForDownload.setSelection(configuration.getAttribute( PreferenceConstants.J_PN_AskForDownload  , true ));
			
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
		
		configuration.setAttribute( PreferenceConstants.J_PN_DownloadRomImage, downloadImgGroup.getSelection());
		configuration.setAttribute( PreferenceConstants.J_PN_RomImagePath, osImagePath.getText());
		
		String downloadAddressText = downloadAddress.getText().trim().toLowerCase();
		
		int index = downloadAddressText.indexOf('x');
		if (index > 0)
		{
			downloadAddressText = downloadAddressText.substring(2); //ignore 0x or 0X.
		}
		try {
			Long longValue = Long.parseLong(downloadAddressText, 16);
			configuration.setAttribute( PreferenceConstants.J_PN_DownloadAddress, longValue.intValue());
		} catch (NumberFormatException e) {
			setErrorMessage(Messages.getString("RomImageTab.22")); //$NON-NLS-1		 //$NON-NLS-1$
			return;
		}
		configuration.setAttribute( PreferenceConstants.J_PN_AskForDownload, askForDownload.getSelection());		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return Messages.getString("RomImageTab.0"); //$NON-NLS-1$
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getImage()
	 */
	public Image getImage() {
		return LaunchImages.get(LaunchImages.IMG_ROM_IMAGE_TAB);
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
			//else {
			//	setErrorMessage(Messages.getString("RomLogFileTab.21"));
			//}

			if (downloadImgGroup.getSelection()) {
				
				try {				
					int debugMode = config.getAttribute( PreferenceConstants.J_PN_DebugRunFromStart, PreferenceConstants.J_PV_DebugRunFromStart_Attach);
					
					if (debugMode == PreferenceConstants.J_PV_DebugRunFromStart_Attach) {
						setMessage(Messages.getString("RomImageTab.27")); //$NON-NLS-1$
					}						
				}
				catch(CoreException e) {					
					LaunchPlugin.log(e);
				}

				// check if memory config file is specified, if specified, check if it exists or not.
				String imgFilePath = osImagePath.getText().trim();
				if (imgFilePath.length() < 1) {
					setErrorMessage(Messages.getString("RomImageTab.23")); //$NON-NLS-1$
					result = false;
				} else {
					if (!new File(imgFilePath).exists()) {
						setErrorMessage(Messages.getString("RomImageTab.24")); //$NON-NLS-1$
						result = false;
					}
				}
			
				String downloadAddressText = downloadAddress.getText().trim().toLowerCase();			
				if (downloadAddressText.length() < 3) {
					setErrorMessage(Messages.getString("RomImageTab.22")); //$NON-NLS-1$
					result = false;
				} else {
					try {						
						int index = downloadAddressText.indexOf('x');
						if (index > 0)
						{
							downloadAddressText = downloadAddressText.substring(2); //ignore 0x or 0X.
						}
						
						Long longValue = Long.parseLong(downloadAddressText, 16);
						
						int downloadAddressValue = longValue.intValue();
					}
					catch (NumberFormatException e) {
						setErrorMessage(Messages.getString("RomImageTab.22")); //$NON-NLS-1$
						result = false;
						return result;
					}
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
