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


package com.nokia.carbide.remoteconnections.tests.extensions;

import com.nokia.carbide.remoteconnections.interfaces.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.text.MessageFormat;
import java.util.*;

/**
 *
 */
public class IntervalConnectionType implements IConnectionType {

	public static final String KEY = "interval";
	public static final String VALUE = "100";

	/**
	 * 
	 */
	public IntervalConnectionType() {
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.extensions.IConnectionType#getConnectionFactory()
	 */
	public IConnectionFactory getConnectionFactory() {
		return new IConnectionFactory() {
			private Map<String, String> settings = new HashMap<String, String>(Collections.singletonMap(KEY, VALUE));
			
			public IConnection createConnection(Map<String, String> settings) {
				IntervalConnection connection = new IntervalConnection(IntervalConnectionType.this);
				if (settings != null) {
					connection.updateSettings(settings);
				}
				return connection;
			}

			public void createEditingUI(Composite parent, final IValidationErrorReporter errorReporter, Map<String, String> initialSettings) {
				Composite composite = new Composite(parent, SWT.NONE);
				composite.setLayout(new GridLayout(2, false));
				composite.setLayoutData(new GridData(GridData.FILL_BOTH));
				Label label = new Label(composite, SWT.NONE);
				label.setText("Test to cycle interval (ms):");
				label.setLayoutData(new GridData());
				final Text text = new Text(composite, SWT.BORDER);
				text.setText(settings.get(KEY));
				text.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL));
				text.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						String value = text.getText().trim();
						try {
							int intval = Integer.parseInt(value);
							if (intval < 0) {
								errorReporter.setErrorMessage("Negative values not allowed");
								return;
							}
						}
						catch (NumberFormatException x) {
							if (value.length() == 0)
								errorReporter.setErrorMessage("A value must be entered");
							else
								errorReporter.setErrorMessage(MessageFormat.format("''{0}'' is not a valid integer", value));
							return;
						}
						errorReporter.setErrorMessage(null);
						settings.put(KEY, value);
					}
				});
				if (initialSettings != null && initialSettings.containsKey(KEY))
					text.setText(initialSettings.get(KEY));
			}

			public Map<String, String> getSettingsFromUI() {
				return settings;
			}
			
		};
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.extensions.IConnectionType#getDisplayName()
	 */
	public String getDisplayName() {
		return "Latency Interval Connection";
	}

	public String getDescription() {
		return "The settings are the latency between testing and a change in status";
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.extensions.IConnectionType#getHelpContext()
	 */
	public String getHelpContext() {
		return "com.nokia.cdt.debug.launch.trk_connection_usb";
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.extensions.IConnectionType#getIdentifier()
	 */
	public String getIdentifier() {
		return getClass().getName();
	}

}
