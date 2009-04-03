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
import com.nokia.carbide.remoteconnections.interfaces.*;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

public class POUConnectionDialog extends TrayDialog {

	private final IService service;
	private IClientServiceSiteUI clientSiteUI;
	private IConnection connection;
	/**
	 * Create the dialog
	 */
	public POUConnectionDialog(IService service, IConnection initialConnection) {
		super((Shell) null);
		this.service = service;
		this.connection = initialConnection;
		clientSiteUI = RemoteConnectionsActivator.getConnectionsManager().getClientSiteUI(service);
		
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Client Site UI for " + service.getDisplayName());
	}
	
	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		clientSiteUI.createComposite(parent);
		if (connection != null)
			clientSiteUI.selectConnection(connection);
		return container;
	}

	public IConnection getSelectedConnection() {
		return clientSiteUI.getSelectedConnection();
	}

	
}
