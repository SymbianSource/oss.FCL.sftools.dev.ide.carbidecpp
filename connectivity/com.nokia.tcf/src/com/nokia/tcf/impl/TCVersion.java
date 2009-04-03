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

import com.nokia.tcf.api.ITCVersion;

public class TCVersion implements ITCVersion {

	private String version = null;

	public TCVersion() {
		this.version = new String();
	}
	/**
	 * Construct version with specified string.
	 * 
	 * @param version
	 */
	public TCVersion(String version) {
		super();
		this.version = version;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCVersion#getVersionString()
	 */
	public String getVersionString() {
		return this.version;
	}

	/* (non-Javadoc)
	 * @see com.nokia.tcf.api.ITCVersion#setVersionString(java.lang.String)
	 */
	public void setVersionString(String inVersion) {
		this.version = inVersion;
	}

}
