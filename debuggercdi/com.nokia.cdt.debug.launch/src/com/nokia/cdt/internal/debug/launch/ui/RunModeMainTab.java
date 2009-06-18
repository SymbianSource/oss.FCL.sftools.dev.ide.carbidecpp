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

import java.util.StringTokenizer;

import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.freescale.cdt.debug.cw.core.RemoteConnectionsTRKHelper;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IClientServiceSiteUI;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IClientServiceSiteUI.IListener;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

import cwdbg.PreferenceConstants;

public class RunModeMainTab extends CarbideMainTab implements IResourceChangeListener {

	public RunModeMainTab() {
		super(DONT_CHECK_PROGRAM);
	}

	public RunModeMainTab(boolean wantsConnectionUI) {
		super(DONT_CHECK_PROGRAM);
		this.wantsConnectionUI = wantsConnectionUI;
	}

	protected Label remoteLabel;
	protected Text remoteText;
	protected IConnection connection;
	protected boolean wantsConnectionUI = true;
	protected IClientServiceSiteUI clientSiteUI;
	
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		
		LaunchPlugin.getDefault().getWorkbench().getHelpSystem().setHelp(getControl(), LaunchTabHelpIds.RUN_MODE_MAIN);
		
		GridLayout topLayout = new GridLayout();
		comp.setLayout(topLayout);

		createVerticalSpacer(comp, 1);
		createProjectGroup(comp, 1);
		createRemoteAppGroup(comp, 1);

		fProjLabel.setToolTipText(Messages.getString("RunModeMainTab.8")); //$NON-NLS-1$
		fProjText.setToolTipText(Messages.getString("RunModeMainTab.8")); //$NON-NLS-1$

		if (wantsConnectionUI)
		{
			createVerticalSpacer(comp, 1);
			clientSiteUI = RemoteConnectionsActivator.getConnectionsManager().getClientSiteUI(LaunchPlugin.getTRKService());
			clientSiteUI.createComposite(comp);
			clientSiteUI.addListener(new IListener() {
				public void connectionSelected() {
					connection = clientSiteUI.getSelectedConnection();
					updateLaunchConfigurationDialog();
				}
			});
		}
		
