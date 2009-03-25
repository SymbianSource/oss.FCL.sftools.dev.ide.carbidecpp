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


package com.nokia.carbide.remoteconnections.internal.registry;

import com.nokia.carbide.remoteconnections.*;
import com.nokia.carbide.remoteconnections.interfaces.*;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.Version;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.*;

import javax.xml.parsers.*;

/**
 * Reads connections element containing connection settings data as XML
 * @see Writer
 */
public class Reader {

	private static final String CONNECTIONS_ELEMENT = "connections"; //$NON-NLS-1$
	private static final String VERSION_ATTR = "version"; //$NON-NLS-1$
	private static final String CONNECTION_ELEMENT = "connection"; //$NON-NLS-1$
	private static final String ID_ATTR = "id"; //$NON-NLS-1$
	private static final String DISPLAY_NAME_ATTR = "displayName"; //$NON-NLS-1$
	private static final String TYPE_ATTR = "type"; //$NON-NLS-1$
	private static final String SETTINGS_ELEMENT = "settings"; //$NON-NLS-1$
	private static final String SETTING_ELEMENT = "setting"; //$NON-NLS-1$
	private static final String NAME_ATTR = "name"; //$NON-NLS-1$

	public static List<IConnection> readFromXML(IConnectionTypeProvider connectionTypeProvider, InputStream is) throws SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} 
		catch (ParserConfigurationException e) {
			Check.checkContract(false); // should never occur
		}
		Check.checkState(db != null);
		Document document = db.parse(is);
		is.close();
		
		NodeList connectionsNodes = document.getElementsByTagName(CONNECTIONS_ELEMENT);
		Check.checkContract(connectionsNodes.getLength() >= 1);
		// ignore all but first <connections> element (should only be one)
		Element connectionsElement = (Element) connectionsNodes.item(0);
		String versionStr = connectionsElement.getAttribute(VERSION_ATTR);
		Version version = Version.parseVersion(versionStr);
		if (version.getMajor() == 1)
			return readV1(connectionTypeProvider, connectionsElement);
		
		throw new IllegalArgumentException(
				MessageFormat.format(Messages.getString("Reader.UnknownVersionError"),  //$NON-NLS-1$
						new Object[] { versionStr }));
	}
	
	private static List<IConnection> readV1(IConnectionTypeProvider connectionTypeProvider, Element connectionsElement) {
		List<IConnection> connections = new ArrayList<IConnection>();
		
		NodeList connectionNodes = connectionsElement.getElementsByTagName(CONNECTION_ELEMENT);
		for (int i = 0; i < connectionNodes.getLength(); i++) {
			Element connectionElement = (Element) connectionNodes.item(i);
			String typeId = connectionElement.getAttribute(TYPE_ATTR);
			String id = connectionElement.getAttribute(ID_ATTR);
			String displayName = connectionElement.getAttribute(DISPLAY_NAME_ATTR);
			IConnectionType connectionType = connectionTypeProvider.getConnectionType(typeId);
			if (connectionType == null) {
				Plugin p = RemoteConnectionsActivator.getDefault();
				String message = 
					MessageFormat.format(Messages.getString("Reader.MissingConnectionTypeError"),  //$NON-NLS-1$
							new Object[] { id, typeId });
				Logging.log(p, Logging.newStatus(p, IStatus.WARNING, message));
				continue;
			}
			Map<String, String> settings = readSettingsV1(connectionElement); 
			IConnection connection = connectionType.getConnectionFactory().createConnection(settings);
			connection.setIdentifier(id);
			connection.setDisplayName(displayName);
			connections.add(connection);
		}
		
		return connections;
	}

	private static Map<String, String> readSettingsV1(Element connectionElement) {
		Map<String, String> settings = new HashMap<String, String>();
		
		NodeList settingsNodes = connectionElement.getElementsByTagName(SETTINGS_ELEMENT);
		Check.checkContract(settingsNodes.getLength() >= 1);
		// ignore all but first <settings> element (should only be one)
		Element settingsElement = (Element) settingsNodes.item(0);
		NodeList settingNodes = settingsElement.getElementsByTagName(SETTING_ELEMENT);
		for (int i = 0; i < settingNodes.getLength(); i++) {
			Element settingElement = (Element) settingNodes.item(i);
			String key = settingElement.getAttribute(NAME_ATTR);
			String value = settingElement.getTextContent();
			settings.put(key, value);
		}
		return settings;
	}
	
}
