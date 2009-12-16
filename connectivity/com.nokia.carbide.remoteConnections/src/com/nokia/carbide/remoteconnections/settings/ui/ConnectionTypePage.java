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

import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.*;
import com.nokia.carbide.remoteconnections.internal.registry.Registry;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.jface.viewers.*;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.text.MessageFormat;
import java.util.*;
import java.util.List;

public class ConnectionTypePage extends WizardPage {

	private static final String INITIAL_NAME_FMT = "connection {0}"; //$NON-NLS-1$
	private static final String UID = ".uid"; //$NON-NLS-1$

	private final SettingsWizard settingsWizard;
	private ListViewer viewer;
	private Text nameText;
	private Label connectionTypeDescLabel;
	private Label servicesLabel;
	private boolean initialized;

	protected ConnectionTypePage(SettingsWizard settingsWizard) {
		super("typepage"); //$NON-NLS-1$
		this.settingsWizard = settingsWizard;
		setTitle(Messages.getString("ConnectionTypePage.Title")); //$NON-NLS-1$
		setDescription(Messages.getString("ConnectionTypePage.Description")); //$NON-NLS-1$
	}
	
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setData(UID, "ConnectionTypePage"); //$NON-NLS-1$
		
		Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText(Messages.getString("ConnectionTypePage.NameLabel")); //$NON-NLS-1$
		nameText = new Text(container, SWT.BORDER);
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		nameText.setLayoutData(gd);
		nameText.setToolTipText(Messages.getString("ConnectionTypePage.NameTextToolTip")); //$NON-NLS-1$
		nameText.setData(UID, "nameText"); //$NON-NLS-1$
		nameText.setText(getInitialNameText());
		nameText.selectAll();
		nameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(validatePage());
			}
		});

		Label label = new Label(container, SWT.NONE);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		label.setText(Messages.getString("ConnectionTypePage.ViewerLabel")); //$NON-NLS-1$
		
		viewer = new ListViewer(container, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL);
		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				Check.checkState(element instanceof IConnectionType);
				IConnectionType connectionType = (IConnectionType) element;
				return connectionType.getDisplayName();
			}
		});
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setInput(getConnectionTypes());
		gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		viewer.getControl().setLayoutData(gd);
		viewer.getControl().setData(UID, "viewer"); //$NON-NLS-1$
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			private IConnectionType previousSelection;
			public void selectionChanged(SelectionChangedEvent event) {
				Object currentSelection = ((IStructuredSelection) event.getSelection()).getFirstElement();
				if (!currentSelection.equals(previousSelection)) {
					connectionTypeDescLabel.setText(getConnectionTypeDescription());
					servicesLabel.setText(getServicesString());
					settingsWizard.connectionTypeChanged();
					previousSelection = (IConnectionType) currentSelection;
				}
			}
		});
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				getWizard().getContainer().showPage(getNextPage());
			}
		});
		viewer.setSorter(new ViewerSorter() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				IConnectionType t1 = (IConnectionType) e1;
				IConnectionType t2 = (IConnectionType) e2;
				return t1.getDisplayName().compareToIgnoreCase(t2.getDisplayName());
			}
		});
		viewer.getList().select(getCurrentTypeIndex());
		
		connectionTypeDescLabel = new Label(container, SWT.WRAP);
		connectionTypeDescLabel.setText(getConnectionTypeDescription());
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		connectionTypeDescLabel.setLayoutData(gd);
		connectionTypeDescLabel.setData(UID, "additionalNotesLabel"); //$NON-NLS-1$
		
		servicesLabel = new Label(container, SWT.WRAP);
		servicesLabel.setText(getServicesString());
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		servicesLabel.setLayoutData(gd);
		servicesLabel.setData(UID, "servicesLabel"); //$NON-NLS-1$
		
		setControl(container);
		RemoteConnectionsActivator.setHelp(container, ".connection_type_page"); //$NON-NLS-1$
	}
	
	private String getServicesString() {
		StringBuilder servicesString = new StringBuilder();
		Collection<IService> services = 
			Registry.instance().getCompatibleServices(getConnectionType());
		if (services == null || services.isEmpty())
			return ""; //$NON-NLS-1$
		for (Iterator<IService> iterator = services.iterator(); iterator.hasNext();) {
			servicesString.append(iterator.next().getDisplayName());
			if (iterator.hasNext())
				servicesString.append(", "); //$NON-NLS-1$
		}
		
		return MessageFormat.format(Messages.getString("ConnectionTypePage.SupportedServicesLabel"), new Object[] { servicesString.toString() }); //$NON-NLS-1$
	}

	private String getConnectionTypeDescription() {
		return getConnectionType().getDescription();
	}
	
	private int getCurrentTypeIndex() {
		IConnection connectionToEdit = settingsWizard.getConnectionToEdit();
		if (connectionToEdit != null) {
			Object input = viewer.getInput();
			if (input != null) {
				Collection<IConnectionType> connectionTypes = (Collection<IConnectionType>) input;
				for (int i = 0; i < connectionTypes.size(); i++) {
					IConnectionType connectionType = (IConnectionType) viewer.getElementAt(i);
					if (connectionToEdit.getConnectionType().equals(connectionType))
						return i;
				}
			}
		}
		return 0;
	}
	
	private Collection<IConnectionType> getConnectionTypes() {
		Collection<IConnectionType> connectionTypes = getValidConnectionTypes();
		IService serviceToRestrict = settingsWizard.getServiceToRestrict();
		if (serviceToRestrict != null) {
			List<IConnectionType> restrictedConnectionTypes = new ArrayList<IConnectionType>();
			Collection<String> compatibleConnectionTypeIds = 
				Registry.instance().getCompatibleConnectionTypeIds(serviceToRestrict);
			for (String connectionTypeId : compatibleConnectionTypeIds) {
				IConnectionType connectionType = 
					Registry.instance().getConnectionType(connectionTypeId);
				if (connectionTypes.contains(connectionType))
					restrictedConnectionTypes.add(connectionType);
			}
			return restrictedConnectionTypes;
		}
		
		return connectionTypes;
	}

	private Collection<IConnectionType> getValidConnectionTypes() {
		// valid connection types have at least one compatible service, or are the actual connection type of the connection being edited
		IConnection connectionToEdit = settingsWizard.getConnectionToEdit();
		IConnectionType connectionTypeToEdit = connectionToEdit != null ? connectionToEdit.getConnectionType() : null;
		Collection<IConnectionType> allConnectionTypes = 
		Registry.instance().getConnectionTypes();
		Collection<IConnectionType> connectionTypes = new ArrayList<IConnectionType>();
		for (IConnectionType connectionType : allConnectionTypes) {
			if (!Registry.instance().getCompatibleServices(connectionType).isEmpty() ||
					connectionType.equals(connectionTypeToEdit))
				connectionTypes.add(connectionType);
		}
		return connectionTypes;
	}

	private boolean validatePage() {
		setErrorMessage(null);
		String name = getName();
		boolean isValid = isNameUnique(name);
		if (!isValid) {
			setErrorMessage(MessageFormat.format(Messages.getString("ConnectionTypePage.ConnectionNameInUseError"), new Object[] { name } )); //$NON-NLS-1$
		}
		
		return isValid;
	}
	
	private boolean isNameUnique(String name) {
		boolean inUse = Registry.instance().connectionNameInUse(name);
		IConnection connectionToEdit = settingsWizard.getConnectionToEdit();
		if (connectionToEdit != null && inUse)
			inUse = !name.equals(connectionToEdit.getDisplayName());
		
		return !inUse;
	}

	private String getInitialNameText() {
		IConnection connectionToEdit = settingsWizard.getConnectionToEdit();
		if (connectionToEdit != null)
			return connectionToEdit.getDisplayName();
		
		long i = 1;
		while (true) {
			String name = MessageFormat.format(INITIAL_NAME_FMT, new Object[] { Long.toString(i++) });
			if (isNameUnique(name))
				return name;
		}
	}

	public String getName() {
		return nameText.getText().trim();
	}
	
	public IConnectionType getConnectionType() {
		return (IConnectionType) ((IStructuredSelection) viewer.getSelection()).getFirstElement();
	}
	
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (!initialized && visible) {
			initialized = true;
			viewer.setSelection(viewer.getSelection());
			settingsWizard.connectionTypeChanged();
		}
	}
}
