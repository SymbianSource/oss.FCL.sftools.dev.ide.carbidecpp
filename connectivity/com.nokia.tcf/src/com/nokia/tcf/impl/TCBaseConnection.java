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
package com.nokia.tcf.impl;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.nokia.tcf.Activator;
import com.nokia.tcf.api.ITCConnection;
import com.nokia.tcf.api.TCErrorConstants;

public abstract class TCBaseConnection implements ITCConnection {

	private int connectionType;
	private long retryInterval;
	private long retryTimeout;
	protected String decodeFormat;
	/**
	 * @param connectionType
	 * @param retryInterval
	 * @param retryTimeout
	 * @param decodeFormat
	 */
	public TCBaseConnection(int connectionType, long retryInterval,
			long retryTimeout, String decodeFormat) {
		super();
		this.connectionType = connectionType;
		this.retryInterval = retryInterval;
		this.retryTimeout = retryTimeout;
		this.decodeFormat = decodeFormat;
	}

	/**
	 * @param connectionType
	 */
	public TCBaseConnection(int connectionType) {
		super();
		this.connectionType = connectionType;
		this.retryInterval = DEFAULT_COMM_ERROR_RETRY_INTERVAL;
		this.retryTimeout = DEFAULT_COMM_ERROR_RETRY_TIMEOUT;
		this.decodeFormat = "ost";
	}

	/**
	 * @param connectionType
	 * @param retryInterval
	 * @param retryTimeout
	 */
	public TCBaseConnection(int connectionType, long retryInterval,
			long retryTimeout) {
		super();
		this.connectionType = connectionType;
		this.retryInterval = retryInterval;
		this.retryTimeout = retryTimeout;
		this.decodeFormat = "ost";
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCConnection#getConnectionString()
	 */
	public abstract String getConnectionDescription();
	
	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCConnection#getConnectionType()
	 */
	public int getConnectionType() {
		return this.connectionType;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCConnection#getRetryInterval()
	 */
	public long getRetryInterval() {
		return this.retryInterval;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCConnection#getRetryTimeout()
	 */
	public long getRetryTimeout() {
		return this.retryTimeout;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCConnection#setConnectionType(int)
	 */
	public IStatus setConnectionType(int inConnectionType) {
		IStatus status = new Status(Status.OK, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_NONE, "OK", null);
		connectionType = inConnectionType;
		return status;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCConnection#setRetryInterval(long)
	 */
	public IStatus setRetryInterval(long inRetryInterval) {
		IStatus status = new Status(Status.OK, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_NONE, "OK", null);
		if (inRetryInterval <= 0) {
			this.retryInterval = ITCConnection.DEFAULT_COMM_ERROR_RETRY_INTERVAL;
		} else {
			this.retryInterval = inRetryInterval;
		}
		return status;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCConnection#setRetryTimeout(long)
	 */
	public IStatus setRetryTimeout(long inRetryTimeout) {
		IStatus status = new Status(Status.OK, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_NONE, "OK", null);
		if (inRetryTimeout <= 0) {
			this.retryTimeout = ITCConnection.DEFAULT_COMM_ERROR_RETRY_TIMEOUT;
		} else {
			this.retryTimeout = inRetryTimeout;
		}
		return status;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCConnection#getDecodeFormat()
	 */
	public String getDecodeFormat() {
		return this.decodeFormat;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCConnection#setDecodeFormat(long)
	 */
	public IStatus setDecodeFormat(String inDecodeFormat) {
		IStatus status = new Status(Status.OK, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_NONE, "OK", null);
		if ((inDecodeFormat.compareToIgnoreCase("platsim") == 0) ||
				(inDecodeFormat.compareToIgnoreCase("ost") == 0) ||
				(inDecodeFormat.compareToIgnoreCase("rawtrk") == 0)) {
			decodeFormat = inDecodeFormat;
		} else {
			status  = new Status(Status.ERROR, Activator.PLUGIN_ID, (int)TCErrorConstants.TCAPI_ERR_INVALID_DECODE_FORMAT, "Error", null);
			decodeFormat = "ost";
		}
		return status;
	}

}
