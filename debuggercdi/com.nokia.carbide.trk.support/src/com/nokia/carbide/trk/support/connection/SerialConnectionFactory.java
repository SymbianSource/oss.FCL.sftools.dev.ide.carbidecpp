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


package com.nokia.carbide.trk.support.connection;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.freescale.cdt.debug.cw.core.SerialConnectionSettings;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionFactory;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;
import com.nokia.carbide.trk.support.Messages;

/**
 * Implementation of IConnectionFactory for serial connections
 */
public class SerialConnectionFactory implements IConnectionFactory {
	
	private final IConnectionType connectionType;
	private SerialConnectionSettings settings;
	private Map<String, ComboViewer> viewers;
	private boolean showOnlyPort;

	public SerialConnectionFactory(IConnectionType connectionType, boolean showOnlyPort) {
		this.connectionType = connectionType;
		this.showOnlyPort = showOnlyPort;
		viewers = new HashMap<String, ComboViewer>();
		settings = new SerialConnectionSettings(null);
	}

	public IConnection createConnection(Map<String, String> settings) {
		if (settings == null)
			settings = new HashMap<String, String>(this.settings.getSettings());
		SerialConnectionSettings.setDefaultPortNumber(settings.get(SerialConnectionSettings.PORT));
		return new SerialConnection(connectionType, settings);
	}
	
	public void createEditingUI(Composite parent, IValidationErrorReporter errorReporter, Map<String, String> initialSettings) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		createComboForSettings(composite, 
				Messages.getString("SerialConnectionFactory.PortLabel"),  //$NON-NLS-1$
				SerialConnectionSettings.PORT, settings.getComPortStrings());
		if (!showOnlyPort) {
			createComboForSettings(composite, 
					Messages.getString("SerialConnectionFactory.BaudLabel"),  //$NON-NLS-1$
					SerialConnectionSettings.BAUD, SerialConnectionSettings.BAUD_VALS);
		}
//		createComboForSettings(composite, 
//				Messages.getString("SerialConnectionFactory.DataBitsLabel"),  //$NON-NLS-1$
//				SerialConnectionSettings.DATA_BITS, SerialConnectionSettings.DATA_BIT_VALS, listener);
//		createComboForSettings(composite, 
//				Messages.getString("SerialConnectionFactory.ParityLabel"),  //$NON-NLS-1$
//				SerialConnectionSettings.PARITY, SerialConnectionSettings.PARITY_VALS, listener);
//		createComboForSettings(composite, 
//				Messages.getString("SerialConnectionFactory.StopBitsLabel"),  //$NON-NLS-1$
//				SerialConnectionSettings.STOP_BITS, SerialConnectionSettings.STOP_BIT_VALS, listener);
//		createComboForSettings(composite, 
//				Messages.getString("SerialConnectionFactory.FlowControlLabel"),  //$NON-NLS-1$
//				SerialConnectionSettings.FLOW_CONTROL, SerialConnectionSettings.FLOW_CONTROL_VALS, listener);

		if (initialSettings != null)
			settings.initialize(initialSettings);
		
		putSettingsToUI();
	}

	private void createComboForSettings(Composite composite, String labelStr, final String key, String[] input) {
		Label label = new Label(composite, SWT.NONE);
		label.setText(labelStr);
		
		final ComboViewer viewer = new ComboViewer(composite, SWT.READ_ONLY);
		viewer.getCombo().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				int index = SerialConnectionSettings.toIndex(key, element.toString());
				return SerialConnectionSettings.toDisplayString(key, index);
			}
		});
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				int index = viewer.getCombo().getSelectionIndex();
				settings.setByIndex(key, index);
			}
		});
		viewer.setInput(input);
		viewer.getControl().setData(".uid", "SerialConnectionFactory." + key); //$NON-NLS-1$ //$NON-NLS-2$
		viewers.put(key, viewer);
	}

	public Map<String, String> getSettingsFromUI() {
		return new HashMap<String, String>(settings.getSettings());
	}

	private void putSettingsToUI() {
		Map<String, String> map = settings.getSettings();
		for (String key : map.keySet()) {
			ComboViewer viewer = viewers.get(key);
			if (viewer != null) {
				String element = map.get(key);
				viewer.setSelection(element != null ? new StructuredSelection(element) : StructuredSelection.EMPTY);
			}
		}
	}
}
