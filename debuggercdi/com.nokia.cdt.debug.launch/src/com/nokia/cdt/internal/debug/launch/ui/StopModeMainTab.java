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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.freescale.cdt.debug.cw.core.RemoteConnectionsTRKHelper;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IClientServiceSiteUI2;
import com.nokia.carbide.remoteconnections.interfaces.IClientServiceSiteUI2.IListener;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionFactory;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

public class StopModeMainTab extends CarbideMainTab {

	protected IClientServiceSiteUI2 clientSiteUI;
	protected String connection;

	// PlatSim settings keys
	private final static String SETTING_LOCATION = "location"; //$NON-NLS-1$
	private final static String SETTING_INSTANCE = "instance"; //$NON-NLS-1$

	private static String PLATSIM_CONNECTION_TYPE = "com.nokia.carbide.trk.support.connection.PlatSimConnectionType"; //$NON-NLS-1$

	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		
		LaunchPlugin.getDefault().getWorkbench().getHelpSystem().setHelp(getControl(), LaunchTabHelpIds.STOP_MODE_MAIN);
		
		GridLayout topLayout = new GridLayout();
		comp.setLayout(topLayout);

		createVerticalSpacer(comp, 1);
		createProjectGroup(comp, 1);
		createExeFileGroup(comp, 1);
		
		fProgLabel.setText(Messages.getString("StopModeMainTab.0")); //$NON-NLS-1$
		fProgLabel.setToolTipText(Messages.getString("StopModeMainTab.1")); //$NON-NLS-1$
		fProgText.setToolTipText(Messages.getString("StopModeMainTab.1")); //$NON-NLS-1$

		fProjLabel.setToolTipText(Messages.getString("StopModeMainTab.2")); //$NON-NLS-1$
		fProjText.setToolTipText(Messages.getString("StopModeMainTab.2")); //$NON-NLS-1$

		clientSiteUI = RemoteConnectionsActivator.getConnectionsManager().getClientSiteUI2(LaunchPlugin.getPlatSimService());
		clientSiteUI.createComposite(comp);
		clientSiteUI.addListener(new IListener() {
			public void connectionSelected() {
				connection = clientSiteUI.getSelectedConnection();
				updateLaunchConfigurationDialog();
			}
		});

		createVerticalSpacer(comp, 1);
		if (wantsTerminalOption() /*&& ProcessFactory.supportesTerminal()*/) {
			createTerminalOption(comp, 1);
			createVerticalSpacer(comp, 1);
		}
		createBuildOptionGroup(comp, 1);
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
	}

	public void initializeFrom(ILaunchConfiguration config) {
		super.initializeFrom(config);

		// if a PlatSim stop mode launch does not have a connection,
		// it may need to be converted to have a connection
		try {
			connection = RemoteConnectionsTRKHelper.getConnectionIdFromConfig(config);
		} catch (CoreException e) {
		}
		
		if (connection == null && config instanceof ILaunchConfigurationWorkingCopy) {
			String location = null;
			String instanceId = null;
			try {
				// for new launches, the remote connection holds the location and instance
				// we can use "1" as the default PlatSim instanceId, but we won't create a new
				// connection if we do not have a PlatSim_Location attribute
				location = config.getAttribute("com.nokia.cdt.debug.launch.PlatSim_Location", (String) null);
				instanceId = config.getAttribute("com.nokia.cdt.debug.launch.PlatSim_Instance_Id", "1");
			} catch (CoreException ce) {
				LaunchPlugin.log(ce);
			}

			if (location != null && location.length() > 0) {

				ILaunchConfigurationWorkingCopy wcConfig = (ILaunchConfigurationWorkingCopy)config;
				
				// if an existing PlatSim connection matches, then use it
				// if no connection matches, then create a new one
				IConnection connectionToUse = findOrCreatePlatSimConnection(location, instanceId);
				connection = connectionToUse.getIdentifier();
				if (connection != null) {
					wcConfig.setAttribute(RemoteConnectionsTRKHelper.CONNECTION_ATTRIBUTE, connection);
				}		
			}
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
		if (connection != null) {
			config.setAttribute(RemoteConnectionsTRKHelper.CONNECTION_ATTRIBUTE, connection);
		}		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#isValid(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public boolean isValid(ILaunchConfiguration config) {
		boolean result = super.isValid(config);
		return result;
	}
	

	/**
	 * Find a PlatSim connection with the given location and instance, or create one
	 * 
	 * @param locationToFind path to PlatSim executable
	 * @param instanceToFind PlatSim instance number
	 * @return
	 */
	private IConnection findOrCreatePlatSimConnection(String locationToFind, String instanceToFind) {
		Collection<IConnection> connections = RemoteConnectionsActivator.getConnectionsManager().getConnections();
		IConnection matchingConnection = null;
		for (IConnection connection : connections) {
			String connectionTypeId = connection.getConnectionType().getIdentifier();
			if (connectionTypeId.equals(PLATSIM_CONNECTION_TYPE)) {
				Map<String, String> settings = connection.getSettings(); 
				String instance = settings.get(SETTING_INSTANCE);
				String location = settings.get(SETTING_LOCATION);
				if (instanceToFind.equals(instance) && locationToFind.equals(location)) {
					matchingConnection = connection;
					break;
				}
			}
		}
		
		if (matchingConnection == null) {
			// create a new one
			IConnectionType connectionType = RemoteConnectionsActivator.getConnectionTypeProvider().getConnectionType(PLATSIM_CONNECTION_TYPE);
			if (connectionType == null)
				return null;
			IConnectionFactory factory = connectionType.getConnectionFactory();
			Map<String, String> settings = new HashMap<String, String>(factory.getSettingsFromUI());
			settings.put(SETTING_LOCATION, locationToFind);
			settings.put(SETTING_INSTANCE, instanceToFind);
			matchingConnection = factory.createConnection(settings);

			// if location is non-null, see if you can find the brief SDK name
			// TODO: if we could access PlatSimManager from here, use the following
//			String preferredName = null;
//			try {
//				IPlatSimInstance theInstance = PlatSimManager.INSTANCE.getInstallationInstance(
//						new Path(locationToFind), Integer.parseInt(instanceToFind));
//				preferredName = PlatSimManager.INSTANCE.getPreferredConnectionName(theInstance);
//			} catch (Exception e) {
//				// no luck: suggest nothing
//			}
//
//			if (preferredName == null) {
//				if (locationToFind.charAt(1) == ':') // Windows drive indicator
//					preferredName = "PlatSim " + locationToFind.substring(0, 2) +
//									", Instance " + instanceToFind;
//				else
//					preferredName = "PlatSim Instance " + instanceToFind;
//			}

			String preferredName;
			if (locationToFind.charAt(1) == ':') // Windows drive indicator
				preferredName = "PlatSim " + locationToFind.substring(0, 2) +
									", Instance " + instanceToFind;
			else
				preferredName = "PlatSim Instance " + instanceToFind;

			String newName = preferredName;
			boolean isUnique = true;
			long i = 1;
			do {
				for (IConnection connection : connections) {
					if (preferredName.equals(connection.getDisplayName())) {
						isUnique = false;
						break;
					}
				}
				
				if (!isUnique)
					newName = preferredName + i;
			} while (!isUnique);

			matchingConnection.setDisplayName(newName);
			RemoteConnectionsActivator.getConnectionsManager().addConnection(matchingConnection);
		}
		
		return matchingConnection;
	}


}
