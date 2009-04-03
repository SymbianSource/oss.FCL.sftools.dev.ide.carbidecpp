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


package com.nokia.carbide.remoteconnections.tests;

import com.nokia.carbide.remoteconnections.interfaces.*;
import com.nokia.carbide.remoteconnections.internal.registry.*;
import com.nokia.carbide.remoteconnections.internal.registry.Reader;
import com.nokia.carbide.remoteconnections.internal.registry.Writer;
import com.nokia.carbide.remoteconnections.tests.extensions.TestFilter;

import org.eclipse.swt.widgets.Composite;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;

import junit.framework.TestCase;

/**
 *
 */
public class SerializationTest extends TestCase {
	
	private static final String C0 = "C0";
	private static final String C1 = "C1";
	private static final String C2 = "C2";
	private static final String VAL1_KEY = "val1";
	private static final String VAL1_VALUE = "ONE";
	private static final String VAL2_KEY = "val2";
	private static final String VAL2_VALUE = "TWO";
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n";
	private static final String NO_SETTINGS_TYPE = "SerializerTest.NoSettingsType";
	private static final String ONE_SETTING_TYPE = "SerializerTest.OneSettingType";
	private static final String TWO_SETTINGS_TYPE = "SerializerTest.TwoSettingsType";
	private static final String CONNECTION_START_FMT = "\t<connection id=\"{0}\" type=\"{1}\" displayName=\"{2}\">\n";
	private static final String CONNECTIONS_START = "<connections version=\"1\">\n";
	private static final String CONNECTIONS_END = "</connections>\n";
	private static final String CONNECTION_END = "\t</connection>\n";
	private static final String SETTINGS_START = "\t\t<settings>\n";
	private static final String SETTINGS_END = "\t\t</settings>\n";

	private static String CONNECTION_NO_SETTINGS = 
		MessageFormat.format(CONNECTION_START_FMT, new Object[] { C0, NO_SETTINGS_TYPE, C0 }) +
		SETTINGS_START +
		SETTINGS_END +
		CONNECTION_END;
	
	private static String CONNECTION_ONE_SETTING =
		MessageFormat.format(CONNECTION_START_FMT, new Object[] { C1, ONE_SETTING_TYPE, C1 }) +
		SETTINGS_START +
		"\t\t\t<setting name=\"" +
		VAL1_KEY +
		"\">" +
		VAL1_VALUE +
		"</setting>\n" +
		SETTINGS_END +
		CONNECTION_END;

	private static String CONNECTION_TWO_SETTINGS =
		MessageFormat.format(CONNECTION_START_FMT, new Object[] { C2, TWO_SETTINGS_TYPE, C2 }) +
		SETTINGS_START +
		"\t\t\t<setting name=\"" +
		VAL1_KEY +
		"\">" +
		VAL1_VALUE +
		"</setting>\n" +
		"\t\t\t<setting name=\"" +
		VAL2_KEY +
		"\">" +
		VAL2_VALUE +
		"</setting>\n" +
		SETTINGS_END +
		CONNECTION_END;
	
	private static class ConnectionTypeProvider implements IConnectionTypeProvider {
		public IConnectionType getConnectionType(String identifier) {
			return new ConnectionTypeImpl(identifier);
		}
		
		public Collection<IService> getCompatibleServices(IConnectionType connectionType) {
			return Collections.EMPTY_LIST; // unimplemented
		}

		public Collection<IConnectionType> getConnectionTypes() {
			return Collections.EMPTY_LIST; // unimplemented
		}

		public IService findServiceByID(String id) {
			return null; // unimplemented
		}

		public Collection<String> getCompatibleConnectionTypeIds(IService service) {
			return null; // unimplemented
		}
	}

	private static class ConnectionTypeImpl implements IConnectionType {
		private String id;

		public ConnectionTypeImpl(String id) {
			this.id = id;
		}

		public IConnectionFactory getConnectionFactory() {
			return new IConnectionFactory() {

				public IConnection createConnection(Map<String, String> settings) {
					IConnection connection = new ConnectionImpl(id);
					connection.updateSettings(settings);
					return connection;
				}

				public void createEditingUI(Composite parent, IValidationErrorReporter errorReporter, Map<String, String> initialSettings) {
				}

				public Map<String, String> getSettingsFromUI() {
					return null;
				}
				
			};
		}
		
