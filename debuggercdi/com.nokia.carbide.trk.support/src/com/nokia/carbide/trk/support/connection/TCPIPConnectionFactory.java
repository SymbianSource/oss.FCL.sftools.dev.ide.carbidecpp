package com.nokia.carbide.trk.support.connection;

import com.nokia.carbide.remoteconnections.interfaces.*;
import com.nokia.carbide.trk.support.Messages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.HashMap;
import java.util.Map;

public class TCPIPConnectionFactory implements IConnectionFactory {

	public static final String IP_ADDRESS = "ipAddress"; //$NON-NLS-1$
	public static final String IP_PORT = "port"; //$NON-NLS-1$
	public static final String DEFAULT_PORT = "7654"; //$NON-NLS-1$
	protected static final String UID = ".uid"; //$NON-NLS-1$
	
	protected IConnectionType connectionType;
	private Text text;
	protected String address;
	protected HashMap<String, String> settings;
	protected Composite composite;

	public TCPIPConnectionFactory(IConnectionType connectionType) {
		this.connectionType = connectionType;
		settings = new HashMap<String, String>();
		settings.put(IP_PORT, DEFAULT_PORT); // hard-coded
		address = ""; //$NON-NLS-1$
	}
	
	public TCPIPConnectionFactory(IConnectionType connectionType, String initialAddress) {
		this(connectionType);
		address = initialAddress;
	}

	public IConnection createConnection(Map<String, String> settings) {
		if (settings == null)
			settings = getSettingsFromUI();
		return new TCPIPConnection(connectionType, settings);
	}

	public void createEditingUI(Composite parent, final IValidationErrorReporter errorReporter, Map<String, String> initialSettings) {
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
	
		Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.getString("TCPIPConnectionFactory.Label")); //$NON-NLS-1$
	
		text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				errorReporter.setErrorMessage(null);
				String string = text.getText();
				if (validateAddress(errorReporter))
					address = string;
			}
		});
		text.setText(address);
		text.setData(UID, "TCPIPConnectionFactory.text"); //$NON-NLS-1$ $NON-NLS-2$
		
		if (initialSettings != null) {
			String address = initialSettings.get(IP_ADDRESS);
			if (address != null)
				text.setText(address);
		}
		validateAddress(errorReporter);
	}
	
	private boolean validateAddress(IValidationErrorReporter errorReporter) {
		String string = text.getText();
		if (string.length() == 0) {
			errorReporter.setErrorMessage(Messages.getString("TCPIPConnectionFactory.NoAddressError")); //$NON-NLS-1$
			return false;
		}
		
		return true;
	}

	public Map<String, String> getSettingsFromUI() {
		settings.put(IP_ADDRESS, address);
		return settings;
	}

}