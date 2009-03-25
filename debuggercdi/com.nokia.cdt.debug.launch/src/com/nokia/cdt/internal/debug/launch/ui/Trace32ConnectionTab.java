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

import com.nokia.carbide.cpp.internal.support.SupportPlugin;
import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

import org.eclipse.cdt.launch.ui.CLaunchConfigurationTab;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
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

public class Trace32ConnectionTab extends CLaunchConfigurationTab {

	private Label t32ExeLabel;
	private Text t32ExePath;
	private Button t32ExeBrowse;
	
	private Label t32ConfigLabel;
	private Text t32ConfigFilePath;
	private Button t32ConfigFileBrowse;
	
	private Label t32BootConfigLabel;
	private Text t32BootConfigFilePath;
	private Button t32BootConfigFileBrowse;

	//private Label t32PortLabel;
	//private Text t32Port;

	private Button viewT32Messages;
	

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), LaunchTabHelpIds.STOP_MODE_T32_CONNECTION);
		
		GridLayout topLayout = new GridLayout();
		topLayout.numColumns = 2;
		comp.setLayout(topLayout);

		createVerticalSpacer(comp, 2);
		createT32TextGroup(comp, 1);
		createVerticalSpacer(comp, 1);

		Dialog.applyDialogFont(parent);
		checkControlState();
	}

	protected void createT32TextGroup(Composite parent, int colSpan) {
		Composite projComp = new Composite(parent, SWT.NONE);
		GridLayout projLayout = new GridLayout();
		projLayout.numColumns = 2;
		projLayout.marginHeight = 0;
		projLayout.marginWidth = 0;
		projComp.setLayout(projLayout);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = colSpan;
		projComp.setLayoutData(data);

		t32ExeLabel = new Label(projComp, SWT.NONE);
		t32ExeLabel.setText(Messages.getString("Trace32ConnectionTab.1")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 2;
		t32ExeLabel.setLayoutData(data);
		t32ExeLabel.setToolTipText(Messages.getString("Trace32ConnectionTab.2")); //$NON-NLS-1$

		t32ExePath = new Text(projComp, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		t32ExePath.setLayoutData(data);
		t32ExePath.setToolTipText(Messages.getString("Trace32ConnectionTab.2")); //$NON-NLS-1$
		t32ExePath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		t32ExeBrowse = createPushButton(projComp, Messages.getString("Trace32ConnectionTab.3"), null); //$NON-NLS-1$
		t32ExeBrowse.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.getString("Trace32ConnectionTab.4")); //$NON-NLS-1$
				dialog.setFilterExtensions(new String[] {"*.exe*", "*.*"}); //$NON-NLS-1$ //$NON-NLS-2$
				dialog.setFilterNames(new String[] {Messages.getString("Trace32ConnectionTab.23"), Messages.getString("Trace32ConnectionTab.25")}); //$NON-NLS-1$ //$NON-NLS-2$

				String result = dialog.open();
				if (result != null && new File(result).exists()) {
					t32ExePath.setText(result);
					updateLaunchConfigurationDialog();
				}
			}
		});

		t32ConfigLabel = new Label(projComp, SWT.NONE);
		t32ConfigLabel.setText(Messages.getString("Trace32ConnectionTab.5")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 2;
		t32ConfigLabel.setLayoutData(data);
		t32ConfigLabel.setToolTipText(Messages.getString("Trace32ConnectionTab.6")); //$NON-NLS-1$

		t32ConfigFilePath = new Text(projComp, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		t32ConfigFilePath.setLayoutData(data);
		t32ConfigFilePath.setToolTipText(Messages.getString("Trace32ConnectionTab.6")); //$NON-NLS-1$
		t32ConfigFilePath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		t32ConfigFileBrowse = createPushButton(projComp, Messages.getString("Trace32ConnectionTab.3"), null); //$NON-NLS-1$
		t32ConfigFileBrowse.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.getString("Trace32ConnectionTab.7")); //$NON-NLS-1$
				dialog.setFilterExtensions(new String[] {"*.t32*", "*.*"}); //$NON-NLS-1$ //$NON-NLS-2$
				dialog.setFilterNames(new String[] {Messages.getString("Trace32ConnectionTab.24"), Messages.getString("Trace32ConnectionTab.25")}); //$NON-NLS-1$ //$NON-NLS-2$

				String result = dialog.open();
				if (result != null && new File(result).exists()) {
					t32ConfigFilePath.setText(result);
					updateLaunchConfigurationDialog();
				}
			}
		});

		t32BootConfigLabel = new Label(projComp, SWT.NONE);
		t32BootConfigLabel.setText(Messages.getString("Trace32ConnectionTab.8")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 2;
		t32BootConfigLabel.setLayoutData(data);
		t32BootConfigLabel.setToolTipText(Messages.getString("Trace32ConnectionTab.9")); //$NON-NLS-1$

		t32BootConfigFilePath = new Text(projComp, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		t32BootConfigFilePath.setLayoutData(data);
		t32BootConfigFilePath.setToolTipText(Messages.getString("Trace32ConnectionTab.9")); //$NON-NLS-1$
		t32BootConfigFilePath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		t32BootConfigFileBrowse = createPushButton(projComp, Messages.getString("Trace32ConnectionTab.3"), null); //$NON-NLS-1$
		t32BootConfigFileBrowse.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.getString("Trace32ConnectionTab.10")); //$NON-NLS-1$
				dialog.setFilterExtensions(new String[] {"*.cmm*", "*.*"}); //$NON-NLS-1$ //$NON-NLS-2$
				dialog.setFilterNames(new String[] {Messages.getString("Trace32ConnectionTab.22"), Messages.getString("Trace32ConnectionTab.25")}); //$NON-NLS-1$ //$NON-NLS-2$

				String result = dialog.open();
				if (result != null && new File(result).exists()) {
					t32BootConfigFilePath.setText(result);
					updateLaunchConfigurationDialog();
				}
			}
		});

		/*t32PortLabel = new Label(projComp, SWT.NONE);
		t32PortLabel.setText(Messages.getString("Trace32ConnectionTab.11")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 2;
		t32PortLabel.setLayoutData(data);
		t32PortLabel.setToolTipText(Messages.getString("Trace32ConnectionTab.12")); //$NON-NLS-1$

		t32Port = new Text(projComp, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		t32Port.setLayoutData(data);
		t32Port.setToolTipText(Messages.getString("Trace32ConnectionTab.12")); //$NON-NLS-1$
		t32Port.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});*/

		createVerticalSpacer(projComp, 1);

		viewT32Messages = createCheckButton(projComp, Messages.getString("Trace32ConnectionTab.13")); //$NON-NLS-1$
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 2;
		viewT32Messages.setLayoutData(data);
		viewT32Messages.setToolTipText(Messages.getString("Trace32ConnectionTab.14")); //$NON-NLS-1$
		viewT32Messages.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		createVerticalSpacer(projComp, 1);
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
			String t32ExePathStr = "C:\\t32\\t32marm.exe"; //$NON-NLS-1$
	
			//set the default config file from the Symbian support folder which has the predefined settings for connecting to Carbide.
			String supportDir = SupportPlugin.getDefault().getSymbianSupportDirectory();
			String defaultConfigFilePath = supportDir + "\\Trace32\\config_carbide.t32"; //$NON-NLS-1$
	
			if (new File("C:\\apps\\t32\\t32marm.exe").exists()) { //$NON-NLS-1$
				t32ExePathStr = "C:\\apps\\t32\\t32marm.exe"; //$NON-NLS-1$
				// use config_carbide_1.t32 where the SYS path is C:\Apps\T32\
				defaultConfigFilePath = supportDir + "\\Trace32\\config_carbide_1.t32"; //$NON-NLS-1$
			}
			
			t32ExePath.setText(configuration.getAttribute( SettingsData.spn_Trace32Conn_ExePath, t32ExePathStr)); 							
			t32ConfigFilePath.setText(configuration.getAttribute( SettingsData.spn_Trace32Conn_ConfigFilePath, defaultConfigFilePath)); //$NON-NLS-1$
			t32BootConfigFilePath.setText(configuration.getAttribute( SettingsData.spn_Trace32Conn_BootScriptFile, "")); //$NON-NLS-1$
			viewT32Messages.setSelection(configuration.getAttribute( SettingsData.spn_Trace32Conn_LogOption, false));	
			
			checkControlState();
		} catch (CoreException e) {
			LaunchPlugin.log(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute( SettingsData.spn_Trace32Conn_ExePath, t32ExePath.getText());
		configuration.setAttribute( SettingsData.spn_Trace32Conn_ConfigFilePath, t32ConfigFilePath.getText());
		configuration.setAttribute( SettingsData.spn_Trace32Conn_BootScriptFile, t32BootConfigFilePath.getText());
		configuration.setAttribute( SettingsData.spn_Trace32Conn_LogOption, viewT32Messages.getSelection());
		
		configuration.setAttribute( SettingsData.spn_Trace32Conn_BootConfigArgs, ""); //$NON-NLS-1$
		
		String portNumber = "20000"; //$NON-NLS-1$
		T32ConfigFileReader configFileReader = new T32ConfigFileReader(t32ConfigFilePath.getText().trim());
		if (configFileReader.isValid()) {
			portNumber = configFileReader.getPortNumber();
		}
		
		configuration.setAttribute( SettingsData.spn_Trace32Conn_UdpPort, portNumber);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return Messages.getString("Trace32ConnectionTab.21"); //$NON-NLS-1$
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
			String pcPath = t32ExePath.getText().trim();
			if (pcPath.length() < 1) {
				setErrorMessage(Messages.getString("Trace32ConnectionTab.15")); //$NON-NLS-1$
				return false;
			} else {
				if (!new File(pcPath).exists()) {
					setErrorMessage(Messages.getString("Trace32ConnectionTab.16")); //$NON-NLS-1$
					result = false;
				}
			}
			
			String configFilePath = t32ConfigFilePath.getText().trim();
			if (configFilePath.length() < 1) {
				setErrorMessage(Messages.getString("Trace32ConnectionTab.17")); //$NON-NLS-1$
				result = false;
			} else {
				if (!new File(configFilePath).exists()) {
					setErrorMessage(Messages.getString("Trace32ConnectionTab.18")); //$NON-NLS-1$
					result = false;
				}
			} 	
						
			T32ConfigFileReader configFileReader = new T32ConfigFileReader(t32ConfigFilePath.getText().trim());
			if (!configFileReader.isValid()) {
	         	result = false;
            	setErrorMessage(Messages.getString("Trace32ConnectionTab.26")); //$NON-NLS-1$
			} else {			
				// get the t32exe path
				IPath t32Path = new Path(t32ExePath.getText().trim().toLowerCase());
				t32Path = t32Path.removeLastSegments(1);
				
				if (configFileReader.getT32Path()!= null && configFileReader.getT32Path().length()>0) {
					IPath t32PathfromConfigFile = new Path(configFileReader.getT32Path().toLowerCase());					
					if (!t32PathfromConfigFile.equals(t32Path)) {
						setErrorMessage(Messages.getString("Trace32ConnectionTab.27")); //$NON-NLS-1$
						result = false;
					}	
				}			
			}
			
			String cmmScriptPath = t32BootConfigFilePath.getText().trim();
			if (cmmScriptPath.length() < 1) {
				setErrorMessage(Messages.getString("Trace32ConnectionTab.19")); //$NON-NLS-1$
				result = false;
			} else {
				if (!new File(cmmScriptPath).exists()) {
					setErrorMessage(Messages.getString("Trace32ConnectionTab.20")); //$NON-NLS-1$
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
