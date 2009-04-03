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

/**
 * 
 * Interface for embedded web server
 * 
 */
public interface IEmbeddedWebServer {
	/**
	 * Start web server
	 * 
	 * @param ID
	 *            Identity of the web server.
	 * @throws Exception
	 */
	void startWebServer(String ID) throws Exception;

	/**
	 * Stops webserver
	 * 
	 * @throws Exception
	 */
	void stopWebServer() throws Exception;

	/**
	 * Get id of web server
	 * 
	 * @return id of web server
	 */
	String getServerID();

	/**
	 * Get root address of web server (for example http://localhost:port/hover/)
	 * 
	 * @return root address of web server
	 */
	String getRootPath();
}