		public String getDisplayName() {
			return null;
		}
		
		public String getDescription() {
			return null;
		}

		public String getHelpContext() {
			return null;
		}
		
		public String getIdentifier() {
			return id;
		}
	}
	
	private static class ConnectionImpl implements IConnection {

		private String id;
		private String name;
		private Map<String, String> settings;
		private final String typeId;

		public ConnectionImpl(String typeId) {
			this.typeId = typeId;
			settings = Collections.EMPTY_MAP;
		}
		
		public void dispose() {
		}

		public IConnectionType getConnectionType() {
			return new ConnectionTypeImpl(typeId);
		}

		public String getIdentifier() {
			return id;
		}

		public Map<String, String> getSettings() {
			return settings;
		}

		public void setIdentifier(String id) {
			this.id = id;
		}

		public String getDisplayName() {
			return name;
		}

		public void setDisplayName(String name) {
			this.name = name;
		}

		public void updateSettings(Map<String, String> newSettings) {
			this.settings = newSettings;
		}

		public void useConnection(boolean use) {
		}
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		TestFilter.isTest = true;
		Registry.instance().loadExtensions();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	private String createConnectionData(String[] connectionStrings) {
		StringBuilder builder = new StringBuilder();
		builder.append(XML_HEADER);
		builder.append(CONNECTIONS_START);
		for (String string : connectionStrings) {
			builder.append(string);
		}
		builder.append(CONNECTIONS_END);
		return builder.toString();
	}
	
	private void setConnectionId(IConnection conn, String id) {
		conn.setIdentifier(id);
		conn.setDisplayName(id);
	}
	
	public void testWriteNoConnections() throws Exception {
		OutputStream os = new ByteArrayOutputStream(); 
		Writer.writeToXML(os, Collections.EMPTY_LIST);
		assertEquals(createConnectionData(new String[0]), os.toString());
	}
	
	public void testWriteNoSettings() throws Exception {
		OutputStream os = new ByteArrayOutputStream(); 
		ConnectionImpl conn = new ConnectionImpl(NO_SETTINGS_TYPE);
		setConnectionId(conn, C0);
		Writer.writeToXML(os, Collections.singletonList((IConnection) conn));
		assertEquals(createConnectionData( new String[] {CONNECTION_NO_SETTINGS} ), os.toString());
	}
	
	public void testWriteOneSetting() throws Exception {
		OutputStream os = new ByteArrayOutputStream(); 
		ConnectionImpl conn = new ConnectionImpl(ONE_SETTING_TYPE);
		setConnectionId(conn, C1);
		conn.updateSettings(Collections.singletonMap(VAL1_KEY, VAL1_VALUE));
		Writer.writeToXML(os, Collections.singletonList((IConnection) conn));
		assertEquals(createConnectionData( new String[] {CONNECTION_ONE_SETTING} ), os.toString());
	}
	
	public void testWriteTwoSettings() throws Exception {
		OutputStream os = new ByteArrayOutputStream(); 
		ConnectionImpl conn = new ConnectionImpl(TWO_SETTINGS_TYPE);
		setConnectionId(conn, C2);
		Map<String, String> settings = new HashMap();
		settings.put(VAL1_KEY, VAL1_VALUE);
		settings.put(VAL2_KEY, VAL2_VALUE);
		conn.updateSettings(settings);
		Writer.writeToXML(os, Collections.singletonList((IConnection) conn));
		assertEquals(createConnectionData( new String[] {CONNECTION_TWO_SETTINGS} ), os.toString());
	}
	
	public void testWriteMultConnections() throws Exception {
		OutputStream os = new ByteArrayOutputStream(); 
		ConnectionImpl conn0 = new ConnectionImpl(NO_SETTINGS_TYPE);
		setConnectionId(conn0, C0);
		ConnectionImpl conn1 = new ConnectionImpl(ONE_SETTING_TYPE);
		setConnectionId(conn1, C1);
		conn1.updateSettings(Collections.singletonMap(VAL1_KEY, VAL1_VALUE));
		ConnectionImpl conn2 = new ConnectionImpl(TWO_SETTINGS_TYPE);
		setConnectionId(conn2, C2);
		Map<String, String> settings = new HashMap();
		settings.put(VAL1_KEY, VAL1_VALUE);
		settings.put(VAL2_KEY, VAL2_VALUE);
		conn2.updateSettings(settings);
		IConnection[] connections = new IConnection[] { conn0, conn1, conn2 };
		Writer.writeToXML(os, Arrays.asList(connections));
		String connectionData = 
			createConnectionData( 
					new String[] {CONNECTION_NO_SETTINGS, CONNECTION_ONE_SETTING, CONNECTION_TWO_SETTINGS} );
		assertEquals(connectionData, os.toString());
	}
	
