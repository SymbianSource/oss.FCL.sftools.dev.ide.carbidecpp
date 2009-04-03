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
*
* Description: 
*
*/
/**
 * 
 */
package com.nokia.tcf.api;

import org.eclipse.core.runtime.IStatus;

/**
 * This is the main API class and must be instantiated by the client. 
 * The implementation can be created by using the TCFClassFactory.
 */
public interface ITCAPIConnection {

	/** 
	 * Adds an error listener - multiple error listeners can be used
	 * by one client
	 *  
	 * @param inErrorListener
	 * @return
	 */
	public IStatus addErrorListener(ITCErrorListener inErrorListener);
	
	/**
	 * Remove an error listener from the list for this client
	 * 
	 * @param inErrorListener
	 * @return
	 */
	public IStatus removeErrorListener(ITCErrorListener inErrorListener);

	/**
	 * Connect using a specified connection type and settings. If this
	 * connection is already open, the inConnection settings is ignored.
	 * 
	 * @param inConnection
	 * @param inMessageOptions
	 * @param inMessageIds
	 * @return IStatus
	 */
	public IStatus connect(ITCConnection inConnection, ITCMessageOptions inMessageOptions, ITCMessageIds inMessageIds);
	
	/**
	 * Connect using an already open connection. If no connections or multiple
	 * connections are open, then this returns an error.
	 * 
	 * Use getConnections to get a list of open connections.
	 * 
	 * @param inMessageOptions
	 * @param inMessageIds
	 * @return IStatus
	 */
	public IStatus connect(ITCMessageOptions inMessageOptions, ITCMessageIds inMessageIds);
	
	/**
	 * Disconnect from the target. If this client is not connected this is
	 * ignored.
	 * 
	 * @return IStatus
	 */
	public IStatus disconnect();

	/**
	 * Get all the open connection settings. This will work whether or not
	 * this client is connected. If no connections exist null is returned.
	 * 
	 * @return ITCConnecion[]
	 */
	public ITCConnection[] getConnections();

	/**
	 * Get a reference to the input stream for this client. This can only be
	 * done if a successful {@link #connect} has been done.
	 * 
	 * This is an alternate form of storage for messages to that of the message file.
	 * 
	 * If a connection does not exist, null is returned.
	 * 
	 * @return ITCMessageInputStream
	 */
	public ITCMessageInputStream getInputStream();

	/**
	 * Get the versions of connection components for an open connection.
	 * 
	 * If a connection does not exist, null is returned.
	 * 
	 * If a connection exists, at least 2 versions will be returned:
	 * [0] = version of TCFClient.dll
	 * [1] = version of TCFServer.exe
	 * If the connection version can be obtained, [2] will contain that version.
	 * 
	 * @return ITCVersion[]
	 */
	public ITCVersion[] getVersions();
	
	/**
	 * Send a message to the target.
	 * 
	 * If a connection does not exist, an error is returned.
	 * 
	 * @param inMessage
	 * @return IStatus
	 */
	public IStatus sendMessage(ITCMessage inMessage);
	
	/**
	 * Resets the message Ids to process for this client. Processing must
	 * be stopped to change this. If this client is not connected, an error
	 * is returned.
	 * 
	 * @param inMessageIds
	 */
	public IStatus setTCMessageIds(ITCMessageIds inMessageIds);
	
	/**
	 * Start (or restart) processing messages. A successful {@link #connect} does an automatic
	 * start, so a separate call to this is unnecessary, unless a {@link #stop} has
	 * been done.
	 * 
	 * If processing is already started, this will be ignored. 
	 * If a connection does not exist, an error is returned.
	 * 
	 * @return IStatus
	 */
	public IStatus start();

	/**
	 * Stop processing messages without disconnecting. Use {@link #start} to restart.
	 * 
	 * If processing is already stopped, this is ignored.
	 * If a connection does not exist, an error is returned.
	 * 
	 * @return IStatus
	 */
	public IStatus stop();
	
	/**
	 * Test this client's connection.
	 * 
	 * If a connection does not exist, an error is returned.
	 * 
	 * @return IStatus
	 */
	public IStatus testConnection();
	
	/**
	 * Test the specified connection. It this matches a currently open connection, it
	 * returns the status of that connection and leaves that connection open. If this
	 * connection is not yet open, it opens this new connection, returns the status, and
	 * closes the connection.
	 * 
	 * @param inConnection
	 * @return
	 */
	public IStatus testConnection(ITCConnection inConnection);
	
	/**
	 * Clears the current client's message output file. If no file is in use (closed or never opened)
	 * this method does nothing.
	 * 
	 * Otherwise, it tells the TCF to stop processing for this client, closes and re-opens the message file.
	 * 
	 * @return IStatus - if I/O Exception occurs
	 */
	public IStatus clearMessageFile();
	
}
