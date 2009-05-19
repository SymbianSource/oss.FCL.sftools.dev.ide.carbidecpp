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

package com.nokia.carbide.remoteconnections.tests.extensions;

import com.nokia.carbide.remoteconnections.interfaces.*;
import com.nokia.carbide.trk.support.connection.TCPIPConnectionFactory;

import org.osgi.framework.Version;

import java.util.Collections;
import java.util.Map;

@SuppressWarnings("restriction")
public class DefaultProvidingTCPIPService implements IService2 {

	public static final String DEFAULT_PORT = "4144";
	public static String ID = DefaultProvidingTCPIPService.class.getName();

	public DefaultProvidingTCPIPService() {
	}

	public String getAdditionalServiceInfo() {
		return "No testing available for this service";
	}

	public String getDisplayName() {
		return "Default Providing Service";
	}

	public String getIdentifier() {
		return ID;
	}

	public IRemoteAgentInstallerProvider getInstallerProvider() {
		return null;
	}

	public boolean isTestable() {
		return false;
	}

	public Map<String, String> getDefaults() {
		return Collections.singletonMap(TCPIPConnectionFactory.IP_PORT, DEFAULT_PORT);
	}

	public IConnectedService createInstance(IConnection connection) {
		final IConnectedService[] connectedService = { null }; 
		IConnectedService cs = new IConnectedService() {
			private boolean enabled;

			public void addStatusChangedListener(IStatusChangedListener listener) {
			}

			public void dispose() {
			}

			public IService getService() {
				return DefaultProvidingTCPIPService.this;
			}

			public IStatus getStatus() {
				return new IStatus() {
					public IConnectedService getConnectedService() {
						return connectedService[0];
					}

					public EStatus getEStatus() {
						return EStatus.UNKNOWN;
					}

					public String getLongDescription() {
						return "This is for testing only";
					}

					public String getShortDescription() {
						return "Unknown status";
					}
				};
			}

			public boolean isEnabled() {
				return enabled;
			}

			public void removeStatusChangedListener(IStatusChangedListener listener) {
			}

			public void setDeviceOS(String familyName, Version version) {
			}

			public void setEnabled(boolean enabled) {
				this.enabled = enabled;
			}

			public void testStatus() {
			}
		};

		connectedService[0] = cs;
		return cs;
	}

	public boolean wantsDeviceOS() {
		return false;
	}

	public Object getAdapter(Class adapter) {
		return null;
	}
}
