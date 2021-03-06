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
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatusChangedListener;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionTypeProvider;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionsManager.IConnectionListener;
import com.nokia.carbide.remoteconnections.settings.ui.SettingsWizard;
import com.nokia.cpp.internal.api.utils.ui.LinkParser;
import com.nokia.cpp.internal.api.utils.ui.LinkParser.Element;

/**
 *	This dialog allows in-depth configuration of the connection settings.
 */
public class ConnectToDeviceDialog extends AbstractLaunchSettingsDialog implements IConnectionListener, IStatusChangedListener {
	private IConnectionsManager manager;
	private IConnectionTypeProvider typeProvider;
	private FontMetrics fm;
	private ComboViewer viewer;
	private Button editButton;
	private Label descriptionLabel;
	private Button newButton;
	private IConnectedService currentServiceListener;
	private IConnectionWizardData connectionData;

	protected ConnectToDeviceDialog(Shell shell, IWizardData data) {
		super(shell, data);
		manager = RemoteConnectionsActivator.getConnectionsManager();
		typeProvider = RemoteConnectionsActivator.getConnectionTypeProvider();
		connectionData = (IConnectionWizardData) data;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		initializeDialogUnits(parent);
		final Composite composite = initDialogArea(parent, 
				Messages.getString("ConnectToDeviceDialog.Title"), //$NON-NLS-1$
				LaunchWizardHelpIds.WIZARD_DIALOG_CHANGE_CONNECTION);
		
		Group viewerGroup = new Group(composite, SWT.NONE);
		viewerGroup.setText(Messages.getString("ConnectToDeviceDialog.GroupLabel")); //$NON-NLS-1$
		GridDataFactory.fillDefaults().applyTo(viewerGroup);
		GridLayoutFactory.swtDefaults().applyTo(viewerGroup);
		
		viewer = new ComboViewer(viewerGroup, SWT.READ_ONLY);
		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IConnection)
					return ((IConnection) element).getDisplayName();
				
				return Messages.getString("ConnectToDeviceDialog.NoCurrentItem"); //$NON-NLS-1$
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

		parent.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				manager.removeConnectionListener(ConnectToDeviceDialog.this);
				