		createVerticalSpacer(comp, 1);
		if (wantsTerminalOption() /*&& ProcessFactory.supportesTerminal()*/) {
			createTerminalOption(comp, 1);
			createVerticalSpacer(comp, 1);
		}
		createBuildOptionGroup(comp, 1);

		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_BUILD);
}

	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	protected void createRemoteAppGroup(Composite parent, int colSpan) {
		Composite projComp = new Composite(parent, SWT.NONE);
		GridLayout projLayout = new GridLayout();
		projLayout.numColumns = 2;
		projLayout.marginHeight = 0;
		projLayout.marginWidth = 0;
		projComp.setLayout(projLayout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = colSpan;
		projComp.setLayoutData(gd);

		remoteLabel = new Label(projComp, SWT.NONE);
		remoteLabel.setText(Messages.getString("RunModeMainTab.2")); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalSpan = 2;
		remoteLabel.setLayoutData(gd);
		remoteLabel.setToolTipText(Messages.getString("RunModeMainTab.3")); //$NON-NLS-1$

		remoteText = new Text(projComp, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		remoteText.setLayoutData(gd);
		remoteText.setToolTipText(Messages.getString("RunModeMainTab.3")); //$NON-NLS-1$
		remoteText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent evt) {
				updateLaunchConfigurationDialog();
			}
		});

	}

	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
	}

	public void initializeFrom(ILaunchConfiguration config) {
		super.initializeFrom(config);
		try {
			remoteText.setText(config.getAttribute(PreferenceConstants.J_PN_RemoteProcessToLaunch, "")); //$NON-NLS-1$
		} catch (CoreException e) {
			LaunchPlugin.log(e);
		}
		try {
	        if (!RemoteConnectionsTRKHelper.configUsesConnectionAttribute(config)) {
	        	config = RemoteConnectionsTRKHelper.attemptUpdateLaunchConfiguration(config.getWorkingCopy());
	        }
			connection = RemoteConnectionsTRKHelper.getConnectionFromConfig(config);
		} catch (CoreException e) {
		}
		if (clientSiteUI != null)
		{
			if (connection != null)
				clientSiteUI.selectConnection(connection);
			else {
				connection = clientSiteUI.getSelectedConnection();
			}
		}
	}

	public void performApply(ILaunchConfigurationWorkingCopy config) {
		super.performApply(config);
		config.setAttribute(PreferenceConstants.J_PN_RemoteProcessToLaunch, remoteText.getText());
		if (connection != null) {
			config.setAttribute(RemoteConnectionsTRKHelper.CONNECTION_ATTRIBUTE, connection.getIdentifier());
		}		
		// Now check if the process to launch is the main target exe 
		// for debugging. If not, try to set process to launch as the 
		// main target if its in the list of target executables
		if (isRemoteTextValid(remoteText.getText()) == null) {
			try {
				Path processToLaunch = new Path(remoteText.getText());				
				Path mainExePath = new Path(config.getAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME, ""));
				if (!processToLaunch.lastSegment().equalsIgnoreCase(mainExePath.lastSegment())) {
					// passing null as the monitor should be ok as its not really being used.
					String executablesToTarget = ExecutablesTab.getExecutablesToTarget(config, null);					
					if (executablesToTarget != null && executablesToTarget.length() > 0) {
						StringTokenizer tokenizer = new StringTokenizer(executablesToTarget, ","); //$NON-NLS-1$
						while (tokenizer.hasMoreTokens()) {
							String exe = tokenizer.nextToken();
							String enabled = tokenizer.nextToken();
			
							Path exePath = new Path(exe);
							if (enabled.compareTo("1") == 0 //$NON-NLS-1$
									&& exePath.lastSegment().equalsIgnoreCase(processToLaunch.lastSegment())) {
								config.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME, exePath.toOSString());
								break;
							}
						}
					}
				}
			} catch(CoreException ce) {			
				ce.printStackTrace();			
			}
		}
	}
	
	public static String isRemoteTextValid(String remoteText) {
		String remoteProcess = remoteText.trim();
		if (remoteProcess.length() < 1) {
			return Messages.getString("RunModeMainTab.6"); //$NON-NLS-1$
		} else {
			char drive = remoteProcess.charAt(0);
			char colon = remoteProcess.length() < 2 ? 0x0 : remoteProcess.charAt(1);
			char root = remoteProcess.length() < 3 ? 0x0 : remoteProcess.charAt(2);
			char lastChar = remoteProcess.charAt(remoteProcess.length() - 1);
			if (!Character.isLetter(drive) || colon != ':' || root != '\\' || 
					lastChar == '\\' || lastChar == '/' || lastChar == ':') { 
				return Messages.getString("RunModeMainTab.7"); //$NON-NLS-1$
			}
		}
		
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#isValid(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public boolean isValid(ILaunchConfiguration config) {
		boolean result = super.isValid(config);
		if (result){
			String error = isRemoteTextValid(remoteText.getText());
			if (error != null) {
				setErrorMessage(error);
				result = false;
			}
			else {
				if (clientSiteUI != null)
				{
					connection = clientSiteUI.getSelectedConnection();
					if (connection == null) {
						setErrorMessage(Messages.getString("RunModeMainTab.NoConnectionError")); //$NON-NLS-1$
						result = false;
					}
				}
			}
		}
		
		return result;
	}

	public void resourceChanged(IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.POST_BUILD) {

			final RunModeMainTab tab = this;
			Display.getDefault().asyncExec(new Runnable() {

				public void run() {
					tab.getLaunchConfigurationDialog().updateButtons();
					tab.getLaunchConfigurationDialog().updateMessage();
				}
			});
		}
	}

}
