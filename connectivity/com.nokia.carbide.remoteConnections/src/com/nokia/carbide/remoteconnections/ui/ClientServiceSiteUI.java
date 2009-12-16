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


package com.nokia.carbide.remoteconnections.ui;

import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.*;
import com.nokia.carbide.remoteconnections.internal.registry.Registry;
import com.nokia.carbide.remoteconnections.settings.ui.SettingsWizard;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ListenerList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.*;
import java.util.List;

/**
 * Implementation of IClientServiceSiteUI
 */
public class ClientServiceSiteUI implements IClientServiceSiteUI {

	private IService service;
	private ComboViewer viewer;
	private FontMetrics fm;
	private Set<IConnectionType> compatibleConnectionTypes;
	private Button editButton;
	private Button newButton;
	private IConnection connection;
	private ListenerList<IListener> listenerList;
	private static final String UID = ".uid"; //$NON-NLS-1$
	

	public ClientServiceSiteUI(IService service) {
		Check.checkArg(service);
		this.service = service;
	}

	public void createComposite(Composite parent) {
		initializeDialogUnits(parent);
		Group group = new Group(parent, SWT.NONE);
		group.setText(Messages.getString("ClientServiceSiteUI.UseConnectionGroupLabel")); //$NON-NLS-1$
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		group.setData(UID, "useConnectionGroup"); //$NON-NLS-1$

		viewer = new ComboViewer(group, SWT.READ_ONLY);
		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				Check.checkContract(element instanceof IConnection);
				return ((IConnection) element).getDisplayName();
			}
		});
		viewer.setContentProvider(new ArrayContentProvider());
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		viewer.getCombo().setLayoutData(gd);
		viewer.getControl().setData(UID, "viewer"); //$NON-NLS-1$
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				IConnection connection = (IConnection) selection.getFirstElement();
				if (!connection.equals(ClientServiceSiteUI.this.connection)) {
					ClientServiceSiteUI.this.connection = connection;
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
		newButton.setText(Messages.getString("ClientServiceSiteUI.NewButtonLabel")); //$NON-NLS-1$
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
				setViewerInput(wizard.getConnectionToEdit());
			}
		});
		
		editButton = new Button(composite, SWT.PUSH);
		editButton.setText(Messages.getString("ClientServiceSiteUI.EditButtonLabel")); //$NON-NLS-1$
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
				if (value instanceof IConnection) {
					SettingsWizard wizard = new SettingsWizard((IConnection) value, service);
					wizard.open(composite.getShell());
					setViewerInput(wizard.getConnectionToEdit());
				}
			}
		});

		setViewerInput(null);
	}

	private void setViewerInput(IConnection connection) {
		List<IConnection> input = getCompatibleConnections();
		viewer.setInput(input);
		if (input.isEmpty())
			viewer.getCombo().setEnabled(false);
		else {
			viewer.getCombo().setEnabled(true);
			if (connection == null) {
				viewer.getCombo().select(0);
				viewer.setSelection(viewer.getSelection());
			}
			else
				selectConnection(connection);
		}
		editButton.setEnabled(!viewer.getSelection().isEmpty());
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

	public void selectConnection(IConnection connection) {
		if (!viewerInputContainsConnection(connection)) {
			addConnectionToViewerInput(connection);
		}
		viewer.setSelection(new StructuredSelection(connection));
	}
	
	private boolean viewerInputContainsConnection(IConnection connection) {
		Object input = viewer.getInput();
		if (input instanceof List) {
			return ((List) input).contains(connection);
		}
		return false;
	}

	private void addConnectionToViewerInput(IConnection connection) {
		Object input = viewer.getInput();
		if (input instanceof Collection) {
			List<IConnection> newInput = new ArrayList<IConnection>((Collection) input);
			newInput.add(connection);
			viewer.setInput(newInput);
		}
	}
	
	public IConnection getSelectedConnection() {
		return connection;
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
}
