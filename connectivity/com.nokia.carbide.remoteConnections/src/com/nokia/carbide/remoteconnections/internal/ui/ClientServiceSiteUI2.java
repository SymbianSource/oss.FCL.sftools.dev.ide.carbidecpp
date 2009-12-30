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


package com.nokia.carbide.remoteconnections.internal.ui;

import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.*;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionListener;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionsManagerListener;
import com.nokia.carbide.remoteconnections.internal.registry.Registry;
import com.nokia.carbide.remoteconnections.settings.ui.SettingsWizard;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ListenerList;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.text.MessageFormat;
import java.util.*;
import java.util.List;

/**
 * Implementation of IClientServiceSiteUI2
 */
@SuppressWarnings("deprecation")
public class ClientServiceSiteUI2 implements IClientServiceSiteUI2, IConnectionListener, IConnectionsManagerListener {

	private IService service;
	private ComboViewer viewer;
	private FontMetrics fm;
	private Set<IConnectionType> compatibleConnectionTypes;
	private Button editButton;
	private Button newButton;
	private String connection;
	private ListenerList<IListener> listenerList;
	private static final String UID = ".uid"; //$NON-NLS-1$
	private Map<String, String> connectionNames;

	public ClientServiceSiteUI2(IService service) {
		Check.checkArg(service);
		this.service = service;
	}
	
