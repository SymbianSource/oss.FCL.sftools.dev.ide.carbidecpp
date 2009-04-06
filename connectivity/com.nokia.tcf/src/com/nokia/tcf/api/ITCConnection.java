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
 * This interface the basis of all connection type classes. Instantiate one of these classes using
 * the TCFClassFactory for the connection type required.
 * 
 */
public interface ITCConnection {
	
	/**
	 * Default retry interval and retry timeout (in seconds)
	 */
	public final long DEFAULT_COMM_ERROR_RETRY_INTERVAL = 1; // 1 second
	public final long DEFAULT_COMM_ERROR_RETRY_TIMEOUT = 5*60; // 5 minutes

	/**
	 * Return a string detailing this connection
	 * @return
	 */
	public String getConnectionDescription();
	/**
	 * Return connection type
	 * @return
	 */
	public String getConnectionType();
	/**
	 * Get the current retry interval in seconds
	 * @return
	 */
	public long getRetryInterval();
	/**
	 * Get the current retry timeout in seconds
	 * @return
	 */
	public long getRetryTimeout();
	/**
	 * Set the connection type
	 * @param inConnectionType
	 * @return IStatus
	 */
	public IStatus setConnectionType(String inConnectionType);
	
	/**
	 * Set the retry interval after a comm error in seconds. The default is 1 second. 
	 * Must be >= 1 second and less then retry timeout.
	 * 
	 * @param inRetryInterval in seconds
	 * @return IStatus
	 */
	public IStatus setRetryInterval(long inRetryInterval);
	/**
	 * Set the retry timeout after a comm error in seconds. The default is 5 minutes.
	 * Must be > then the retry interval.
	 * 
	 * @param inRetryTimeout in seconds
	 * @return IStatus
	 */
	public IStatus setRetryTimeout(long inRetryTimeout);

	/**
	 * Specify which message format to decode on incoming messages
	 * A string to indicate the formatting types
	 * e.g. "platsim", "ost", etc.
	 * 
	 * @param inDecodeFormat
	 */
	public IStatus setDecodeFormat(String inDecodeFormat);

	/**
	 * Returns the current decode format for this connection
	 * 
	 * @return
	 */
	public String getDecodeFormat();
}
