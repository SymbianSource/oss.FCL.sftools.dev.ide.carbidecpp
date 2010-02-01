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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;

import com.nokia.carbide.remoteconnections.internal.api.IConnection2;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatus.EConnectionStatus;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ListenerList;

/**
 * A standard implementation of IConnection
 */
public abstract class AbstractConnection implements IConnection2 {
	
	public static class ConnectionStatus implements IConnectionStatus {
		private EConnectionStatus estatus;
		private String shortDescription;
		private String longDescription;
		
		public ConnectionStatus(EConnectionStatus estatus, String shortDescription, String longDescription) {
			this.estatus = estatus;
			this.shortDescription = shortDescription;
			this.longDescription = longDescription;
		}

		public EConnectionStatus getEConnectionStatus() {
			return estatus;
		}
		
		public String getShortDescription() {
			return shortDescription;
		}

		public String getLongDescription() {
			return longDescription;
		}
		
		public void setEStatus(EConnectionStatus estatus) {
			this.estatus = estatus;
		}

		public void setDescriptions(String shortDescription, String longDescription) {
			this.shortDescription = shortDescription;
			this.longDescription = longDescription;
		}
	}

	private final IConnectionType connectionType;
	private final Map<String, String> settings;
	private String name;
	private String id;
	private boolean dynamic;
	private IConnectionStatus status;
	private ImageDescriptor imageDescriptor;
	private ListenerList<IConnectionStatusChangedListener> listeners;

	public AbstractConnection(IConnectionType connectionType, Map<String, String> settings) {
		this.connectionType = connectionType;
		this.settings = new HashMap<String, String>(settings);
		status = new ConnectionStatus(EConnectionStatus.NONE, "", ""); //$NON-NLS-1$ //$NON-NLS-2$
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

	public boolean isDynamic() {
		return dynamic;
	}
	
	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}
	
	public IConnectionStatus getStatus() {
		return status;
	}

	public void setStatus(IConnectionStatus status) {
		Check.checkArg(status);
		this.status = status;
		fireStatusChanged();
	}
	
	public void addStatusChangedListener(IConnectionStatusChangedListener listener) {
		if (listeners == null)
			listeners = new ListenerList<IConnectionStatusChangedListener>();
		listeners.add(listener);
	}
	
	public void removeStatusChangedListener(IConnectionStatusChangedListener listener) {
		if (listeners != null)
			listeners.remove(listener);
	}
	
	public void fireStatusChanged() {
		if (listeners == null)
			return;
		for (IConnectionStatusChangedListener listener : listeners) {
			listener.statusChanged(status);
		}
	}

	public ImageDescriptor getImageDescriptor() {
		return imageDescriptor;
	}
	
	public void setImageDescriptor(ImageDescriptor imageDescriptor) {
		this.imageDescriptor = imageDescriptor;
	}
	

}
