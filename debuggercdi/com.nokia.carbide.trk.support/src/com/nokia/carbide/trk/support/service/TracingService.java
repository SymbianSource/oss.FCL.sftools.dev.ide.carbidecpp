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


package com.nokia.carbide.trk.support.service;

import com.nokia.carbide.remoteconnections.interfaces.*;
import com.nokia.carbide.trk.support.Messages;
import com.nokia.carbide.trk.support.connection.TCPIPConnectionType;
import com.nokia.carbide.trk.support.connection.USBConnectionType;
import com.nokia.cpp.internal.api.utils.core.Check;


import java.util.Arrays;
import java.util.Collection;

/**
 * Implementation of IService for Tracing
 */
public class TracingService implements IService {

	public IConnectedService createInstance(IConnection connection) {
		Check.checkContract(connection instanceof AbstractSynchronizedConnection);
		return new TracingConnectedService(this, (AbstractSynchronizedConnection) connection);
	}

	public String getAdditionalServiceInfo() {
		return Messages.getString("TracingService.AdditionalInfoString"); //$NON-NLS-1$
	}

	public Collection<String> getCompatibleConnectionTypeIds() {
		return Arrays.asList(new String[] {
				TCPIPConnectionType.ID,
				USBConnectionType.ID
		});
	}

	public String getDisplayName() {
		return Messages.getString("TracingService.Label"); //$NON-NLS-1$
	}

	public String getIdentifier() {
		return getClass().getName();
	}

	public IRemoteAgentInstallerProvider getInstallerProvider() {
		return new TracingInstallerProvider(this);
	}

	public boolean isTestable() {
		return true;
	}

}
