/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.remoteconnections.settings.ui;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;
import com.nokia.carbide.remoteconnections.interfaces.IService;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2;
import com.nokia.carbide.remoteconnections.internal.registry.Registry;

/**
 * Main wizard class for hosting new and edit connection settings UI
 */
public class SettingsWizard extends Wizard {

	private ConnectionTypePage connectionTypePage;
	private ConnectionSettingsPage connectionSettingsPage;
	private IConnection connectionToEdit;
	private IService serviceToRestrict;
	private Map<IConnectedService, Boolean> savedEnabledStates;
	
	public SettingsWizard(IConnection connectionToEdit, IService serviceToRestrict) {
		this.connectionToEdit = connectionToEdit;
		this.serviceToRestrict = serviceToRestrict;
		if (connectionToEdit == null)
			setWindowTitle(Messages.getString("SettingsWizard.WindowTitle.New")); //$NON-NLS-1$
		else {
			setWindowTitle(MessageFormat.format(Messages.getString("SettingsWizard.WindowTitle.Edit"), connectionToEdit.getDisplayName())); //$NON-NLS-1$
			saveConnectedServicesEnabledState();
			enableConnectedServices(false);
		}
		setNeedsProgressMonitor(true);
	}
	
	public SettingsWizard(IConnection connectionToEdit) {
		this(connectionToEdit, null);
	}
	
	public SettingsWizard() {
		this(null, null);
	}

	@Override
	public void addPages() {
		connectionTypePage = new ConnectionTypePage(this);
		addPage(connectionTypePage);
		connectionSettingsPage = new ConnectionSettingsPage(this);
		addPage(connectionSettingsPage);
	}
	
    public IWizardPage getStartingPage() {
    	if (connectionToEdit == null)
    		return connectionTypePage;
    	else
    		return connectionSettingsPage;
    }
    
    public IConnection getConnectionToEdit() {
		return connectionToEdit;
	}
    
    public boolean isConnectionToEditDynamic() {
    	return connectionToEdit instanceof IConnection2 &&
    		((IConnection2) connectionToEdit).isDynamic();
    }

	public IService getServiceToRestrict() {
		return serviceToRestrict;		
	}
	
	public IConnectionType getConnectionType() {
		return connectionTypePage.getConnectionType();
	}
	
	@Override
	public boolean performFinish() {
		String newName = connectionTypePage.getName();
		Map<String, String> newSettings = connectionSettingsPage.getSettings();
		IConnectionType newConnectionType = connectionTypePage.getConnectionType();
		if (connectionToEdit == null || !connectionToEdit.getConnectionType().equals(newConnectionType)) {
			String id = null;
			if (connectionToEdit != null) {
				id = connectionToEdit.getIdentifier();
				Registry.instance().removeConnection(connectionToEdit);
			}
			connectionToEdit = newConnectionType.getConnectionFactory().createConnection(newSettings);
			if (id != null)
				connectionToEdit.setIdentifier(id);
			
			connectionToEdit.setDisplayName(newName);
			Registry.instance().addConnection(connectionToEdit);
		}
		else if (newSettings != null) {
			connectionToEdit.updateSettings(newSettings);
		}
		if (!newName.equals(connectionToEdit.getDisplayName())) {
			connectionToEdit.setDisplayName(newName);
			Registry.instance().updateDisplays();
		}
		
		enableConnectedServices(true);
		Registry.instance().storeConnections();
		return true;
	}
	
	@Override
	public boolean performCancel() {
		enableConnectedServices(true);
		return true;
	}

	private void saveConnectedServicesEnabledState() {
		Collection<IConnectedService> connectedServices = Registry.instance().getConnectedServices(connectionToEdit);
		if (connectedServices.isEmpty())
			return;
		if (savedEnabledStates == null)
			savedEnabledStates = new HashMap<IConnectedService, Boolean>();
		for (IConnectedService connectedService : connectedServices) {
			savedEnabledStates.put(connectedService, connectedService.isEnabled());
		}
	}

	private void enableConnectedServices(boolean enabled) {
		Collection<IConnectedService> connectedServices = Registry.instance().getConnectedServices(connectionToEdit);
		if (connectedServices.isEmpty() || savedEnabledStates == null)
			return;
		for (IConnectedService connectedService : connectedServices) {
			if (!enabled) // disable when asked
				connectedService.setEnabled(false);
			else {
				Boolean wasEnabled = savedEnabledStates.get(connectedService);
				if (wasEnabled != null && wasEnabled) // reenable only if was enabled
					connectedService.setEnabled(true);
			}
		}
	}

	public int open(Shell shell) {
		WizardDialog dialog = new WizardDialog(shell, this);
		return dialog.open();
	}

	public void connectionTypeChanged() {
		connectionSettingsPage.updateDynamicUI();
		getContainer().updateButtons();
	}
}
