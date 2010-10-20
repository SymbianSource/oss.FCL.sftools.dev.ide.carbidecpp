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
import com.nokia.carbide.remoteconnections.settings.ui.SettingsWizard;

/**
 * Present the "Connect to device" section with a short description.
 */
public class ConnectToDeviceSection extends AbstractLaunchWizardSection implements IConnectionListener {

	private static final String NO_CURRENT_CONNECTION_MSG = Messages.getString("ConnectToDeviceSection.NoConnectionMsg"); //$NON-NLS-1$
	private final IConnectionsManager manager;
	private IConnectionWizardData connectionData;

	/**
	 * @param unifiedLaunchOptionsPage 
	 * 
	 */
	public ConnectToDeviceSection(IWizardData data, AbstractUnifiedLaunchOptionsPage launchOptionsPage) {
		super(data, Messages.getString("ConnectToDeviceSection.Title"), launchOptionsPage); //$NON-NLS-1$
		manager = RemoteConnectionsActivator.getConnectionsManager();
		connectionData = (IConnectionWizardData) data;
	}
	
	public void createControl(Composite parent) {
		createSection(parent, 0);
		manager.addConnectionListener(this);
	}
	
	@Override
	protected void dispose() {
		manager.removeConnectionListener(this);
	}
	
	public void initializeSettings() {
		connectionData.setConnection(manager.getCurrentConnection());
	}

	@Override
	protected void validate() {
		status = revalidate(connectionData);
	}

	/** Get the simple status for the connection state */
	static IStatus revalidate(IConnectionWizardData data) {
		IStatus status = Status.OK_STATUS;
		
		if (data.getConnection() == null) {
			status = error(NO_CURRENT_CONNECTION_MSG);
		}
		
		return status;
	}
	
	static String getStandardPNPMessage() {
		return Messages.getString("ConnectToDeviceSection.StdPNPMsg"); //$NON-NLS-1$
	}

	@Override
	protected void updateUI() {
		if (control == null || control.isDisposed())
			return;
		
		String msg;
		if (connectionData.getConnection() != null)
			msg = MessageFormat.format(Messages.getString("ConnectToDeviceSection.CurrentConnectionLabel"), connectionData.getConnectionName()); //$NON-NLS-1$
		else
			msg = MessageFormat.format("{0} {1}", NO_CURRENT_CONNECTION_MSG, getStandardPNPMessage()); //$NON-NLS-1$
			
		descriptionLabel.setText(msg);
		launchOptionsPage.changed();
	}
	
	@Override
	protected AbstractLaunchSettingsDialog createChangeSettingsDialog(Shell shell, IWizardData dialogData) {
		return new ConnectToDeviceDialog(shell, dialogData);
	}
	
	protected void refresh() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				validate();
				updateUI();
			}
		});
	}
	
	private void doConnectionsChanged() {
		connectionData.setConnection(manager.getCurrentConnection());
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
		// if no connections are available, immediately offer to create a connection
		
		if (manager.getConnections().isEmpty()) {
			SettingsWizard wizard = new SettingsWizard(null, connectionData.getService());
			wizard.open(getControl().getShell());
			IConnection newConnection = wizard.getConnectionToEdit();
			connectionData.setConnection(newConnection);
		} else {
			super.doChange();
		}
		
		IConnection connection = connectionData.getConnection();
		if (connection != null && !connection.equals(manager.getCurrentConnection()))
			manager.setCurrentConnection(connection);
	}
}
