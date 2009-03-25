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

import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;

/**
 * Serializes connections into output stream as XML
 * Example serialized single test connection:
 * 
 * <blockquote><pre>
 *&lt;connections version="1"&gt;
 *	&lt;connection id="connection 1" type="com.nokia.carbide.remoteconnections.tests.extensions.TestConnectionType"&gt;
 *		&lt;settings&gt;
 *			&lt;setting name="file"&gt;"C:\\testConnection.txt"&lt;/setting&gt;
 *		&lt;/settings&gt;
 *	&lt;/connection&gt;
 *&lt;/connections&gt;
 * </pre></blockquote>
 */
public class Writer {

	private static final String CURRENT_VERSION = "1"; //$NON-NLS-1$
	
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n"; //$NON-NLS-1$
	private static final String CONNECTIONS_START = "<connections version=\"" + CURRENT_VERSION + "\">\n"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String CONNECTIONS_END = "</connections>\n"; //$NON-NLS-1$
	private static final String CONNECTION_START_FMT = "\t<connection id=\"{0}\" type=\"{1}\" displayName=\"{2}\">\n"; //$NON-NLS-1$
	private static final String CONNECTION_END = "\t</connection>\n"; //$NON-NLS-1$
	private static final String SETTINGS_START = "\t\t<settings>\n"; //$NON-NLS-1$
	private static final String SETTINGS_END = "\t\t</settings>\n"; //$NON-NLS-1$
	private static final String SETTING_START_FMT = "\t\t\t<setting name=\"{0}\">"; //$NON-NLS-1$
	private static final String SETTING_END = "</setting>\n"; //$NON-NLS-1$

	public static void writeToXML(OutputStream os, Collection<IConnection> connections) throws IOException {
		Check.checkArg(os);
		Check.checkArg(connections);
		
		os.write(XML_HEADER.getBytes());
		os.write(CONNECTIONS_START.getBytes());
		for (IConnection connection : connections) {
			writeConnectionStart(os, connection.getIdentifier(), 
					connection.getConnectionType().getIdentifier(), connection.getDisplayName());
			writeSettings(os, connection.getSettings());
			os.write(CONNECTION_END.getBytes());
		}
		os.write(CONNECTIONS_END.getBytes());
		os.close();
	}

	private static void writeConnectionStart(OutputStream os, String connectionId, String typeId, String displayName) throws IOException {
		String connectionStart = 
			MessageFormat.format(CONNECTION_START_FMT, new Object[] { connectionId, typeId, displayName });
		os.write(connectionStart.getBytes());
	}

	private static void writeSettings(OutputStream os, Map<String, String> settings) throws IOException {
		os.write(SETTINGS_START.getBytes());
		for (String key : settings.keySet()) {
			String settingStart =
				MessageFormat.format(SETTING_START_FMT, new Object[] { key } );
			os.write(settingStart.getBytes());
			String value = settings.get(key);
			os.write(value.getBytes());
			os.write(SETTING_END.getBytes());
		}
		os.write(SETTINGS_END.getBytes());
	}
	
}