	public void createComposite(Composite parent) {
		initializeDialogUnits(parent);
		Group group = new Group(parent, SWT.NONE);
		group.setText(Messages.getString("ClientServiceSiteUI2.UseConnectionGroupLabel")); //$NON-NLS-1$
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		group.setData(UID, "useConnectionGroup"); //$NON-NLS-1$

		viewer = new ComboViewer(group, SWT.READ_ONLY);
		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				Check.checkContract(element instanceof String);
				String id = (String) element;
				return connectionNames.get(id);
			}
		});
		viewer.setContentProvider(new ArrayContentProvider());
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		viewer.getCombo().setLayoutData(gd);
		viewer.getControl().setData(UID, "viewer"); //$NON-NLS-1$
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				String connection = (String) selection.getFirstElement();
				if (!connection.equals(ClientServiceSiteUI2.this.connection)) {
					ClientServiceSiteUI2.this.connection = connection;
					fireConnectionSelected();
				}
			}
		});

		final Composite composite = new Composite(group, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.makeColumnsEqualWidth = true;
		layout.marginWidth = Dialog.convertHorizontalDLUsToPixels(fm, IDialogConstants.HORIZONTAL_MARGIN);
		layout.marginHeight = Dialog.convertVerticalDLUsToPixels(fm, IDialogConstants.VERTICAL_MARGIN);
		layout.horizontalSpacing = Dialog.convertHorizontalDLUsToPixels(fm, IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing = Dialog.convertVerticalDLUsToPixels(fm, IDialogConstants.VERTICAL_SPACING);
		composite.setLayout(layout);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_CENTER);
		composite.setLayoutData(gd);
		composite.setFont(parent.getFont());
		
		newButton = new Button(composite, SWT.PUSH);
		newButton.setText(Messages.getString("ClientServiceSiteUI2.NewButtonLabel")); //$NON-NLS-1$
		newButton.setFont(JFaceResources.getDialogFont());
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		int widthHint = Dialog.convertHorizontalDLUsToPixels(fm, IDialogConstants.BUTTON_WIDTH);
		Point minSize = newButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		gd.widthHint = Math.max(widthHint, minSize.x);
		newButton.setLayoutData(gd);
		newButton.setData(UID, "newButton"); //$NON-NLS-1$
		newButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SettingsWizard wizard = new SettingsWizard(null, service);
				wizard.open(composite.getShell());
				IConnection connection = wizard.getConnectionToEdit();
				// note: refresh ASAP so the selection will be valid; but endure a listener event
				// which will redo this
				refreshUI();
				setViewerInput(connection);
			}
		});
		
		editButton = new Button(composite, SWT.PUSH);
		editButton.setText(Messages.getString("ClientServiceSiteUI2.EditButtonLabel")); //$NON-NLS-1$
		editButton.setFont(JFaceResources.getDialogFont());
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		widthHint = Dialog.convertHorizontalDLUsToPixels(fm, IDialogConstants.BUTTON_WIDTH);
		minSize = editButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		gd.widthHint = Math.max(widthHint, minSize.x);
		editButton.setLayoutData(gd);
		editButton.setData(UID, "editButton"); //$NON-NLS-1$
		editButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
				Object value = selection.getFirstElement();
				if (value instanceof String) {
					IConnection editConnection = getActualConnection((String) value);
					SettingsWizard wizard = new SettingsWizard(editConnection, service);
					wizard.open(composite.getShell());
					
					// leave the viewer the same, callback will refresh anything needed
				}
			}
		});

		// attach listeners
		RemoteConnectionsActivator.getConnectionsManager().addConnectionListener(this);
		RemoteConnectionsActivator.getConnectionsManager().addConnectionStoreChangedListener(this);

		// remove listeners on dispose
		group.addDisposeListener(new DisposeListener() {
			
			public void widgetDisposed(DisposeEvent e) {
				RemoteConnectionsActivator.getConnectionsManager().addConnectionListener(ClientServiceSiteUI2.this);
				RemoteConnectionsActivator.getConnectionsManager().addConnectionStoreChangedListener(ClientServiceSiteUI2.this);
			}
		});
		
		setViewerInput(null);
	}

	/**
	 * Get the actual connection for an identifier.
	 * This is not {@link IConnectionsManager#ensureConnection(String, IService)} because we don't
	 * want to actually validate the connection now.
	 * @param id
	 * @return {@link IConnection} or <code>null</code>
	 */
	protected IConnection getActualConnection(String id) {
		if (id == null) {
			return null;
		}
		if (id.equals(Registry.DEFAULT_CONNECTION_ID)) {
			return RemoteConnectionsActivator.getConnectionsManager().getDefaultConnection();
		}
		for (IConnection connection : RemoteConnectionsActivator.getConnectionsManager().getConnections()) {
			if (connection.getIdentifier().equals(id)) {
				return connection;
			}
		}
		return null;
	}

	/**
	 * Set the selected input.  
	 * @param connection existing connection or <code>null</code> for the default   
	 */
	private void setViewerInput(IConnection connection) {
		List<IConnection> compatible = getCompatibleConnections();
		connectionNames = new LinkedHashMap<String, String>();
		
		// update the default
		IConnection defaultConnection = RemoteConnectionsActivator.getConnectionsManager().getDefaultConnection();
		
		connectionNames.put(Registry.DEFAULT_CONNECTION_ID, createDefaultConnectionName(defaultConnection));
		
		for (IConnection conn : compatible) {
			connectionNames.put(conn.getIdentifier(), conn.getDisplayName());
		}
		
		viewer.setInput(connectionNames.keySet());
		
		if (connectionNames.isEmpty())
			viewer.getCombo().setEnabled(false);
		else {
			viewer.getCombo().setEnabled(true);
			if (connection == null) {
				viewer.getCombo().select(0);
				viewer.setSelection(viewer.getSelection());
			}
			else
				selectConnection(connection.getIdentifier());
		}
		editButton.setEnabled(!viewer.getSelection().isEmpty());
		
		// fire listener in case we selected anew or the default connection changed
		fireConnectionSelected();
	}

	private void refreshUI() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				if (viewer != null && viewer.getContentProvider() != null) {
					
					// try to preserve the currently selected item, if it's a concrete
					// connection; if it's default, allow for the new default to be chosen.
					IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
					Object value = selection.getFirstElement();
					String current = null;
					if (value instanceof String) {
						current = (String) value;
					}
					if (Registry.DEFAULT_CONNECTION_ID.equals(current)) {
						current = null;
					}
					setViewerInput(getActualConnection(current));
				}
			}
		});
	}
	
	/**
	 * @param defaultConnection
	 * @return
	 */
	private String createDefaultConnectionName(IConnection defaultConnection) {
		return MessageFormat.format(Messages.getString("ClientServiceSiteUI2.DefaultConnectionFormat"), //$NON-NLS-1$
				defaultConnection != null ? defaultConnection.getDisplayName() : 
					Messages.getString("ClientServiceSiteUI2.DefaultConnection_NoneDetected")); //$NON-NLS-1$
	}

	private void initializeDialogUnits(Composite parent) {
		GC gc = new GC(parent);
		gc.setFont(JFaceResources.getDialogFont());
		fm = gc.getFontMetrics();
		gc.dispose();
	}

	private List<IConnection> getCompatibleConnections() {
		getCompatibleConnectionTypes();
		
		List<IConnection> compatibleConnections = new ArrayList<IConnection>();
		for (IConnection connection : Registry.instance().getConnections()) {
			if (isCompatibleConnection(connection))
				compatibleConnections.add(connection);
		}
		return compatibleConnections;
	}
	
	private boolean isCompatibleConnection(IConnection connection) {
		return compatibleConnectionTypes.contains(connection.getConnectionType());
	}

	private void getCompatibleConnectionTypes() {
		compatibleConnectionTypes = new HashSet<IConnectionType>();
		Collection<String> compatibleTypeIds =
			Registry.instance().getCompatibleConnectionTypeIds(service);
		for (String typeId : compatibleTypeIds) {
			compatibleConnectionTypes.add(
					Registry.instance().getConnectionType(typeId));
		}
	}

	public void selectConnection(String connection) {
		viewer.setSelection(new StructuredSelection(connection));
	}
	
	public String getSelectedConnection() {
		return connection;
	}
	
	public IStatus getSelectionStatus() {
		String requiredConnectionTypes = getAllowedConnectionTypesString();
		
		// no selection yet...?
		if (connection == null) {
			return new Status(IStatus.ERROR, RemoteConnectionsActivator.PLUGIN_ID,
					MessageFormat.format(
							Messages.getString("ClientServiceSiteUI2.NoConnectionError"), //$NON-NLS-1$
							service.getDisplayName(),
							requiredConnectionTypes));
		}
		
		// check whether the default is compatible with the service and connection type
		if (Registry.DEFAULT_CONNECTION_ID.equals(connection)) {
			IConnection actual = getActualConnection(connection);
			if (actual == null) {
				return new Status(IStatus.ERROR, RemoteConnectionsActivator.PLUGIN_ID,
						MessageFormat.format(
							Messages.getString("ClientServiceSiteUI2.NoDefaultConnection"), //$NON-NLS-1$
							service.getDisplayName(),
							requiredConnectionTypes));
			}
			
			// is the service supported?
			boolean found = false;
			for (IConnectedService aService : Registry.instance().getConnectedServices(actual)) {
				if (service.getIdentifier().equals(aService.getService().getIdentifier())) {
					found = true;
					break;
				}
			}
			if (!found) {
				return new Status(IStatus.WARNING, RemoteConnectionsActivator.PLUGIN_ID,
						MessageFormat.format(
								Messages.getString("ClientServiceSiteUI2.IncompatibleDefaultConnectionService") //$NON-NLS-1$
								+ "\n"  //$NON-NLS-1$
								+ Messages.getString("ClientServiceSiteUI2.IncompatibleDefaultConnectionFixupAdvice"), //$NON-NLS-1$
								actual.getDisplayName(),
								service.getDisplayName()));
			}
			
			// is the hardware type supported by the service?
			if (!isCompatibleConnection(actual)) {
				return new Status(IStatus.WARNING, RemoteConnectionsActivator.PLUGIN_ID,
						MessageFormat.format(
								Messages.getString("ClientServiceSiteUI2.IncompatibleDefaultConnectionType") //$NON-NLS-1$
								+ "\n"  //$NON-NLS-1$
								+ Messages.getString("ClientServiceSiteUI2.IncompatibleDefaultConnectionFixupAdvice"), //$NON-NLS-1$
								actual.getDisplayName(),
								requiredConnectionTypes));
		
			}
		}
		
		// otherwise, it's okay!
		return Status.OK_STATUS;
	}

	private String getAllowedConnectionTypesString() {
		StringBuilder requiredConnectionTypes = new StringBuilder();
		IConnectionType[] connectionTypes = 
			(IConnectionType[]) compatibleConnectionTypes.toArray(new IConnectionType[compatibleConnectionTypes.size()]);
		for (int i = 0; i < connectionTypes.length; i++) {
			IConnectionType type = connectionTypes[i];
			if (requiredConnectionTypes.length() > 0)
				requiredConnectionTypes.append(", "); //$NON-NLS-1$
			if (i == connectionTypes.length - 1)
				requiredConnectionTypes.append(Messages.getString("ClientServiceSiteUI2.Or")); //$NON-NLS-1$
			requiredConnectionTypes.append(type.getDisplayName());
		}
		return requiredConnectionTypes.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IClientServiceSiteUI2#getConnectionDisplayName(java.lang.String)
	 */
	public String getConnectionDisplayName(String connection) {
		String display = connectionNames.get(connection);
		if (display == null)
			display = MessageFormat.format(Messages.getString("ClientServiceSiteUI2.DeletedConnectionDisplayName"), connection); //$NON-NLS-1$
		return display;
	}

	public void addListener(IListener listener) {
		if (listenerList == null)
			listenerList = new ListenerList<IListener>();
		listenerList.add(listener);
	}

	public void removeListener(IListener listener) {
		if (listenerList != null)
			listenerList.remove(listener);
	}

	private void fireConnectionSelected() {
		if (listenerList != null) {
			for (IListener listener : listenerList) {
				listener.connectionSelected();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionListener#connectionAdded(com.nokia.carbide.remoteconnections.interfaces.IConnection)
	 */
	public void connectionAdded(IConnection connection) {
		refreshUI();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionListener#connectionRemoved(com.nokia.carbide.remoteconnections.interfaces.IConnection)
	 */
	public void connectionRemoved(IConnection connection) {
		refreshUI();		
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionListener#defaultConnectionSet(com.nokia.carbide.remoteconnections.interfaces.IConnection)
	 */
	public void defaultConnectionSet(IConnection connection) {
		refreshUI();		
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionsManagerListener#connectionStoreChanged()
	 */
	public void connectionStoreChanged() {
		refreshUI();		
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionsManagerListener#displayChanged()
	 */
	public void displayChanged() {
		refreshUI();		
	}
}
