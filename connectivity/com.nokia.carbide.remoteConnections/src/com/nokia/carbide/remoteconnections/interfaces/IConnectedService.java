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


package com.nokia.carbide.remoteconnections.interfaces;

import org.osgi.framework.Version;

/**
 * An interface for a service connected to a remote agent via a connection
 */
public interface IConnectedService {
	
	/**
	 * The status of a connected service
	 */
	public interface IStatus {
		enum EStatus {
			DOWN, UP, IN_USE, UNKNOWN
		};
		
		EStatus getEStatus();
		
		String getShortDescription();
		
		String getLongDescription();
		
		IConnectedService getConnectedService();
	}

	/**
	 * A listener for status changes
	 */
	public interface IStatusChangedListener {
		void statusChanged(IStatus status);
	}

	/**
	 * Adds a listener for status changes
	 * @param listener IStatusChangedListener
	 */
	void addStatusChangedListener(IStatusChangedListener listener);
	
	/**
	 * Removes a listener for status changes
	 * @param listener IStatusChangedListener
	 */
	void removeStatusChangedListener(IStatusChangedListener listener);

	/**
	 * Tests the status of this service and potentially causes a status change
	 */
	void testStatus();
	
	/**
	 * Get the current status
	 * @return IStatus
	 */
	IStatus getStatus();
	
	/**
	 * Dispose this connected service
	 */
	void dispose();

	/**
	 * Return the IService the created this connected service.
	 * @return IService
	 */
	IService getService();
	
	/**
	 * Set the device OS values, to potentially affect the information for the user.
	 * @param familyName
	 * @param version
	 */
	void setDeviceOS(String familyName, Version version);
	
	/**
	 * Set the connected service enabled state
	 * @param enabled boolean
	 */
	void setEnabled(boolean enabled);
	
	/**
	 * Return whether the connected service is enabled
	 * @return boolean
	 */
	boolean isEnabled();
}