	public void testReadNoConnections() throws Exception {
		String connectionData = createConnectionData(new String[0]);
		InputStream is = new ByteArrayInputStream(connectionData.getBytes());
		List<IConnection> connections = Reader.readFromXML(new ConnectionTypeProvider(), is);
		assertTrue(connections.isEmpty());
	}
	
	public void testReadNoSettings() throws Exception {
		String connectionData = createConnectionData(new String[] {CONNECTION_NO_SETTINGS});
		InputStream is = new ByteArrayInputStream(connectionData.getBytes());
		List<IConnection> connections = Reader.readFromXML(new ConnectionTypeProvider(), is);
		assertEquals(1, connections.size());
		IConnection conn = connections.get(0);
		assertEquals(C0, conn.getIdentifier());
		assertEquals(NO_SETTINGS_TYPE, conn.getConnectionType().getIdentifier());
	}
	
	public void testReadOneSetting() throws Exception {
		String connectionData = createConnectionData(new String[] {CONNECTION_ONE_SETTING});
		InputStream is = new ByteArrayInputStream(connectionData.getBytes());
		List<IConnection> connections = Reader.readFromXML(new ConnectionTypeProvider(), is);
		assertEquals(1, connections.size());
		IConnection conn = connections.get(0);
		assertEquals(C1, conn.getIdentifier());
		assertEquals(ONE_SETTING_TYPE, conn.getConnectionType().getIdentifier());
		assertEquals(Collections.singletonMap(VAL1_KEY, VAL1_VALUE), conn.getSettings());
	}
	
	public void testReadTwoSettings() throws Exception {
		String connectionData = createConnectionData(new String[] {CONNECTION_TWO_SETTINGS});
		InputStream is = new ByteArrayInputStream(connectionData.getBytes());
		List<IConnection> connections = Reader.readFromXML(new ConnectionTypeProvider(), is);
		assertEquals(1, connections.size());
		IConnection conn = connections.get(0);
		assertEquals(C2, conn.getIdentifier());
		assertEquals(TWO_SETTINGS_TYPE, conn.getConnectionType().getIdentifier());
		Map<String, String> settings = new HashMap();
		settings.put(VAL1_KEY, VAL1_VALUE);
		settings.put(VAL2_KEY, VAL2_VALUE);
		assertEquals(settings, conn.getSettings());
	}
	
	public void testReadMultConnections() throws Exception {
		String connectionData = 
			createConnectionData( 
					new String[] {CONNECTION_NO_SETTINGS, CONNECTION_ONE_SETTING, CONNECTION_TWO_SETTINGS} );
		InputStream is = new ByteArrayInputStream(connectionData.getBytes());
		List<IConnection> connections = Reader.readFromXML(new ConnectionTypeProvider(), is);
		assertEquals(3, connections.size());
		IConnection conn0 = connections.get(0);
		assertEquals(C0, conn0.getIdentifier());
		assertEquals(NO_SETTINGS_TYPE, conn0.getConnectionType().getIdentifier());
		IConnection conn1 = connections.get(1);
		assertEquals(C1, conn1.getIdentifier());
		assertEquals(ONE_SETTING_TYPE, conn1.getConnectionType().getIdentifier());
		assertEquals(Collections.singletonMap(VAL1_KEY, VAL1_VALUE), conn1.getSettings());
		IConnection conn2 = connections.get(2);
		assertEquals(C2, conn2.getIdentifier());
		assertEquals(TWO_SETTINGS_TYPE, conn2.getConnectionType().getIdentifier());
		Map<String, String> settings = new HashMap();
		settings.put(VAL1_KEY, VAL1_VALUE);
		settings.put(VAL2_KEY, VAL2_VALUE);
		assertEquals(settings, conn2.getSettings());
	}
}
