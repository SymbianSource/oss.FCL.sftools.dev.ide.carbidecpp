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

import java.util.HashMap;
import java.util.Map;

import com.nokia.carbide.remoteconnections.interfaces.AbstractConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;
import com.nokia.cpp.internal.api.utils.core.Check;

public class IntervalConnection extends AbstractConnection {

	private int msInterval;
	
	public IntervalConnection(IConnectionType connectionType) {
		super(connectionType, new HashMap<String, String>());
		msInterval = Integer.parseInt(IntervalConnectionType.VALUE);
		getSettings().put(IntervalConnectionType.KEY, Integer.toString(msInterval));
	}

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
		return getDisplayName();
	}

	public void useConnection(boolean use) {
		// TODO Auto-generated method stub
	}
}
