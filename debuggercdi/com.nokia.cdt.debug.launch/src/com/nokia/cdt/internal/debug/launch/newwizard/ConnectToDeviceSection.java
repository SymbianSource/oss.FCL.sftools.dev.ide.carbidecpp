/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.cdt.internal.debug.launch.newwizard;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionListener;

/**
 * Present the "Connect to device" section with a short description.
 */
public class ConnectToDeviceSection extends AbstractLaunchWizardSection implements IConnectionListener {

	private static final String NO_CURRENT_CONNECTION_MSG = "No current connection is defined or detected.";
	private final UnifiedLaunchOptionsPage launchOptionsPage;
	private final IConnectionsManager manager;

	/**
	 * @param unifiedLaunchOptionsPage 
	 * 
	 */
	public ConnectToDeviceSection(LaunchOptionsData data, UnifiedLaunchOptionsPage launchOptionsPage) {
		super(data, "Connect to device");
		this.launchOptionsPage = launchOptionsPage;
		manager = RemoteConnectionsActivator.getConnectionsManager();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.wizard2.IWizardSection#createComposite(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		createSection(parent, 0);
		manager.addConnectionListener(this);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.wizard2.AbstractLaunchWizardSection#dispose()
	 */
	@Override
	protected void dispose() {
		manager.removeConnectionListener(this);
	}
	
	public void initializeSettings() {
		data.setConnection(manager.getCurrentConnection());
	}

	@Override
	protected void validate() {
		status = revalidate(data);
	}

	/** Get the simple status for the connection state */
	static IStatus revalidate(LaunchOptionsData data) {
		IStatus status = Status.OK_STATUS;
		
		if (data.getConnection() == null) {
			status = error(NO_CURRENT_CONNECTION_MSG);
		}
		
		return status;
	}
	
	static String getStandardPNPMessage() {
		return "You may plug in a device over USB or activate it over WLAN, or create a new connection now for your device or simulator.";
	}

	@Override
	protected void updateUI() {
		if (control == null || control.isDisposed())
			return;
		
		String msg;
		if (data.getConnection() != null)
			msg = MessageFormat.format("The current connection is now ''{0}''.", data.getConnectionName());
		else
			msg = MessageFormat.format("{0} {1}", NO_CURRENT_CONNECTION_MSG, getStandardPNPMessage());
			
		descriptionLabel.setText(msg);
		launchOptionsPage.changed();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.cdt.internal.debug.launch.wizard2.AbstractLaunchWizardSection#createChangeSettingsDialog(org.eclipse.swt.widgets.Shell, com.nokia.cdt.internal.debug.launch.wizard2.LaunchOptionsData)
	 */
	@Override
	protected AbstractLaunchSettingsDialog createChangeSettingsDialog(Shell shell, LaunchOptionsData dialogData) {
		return new ConnectToDeviceDialog(shell, dialogData);
	}
	
	protected void refresh() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				validate();
				updateUI();
			}
		});
	}
	
	private void doConnectionsChanged() {
		data.setConnection(manager.getCurrentConnection());
		refresh();
	}
	
	public void connectionAdded(IConnection connection) {
		doConnectionsChanged();
	}
	
	public void connectionRemoved(IConnection connection) {
		doConnectionsChanged();
	}
	
	public void currentConnectionSet(IConnection connection) {
		doConnectionsChanged();
	}
	
	@Override
	protected void doChange() {
		super.doChange();
		IConnection connection = data.getConnection();
		if (connection != null && !connection.equals(manager.getCurrentConnection()))
			manager.setCurrentConnection(connection);
	}
}
