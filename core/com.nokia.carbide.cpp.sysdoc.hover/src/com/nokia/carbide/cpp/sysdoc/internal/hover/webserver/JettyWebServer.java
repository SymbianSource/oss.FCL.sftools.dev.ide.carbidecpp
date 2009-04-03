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

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.http.jetty.JettyConfigurator;
import org.eclipse.equinox.http.jetty.JettyConstants;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;

public class JettyWebServer implements IEmbeddedWebServer {
	private String serverID;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nokia.carbide.cpp.sysdoc.internal.hover.webserver.IEmbeddedWebServer
	 * #startWebServer(java.lang.String)
	 */
	public void startWebServer(String ID) throws BundleException {

		Bundle bundle = Platform.getBundle("org.eclipse.equinox.http.registry");

		if (bundle.getState() == Bundle.RESOLVED) {
			bundle.start(Bundle.START_TRANSIENT);
		}

		Dictionary<String, Object> settings = new Hashtable<String, Object>();
		settings.put(JettyConstants.HTTP_ENABLED, JettyConfig.isHttpEnabled());
		settings
				.put(JettyConstants.HTTPS_ENABLED, JettyConfig.isHttpsEnabled());
		settings.put(JettyConstants.HTTP_PORT, JettyConfig.getPort());
		settings.put(JettyConstants.HTTP_HOST, JettyConfig.getHostName());
		settings.put(JettyConstants.CONTEXT_PATH, JettyConfig.getContextPath());
		settings.put(JettyConstants.CONTEXT_SESSIONINACTIVEINTERVAL,
				JettyConfig.getContectSessionInActiveInterval());
		try {
			serverID = ID + ".server";
			JettyConfigurator.startServer(serverID, settings);
		} catch (Exception e) {
			Logger.logError(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nokia.carbide.cpp.sysdoc.internal.hover.webserver.IEmbeddedWebServer
	 * #getRootPath()
	 */
	public String getRootPath() {
		String host = "http://";
		String hostName = JettyConfig.getHostName();
		if (hostName.equals("0.0.0.0") || hostName.equals("localhost")
				|| hostName.equals("127.0.0.1")) {
			host = host + "localhost";
		} else {
			host = host + hostName;
		}
		String path = host + ":" + JettyConfig.getPort()
				+ HoverConstants.SERVLET_HOVER_CONTEXT;
		return path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nokia.carbide.cpp.sysdoc.internal.hover.webserver.IEmbeddedWebServer
	 * #stopWebServer()
	 */
	public void stopWebServer() throws Exception {
		JettyConfigurator.stopServer(serverID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nokia.carbide.cpp.sysdoc.internal.hover.webserver.IEmbeddedWebServer
	 * #getServerID()
	 */
	public String getServerID() {
		return serverID;
	}

}
