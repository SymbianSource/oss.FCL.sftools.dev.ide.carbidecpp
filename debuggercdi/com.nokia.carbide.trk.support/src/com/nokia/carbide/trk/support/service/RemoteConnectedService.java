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

import org.osgi.framework.Version;

import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IService;
import com.nokia.cpp.internal.api.utils.core.IListenerFiring;
import com.nokia.cpp.internal.api.utils.core.ListenerList;

/**
 * This is a stub for the TRK and Trace connected services used when the host
 * does not support Chad's TCF.  This reconciles the fact that a TRK connection publishes
 * these services, but we cannot actually instantiate them.
 * <p>
 * In the future this should find a way to talk to the device over Eclipse TCF or something.
 */
public class RemoteConnectedService implements IConnectedService {

	private boolean enabled = true;
	private final IService service;
	private IStatus unknownStatus = new IStatus() {

		public IConnectedService getConnectedService() {
			return RemoteConnectedService.this;
		}

		public EStatus getEStatus() {
			return EStatus.UNKNOWN;
		}

		public String getLongDescription() {
			return "The device is connected remotely; testing not yet implemented.";
		}

		public String getShortDescription() {
			return "Unknown";
		}
		
	};
	
	private IStatus noStatus = new IStatus() {

		public IConnectedService getConnectedService() {
			return RemoteConnectedService.this;
		}

		public EStatus getEStatus() {
			return EStatus.UNKNOWN;
		}

		public String getLongDescription() {
			return Messages.getString("AbstractConnectedService.UserDisabledMessage");
		}

		public String getShortDescription() {
			return Messages.getString("AbstractConnectedService.NoTestingLabel");
		}
		
	};
	private ListenerList<IStatusChangedListener> listeners;
	private IStatus currentStatus;
	/**
	 * 
	 */
	public RemoteConnectedService(IService service) {
		this.service = service;
		listeners = new ListenerList<IStatusChangedListener>();
		currentStatus = noStatus;
	}
	public void addStatusChangedListener(IStatusChangedListener listener) {
		listeners.add(listener);
	}

	public void dispose() {
	}

	public IService getService() {
		return service;
	}

	public IStatus getStatus() {
		return currentStatus;
	}

	public void removeStatusChangedListener(IStatusChangedListener listener) {
		listeners.remove(listener);
	}

	public void testStatus() {
	}

	public void setDeviceOS(String familyName, Version version) {
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled  = enabled;
		
		currentStatus = enabled ? unknownStatus : noStatus;
				
		fireListeners();
	}
	/**
	 * 
	 */
	private void fireListeners() {
		listeners.fireListeners(new IListenerFiring<IStatusChangedListener>() {
			
			public void fire(IStatusChangedListener listener) {
				listener.statusChanged(getStatus());
			}
		});
	}
}
