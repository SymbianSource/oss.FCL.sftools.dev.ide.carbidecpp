/**
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

package com.nokia.carbide.remoteconnections.internal.api;

import java.net.URL;

import org.eclipse.core.runtime.CoreException;

/**
 * An interface to a device discovery agent
 * @since 3.0
 */
public interface IDeviceDiscoveryAgent {

	/**
	 * An interface for discovery agents to implement that the manager uses
	 * before loading the agent to get errors about required components.
	 * <p>
	 * Every discovery agent must implement this interface.
	 */
	public interface IPrerequisiteStatus {
		/**
		 * Is the status OK?
		 * @return boolean 
		 */
		boolean isOK();
		/**
		 * If status is not OK, return error message text.
		 * @return String
		 */
		String getErrorText();
		/**
		 * Optionally return a URL for user to browse to for more information on error.
		 * @return URL
		 */
		URL getURL();
		
	}

	/**
	 * Starts agent. Once started, runs until stopped.
	 * @throws CoreException
	 */
	void start() throws CoreException;
	
	/**
	 * Stops agent.
	 * @throws CoreException
	 */
	void stop() throws CoreException;
	
	/**
	 * Returns a URL to specific information about this discovery mechanism,
	 * troubleshooting, etc.
	 * @return URL
	 */
	URL getInformation();
	
	/**
	 * Returns a display name for the particular discovery agent in case we need to
	 * let the user know errors from the agents.
	 * @return String
	 */
	String getDisplayName();
	
	/**
	 * Manager will call this to get any status of prerequisites not satisfied before
	 * the manager calls start(). The agent should check its prerequisites at this time.
	 * <p>
	 * If the agent has no prerequisites return a status of OK.
	 * @return IPrerequisiteStatus
	 */
	IPrerequisiteStatus getPrerequisiteStatus();
	
	// In addition, there may need to be an additional API to do a deeper form of discovery for
	// connection mechanisms that require pairing (like BT or Wifi). In these cases, normal discovery
	// will probably be for already paired devices, however, the user will want to discover all 
	// potential devices from some UI in order to set up a paired device.
	// A method for doing this will need to be added.
	
}
