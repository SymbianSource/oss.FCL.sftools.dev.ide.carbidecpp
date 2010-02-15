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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionTypeProvider;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionListener;
import com.nokia.carbide.remoteconnections.settings.ui.SettingsWizard;

/**
 *	This dialog allows in-depth configuration of the connection settings.
 */
@SuppressWarnings("restriction")
public class ConnectToDeviceDialog extends AbstractLaunchSettingsDialog implements IConnectionListener {
	private IConnectionsManager manager;
	private IConnectionTypeProvider typeProvider;
	private ComboViewer viewer;
	private Button editButton;
	private Label descriptionLabel;

	protected ConnectToDeviceDialog(Shell shell, LaunchWizardData data) {
		super(shell, data);
		manager = RemoteConnectionsActivator.getConnectionsManager();
		typeProvider = RemoteConnectionsActivator.getConnectionTypeProvider();
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite composite = initDialogArea(parent, 
				"Change connection",
				LaunchWizardHelpIds.WIZARD_DIALOG_CHANGE_CONNECTION);
		
		Composite viewerGroup = new Composite(composite, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(viewerGroup);
		GridLayoutFactory.swtDefaults().numColumns(3).applyTo(viewerGroup);
		
		Label label = new Label(viewerGroup, SWT.NONE);
		label.setText("Current connection");
		GridDataFactory.defaultsFor(label).applyTo(label);
		
		viewer = new ComboViewer(viewerGroup, SWT.READ_ONLY);
		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IConnection)
					return ((IConnection) element).getDisplayName();
				
				return "No Current connection";
			}
		});
		viewer.setContentProvider(new ArrayContentProvider());
		Combo combo = viewer.getCombo();
		GridDataFactory.defaultsFor(combo).grab(true, false).applyTo(combo);
		viewer.getControl().setData(UID, "combo_viewer"); //$NON-NLS-1$
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if (getDialogArea() != null)
					connectionSelected(getConnectionFromSelection(event.getSelection()));
			}
		});
		manager.addConnectionListener(this);
		
		editButton = new Button(viewerGroup, SWT.PUSH);
		editButton.setText("Edit...");
		GridDataFactory.defaultsFor(editButton).applyTo(editButton);
		editButton.setData(UID, "edit_button"); //$NON-NLS-1$
		editButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IConnection connection = getConnectionFromSelection(viewer.getSelection()); 
				if (connection != null) {
					SettingsWizard wizard = new SettingsWizard(connection, data.getService());
					wizard.open(composite.getShell());
				}
			}
		});

		descriptionLabel = new Label(composite, SWT.WRAP);
		GridDataFactory.defaultsFor(descriptionLabel).grab(false, true).applyTo(descriptionLabel);
		composite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				descriptionLabel.pack();
			}
		});
		
		setViewerInput(data.getConnection());

		return composite;
	}
	
	protected void validate() {
		IStatus status = ConnectToDeviceSection.revalidate(data);

		if (status.isOK()) {
			IConnection connection = data.getConnection();
			if (connection != null) {
				IConnectedService connectedService = null;
				Collection<IConnectedService> services = manager.getConnectedServices(connection);
				if (services != null) {
					for (IConnectedService service : services) {
						if (service != null && service.getService().getIdentifier().equals(data.getService().getIdentifier())) {
							connectedService = service;
						}
					}
				}
				
				if (connectedService == null) {
					status = error(MessageFormat.format(
							"The selected connection does not support {0}",
							data.getService().getDisplayName()));
				}
				else {
					com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus serviceStatus = 
						connectedService.getStatus();
					if (!serviceStatus.getEStatus().equals(
							com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus.EStatus.UP)) {
						status = warning("The selected connection may not be usable for debugging:\n {0}", 
								serviceStatus.getLongDescription());
					}
				}
			}
		}
		updateStatus(status);
	}

	/**
	 *	Update for a change in the connection.  We will attempt to connect to the
	 *  device (once) to detect what TRK it is running.
	 */
	private void updateConnection(IConnection connection) {
		String standardPNPMessage = ConnectToDeviceSection.getStandardPNPMessage();
		data.setConnection(connection);
		if (connection != null) {
			descriptionLabel.setText(standardPNPMessage);
		} else {
			descriptionLabel.setText("No connections are detected or defined.  " + standardPNPMessage);
		}
		
	}

	public void connectionSelected(IConnection connection) {
		updateConnection(connection);
		validate();
	}
	
	public void connectionAdded(IConnection connection) {
		refreshUI();
	}

	public void connectionRemoved(IConnection connection) {
		refreshUI();		
	}

	public void currentConnectionSet(IConnection connection) {
		refreshUI();		
	}

	private Set<IConnectionType> getCompatibleConnectionTypes() {
		HashSet<IConnectionType> types = new HashSet<IConnectionType>();
		Collection<String> compatibleTypeIds =
			typeProvider.getCompatibleConnectionTypeIds(data.getService());
		for (String typeId : compatibleTypeIds) {
			types.add(typeProvider.getConnectionType(typeId));
		}
		return types;
	}

	private List<IConnection> getCompatibleConnections() {
		Set<IConnectionType> types = getCompatibleConnectionTypes();
		
		List<IConnection> compatibleConnections = new ArrayList<IConnection>();
		for (IConnection connection : manager.getConnections()) {
			if (types.contains(connection.getConnectionType()))
				compatibleConnections.add(connection);
		}
		return compatibleConnections;
	}
	
	private void setViewerInput(IConnection connection) {
		List<IConnection> connections = getCompatibleConnections();
		viewer.setInput(connections);
		
		if (connections.isEmpty())
			viewer.getCombo().setEnabled(false);
		else {
			viewer.getCombo().setEnabled(true);
			if (connection == null) {
				viewer.getCombo().select(0);
				ISelection selection = viewer.getSelection();
				connection = getConnectionFromSelection(selection);
				viewer.setSelection(selection);
			}
			else
				viewer.setSelection(new StructuredSelection(connection));
		}
		editButton.setEnabled(!viewer.getSelection().isEmpty());
		
		// fire listener in case we selected anew or the current connection changed
		connectionSelected(connection);
	}
	
	private IConnection getConnectionFromSelection(ISelection selection) {
		return (IConnection) ((IStructuredSelection) selection).getFirstElement();
	}

	private void refreshUI() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				if (viewer != null && viewer.getContentProvider() != null) {
					setViewerInput(getConnectionFromSelection(viewer.getSelection()));
				}
			}
		});
	}
	
	@Override
	public boolean close() {
		manager.addConnectionListener(this);
		return super.close();
	}
}

