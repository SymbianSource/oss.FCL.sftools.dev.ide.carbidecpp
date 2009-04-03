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

import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;

import java.util.HashMap;
import java.util.Map;

/**
 * A standard implementation of IConnection
 */
public abstract class AbstractConnection implements IConnection {

	private final IConnectionType connectionType;
	private final Map<String, String> settings;
	private String name;
	private String id;

	public AbstractConnection(IConnectionType connectionType, Map<String, String> settings) {
		this.connectionType = connectionType;
		this.settings = new HashMap<String, String>(settings);
	}

	public void dispose() {
	}

	public IConnectionType getConnectionType() {
		return connectionType;
	}

	public String getDisplayName() {
		return name;
	}

	public String getIdentifier() {
		return id;
	}

	public Map<String, String> getSettings() {
		return settings;
	}

	public void setDisplayName(String name) {
		this.name = name;
	}

	public void setIdentifier(String id) {
		this.id = id;
	}

	public synchronized void updateSettings(Map<String, String> newSettings) {
//		System.out.println("settings update, thread="+Thread.currentThread().getId());
		settings.putAll(newSettings);
	}

}
