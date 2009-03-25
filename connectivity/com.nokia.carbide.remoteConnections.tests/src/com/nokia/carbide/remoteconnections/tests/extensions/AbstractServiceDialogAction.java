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

package com.nokia.carbide.remoteconnections.tests.extensions;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IService;
import com.nokia.carbide.remoteconnections.internal.registry.Registry;
import com.nokia.carbide.remoteconnections.tests.Activator;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import java.util.Collection;

public abstract class AbstractServiceDialogAction implements IWorkbenchWindowActionDelegate {

	public AbstractServiceDialogAction() {
		super();
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
	}
	
	protected abstract String getServiceId(); 
	
	public void run(IAction action) {
		IService service = findService(getServiceId());
		IConnection connection = getPersistedConnection(getServiceId());
		POUConnectionDialog dialog = new POUConnectionDialog(service, connection);
		dialog.open();
		connection = dialog.getSelectedConnection();
		persistConnection(getServiceId(), connection);
	}

	protected void persistConnection(String key, IConnection connection) {
		if (connection != null) {
			Activator.getDefault().getPreferenceStore().putValue(key, connection.getIdentifier());
			Activator.getDefault().savePluginPreferences();
		}
	}

	protected IConnection getPersistedConnection(String key) {
		String connectionId = Activator.getDefault().getPreferenceStore().getString(key);
		if (connectionId != null) {
			Collection<IConnection> connections = RemoteConnectionsActivator.getConnectionsManager().getConnections();
			for (IConnection connection : connections) {
				if (connection.getIdentifier().equals(connectionId))
					return connection;
			}
		}
		return null;
	}

	protected IService findService(String serviceId) {
		Collection<IService> services = Registry.instance().getServices();
		for (IService service : services) {
			if (service.getIdentifier().equals(serviceId)) {
				return service;
			}
		}
		return null;
	}

}