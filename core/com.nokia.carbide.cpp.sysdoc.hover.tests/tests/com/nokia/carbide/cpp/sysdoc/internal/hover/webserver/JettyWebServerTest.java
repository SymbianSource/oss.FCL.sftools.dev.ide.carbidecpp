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
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.internal.hover.webserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

import com.nokia.carbide.cpp.sysdoc.hover.Activator;
import com.nokia.carbide.cpp.sysdoc.hover.BaseTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants;

public class JettyWebServerTest extends BaseTest {

	@Test
	public void testStartStopWebServer() throws Exception {
		IEmbeddedWebServer webServer = new JettyWebServer();
		webServer.startWebServer(Activator.PLUGIN_ID);
		Thread.sleep(5000);
		webServer.stopWebServer();
	}

	@Test
	public void testReadWebPage() throws Exception {
		IEmbeddedWebServer webServer = new JettyWebServer();
		webServer.startWebServer(Activator.PLUGIN_ID);
		String url = "http://localhost:" + JettyConfig.getPort()
				+ HoverConstants.SERVLET_HOVER_CONTEXT;
		String content = readWebPage(url, true);
		Thread.sleep(5000);
		assertTrue(content.equals(HoverConstants.TEST_CONTENT));

	}

	public static String readWebPage(String url, boolean testMode)
			throws Exception {
		ServletJarBridge.setTestMode(testMode);
		StringBuffer sb = new StringBuffer();
		URL page = new URL(url);
		URLConnection conn = page.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn
				.getInputStream()));
		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			sb.append(inputLine);
		}
		in.close();
		return sb.toString();
	}
}
