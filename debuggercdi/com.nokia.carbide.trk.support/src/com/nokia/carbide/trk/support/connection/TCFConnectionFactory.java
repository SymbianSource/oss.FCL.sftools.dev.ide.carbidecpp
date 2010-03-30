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

package com.nokia.carbide.trk.support.connection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;
import com.nokia.carbide.remoteconnections.interfaces.IService;
import com.nokia.carbide.remoteconnections.interfaces.IService2;

public class TCFConnectionFactory extends TCPIPConnectionFactory {

	private static final int MIN_DYN_PORT_NUM = 49152;
	private static final int MAX_DYN_PORT_NUM = 65535;
	private Map<String, Integer> serviceIdToPortMappings;
	private TableViewer viewer;
	
	public TCFConnectionFactory(IConnectionType connectionType) {
		super(connectionType);
		serviceIdToPortMappings = new HashMap<String, Integer>();
		initializePortMappings();
	}

	private void initializePortMappings() {
		Collection<IService> services = RemoteConnectionsActivator.getConnectionTypeProvider().getCompatibleServices(connectionType);
		for (IService service : services) {
			IService2 service2 = service instanceof IService2 ? (IService2) service : null;
			Integer defaultPort = null;
			if (service2 != null) {
				try {
					String defaultPortString = service2.getDefaults().get(IP_PORT);
					defaultPort = Integer.valueOf(defaultPortString);
				} catch (NumberFormatException e) {
					// if it doesn't parse as an int, we ignore it and provide a dynamic default
				}
			}
			if (defaultPort != null)
				serviceIdToPortMappings.put(service.getIdentifier(), defaultPort);
		}
		for (IService service : services) {
			String identifier = service.getIdentifier();
			if (!serviceIdToPortMappings.containsKey(identifier))
				serviceIdToPortMappings.put(identifier, getUnusedDynamicDefault());
		}
		
	}

	private Integer getUnusedDynamicDefault() {
		for (Integer val = MIN_DYN_PORT_NUM; val < MAX_DYN_PORT_NUM; val++) {
			if (!serviceIdToPortMappings.containsValue(val))
				return val;
		}
		return -1; // should never get here!!!
	}

	@Override
	public IConnection createConnection(Map<String, String> settings) {
		if (settings == null)
			settings = getSettingsFromUI();
		return new TCFConnection(connectionType, settings);
	}

	@Override
	public void createEditingUI(Composite parent, IValidationErrorReporter errorReporter, Map<String, String> initialSettings) {
		super.createEditingUI(parent, errorReporter, initialSettings);
/*		updatePortMappings(initialSettings);
		Label label = new Label(composite, SWT.NONE);
		GridDataFactory.defaultsFor(label).span(2, 1).applyTo(label);
		label.setText(Messages.getString("TCPIPPortMappingConnectionFactory.ViewerLabel")); //$NON-NLS-1$
	
		viewer = new TableViewer(composite, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
		viewer.setContentProvider(new ArrayContentProvider());
		
		TableViewerColumn serviceColumn = new TableViewerColumn(viewer, SWT.LEFT);
		serviceColumn.getColumn().setText(Messages.getString("TCPIPPortMappingConnectionFactory.ServiceHeader")); //$NON-NLS-1$
		
		TableViewerColumn portColumn = new TableViewerColumn(viewer, SWT.RIGHT);
		portColumn.getColumn().setText(Messages.getString("TCPIPPortMappingConnectionFactory.PortHeader")); //$NON-NLS-1$
		portColumn.setEditingSupport(new PortColumnEditingSupport(viewer));
		
		viewer.setLabelProvider(new TableLabelProvider());
		viewer.setInput(serviceIdToPortMappings.keySet());
		
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		GridDataFactory.defaultsFor(table).span(2, 1).hint(SWT.DEFAULT, 60).grab(true, false).applyTo(table);
		table.setToolTipText(Messages.getString("TCPIPPortMappingConnectionFactory.ViewerTooltip")); //$NON-NLS-1$
		table.setData(UID, "TCPIPPortMappingConnectionFactory.table"); //$NON-NLS-1$
		table.setData("viewer", viewer); //$NON-NLS-1$
		packColumns();
*/	}

	private Integer getValidValue(String value) {
		try {
			int intVal = Integer.parseInt(value);
			if (intVal < 0)
				return new Integer(0);
			else if (intVal > MAX_DYN_PORT_NUM)
				return new Integer(MAX_DYN_PORT_NUM);
			else
				return new Integer(intVal);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public Map<String, String> getSettingsFromUI() {
		Map<String, String> settings = super.getSettingsFromUI();
		addMappings(settings);
		return settings;
	}

	private void addMappings(Map<String, String> settings) {
		for (String serviceId : serviceIdToPortMappings.keySet()) {
			settings.put(serviceId, String.valueOf(serviceIdToPortMappings.get(serviceId).intValue()));
		}
		settings.remove(IP_PORT);
	}

}
