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

import java.io.IOException;
import java.net.ServerSocket;

import com.nokia.carbide.cpp.sysdoc.internal.hover.core.MessagesConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.exceptions.HoverException;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;

/**
 * A configuration class holds properties of jetty server
 */
public class JettyConfig {
	private static int port = -1;

	public static int getPort() {
		if (port == -1) {
			port = findFreePort();
		}
		return port;
	}

	public static Boolean isHttpsEnabled() {
		return Boolean.FALSE;
	}

	public static Boolean isHttpEnabled() {
		return Boolean.TRUE;
	}

	public static String getHostName() {
		return "0.0.0.0"; // localhost
	}

	public static String getContextPath() {
		return "/";
	}

	public static int getContectSessionInActiveInterval() {
		return 1800;
	}

	/**
	 * Find a free port
	 * 
	 * @return a free port on localhost, otherwise it throws a
	 *         {@link HoverInitializationException} exception
	 */
	private static int findFreePort() {
		ServerSocket tmpSocket = null;
		try {
			tmpSocket = new ServerSocket(0);
			return tmpSocket.getLocalPort();
		} catch (IOException e) {
			Logger.logError(e);
		} finally {
			if (tmpSocket != null) {
				try {
					// we dont need socket anymore, lets close it...
					tmpSocket.close();
				} catch (IOException e) {
				}
			}
		}
		throw new HoverException(MessagesConstants.NO_FREE_PORT);
	}
}
