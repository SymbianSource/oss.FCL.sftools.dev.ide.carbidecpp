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


package com.nokia.carbide.remoteconnections.tests.extensions;

import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.util.Collections;
import java.util.Map;

public class IntervalConnection implements IConnection {

	private int msInterval;
	private String id;
	private String name;
	private IConnectionType connectionType;
	
	public IntervalConnection(IConnectionType connectionType) {
		this.connectionType = connectionType;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.extensions.IConnection#dispose()
	 */
	public void dispose() {
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.extensions.IConnection#getConnectionType()
	 */
	public IConnectionType getConnectionType() {
		return connectionType;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.extensions.IConnection#getIdentifier()
	 */
	public String getIdentifier() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IConnection#getDisplayName()
	 */
	public String getDisplayName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.interfaces.IConnection#setDisplayName(java.lang.String)
	 */
	public void setDisplayName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.extensions.IConnection#getSettings()
	 */
	public Map<String, String> getSettings() {
		return Collections.singletonMap(IntervalConnectionType.KEY, Integer.toString(msInterval));
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.extensions.IConnection#setIdentifier(java.lang.String)
	 */
	public void setIdentifier(String id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.remoteconnections.extensions.IConnection#updateSettings(java.util.Map)
	 */
	public void updateSettings(Map<String, String> newSettings) {
		Check.checkContract(newSettings.containsKey(IntervalConnectionType.KEY));
		String string = newSettings.get(IntervalConnectionType.KEY);
		msInterval = Integer.parseInt(string);
	}

	public int getInterval() {
		return msInterval;
	}
	
	public void setInterval(int ms) {
		msInterval = ms;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public void useConnection(boolean use) {
	}
}