				if (currentServiceListener != null)
					currentServiceListener.removeStatusChangedListener(ConnectToDeviceDialog.this);
			}
		});
		
		final Composite buttonGroup = new Composite(viewerGroup, SWT.NONE);
		int w = Dialog.convertHorizontalDLUsToPixels(fm, IDialogConstants.HORIZONTAL_MARGIN);
		int h = Dialog.convertVerticalDLUsToPixels(fm, IDialogConstants.VERTICAL_MARGIN);
		int hs = Dialog.convertHorizontalDLUsToPixels(fm, IDialogConstants.HORIZONTAL_SPACING);
		int vs = Dialog.convertVerticalDLUsToPixels(fm, IDialogConstants.VERTICAL_SPACING);
		GridLayoutFactory.swtDefaults().numColumns(2).equalWidth(true).
			margins(w, h).spacing(hs, vs).applyTo(buttonGroup);
		GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(buttonGroup);
		buttonGroup.setFont(parent.getFont());
		
		newButton = new Button(buttonGroup, SWT.PUSH);
		newButton.setText(Messages.getString("ConnectToDeviceDialog.NewLabel")); //$NON-NLS-1$
		newButton.setFont(JFaceResources.getDialogFont());
		int widthHint = Dialog.convertHorizontalDLUsToPixels(fm, IDialogConstants.BUTTON_WIDTH);
		Point minSize = newButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		widthHint = Math.max(widthHint, minSize.x);
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.TOP).hint(widthHint, SWT.DEFAULT).applyTo(newButton);
		newButton.setData(UID, "newButton"); //$NON-NLS-1$
		newButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SettingsWizard wizard = new SettingsWizard(null, connectionData.getService());
				wizard.open(composite.getShell());
				IConnection connection = wizard.getConnectionToEdit();
				setViewerInput(connection);
			}
		});
		
		editButton = new Button(buttonGroup, SWT.PUSH);
		editButton.setText(Messages.getString("ConnectToDeviceDialog.EditLabel")); //$NON-NLS-1$
		editButton.setFont(JFaceResources.getDialogFont());
		widthHint = Dialog.convertHorizontalDLUsToPixels(fm, IDialogConstants.BUTTON_WIDTH);
		minSize = editButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		widthHint = Math.max(widthHint, minSize.x);
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).hint(widthHint, SWT.DEFAULT).applyTo(editButton);
		editButton.setData(UID, "editButton"); //$NON-NLS-1$
		editButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IConnection connection = getConnectionFromSelection(viewer.getSelection()); 
				if (connection != null) {
					SettingsWizard wizard = new SettingsWizard(connection, connectionData.getService());
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
		
		setViewerInput(connectionData.getConnection());

		return composite;
	}
	
	private void initializeDialogUnits(Composite parent) {
		GC gc = new GC(parent);
		gc.setFont(JFaceResources.getDialogFont());
		fm = gc.getFontMetrics();
		gc.dispose();
	}

	protected void validate() {
		IStatus status = ConnectToDeviceSection.revalidate(connectionData);

		if (status.isOK()) {
			IConnection connection = connectionData.getConnection();
			if (connection != null) {
				IConnectedService connectedService = findConnectedServiceFromConnection(connection);
				
				if (connectedService == null) {
					status = error(MessageFormat.format(
							Messages.getString("ConnectToDeviceDialog.ServiceNotSupportedError"), //$NON-NLS-1$
							connectionData.getService().getDisplayName()));
				}
				else {
					com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus serviceStatus = 
						connectedService.getStatus();
					if (!serviceStatus.getEStatus().equals(
							com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus.EStatus.UP)) {
						String description  = serviceStatus.getLongDescription();
						if (description != null) {
							List<Element> elements = LinkParser.parseText(description);
							StyledString styledString = LinkParser.getStyledString(elements);
							description = styledString.getString();
						}
						status = warning(Messages.getString("ConnectToDeviceDialog.ServiceNotAvailWarning"),  //$NON-NLS-1$
								description == null ? "" : description); //$NON-NLS-1$
					}
				}
			}
		}
		updateStatus(status);
	}

	private IConnectedService findConnectedServiceFromConnection(IConnection connection) {
		Collection<IConnectedService> services = manager.getConnectedServices(connection);
		for (IConnectedService connectedService : services) {
			if (connectedService != null && connectedService.getService().getIdentifier().equals(connectionData.getService().getIdentifier())) {
				return connectedService;
			}
		}
		return null;
	}

	/**
	 *	Update for a change in the connection.  We will attempt to connect to the
	 *  device (once) to detect what TRK it is running.
	 */
	private void updateConnection(IConnection connection) {
		String standardPNPMessage = ConnectToDeviceSection.getStandardPNPMessage();
		connectionData.setConnection(connection);
		if (connection != null) {
			descriptionLabel.setText(standardPNPMessage);
		} else {
			descriptionLabel.setText(Messages.getString("ConnectToDeviceDialog.NoConnectionsText") + standardPNPMessage); //$NON-NLS-1$
		}
		if (currentServiceListener != null)
			currentServiceListener.removeStatusChangedListener(this);
		currentServiceListener = findConnectedServiceFromConnection(connection);
		if (currentServiceListener != null)
			currentServiceListener.addStatusChangedListener(this);
	}

	protected void connectionSelected(IConnection connection) {
		updateConnection(connection);
		validate();
	}
	
	public void connectionAdded(IConnection connection) {
		refreshUI(null);
	}

	public void connectionRemoved(IConnection connection) {
		refreshUI(null);		
	}

	public void currentConnectionSet(IConnection connection) {
//		refreshUI(connection);
	}

	public void statusChanged(com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus status) {
		validate();
	}
	
	private Set<IConnectionType> getCompatibleConnectionTypes() {
		HashSet<IConnectionType> types = new HashSet<IConnectionType>();
		Collection<String> compatibleTypeIds =
			typeProvider.getCompatibleConnectionTypeIds(connectionData.getService());
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

	private void refreshUI(final IConnection connection) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				if (viewer != null && viewer.getContentProvider() != null) {
					IConnection connectionToSet = connection == null ? getConnectionFromSelection(viewer.getSelection()) : connection;
					setViewerInput(connectionToSet);
				}
			}
		});
	}
}

