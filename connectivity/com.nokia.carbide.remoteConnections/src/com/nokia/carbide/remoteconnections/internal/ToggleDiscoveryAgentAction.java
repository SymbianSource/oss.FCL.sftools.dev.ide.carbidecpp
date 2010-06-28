/**
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

package com.nokia.carbide.remoteconnections.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.internal.api.IDeviceDiscoveryAgent;

public class ToggleDiscoveryAgentAction extends Action {

	private final IDeviceDiscoveryAgent agent;

	public ToggleDiscoveryAgentAction(IDeviceDiscoveryAgent agent) {
		this.agent = agent;
		setChecked(agent.isRunning());
	}

	@Override
	public void run() {
		try {
			if (agent.isRunning())
				agent.stop();
			else
				agent.start();
			setChecked(agent.isRunning());
		} catch (CoreException e) {
			RemoteConnectionsActivator.logError(e);
		}
	}
	
	@Override
	public int getStyle() {
		return IAction.AS_CHECK_BOX;
	}
	
	@Override
	public String getText() {
		return agent.getDisplayName();
	}
}

