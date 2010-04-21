/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.cdt.debug.common.internal.api;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;

import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.cdt.debug.common.CarbideCommonDebuggerPlugin;

/**
 * This extension augments a launch delegate's behavior.  Every extension is called for
 * every connection-based launch; ignore the call if the connection is not relevant
 * for your needs. 
 * 
 * Note: the extension instance is created once for each session.
 */
public interface ILaunchDelegateConnectionExtension {
	String ID = CarbideCommonDebuggerPlugin.PLUGIN_ID + ".launchDelegateConnectionExtension"; //$NON-NLS-1$
	
	/** 
	 * Initialize a connection for a run or debug launch after it has been selected (either it was
	 * static or was selected by the user; you cannot influence the connection selector).
	 * This is called before the connection is marked "in use".
	 * @param launch
	 * @param connection
	 * @param monitor
	 * @throws CoreException if the extension determines some problem with the
	 * connection for this configuration
	 */
	void initializeConnection(ILaunch launch, IConnection connection, IProgressMonitor monitor) throws CoreException;

	/**
	 * Handle any work when the launch has started successfully.
	 * @param launch
	 * @param connection
	 * @param monitor 
	 * @throws CoreException
	 */
	void launchStarted(ILaunch launch, IConnection connection, IProgressMonitor monitor) throws CoreException; 
	
	/**
	 * Terminate a connection for a launch after a session has completed.  This
	 * is called before the connection is marked "not in use".
	 * @param launch
	 * @param connection
	 * @param monitor
	 * @throws CoreException if the extension encounters a failure terminating the connection
	 */
	void terminateConnection(ILaunch launch, IConnection connection, IProgressMonitor monitor) throws CoreException;

}
