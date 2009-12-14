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

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.CProjectDescriptionEvent;
import org.eclipse.cdt.core.settings.model.ICProjectDescriptionListener;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.launch.ui.CLaunchConfigurationTab;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

import com.freescale.swt.widgets.CheckboxGroup;
import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;

import cwdbg.PreferenceConstants;


public class InstallationTab extends CLaunchConfigurationTab implements ICProjectDescriptionListener {

	private ILaunchConfiguration configuration;
	
	private Label hostLabel;
	private Text hostPath;
	private Button hostBrowse;
	private Label puLabel;
	private Text puPath;
	private Link puLink;
	private Label targetLabel;
	private Text targetPath;
	private Button forceInstall;
	private CheckboxGroup installerUIGroup;
	private Label driveLabel;
	private Combo drive;
	
	private String driveLetterArray[] = {"A", "B", "C", "D", "E", "F", "G"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), LaunchTabHelpIds.RUN_MODE_INSTALLATION);
		
		GridLayout topLayout = new GridLayout();
		topLayout.numColumns = 2;
		comp.setLayout(topLayout);

		createVerticalSpacer(comp, 1);
		createSisGroup(comp, 1);
		createVerticalSpacer(comp, 1);
		createInstallerUIGroup(comp, 1);
		createVerticalSpacer(comp, 1);
		createPUInstallerUIGroup(comp, 1);

		Dialog.applyDialogFont(parent);
		checkControlState();
	}

	protected void createSisGroup(Composite parent, int colSpan) {
		Composite projComp = new Composite(parent, SWT.NONE);
		GridLayout projLayout = new GridLayout();
		projLayout.numColumns = 2;
		projLayout.marginHeight = 0;
		projLayout.marginWidth = 0;
		projComp.setLayout(projLayout);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = colSpan;
		projComp.setLayoutData(data);

		hostLabel = new Label(projComp, SWT.NONE);
		hostLabel.setText(Messages.getString("InstallationTab.1")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 2;
		hostLabel.setLayoutData(data);
		hostLabel.setToolTipText(Messages.getString("InstallationTab.2")); //$NON-NLS-1$
		hostLabel.setData(".uid", "InstallationTab.hostLabel");  //$NON-NLS-1$ //$NON-NLS-2$

		hostPath = new Text(projComp, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		hostPath.setLayoutData(data);
		hostPath.setToolTipText(Messages.getString("InstallationTab.2")); //$NON-NLS-1$
		hostPath.setData(".uid", "InstallationTab.hostPath");  //$NON-NLS-1$ //$NON-NLS-2$
		hostPath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updatePULabel();
				updateLaunchConfigurationDialog();
			}
		});

		hostBrowse = createPushButton(projComp, Messages.getString("InstallationTab.3"), null); //$NON-NLS-1$
		hostBrowse.setData(".uid", "InstallationTab.hostBrowse");  //$NON-NLS-1$ //$NON-NLS-2$
		hostBrowse.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.getString("InstallationTab.4")); //$NON-NLS-1$
				dialog.setFilterExtensions(new String[] {"*.sis*", "*.*"}); //$NON-NLS-1$ //$NON-NLS-2$
				dialog.setFilterNames(new String[] {Messages.getString("InstallationTab.27"), Messages.getString("InstallationTab.28")}); //$NON-NLS-1$ //$NON-NLS-2$
				
				BrowseDialogUtils.initializeFrom(dialog, hostPath);
				
				String result = dialog.open();
				if (result != null && new File(result).exists()) {
					hostPath.setText(result);
					updatePULabel();
					updateLaunchConfigurationDialog();
				}
			}
		});

		targetLabel = new Label(projComp, SWT.NONE);
		targetLabel.setText(Messages.getString("InstallationTab.5")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 2;
		targetLabel.setLayoutData(data);
		targetLabel.setToolTipText(Messages.getString("InstallationTab.6")); //$NON-NLS-1$
		targetLabel.setData(".uid", "InstallationTab.targetLabel");  //$NON-NLS-1$ //$NON-NLS-2$

		targetPath = new Text(projComp, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		targetPath.setLayoutData(data);
		targetPath.setToolTipText(Messages.getString("InstallationTab.6")); //$NON-NLS-1$
		targetPath.setData(".uid", "InstallationTab.targetPath");  //$NON-NLS-1$ //$NON-NLS-2$
		targetPath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		createVerticalSpacer(projComp, 2);

		forceInstall = createCheckButton(projComp, Messages.getString("InstallationTab.7")); //$NON-NLS-1$
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 2;
		forceInstall.setLayoutData(data);
		forceInstall.setToolTipText(Messages.getString("InstallationTab.8")); //$NON-NLS-1$
		forceInstall.setData(".uid", "InstallationTab.forceInstall");  //$NON-NLS-1$ //$NON-NLS-2$
		forceInstall.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
	}

	protected void createInstallerUIGroup(Composite parent, int colSpan) {
		Composite projComp = new Composite(parent, SWT.NONE);
		GridLayout projLayout = new GridLayout();
		projLayout.numColumns = 2;
		projLayout.marginHeight = 0;
		projLayout.marginWidth = 0;
		projComp.setLayout(projLayout);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = colSpan;
		projComp.setLayoutData(data);

		installerUIGroup = new CheckboxGroup(projComp, SWT.CHECK);
		installerUIGroup.setText(Messages.getString("InstallationTab.9")); //$NON-NLS-1$
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		installerUIGroup.setLayoutData(data);
		installerUIGroup.setToolTipText(Messages.getString("InstallationTab.10")); //$NON-NLS-1$
		installerUIGroup.getGroup().setLayout(projLayout);
		installerUIGroup.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		driveLabel = new Label(installerUIGroup.getGroup(), SWT.NONE);
		driveLabel.setText(Messages.getString("InstallationTab.11")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 1;
		driveLabel.setLayoutData(data);
		driveLabel.setToolTipText(Messages.getString("InstallationTab.12")); //$NON-NLS-1$
		driveLabel.setData(".uid", "InstallationTab.driveLabel");  //$NON-NLS-1$ //$NON-NLS-2$
		drive = new Combo(installerUIGroup.getGroup(), SWT.READ_ONLY);
		for (int i=0; i<driveLetterArray.length; i++) {
			drive.add(driveLetterArray[i]);
		}
		data = new GridData();
		data.horizontalSpan = 1;
		drive.setLayoutData(data);
		drive.setToolTipText(Messages.getString("InstallationTab.12")); //$NON-NLS-1$
		
		drive.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
	}

	protected void createPUInstallerUIGroup(Composite parent, int colSpan) {
		Composite projComp = new Composite(parent, SWT.NONE);
		GridLayout projLayout = new GridLayout();
		projLayout.numColumns = 2;
		projLayout.marginHeight = 0;
		projLayout.marginWidth = 0;
		projComp.setLayout(projLayout);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = colSpan;
		projComp.setLayoutData(data);

		puLabel = new Label(projComp, SWT.NONE);
		puLabel.setText(Messages.getString("InstallationTab.puLabel")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 2;
		puLabel.setLayoutData(data);
		puLabel.setToolTipText(Messages.getString("InstallationTab.puTooltip")); //$NON-NLS-1$

		puPath = new Text(projComp, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		puPath.setLayoutData(data);
		puPath.setToolTipText(Messages.getString("InstallationTab.puTooltip")); //$NON-NLS-1$
		puPath.setEditable(false);

		puLink = new Link(projComp, SWT.NONE);
		puLink.setText("<a>" + Messages.getString("InstallationTab.puHyperlink") + "...</a>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		data = new GridData(GridData.FILL_HORIZONTAL);
		puLink.setLayoutData(data);
		puLink.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// listen for events so we can detect if they click on the link below and change sis info.
				CoreModel.getDefault().getProjectDescriptionManager().addCProjectDescriptionListener(InstallationTab.this, CProjectDescriptionEvent.APPLIED);

				PreferencesUtil.createPropertyDialogOn(getShell(), getProject(), "com.nokia.carbide.cdt.internal.builder.ui.CarbideBuildConfigurationsPage", null, null).open(); //$NON-NLS-1$
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
			hostPath.setText(configuration.getAttribute( PreferenceConstants.J_PN_SisFileHostPath, "")); //$NON-NLS-1$
			targetPath.setText(configuration.getAttribute( PreferenceConstants.J_PN_SisFileTargetPath, "")); //$NON-NLS-1$
			forceInstall.setSelection(configuration.getAttribute( PreferenceConstants.J_PN_AlwaysInstallSisFile, false));
			installerUIGroup.setSelection(configuration.getAttribute( PreferenceConstants.J_PN_HideInstallerUI, true));
			drive.select(configuration.getAttribute( PreferenceConstants.J_PN_InstallToDrive, 2));

			// cache the configuration
			this.configuration = configuration;
			
			// update the pu stuff
			updatePULabel();
			
			checkControlState();
		} catch (CoreException e) {
			LaunchPlugin.log(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute( PreferenceConstants.J_PN_SisFileHostPath, hostPath.getText());
		configuration.setAttribute( PreferenceConstants.J_PN_SisFileTargetPath, targetPath.getText());
		configuration.setAttribute( PreferenceConstants.J_PN_AlwaysInstallSisFile, forceInstall.getSelection());
		configuration.setAttribute( PreferenceConstants.J_PN_HideInstallerUI, installerUIGroup.getSelection());
		configuration.setAttribute( PreferenceConstants.J_PN_InstallToDrive, drive.getSelectionIndex());

	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return Messages.getString("InstallationTab.13"); //$NON-NLS-1$
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getImage()
	 */
	public Image getImage() {
		return LaunchImages.get(LaunchImages.IMG_VIEW_INSTALLATION_TAB);
	}

	protected void checkControlState()
	{
	}

	public boolean isValid(ILaunchConfiguration config) {
		
		setErrorMessage(null);
		setMessage(null);

		boolean result = super.isValid(config);
		if (result) {
			String pcPath = hostPath.getText().trim();
			if (pcPath.length() < 1) {
				// allow this but put a note at the top about it.
				setMessage(Messages.getString("InstallationTab.14")); //$NON-NLS-1$
			} else {
				if (!new File(pcPath).exists()) {
					setErrorMessage(Messages.getString("InstallationTab.15")); //$NON-NLS-1$
				}
			}
			
			String remotePath = targetPath.getText().trim();
			if (remotePath.length() < 1) {
				setErrorMessage(Messages.getString("InstallationTab.16")); //$NON-NLS-1$
				result = false;
			} else if (remotePath.length() < 3) {
				setErrorMessage(Messages.getString("InstallationTab.17")); //$NON-NLS-1$
				result = false;
			} else {
				char drive = remotePath.charAt(0);
				char colon = remotePath.charAt(1);
				if (!Character.isLetter(drive) || colon != ':') { 
					setErrorMessage(Messages.getString("InstallationTab.17")); //$NON-NLS-1$
					result = false;
				}
			}
			
			// make sure the drive to install on matches the drive of the process to launch
			try {
				if (installerUIGroup.getSelection()) {
					String remoteProcess = config.getAttribute(PreferenceConstants.J_PN_RemoteProcessToLaunch, ""); //$NON-NLS-1$
					if (remoteProcess.length() > 0) {
						char driveLetter = remoteProcess.toUpperCase().charAt(0);						
						int driveIndex = drive.getSelectionIndex();
						if (driveLetter != driveLetterArray[driveIndex].charAt(0)) {
							setErrorMessage(Messages.getString("InstallationTab.18")); //$NON-NLS-1$
							result = false;
						}
					}
				}
			} catch (CoreException e) {
				LaunchPlugin.log(e);
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
	
	private void updatePULabel() {
		puLabel.setVisible(false);
		puPath.setVisible(false);
		puLink.setVisible(false);
		puPath.setText(""); //$NON-NLS-1$
		
		if (configuration == null) {
			return;
		}
		
		try {
			ILaunchConfigurationWorkingCopy config = configuration.getWorkingCopy();
			String buildConfigName = config.getAttribute(ICDTLaunchConfigurationConstants.ATTR_PROJECT_BUILD_CONFIG_ID, "");
			if (buildConfigName.length() > 0) {
	    		IProject project = getProject();
	    		if (project != null) {
			        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			        if (cpi != null) {
			        	for (ICarbideBuildConfiguration buildConfig : cpi.getBuildConfigurations()) {
			        		if (buildConfig.getDisplayString().equals(buildConfigName)) {
			        			// enable the controls for EKA2 configs
			        			if (buildConfig.getSDK().isEKA2()) {
			        				puLabel.setVisible(true);
			        				puPath.setVisible(true);
			        				puLink.setVisible(true);
			        			}
								IPath puSisPath = CarbideCPPBuilder.getPartialUpgradeSisPath(buildConfig, new Path(hostPath.getText().trim()));
								if (puSisPath == null) {
									puPath.setText(Messages.getString("InstallationTab.puNotEnabled")); //$NON-NLS-1$
								} else if (!puSisPath.toFile().exists()) {
									puPath.setText(Messages.getString("InstallationTab.puNotAvailable")); //$NON-NLS-1$
								} else {
									puPath.setText(puSisPath.toOSString());
								}
								if (puSisPath != null && puSisPath.toFile().exists()) {
								}
				        		break;
			        		}
			        	}
			        }
	    		}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	private IProject getProject() {
		try {
			String projectName = configuration.getAttribute(ICDTLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
	    	if (projectName.length() > 0) {
	    		return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
	    	}
		} catch (CoreException e) {
			e.printStackTrace();
			LaunchPlugin.log(e);
		}
		return null;
	}

	public void handleEvent(CProjectDescriptionEvent event) {
		if (event.getProject() != getProject()) {
			return;
		}
		
		updatePULabel();
	}

	@Override
	public void dispose() {
		CoreModel.getDefault().getProjectDescriptionManager().removeCProjectDescriptionListener(this);
		super.dispose();
	}
	
}
