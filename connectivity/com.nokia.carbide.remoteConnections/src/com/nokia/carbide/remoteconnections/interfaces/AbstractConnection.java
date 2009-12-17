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

import com.nokia.carbide.remoteconnections.internal.IConnection2;
import com.nokia.carbide.remoteconnections.internal.IConnection2.IStatus.EStatus;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ListenerList;

/**
 * A standard implementation of IConnection
 */
public abstract class AbstractConnection implements IConnection2 {
	
	public class Status implements IStatus {
		private EStatus estatus;
		private String description;
		
		public Status(EStatus estatus, String description) {
			this.estatus = estatus;
			this.description = description;
		}

		public EStatus getEStatus() {
			return estatus;
		}
		
		public String getDescription() {
			return description;
		}

		public void setEStatus(EStatus estatus) {
			this.estatus = estatus;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}

	private final IConnectionType connectionType;
	private final Map<String, String> settings;
	private String name;
	private String id;
	private boolean dynamic;
	private IStatus status;
	private ImageDescriptor imageDescriptor;
	private ListenerList<IStatusChangedListener> listeners;

	public AbstractConnection(IConnectionType connectionType, Map<String, String> settings) {
		this.connectionType = connectionType;
		this.settings = new HashMap<String, String>(settings);
		status = new Status(EStatus.NOT_READY, ""); //$NON-NLS-1$
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
	
	public IStatus getStatus() {
		return status;
	}

	public void setStatus(IStatus status) {
		Check.checkArg(status);
		this.status = status;
	}
	
	public void addStatusChangedListener(IStatusChangedListener listener) {
		if (listeners == null)
			listeners = new ListenerList<IStatusChangedListener>();
		listeners.add(listener);
	}
	
	public void removeStatusChangedListener(IStatusChangedListener listener) {
		if (listeners != null)
			listeners.remove(listener);
	}
	
	public void fireStatusChanged() {
		if (listeners == null)
			return;
		for (IStatusChangedListener listener : listeners) {
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
