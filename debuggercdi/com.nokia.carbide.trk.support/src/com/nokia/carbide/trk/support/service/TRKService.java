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
import com.nokia.carbide.trk.support.connection.*;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.HostOS;

import java.util.*;

/**
 * Implementation of IService for TRK
 */
public class TRKService implements IService {

	public IConnectedService createInstance(IConnection connection) {
		Check.checkContract(connection instanceof AbstractSynchronizedConnection);
		return new TRKConnectedService(this, (AbstractSynchronizedConnection) connection);
	}

	public String getAdditionalServiceInfo() {
		return Messages.getString("TRKService.ServiceInfo"); //$NON-NLS-1$
	}

	public Collection<String> getCompatibleConnectionTypeIds() {
		return Arrays.asList(new String[] {
				SerialConnectionType.ID, 
				SerialBTConnectionType.ID,
				TCPIPConnectionType.ID,
				USBConnectionType.ID}); 
	}

	public String getDisplayName() {
		return Messages.getString("TRKService.Label"); //$NON-NLS-1$
	}

	public String getIdentifier() {
		return getClass().getName();
	}

	public IRemoteAgentInstallerProvider getInstallerProvider() {
		if (HostOS.IS_UNIX)
			return null;		// TODO: implement

		return new TRKInstallerProvider(this);
	}

	public boolean isTestable() {
		return true;
	}

}
